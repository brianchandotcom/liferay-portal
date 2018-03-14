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

import com.liferay.oauth2.provider.model.OAuth2Application;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing OAuth2Application in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2Application
 * @generated
 */
@ProviderType
public class OAuth2ApplicationCacheModel implements CacheModel<OAuth2Application>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2ApplicationCacheModel)) {
			return false;
		}

		OAuth2ApplicationCacheModel oAuth2ApplicationCacheModel = (OAuth2ApplicationCacheModel)obj;

		if (oAuth2ApplicationId == oAuth2ApplicationCacheModel.oAuth2ApplicationId) {
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
		StringBundler sb = new StringBundler(37);

		sb.append("{oAuth2ApplicationId=");
		sb.append(oAuth2ApplicationId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", allowedGrantTypes=");
		sb.append(allowedGrantTypes);
		sb.append(", clientConfidential=");
		sb.append(clientConfidential);
		sb.append(", clientId=");
		sb.append(clientId);
		sb.append(", clientSecret=");
		sb.append(clientSecret);
		sb.append(", description=");
		sb.append(description);
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
		sb.append(", scopes=");
		sb.append(scopes);
		sb.append(", features=");
		sb.append(features);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2Application toEntityModel() {
		OAuth2ApplicationImpl oAuth2ApplicationImpl = new OAuth2ApplicationImpl();

		oAuth2ApplicationImpl.setOAuth2ApplicationId(oAuth2ApplicationId);
		oAuth2ApplicationImpl.setCompanyId(companyId);

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

		oAuth2ApplicationImpl.setUserId(userId);

		if (userName == null) {
			oAuth2ApplicationImpl.setUserName("");
		}
		else {
			oAuth2ApplicationImpl.setUserName(userName);
		}

		if (allowedGrantTypes == null) {
			oAuth2ApplicationImpl.setAllowedGrantTypes("");
		}
		else {
			oAuth2ApplicationImpl.setAllowedGrantTypes(allowedGrantTypes);
		}

		oAuth2ApplicationImpl.setClientConfidential(clientConfidential);

		if (clientId == null) {
			oAuth2ApplicationImpl.setClientId("");
		}
		else {
			oAuth2ApplicationImpl.setClientId(clientId);
		}

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

		if (scopes == null) {
			oAuth2ApplicationImpl.setScopes("");
		}
		else {
			oAuth2ApplicationImpl.setScopes(scopes);
		}

		if (features == null) {
			oAuth2ApplicationImpl.setFeatures("");
		}
		else {
			oAuth2ApplicationImpl.setFeatures(features);
		}

		oAuth2ApplicationImpl.resetOriginalValues();

		return oAuth2ApplicationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		oAuth2ApplicationId = objectInput.readLong();

		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		allowedGrantTypes = objectInput.readUTF();

		clientConfidential = objectInput.readBoolean();
		clientId = objectInput.readUTF();
		clientSecret = objectInput.readUTF();
		description = objectInput.readUTF();
		homePageURL = objectInput.readUTF();

		iconFileEntryId = objectInput.readLong();
		name = objectInput.readUTF();
		privacyPolicyURL = objectInput.readUTF();
		redirectURIs = objectInput.readUTF();
		scopes = objectInput.readUTF();
		features = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(oAuth2ApplicationId);

		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		if (allowedGrantTypes == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(allowedGrantTypes);
		}

		objectOutput.writeBoolean(clientConfidential);

		if (clientId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientId);
		}

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

		if (scopes == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(scopes);
		}

		if (features == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(features);
		}
	}

	public long oAuth2ApplicationId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public long userId;
	public String userName;
	public String allowedGrantTypes;
	public boolean clientConfidential;
	public String clientId;
	public String clientSecret;
	public String description;
	public String homePageURL;
	public long iconFileEntryId;
	public String name;
	public String privacyPolicyURL;
	public String redirectURIs;
	public String scopes;
	public String features;
}