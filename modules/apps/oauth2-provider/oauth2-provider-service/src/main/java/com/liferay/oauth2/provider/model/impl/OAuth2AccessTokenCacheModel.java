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

import com.liferay.oauth2.provider.model.OAuth2AccessToken;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuth2AccessToken in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2AccessToken
 * @generated
 */
@ProviderType
public class OAuth2AccessTokenCacheModel implements CacheModel<OAuth2AccessToken>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2AccessTokenCacheModel)) {
			return false;
		}

		OAuth2AccessTokenCacheModel oAuth2AccessTokenCacheModel = (OAuth2AccessTokenCacheModel)obj;

		if (OAuth2AccessTokenId == oAuth2AccessTokenCacheModel.OAuth2AccessTokenId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, OAuth2AccessTokenId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{OAuth2AccessTokenId=");
		sb.append(OAuth2AccessTokenId);
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
		sb.append(", oAuth2RefreshTokenId=");
		sb.append(oAuth2RefreshTokenId);
		sb.append(", OAuth2AccessTokenContent=");
		sb.append(OAuth2AccessTokenContent);
		sb.append(", OAuth2AccessTokenType=");
		sb.append(OAuth2AccessTokenType);
		sb.append(", scopeAliases=");
		sb.append(scopeAliases);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2AccessToken toEntityModel() {
		OAuth2AccessTokenImpl oAuth2AccessTokenImpl = new OAuth2AccessTokenImpl();

		oAuth2AccessTokenImpl.setOAuth2AccessTokenId(OAuth2AccessTokenId);
		oAuth2AccessTokenImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			oAuth2AccessTokenImpl.setCreateDate(null);
		}
		else {
			oAuth2AccessTokenImpl.setCreateDate(new Date(createDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			oAuth2AccessTokenImpl.setExpirationDate(null);
		}
		else {
			oAuth2AccessTokenImpl.setExpirationDate(new Date(expirationDate));
		}

		if (remoteIPInfo == null) {
			oAuth2AccessTokenImpl.setRemoteIPInfo("");
		}
		else {
			oAuth2AccessTokenImpl.setRemoteIPInfo(remoteIPInfo);
		}

		oAuth2AccessTokenImpl.setUserId(userId);

		if (userName == null) {
			oAuth2AccessTokenImpl.setUserName("");
		}
		else {
			oAuth2AccessTokenImpl.setUserName(userName);
		}

		oAuth2AccessTokenImpl.setOAuth2ApplicationId(oAuth2ApplicationId);
		oAuth2AccessTokenImpl.setOAuth2RefreshTokenId(oAuth2RefreshTokenId);

		if (OAuth2AccessTokenContent == null) {
			oAuth2AccessTokenImpl.setOAuth2AccessTokenContent("");
		}
		else {
			oAuth2AccessTokenImpl.setOAuth2AccessTokenContent(OAuth2AccessTokenContent);
		}

		if (OAuth2AccessTokenType == null) {
			oAuth2AccessTokenImpl.setOAuth2AccessTokenType("");
		}
		else {
			oAuth2AccessTokenImpl.setOAuth2AccessTokenType(OAuth2AccessTokenType);
		}

		if (scopeAliases == null) {
			oAuth2AccessTokenImpl.setScopeAliases("");
		}
		else {
			oAuth2AccessTokenImpl.setScopeAliases(scopeAliases);
		}

		oAuth2AccessTokenImpl.resetOriginalValues();

		return oAuth2AccessTokenImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		OAuth2AccessTokenId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		remoteIPInfo = objectInput.readUTF();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		oAuth2ApplicationId = objectInput.readLong();

		oAuth2RefreshTokenId = objectInput.readLong();
		OAuth2AccessTokenContent = objectInput.readUTF();
		OAuth2AccessTokenType = objectInput.readUTF();
		scopeAliases = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(OAuth2AccessTokenId);

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

		objectOutput.writeLong(oAuth2RefreshTokenId);

		if (OAuth2AccessTokenContent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(OAuth2AccessTokenContent);
		}

		if (OAuth2AccessTokenType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(OAuth2AccessTokenType);
		}

		if (scopeAliases == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scopeAliases);
		}
	}

	public long OAuth2AccessTokenId;
	public long companyId;
	public long createDate;
	public long expirationDate;
	public String remoteIPInfo;
	public long userId;
	public String userName;
	public long oAuth2ApplicationId;
	public long oAuth2RefreshTokenId;
	public String OAuth2AccessTokenContent;
	public String OAuth2AccessTokenType;
	public String scopeAliases;
}