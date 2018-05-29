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

package com.liferay.oauth.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth.model.OAuthUser;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuthUser in entity cache.
 *
 * @author Ivica Cardic
 * @see OAuthUser
 * @generated
 */
@ProviderType
public class OAuthUserCacheModel implements CacheModel<OAuthUser>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuthUserCacheModel)) {
			return false;
		}

		OAuthUserCacheModel oAuthUserCacheModel = (OAuthUserCacheModel)obj;

		if (oAuthUserId == oAuthUserCacheModel.oAuthUserId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuthUserId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{oAuthUserId=");
		sb.append(oAuthUserId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", oAuthApplicationId=");
		sb.append(oAuthApplicationId);
		sb.append(", accessToken=");
		sb.append(accessToken);
		sb.append(", accessSecret=");
		sb.append(accessSecret);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuthUser toEntityModel() {
		OAuthUserImpl oAuthUserImpl = new OAuthUserImpl();

		oAuthUserImpl.setOAuthUserId(oAuthUserId);
		oAuthUserImpl.setCompanyId(companyId);
		oAuthUserImpl.setUserId(userId);

		if (userName == null) {
			oAuthUserImpl.setUserName("");
		}
		else {
			oAuthUserImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			oAuthUserImpl.setCreateDate(null);
		}
		else {
			oAuthUserImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			oAuthUserImpl.setModifiedDate(null);
		}
		else {
			oAuthUserImpl.setModifiedDate(new Date(modifiedDate));
		}

		oAuthUserImpl.setOAuthApplicationId(oAuthApplicationId);

		if (accessToken == null) {
			oAuthUserImpl.setAccessToken("");
		}
		else {
			oAuthUserImpl.setAccessToken(accessToken);
		}

		if (accessSecret == null) {
			oAuthUserImpl.setAccessSecret("");
		}
		else {
			oAuthUserImpl.setAccessSecret(accessSecret);
		}

		oAuthUserImpl.resetOriginalValues();

		return oAuthUserImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuthUserId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		oAuthApplicationId = objectInput.readLong();
		accessToken = objectInput.readUTF();
		accessSecret = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(oAuthUserId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(oAuthApplicationId);

		if (accessToken == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(accessToken);
		}

		if (accessSecret == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(accessSecret);
		}
	}

	public long oAuthUserId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long oAuthApplicationId;
	public String accessToken;
	public String accessSecret;
}