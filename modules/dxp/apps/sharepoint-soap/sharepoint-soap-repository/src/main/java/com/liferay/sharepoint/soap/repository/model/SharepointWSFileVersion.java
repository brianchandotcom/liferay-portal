/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.model;

import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.sharepoint.soap.repository.connector.SharepointVersion;

import java.util.Date;

/**
 * @author Iván Zaera
 */
public class SharepointWSFileVersion implements ExtRepositoryFileVersion {

	public SharepointWSFileVersion(SharepointVersion sharepointVersion) {
		_sharepointVersion = sharepointVersion;
	}

	@Override
	public String getChangeLog() {
		return _sharepointVersion.getComments();
	}

	@Override
	public Date getCreateDate() {
		return _sharepointVersion.getCreatedDate();
	}

	@Override
	public String getExtRepositoryModelKey() {
		return _sharepointVersion.getId();
	}

	@Override
	public String getMimeType() {
		return null;
	}

	@Override
	public String getOwner() {
		return _sharepointVersion.getCreatedBy();
	}

	public SharepointVersion getSharepointVersion() {
		return _sharepointVersion;
	}

	@Override
	public long getSize() {
		return _sharepointVersion.getSize();
	}

	@Override
	public String getVersion() {
		return _sharepointVersion.getVersion();
	}

	private final SharepointVersion _sharepointVersion;

}