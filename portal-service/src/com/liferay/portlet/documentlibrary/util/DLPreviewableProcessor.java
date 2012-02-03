/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.io.FileFilter;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Alexander Chow
 */
public abstract class DLPreviewableProcessor implements DLProcessor {

	public static final String PREVIEW_PATH = "document_preview/";

	public static final String PREVIEW_TMP_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/" + PREVIEW_PATH;

	public static final long REPOSITORY_ID = CompanyConstants.SYSTEM;

	public static final int THUMBNAIL_INDEX_CUSTOM_1 = 1;

	public static final int THUMBNAIL_INDEX_CUSTOM_2 = 2;

	public static final int THUMBNAIL_INDEX_DEFAULT = 0;

	public static final String THUMBNAIL_PATH = "document_thumbnail/";

	public static final String THUMBNAIL_TMP_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/" + THUMBNAIL_PATH;

	public static void deleteFiles() {
		long[] companyIds = PortalUtil.getCompanyIds();

		for (long companyId : companyIds) {
			try {
				DLStoreUtil.deleteDirectory(
					companyId, REPOSITORY_ID, PREVIEW_PATH);
			}
			catch (Exception e) {
			}

			try {
				DLStoreUtil.deleteDirectory(
					companyId, REPOSITORY_ID, THUMBNAIL_PATH);
			}
			catch (Exception e) {
			}
		}
	}

	public static void deleteFiles(FileEntry fileEntry, String thumbnailType) {
		deleteFiles(
			fileEntry.getCompanyId(), fileEntry.getRepositoryId(),
			fileEntry.getFileEntryId(), -1, thumbnailType);
	}

	public static void deleteFiles(
		FileVersion fileVersion, String thumbnailType) {

		deleteFiles(
			fileVersion.getCompanyId(), fileVersion.getRepositoryId(),
			fileVersion.getFileEntryId(), fileVersion.getFileVersionId(),
			thumbnailType);
	}

	public boolean isSupported(FileVersion fileVersion) {
		if (fileVersion == null) {
			return false;
		}

		return isSupported(fileVersion.getMimeType());
	}

	protected static void deleteFiles(
		long companyId, long groupId, long fileEntryId, long fileVersionId,
		String thumbnailType) {

		try {
			DLStoreUtil.deleteDirectory(
				companyId, REPOSITORY_ID,
				getPathSegment(groupId, fileEntryId, fileVersionId, true));
		}
		catch (Exception e) {
		}

		try {
			String dirName = getPathSegment(
				groupId, fileEntryId, fileVersionId, false);

			if (fileVersionId > 0) {
				dirName = dirName.concat(StringPool.PERIOD);
				dirName = dirName.concat(thumbnailType);
			}

			DLStoreUtil.deleteDirectory(companyId, REPOSITORY_ID, dirName);
		}
		catch (Exception e) {
		}
	}

	protected static String getPathSegment(
		FileVersion fileVersion, boolean preview) {

		return getPathSegment(
			fileVersion.getGroupId(), fileVersion.getFileEntryId(),
			fileVersion.getFileVersionId(), preview);
	}

	protected static String getPathSegment(
		long groupId, long fileEntryId, long fileVersionId, boolean preview) {

		StringBundler sb = null;

		if (fileVersionId > 0) {
			sb = new StringBundler(5);
		}
		else {
			sb = new StringBundler(3);
		}

		if (preview) {
			sb.append(PREVIEW_PATH);
		}
		else {
			sb.append(THUMBNAIL_PATH);
		}

		sb.append(groupId);
		sb.append(DLUtil.getDividedPath(fileEntryId));

		if (fileVersionId > 0) {
			sb.append(StringPool.SLASH);
			sb.append(fileVersionId);
		}

		return sb.toString();
	}

