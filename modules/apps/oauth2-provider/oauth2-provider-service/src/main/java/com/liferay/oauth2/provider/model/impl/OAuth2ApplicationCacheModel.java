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

import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuth2Application in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2ApplicationCacheModel
	implements CacheModel<OAuth2Application>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2ApplicationCacheModel)) {
			return false;
		}

		OAuth2ApplicationCacheModel oAuth2ApplicationCacheModel =
			(OAuth2ApplicationCacheModel)obj;

		if (oAuth2ApplicationId ==
				oAuth2ApplicationCacheModel.oAuth2ApplicationId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, oAuth2ApplicationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(41);

		sb.append("{oAuth2ApplicationId=");
		sb.append(oAuth2ApplicationId);
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
		sb.append(", oAuth2ApplicationScopeAliasesId=");
		sb.append(oAuth2ApplicationScopeAliasesId);
		sb.append(", allowedGrantTypes=");
		sb.append(allowedGrantTypes);
		sb.append(", clientCredentialUserId=");
		sb.append(clientCredentialUserId);
		sb.append(", clientCredentialUserName=");
		sb.append(clientCredentialUserName);
		sb.append(", clientId=");
		sb.append(clientId);
		sb.append(", clientProfile=");
		sb.append(clientProfile);
		sb.append(", clientSecret=");
		sb.append(clientSecret);
		sb.append(", description=");
		sb.append(description);
		sb.append(", features=");
		sb.append(features);
		sb.append(", homePageURL=");
		sb.append(homePageURL);
		sb.append(", iconFileEntryId=");
		sb.append(iconFileEntryId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", privacyPolicyURL=");
		sb.append(privacyPolicyURL);
		sb.append(", redirectURIs=");
		sb.append(redirectURIs);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2Application toEntityModel() {
		OAuth2ApplicationImpl oAuth2ApplicationImpl =
			new OAuth2ApplicationImpl();

		oAuth2ApplicationImpl.setOAuth2ApplicationId(oAuth2ApplicationId);
		oAuth2ApplicationImpl.setCompanyId(companyId);
		oAuth2ApplicationImpl.setUserId(userId);

		if (userName == null) {
			oAuth2ApplicationImpl.setUserName("");
		}
		else {
			oAuth2ApplicationImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			oAuth2ApplicationImpl.setCreateDate(null);
		}
		else {
			oAuth2ApplicationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			oAuth2ApplicationImpl.setModifiedDate(null);
		}
		else {
			oAuth2ApplicationImpl.setModifiedDate(new Date(modifiedDate));
		}

		oAuth2ApplicationImpl.setOAuth2ApplicationScopeAliasesId(
			oAuth2ApplicationScopeAliasesId);

		if (allowedGrantTypes == null) {
			oAuth2ApplicationImpl.setAllowedGrantTypes("");
		}
		else {
			oAuth2ApplicationImpl.setAllowedGrantTypes(allowedGrantTypes);
		}

		oAuth2ApplicationImpl.setClientCredentialUserId(clientCredentialUserId);

		if (clientCredentialUserName == null) {
			oAuth2ApplicationImpl.setClientCredentialUserName("");
		}
		else {
			oAuth2ApplicationImpl.setClientCredentialUserName(
				clientCredentialUserName);
		}

		if (clientId == null) {
			oAuth2ApplicationImpl.setClientId("");
		}
		else {
			oAuth2ApplicationImpl.setClientId(clientId);
		}

		oAuth2ApplicationImpl.setClientProfile(clientProfile);

		if (clientSecret == null) {
			oAuth2ApplicationImpl.setClientSecret("");
		}
		else {
			oAuth2ApplicationImpl.setClientSecret(clientSecret);
		}

		if (description == null) {
			oAuth2ApplicationImpl.setDescription("");
		}
		else {
			oAuth2ApplicationImpl.setDescription(description);
		}

		if (features == null) {
			oAuth2ApplicationImpl.setFeatures("");
		}
		else {
			oAuth2ApplicationImpl.setFeatures(features);
		}

		if (homePageURL == null) {
			oAuth2ApplicationImpl.setHomePageURL("");
		}
		else {
			oAuth2ApplicationImpl.setHomePageURL(homePageURL);
		}

		oAuth2ApplicationImpl.setIconFileEntryId(iconFileEntryId);

		if (name == null) {
			oAuth2ApplicationImpl.setName("");
		}
		else {
			oAuth2ApplicationImpl.setName(name);
		}

		if (privacyPolicyURL == null) {
			oAuth2ApplicationImpl.setPrivacyPolicyURL("");
		}
		else {
			oAuth2ApplicationImpl.setPrivacyPolicyURL(privacyPolicyURL);
		}

		if (redirectURIs == null) {
			oAuth2ApplicationImpl.setRedirectURIs("");
		}
		else {
			oAuth2ApplicationImpl.setRedirectURIs(redirectURIs);
		}

		oAuth2ApplicationImpl.resetOriginalValues();

		return oAuth2ApplicationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuth2ApplicationId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		oAuth2ApplicationScopeAliasesId = objectInput.readLong();
		allowedGrantTypes = objectInput.readUTF();

		clientCredentialUserId = objectInput.readLong();
		clientCredentialUserName = objectInput.readUTF();
		clientId = objectInput.readUTF();

		clientProfile = objectInput.readInt();
		clientSecret = objectInput.readUTF();
		description = objectInput.readUTF();
		features = objectInput.readUTF();
		homePageURL = objectInput.readUTF();

		iconFileEntryId = objectInput.readLong();
		name = objectInput.readUTF();
		privacyPolicyURL = objectInput.readUTF();
		redirectURIs = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(oAuth2ApplicationId);

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

		objectOutput.writeLong(oAuth2ApplicationScopeAliasesId);

		if (allowedGrantTypes == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(allowedGrantTypes);
		}

		objectOutput.writeLong(clientCredentialUserId);

		if (clientCredentialUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientCredentialUserName);
		}

		if (clientId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientId);
		}

		objectOutput.writeInt(clientProfile);

		if (clientSecret == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientSecret);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (features == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(features);
		}

		if (homePageURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(homePageURL);
		}

		objectOutput.writeLong(iconFileEntryId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (privacyPolicyURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(privacyPolicyURL);
		}

		if (redirectURIs == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(redirectURIs);
		}
	}

	public long oAuth2ApplicationId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long oAuth2ApplicationScopeAliasesId;
	public String allowedGrantTypes;
	public long clientCredentialUserId;
	public String clientCredentialUserName;
	public String clientId;
	public int clientProfile;
	public String clientSecret;
	public String description;
	public String features;
	public String homePageURL;
	public long iconFileEntryId;
	public String name;
	public String privacyPolicyURL;
	public String redirectURIs;

}