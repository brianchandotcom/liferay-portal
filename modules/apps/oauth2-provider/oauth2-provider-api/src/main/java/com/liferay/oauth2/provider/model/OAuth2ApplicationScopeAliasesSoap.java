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
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class OAuth2ApplicationScopeAliasesSoap implements Serializable {

	public static OAuth2ApplicationScopeAliasesSoap toSoapModel(
		OAuth2ApplicationScopeAliases model) {

		OAuth2ApplicationScopeAliasesSoap soapModel =
			new OAuth2ApplicationScopeAliasesSoap();

		soapModel.setOAuth2ApplicationScopeAliasesId(
			model.getOAuth2ApplicationScopeAliasesId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setOAuth2ApplicationId(model.getOAuth2ApplicationId());

		return soapModel;
	}

	public static OAuth2ApplicationScopeAliasesSoap[] toSoapModels(
		OAuth2ApplicationScopeAliases[] models) {

		OAuth2ApplicationScopeAliasesSoap[] soapModels =
			new OAuth2ApplicationScopeAliasesSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OAuth2ApplicationScopeAliasesSoap[][] toSoapModels(
		OAuth2ApplicationScopeAliases[][] models) {

		OAuth2ApplicationScopeAliasesSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new OAuth2ApplicationScopeAliasesSoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new OAuth2ApplicationScopeAliasesSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OAuth2ApplicationScopeAliasesSoap[] toSoapModels(
		List<OAuth2ApplicationScopeAliases> models) {

		List<OAuth2ApplicationScopeAliasesSoap> soapModels =
			new ArrayList<OAuth2ApplicationScopeAliasesSoap>(models.size());

		for (OAuth2ApplicationScopeAliases model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new OAuth2ApplicationScopeAliasesSoap[soapModels.size()]);
	}

	public OAuth2ApplicationScopeAliasesSoap() {
	}

	public long getPrimaryKey() {
		return _oAuth2ApplicationScopeAliasesId;
	}

	public void setPrimaryKey(long pk) {
		setOAuth2ApplicationScopeAliasesId(pk);
	}

	public long getOAuth2ApplicationScopeAliasesId() {
		return _oAuth2ApplicationScopeAliasesId;
	}

	public void setOAuth2ApplicationScopeAliasesId(
		long oAuth2ApplicationScopeAliasesId) {

		_oAuth2ApplicationScopeAliasesId = oAuth2ApplicationScopeAliasesId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
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

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getOAuth2ApplicationId() {
		return _oAuth2ApplicationId;
	}

	public void setOAuth2ApplicationId(long oAuth2ApplicationId) {
		_oAuth2ApplicationId = oAuth2ApplicationId;
	}

	private long _oAuth2ApplicationScopeAliasesId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private long _oAuth2ApplicationId;

}