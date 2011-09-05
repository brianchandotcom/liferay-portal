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

import com.liferay.portal.kernel.io.FileFilter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

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

	public static void deleteFiles(
		long companyId, long groupId, long fileEntryId, long fileVersionId) {

		try {
			DLStoreUtil.deleteDirectory(
				companyId, REPOSITORY_ID,
				_getPathSegment(groupId, fileEntryId, fileVersionId, true));
		}
		catch (Exception e) {
		}

		try {
			DLStoreUtil.deleteDirectory(
				companyId, REPOSITORY_ID,
				_getPathSegment(groupId, fileEntryId, fileVersionId, false));
		}
		catch (Exception e) {
		}
	}

	public static void deleteFiles(FileEntry fileEntry) {
		deleteFiles(
			fileEntry.getCompanyId(), fileEntry.getGroupId(),
			fileEntry.getFileEntryId(), -1);
	}

	public static void deleteFiles(FileVersion fileVersion) {
		deleteFiles(
			fileVersion.getCompanyId(), fileVersion.getGroupId(),
			fileVersion.getFileEntryId(), fileVersion.getFileVersionId());
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

	protected InputStream doGetPreviewAsStream(FileVersion fileVersion)
		throws Exception {

		return doGetPreviewAsStream(fileVersion, 0);
	}

	protected InputStream doGetPreviewAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		return DLStoreUtil.getFileAsStream(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getPreviewFilePath(fileVersion, index));
	}

	protected long doGetPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return doGetPreviewFileSize(fileVersion, 0);
	}

	protected long doGetPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return DLStoreUtil.getFileSize(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getPreviewFilePath(fileVersion, index));
	}

	protected InputStream doGetThumbnailAsStream(FileVersion fileVersion)
		throws Exception {

		return doGetThumbnailAsStream(fileVersion, 0);
	}

	protected InputStream doGetThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		return DLStoreUtil.getFileAsStream(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getThumbnailFilePath(fileVersion));
	}

	protected long doGetThumbnailFileSize(FileVersion fileVersion)
		throws Exception {

		return doGetThumbnailFileSize(fileVersion, 0);
	}

	protected long doGetThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		return DLStoreUtil.getFileSize(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getThumbnailFilePath(fileVersion));
	}

	protected int getPreviewFileCount(FileVersion fileVersion)
		throws Exception {

		try {
			String[] fileNames = DLStoreUtil.getFileNames(
				fileVersion.getCompanyId(), REPOSITORY_ID,
				_getPathSegment(fileVersion, true));

			return fileNames.length;
		}
		catch (Exception e) {
		}

		return 0;
	}

	protected String getPreviewFilePath(FileVersion fileVersion) {
		return getPreviewFilePath(fileVersion, 0);
	}

	protected String getPreviewFilePath(FileVersion fileVersion, int index) {
		StringBundler sb = null;

		if (index > 0) {
			sb = new StringBundler(5);
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_getPathSegment(fileVersion, true));

		if (index > 0) {
			sb.append(StringPool.SLASH);
			sb.append(index - 1);
		}

		sb.append(StringPool.PERIOD);
		sb.append(getPreviewType());

		return sb.toString();
	}

	protected File getPreviewTempFile(String tempFileId) {
		return getPreviewTempFile(tempFileId, 0);
	}

	protected File getPreviewTempFile(String tempFileId, int index) {
		String previewTempFilePath = getPreviewTempFilePath(tempFileId, index);

		return new File(previewTempFilePath);
	}

	protected int getPreviewTempFileCount(FileVersion fileVersion) {
		String tempFileId = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		StringBundler sb = new StringBundler(5);

		sb.append(tempFileId);
		sb.append(StringPool.DASH);
		sb.append("(.*)");
		sb.append(StringPool.PERIOD);
		sb.append(getPreviewType());

		File dir = new File(PREVIEW_TMP_PATH);

		File[] files = dir.listFiles(new FileFilter(sb.toString()));

		if (_log.isDebugEnabled()) {
			for (File file : files) {
				_log.debug("Preview page for " + tempFileId + " " + file);
			}
		}

		return files.length;
	}

	protected String getPreviewTempFilePath(String id) {
		return getPreviewTempFilePath(id, 0);
	}

	protected String getPreviewTempFilePath(String id, int index) {
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
		sb.append(getPreviewType());

		return sb.toString();
	}

	protected abstract String getPreviewType();

	protected String getThumbnailFilePath(FileVersion fileVersion) {
		return _getPathSegment(fileVersion, false).concat(
			StringPool.PERIOD).concat(getThumbnailType());
	}

	protected File getThumbnailTempFile(String id) {
		String thumbnailTempFilePath = getThumbnailTempFilePath(id);

		return new File(thumbnailTempFilePath);
	}

	protected String getThumbnailTempFilePath(String id) {
		StringBundler sb = new StringBundler(4);

		sb.append(THUMBNAIL_TMP_PATH);
		sb.append(id);
		sb.append(StringPool.PERIOD);
		sb.append(getThumbnailType());

		return sb.toString();
	}

	protected String getThumbnailType() {
		return null;
	}

	private static String _getPathSegment(
		FileVersion fileVersion, boolean preview) {

		return _getPathSegment(
			fileVersion.getGroupId(), fileVersion.getFileEntryId(),
			fileVersion.getFileVersionId(), preview);
	}

	private static String _getPathSegment(
		long groupId, long fileEntryId, long fileVersionId, boolean preview) {

		StringBundler sb = null;

		if (fileVersionId > 0) {
			sb = new StringBundler(6);
		}
		else {
			sb = new StringBundler(4);
		}

		if (preview) {
			sb.append(PREVIEW_PATH);
		}
		else {
			sb.append(THUMBNAIL_PATH);
		}

		sb.append(groupId);
		sb.append(StringPool.SLASH);
		sb.append(fileEntryId);

		if (fileVersionId > 0) {
			sb.append(StringPool.SLASH);
			sb.append(fileVersionId);
		}

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(
		DLPreviewableProcessor.class);

}