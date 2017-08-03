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
public class SamlSpMessageSoap implements Serializable {
	public static SamlSpMessageSoap toSoapModel(SamlSpMessage model) {
		SamlSpMessageSoap soapModel = new SamlSpMessageSoap();

		soapModel.setSamlSpMessageId(model.getSamlSpMessageId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setSamlIdpEntityId(model.getSamlIdpEntityId());
		soapModel.setSamlIdpResponseKey(model.getSamlIdpResponseKey());
		soapModel.setExpirationDate(model.getExpirationDate());

		return soapModel;
	}

	public static SamlSpMessageSoap[] toSoapModels(SamlSpMessage[] models) {
		SamlSpMessageSoap[] soapModels = new SamlSpMessageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SamlSpMessageSoap[][] toSoapModels(SamlSpMessage[][] models) {
		SamlSpMessageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SamlSpMessageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SamlSpMessageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SamlSpMessageSoap[] toSoapModels(List<SamlSpMessage> models) {
		List<SamlSpMessageSoap> soapModels = new ArrayList<SamlSpMessageSoap>(models.size());

		for (SamlSpMessage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SamlSpMessageSoap[soapModels.size()]);
	}

	public SamlSpMessageSoap() {
	}

	public long getPrimaryKey() {
		return _samlSpMessageId;
	}

	public void setPrimaryKey(long pk) {
		setSamlSpMessageId(pk);
	}

	public long getSamlSpMessageId() {
		return _samlSpMessageId;
	}

	public void setSamlSpMessageId(long samlSpMessageId) {
		_samlSpMessageId = samlSpMessageId;
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

	public String getSamlIdpResponseKey() {
		return _samlIdpResponseKey;
	}

	public void setSamlIdpResponseKey(String samlIdpResponseKey) {
		_samlIdpResponseKey = samlIdpResponseKey;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	private long _samlSpMessageId;
	private long _companyId;
	private Date _createDate;
	private String _samlIdpEntityId;
	private String _samlIdpResponseKey;
	private Date _expirationDate;
}