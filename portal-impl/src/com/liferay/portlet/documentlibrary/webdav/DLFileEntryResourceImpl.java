/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.lock.Lock;
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
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public String getContentType() {
		FileEntry fileEntry = getModel();

		try {
			FileVersion fileVersion = fileEntry.getLatestFileVersion();

			return fileVersion.getMimeType();
		}
		catch (Exception e) {
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
		catch (Exception e) {
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
		catch (Exception e) {
		}

		return false;
	}

	private static String _getName(FileEntry fileEntry, boolean appendPath) {
		if (appendPath) {
			String name = fileEntry.getTitle();

			name = DLWebDAVUtil.escapeRawTitle(name);

			return name;
		}

		return StringPool.BLANK;
	}

}