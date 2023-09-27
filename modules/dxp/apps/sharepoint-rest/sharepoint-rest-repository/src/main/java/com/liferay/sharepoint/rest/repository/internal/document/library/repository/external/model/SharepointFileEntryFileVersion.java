/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external.model;

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