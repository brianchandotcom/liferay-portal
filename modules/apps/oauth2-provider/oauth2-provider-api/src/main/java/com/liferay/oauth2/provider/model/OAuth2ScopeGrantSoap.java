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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2ScopeGrantSoap implements Serializable {

	public static OAuth2ScopeGrantSoap toSoapModel(OAuth2ScopeGrant model) {
		OAuth2ScopeGrantSoap soapModel = new OAuth2ScopeGrantSoap();

		soapModel.setOAuth2ScopeGrantId(model.getOAuth2ScopeGrantId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setOAuth2ApplicationScopeAliasesId(
			model.getOAuth2ApplicationScopeAliasesId());
		soapModel.setApplicationName(model.getApplicationName());
		soapModel.setBundleSymbolicName(model.getBundleSymbolicName());
		soapModel.setScope(model.getScope());
		soapModel.setScopeAliases(model.getScopeAliases());

		return soapModel;
	}

	public static OAuth2ScopeGrantSoap[] toSoapModels(
		OAuth2ScopeGrant[] models) {

		OAuth2ScopeGrantSoap[] soapModels =
			new OAuth2ScopeGrantSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OAuth2ScopeGrantSoap[][] toSoapModels(
		OAuth2ScopeGrant[][] models) {

		OAuth2ScopeGrantSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new OAuth2ScopeGrantSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OAuth2ScopeGrantSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OAuth2ScopeGrantSoap[] toSoapModels(
		List<OAuth2ScopeGrant> models) {

		List<OAuth2ScopeGrantSoap> soapModels =
			new ArrayList<OAuth2ScopeGrantSoap>(models.size());

		for (OAuth2ScopeGrant model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OAuth2ScopeGrantSoap[soapModels.size()]);
	}

	public OAuth2ScopeGrantSoap() {
	}

	public long getPrimaryKey() {
		return _oAuth2ScopeGrantId;
	}

	public void setPrimaryKey(long pk) {
		setOAuth2ScopeGrantId(pk);
	}

	public long getOAuth2ScopeGrantId() {
		return _oAuth2ScopeGrantId;
	}

	public void setOAuth2ScopeGrantId(long oAuth2ScopeGrantId) {
		_oAuth2ScopeGrantId = oAuth2ScopeGrantId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getOAuth2ApplicationScopeAliasesId() {
		return _oAuth2ApplicationScopeAliasesId;
	}

	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {

		_oAuth2ApplicationScopeAliasesId = oAuth2ApplicationScopeAliasesId;
	}

	public String getApplicationName() {
		return _applicationName;
	}

	public void setApplicationName(String applicationName) {
		_applicationName = applicationName;
	}

	public String getBundleSymbolicName() {
		return _bundleSymbolicName;
	}

	public void setBundleSymbolicName(String bundleSymbolicName) {
		_bundleSymbolicName = bundleSymbolicName;
	}

	public String getScope() {
		return _scope;
	}

	public void setScope(String scope) {
		_scope = scope;
	}

	public String getScopeAliases() {
		return _scopeAliases;
	}

	public void setScopeAliases(String scopeAliases) {
		_scopeAliases = scopeAliases;
	}

	private long _oAuth2ScopeGrantId;
	private long _companyId;
	private long _oAuth2ApplicationScopeAliasesId;
	private String _applicationName;
	private String _bundleSymbolicName;
	private String _scope;
	private String _scopeAliases;

}