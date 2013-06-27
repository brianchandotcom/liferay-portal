/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link User}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see User
 * @generated
 */
public class UserWrapper implements User, ModelWrapper<User> {
	public UserWrapper(User user) {
		_user = user;
	}

	@Override
	public Class<?> getModelClass() {
		return User.class;
	}

	@Override
	public String getModelClassName() {
		return User.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("userId", getUserId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("defaultUser", getDefaultUser());
		attributes.put("contactId", getContactId());
		attributes.put("password", getPassword());
		attributes.put("passwordEncrypted", getPasswordEncrypted());
		attributes.put("passwordReset", getPasswordReset());
		attributes.put("passwordModifiedDate", getPasswordModifiedDate());
		attributes.put("digest", getDigest());
		attributes.put("reminderQueryQuestion", getReminderQueryQuestion());
		attributes.put("reminderQueryAnswer", getReminderQueryAnswer());
		attributes.put("graceLoginCount", getGraceLoginCount());
		attributes.put("screenName", getScreenName());
		attributes.put("emailAddress", getEmailAddress());
		attributes.put("facebookId", getFacebookId());
		attributes.put("ldapServerId", getLdapServerId());
		attributes.put("openId", getOpenId());
		attributes.put("portraitId", getPortraitId());
		attributes.put("languageId", getLanguageId());
		attributes.put("timeZoneId", getTimeZoneId());
		attributes.put("greeting", getGreeting());
		attributes.put("comments", getComments());
		attributes.put("firstName", getFirstName());
		attributes.put("middleName", getMiddleName());
		attributes.put("lastName", getLastName());
		attributes.put("jobTitle", getJobTitle());
		attributes.put("loginDate", getLoginDate());
		attributes.put("loginIP", getLoginIP());
		attributes.put("lastLoginDate", getLastLoginDate());
		attributes.put("lastLoginIP", getLastLoginIP());
		attributes.put("lastFailedLoginDate", getLastFailedLoginDate());
		attributes.put("failedLoginAttempts", getFailedLoginAttempts());
		attributes.put("lockout", getLockout());
		attributes.put("lockoutDate", getLockoutDate());
		attributes.put("agreedToTermsOfUse", getAgreedToTermsOfUse());
		attributes.put("emailAddressVerified", getEmailAddressVerified());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Boolean defaultUser = (Boolean)attributes.get("defaultUser");

		if (defaultUser != null) {
			setDefaultUser(defaultUser);
		}

		Long contactId = (Long)attributes.get("contactId");

		if (contactId != null) {
			setContactId(contactId);
		}

		String password = (String)attributes.get("password");

		if (password != null) {
			setPassword(password);
		}

		Boolean passwordEncrypted = (Boolean)attributes.get("passwordEncrypted");

		if (passwordEncrypted != null) {
			setPasswordEncrypted(passwordEncrypted);
		}

		Boolean passwordReset = (Boolean)attributes.get("passwordReset");

		if (passwordReset != null) {
			setPasswordReset(passwordReset);
		}

		Date passwordModifiedDate = (Date)attributes.get("passwordModifiedDate");

		if (passwordModifiedDate != null) {
			setPasswordModifiedDate(passwordModifiedDate);
		}

		String digest = (String)attributes.get("digest");

		if (digest != null) {
			setDigest(digest);
		}

		String reminderQueryQuestion = (String)attributes.get(
				"reminderQueryQuestion");

		if (reminderQueryQuestion != null) {
			setReminderQueryQuestion(reminderQueryQuestion);
		}

		String reminderQueryAnswer = (String)attributes.get(
				"reminderQueryAnswer");

		if (reminderQueryAnswer != null) {
			setReminderQueryAnswer(reminderQueryAnswer);
		}

		Integer graceLoginCount = (Integer)attributes.get("graceLoginCount");

		if (graceLoginCount != null) {
			setGraceLoginCount(graceLoginCount);
		}

		String screenName = (String)attributes.get("screenName");

		if (screenName != null) {
			setScreenName(screenName);
		}

		String emailAddress = (String)attributes.get("emailAddress");

		if (emailAddress != null) {
			setEmailAddress(emailAddress);
		}

		Long facebookId = (Long)attributes.get("facebookId");

		if (facebookId != null) {
			setFacebookId(facebookId);
		}

		Long ldapServerId = (Long)attributes.get("ldapServerId");

		if (ldapServerId != null) {
			setLdapServerId(ldapServerId);
		}

		String openId = (String)attributes.get("openId");

		if (openId != null) {
			setOpenId(openId);
		}

		Long portraitId = (Long)attributes.get("portraitId");

		if (portraitId != null) {
			setPortraitId(portraitId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String timeZoneId = (String)attributes.get("timeZoneId");

		if (timeZoneId != null) {
			setTimeZoneId(timeZoneId);
		}

		String greeting = (String)attributes.get("greeting");

		if (greeting != null) {
			setGreeting(greeting);
		}

		String comments = (String)attributes.get("comments");

		if (comments != null) {
			setComments(comments);
		}

		String firstName = (String)attributes.get("firstName");

		if (firstName != null) {
			setFirstName(firstName);
		}

		String middleName = (String)attributes.get("middleName");

		if (middleName != null) {
			setMiddleName(middleName);
		}

		String lastName = (String)attributes.get("lastName");

		if (lastName != null) {
			setLastName(lastName);
		}

		String jobTitle = (String)attributes.get("jobTitle");

		if (jobTitle != null) {
			setJobTitle(jobTitle);
		}

		Date loginDate = (Date)attributes.get("loginDate");

		if (loginDate != null) {
			setLoginDate(loginDate);
		}

		String loginIP = (String)attributes.get("loginIP");

		if (loginIP != null) {
			setLoginIP(loginIP);
		}

		Date lastLoginDate = (Date)attributes.get("lastLoginDate");

		if (lastLoginDate != null) {
			setLastLoginDate(lastLoginDate);
		}

		String lastLoginIP = (String)attributes.get("lastLoginIP");

		if (lastLoginIP != null) {
			setLastLoginIP(lastLoginIP);
		}

		Date lastFailedLoginDate = (Date)attributes.get("lastFailedLoginDate");

		if (lastFailedLoginDate != null) {
			setLastFailedLoginDate(lastFailedLoginDate);
		}

		Integer failedLoginAttempts = (Integer)attributes.get(
				"failedLoginAttempts");

		if (failedLoginAttempts != null) {
			setFailedLoginAttempts(failedLoginAttempts);
		}

		Boolean lockout = (Boolean)attributes.get("lockout");

		if (lockout != null) {
			setLockout(lockout);
		}

		Date lockoutDate = (Date)attributes.get("lockoutDate");

		if (lockoutDate != null) {
			setLockoutDate(lockoutDate);
		}

		Boolean agreedToTermsOfUse = (Boolean)attributes.get(
				"agreedToTermsOfUse");

		if (agreedToTermsOfUse != null) {
			setAgreedToTermsOfUse(agreedToTermsOfUse);
		}

		Boolean emailAddressVerified = (Boolean)attributes.get(
				"emailAddressVerified");

		if (emailAddressVerified != null) {
			setEmailAddressVerified(emailAddressVerified);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	* Returns the primary key of this user.
	*
	* @return the primary key of this user
	*/
	@Override
	public long getPrimaryKey() {
		return _user.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user.
	*
	* @param primaryKey the primary key of this user
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_user.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this user.
	*
	* @return the uuid of this user
	*/
	@Override
	public java.lang.String getUuid() {
		return _user.getUuid();
	}

	/**
	* Sets the uuid of this user.
	*
	* @param uuid the uuid of this user
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_user.setUuid(uuid);
	}

	/**
	* Returns the user ID of this user.
	*
	* @return the user ID of this user
	*/
	@Override
	public long getUserId() {
		return _user.getUserId();
	}

	/**
	* Sets the user ID of this user.
	*
	* @param userId the user ID of this user
	*/
	@Override
	public void setUserId(long userId) {
		_user.setUserId(userId);
	}

	/**
	* Returns the user uuid of this user.
	*
	* @return the user uuid of this user
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserUuid();
	}

	/**
	* Sets the user uuid of this user.
	*
	* @param userUuid the user uuid of this user
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_user.setUserUuid(userUuid);
	}

	/**
	* Returns the company ID of this user.
	*
	* @return the company ID of this user
	*/
	@Override
	public long getCompanyId() {
		return _user.getCompanyId();
	}

	/**
	* Sets the company ID of this user.
	*
	* @param companyId the company ID of this user
	*/
	@Override
	public void setCompanyId(long companyId) {
		_user.setCompanyId(companyId);
	}

	/**
	* Returns the create date of this user.
	*
	* @return the create date of this user
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _user.getCreateDate();
	}

	/**
	* Sets the create date of this user.
	*
	* @param createDate the create date of this user
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_user.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this user.
	*
	* @return the modified date of this user
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _user.getModifiedDate();
	}

	/**
	* Sets the modified date of this user.
	*
	* @param modifiedDate the modified date of this user
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_user.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the default user of this user.
	*
	* @return the default user of this user
	*/
	@Override
	public boolean getDefaultUser() {
		return _user.getDefaultUser();
	}

	/**
	* Returns <code>true</code> if this user is default user.
	*
	* @return <code>true</code> if this user is default user; <code>false</code> otherwise
	*/
	@Override
	public boolean isDefaultUser() {
		return _user.isDefaultUser();
	}

	/**
	* Sets whether this user is default user.
	*
	* @param defaultUser the default user of this user
	*/
	@Override
	public void setDefaultUser(boolean defaultUser) {
		_user.setDefaultUser(defaultUser);
	}

	/**
	* Returns the contact ID of this user.
	*
	* @return the contact ID of this user
	*/
	@Override
	public long getContactId() {
		return _user.getContactId();
	}

	/**
	* Sets the contact ID of this user.
	*
	* @param contactId the contact ID of this user
	*/
	@Override
	public void setContactId(long contactId) {
		_user.setContactId(contactId);
	}

	/**
	* Returns the password of this user.
	*
	* @return the password of this user
	*/
	@Override
	public java.lang.String getPassword() {
		return _user.getPassword();
	}

	/**
	* Sets the password of this user.
	*
	* @param password the password of this user
	*/
	@Override
	public void setPassword(java.lang.String password) {
		_user.setPassword(password);
	}

	/**
	* Returns the password encrypted of this user.
	*
	* @return the password encrypted of this user
	*/
	@Override
	public boolean getPasswordEncrypted() {
		return _user.getPasswordEncrypted();
	}

	/**
	* Returns <code>true</code> if this user is password encrypted.
	*
	* @return <code>true</code> if this user is password encrypted; <code>false</code> otherwise
	*/
	@Override
	public boolean isPasswordEncrypted() {
		return _user.isPasswordEncrypted();
	}

	/**
	* Sets whether this user is password encrypted.
	*
	* @param passwordEncrypted the password encrypted of this user
	*/
	@Override
	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_user.setPasswordEncrypted(passwordEncrypted);
	}

	/**
	* Returns the password reset of this user.
	*
	* @return the password reset of this user
	*/
	@Override
	public boolean getPasswordReset() {
		return _user.getPasswordReset();
	}

	/**
	* Returns <code>true</code> if this user is password reset.
	*
	* @return <code>true</code> if this user is password reset; <code>false</code> otherwise
	*/
	@Override
	public boolean isPasswordReset() {
		return _user.isPasswordReset();
	}

	/**
	* Sets whether this user is password reset.
	*
	* @param passwordReset the password reset of this user
	*/
	@Override
	public void setPasswordReset(boolean passwordReset) {
		_user.setPasswordReset(passwordReset);
	}

	/**
	* Returns the password modified date of this user.
	*
	* @return the password modified date of this user
	*/
	@Override
	public java.util.Date getPasswordModifiedDate() {
		return _user.getPasswordModifiedDate();
	}

	/**
	* Sets the password modified date of this user.
	*
	* @param passwordModifiedDate the password modified date of this user
	*/
	@Override
	public void setPasswordModifiedDate(java.util.Date passwordModifiedDate) {
		_user.setPasswordModifiedDate(passwordModifiedDate);
	}

	/**
	* Returns the digest of this user.
	*
	* @return the digest of this user
	*/
	@Override
	public java.lang.String getDigest() {
		return _user.getDigest();
	}

	/**
	* Sets the digest of this user.
	*
	* @param digest the digest of this user
	*/
	@Override
	public void setDigest(java.lang.String digest) {
		_user.setDigest(digest);
	}

	/**
	* Returns the reminder query question of this user.
	*
	* @return the reminder query question of this user
	*/
	@Override
	public java.lang.String getReminderQueryQuestion() {
		return _user.getReminderQueryQuestion();
	}

	/**
	* Sets the reminder query question of this user.
	*
	* @param reminderQueryQuestion the reminder query question of this user
	*/
	@Override
	public void setReminderQueryQuestion(java.lang.String reminderQueryQuestion) {
		_user.setReminderQueryQuestion(reminderQueryQuestion);
	}

	/**
	* Returns the reminder query answer of this user.
	*
	* @return the reminder query answer of this user
	*/
	@Override
	public java.lang.String getReminderQueryAnswer() {
		return _user.getReminderQueryAnswer();
	}

	/**
	* Sets the reminder query answer of this user.
	*
	* @param reminderQueryAnswer the reminder query answer of this user
	*/
	@Override
	public void setReminderQueryAnswer(java.lang.String reminderQueryAnswer) {
		_user.setReminderQueryAnswer(reminderQueryAnswer);
	}

	/**
	* Returns the grace login count of this user.
	*
	* @return the grace login count of this user
	*/
	@Override
	public int getGraceLoginCount() {
		return _user.getGraceLoginCount();
	}

	/**
	* Sets the grace login count of this user.
	*
	* @param graceLoginCount the grace login count of this user
	*/
	@Override
	public void setGraceLoginCount(int graceLoginCount) {
		_user.setGraceLoginCount(graceLoginCount);
	}

	/**
	* Returns the screen name of this user.
	*
	* @return the screen name of this user
	*/
	@Override
	public java.lang.String getScreenName() {
		return _user.getScreenName();
	}

	/**
	* Sets the screen name of this user.
	*
	* @param screenName the screen name of this user
	*/
	@Override
	public void setScreenName(java.lang.String screenName) {
		_user.setScreenName(screenName);
	}

	/**
	* Returns the email address of this user.
	*
	* @return the email address of this user
	*/
	@Override
	public java.lang.String getEmailAddress() {
		return _user.getEmailAddress();
	}

	/**
	* Sets the email address of this user.
	*
	* @param emailAddress the email address of this user
	*/
	@Override
	public void setEmailAddress(java.lang.String emailAddress) {
		_user.setEmailAddress(emailAddress);
	}

	/**
	* Returns the facebook ID of this user.
	*
	* @return the facebook ID of this user
	*/
	@Override
	public long getFacebookId() {
		return _user.getFacebookId();
	}

	/**
	* Sets the facebook ID of this user.
	*
	* @param facebookId the facebook ID of this user
	*/
	@Override
	public void setFacebookId(long facebookId) {
		_user.setFacebookId(facebookId);
	}

	/**
	* Returns the ldap server ID of this user.
	*
	* @return the ldap server ID of this user
	*/
	@Override
	public long getLdapServerId() {
		return _user.getLdapServerId();
	}

	/**
	* Sets the ldap server ID of this user.
	*
	* @param ldapServerId the ldap server ID of this user
	*/
	@Override
	public void setLdapServerId(long ldapServerId) {
		_user.setLdapServerId(ldapServerId);
	}

	/**
	* Returns the open ID of this user.
	*
	* @return the open ID of this user
	*/
	@Override
	public java.lang.String getOpenId() {
		return _user.getOpenId();
	}

	/**
	* Sets the open ID of this user.
	*
	* @param openId the open ID of this user
	*/
	@Override
	public void setOpenId(java.lang.String openId) {
		_user.setOpenId(openId);
	}

	/**
	* Returns the portrait ID of this user.
	*
	* @return the portrait ID of this user
	*/
	@Override
	public long getPortraitId() {
		return _user.getPortraitId();
	}

	/**
	* Sets the portrait ID of this user.
	*
	* @param portraitId the portrait ID of this user
	*/
	@Override
	public void setPortraitId(long portraitId) {
		_user.setPortraitId(portraitId);
	}

	/**
	* Returns the language ID of this user.
	*
	* @return the language ID of this user
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _user.getLanguageId();
	}

	/**
	* Sets the language ID of this user.
	*
	* @param languageId the language ID of this user
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_user.setLanguageId(languageId);
	}

	/**
	* Returns the time zone ID of this user.
	*
	* @return the time zone ID of this user
	*/
	@Override
	public java.lang.String getTimeZoneId() {
		return _user.getTimeZoneId();
	}

	/**
	* Sets the time zone ID of this user.
	*
	* @param timeZoneId the time zone ID of this user
	*/
	@Override
	public void setTimeZoneId(java.lang.String timeZoneId) {
		_user.setTimeZoneId(timeZoneId);
	}

	/**
	* Returns the greeting of this user.
	*
	* @return the greeting of this user
	*/
	@Override
	public java.lang.String getGreeting() {
		return _user.getGreeting();
	}

	/**
	* Sets the greeting of this user.
	*
	* @param greeting the greeting of this user
	*/
	@Override
	public void setGreeting(java.lang.String greeting) {
		_user.setGreeting(greeting);
	}

	/**
	* Returns the comments of this user.
	*
	* @return the comments of this user
	*/
	@Override
	public java.lang.String getComments() {
		return _user.getComments();
	}

	/**
	* Sets the comments of this user.
	*
	* @param comments the comments of this user
	*/
	@Override
	public void setComments(java.lang.String comments) {
		_user.setComments(comments);
	}

	/**
	* Returns the first name of this user.
	*
	* @return the first name of this user
	*/
	@Override
	public java.lang.String getFirstName() {
		return _user.getFirstName();
	}

	/**
	* Sets the first name of this user.
	*
	* @param firstName the first name of this user
	*/
	@Override
	public void setFirstName(java.lang.String firstName) {
		_user.setFirstName(firstName);
	}

	/**
	* Returns the middle name of this user.
	*
	* @return the middle name of this user
	*/
	@Override
	public java.lang.String getMiddleName() {
		return _user.getMiddleName();
	}

	/**
	* Sets the middle name of this user.
	*
	* @param middleName the middle name of this user
	*/
	@Override
	public void setMiddleName(java.lang.String middleName) {
		_user.setMiddleName(middleName);
	}

	/**
	* Returns the last name of this user.
	*
	* @return the last name of this user
	*/
	@Override
	public java.lang.String getLastName() {
		return _user.getLastName();
	}

	/**
	* Sets the last name of this user.
	*
	* @param lastName the last name of this user
	*/
	@Override
	public void setLastName(java.lang.String lastName) {
		_user.setLastName(lastName);
	}

	/**
	* Returns the job title of this user.
	*
	* @return the job title of this user
	*/
	@Override
	public java.lang.String getJobTitle() {
		return _user.getJobTitle();
	}

	/**
	* Sets the job title of this user.
	*
	* @param jobTitle the job title of this user
	*/
	@Override
	public void setJobTitle(java.lang.String jobTitle) {
		_user.setJobTitle(jobTitle);
	}

	/**
	* Returns the login date of this user.
	*
	* @return the login date of this user
	*/
	@Override
	public java.util.Date getLoginDate() {
		return _user.getLoginDate();
	}

	/**
	* Sets the login date of this user.
	*
	* @param loginDate the login date of this user
	*/
	@Override
	public void setLoginDate(java.util.Date loginDate) {
		_user.setLoginDate(loginDate);
	}

	/**
	* Returns the login i p of this user.
	*
	* @return the login i p of this user
	*/
	@Override
	public java.lang.String getLoginIP() {
		return _user.getLoginIP();
	}

	/**
	* Sets the login i p of this user.
	*
	* @param loginIP the login i p of this user
	*/
	@Override
	public void setLoginIP(java.lang.String loginIP) {
		_user.setLoginIP(loginIP);
	}

	/**
	* Returns the last login date of this user.
	*
	* @return the last login date of this user
	*/
	@Override
	public java.util.Date getLastLoginDate() {
		return _user.getLastLoginDate();
	}

	/**
	* Sets the last login date of this user.
	*
	* @param lastLoginDate the last login date of this user
	*/
	@Override
	public void setLastLoginDate(java.util.Date lastLoginDate) {
		_user.setLastLoginDate(lastLoginDate);
	}

	/**
	* Returns the last login i p of this user.
	*
	* @return the last login i p of this user
	*/
	@Override
	public java.lang.String getLastLoginIP() {
		return _user.getLastLoginIP();
	}

	/**
	* Sets the last login i p of this user.
	*
	* @param lastLoginIP the last login i p of this user
	*/
	@Override
	public void setLastLoginIP(java.lang.String lastLoginIP) {
		_user.setLastLoginIP(lastLoginIP);
	}

	/**
	* Returns the last failed login date of this user.
	*
	* @return the last failed login date of this user
	*/
	@Override
	public java.util.Date getLastFailedLoginDate() {
		return _user.getLastFailedLoginDate();
	}

	/**
	* Sets the last failed login date of this user.
	*
	* @param lastFailedLoginDate the last failed login date of this user
	*/
	@Override
	public void setLastFailedLoginDate(java.util.Date lastFailedLoginDate) {
		_user.setLastFailedLoginDate(lastFailedLoginDate);
	}

	/**
	* Returns the failed login attempts of this user.
	*
	* @return the failed login attempts of this user
	*/
	@Override
	public int getFailedLoginAttempts() {
		return _user.getFailedLoginAttempts();
	}

	/**
	* Sets the failed login attempts of this user.
	*
	* @param failedLoginAttempts the failed login attempts of this user
	*/
	@Override
	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_user.setFailedLoginAttempts(failedLoginAttempts);
	}

	/**
	* Returns the lockout of this user.
	*
	* @return the lockout of this user
	*/
	@Override
	public boolean getLockout() {
		return _user.getLockout();
	}

	/**
	* Returns <code>true</code> if this user is lockout.
	*
	* @return <code>true</code> if this user is lockout; <code>false</code> otherwise
	*/
	@Override
	public boolean isLockout() {
		return _user.isLockout();
	}

	/**
	* Sets whether this user is lockout.
	*
	* @param lockout the lockout of this user
	*/
	@Override
	public void setLockout(boolean lockout) {
		_user.setLockout(lockout);
	}

	/**
	* Returns the lockout date of this user.
	*
	* @return the lockout date of this user
	*/
	@Override
	public java.util.Date getLockoutDate() {
		return _user.getLockoutDate();
	}

	/**
	* Sets the lockout date of this user.
	*
	* @param lockoutDate the lockout date of this user
	*/
	@Override
	public void setLockoutDate(java.util.Date lockoutDate) {
		_user.setLockoutDate(lockoutDate);
	}

	/**
	* Returns the agreed to terms of use of this user.
	*
	* @return the agreed to terms of use of this user
	*/
	@Override
	public boolean getAgreedToTermsOfUse() {
		return _user.getAgreedToTermsOfUse();
	}

	/**
	* Returns <code>true</code> if this user is agreed to terms of use.
	*
	* @return <code>true</code> if this user is agreed to terms of use; <code>false</code> otherwise
	*/
	@Override
	public boolean isAgreedToTermsOfUse() {
		return _user.isAgreedToTermsOfUse();
	}

	/**
	* Sets whether this user is agreed to terms of use.
	*
	* @param agreedToTermsOfUse the agreed to terms of use of this user
	*/
	@Override
	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_user.setAgreedToTermsOfUse(agreedToTermsOfUse);
	}

	/**
	* Returns the email address verified of this user.
	*
	* @return the email address verified of this user
	*/
	@Override
	public boolean getEmailAddressVerified() {
		return _user.getEmailAddressVerified();
	}

	/**
	* Returns <code>true</code> if this user is email address verified.
	*
	* @return <code>true</code> if this user is email address verified; <code>false</code> otherwise
	*/
	@Override
	public boolean isEmailAddressVerified() {
		return _user.isEmailAddressVerified();
	}

	/**
	* Sets whether this user is email address verified.
	*
	* @param emailAddressVerified the email address verified of this user
	*/
	@Override
	public void setEmailAddressVerified(boolean emailAddressVerified) {
		_user.setEmailAddressVerified(emailAddressVerified);
	}

	/**
	* Returns the status of this user.
	*
	* @return the status of this user
	*/
	@Override
	public int getStatus() {
		return _user.getStatus();
	}

	/**
	* Sets the status of this user.
	*
	* @param status the status of this user
	*/
	@Override
	public void setStatus(int status) {
		_user.setStatus(status);
	}

	@Override
	public boolean isNew() {
		return _user.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_user.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _user.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_user.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _user.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _user.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_user.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _user.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_user.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_user.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_user.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserWrapper((User)_user.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.User user) {
		return _user.compareTo(user);
	}

	@Override
	public int hashCode() {
		return _user.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.User> toCacheModel() {
		return _user.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.User toEscapedModel() {
		return new UserWrapper(_user.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.User toUnescapedModel() {
		return new UserWrapper(_user.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _user.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _user.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_user.persist();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getAddresses();
	}

	@Override
	public java.util.Date getBirthday()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getBirthday();
	}

	@Override
	public java.lang.String getCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getCompanyMx();
	}

	@Override
	public com.liferay.portal.model.Contact getContact()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getContact();
	}

	@Override
	public java.lang.String getDigest(java.lang.String password) {
		return _user.getDigest(password);
	}

	@Override
	public java.lang.String getDisplayEmailAddress() {
		return _user.getDisplayEmailAddress();
	}

	@Override
	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getDisplayURL(portalURL, mainPath);
	}

	@Override
	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getDisplayURL(portalURL, mainPath, privateLayout);
	}

	@Override
	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getDisplayURL(themeDisplay);
	}

	@Override
	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getDisplayURL(themeDisplay, privateLayout);
	}

	@Override
	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getEmailAddresses();
	}

	@Override
	public boolean getFemale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getFemale();
	}

