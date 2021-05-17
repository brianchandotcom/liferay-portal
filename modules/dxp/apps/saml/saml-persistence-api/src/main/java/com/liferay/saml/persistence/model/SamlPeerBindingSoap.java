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
public class SamlPeerBindingSoap implements Serializable {

	public static SamlPeerBindingSoap toSoapModel(SamlPeerBinding model) {
		SamlPeerBindingSoap soapModel = new SamlPeerBindingSoap();

		soapModel.setSamlPeerBindingId(model.getSamlPeerBindingId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setDeleted(model.isDeleted());
		soapModel.setSamlNameIdFormat(model.getSamlNameIdFormat());
		soapModel.setSamlNameIdNameQualifier(
			model.getSamlNameIdNameQualifier());
		soapModel.setSamlNameIdSpNameQualifier(
			model.getSamlNameIdSpNameQualifier());
		soapModel.setSamlNameIdSpProvidedId(model.getSamlNameIdSpProvidedId());
		soapModel.setSamlNameIdValue(model.getSamlNameIdValue());
		soapModel.setSamlPeerEntityId(model.getSamlPeerEntityId());

		return soapModel;
	}

	public static SamlPeerBindingSoap[] toSoapModels(SamlPeerBinding[] models) {
		SamlPeerBindingSoap[] soapModels =
			new SamlPeerBindingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SamlPeerBindingSoap[][] toSoapModels(
		SamlPeerBinding[][] models) {

		SamlPeerBindingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SamlPeerBindingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SamlPeerBindingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SamlPeerBindingSoap[] toSoapModels(
		List<SamlPeerBinding> models) {

		List<SamlPeerBindingSoap> soapModels =
			new ArrayList<SamlPeerBindingSoap>(models.size());

		for (SamlPeerBinding model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SamlPeerBindingSoap[soapModels.size()]);
	}

	public SamlPeerBindingSoap() {
	}

	public long getPrimaryKey() {
		return _samlPeerBindingId;
	}

	public void setPrimaryKey(long pk) {
		setSamlPeerBindingId(pk);
	}

	public long getSamlPeerBindingId() {
		return _samlPeerBindingId;
	}

	public void setSamlPeerBindingId(long samlPeerBindingId) {
		_samlPeerBindingId = samlPeerBindingId;
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

	public boolean getDeleted() {
		return _deleted;
	}

	public boolean isDeleted() {
		return _deleted;
	}

	public void setDeleted(boolean deleted) {
		_deleted = deleted;
	}

	public String getSamlNameIdFormat() {
		return _samlNameIdFormat;
	}

	public void setSamlNameIdFormat(String samlNameIdFormat) {
		_samlNameIdFormat = samlNameIdFormat;
	}

	public String getSamlNameIdNameQualifier() {
		return _samlNameIdNameQualifier;
	}

	public void setSamlNameIdNameQualifier(String samlNameIdNameQualifier) {
		_samlNameIdNameQualifier = samlNameIdNameQualifier;
	}

	public String getSamlNameIdSpNameQualifier() {
		return _samlNameIdSpNameQualifier;
	}

	public void setSamlNameIdSpNameQualifier(String samlNameIdSpNameQualifier) {
		_samlNameIdSpNameQualifier = samlNameIdSpNameQualifier;
	}

	public String getSamlNameIdSpProvidedId() {
		return _samlNameIdSpProvidedId;
	}

	public void setSamlNameIdSpProvidedId(String samlNameIdSpProvidedId) {
		_samlNameIdSpProvidedId = samlNameIdSpProvidedId;
	}

	public String getSamlNameIdValue() {
		return _samlNameIdValue;
	}

	public void setSamlNameIdValue(String samlNameIdValue) {
		_samlNameIdValue = samlNameIdValue;
	}

	public String getSamlPeerEntityId() {
		return _samlPeerEntityId;
	}

	public void setSamlPeerEntityId(String samlPeerEntityId) {
		_samlPeerEntityId = samlPeerEntityId;
	}

	private long _samlPeerBindingId;
	private long _companyId;
	private Date _createDate;
	private long _userId;
	private String _userName;
	private boolean _deleted;
	private String _samlNameIdFormat;
	private String _samlNameIdNameQualifier;
	private String _samlNameIdSpNameQualifier;
	private String _samlNameIdSpProvidedId;
	private String _samlNameIdValue;
	private String _samlPeerEntityId;

}