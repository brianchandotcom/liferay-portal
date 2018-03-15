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

package com.liferay.oauth2.provider.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.oauth2.provider.model.OAuth2RefreshToken;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuth2RefreshToken in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2RefreshToken
 * @generated
 */
@ProviderType
public class OAuth2RefreshTokenCacheModel implements CacheModel<OAuth2RefreshToken>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2RefreshTokenCacheModel)) {
			return false;
		}

		OAuth2RefreshTokenCacheModel oAuth2RefreshTokenCacheModel = (OAuth2RefreshTokenCacheModel)obj;

		if (oAuth2RefreshTokenId == oAuth2RefreshTokenCacheModel.oAuth2RefreshTokenId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuth2RefreshTokenId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{oAuth2RefreshTokenId=");
		sb.append(oAuth2RefreshTokenId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", remoteIPInfo=");
		sb.append(remoteIPInfo);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", oAuth2ApplicationId=");
		sb.append(oAuth2ApplicationId);
		sb.append(", oAuth2RefreshTokenContent=");
		sb.append(oAuth2RefreshTokenContent);
		sb.append(", scopeAliases=");
		sb.append(scopeAliases);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2RefreshToken toEntityModel() {
		OAuth2RefreshTokenImpl oAuth2RefreshTokenImpl = new OAuth2RefreshTokenImpl();

		oAuth2RefreshTokenImpl.setOAuth2RefreshTokenId(oAuth2RefreshTokenId);
		oAuth2RefreshTokenImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			oAuth2RefreshTokenImpl.setCreateDate(null);
		}
		else {
			oAuth2RefreshTokenImpl.setCreateDate(new Date(createDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			oAuth2RefreshTokenImpl.setExpirationDate(null);
		}
		else {
			oAuth2RefreshTokenImpl.setExpirationDate(new Date(expirationDate));
		}

		if (remoteIPInfo == null) {
			oAuth2RefreshTokenImpl.setRemoteIPInfo("");
		}
		else {
			oAuth2RefreshTokenImpl.setRemoteIPInfo(remoteIPInfo);
		}

		oAuth2RefreshTokenImpl.setUserId(userId);

		if (userName == null) {
			oAuth2RefreshTokenImpl.setUserName("");
		}
		else {
			oAuth2RefreshTokenImpl.setUserName(userName);
		}

		oAuth2RefreshTokenImpl.setOAuth2ApplicationId(oAuth2ApplicationId);

		if (oAuth2RefreshTokenContent == null) {
			oAuth2RefreshTokenImpl.setOAuth2RefreshTokenContent("");
		}
		else {
			oAuth2RefreshTokenImpl.setOAuth2RefreshTokenContent(oAuth2RefreshTokenContent);
		}

		if (scopeAliases == null) {
			oAuth2RefreshTokenImpl.setScopeAliases("");
		}
		else {
			oAuth2RefreshTokenImpl.setScopeAliases(scopeAliases);
		}

		oAuth2RefreshTokenImpl.resetOriginalValues();

		return oAuth2RefreshTokenImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuth2RefreshTokenId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		remoteIPInfo = objectInput.readUTF();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		oAuth2ApplicationId = objectInput.readLong();
		oAuth2RefreshTokenContent = objectInput.readUTF();
		scopeAliases = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(oAuth2RefreshTokenId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(expirationDate);

		if (remoteIPInfo == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(remoteIPInfo);
		}

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(oAuth2ApplicationId);

		if (oAuth2RefreshTokenContent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(oAuth2RefreshTokenContent);
		}

		if (scopeAliases == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scopeAliases);
		}
	}

	public long oAuth2RefreshTokenId;
	public long companyId;
	public long createDate;
	public long expirationDate;
	public String remoteIPInfo;
	public long userId;
	public String userName;
	public long oAuth2ApplicationId;
	public String oAuth2RefreshTokenContent;
	public String scopeAliases;
}