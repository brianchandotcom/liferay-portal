/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OAuth2Application}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2Application
 * @generated
 */
public class OAuth2ApplicationWrapper
	extends BaseModelWrapper<OAuth2Application>
	implements ModelWrapper<OAuth2Application>, OAuth2Application {

	public OAuth2ApplicationWrapper(OAuth2Application oAuth2Application) {
		super(oAuth2Application);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("oAuth2ApplicationId", getOAuth2ApplicationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"oAuth2ApplicationScopeAliasesId",
			getOAuth2ApplicationScopeAliasesId());
		attributes.put("allowedGrantTypes", getAllowedGrantTypes());
		attributes.put(
			"clientAuthenticationMethod", getClientAuthenticationMethod());
		attributes.put("clientCredentialUserId", getClientCredentialUserId());
		attributes.put(
			"clientCredentialUserName", getClientCredentialUserName());
		attributes.put("clientId", getClientId());
		attributes.put("clientProfile", getClientProfile());
		attributes.put("clientSecret", getClientSecret());
		attributes.put("description", getDescription());
		attributes.put("features", getFeatures());
		attributes.put("homePageURL", getHomePageURL());
		attributes.put("iconFileEntryId", getIconFileEntryId());
		attributes.put("jwks", getJwks());
		attributes.put("name", getName());
		attributes.put("privacyPolicyURL", getPrivacyPolicyURL());
		attributes.put("redirectURIs", getRedirectURIs());
		attributes.put("rememberDevice", isRememberDevice());
		attributes.put("trustedApplication", isTrustedApplication());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long oAuth2ApplicationId = (Long)attributes.get("oAuth2ApplicationId");

		if (oAuth2ApplicationId != null) {
			setOAuth2ApplicationId(oAuth2ApplicationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long oAuth2ApplicationScopeAliasesId = (Long)attributes.get(
			"oAuth2ApplicationScopeAliasesId");

		if (oAuth2ApplicationScopeAliasesId != null) {
			setOAuth2ApplicationScopeAliasesId(oAuth2ApplicationScopeAliasesId);
		}

		String allowedGrantTypes = (String)attributes.get("allowedGrantTypes");

		if (allowedGrantTypes != null) {
			setAllowedGrantTypes(allowedGrantTypes);
		}

		String clientAuthenticationMethod = (String)attributes.get(
			"clientAuthenticationMethod");

		if (clientAuthenticationMethod != null) {
			setClientAuthenticationMethod(clientAuthenticationMethod);
		}

		Long clientCredentialUserId = (Long)attributes.get(
			"clientCredentialUserId");

		if (clientCredentialUserId != null) {
			setClientCredentialUserId(clientCredentialUserId);
		}

		String clientCredentialUserName = (String)attributes.get(
			"clientCredentialUserName");

		if (clientCredentialUserName != null) {
			setClientCredentialUserName(clientCredentialUserName);
		}

		String clientId = (String)attributes.get("clientId");

		if (clientId != null) {
			setClientId(clientId);
		}

		Integer clientProfile = (Integer)attributes.get("clientProfile");

		if (clientProfile != null) {
			setClientProfile(clientProfile);
		}

		String clientSecret = (String)attributes.get("clientSecret");

		if (clientSecret != null) {
			setClientSecret(clientSecret);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String features = (String)attributes.get("features");

		if (features != null) {
			setFeatures(features);
		}

		String homePageURL = (String)attributes.get("homePageURL");

		if (homePageURL != null) {
			setHomePageURL(homePageURL);
		}

		Long iconFileEntryId = (Long)attributes.get("iconFileEntryId");

		if (iconFileEntryId != null) {
			setIconFileEntryId(iconFileEntryId);
		}

		String jwks = (String)attributes.get("jwks");

		if (jwks != null) {
			setJwks(jwks);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String privacyPolicyURL = (String)attributes.get("privacyPolicyURL");

		if (privacyPolicyURL != null) {
			setPrivacyPolicyURL(privacyPolicyURL);
		}

		String redirectURIs = (String)attributes.get("redirectURIs");

		if (redirectURIs != null) {
			setRedirectURIs(redirectURIs);
		}

		Boolean rememberDevice = (Boolean)attributes.get("rememberDevice");

		if (rememberDevice != null) {
			setRememberDevice(rememberDevice);
		}

		Boolean trustedApplication = (Boolean)attributes.get(
			"trustedApplication");

		if (trustedApplication != null) {
			setTrustedApplication(trustedApplication);
		}
	}

	@Override
	public OAuth2Application cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the allowed grant types of this o auth2 application.
	 *
	 * @return the allowed grant types of this o auth2 application
	 */
	@Override
	public String getAllowedGrantTypes() {
		return model.getAllowedGrantTypes();
	}

	@Override
	public java.util.List<com.liferay.oauth2.provider.constants.GrantType>
		getAllowedGrantTypesList() {

		return model.getAllowedGrantTypesList();
	}

	/**
	 * Returns the client authentication method of this o auth2 application.
	 *
	 * @return the client authentication method of this o auth2 application
	 */
	@Override
	public String getClientAuthenticationMethod() {
		return model.getClientAuthenticationMethod();
	}

	/**
	 * Returns the client credential user ID of this o auth2 application.
	 *
	 * @return the client credential user ID of this o auth2 application
	 */
	@Override
	public long getClientCredentialUserId() {
		return model.getClientCredentialUserId();
	}

	/**
	 * Returns the client credential user name of this o auth2 application.
	 *
	 * @return the client credential user name of this o auth2 application
	 */
	@Override
	public String getClientCredentialUserName() {
		return model.getClientCredentialUserName();
	}

	/**
	 * Returns the client credential user uuid of this o auth2 application.
	 *
	 * @return the client credential user uuid of this o auth2 application
	 */
	@Override
	public String getClientCredentialUserUuid() {
		return model.getClientCredentialUserUuid();
	}

	/**
	 * Returns the client ID of this o auth2 application.
	 *
	 * @return the client ID of this o auth2 application
	 */
	@Override
	public String getClientId() {
		return model.getClientId();
	}

	/**
	 * Returns the client profile of this o auth2 application.
	 *
	 * @return the client profile of this o auth2 application
	 */
	@Override
	public int getClientProfile() {
		return model.getClientProfile();
	}

	/**
	 * Returns the client secret of this o auth2 application.
	 *
	 * @return the client secret of this o auth2 application
	 */
	@Override
	public String getClientSecret() {
		return model.getClientSecret();
	}

	/**
	 * Returns the company ID of this o auth2 application.
	 *
	 * @return the company ID of this o auth2 application
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this o auth2 application.
	 *
	 * @return the create date of this o auth2 application
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this o auth2 application.
	 *
	 * @return the description of this o auth2 application
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the external reference code of this o auth2 application.
	 *
	 * @return the external reference code of this o auth2 application
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the features of this o auth2 application.
	 *
	 * @return the features of this o auth2 application
	 */
	@Override
	public String getFeatures() {
		return model.getFeatures();
	}

	@Override
	public java.util.List<String> getFeaturesList() {
		return model.getFeaturesList();
	}

	/**
	 * Returns the home page url of this o auth2 application.
	 *
	 * @return the home page url of this o auth2 application
	 */
	@Override
	public String getHomePageURL() {
		return model.getHomePageURL();
	}

	/**
	 * Returns the icon file entry ID of this o auth2 application.
	 *
	 * @return the icon file entry ID of this o auth2 application
	 */
	@Override
	public long getIconFileEntryId() {
		return model.getIconFileEntryId();
	}

	/**
	 * Returns the jwks of this o auth2 application.
	 *
	 * @return the jwks of this o auth2 application
	 */
	@Override
	public String getJwks() {
		return model.getJwks();
	}

	/**
	 * Returns the modified date of this o auth2 application.
	 *
	 * @return the modified date of this o auth2 application
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this o auth2 application.
	 *
	 * @return the name of this o auth2 application
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the o auth2 application ID of this o auth2 application.
	 *
	 * @return the o auth2 application ID of this o auth2 application
	 */
	@Override
	public long getOAuth2ApplicationId() {
		return model.getOAuth2ApplicationId();
	}

	/**
	 * Returns the o auth2 application scope aliases ID of this o auth2 application.
	 *
	 * @return the o auth2 application scope aliases ID of this o auth2 application
	 */
	@Override
	public long getOAuth2ApplicationScopeAliasesId() {
		return model.getOAuth2ApplicationScopeAliasesId();
	}

	/**
	 * Returns the primary key of this o auth2 application.
	 *
	 * @return the primary key of this o auth2 application
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the privacy policy url of this o auth2 application.
	 *
	 * @return the privacy policy url of this o auth2 application
	 */
	@Override
	public String getPrivacyPolicyURL() {
		return model.getPrivacyPolicyURL();
	}

	/**
	 * Returns the redirect ur is of this o auth2 application.
	 *
	 * @return the redirect ur is of this o auth2 application
	 */
	@Override
	public String getRedirectURIs() {
		return model.getRedirectURIs();
	}

	@Override
	public java.util.List<String> getRedirectURIsList() {
		return model.getRedirectURIsList();
	}

	/**
	 * Returns the remember device of this o auth2 application.
	 *
	 * @return the remember device of this o auth2 application
	 */
	@Override
	public boolean getRememberDevice() {
		return model.getRememberDevice();
	}

	/**
	 * Returns the trusted application of this o auth2 application.
	 *
	 * @return the trusted application of this o auth2 application
	 */
	@Override
	public boolean getTrustedApplication() {
		return model.getTrustedApplication();
	}

	/**
	 * Returns the user ID of this o auth2 application.
	 *
	 * @return the user ID of this o auth2 application
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this o auth2 application.
	 *
	 * @return the user name of this o auth2 application
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this o auth2 application.
	 *
	 * @return the user uuid of this o auth2 application
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this o auth2 application.
	 *
	 * @return the uuid of this o auth2 application
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this o auth2 application is remember device.
	 *
	 * @return <code>true</code> if this o auth2 application is remember device; <code>false</code> otherwise
	 */
	@Override
	public boolean isRememberDevice() {
		return model.isRememberDevice();
	}

	/**
	 * Returns <code>true</code> if this o auth2 application is trusted application.
	 *
	 * @return <code>true</code> if this o auth2 application is trusted application; <code>false</code> otherwise
	 */
	@Override
	public boolean isTrustedApplication() {
		return model.isTrustedApplication();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the allowed grant types of this o auth2 application.
	 *
	 * @param allowedGrantTypes the allowed grant types of this o auth2 application
	 */
	@Override
	public void setAllowedGrantTypes(String allowedGrantTypes) {
		model.setAllowedGrantTypes(allowedGrantTypes);
	}

	@Override
	public void setAllowedGrantTypesList(
		java.util.List<com.liferay.oauth2.provider.constants.GrantType>
			allowedGrantTypesList) {

		model.setAllowedGrantTypesList(allowedGrantTypesList);
	}

	/**
	 * Sets the client authentication method of this o auth2 application.
	 *
	 * @param clientAuthenticationMethod the client authentication method of this o auth2 application
	 */
	@Override
	public void setClientAuthenticationMethod(
		String clientAuthenticationMethod) {

		model.setClientAuthenticationMethod(clientAuthenticationMethod);
	}

	/**
	 * Sets the client credential user ID of this o auth2 application.
	 *
	 * @param clientCredentialUserId the client credential user ID of this o auth2 application
	 */
	@Override
	public void setClientCredentialUserId(long clientCredentialUserId) {
		model.setClientCredentialUserId(clientCredentialUserId);
	}

	/**
	 * Sets the client credential user name of this o auth2 application.
	 *
	 * @param clientCredentialUserName the client credential user name of this o auth2 application
	 */
	@Override
	public void setClientCredentialUserName(String clientCredentialUserName) {
		model.setClientCredentialUserName(clientCredentialUserName);
	}

	/**
	 * Sets the client credential user uuid of this o auth2 application.
	 *
	 * @param clientCredentialUserUuid the client credential user uuid of this o auth2 application
	 */
	@Override
	public void setClientCredentialUserUuid(String clientCredentialUserUuid) {
		model.setClientCredentialUserUuid(clientCredentialUserUuid);
	}

	/**
	 * Sets the client ID of this o auth2 application.
	 *
	 * @param clientId the client ID of this o auth2 application
	 */
	@Override
	public void setClientId(String clientId) {
		model.setClientId(clientId);
	}

	/**
	 * Sets the client profile of this o auth2 application.
	 *
	 * @param clientProfile the client profile of this o auth2 application
	 */
	@Override
	public void setClientProfile(int clientProfile) {
		model.setClientProfile(clientProfile);
	}

	/**
	 * Sets the client secret of this o auth2 application.
	 *
	 * @param clientSecret the client secret of this o auth2 application
	 */
	@Override
	public void setClientSecret(String clientSecret) {
		model.setClientSecret(clientSecret);
	}

	/**
	 * Sets the company ID of this o auth2 application.
	 *
	 * @param companyId the company ID of this o auth2 application
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this o auth2 application.
	 *
	 * @param createDate the create date of this o auth2 application
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this o auth2 application.
	 *
	 * @param description the description of this o auth2 application
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the external reference code of this o auth2 application.
	 *
	 * @param externalReferenceCode the external reference code of this o auth2 application
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the features of this o auth2 application.
	 *
	 * @param features the features of this o auth2 application
	 */
	@Override
	public void setFeatures(String features) {
		model.setFeatures(features);
	}

	@Override
	public void setFeaturesList(java.util.List<String> featuresList) {
		model.setFeaturesList(featuresList);
	}

	/**
	 * Sets the home page url of this o auth2 application.
	 *
	 * @param homePageURL the home page url of this o auth2 application
	 */
	@Override
	public void setHomePageURL(String homePageURL) {
		model.setHomePageURL(homePageURL);
	}

	/**
	 * Sets the icon file entry ID of this o auth2 application.
	 *
	 * @param iconFileEntryId the icon file entry ID of this o auth2 application
	 */
	@Override
	public void setIconFileEntryId(long iconFileEntryId) {
		model.setIconFileEntryId(iconFileEntryId);
	}

	/**
	 * Sets the jwks of this o auth2 application.
	 *
	 * @param jwks the jwks of this o auth2 application
	 */
	@Override
	public void setJwks(String jwks) {
		model.setJwks(jwks);
	}

	/**
	 * Sets the modified date of this o auth2 application.
	 *
	 * @param modifiedDate the modified date of this o auth2 application
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this o auth2 application.
	 *
	 * @param name the name of this o auth2 application
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the o auth2 application ID of this o auth2 application.
	 *
	 * @param oAuth2ApplicationId the o auth2 application ID of this o auth2 application
	 */
	@Override
	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		model.setOAuth2ApplicationId(oAuth2ApplicationId);
	}

	/**
	 * Sets the o auth2 application scope aliases ID of this o auth2 application.
	 *
	 * @param oAuth2ApplicationScopeAliasesId the o auth2 application scope aliases ID of this o auth2 application
	 */
	@Override
	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {

		model.setOAuth2ApplicationScopeAliasesId(
			oAuth2ApplicationScopeAliasesId);
	}

	/**
	 * Sets the primary key of this o auth2 application.
	 *
	 * @param primaryKey the primary key of this o auth2 application
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the privacy policy url of this o auth2 application.
	 *
	 * @param privacyPolicyURL the privacy policy url of this o auth2 application
	 */
	@Override
	public void setPrivacyPolicyURL(String privacyPolicyURL) {
		model.setPrivacyPolicyURL(privacyPolicyURL);
	}

	/**
	 * Sets the redirect ur is of this o auth2 application.
	 *
	 * @param redirectURIs the redirect ur is of this o auth2 application
	 */
	@Override
	public void setRedirectURIs(String redirectURIs) {
		model.setRedirectURIs(redirectURIs);
	}

	@Override
	public void setRedirectURIsList(java.util.List<String> redirectURIsList) {
		model.setRedirectURIsList(redirectURIsList);
	}

	/**
	 * Sets whether this o auth2 application is remember device.
	 *
	 * @param rememberDevice the remember device of this o auth2 application
	 */
	@Override
	public void setRememberDevice(boolean rememberDevice) {
		model.setRememberDevice(rememberDevice);
	}

	/**
	 * Sets whether this o auth2 application is trusted application.
	 *
	 * @param trustedApplication the trusted application of this o auth2 application
	 */
	@Override
	public void setTrustedApplication(boolean trustedApplication) {
		model.setTrustedApplication(trustedApplication);
	}

	/**
	 * Sets the user ID of this o auth2 application.
	 *
	 * @param userId the user ID of this o auth2 application
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this o auth2 application.
	 *
	 * @param userName the user name of this o auth2 application
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this o auth2 application.
	 *
	 * @param userUuid the user uuid of this o auth2 application
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this o auth2 application.
	 *
	 * @param uuid the uuid of this o auth2 application
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected OAuth2ApplicationWrapper wrap(
		OAuth2Application oAuth2Application) {

		return new OAuth2ApplicationWrapper(oAuth2Application);
	}

}