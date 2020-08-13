/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Mika Koivisto
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SamlIdpSsoSessionSoap implements Serializable {

	public static SamlIdpSsoSessionSoap toSoapModel(SamlIdpSsoSession model) {
		SamlIdpSsoSessionSoap soapModel = new SamlIdpSsoSessionSoap();

		soapModel.setSamlIdpSsoSessionId(model.getSamlIdpSsoSessionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSamlIdpSsoSessionKey(model.getSamlIdpSsoSessionKey());

		return soapModel;
	}

	public static SamlIdpSsoSessionSoap[] toSoapModels(
		SamlIdpSsoSession[] models) {

		SamlIdpSsoSessionSoap[] soapModels =
			new SamlIdpSsoSessionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SamlIdpSsoSessionSoap[][] toSoapModels(
		SamlIdpSsoSession[][] models) {

		SamlIdpSsoSessionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SamlIdpSsoSessionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SamlIdpSsoSessionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SamlIdpSsoSessionSoap[] toSoapModels(
		List<SamlIdpSsoSession> models) {

		List<SamlIdpSsoSessionSoap> soapModels =
			new ArrayList<SamlIdpSsoSessionSoap>(models.size());

		for (SamlIdpSsoSession model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SamlIdpSsoSessionSoap[soapModels.size()]);
	}

	public SamlIdpSsoSessionSoap() {
	}

	public long getPrimaryKey() {
		return _samlIdpSsoSessionId;
	}

	public void setPrimaryKey(long pk) {
		setSamlIdpSsoSessionId(pk);
	}

	public long getSamlIdpSsoSessionId() {
		return _samlIdpSsoSessionId;
	}

	public void setSamlIdpSsoSessionId(long samlIdpSsoSessionId) {
		_samlIdpSsoSessionId = samlIdpSsoSessionId;
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

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getSamlIdpSsoSessionKey() {
		return _samlIdpSsoSessionKey;
	}

	public void setSamlIdpSsoSessionKey(String samlIdpSsoSessionKey) {
		_samlIdpSsoSessionKey = samlIdpSsoSessionKey;
	}

	private long _samlIdpSsoSessionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _samlIdpSsoSessionKey;

}