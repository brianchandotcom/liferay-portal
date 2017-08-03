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

package com.liferay.sharepoint.repository.model;

import com.liferay.document.library.repository.external.ExtRepositoryFileVersion;
import com.liferay.sharepoint.connector.SharepointVersion;

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