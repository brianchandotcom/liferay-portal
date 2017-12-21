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

package com.liferay.sharepoint.oauth2.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import com.liferay.sharepoint.oauth2.model.SharepointOAuth2TokenEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SharepointOAuth2TokenEntry in entity cache.
 *
 * @author Adolfo Pérez
 * @see SharepointOAuth2TokenEntry
 * @generated
 */
@ProviderType
public class SharepointOAuth2TokenEntryCacheModel implements CacheModel<SharepointOAuth2TokenEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SharepointOAuth2TokenEntryCacheModel)) {
			return false;
		}

		SharepointOAuth2TokenEntryCacheModel sharepointOAuth2TokenEntryCacheModel =
			(SharepointOAuth2TokenEntryCacheModel)obj;

		if (sharepointOAuth2TokenEntryId == sharepointOAuth2TokenEntryCacheModel.sharepointOAuth2TokenEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, sharepointOAuth2TokenEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{sharepointOAuth2TokenEntryId=");
		sb.append(sharepointOAuth2TokenEntryId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", accessToken=");
		sb.append(accessToken);
		sb.append(", configurationPid=");
		sb.append(configurationPid);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", refreshToken=");
		sb.append(refreshToken);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SharepointOAuth2TokenEntry toEntityModel() {
		SharepointOAuth2TokenEntryImpl sharepointOAuth2TokenEntryImpl = new SharepointOAuth2TokenEntryImpl();

		sharepointOAuth2TokenEntryImpl.setSharepointOAuth2TokenEntryId(sharepointOAuth2TokenEntryId);
		sharepointOAuth2TokenEntryImpl.setUserId(userId);

		if (userName == null) {
			sharepointOAuth2TokenEntryImpl.setUserName("");
		}
		else {
			sharepointOAuth2TokenEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			sharepointOAuth2TokenEntryImpl.setCreateDate(null);
		}
		else {
			sharepointOAuth2TokenEntryImpl.setCreateDate(new Date(createDate));
		}

		if (accessToken == null) {
			sharepointOAuth2TokenEntryImpl.setAccessToken("");
		}
		else {
			sharepointOAuth2TokenEntryImpl.setAccessToken(accessToken);
		}

		if (configurationPid == null) {
			sharepointOAuth2TokenEntryImpl.setConfigurationPid("");
		}
		else {
			sharepointOAuth2TokenEntryImpl.setConfigurationPid(configurationPid);
		}

		if (expirationDate == Long.MIN_VALUE) {
			sharepointOAuth2TokenEntryImpl.setExpirationDate(null);
		}
		else {
			sharepointOAuth2TokenEntryImpl.setExpirationDate(new Date(
					expirationDate));
		}

		if (refreshToken == null) {
			sharepointOAuth2TokenEntryImpl.setRefreshToken("");
		}
		else {
			sharepointOAuth2TokenEntryImpl.setRefreshToken(refreshToken);
		}

		sharepointOAuth2TokenEntryImpl.resetOriginalValues();

		return sharepointOAuth2TokenEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		sharepointOAuth2TokenEntryId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		accessToken = objectInput.readUTF();
		configurationPid = objectInput.readUTF();
		expirationDate = objectInput.readLong();
		refreshToken = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(sharepointOAuth2TokenEntryId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);

		if (accessToken == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(accessToken);
		}

		if (configurationPid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(configurationPid);
		}

		objectOutput.writeLong(expirationDate);

		if (refreshToken == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(refreshToken);
		}
	}

	public long sharepointOAuth2TokenEntryId;
	public long userId;
	public String userName;
	public long createDate;
	public String accessToken;
	public String configurationPid;
	public long expirationDate;
	public String refreshToken;
}