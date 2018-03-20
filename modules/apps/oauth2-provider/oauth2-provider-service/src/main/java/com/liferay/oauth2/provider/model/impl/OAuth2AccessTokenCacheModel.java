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

		if (oAuth2AccessTokenId == oAuth2AccessTokenCacheModel.oAuth2AccessTokenId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuth2AccessTokenId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{oAuth2AccessTokenId=");
		sb.append(oAuth2AccessTokenId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", oAuth2ApplicationId=");
		sb.append(oAuth2ApplicationId);
		sb.append(", oAuth2RefreshTokenId=");
		sb.append(oAuth2RefreshTokenId);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", remoteIPInfo=");
		sb.append(remoteIPInfo);
		sb.append(", scopeAliases=");
		sb.append(scopeAliases);
		sb.append(", tokenContent=");
		sb.append(tokenContent);
		sb.append(", tokenType=");
		sb.append(tokenType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2AccessToken toEntityModel() {
		OAuth2AccessTokenImpl oAuth2AccessTokenImpl = new OAuth2AccessTokenImpl();

		oAuth2AccessTokenImpl.setOAuth2AccessTokenId(oAuth2AccessTokenId);
		oAuth2AccessTokenImpl.setCompanyId(companyId);
		oAuth2AccessTokenImpl.setUserId(userId);

		if (userName == null) {
			oAuth2AccessTokenImpl.setUserName("");
		}
		else {
			oAuth2AccessTokenImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			oAuth2AccessTokenImpl.setCreateDate(null);
		}
		else {
			oAuth2AccessTokenImpl.setCreateDate(new Date(createDate));
		}

		oAuth2AccessTokenImpl.setOAuth2ApplicationId(oAuth2ApplicationId);
		oAuth2AccessTokenImpl.setOAuth2RefreshTokenId(oAuth2RefreshTokenId);

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

		if (scopeAliases == null) {
			oAuth2AccessTokenImpl.setScopeAliases("");
		}
		else {
			oAuth2AccessTokenImpl.setScopeAliases(scopeAliases);
		}

		if (tokenContent == null) {
			oAuth2AccessTokenImpl.setTokenContent("");
		}
		else {
			oAuth2AccessTokenImpl.setTokenContent(tokenContent);
		}

		if (tokenType == null) {
			oAuth2AccessTokenImpl.setTokenType("");
		}
		else {
			oAuth2AccessTokenImpl.setTokenType(tokenType);
		}

		oAuth2AccessTokenImpl.resetOriginalValues();

		return oAuth2AccessTokenImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuth2AccessTokenId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		oAuth2ApplicationId = objectInput.readLong();

		oAuth2RefreshTokenId = objectInput.readLong();
		expirationDate = objectInput.readLong();
		remoteIPInfo = objectInput.readUTF();
		scopeAliases = objectInput.readUTF();
		tokenContent = objectInput.readUTF();
		tokenType = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(oAuth2AccessTokenId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);

		objectOutput.writeLong(oAuth2ApplicationId);

		objectOutput.writeLong(oAuth2RefreshTokenId);
		objectOutput.writeLong(expirationDate);

		if (remoteIPInfo == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(remoteIPInfo);
		}

		if (scopeAliases == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scopeAliases);
		}

		if (tokenContent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(tokenContent);
		}

		if (tokenType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(tokenType);
		}
	}

	public long oAuth2AccessTokenId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long oAuth2ApplicationId;
	public long oAuth2RefreshTokenId;
	public long expirationDate;
	public String remoteIPInfo;
	public String scopeAliases;
	public String tokenContent;
	public String tokenType;
}