	@Override
	public java.lang.String getFullName() {
		return _user.getFullName();
	}

	@Override
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroup();
	}

	@Override
	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroupId();
	}

	@Override
	public long[] getGroupIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroupIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroups();
	}

	@Override
	public java.util.Locale getLocale() {
		return _user.getLocale();
	}

	@Override
	public java.lang.String getLogin()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getLogin();
	}

	@Override
	public boolean getMale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMale();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySiteGroups();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups(
		boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySiteGroups(includeControlPanel, max);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups(
		int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySiteGroups(max);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups(
		java.lang.String[] classNames, boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySiteGroups(classNames, includeControlPanel, max);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySiteGroups(classNames, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups}
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySites();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(boolean,
	int)}
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySites(includeControlPanel, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(int)}
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites(int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySites(max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(String[],
	boolean, int)}
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		java.lang.String[] classNames, boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySites(classNames, includeControlPanel, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(String[],
	int)}
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySites(classNames, max);
	}

	@Override
	public long[] getOrganizationIds()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getOrganizationIds();
	}

	@Override
	public long[] getOrganizationIds(boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getOrganizationIds(includeAdministrative);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getOrganizations();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getOrganizations(includeAdministrative);
	}

	@Override
	public boolean getPasswordModified() {
		return _user.getPasswordModified();
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPasswordPolicy();
	}

	@Override
	public java.lang.String getPasswordUnencrypted() {
		return _user.getPasswordUnencrypted();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Phone> getPhones()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getPhones();
	}

	@Override
	public java.lang.String getPortraitURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPortraitURL(themeDisplay);
	}

	@Override
	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPublicLayoutsPageCount();
	}

	@Override
	public java.util.Set<java.lang.String> getReminderQueryQuestions()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getReminderQueryQuestions();
	}

	@Override
	public long[] getRoleIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getRoleIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Role> getRoles()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getRoles();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getSiteGroups()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getSiteGroups();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getSiteGroups(
		boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getSiteGroups(includeAdministrative);
	}

	@Override
	public long[] getTeamIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getTeamIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getTeams()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getTeams();
	}

	@Override
	public java.util.TimeZone getTimeZone() {
		return _user.getTimeZone();
	}

	@Override
	public long[] getUserGroupIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserGroupIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserGroups();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Website> getWebsites()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getWebsites();
	}

	@Override
	public boolean hasCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasCompanyMx();
	}

	@Override
	public boolean hasCompanyMx(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasCompanyMx(emailAddress);
	}

	@Override
	public boolean hasMySites()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasMySites();
	}

	@Override
	public boolean hasOrganization()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasOrganization();
	}

	@Override
	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasPublicLayouts();
	}

	@Override
	public boolean hasReminderQuery() {
		return _user.hasReminderQuery();
	}

	@Override
	public boolean isActive() {
		return _user.isActive();
	}

	@Override
	public boolean isFemale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.isFemale();
	}

	@Override
	public boolean isMale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.isMale();
	}

	@Override
	public boolean isPasswordModified() {
		return _user.isPasswordModified();
	}

	@Override
	public void setPasswordModified(boolean passwordModified) {
		_user.setPasswordModified(passwordModified);
	}

	@Override
	public void setPasswordUnencrypted(java.lang.String passwordUnencrypted) {
		_user.setPasswordUnencrypted(passwordUnencrypted);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserWrapper)) {
			return false;
		}

		UserWrapper userWrapper = (UserWrapper)obj;

		if (Validator.equals(_user, userWrapper._user)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public User getWrappedUser() {
		return _user;
	}

	@Override
	public User getWrappedModel() {
		return _user;
	}

	@Override
	public void resetOriginalValues() {
		_user.resetOriginalValues();
	}

	private User _user;
}