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

package com.liferay.multi.factor.authentication.fido2.credential.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Arthur Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class MFAFIDO2CredentialEntrySoap implements Serializable {

	public static MFAFIDO2CredentialEntrySoap toSoapModel(
		MFAFIDO2CredentialEntry model) {

		MFAFIDO2CredentialEntrySoap soapModel =
			new MFAFIDO2CredentialEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setMfaFIDO2CredentialEntryId(
			model.getMfaFIDO2CredentialEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCredentialKey(model.getCredentialKey());
		soapModel.setCredentialType(model.getCredentialType());
		soapModel.setFailedAttempts(model.getFailedAttempts());
		soapModel.setPublicKeyCOSE(model.getPublicKeyCOSE());
		soapModel.setSignatureCount(model.getSignatureCount());

		return soapModel;
	}

	public static MFAFIDO2CredentialEntrySoap[] toSoapModels(
		MFAFIDO2CredentialEntry[] models) {

		MFAFIDO2CredentialEntrySoap[] soapModels =
			new MFAFIDO2CredentialEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MFAFIDO2CredentialEntrySoap[][] toSoapModels(
		MFAFIDO2CredentialEntry[][] models) {

		MFAFIDO2CredentialEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new MFAFIDO2CredentialEntrySoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new MFAFIDO2CredentialEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MFAFIDO2CredentialEntrySoap[] toSoapModels(
		List<MFAFIDO2CredentialEntry> models) {

		List<MFAFIDO2CredentialEntrySoap> soapModels =
			new ArrayList<MFAFIDO2CredentialEntrySoap>(models.size());

		for (MFAFIDO2CredentialEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new MFAFIDO2CredentialEntrySoap[soapModels.size()]);
	}

	public MFAFIDO2CredentialEntrySoap() {
	}

	public long getPrimaryKey() {
		return _mfaFIDO2CredentialEntryId;
	}

	public void setPrimaryKey(long pk) {
		setMfaFIDO2CredentialEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getMfaFIDO2CredentialEntryId() {
		return _mfaFIDO2CredentialEntryId;
	}

	public void setMfaFIDO2CredentialEntryId(long mfaFIDO2CredentialEntryId) {
		_mfaFIDO2CredentialEntryId = mfaFIDO2CredentialEntryId;
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

	public String getCredentialKey() {
		return _credentialKey;
	}

	public void setCredentialKey(String credentialKey) {
		_credentialKey = credentialKey;
	}

	public int getCredentialType() {
		return _credentialType;
	}

	public void setCredentialType(int credentialType) {
		_credentialType = credentialType;
	}

	public int getFailedAttempts() {
		return _failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		_failedAttempts = failedAttempts;
	}

	public String getPublicKeyCOSE() {
		return _publicKeyCOSE;
	}

	public void setPublicKeyCOSE(String publicKeyCOSE) {
		_publicKeyCOSE = publicKeyCOSE;
	}

	public long getSignatureCount() {
		return _signatureCount;
	}

	public void setSignatureCount(long signatureCount) {
		_signatureCount = signatureCount;
	}

	private long _mvccVersion;
	private long _mfaFIDO2CredentialEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _credentialKey;
	private int _credentialType;
	private int _failedAttempts;
	private String _publicKeyCOSE;
	private long _signatureCount;

}