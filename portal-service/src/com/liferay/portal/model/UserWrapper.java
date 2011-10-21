/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link User}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       User
 * @generated
 */
public class UserWrapper implements User, ModelWrapper<User> {
	public UserWrapper(User user) {
		_user = user;
	}

	public Class<?> getModelClass() {
		return User.class;
	}

	public String getModelClassName() {
		return User.class.getName();
	}

	/**
	* Returns the primary key of this user.
	*
	* @return the primary key of this user
	*/
	public long getPrimaryKey() {
		return _user.getPrimaryKey();
	}

	/**
	* Sets the primary key of this user.
	*
	* @param primaryKey the primary key of this user
	*/
	public void setPrimaryKey(long primaryKey) {
		_user.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this user.
	*
	* @return the uuid of this user
	*/
	public java.lang.String getUuid() {
		return _user.getUuid();
	}

	/**
	* Sets the uuid of this user.
	*
	* @param uuid the uuid of this user
	*/
	public void setUuid(java.lang.String uuid) {
		_user.setUuid(uuid);
	}

	/**
	* Returns the user ID of this user.
	*
	* @return the user ID of this user
	*/
	public long getUserId() {
		return _user.getUserId();
	}

	/**
	* Sets the user ID of this user.
	*
	* @param userId the user ID of this user
	*/
	public void setUserId(long userId) {
		_user.setUserId(userId);
	}

	/**
	* Returns the user uuid of this user.
	*
	* @return the user uuid of this user
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserUuid();
	}

	/**
	* Sets the user uuid of this user.
	*
	* @param userUuid the user uuid of this user
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_user.setUserUuid(userUuid);
	}

	/**
	* Returns the company ID of this user.
	*
	* @return the company ID of this user
	*/
	public long getCompanyId() {
		return _user.getCompanyId();
	}

	/**
	* Sets the company ID of this user.
	*
	* @param companyId the company ID of this user
	*/
	public void setCompanyId(long companyId) {
		_user.setCompanyId(companyId);
	}

	/**
	* Returns the create date of this user.
	*
	* @return the create date of this user
	*/
	public java.util.Date getCreateDate() {
		return _user.getCreateDate();
	}

	/**
	* Sets the create date of this user.
	*
	* @param createDate the create date of this user
	*/
	public void setCreateDate(java.util.Date createDate) {
		_user.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this user.
	*
	* @return the modified date of this user
	*/
	public java.util.Date getModifiedDate() {
		return _user.getModifiedDate();
	}

	/**
	* Sets the modified date of this user.
	*
	* @param modifiedDate the modified date of this user
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_user.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the default user of this user.
	*
	* @return the default user of this user
	*/
	public boolean getDefaultUser() {
		return _user.getDefaultUser();
	}

	/**
	* Returns <code>true</code> if this user is default user.
	*
	* @return <code>true</code> if this user is default user; <code>false</code> otherwise
	*/
	public boolean isDefaultUser() {
		return _user.isDefaultUser();
	}

	/**
	* Sets whether this user is default user.
	*
	* @param defaultUser the default user of this user
	*/
	public void setDefaultUser(boolean defaultUser) {
		_user.setDefaultUser(defaultUser);
	}

	/**
	* Returns the contact ID of this user.
	*
	* @return the contact ID of this user
	*/
	public long getContactId() {
		return _user.getContactId();
	}

	/**
	* Sets the contact ID of this user.
	*
	* @param contactId the contact ID of this user
	*/
	public void setContactId(long contactId) {
		_user.setContactId(contactId);
	}

	/**
	* Returns the password of this user.
	*
	* @return the password of this user
	*/
	public java.lang.String getPassword() {
		return _user.getPassword();
	}

	/**
	* Sets the password of this user.
	*
	* @param password the password of this user
	*/
	public void setPassword(java.lang.String password) {
		_user.setPassword(password);
	}

	/**
	* Returns the password encrypted of this user.
	*
	* @return the password encrypted of this user
	*/
	public boolean getPasswordEncrypted() {
		return _user.getPasswordEncrypted();
	}

	/**
	* Returns <code>true</code> if this user is password encrypted.
	*
	* @return <code>true</code> if this user is password encrypted; <code>false</code> otherwise
	*/
	public boolean isPasswordEncrypted() {
		return _user.isPasswordEncrypted();
	}

	/**
	* Sets whether this user is password encrypted.
	*
	* @param passwordEncrypted the password encrypted of this user
	*/
	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_user.setPasswordEncrypted(passwordEncrypted);
	}

	/**
	* Returns the password reset of this user.
	*
	* @return the password reset of this user
	*/
	public boolean getPasswordReset() {
		return _user.getPasswordReset();
	}

	/**
	* Returns <code>true</code> if this user is password reset.
	*
	* @return <code>true</code> if this user is password reset; <code>false</code> otherwise
	*/
	public boolean isPasswordReset() {
		return _user.isPasswordReset();
	}

	/**
	* Sets whether this user is password reset.
	*
	* @param passwordReset the password reset of this user
	*/
	public void setPasswordReset(boolean passwordReset) {
		_user.setPasswordReset(passwordReset);
	}

	/**
	* Returns the password modified date of this user.
	*
	* @return the password modified date of this user
	*/
	public java.util.Date getPasswordModifiedDate() {
		return _user.getPasswordModifiedDate();
	}

	/**
	* Sets the password modified date of this user.
	*
	* @param passwordModifiedDate the password modified date of this user
	*/
	public void setPasswordModifiedDate(java.util.Date passwordModifiedDate) {
		_user.setPasswordModifiedDate(passwordModifiedDate);
	}

	/**
	* Returns the digest of this user.
	*
	* @return the digest of this user
	*/
	public java.lang.String getDigest() {
		return _user.getDigest();
	}

	/**
	* Sets the digest of this user.
	*
	* @param digest the digest of this user
	*/
	public void setDigest(java.lang.String digest) {
		_user.setDigest(digest);
	}

	/**
	* Returns the reminder query question of this user.
	*
	* @return the reminder query question of this user
	*/
	public java.lang.String getReminderQueryQuestion() {
		return _user.getReminderQueryQuestion();
	}

	/**
	* Sets the reminder query question of this user.
	*
	* @param reminderQueryQuestion the reminder query question of this user
	*/
	public void setReminderQueryQuestion(java.lang.String reminderQueryQuestion) {
		_user.setReminderQueryQuestion(reminderQueryQuestion);
	}

	/**
	* Returns the reminder query answer of this user.
	*
	* @return the reminder query answer of this user
	*/
	public java.lang.String getReminderQueryAnswer() {
		return _user.getReminderQueryAnswer();
	}

	/**
	* Sets the reminder query answer of this user.
	*
	* @param reminderQueryAnswer the reminder query answer of this user
	*/
	public void setReminderQueryAnswer(java.lang.String reminderQueryAnswer) {
		_user.setReminderQueryAnswer(reminderQueryAnswer);
	}

	/**
	* Returns the grace login count of this user.
	*
	* @return the grace login count of this user
	*/
	public int getGraceLoginCount() {
		return _user.getGraceLoginCount();
	}

	/**
	* Sets the grace login count of this user.
	*
	* @param graceLoginCount the grace login count of this user
	*/
	public void setGraceLoginCount(int graceLoginCount) {
		_user.setGraceLoginCount(graceLoginCount);
	}

	/**
	* Returns the screen name of this user.
	*
	* @return the screen name of this user
	*/
	public java.lang.String getScreenName() {
		return _user.getScreenName();
	}

	/**
	* Sets the screen name of this user.
	*
	* @param screenName the screen name of this user
	*/
	public void setScreenName(java.lang.String screenName) {
		_user.setScreenName(screenName);
	}

	/**
	* Returns the email address of this user.
	*
	* @return the email address of this user
	*/
	public java.lang.String getEmailAddress() {
		return _user.getEmailAddress();
	}

	/**
	* Sets the email address of this user.
	*
	* @param emailAddress the email address of this user
	*/
	public void setEmailAddress(java.lang.String emailAddress) {
		_user.setEmailAddress(emailAddress);
	}

	/**
	* Returns the facebook ID of this user.
	*
	* @return the facebook ID of this user
	*/
	public long getFacebookId() {
		return _user.getFacebookId();
	}

	/**
	* Sets the facebook ID of this user.
	*
	* @param facebookId the facebook ID of this user
	*/
	public void setFacebookId(long facebookId) {
		_user.setFacebookId(facebookId);
	}

	/**
	* Returns the open ID of this user.
	*
	* @return the open ID of this user
	*/
	public java.lang.String getOpenId() {
		return _user.getOpenId();
	}

	/**
	* Sets the open ID of this user.
	*
	* @param openId the open ID of this user
	*/
	public void setOpenId(java.lang.String openId) {
		_user.setOpenId(openId);
	}

	/**
	* Returns the portrait ID of this user.
	*
	* @return the portrait ID of this user
	*/
	public long getPortraitId() {
		return _user.getPortraitId();
	}

	/**
	* Sets the portrait ID of this user.
	*
	* @param portraitId the portrait ID of this user
	*/
	public void setPortraitId(long portraitId) {
		_user.setPortraitId(portraitId);
	}

	/**
	* Returns the language ID of this user.
	*
	* @return the language ID of this user
	*/
	public java.lang.String getLanguageId() {
		return _user.getLanguageId();
	}

	/**
	* Sets the language ID of this user.
	*
	* @param languageId the language ID of this user
	*/
	public void setLanguageId(java.lang.String languageId) {
		_user.setLanguageId(languageId);
	}

	/**
	* Returns the time zone ID of this user.
	*
	* @return the time zone ID of this user
	*/
	public java.lang.String getTimeZoneId() {
		return _user.getTimeZoneId();
	}

	/**
	* Sets the time zone ID of this user.
	*
	* @param timeZoneId the time zone ID of this user
	*/
	public void setTimeZoneId(java.lang.String timeZoneId) {
		_user.setTimeZoneId(timeZoneId);
	}

	/**
	* Returns the greeting of this user.
	*
	* @return the greeting of this user
	*/
	public java.lang.String getGreeting() {
		return _user.getGreeting();
	}

	/**
	* Sets the greeting of this user.
	*
	* @param greeting the greeting of this user
	*/
	public void setGreeting(java.lang.String greeting) {
		_user.setGreeting(greeting);
	}

	/**
	* Returns the comments of this user.
	*
	* @return the comments of this user
	*/
	public java.lang.String getComments() {
		return _user.getComments();
	}

	/**
	* Sets the comments of this user.
	*
	* @param comments the comments of this user
	*/
	public void setComments(java.lang.String comments) {
		_user.setComments(comments);
	}

	/**
	* Returns the first name of this user.
	*
	* @return the first name of this user
	*/
	public java.lang.String getFirstName() {
		return _user.getFirstName();
	}

	/**
	* Sets the first name of this user.
	*
	* @param firstName the first name of this user
	*/
	public void setFirstName(java.lang.String firstName) {
		_user.setFirstName(firstName);
	}

	/**
	* Returns the middle name of this user.
	*
	* @return the middle name of this user
	*/
	public java.lang.String getMiddleName() {
		return _user.getMiddleName();
	}

	/**
	* Sets the middle name of this user.
	*
	* @param middleName the middle name of this user
	*/
	public void setMiddleName(java.lang.String middleName) {
		_user.setMiddleName(middleName);
	}

	/**
	* Returns the last name of this user.
	*
	* @return the last name of this user
	*/
	public java.lang.String getLastName() {
		return _user.getLastName();
	}

	/**
	* Sets the last name of this user.
	*
	* @param lastName the last name of this user
	*/
	public void setLastName(java.lang.String lastName) {
		_user.setLastName(lastName);
	}

	/**
	* Returns the job title of this user.
	*
	* @return the job title of this user
	*/
	public java.lang.String getJobTitle() {
		return _user.getJobTitle();
	}

	/**
	* Sets the job title of this user.
	*
	* @param jobTitle the job title of this user
	*/
	public void setJobTitle(java.lang.String jobTitle) {
		_user.setJobTitle(jobTitle);
	}

	/**
	* Returns the login date of this user.
	*
	* @return the login date of this user
	*/
	public java.util.Date getLoginDate() {
		return _user.getLoginDate();
	}

	/**
	* Sets the login date of this user.
	*
	* @param loginDate the login date of this user
	*/
	public void setLoginDate(java.util.Date loginDate) {
		_user.setLoginDate(loginDate);
	}

	/**
	* Returns the login i p of this user.
	*
	* @return the login i p of this user
	*/
	public java.lang.String getLoginIP() {
		return _user.getLoginIP();
	}

	/**
	* Sets the login i p of this user.
	*
	* @param loginIP the login i p of this user
	*/
	public void setLoginIP(java.lang.String loginIP) {
		_user.setLoginIP(loginIP);
	}

	/**
	* Returns the last login date of this user.
	*
	* @return the last login date of this user
	*/
	public java.util.Date getLastLoginDate() {
		return _user.getLastLoginDate();
	}

	/**
	* Sets the last login date of this user.
	*
	* @param lastLoginDate the last login date of this user
	*/
	public void setLastLoginDate(java.util.Date lastLoginDate) {
		_user.setLastLoginDate(lastLoginDate);
	}

	/**
	* Returns the last login i p of this user.
	*
	* @return the last login i p of this user
	*/
	public java.lang.String getLastLoginIP() {
		return _user.getLastLoginIP();
	}

	/**
	* Sets the last login i p of this user.
	*
	* @param lastLoginIP the last login i p of this user
	*/
	public void setLastLoginIP(java.lang.String lastLoginIP) {
		_user.setLastLoginIP(lastLoginIP);
	}

	/**
	* Returns the last failed login date of this user.
	*
	* @return the last failed login date of this user
	*/
	public java.util.Date getLastFailedLoginDate() {
		return _user.getLastFailedLoginDate();
	}

	/**
	* Sets the last failed login date of this user.
	*
	* @param lastFailedLoginDate the last failed login date of this user
	*/
	public void setLastFailedLoginDate(java.util.Date lastFailedLoginDate) {
		_user.setLastFailedLoginDate(lastFailedLoginDate);
	}

	/**
	* Returns the failed login attempts of this user.
	*
	* @return the failed login attempts of this user
	*/
	public int getFailedLoginAttempts() {
		return _user.getFailedLoginAttempts();
	}

	/**
	* Sets the failed login attempts of this user.
	*
	* @param failedLoginAttempts the failed login attempts of this user
	*/
	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_user.setFailedLoginAttempts(failedLoginAttempts);
	}

