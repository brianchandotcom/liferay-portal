/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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