	protected void addFileToStore(
			long companyId, String dirName, String filePath, File srcFile)
		throws Exception {

		try {
			DLStoreUtil.addDirectory(companyId, REPOSITORY_ID, dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		DLStoreUtil.addFile(companyId, REPOSITORY_ID, filePath, srcFile);
	}

	protected void addFileToStore(
			long companyId, String dirName, String filePath, InputStream is)
		throws Exception {

		try {
			DLStoreUtil.addDirectory(companyId, REPOSITORY_ID, dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		DLStoreUtil.addFile(companyId, REPOSITORY_ID, filePath, is);
	}

	protected void directBinaryImportThumbnail(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement, String binPathName, int index)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

		String binPath = fileEntryElement.attributeValue(binPathName);

		InputStream is = portletDataContext.getZipEntryAsInputStream(binPath);

		String thumbnailFilePath = getThumbnailFilePath(
			fileVersion, getThumbnailType(fileVersion), index);

		addFileToStore(
			portletDataContext.getCompanyId(), THUMBNAIL_PATH,
			thumbnailFilePath, is);
	}

	protected InputStream doGetPreviewAsStream(
			FileVersion fileVersion, int index, String type)
		throws Exception {

		return DLStoreUtil.getFileAsStream(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getPreviewFilePath(fileVersion, index, type));
	}

	protected InputStream doGetPreviewAsStream(
			FileVersion fileVersion, String type)
		throws Exception {

		return doGetPreviewAsStream(fileVersion, 0, type);
	}

	protected int doGetPreviewFileCount(FileVersion fileVersion)
		throws Exception {

		try {
			String[] fileNames = DLStoreUtil.getFileNames(
				fileVersion.getCompanyId(), REPOSITORY_ID,
				getPathSegment(fileVersion, true));

			return fileNames.length;
		}
		catch (Exception e) {
		}

		return 0;
	}

	protected long doGetPreviewFileSize(
			FileVersion fileVersion, int index, String type)
		throws Exception {

		return DLStoreUtil.getFileSize(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getPreviewFilePath(fileVersion, index, type));
	}

	protected long doGetPreviewFileSize(FileVersion fileVersion, String type)
		throws Exception {

		return doGetPreviewFileSize(fileVersion, 0, type);
	}

	protected File doGetThumbnail(FileVersion fileVersion, int index) {
		try {
			String imageType = getThumbnailType(fileVersion);

			return DLStoreUtil.getFile(
				fileVersion.getCompanyId(), REPOSITORY_ID,
				getThumbnailFilePath(fileVersion, imageType, index));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	protected InputStream doGetThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		String type = getThumbnailType(fileVersion);

		return DLStoreUtil.getFileAsStream(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getThumbnailFilePath(fileVersion, type, index));
	}

	protected long doGetThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		String type = getThumbnailType(fileVersion);

		return DLStoreUtil.getFileSize(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getThumbnailFilePath(fileVersion, type, index));
	}

	protected void exportBinary(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileVersion fileVersion, File file, String binPath,
			String binPathName)
		throws SystemException {

		try {
			InputStream is = new FileInputStream(file);

			exportBinary(
				portletDataContext, fileEntryElement, fileVersion, is, binPath,
				binPathName);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void exportBinary(
			PortletDataContext portletDataContext, Element fileEntryElement,
			FileVersion fileVersion, InputStream is, String binPath,
			String binPathName)
		throws SystemException {

		fileEntryElement.addAttribute(binPathName, binPath);

		if (is == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No input stream found for file entry " +
						fileVersion.getFileEntryId());
			}

			fileEntryElement.detach();

			return;
		}

		portletDataContext.addZipEntry(binPath, is);
	}

	protected void exportThumbnail(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement, String binPathName, int index)
		throws PortalException, SystemException {

		FileVersion fileVersion = fileEntry.getFileVersion();

		File file = doGetThumbnail(fileVersion, index);

		String binPath = getBinPath(portletDataContext, fileEntry, index);

		fileEntryElement.addAttribute(binPathName, binPath);

		exportBinary(
			portletDataContext, fileEntryElement, fileVersion, file, binPath,
			binPathName);
	}

	protected void exportThumbnails(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			Element fileEntryElement, String binPathSuffix)
		throws PortalException, SystemException {

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (!isSupported(fileVersion) || !hasThumbnails(fileVersion)) {
			return;
		}

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			exportThumbnail(
				portletDataContext, fileEntry, fileEntryElement,
				"bin-path-thumbnail-default-" + binPathSuffix,
				THUMBNAIL_INDEX_DEFAULT);

			exportThumbnail(
				portletDataContext, fileEntry, fileEntryElement,
				"bin-path-thumbnail-custom-1-" + binPathSuffix,
				THUMBNAIL_INDEX_CUSTOM_1);

			exportThumbnail(
				portletDataContext, fileEntry, fileEntryElement,
				"bin-path-thumbnail-custom-2-" + binPathSuffix,
				THUMBNAIL_INDEX_CUSTOM_2);
		}
	}

	protected String getBinPath(
		PortletDataContext portletDataContext, FileEntry fileEntry, int index) {

		StringBundler sb = new StringBundler(8);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/bin/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(THUMBNAIL_PATH);
		sb.append(fileEntry.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(index);

		return sb.toString();
	}

	protected String getBinPath(
		PortletDataContext portletDataContext, FileEntry fileEntry,
		String type) {

		StringBundler sb = new StringBundler(8);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/bin/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(PREVIEW_PATH);
		sb.append(fileEntry.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(type);

		return sb.toString();
	}

	protected String getPreviewFilePath(
		FileVersion fileVersion, int index, String type) {

		StringBundler sb = null;

		if (index > 0) {
			sb = new StringBundler(5);
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(getPathSegment(fileVersion, true));

		if (index > 0) {
			sb.append(StringPool.SLASH);
			sb.append(index - 1);
		}

		sb.append(StringPool.PERIOD);
		sb.append(type);

		return sb.toString();
	}

	protected String getPreviewFilePath(FileVersion fileVersion, String type) {
		return getPreviewFilePath(fileVersion, 0, type);
	}

	protected File getPreviewTempFile(String id, int index, String type) {
		String previewTempFilePath = getPreviewTempFilePath(id, index, type);

		return new File(previewTempFilePath);
	}

	protected File getPreviewTempFile(String id, String type) {
		return getPreviewTempFile(id, 0, type);
	}

	protected int getPreviewTempFileCount(
		FileVersion fileVersion, String type) {

		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		StringBundler sb = new StringBundler(5);

		sb.append(tempFileId);
		sb.append(StringPool.DASH);
		sb.append("(.*)");
		sb.append(StringPool.PERIOD);
		sb.append(type);

		File dir = new File(PREVIEW_TMP_PATH);

		File[] files = dir.listFiles(new FileFilter(sb.toString()));

		if (_log.isDebugEnabled()) {
			for (File file : files) {
				_log.debug("Preview page for " + tempFileId + " " + file);
			}
		}

		return files.length;
	}

	protected String getPreviewTempFilePath(String id, int index, String type) {
		StringBundler sb = null;

		if (index > 0) {
			sb = new StringBundler(6);
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(PREVIEW_TMP_PATH);
		sb.append(id);

		if (index > 0) {
			sb.append(StringPool.DASH);
			sb.append(index - 1);
		}

		sb.append(StringPool.PERIOD);
		sb.append(type);

		return sb.toString();
	}

	protected String getPreviewTempFilePath(String id, String type) {
		return getPreviewTempFilePath(id, 0, type);
	}

	protected abstract String getPreviewType(FileVersion fileVersion);

	protected String getThumbnailFilePath(
		FileVersion fileVersion, String type, int index) {

		StringBundler sb = new StringBundler(5);

		sb.append(getPathSegment(fileVersion, false));

		if (index != THUMBNAIL_INDEX_DEFAULT) {
			sb.append(StringPool.DASH);
			sb.append(index);
		}

		sb.append(StringPool.PERIOD);
		sb.append(type);

		return sb.toString();
	}

	protected File getThumbnailTempFile(String id, String type) {
		String thumbnailTempFilePath = getThumbnailTempFilePath(id, type);

		return new File(thumbnailTempFilePath);
	}

	protected String getThumbnailTempFilePath(String id, String type) {
		StringBundler sb = new StringBundler(4);

		sb.append(THUMBNAIL_TMP_PATH);
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(type);

		return sb.toString();
	}

	protected abstract String getThumbnailType(FileVersion fileVersion);

	protected boolean hasThumbnail(FileVersion fileVersion, int index) {
		try {
			String imageType = getThumbnailType(fileVersion);

			return DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID,
				getThumbnailFilePath(fileVersion, imageType, index));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	protected boolean hasThumbnails(FileVersion fileVersion) {
		try {
			if (isThumbnailEnabled(THUMBNAIL_INDEX_DEFAULT)) {
				if (!hasThumbnail(fileVersion, THUMBNAIL_INDEX_DEFAULT)) {
					return false;
				}
			}

			if (isThumbnailEnabled(THUMBNAIL_INDEX_CUSTOM_1)) {
				if (!hasThumbnail(fileVersion, THUMBNAIL_INDEX_CUSTOM_1)) {
					return false;
				}
			}

			if (isThumbnailEnabled(THUMBNAIL_INDEX_CUSTOM_2)) {
				if (!hasThumbnail(fileVersion, THUMBNAIL_INDEX_CUSTOM_2)) {
					return false;
				}
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return true;
	}

	protected void importThumbnail(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement,
			String binPathName, int index)
		throws Exception {

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			directBinaryImportThumbnail(
				portletDataContext, importedFileEntry, fileEntryElement,
				binPathName, index);
		}
		else {
			FileVersion fileVersion = fileEntry.getFileVersion();

			File file = doGetThumbnail(fileVersion, index);

			try {
				InputStream is = new FileInputStream(file);

				FileVersion importedFileVersion =
					importedFileEntry.getFileVersion();

				String thumbnailFilePath = getThumbnailFilePath(
					importedFileVersion, getThumbnailType(importedFileVersion),
					index);

				addFileToStore(
					portletDataContext.getCompanyId(), THUMBNAIL_PATH,
					thumbnailFilePath, is);
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}
	}

	protected void importThumbnails(
			PortletDataContext portletDataContext, FileEntry fileEntry,
			FileEntry importedFileEntry, Element fileEntryElement,
			String binPathSuffix)
		throws Exception {

		importThumbnail(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement,
			"bin-path-thumbnail-default-" + binPathSuffix,
			THUMBNAIL_INDEX_DEFAULT);

		importThumbnail(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement,
			"bin-path-thumbnail-custom-1-" + binPathSuffix,
			THUMBNAIL_INDEX_CUSTOM_1);

		importThumbnail(
			portletDataContext, fileEntry, importedFileEntry, fileEntryElement,
			"bin-path-thumbnail-custom-2-" + binPathSuffix,
			THUMBNAIL_INDEX_CUSTOM_2);
	}

	protected boolean isThumbnailEnabled(int index) throws Exception {
		if (index == THUMBNAIL_INDEX_DEFAULT) {
			if (GetterUtil.getBoolean(
					PropsUtil.get(
						PropsKeys.DL_FILE_ENTRY_THUMBNAIL_ENABLED))) {

				return true;
			}
		}
		else if (index == THUMBNAIL_INDEX_CUSTOM_1) {
			if ((PrefsPropsUtil.getInteger(
					PropsKeys.
						DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT) > 0) ||
				(PrefsPropsUtil.getInteger(
					PropsKeys.
						DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH) > 0)) {

				return true;
			}
		}
		else if (index == THUMBNAIL_INDEX_CUSTOM_2) {
			if ((PrefsPropsUtil.getInteger(
					PropsKeys.
						DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT) > 0) ||
				(PrefsPropsUtil.getInteger(
					PropsKeys.
						DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH) > 0)) {

				return true;
			}
		}

		return false;
	}

	protected void storeThumbnailImages(FileVersion fileVersion, File file)
		throws Exception {

		ImageBag imageBag = ImageToolUtil.read(file);

		RenderedImage renderedImage = imageBag.getRenderedImage();

		storeThumbnailImages(fileVersion, renderedImage);
	}

	protected void storeThumbnailImages(
			FileVersion fileVersion, RenderedImage renderedImage)
		throws Exception {

		storeThumbnailmage(fileVersion, renderedImage, THUMBNAIL_INDEX_DEFAULT);
		storeThumbnailmage(
			fileVersion, renderedImage, THUMBNAIL_INDEX_CUSTOM_1);
		storeThumbnailmage(
			fileVersion, renderedImage, THUMBNAIL_INDEX_CUSTOM_2);
	}

	protected void storeThumbnailmage(
			FileVersion fileVersion, RenderedImage renderedImage, int index)
		throws Exception {

		if (!isThumbnailEnabled(index) || hasThumbnail(fileVersion, index)) {
			return;
		}

		String type = getThumbnailType(fileVersion);

		String maxHeightPropsKey = PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT;
		String maxWidthPropsKey = PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH;

		if (index == THUMBNAIL_INDEX_CUSTOM_1) {
			maxHeightPropsKey =
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT;
			maxWidthPropsKey =
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH;
		}
		else if (index == THUMBNAIL_INDEX_CUSTOM_2) {
			maxHeightPropsKey =
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT;
			maxWidthPropsKey =
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH;
		}

		RenderedImage thumbnailRenderedImage = ImageToolUtil.scale(
			renderedImage, PrefsPropsUtil.getInteger(maxHeightPropsKey),
			PrefsPropsUtil.getInteger(maxWidthPropsKey));

		byte[] bytes = ImageToolUtil.getBytes(
			thumbnailRenderedImage, MimeTypesUtil.getContentType("A." + type));

		File file = FileUtil.createTempFile(bytes);

		try {
			addFileToStore(
				fileVersion.getCompanyId(), THUMBNAIL_PATH,
				getThumbnailFilePath(fileVersion, type, index), file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLPreviewableProcessor.class);

}