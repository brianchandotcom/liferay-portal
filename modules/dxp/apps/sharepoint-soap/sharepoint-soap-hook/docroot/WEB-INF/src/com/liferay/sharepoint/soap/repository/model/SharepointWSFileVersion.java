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

package com.liferay.sharepoint.soap.repository.model;

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