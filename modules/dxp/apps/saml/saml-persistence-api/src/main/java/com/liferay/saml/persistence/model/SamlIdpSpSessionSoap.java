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
public class SamlIdpSpSessionSoap implements Serializable {

	public static SamlIdpSpSessionSoap toSoapModel(SamlIdpSpSession model) {
		SamlIdpSpSessionSoap soapModel = new SamlIdpSpSessionSoap();

		soapModel.setSamlIdpSpSessionId(model.getSamlIdpSpSessionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSamlIdpSsoSessionId(model.getSamlIdpSsoSessionId());
		soapModel.setSamlSpEntityId(model.getSamlSpEntityId());
		soapModel.setNameIdFormat(model.getNameIdFormat());
		soapModel.setNameIdValue(model.getNameIdValue());

		return soapModel;
	}

	public static SamlIdpSpSessionSoap[] toSoapModels(
		SamlIdpSpSession[] models) {

		SamlIdpSpSessionSoap[] soapModels =
			new SamlIdpSpSessionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SamlIdpSpSessionSoap[][] toSoapModels(
		SamlIdpSpSession[][] models) {

		SamlIdpSpSessionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SamlIdpSpSessionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SamlIdpSpSessionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SamlIdpSpSessionSoap[] toSoapModels(
		List<SamlIdpSpSession> models) {

		List<SamlIdpSpSessionSoap> soapModels =
			new ArrayList<SamlIdpSpSessionSoap>(models.size());

		for (SamlIdpSpSession model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SamlIdpSpSessionSoap[soapModels.size()]);
	}

	public SamlIdpSpSessionSoap() {
	}

	public long getPrimaryKey() {
		return _samlIdpSpSessionId;
	}

	public void setPrimaryKey(long pk) {
		setSamlIdpSpSessionId(pk);
	}

	public long getSamlIdpSpSessionId() {
		return _samlIdpSpSessionId;
	}

	public void setSamlIdpSpSessionId(long samlIdpSpSessionId) {
		_samlIdpSpSessionId = samlIdpSpSessionId;
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

	public long getSamlIdpSsoSessionId() {
		return _samlIdpSsoSessionId;
	}

	public void setSamlIdpSsoSessionId(long samlIdpSsoSessionId) {
		_samlIdpSsoSessionId = samlIdpSsoSessionId;
	}

	public String getSamlSpEntityId() {
		return _samlSpEntityId;
	}

	public void setSamlSpEntityId(String samlSpEntityId) {
		_samlSpEntityId = samlSpEntityId;
	}

	public String getNameIdFormat() {
		return _nameIdFormat;
	}

	public void setNameIdFormat(String nameIdFormat) {
		_nameIdFormat = nameIdFormat;
	}

	public String getNameIdValue() {
		return _nameIdValue;
	}

	public void setNameIdValue(String nameIdValue) {
		_nameIdValue = nameIdValue;
	}

	private long _samlIdpSpSessionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _samlIdpSsoSessionId;
	private String _samlSpEntityId;
	private String _nameIdFormat;
	private String _nameIdValue;

}