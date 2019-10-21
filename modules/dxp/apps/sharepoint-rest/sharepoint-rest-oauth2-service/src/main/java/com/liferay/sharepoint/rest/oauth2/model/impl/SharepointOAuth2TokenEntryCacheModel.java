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

package com.liferay.sharepoint.rest.oauth2.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.sharepoint.rest.oauth2.model.SharepointOAuth2TokenEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SharepointOAuth2TokenEntry in entity cache.
 *
 * @author Adolfo Pérez
 * @generated
 */
public class SharepointOAuth2TokenEntryCacheModel
	implements CacheModel<SharepointOAuth2TokenEntry>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SharepointOAuth2TokenEntryCacheModel)) {
			return false;
		}

		SharepointOAuth2TokenEntryCacheModel
			sharepointOAuth2TokenEntryCacheModel =
				(SharepointOAuth2TokenEntryCacheModel)obj;

		if (sharepointOAuth2TokenEntryId ==
				sharepointOAuth2TokenEntryCacheModel.
					sharepointOAuth2TokenEntryId) {

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
		StringBundler sb = new StringBundler(19);

		sb.append("{sharepointOAuth2TokenEntryId=");
		sb.append(sharepointOAuth2TokenEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
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
		SharepointOAuth2TokenEntryImpl sharepointOAuth2TokenEntryImpl =
			new SharepointOAuth2TokenEntryImpl();

		sharepointOAuth2TokenEntryImpl.setSharepointOAuth2TokenEntryId(
			sharepointOAuth2TokenEntryId);
		sharepointOAuth2TokenEntryImpl.setCompanyId(companyId);
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
			sharepointOAuth2TokenEntryImpl.setConfigurationPid(
				configurationPid);
		}

		if (expirationDate == Long.MIN_VALUE) {
			sharepointOAuth2TokenEntryImpl.setExpirationDate(null);
		}
		else {
			sharepointOAuth2TokenEntryImpl.setExpirationDate(
				new Date(expirationDate));
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

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		accessToken = objectInput.readUTF();
		configurationPid = objectInput.readUTF();
		expirationDate = objectInput.readLong();
		refreshToken = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(sharepointOAuth2TokenEntryId);

		objectOutput.writeLong(companyId);

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
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public String accessToken;
	public String configurationPid;
	public long expirationDate;
	public String refreshToken;

}