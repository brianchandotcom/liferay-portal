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

package com.liferay.saml.persistence.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Mika Koivisto
 * @generated
 */
@ProviderType
public class SamlSpAuthRequestSoap implements Serializable {
	public static SamlSpAuthRequestSoap toSoapModel(SamlSpAuthRequest model) {
		SamlSpAuthRequestSoap soapModel = new SamlSpAuthRequestSoap();

		soapModel.setSamlSpAuthnRequestId(model.getSamlSpAuthnRequestId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setSamlIdpEntityId(model.getSamlIdpEntityId());
		soapModel.setSamlSpAuthRequestKey(model.getSamlSpAuthRequestKey());

		return soapModel;
	}

	public static SamlSpAuthRequestSoap[] toSoapModels(
		SamlSpAuthRequest[] models) {
		SamlSpAuthRequestSoap[] soapModels = new SamlSpAuthRequestSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SamlSpAuthRequestSoap[][] toSoapModels(
		SamlSpAuthRequest[][] models) {
		SamlSpAuthRequestSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SamlSpAuthRequestSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SamlSpAuthRequestSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SamlSpAuthRequestSoap[] toSoapModels(
		List<SamlSpAuthRequest> models) {
		List<SamlSpAuthRequestSoap> soapModels = new ArrayList<SamlSpAuthRequestSoap>(models.size());

		for (SamlSpAuthRequest model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SamlSpAuthRequestSoap[soapModels.size()]);
	}

	public SamlSpAuthRequestSoap() {
	}

	public long getPrimaryKey() {
		return _samlSpAuthnRequestId;
	}

	public void setPrimaryKey(long pk) {
		setSamlSpAuthnRequestId(pk);
	}

	public long getSamlSpAuthnRequestId() {
		return _samlSpAuthnRequestId;
	}

	public void setSamlSpAuthnRequestId(long samlSpAuthnRequestId) {
		_samlSpAuthnRequestId = samlSpAuthnRequestId;
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

	public String getSamlIdpEntityId() {
		return _samlIdpEntityId;
	}

	public void setSamlIdpEntityId(String samlIdpEntityId) {
		_samlIdpEntityId = samlIdpEntityId;
	}

	public String getSamlSpAuthRequestKey() {
		return _samlSpAuthRequestKey;
	}

	public void setSamlSpAuthRequestKey(String samlSpAuthRequestKey) {
		_samlSpAuthRequestKey = samlSpAuthRequestKey;
	}

	private long _samlSpAuthnRequestId;
	private long _companyId;
	private Date _createDate;
	private String _samlIdpEntityId;
	private String _samlSpAuthRequestKey;
}