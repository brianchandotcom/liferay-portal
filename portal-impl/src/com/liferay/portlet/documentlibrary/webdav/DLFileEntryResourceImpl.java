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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.webdav.BaseResourceImpl;
import com.liferay.portal.kernel.webdav.WebDAVException;
import com.liferay.portal.kernel.webdav.WebDAVRequest;
import com.liferay.portal.model.Lock;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.io.InputStream;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileEntryResourceImpl extends BaseResourceImpl {

	public DLFileEntryResourceImpl(
		WebDAVRequest webDAVRequest, FileEntry fileEntry, String parentPath,
		String name) {

		super(
			parentPath, name, fileEntry.getTitle(), fileEntry.getCreateDate(),
			fileEntry.getModifiedDate(), fileEntry.getSize());

		setModel(fileEntry);
		setClassName(DLFileEntry.class.getName());
		setPrimaryKey(fileEntry.getPrimaryKey());

		//_webDAVRequest = webDAVRequest;
		_fileEntry = fileEntry;
	}

	@Override
	public InputStream getContentAsStream() throws WebDAVException {
		try {
			FileVersion fileVersion = _fileEntry.getLatestFileVersion();

			return fileVersion.getContentStream(true);
		}
		catch (Exception e) {
			throw new WebDAVException(e);
		}
	}

	@Override
	public String getContentType() {
		try {
			FileVersion fileVersion = _fileEntry.getLatestFileVersion();

			return fileVersion.getMimeType();
		}
		catch (Exception e) {
			return _fileEntry.getMimeType();
		}
	}

	@Override
	public Lock getLock() {
		try {
			return _fileEntry.getLock();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public long getSize() {
		try {
			FileVersion fileVersion = _fileEntry.getLatestFileVersion();

			return fileVersion.getSize();
		}
		catch (Exception e) {
			return _fileEntry.getSize();
		}
	}

	@Override
	public boolean isCollection() {
		return false;
	}

	@Override
	public boolean isLocked() {
		try {
			return _fileEntry.hasLock();
		}
		catch (Exception e) {
		}

		return false;
	}

	private FileEntry _fileEntry;

}