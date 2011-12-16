/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageProcessorUtil;
import com.liferay.portal.kernel.io.FileFilter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.awt.image.RenderedImage;

import java.io.File;
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

	public static int THUMBNAIL_INDEX_CUSTOM_1 = 1;

	public static int THUMBNAIL_INDEX_CUSTOM_2 = 2;

	public static int THUMBNAIL_INDEX_DEFAULT = 0;

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

	protected InputStream doGetThumbnailAsStream(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception {

		String type = getThumbnailType(fileVersion);

		return DLStoreUtil.getFileAsStream(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getThumbnailFilePath(fileVersion, type, thumbnailIndex));
	}

	protected long doGetThumbnailFileSize(
			FileVersion fileVersion, int thumbnailIndex)
		throws Exception {

		String type = getThumbnailType(fileVersion);

		return DLStoreUtil.getFileSize(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getThumbnailFilePath(fileVersion, type, thumbnailIndex));
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

	protected boolean hasThumbnail(
		FileVersion fileVersion, int thumbnailIndex) {

		try {
			String imageType = getThumbnailType(fileVersion);

			return DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), REPOSITORY_ID,
				getThumbnailFilePath(fileVersion, imageType, thumbnailIndex));
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return false;
	}

	protected boolean isCustomThumbnailsEnabled(int thumbnailIndex)
		throws Exception {

		if (thumbnailIndex == 1) {
			if ((PrefsPropsUtil.getInteger(
					PropsKeys.
						DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT) > 0) ||
				(PrefsPropsUtil.getInteger(
					PropsKeys.
						DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH) > 0)) {

				return true;
			}
		}
		else if (thumbnailIndex == 2) {
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

		ImageBag imageBag = ImageProcessorUtil.read(file);

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
			FileVersion fileVersion, RenderedImage renderedImage,
			int thumbnailIndex)
		throws Exception {

		if ((thumbnailIndex > 0) &&
			!isCustomThumbnailsEnabled(thumbnailIndex)) {

			return;
		}

		String type = getThumbnailType(fileVersion);

		String maxHeightPropsKey = PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT;

		if (thumbnailIndex == THUMBNAIL_INDEX_CUSTOM_1) {
			maxHeightPropsKey =
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_HEIGHT;
		}
		else if (thumbnailIndex == THUMBNAIL_INDEX_CUSTOM_2) {
			maxHeightPropsKey =
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_HEIGHT;
		}

		String maxWidthPropsKey = PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH;

		if (thumbnailIndex == THUMBNAIL_INDEX_CUSTOM_1) {
			maxWidthPropsKey =
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_1_MAX_WIDTH;
		}
		else if (thumbnailIndex == THUMBNAIL_INDEX_CUSTOM_2) {
			maxWidthPropsKey =
				PropsKeys.DL_FILE_ENTRY_THUMBNAIL_CUSTOM_2_MAX_WIDTH;
		}

		RenderedImage thumbnailRenderedImage = ImageProcessorUtil.scale(
			renderedImage, PrefsPropsUtil.getInteger(maxHeightPropsKey),
			PrefsPropsUtil.getInteger(maxWidthPropsKey));

		byte[] bytes = ImageProcessorUtil.getBytes(
			thumbnailRenderedImage, MimeTypesUtil.getContentType("A." + type));

		File file = FileUtil.createTempFile(bytes);

		try {
			addFileToStore(
				fileVersion.getCompanyId(), THUMBNAIL_PATH,
				getThumbnailFilePath(fileVersion, type, thumbnailIndex), file);
		}
		finally {
			FileUtil.delete(file);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLPreviewableProcessor.class);

}