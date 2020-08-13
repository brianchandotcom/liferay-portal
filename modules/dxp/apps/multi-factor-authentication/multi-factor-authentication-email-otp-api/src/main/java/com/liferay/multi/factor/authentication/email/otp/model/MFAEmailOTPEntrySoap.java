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

package com.liferay.multi.factor.authentication.email.otp.model;

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
public class MFAEmailOTPEntrySoap implements Serializable {

	public static MFAEmailOTPEntrySoap toSoapModel(MFAEmailOTPEntry model) {
		MFAEmailOTPEntrySoap soapModel = new MFAEmailOTPEntrySoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setMfaEmailOTPEntryId(model.getMfaEmailOTPEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFailedAttempts(model.getFailedAttempts());
		soapModel.setLastFailDate(model.getLastFailDate());
		soapModel.setLastFailIP(model.getLastFailIP());
		soapModel.setLastSuccessDate(model.getLastSuccessDate());
		soapModel.setLastSuccessIP(model.getLastSuccessIP());

		return soapModel;
	}

	public static MFAEmailOTPEntrySoap[] toSoapModels(
		MFAEmailOTPEntry[] models) {

		MFAEmailOTPEntrySoap[] soapModels =
			new MFAEmailOTPEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MFAEmailOTPEntrySoap[][] toSoapModels(
		MFAEmailOTPEntry[][] models) {

		MFAEmailOTPEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new MFAEmailOTPEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new MFAEmailOTPEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MFAEmailOTPEntrySoap[] toSoapModels(
		List<MFAEmailOTPEntry> models) {

		List<MFAEmailOTPEntrySoap> soapModels =
			new ArrayList<MFAEmailOTPEntrySoap>(models.size());

		for (MFAEmailOTPEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MFAEmailOTPEntrySoap[soapModels.size()]);
	}

	public MFAEmailOTPEntrySoap() {
	}

	public long getPrimaryKey() {
		return _mfaEmailOTPEntryId;
	}

	public void setPrimaryKey(long pk) {
		setMfaEmailOTPEntryId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getMfaEmailOTPEntryId() {
		return _mfaEmailOTPEntryId;
	}

	public void setMfaEmailOTPEntryId(long mfaEmailOTPEntryId) {
		_mfaEmailOTPEntryId = mfaEmailOTPEntryId;
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

	public int getFailedAttempts() {
		return _failedAttempts;
	}

	public void setFailedAttempts(int failedAttempts) {
		_failedAttempts = failedAttempts;
	}

	public Date getLastFailDate() {
		return _lastFailDate;
	}

	public void setLastFailDate(Date lastFailDate) {
		_lastFailDate = lastFailDate;
	}

	public String getLastFailIP() {
		return _lastFailIP;
	}

	public void setLastFailIP(String lastFailIP) {
		_lastFailIP = lastFailIP;
	}

	public Date getLastSuccessDate() {
		return _lastSuccessDate;
	}

	public void setLastSuccessDate(Date lastSuccessDate) {
		_lastSuccessDate = lastSuccessDate;
	}

	public String getLastSuccessIP() {
		return _lastSuccessIP;
	}

	public void setLastSuccessIP(String lastSuccessIP) {
		_lastSuccessIP = lastSuccessIP;
	}

	private long _mvccVersion;
	private long _mfaEmailOTPEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private int _failedAttempts;
	private Date _lastFailDate;
	private String _lastFailIP;
	private Date _lastSuccessDate;
	private String _lastSuccessIP;

}