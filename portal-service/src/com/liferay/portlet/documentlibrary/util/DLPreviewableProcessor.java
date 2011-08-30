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
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;

import java.io.File;
import java.io.InputStream;

/**
 * @author Alexander Chow
 */
public abstract class DLPreviewableProcessor implements DLProcessor {

	protected void addFileToStore(
			long companyId, String dirName, String filePath, File srcFile)
		throws Exception {

		try{
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

		String filePath = getPreviewFilePath(fileVersion, index);

		long companyId = fileVersion.getCompanyId();
		long repositoryId = CompanyConstants.SYSTEM;

		return DLStoreUtil.getFileAsStream(companyId, repositoryId, filePath);
	}

	protected long doGetPreviewFileSize(FileVersion fileVersion)
		throws Exception {

		return doGetPreviewFileSize(fileVersion, 0);
	}

	protected long doGetPreviewFileSize(FileVersion fileVersion, int index)
		throws Exception {

		String filePath = getPreviewFilePath(fileVersion, index);

		long companyId = fileVersion.getCompanyId();
		long repositoryId = CompanyConstants.SYSTEM;

		return DLStoreUtil.getFileSize(companyId, repositoryId, filePath);
	}

	protected InputStream doGetThumbnailAsStream(FileVersion fileVersion)
		throws Exception {

		return doGetThumbnailAsStream(fileVersion, 0);
	}

	protected InputStream doGetThumbnailAsStream(
			FileVersion fileVersion, int index)
		throws Exception {

		String filePath = getThumbnailFilePath(fileVersion);

		long companyId = fileVersion.getCompanyId();
		long repositoryId = CompanyConstants.SYSTEM;

		return DLStoreUtil.getFileAsStream(companyId, repositoryId, filePath);
	}

	protected long doGetThumbnailFileSize(FileVersion fileVersion)
		throws Exception {

		return doGetThumbnailFileSize(fileVersion, 0);
	}

	protected long doGetThumbnailFileSize(FileVersion fileVersion, int index)
		throws Exception {

		String filePath = getThumbnailFilePath(fileVersion);

		long companyId = fileVersion.getCompanyId();
		long repositoryId = CompanyConstants.SYSTEM;

		return DLStoreUtil.getFileSize(companyId, repositoryId, filePath);
	}

	protected String getPathSegment(FileVersion fileVersion) {
		StringBundler sb = new StringBundler(5);

		sb.append(fileVersion.getGroupId());
		sb.append(StringPool.SLASH);
		sb.append(fileVersion.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileVersion.getFileVersionId());

		return sb.toString();
	}

	protected int getPreviewFileCount(FileVersion fileVersion)
		throws Exception {

		try {
			String[] files = DLStoreUtil.getFileNames(
				fileVersion.getCompanyId(), REPOSITORY_ID,
				PREVIEW_PATH + getPathSegment(fileVersion));

			return files.length;
		}
		catch (Exception e) {
		}

		return 0;
	}

	protected String getPreviewFilePath(FileVersion fileVersion) {
		return getPreviewFilePath(fileVersion, 0);
	}

	protected String getPreviewFilePath(FileVersion fileVersion, int index) {
		StringBundler sb;

		if (index <= 0) {
			sb = new StringBundler(4);
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(PREVIEW_PATH);
		sb.append(getPathSegment(fileVersion));

		if (index > 0) {
			sb.append(StringPool.SLASH);
			sb.append(index - 1);
		}

		sb.append(StringPool.PERIOD);
		sb.append(getPreviewType());

		return sb.toString();
	}

	protected File getPreviewTempFile(String id) {
		return getPreviewTempFile(id, 0);
	}

	protected File getPreviewTempFile(String id, int index) {
		String filePath = getPreviewTempFilePath(id, index);

		return new File(filePath);
	}

	protected int getPreviewTempFileCount(FileVersion fileVersion) {
		String id = DLUtil.getTempFileId(
			fileVersion.getFileEntryId(), fileVersion.getVersion());

		StringBundler sb = new StringBundler(5);

		sb.append(id);
		sb.append(StringPool.DASH);
		sb.append("(.*)");
		sb.append(StringPool.PERIOD);
		sb.append(getPreviewType());

		File dir = new File(PREVIEW_TMP_PATH);

		File[] files = dir.listFiles(new FileFilter(sb.toString()));

		if (_log.isDebugEnabled()) {
			for (File file : files) {
				_log.debug("Preview page for " + id + " " + file);
			}
		}

		return files.length;
	}

	protected String getPreviewTempFilePath(String id) {
		return getPreviewTempFilePath(id, 0);
	}

	protected String getPreviewTempFilePath(String id, int index) {
		StringBundler sb;

		if (index <= 0) {
			sb = new StringBundler(4);
		}
		else {
			sb = new StringBundler(6);
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
		StringBundler sb = new StringBundler(4);

		sb.append(THUMBNAIL_PATH);
		sb.append(getPathSegment(fileVersion));
		sb.append(StringPool.PERIOD);
		sb.append(getThumbnailType());

		return sb.toString();
	}

	protected File getThumbnailTempFile(String id) {
		String filePath = getThumbnailTempFilePath(id);

		return new File(filePath);
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

	protected static final long REPOSITORY_ID = CompanyConstants.SYSTEM;

	protected static final String PREVIEW_PATH =
		"document_preview/";

	protected static final String PREVIEW_TMP_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/" + PREVIEW_PATH;

	protected static final String THUMBNAIL_PATH =
		"document_thumbnail/";

	protected static final String THUMBNAIL_TMP_PATH =
		SystemProperties.get(SystemProperties.TMP_DIR) +
			"/liferay/" + THUMBNAIL_PATH;

	private static Log _log = LogFactoryUtil.getLog(
		DLPreviewableProcessor.class);

}