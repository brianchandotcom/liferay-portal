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

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.UserServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserSoap implements Serializable {

	public static UserSoap toSoapModel(User model) {
		UserSoap soapModel = new UserSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setExternalReferenceCode(model.getExternalReferenceCode());
		soapModel.setUserId(model.getUserId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDefaultUser(model.isDefaultUser());
		soapModel.setContactId(model.getContactId());
		soapModel.setPassword(model.getPassword());
		soapModel.setPasswordEncrypted(model.isPasswordEncrypted());
		soapModel.setPasswordReset(model.isPasswordReset());
		soapModel.setPasswordModifiedDate(model.getPasswordModifiedDate());
		soapModel.setDigest(model.getDigest());
		soapModel.setReminderQueryQuestion(model.getReminderQueryQuestion());
		soapModel.setReminderQueryAnswer(model.getReminderQueryAnswer());
		soapModel.setGraceLoginCount(model.getGraceLoginCount());
		soapModel.setScreenName(model.getScreenName());
		soapModel.setEmailAddress(model.getEmailAddress());
		soapModel.setFacebookId(model.getFacebookId());
		soapModel.setGoogleUserId(model.getGoogleUserId());
		soapModel.setLdapServerId(model.getLdapServerId());
		soapModel.setOpenId(model.getOpenId());
		soapModel.setPortraitId(model.getPortraitId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setTimeZoneId(model.getTimeZoneId());
		soapModel.setGreeting(model.getGreeting());
		soapModel.setComments(model.getComments());
		soapModel.setFirstName(model.getFirstName());
		soapModel.setMiddleName(model.getMiddleName());
		soapModel.setLastName(model.getLastName());
		soapModel.setJobTitle(model.getJobTitle());
		soapModel.setLoginDate(model.getLoginDate());
		soapModel.setLoginIP(model.getLoginIP());
		soapModel.setLastLoginDate(model.getLastLoginDate());
		soapModel.setLastLoginIP(model.getLastLoginIP());
		soapModel.setLastFailedLoginDate(model.getLastFailedLoginDate());
		soapModel.setFailedLoginAttempts(model.getFailedLoginAttempts());
		soapModel.setLockout(model.isLockout());
		soapModel.setLockoutDate(model.getLockoutDate());
		soapModel.setAgreedToTermsOfUse(model.isAgreedToTermsOfUse());
		soapModel.setEmailAddressVerified(model.isEmailAddressVerified());
		soapModel.setStatus(model.getStatus());

		return soapModel;
	}

	public static UserSoap[] toSoapModels(User[] models) {
		UserSoap[] soapModels = new UserSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static UserSoap[][] toSoapModels(User[][] models) {
		UserSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new UserSoap[models.length][models[0].length];
		}
		else {
			soapModels = new UserSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static UserSoap[] toSoapModels(List<User> models) {
		List<UserSoap> soapModels = new ArrayList<UserSoap>(models.size());

		for (User model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new UserSoap[soapModels.size()]);
	}

	public UserSoap() {
	}

	public long getPrimaryKey() {
		return _userId;
	}

	public void setPrimaryKey(long pk) {
		setUserId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
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

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public boolean getDefaultUser() {
		return _defaultUser;
	}

	public boolean isDefaultUser() {
		return _defaultUser;
	}

	public void setDefaultUser(boolean defaultUser) {
		_defaultUser = defaultUser;
	}

	public long getContactId() {
		return _contactId;
	}

	public void setContactId(long contactId) {
		_contactId = contactId;
	}

	public String getPassword() {
		return _password;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public boolean getPasswordEncrypted() {
		return _passwordEncrypted;
	}

	public boolean isPasswordEncrypted() {
		return _passwordEncrypted;
	}

	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_passwordEncrypted = passwordEncrypted;
	}

	public boolean getPasswordReset() {
		return _passwordReset;
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	public void setPasswordReset(boolean passwordReset) {
		_passwordReset = passwordReset;
	}

	public Date getPasswordModifiedDate() {
		return _passwordModifiedDate;
	}

	public void setPasswordModifiedDate(Date passwordModifiedDate) {
		_passwordModifiedDate = passwordModifiedDate;
	}

	public String getDigest() {
		return _digest;
	}

	public void setDigest(String digest) {
		_digest = digest;
	}

	public String getReminderQueryQuestion() {
		return _reminderQueryQuestion;
	}

	public void setReminderQueryQuestion(String reminderQueryQuestion) {
		_reminderQueryQuestion = reminderQueryQuestion;
	}

	public String getReminderQueryAnswer() {
		return _reminderQueryAnswer;
	}

	public void setReminderQueryAnswer(String reminderQueryAnswer) {
		_reminderQueryAnswer = reminderQueryAnswer;
	}

	public int getGraceLoginCount() {
		return _graceLoginCount;
	}

	public void setGraceLoginCount(int graceLoginCount) {
		_graceLoginCount = graceLoginCount;
	}

	public String getScreenName() {
		return _screenName;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public long getFacebookId() {
		return _facebookId;
	}

	public void setFacebookId(long facebookId) {
		_facebookId = facebookId;
	}

	public String getGoogleUserId() {
		return _googleUserId;
	}

	public void setGoogleUserId(String googleUserId) {
		_googleUserId = googleUserId;
	}

	public long getLdapServerId() {
		return _ldapServerId;
	}

	public void setLdapServerId(long ldapServerId) {
		_ldapServerId = ldapServerId;
	}

	public String getOpenId() {
		return _openId;
	}

	public void setOpenId(String openId) {
		_openId = openId;
	}

	public long getPortraitId() {
		return _portraitId;
	}

	public void setPortraitId(long portraitId) {
		_portraitId = portraitId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getTimeZoneId() {
		return _timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		_timeZoneId = timeZoneId;
	}

	public String getGreeting() {
		return _greeting;
	}

	public void setGreeting(String greeting) {
		_greeting = greeting;
	}

	public String getComments() {
		return _comments;
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public String getMiddleName() {
		return _middleName;
	}

	public void setMiddleName(String middleName) {
		_middleName = middleName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public Date getLoginDate() {
		return _loginDate;
	}

	public void setLoginDate(Date loginDate) {
		_loginDate = loginDate;
	}

	public String getLoginIP() {
		return _loginIP;
	}

	public void setLoginIP(String loginIP) {
		_loginIP = loginIP;
	}

	public Date getLastLoginDate() {
		return _lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		_lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIP() {
		return _lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		_lastLoginIP = lastLoginIP;
	}

	public Date getLastFailedLoginDate() {
		return _lastFailedLoginDate;
	}

	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		_lastFailedLoginDate = lastFailedLoginDate;
	}

	public int getFailedLoginAttempts() {
		return _failedLoginAttempts;
	}

	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_failedLoginAttempts = failedLoginAttempts;
	}

	public boolean getLockout() {
		return _lockout;
	}

	public boolean isLockout() {
		return _lockout;
	}

	public void setLockout(boolean lockout) {
		_lockout = lockout;
	}

	public Date getLockoutDate() {
		return _lockoutDate;
	}

	public void setLockoutDate(Date lockoutDate) {
		_lockoutDate = lockoutDate;
	}

	public boolean getAgreedToTermsOfUse() {
		return _agreedToTermsOfUse;
	}

	public boolean isAgreedToTermsOfUse() {
		return _agreedToTermsOfUse;
	}

	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_agreedToTermsOfUse = agreedToTermsOfUse;
	}

	public boolean getEmailAddressVerified() {
		return _emailAddressVerified;
	}

	public boolean isEmailAddressVerified() {
		return _emailAddressVerified;
	}

	public void setEmailAddressVerified(boolean emailAddressVerified) {
		_emailAddressVerified = emailAddressVerified;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private long _mvccVersion;
	private String _uuid;
	private String _externalReferenceCode;
	private long _userId;
	private long _companyId;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _defaultUser;
	private long _contactId;
	private String _password;
	private boolean _passwordEncrypted;
	private boolean _passwordReset;
	private Date _passwordModifiedDate;
	private String _digest;
	private String _reminderQueryQuestion;
	private String _reminderQueryAnswer;
	private int _graceLoginCount;
	private String _screenName;
	private String _emailAddress;
	private long _facebookId;
	private String _googleUserId;
	private long _ldapServerId;
	private String _openId;
	private long _portraitId;
	private String _languageId;
	private String _timeZoneId;
	private String _greeting;
	private String _comments;
	private String _firstName;
	private String _middleName;
	private String _lastName;
	private String _jobTitle;
	private Date _loginDate;
	private String _loginIP;
	private Date _lastLoginDate;
	private String _lastLoginIP;
	private Date _lastFailedLoginDate;
	private int _failedLoginAttempts;
	private boolean _lockout;
	private Date _lockoutDate;
	private boolean _agreedToTermsOfUse;
	private boolean _emailAddressVerified;
	private int _status;

}