	/**
	* Returns the lockout of this user.
	*
	* @return the lockout of this user
	*/
	public boolean getLockout() {
		return _user.getLockout();
	}

	/**
	* Returns <code>true</code> if this user is lockout.
	*
	* @return <code>true</code> if this user is lockout; <code>false</code> otherwise
	*/
	public boolean isLockout() {
		return _user.isLockout();
	}

	/**
	* Sets whether this user is lockout.
	*
	* @param lockout the lockout of this user
	*/
	public void setLockout(boolean lockout) {
		_user.setLockout(lockout);
	}

	/**
	* Returns the lockout date of this user.
	*
	* @return the lockout date of this user
	*/
	public java.util.Date getLockoutDate() {
		return _user.getLockoutDate();
	}

	/**
	* Sets the lockout date of this user.
	*
	* @param lockoutDate the lockout date of this user
	*/
	public void setLockoutDate(java.util.Date lockoutDate) {
		_user.setLockoutDate(lockoutDate);
	}

	/**
	* Returns the agreed to terms of use of this user.
	*
	* @return the agreed to terms of use of this user
	*/
	public boolean getAgreedToTermsOfUse() {
		return _user.getAgreedToTermsOfUse();
	}

	/**
	* Returns <code>true</code> if this user is agreed to terms of use.
	*
	* @return <code>true</code> if this user is agreed to terms of use; <code>false</code> otherwise
	*/
	public boolean isAgreedToTermsOfUse() {
		return _user.isAgreedToTermsOfUse();
	}

