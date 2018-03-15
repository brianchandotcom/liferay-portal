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

package com.liferay.oauth2.provider.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.oauth2.provider.service.http.OAuth2ApplicationServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.oauth2.provider.service.http.OAuth2ApplicationServiceSoap
 * @generated
 */
@ProviderType
public class OAuth2ApplicationSoap implements Serializable {
	public static OAuth2ApplicationSoap toSoapModel(OAuth2Application model) {
		OAuth2ApplicationSoap soapModel = new OAuth2ApplicationSoap();

		soapModel.setOAuth2ApplicationId(model.getOAuth2ApplicationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setAllowedGrantTypes(model.getAllowedGrantTypes());
		soapModel.setClientConfidential(model.getClientConfidential());
		soapModel.setClientId(model.getClientId());
		soapModel.setClientSecret(model.getClientSecret());
		soapModel.setDescription(model.getDescription());
		soapModel.setHomePageURL(model.getHomePageURL());
		soapModel.setIconFileEntryId(model.getIconFileEntryId());
		soapModel.setName(model.getName());
		soapModel.setPrivacyPolicyURL(model.getPrivacyPolicyURL());
		soapModel.setRedirectURIs(model.getRedirectURIs());
		soapModel.setScopes(model.getScopes());
		soapModel.setFeatures(model.getFeatures());

		return soapModel;
	}

	public static OAuth2ApplicationSoap[] toSoapModels(
		OAuth2Application[] models) {
		OAuth2ApplicationSoap[] soapModels = new OAuth2ApplicationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OAuth2ApplicationSoap[][] toSoapModels(
		OAuth2Application[][] models) {
		OAuth2ApplicationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new OAuth2ApplicationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OAuth2ApplicationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OAuth2ApplicationSoap[] toSoapModels(
		List<OAuth2Application> models) {
		List<OAuth2ApplicationSoap> soapModels = new ArrayList<OAuth2ApplicationSoap>(models.size());

		for (OAuth2Application model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OAuth2ApplicationSoap[soapModels.size()]);
	}

	public OAuth2ApplicationSoap() {
	}

	public long getPrimaryKey() {
		return _oAuth2ApplicationId;
	}

	public void setPrimaryKey(long pk) {
		setOAuth2ApplicationId(pk);
	}

	public long getOAuth2ApplicationId() {
		return _oAuth2ApplicationId;
	}

	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2ApplicationId = oAuth2ApplicationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public String getAllowedGrantTypes() {
		return _allowedGrantTypes;
	}

	public void setAllowedGrantTypes(String allowedGrantTypes) {
		_allowedGrantTypes = allowedGrantTypes;
	}

	public Boolean getClientConfidential() {
		return _clientConfidential;
	}

	public void setClientConfidential(Boolean clientConfidential) {
		_clientConfidential = clientConfidential;
	}

	public String getClientId() {
		return _clientId;
	}

	public void setClientId(String clientId) {
		_clientId = clientId;
	}

	public String getClientSecret() {
		return _clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		_clientSecret = clientSecret;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getHomePageURL() {
		return _homePageURL;
	}

	public void setHomePageURL(String homePageURL) {
		_homePageURL = homePageURL;
	}

	public long getIconFileEntryId() {
		return _iconFileEntryId;
	}

	public void setIconFileEntryId(long iconFileEntryId) {
		_iconFileEntryId = iconFileEntryId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getPrivacyPolicyURL() {
		return _privacyPolicyURL;
	}

	public void setPrivacyPolicyURL(String privacyPolicyURL) {
		_privacyPolicyURL = privacyPolicyURL;
	}

	public String getRedirectURIs() {
		return _redirectURIs;
	}

	public void setRedirectURIs(String redirectURIs) {
		_redirectURIs = redirectURIs;
	}

	public String getScopes() {
		return _scopes;
	}

	public void setScopes(String scopes) {
		_scopes = scopes;
	}

	public String getFeatures() {
		return _features;
	}

	public void setFeatures(String features) {
		_features = features;
	}

	private long _oAuth2ApplicationId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private long _userId;
	private String _userName;
	private String _allowedGrantTypes;
	private Boolean _clientConfidential;
	private String _clientId;
	private String _clientSecret;
	private String _description;
	private String _homePageURL;
	private long _iconFileEntryId;
	private String _name;
	private String _privacyPolicyURL;
	private String _redirectURIs;
	private String _scopes;
	private String _features;
}