/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryResourceImpl extends BaseResourceImpl {

	public DLFileEntryResourceImpl(
		WebDAVRequest webDAVRequest, FileEntry fileEntry, boolean appendPath) {

		super(
			webDAVRequest.getRootPath() + webDAVRequest.getPath(),
			_getName(fileEntry, appendPath), _getName(fileEntry, true),
			fileEntry.getCreateDate(), fileEntry.getModifiedDate(),
			fileEntry.getSize());

		setModel(fileEntry);
		setClassName(DLFileEntry.class.getName());
		setPrimaryKey(fileEntry.getPrimaryKey());
	}

	@Override
	public InputStream getContentAsStream() throws WebDAVException {
		FileEntry fileEntry = getModel();

		try {
			FileVersion fileVersion = fileEntry.getLatestFileVersion();

			return fileVersion.getContentStream(true);
		}
		catch (Exception exception) {
			throw new WebDAVException(exception);
		}
	}

	@Override
	public String getContentType() {
		FileEntry fileEntry = getModel();

		try {
			FileVersion fileVersion = fileEntry.getLatestFileVersion();

			return fileVersion.getMimeType();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return fileEntry.getMimeType();
		}
	}

	@Override
	public Lock getLock() {
		FileEntry fileEntry = getModel();

		return fileEntry.getLock();
	}

	@Override
	public FileEntry getModel() {
		return (FileEntry)super.getModel();
	}

	@Override
	public long getSize() {
		FileEntry fileEntry = getModel();

		try {
			FileVersion fileVersion = fileEntry.getLatestFileVersion();

			return fileVersion.getSize();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return fileEntry.getSize();
		}
	}

	@Override
	public boolean isCollection() {
		return false;
	}

	@Override
	public boolean isLocked() {
		FileEntry fileEntry = getModel();

		try {
			return fileEntry.hasLock();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return false;
	}

	private static String _getName(FileEntry fileEntry, boolean appendPath) {
		if (appendPath) {
			return DLWebDAVUtil.escapeRawTitle(fileEntry.getFileName());
		}

		return StringPool.BLANK;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryResourceImpl.class);

}