	/**
	* Sets whether this user is agreed to terms of use.
	*
	* @param agreedToTermsOfUse the agreed to terms of use of this user
	*/
	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_user.setAgreedToTermsOfUse(agreedToTermsOfUse);
	}

	/**
	* Returns the email address verified of this user.
	*
	* @return the email address verified of this user
	*/
	public boolean getEmailAddressVerified() {
		return _user.getEmailAddressVerified();
	}

	/**
	* Returns <code>true</code> if this user is email address verified.
	*
	* @return <code>true</code> if this user is email address verified; <code>false</code> otherwise
	*/
	public boolean isEmailAddressVerified() {
		return _user.isEmailAddressVerified();
	}

	/**
	* Sets whether this user is email address verified.
	*
	* @param emailAddressVerified the email address verified of this user
	*/
	public void setEmailAddressVerified(boolean emailAddressVerified) {
		_user.setEmailAddressVerified(emailAddressVerified);
	}

	/**
	* Returns the status of this user.
	*
	* @return the status of this user
	*/
	public int getStatus() {
		return _user.getStatus();
	}

	/**
	* Sets the status of this user.
	*
	* @param status the status of this user
	*/
	public void setStatus(int status) {
		_user.setStatus(status);
	}

	public boolean isNew() {
		return _user.isNew();
	}

	public void setNew(boolean n) {
		_user.setNew(n);
	}

	public boolean isCachedModel() {
		return _user.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_user.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _user.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _user.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_user.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _user.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_user.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserWrapper((User)_user.clone());
	}

	public int compareTo(com.liferay.portal.model.User user) {
		return _user.compareTo(user);
	}

	@Override
	public int hashCode() {
		return _user.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.User> toCacheModel() {
		return _user.toCacheModel();
	}

	public com.liferay.portal.model.User toEscapedModel() {
		return new UserWrapper(_user.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _user.toString();
	}

	public java.lang.String toXmlString() {
		return _user.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_user.persist();
	}

	public java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getAddresses();
	}

	public java.util.Date getBirthday()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getBirthday();
	}

	public java.lang.String getCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getCompanyMx();
	}

	public com.liferay.portal.model.Contact getContact()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getContact();
	}

	public java.lang.String getDigest(java.lang.String password) {
		return _user.getDigest(password);
	}

	public java.lang.String getDisplayEmailAddress() {
		return _user.getDisplayEmailAddress();
	}

	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getDisplayURL(themeDisplay);
	}

	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getDisplayURL(portalURL, mainPath);
	}

	public boolean getFemale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getFemale();
	}

	public java.lang.String getFullName() {
		return _user.getFullName();
	}

	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroup();
	}

	public long getGroupId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroupId();
	}

	public long[] getGroupIds()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroupIds();
	}

	public java.util.List<com.liferay.portal.model.Group> getGroups()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getGroups();
	}

	public java.util.Locale getLocale() {
		return _user.getLocale();
	}

	public java.lang.String getLogin()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getLogin();
	}

	public boolean getMale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMale();
	}

	public java.util.List<com.liferay.portal.model.Group> getMySites()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySites();
	}

	public java.util.List<com.liferay.portal.model.Group> getMySites(int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySites(max);
	}

	public java.util.List<com.liferay.portal.model.Group> getMySites(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getMySites(classNames, max);
	}

	public long[] getOrganizationIds()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getOrganizationIds();
	}

	public java.util.List<com.liferay.portal.model.Organization> getOrganizations()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getOrganizations();
	}

	public boolean getPasswordModified() {
		return _user.getPasswordModified();
	}

	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPasswordPolicy();
	}

	public java.lang.String getPasswordUnencrypted() {
		return _user.getPasswordUnencrypted();
	}

	public java.util.List<com.liferay.portal.model.Phone> getPhones()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getPhones();
	}

	public java.lang.String getPortraitURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPortraitURL(themeDisplay);
	}

	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPrivateLayoutsPageCount();
	}

	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getPublicLayoutsPageCount();
	}

	public java.util.Set<java.lang.String> getReminderQueryQuestions()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.getReminderQueryQuestions();
	}

	public long[] getRoleIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getRoleIds();
	}

	public java.util.List<com.liferay.portal.model.Role> getRoles()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getRoles();
	}

	public double getSocialContributionEquity() {
		return _user.getSocialContributionEquity();
	}

	public double getSocialContributionEquity(long groupId) {
		return _user.getSocialContributionEquity(groupId);
	}

	public double getSocialParticipationEquity() {
		return _user.getSocialParticipationEquity();
	}

	public double getSocialParticipationEquity(long groupId) {
		return _user.getSocialParticipationEquity(groupId);
	}

	public double getSocialPersonalEquity() {
		return _user.getSocialPersonalEquity();
	}

	public double getSocialPersonalEquity(long groupId) {
		return _user.getSocialPersonalEquity(groupId);
	}

	public long[] getTeamIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getTeamIds();
	}

	public java.util.List<com.liferay.portal.model.Team> getTeams()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getTeams();
	}

	public long[] getUserGroupIds()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserGroupIds();
	}

	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getUserGroups();
	}

	public java.util.TimeZone getTimeZone() {
		return _user.getTimeZone();
	}

	public boolean hasCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasCompanyMx();
	}

	public boolean hasCompanyMx(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasCompanyMx(emailAddress);
	}

	public boolean hasMySites()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasMySites();
	}

	public boolean hasOrganization()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasOrganization();
	}

	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasPrivateLayouts();
	}

	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.hasPublicLayouts();
	}

	public boolean hasReminderQuery() {
		return _user.hasReminderQuery();
	}

	public boolean isActive() {
		return _user.isActive();
	}

	public boolean isFemale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.isFemale();
	}

	public boolean isMale()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _user.isMale();
	}

	public boolean isPasswordModified() {
		return _user.isPasswordModified();
	}

	public void setPasswordModified(boolean passwordModified) {
		_user.setPasswordModified(passwordModified);
	}

	public void setPasswordUnencrypted(java.lang.String passwordUnencrypted) {
		_user.setPasswordUnencrypted(passwordUnencrypted);
	}

	public void updateSocialContributionEquity(long groupId, double value) {
		_user.updateSocialContributionEquity(groupId, value);
	}

	public void updateSocialParticipationEquity(long groupId, double value) {
		_user.updateSocialParticipationEquity(groupId, value);
	}

	public java.util.List<com.liferay.portal.model.Website> getWebsites()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _user.getWebsites();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public User getWrappedUser() {
		return _user;
	}

	public User getWrappedModel() {
		return _user;
	}

	public void resetOriginalValues() {
		_user.resetOriginalValues();
	}

	private User _user;
}