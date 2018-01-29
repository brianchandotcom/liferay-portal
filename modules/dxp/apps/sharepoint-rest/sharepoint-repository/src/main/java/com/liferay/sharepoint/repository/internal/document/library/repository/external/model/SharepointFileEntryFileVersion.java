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

package com.liferay.sharepoint.repository.internal.document.library.repository.external.model;

import com.liferay.document.library.repository.external.ExtRepositoryFileEntry;
import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;

import java.util.Date;

/**
 * @author Adolfo Pérez
 */
public class SharepointFileEntryFileVersion
	implements ExtRepositoryFileVersion, SharepointModel {

	public SharepointFileEntryFileVersion(
		SharepointFileEntry sharepointFileEntry, String extRepositoryModelKey,
		String version) {

		_sharepointFileEntry = sharepointFileEntry;
		_extRepositoryModelKey = extRepositoryModelKey;
		_version = version;
	}

	@Override
	public ExtRepositoryFileVersion asFileVersion() {
		return this;
	}

	@Override
	public String getCanonicalContentURL() {
		return _sharepointFileEntry.getCanonicalContentURL();
	}

	@Override
	public String getChangeLog() {
		return _sharepointFileEntry.getDescription();
	}

	@Override
	public Date getCreateDate() {
		return _sharepointFileEntry.getCreateDate();
	}

	@Override
	public ExtRepositoryFileEntry getExtRepositoryFileEntry() {
		return _sharepointFileEntry;
	}

	@Override
	public String getExtRepositoryModelKey() {
		return _extRepositoryModelKey;
	}

	@Override
	public String getMimeType() {
		return _sharepointFileEntry.getMimeType();
	}

	@Override
	public String getOwner() {
		return _sharepointFileEntry.getOwner();
	}

	@Override
	public long getSize() {
		return _sharepointFileEntry.getSize();
	}

	@Override
	public String getVersion() {
		return _version;
	}

	private final String _extRepositoryModelKey;
	private final SharepointFileEntry _sharepointFileEntry;
	private final String _version;

}