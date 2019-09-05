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

import com.liferay.oauth2.provider.model.OAuth2Authorization;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuth2Authorization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2AuthorizationCacheModel
	implements CacheModel<OAuth2Authorization>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2AuthorizationCacheModel)) {
			return false;
		}

		OAuth2AuthorizationCacheModel oAuth2AuthorizationCacheModel =
			(OAuth2AuthorizationCacheModel)obj;

		if (oAuth2AuthorizationId ==
				oAuth2AuthorizationCacheModel.oAuth2AuthorizationId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuth2AuthorizationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{oAuth2AuthorizationId=");
		sb.append(oAuth2AuthorizationId);
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
		sb.append(", oAuth2ApplicationScopeAliasesId=");
		sb.append(oAuth2ApplicationScopeAliasesId);
		sb.append(", accessTokenContent=");
		sb.append(accessTokenContent);
		sb.append(", accessTokenContentHash=");
		sb.append(accessTokenContentHash);
		sb.append(", accessTokenCreateDate=");
		sb.append(accessTokenCreateDate);
		sb.append(", accessTokenExpirationDate=");
		sb.append(accessTokenExpirationDate);
		sb.append(", remoteHostInfo=");
		sb.append(remoteHostInfo);
		sb.append(", remoteIPInfo=");
		sb.append(remoteIPInfo);
		sb.append(", refreshTokenContent=");
		sb.append(refreshTokenContent);
		sb.append(", refreshTokenContentHash=");
		sb.append(refreshTokenContentHash);
		sb.append(", refreshTokenCreateDate=");
		sb.append(refreshTokenCreateDate);
		sb.append(", refreshTokenExpirationDate=");
		sb.append(refreshTokenExpirationDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2Authorization toEntityModel() {
		OAuth2AuthorizationImpl oAuth2AuthorizationImpl =
			new OAuth2AuthorizationImpl();

		oAuth2AuthorizationImpl.setOAuth2AuthorizationId(oAuth2AuthorizationId);
		oAuth2AuthorizationImpl.setCompanyId(companyId);
		oAuth2AuthorizationImpl.setUserId(userId);

		if (userName == null) {
			oAuth2AuthorizationImpl.setUserName("");
		}
		else {
			oAuth2AuthorizationImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			oAuth2AuthorizationImpl.setCreateDate(null);
		}
		else {
			oAuth2AuthorizationImpl.setCreateDate(new Date(createDate));
		}

		oAuth2AuthorizationImpl.setOAuth2ApplicationId(oAuth2ApplicationId);
		oAuth2AuthorizationImpl.setOAuth2ApplicationScopeAliasesId(
			oAuth2ApplicationScopeAliasesId);

		if (accessTokenContent == null) {
			oAuth2AuthorizationImpl.setAccessTokenContent("");
		}
		else {
			oAuth2AuthorizationImpl.setAccessTokenContent(accessTokenContent);
		}

		oAuth2AuthorizationImpl.setAccessTokenContentHash(
			accessTokenContentHash);

		if (accessTokenCreateDate == Long.MIN_VALUE) {
			oAuth2AuthorizationImpl.setAccessTokenCreateDate(null);
		}
		else {
			oAuth2AuthorizationImpl.setAccessTokenCreateDate(
				new Date(accessTokenCreateDate));
		}

		if (accessTokenExpirationDate == Long.MIN_VALUE) {
			oAuth2AuthorizationImpl.setAccessTokenExpirationDate(null);
		}
		else {
			oAuth2AuthorizationImpl.setAccessTokenExpirationDate(
				new Date(accessTokenExpirationDate));
		}

		if (remoteHostInfo == null) {
			oAuth2AuthorizationImpl.setRemoteHostInfo("");
		}
		else {
			oAuth2AuthorizationImpl.setRemoteHostInfo(remoteHostInfo);
		}

		if (remoteIPInfo == null) {
			oAuth2AuthorizationImpl.setRemoteIPInfo("");
		}
		else {
			oAuth2AuthorizationImpl.setRemoteIPInfo(remoteIPInfo);
		}

		if (refreshTokenContent == null) {
			oAuth2AuthorizationImpl.setRefreshTokenContent("");
		}
		else {
			oAuth2AuthorizationImpl.setRefreshTokenContent(refreshTokenContent);
		}

		oAuth2AuthorizationImpl.setRefreshTokenContentHash(
			refreshTokenContentHash);

		if (refreshTokenCreateDate == Long.MIN_VALUE) {
			oAuth2AuthorizationImpl.setRefreshTokenCreateDate(null);
		}
		else {
			oAuth2AuthorizationImpl.setRefreshTokenCreateDate(
				new Date(refreshTokenCreateDate));
		}

		if (refreshTokenExpirationDate == Long.MIN_VALUE) {
			oAuth2AuthorizationImpl.setRefreshTokenExpirationDate(null);
		}
		else {
			oAuth2AuthorizationImpl.setRefreshTokenExpirationDate(
				new Date(refreshTokenExpirationDate));
		}

		oAuth2AuthorizationImpl.resetOriginalValues();

		return oAuth2AuthorizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuth2AuthorizationId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		oAuth2ApplicationId = objectInput.readLong();

		oAuth2ApplicationScopeAliasesId = objectInput.readLong();
		accessTokenContent = objectInput.readUTF();

		accessTokenContentHash = objectInput.readLong();
		accessTokenCreateDate = objectInput.readLong();
		accessTokenExpirationDate = objectInput.readLong();
		remoteHostInfo = objectInput.readUTF();
		remoteIPInfo = objectInput.readUTF();
		refreshTokenContent = objectInput.readUTF();

		refreshTokenContentHash = objectInput.readLong();
		refreshTokenCreateDate = objectInput.readLong();
		refreshTokenExpirationDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(oAuth2AuthorizationId);

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

		objectOutput.writeLong(oAuth2ApplicationScopeAliasesId);

		if (accessTokenContent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(accessTokenContent);
		}

		objectOutput.writeLong(accessTokenContentHash);
		objectOutput.writeLong(accessTokenCreateDate);
		objectOutput.writeLong(accessTokenExpirationDate);

		if (remoteHostInfo == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(remoteHostInfo);
		}

		if (remoteIPInfo == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(remoteIPInfo);
		}

		if (refreshTokenContent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(refreshTokenContent);
		}

		objectOutput.writeLong(refreshTokenContentHash);
		objectOutput.writeLong(refreshTokenCreateDate);
		objectOutput.writeLong(refreshTokenExpirationDate);
	}

	public long oAuth2AuthorizationId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long oAuth2ApplicationId;
	public long oAuth2ApplicationScopeAliasesId;
	public String accessTokenContent;
	public long accessTokenContentHash;
	public long accessTokenCreateDate;
	public long accessTokenExpirationDate;
	public String remoteHostInfo;
	public String remoteIPInfo;
	public String refreshTokenContent;
	public long refreshTokenContentHash;
	public long refreshTokenCreateDate;
	public long refreshTokenExpirationDate;

}