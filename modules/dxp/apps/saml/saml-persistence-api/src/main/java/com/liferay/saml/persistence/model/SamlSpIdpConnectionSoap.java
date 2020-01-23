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
 * @generated
 */
public class SamlSpIdpConnectionSoap implements Serializable {

	public static SamlSpIdpConnectionSoap toSoapModel(
		SamlSpIdpConnection model) {

		SamlSpIdpConnectionSoap soapModel = new SamlSpIdpConnectionSoap();

		soapModel.setSamlSpIdpConnectionId(model.getSamlSpIdpConnectionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSamlIdpEntityId(model.getSamlIdpEntityId());
		soapModel.setAssertionSignatureRequired(
			model.isAssertionSignatureRequired());
		soapModel.setClockSkew(model.getClockSkew());
		soapModel.setEnabled(model.isEnabled());
		soapModel.setForceAuthn(model.isForceAuthn());
		soapModel.setLdapImportEnabled(model.isLdapImportEnabled());
		soapModel.setUnknownUsersAreStrangers(
			model.isUnknownUsersAreStrangers());
		soapModel.setMetadataUrl(model.getMetadataUrl());
		soapModel.setMetadataXml(model.getMetadataXml());
		soapModel.setMetadataUpdatedDate(model.getMetadataUpdatedDate());
		soapModel.setName(model.getName());
		soapModel.setNameIdFormat(model.getNameIdFormat());
		soapModel.setSignAuthnRequest(model.isSignAuthnRequest());
		soapModel.setUserAttributeMappings(model.getUserAttributeMappings());

		return soapModel;
	}

	public static SamlSpIdpConnectionSoap[] toSoapModels(
		SamlSpIdpConnection[] models) {

		SamlSpIdpConnectionSoap[] soapModels =
			new SamlSpIdpConnectionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SamlSpIdpConnectionSoap[][] toSoapModels(
		SamlSpIdpConnection[][] models) {

		SamlSpIdpConnectionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SamlSpIdpConnectionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SamlSpIdpConnectionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SamlSpIdpConnectionSoap[] toSoapModels(
		List<SamlSpIdpConnection> models) {

		List<SamlSpIdpConnectionSoap> soapModels =
			new ArrayList<SamlSpIdpConnectionSoap>(models.size());

		for (SamlSpIdpConnection model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SamlSpIdpConnectionSoap[soapModels.size()]);
	}

	public SamlSpIdpConnectionSoap() {
	}

	public long getPrimaryKey() {
		return _samlSpIdpConnectionId;
	}

	public void setPrimaryKey(long pk) {
		setSamlSpIdpConnectionId(pk);
	}

	public long getSamlSpIdpConnectionId() {
		return _samlSpIdpConnectionId;
	}

	public void setSamlSpIdpConnectionId(long samlSpIdpConnectionId) {
		_samlSpIdpConnectionId = samlSpIdpConnectionId;
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

	public String getSamlIdpEntityId() {
		return _samlIdpEntityId;
	}

	public void setSamlIdpEntityId(String samlIdpEntityId) {
		_samlIdpEntityId = samlIdpEntityId;
	}

	public boolean getAssertionSignatureRequired() {
		return _assertionSignatureRequired;
	}

	public boolean isAssertionSignatureRequired() {
		return _assertionSignatureRequired;
	}

	public void setAssertionSignatureRequired(
		boolean assertionSignatureRequired) {

		_assertionSignatureRequired = assertionSignatureRequired;
	}

	public long getClockSkew() {
		return _clockSkew;
	}

	public void setClockSkew(long clockSkew) {
		_clockSkew = clockSkew;
	}

	public boolean getEnabled() {
		return _enabled;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	public boolean getForceAuthn() {
		return _forceAuthn;
	}

	public boolean isForceAuthn() {
		return _forceAuthn;
	}

	public void setForceAuthn(boolean forceAuthn) {
		_forceAuthn = forceAuthn;
	}

	public boolean getLdapImportEnabled() {
		return _ldapImportEnabled;
	}

	public boolean isLdapImportEnabled() {
		return _ldapImportEnabled;
	}

	public void setLdapImportEnabled(boolean ldapImportEnabled) {
		_ldapImportEnabled = ldapImportEnabled;
	}

	public boolean getUnknownUsersAreStrangers() {
		return _unknownUsersAreStrangers;
	}

	public boolean isUnknownUsersAreStrangers() {
		return _unknownUsersAreStrangers;
	}

	public void setUnknownUsersAreStrangers(boolean unknownUsersAreStrangers) {
		_unknownUsersAreStrangers = unknownUsersAreStrangers;
	}

	public String getMetadataUrl() {
		return _metadataUrl;
	}

	public void setMetadataUrl(String metadataUrl) {
		_metadataUrl = metadataUrl;
	}

	public String getMetadataXml() {
		return _metadataXml;
	}

	public void setMetadataXml(String metadataXml) {
		_metadataXml = metadataXml;
	}

	public Date getMetadataUpdatedDate() {
		return _metadataUpdatedDate;
	}

	public void setMetadataUpdatedDate(Date metadataUpdatedDate) {
		_metadataUpdatedDate = metadataUpdatedDate;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getNameIdFormat() {
		return _nameIdFormat;
	}

	public void setNameIdFormat(String nameIdFormat) {
		_nameIdFormat = nameIdFormat;
	}

	public boolean getSignAuthnRequest() {
		return _signAuthnRequest;
	}

	public boolean isSignAuthnRequest() {
		return _signAuthnRequest;
	}

	public void setSignAuthnRequest(boolean signAuthnRequest) {
		_signAuthnRequest = signAuthnRequest;
	}

	public String getUserAttributeMappings() {
		return _userAttributeMappings;
	}

	public void setUserAttributeMappings(String userAttributeMappings) {
		_userAttributeMappings = userAttributeMappings;
	}

	private long _samlSpIdpConnectionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _samlIdpEntityId;
	private boolean _assertionSignatureRequired;
	private long _clockSkew;
	private boolean _enabled;
	private boolean _forceAuthn;
	private boolean _ldapImportEnabled;
	private boolean _unknownUsersAreStrangers;
	private String _metadataUrl;
	private String _metadataXml;
	private Date _metadataUpdatedDate;
	private String _name;
	private String _nameIdFormat;
	private boolean _signAuthnRequest;
	private String _userAttributeMappings;

}