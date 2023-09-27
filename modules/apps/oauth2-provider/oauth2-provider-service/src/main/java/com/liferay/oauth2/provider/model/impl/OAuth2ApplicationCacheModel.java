/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof OAuth2ApplicationCacheModel)) {
			return false;
		}

		OAuth2ApplicationCacheModel oAuth2ApplicationCacheModel =
			(OAuth2ApplicationCacheModel)object;

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
		StringBundler sb = new StringBundler(53);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", oAuth2ApplicationId=");
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
		sb.append(", clientAuthenticationMethod=");
		sb.append(clientAuthenticationMethod);
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
		sb.append(", jwks=");
		sb.append(jwks);
		sb.append(", name=");
		sb.append(name);
		sb.append(", privacyPolicyURL=");
		sb.append(privacyPolicyURL);
		sb.append(", redirectURIs=");
		sb.append(redirectURIs);
		sb.append(", rememberDevice=");
		sb.append(rememberDevice);
		sb.append(", trustedApplication=");
		sb.append(trustedApplication);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public OAuth2Application toEntityModel() {
		OAuth2ApplicationImpl oAuth2ApplicationImpl =
			new OAuth2ApplicationImpl();

		if (uuid == null) {
			oAuth2ApplicationImpl.setUuid("");
		}
		else {
			oAuth2ApplicationImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			oAuth2ApplicationImpl.setExternalReferenceCode("");
		}
		else {
			oAuth2ApplicationImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

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

		if (clientAuthenticationMethod == null) {
			oAuth2ApplicationImpl.setClientAuthenticationMethod("");
		}
		else {
			oAuth2ApplicationImpl.setClientAuthenticationMethod(
				clientAuthenticationMethod);
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

		if (jwks == null) {
			oAuth2ApplicationImpl.setJwks("");
		}
		else {
			oAuth2ApplicationImpl.setJwks(jwks);
		}

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

		oAuth2ApplicationImpl.setRememberDevice(rememberDevice);
		oAuth2ApplicationImpl.setTrustedApplication(trustedApplication);

		oAuth2ApplicationImpl.resetOriginalValues();

		return oAuth2ApplicationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		oAuth2ApplicationId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		oAuth2ApplicationScopeAliasesId = objectInput.readLong();
		allowedGrantTypes = objectInput.readUTF();
		clientAuthenticationMethod = objectInput.readUTF();

		clientCredentialUserId = objectInput.readLong();
		clientCredentialUserName = objectInput.readUTF();
		clientId = objectInput.readUTF();

		clientProfile = objectInput.readInt();
		clientSecret = objectInput.readUTF();
		description = objectInput.readUTF();
		features = objectInput.readUTF();
		homePageURL = objectInput.readUTF();

		iconFileEntryId = objectInput.readLong();
		jwks = objectInput.readUTF();
		name = objectInput.readUTF();
		privacyPolicyURL = objectInput.readUTF();
		redirectURIs = objectInput.readUTF();

		rememberDevice = objectInput.readBoolean();

		trustedApplication = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

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

		if (clientAuthenticationMethod == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(clientAuthenticationMethod);
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

		if (jwks == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(jwks);
		}

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

		objectOutput.writeBoolean(rememberDevice);

		objectOutput.writeBoolean(trustedApplication);
	}

	public String uuid;
	public String externalReferenceCode;
	public long oAuth2ApplicationId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long oAuth2ApplicationScopeAliasesId;
	public String allowedGrantTypes;
	public String clientAuthenticationMethod;
	public long clientCredentialUserId;
	public String clientCredentialUserName;
	public String clientId;
	public int clientProfile;
	public String clientSecret;
	public String description;
	public String features;
	public String homePageURL;
	public long iconFileEntryId;
	public String jwks;
	public String name;
	public String privacyPolicyURL;
	public String redirectURIs;
	public boolean rememberDevice;
	public boolean trustedApplication;

}