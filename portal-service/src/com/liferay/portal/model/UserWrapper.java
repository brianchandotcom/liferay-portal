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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import com.liferay.portlet.exportimport.lar.StagedModelType;

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
@ProviderType
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

		attributes.put("mvccVersion", getMvccVersion());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

	@Override
	public void addRemotePreference(
		com.liferay.portal.kernel.util.RemotePreference remotePreference) {
		_user.addRemotePreference(remotePreference);
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
	public com.liferay.portal.model.Contact fetchContact() {
		return _user.fetchContact();
	}

	/**
	* Returns the user's addresses.
	*
	* @return the user's addresses
	*/
	@Override
	public java.util.List<com.liferay.portal.model.Address> getAddresses() {
		return _user.getAddresses();
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
	* Returns the user's birth date.
	*
	* @return the user's birth date
	*/
	@Override
	public Date getBirthday()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getBirthday();
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
	* Returns the company ID of this user.
	*
	* @return the company ID of this user
	*/
	@Override
	public long getCompanyId() {
		return _user.getCompanyId();
	}

	/**
	* Returns the user's company's mail domain.
	*
	* @return the user's company's mail domain
	*/
	@Override
	public java.lang.String getCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getCompanyMx();
	}

	/**
	* Returns the user's associated contact.
	*
	* @return the user's associated contact
	* @see Contact
	*/
	@Override
	public com.liferay.portal.model.Contact getContact()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getContact();
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
	* Returns the create date of this user.
	*
	* @return the create date of this user
	*/
	@Override
	public Date getCreateDate() {
		return _user.getCreateDate();
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
	* Returns the digest of this user.
	*
	* @return the digest of this user
	*/
	@Override
	public java.lang.String getDigest() {
		return _user.getDigest();
	}

	/**
	* Returns a digest for the user, incorporating the password.
	*
	* @param password a password to incorporate with the digest
	* @return a digest for the user, incorporating the password
	*/
	@Override
	public java.lang.String getDigest(java.lang.String password) {
		return _user.getDigest(password);
	}

	/**
	* Returns the user's primary email address, or a blank string if the
	* address is fake.
	*
	* @return the user's primary email address, or a blank string if the
	address is fake
	*/
	@Override
	public java.lang.String getDisplayEmailAddress() {
		return _user.getDisplayEmailAddress();
	}

	/**
	* Returns the user's display URL, discounting the URL of the user's default
	* intranet site home page.
	*
	* <p>
	* The logic for the display URL to return is as follows:
	* </p>
	*
	* <ol>
	* <li>
	* If the user is the guest user, return an empty string.
	* </li>
	* <li>
	* Else, if a friendly URL is available for the user's profile, return that
	* friendly URL.
	* </li>
	* <li>
	* Otherwise, return the URL of the user's default extranet site home page.
	* </li>
	* </ol>
	*
	* @param portalURL the portal's URL
	* @param mainPath the main path
	* @return the user's display URL
	* @deprecated As of 7.0.0, replaced by {@link #getDisplayURL(ThemeDisplay)}
	*/
	@Deprecated
	@Override
	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getDisplayURL(portalURL, mainPath);
	}

	/**
	* Returns the user's display URL.
	*
	* <p>
	* The logic for the display URL to return is as follows:
	* </p>
	*
	* <ol>
	* <li>
	* If the user is the guest user, return an empty string.
	* </li>
	* <li>
	* Else, if a friendly URL is available for the user's profile, return that
	* friendly URL.
	* </li>
	* <li>
	* Else, if <code>privateLayout</code> is <code>true</code>, return the URL
	* of the user's default intranet site home page.
	* </li>
	* <li>
	* Otherwise, return the URL of the user's default extranet site home page.
	* </li>
	* </ol>
	*
	* @param portalURL the portal's URL
	* @param mainPath the main path
	* @param privateLayout whether to use the URL of the user's default
	intranet(versus extranet)  site home page, if no friendly URL
	is available for the user's profile
	* @return the user's display URL
	* @throws PortalException
	* @deprecated As of 7.0.0, replaced by {@link #getDisplayURL(ThemeDisplay)}
	*/
	@Deprecated
	@Override
	public java.lang.String getDisplayURL(java.lang.String portalURL,
		java.lang.String mainPath, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getDisplayURL(portalURL, mainPath, privateLayout);
	}

	/**
	* Returns the user's display URL based on the theme display, discounting
	* the URL of the user's default intranet site home page.
	*
	* <p>
	* The logic for the display URL to return is as follows:
	* </p>
	*
	* <ol>
	* <li>
	* If the user is the guest user, return an empty string.
	* </li>
	* <li>
	* Else, if a friendly URL is available for the user's profile, return that
	* friendly URL.
	* </li>
	* <li>
	* Otherwise, return the URL of the user's default extranet site home page.
	* </li>
	* </ol>
	*
	* @param themeDisplay the theme display
	* @return the user's display URL
	*/
	@Override
	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getDisplayURL(themeDisplay);
	}

	/**
	* Returns the user's display URL based on the theme display.
	*
	* <p>
	* The logic for the display URL to return is as follows:
	* </p>
	*
	* <ol>
	* <li>
	* If the user is the guest user, return an empty string.
	* </li>
	* <li>
	* Else, if a friendly URL is available for the user's profile, return that
	* friendly URL.
	* </li>
	* <li>
	* Else, if <code>privateLayout</code> is <code>true</code>, return the URL
	* of the user's default intranet site home page.
	* </li>
	* <li>
	* Otherwise, return the URL of the user's default extranet site home page.
	* </li>
	* </ol>
	*
	* @param themeDisplay the theme display
	* @param privateLayout whether to use the URL of the user's default
	intranet (versus extranet) site home page, if no friendly URL is
	available for the user's profile
	* @return the user's display URL
	* @throws PortalException
	*/
	@Override
	public java.lang.String getDisplayURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay,
		boolean privateLayout)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getDisplayURL(themeDisplay, privateLayout);
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
	* Returns the email address verified of this user.
	*
	* @return the email address verified of this user
	*/
	@Override
	public boolean getEmailAddressVerified() {
		return _user.getEmailAddressVerified();
	}

	/**
	* Returns the user's email addresses.
	*
	* @return the user's email addresses
	*/
	@Override
	public java.util.List<com.liferay.portal.model.EmailAddress> getEmailAddresses() {
		return _user.getEmailAddresses();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _user.getExpandoBridge();
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
	* Returns the failed login attempts of this user.
	*
	* @return the failed login attempts of this user
	*/
	@Override
	public int getFailedLoginAttempts() {
		return _user.getFailedLoginAttempts();
	}

	/**
	* Returns <code>true</code> if the user is female.
	*
	* @return <code>true</code> if the user is female; <code>false</code>
	otherwise
	*/
	@Override
	public boolean getFemale()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getFemale();
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
	* Returns the user's full name.
	*
	* @return the user's full name
	*/
	@Override
	public java.lang.String getFullName() {
		return _user.getFullName();
	}

	/**
	* Returns the user's full name.
	*
	* @return the user's full name
	*/
	@Override
	public java.lang.String getFullName(boolean usePrefix, boolean useSuffix) {
		return _user.getFullName(usePrefix, useSuffix);
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
	* Returns the greeting of this user.
	*
	* @return the greeting of this user
	*/
	@Override
	public java.lang.String getGreeting() {
		return _user.getGreeting();
	}

	@Override
	public com.liferay.portal.model.Group getGroup() {
		return _user.getGroup();
	}

	@Override
	public long getGroupId() {
		return _user.getGroupId();
	}

	@Override
	public long[] getGroupIds() {
		return _user.getGroupIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getGroups() {
		return _user.getGroups();
	}

	@Override
	public java.lang.String getInitials() {
		return _user.getInitials();
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
	* Returns the language ID of this user.
	*
	* @return the language ID of this user
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _user.getLanguageId();
	}

	/**
	* Returns the last failed login date of this user.
	*
	* @return the last failed login date of this user
	*/
	@Override
	public Date getLastFailedLoginDate() {
		return _user.getLastFailedLoginDate();
	}

	/**
	* Returns the last login date of this user.
	*
	* @return the last login date of this user
	*/
	@Override
	public Date getLastLoginDate() {
		return _user.getLastLoginDate();
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
	* Returns the last name of this user.
	*
	* @return the last name of this user
	*/
	@Override
	public java.lang.String getLastName() {
		return _user.getLastName();
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

	@Override
	public java.util.Locale getLocale() {
		return _user.getLocale();
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
	* Returns the lockout date of this user.
	*
	* @return the lockout date of this user
	*/
	@Override
	public Date getLockoutDate() {
		return _user.getLockoutDate();
	}

	@Override
	public java.lang.String getLogin()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getLogin();
	}

	/**
	* Returns the login date of this user.
	*
	* @return the login date of this user
	*/
	@Override
	public Date getLoginDate() {
		return _user.getLoginDate();
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
	* Returns <code>true</code> if the user is male.
	*
	* @return <code>true</code> if the user is male; <code>false</code>
	otherwise
	*/
	@Override
	public boolean getMale()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMale();
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
	* Returns the modified date of this user.
	*
	* @return the modified date of this user
	*/
	@Override
	public Date getModifiedDate() {
		return _user.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this user.
	*
	* @return the mvcc version of this user
	*/
	@Override
	public long getMvccVersion() {
		return _user.getMvccVersion();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMySiteGroups();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMySiteGroups(classNames, max);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySiteGroups(
		int max) throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMySiteGroups(max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMySites();
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(String[],
	int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		java.lang.String[] classNames, boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMySites(classNames, includeControlPanel, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(String[],
	int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		java.lang.String[] classNames, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMySites(classNames, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link User#getMySiteGroups(int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites(
		boolean includeControlPanel, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMySites(includeControlPanel, max);
	}

	/**
	* @deprecated As of 6.2.0, replaced by {@link #getMySiteGroups(int)}
	*/
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.model.Group> getMySites(int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getMySites(max);
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

	@Override
	public long[] getOrganizationIds()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getOrganizationIds();
	}

	@Override
	public long[] getOrganizationIds(boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getOrganizationIds(includeAdministrative);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getOrganizations();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Organization> getOrganizations(
		boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getOrganizations(includeAdministrative);
	}

	@Override
	public java.lang.String getOriginalEmailAddress() {
		return _user.getOriginalEmailAddress();
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
	* Returns the password encrypted of this user.
	*
	* @return the password encrypted of this user
	*/
	@Override
	public boolean getPasswordEncrypted() {
		return _user.getPasswordEncrypted();
	}

	@Override
	public boolean getPasswordModified() {
		return _user.getPasswordModified();
	}

	/**
	* Returns the password modified date of this user.
	*
	* @return the password modified date of this user
	*/
	@Override
	public Date getPasswordModifiedDate() {
		return _user.getPasswordModifiedDate();
	}

	@Override
	public com.liferay.portal.model.PasswordPolicy getPasswordPolicy()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getPasswordPolicy();
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

	@Override
	public java.lang.String getPasswordUnencrypted() {
		return _user.getPasswordUnencrypted();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Phone> getPhones() {
		return _user.getPhones();
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

	@Override
	public java.lang.String getPortraitURL(
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getPortraitURL(themeDisplay);
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

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _user.getPrimaryKeyObj();
	}

	@Override
	public int getPrivateLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getPublicLayoutsPageCount();
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
	* Returns the reminder query question of this user.
	*
	* @return the reminder query question of this user
	*/
	@Override
	public java.lang.String getReminderQueryQuestion() {
		return _user.getReminderQueryQuestion();
	}

	@Override
	public java.util.Set<java.lang.String> getReminderQueryQuestions()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getReminderQueryQuestions();
	}

	@Override
	public com.liferay.portal.kernel.util.RemotePreference getRemotePreference(
		java.lang.String name) {
		return _user.getRemotePreference(name);
	}

	@Override
	public java.lang.Iterable<com.liferay.portal.kernel.util.RemotePreference> getRemotePreferences() {
		return _user.getRemotePreferences();
	}

	@Override
	public long[] getRoleIds() {
		return _user.getRoleIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Role> getRoles() {
		return _user.getRoles();
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

	@Override
	public java.util.List<com.liferay.portal.model.Group> getSiteGroups()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getSiteGroups();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Group> getSiteGroups(
		boolean includeAdministrative)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getSiteGroups(includeAdministrative);
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

	@Override
	public long[] getTeamIds() {
		return _user.getTeamIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Team> getTeams() {
		return _user.getTeams();
	}

	@Override
	public java.util.TimeZone getTimeZone() {
		return _user.getTimeZone();
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

	@Override
	public Date getUnlockDate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.getUnlockDate();
	}

	@Override
	public Date getUnlockDate(
		com.liferay.portal.model.PasswordPolicy passwordPolicy) {
		return _user.getUnlockDate(passwordPolicy);
	}

	@Override
	public long[] getUserGroupIds() {
		return _user.getUserGroupIds();
	}

	@Override
	public java.util.List<com.liferay.portal.model.UserGroup> getUserGroups() {
		return _user.getUserGroups();
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
	* Returns the user uuid of this user.
	*
	* @return the user uuid of this user
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _user.getUserUuid();
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

	@Override
	public java.util.List<com.liferay.portal.model.Website> getWebsites() {
		return _user.getWebsites();
	}

	@Override
	public boolean hasCompanyMx()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.hasCompanyMx();
	}

	@Override
	public boolean hasCompanyMx(java.lang.String emailAddress)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.hasCompanyMx(emailAddress);
	}

	@Override
	public boolean hasMySites()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.hasMySites();
	}

	@Override
	public boolean hasOrganization() {
		return _user.hasOrganization();
	}

	@Override
	public boolean hasPrivateLayouts()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.hasPublicLayouts();
	}

	@Override
	public boolean hasReminderQuery() {
		return _user.hasReminderQuery();
	}

	@Override
	public int hashCode() {
		return _user.hashCode();
	}

	@Override
	public boolean isActive() {
		return _user.isActive();
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

	@Override
	public boolean isCachedModel() {
		return _user.isCachedModel();
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

	@Override
	public boolean isEmailAddressComplete() {
		return _user.isEmailAddressComplete();
	}

	@Override
	public boolean isEmailAddressVerificationComplete() {
		return _user.isEmailAddressVerificationComplete();
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

	@Override
	public boolean isEscapedModel() {
		return _user.isEscapedModel();
	}

	@Override
	public boolean isFemale()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.isFemale();
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

	@Override
	public boolean isMale()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _user.isMale();
	}

	@Override
	public boolean isNew() {
		return _user.isNew();
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

	@Override
	public boolean isPasswordModified() {
		return _user.isPasswordModified();
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

	@Override
	public boolean isReminderQueryComplete() {
		return _user.isReminderQueryComplete();
	}

	@Override
	public boolean isSetupComplete() {
		return _user.isSetupComplete();
	}

	@Override
	public boolean isTermsOfUseComplete() {
		return _user.isTermsOfUseComplete();
	}

	@Override
	public void persist() {
		_user.persist();
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

	@Override
	public void setCachedModel(boolean cachedModel) {
		_user.setCachedModel(cachedModel);
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
	* Sets the company ID of this user.
	*
	* @param companyId the company ID of this user
	*/
	@Override
	public void setCompanyId(long companyId) {
		_user.setCompanyId(companyId);
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
	* Sets the create date of this user.
	*
	* @param createDate the create date of this user
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_user.setCreateDate(createDate);
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
	* Sets the digest of this user.
	*
	* @param digest the digest of this user
	*/
	@Override
	public void setDigest(java.lang.String digest) {
		_user.setDigest(digest);
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
	* Sets whether this user is email address verified.
	*
	* @param emailAddressVerified the email address verified of this user
	*/
	@Override
	public void setEmailAddressVerified(boolean emailAddressVerified) {
		_user.setEmailAddressVerified(emailAddressVerified);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
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
	* Sets the failed login attempts of this user.
	*
	* @param failedLoginAttempts the failed login attempts of this user
	*/
	@Override
	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_user.setFailedLoginAttempts(failedLoginAttempts);
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
	* Sets the grace login count of this user.
	*
	* @param graceLoginCount the grace login count of this user
	*/
	@Override
	public void setGraceLoginCount(int graceLoginCount) {
		_user.setGraceLoginCount(graceLoginCount);
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
	* Sets the job title of this user.
	*
	* @param jobTitle the job title of this user
	*/
	@Override
	public void setJobTitle(java.lang.String jobTitle) {
		_user.setJobTitle(jobTitle);
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
	* Sets the last failed login date of this user.
	*
	* @param lastFailedLoginDate the last failed login date of this user
	*/
	@Override
	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		_user.setLastFailedLoginDate(lastFailedLoginDate);
	}

	/**
	* Sets the last login date of this user.
	*
	* @param lastLoginDate the last login date of this user
	*/
	@Override
	public void setLastLoginDate(Date lastLoginDate) {
		_user.setLastLoginDate(lastLoginDate);
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
	* Sets the last name of this user.
	*
	* @param lastName the last name of this user
	*/
	@Override
	public void setLastName(java.lang.String lastName) {
		_user.setLastName(lastName);
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
	* Sets whether this user is lockout.
	*
	* @param lockout the lockout of this user
	*/
	@Override
	public void setLockout(boolean lockout) {
		_user.setLockout(lockout);
	}

	/**
	* Sets the lockout date of this user.
	*
	* @param lockoutDate the lockout date of this user
	*/
	@Override
	public void setLockoutDate(Date lockoutDate) {
		_user.setLockoutDate(lockoutDate);
	}

	/**
	* Sets the login date of this user.
	*
	* @param loginDate the login date of this user
	*/
	@Override
	public void setLoginDate(Date loginDate) {
		_user.setLoginDate(loginDate);
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
	* Sets the middle name of this user.
	*
	* @param middleName the middle name of this user
	*/
	@Override
	public void setMiddleName(java.lang.String middleName) {
		_user.setMiddleName(middleName);
	}

	/**
	* Sets the modified date of this user.
	*
	* @param modifiedDate the modified date of this user
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_user.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this user.
	*
	* @param mvccVersion the mvcc version of this user
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_user.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_user.setNew(n);
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
	* Sets the password of this user.
	*
	* @param password the password of this user
	*/
	@Override
	public void setPassword(java.lang.String password) {
		_user.setPassword(password);
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

	@Override
	public void setPasswordModified(boolean passwordModified) {
		_user.setPasswordModified(passwordModified);
	}

	/**
	* Sets the password modified date of this user.
	*
	* @param passwordModifiedDate the password modified date of this user
	*/
	@Override
	public void setPasswordModifiedDate(Date passwordModifiedDate) {
		_user.setPasswordModifiedDate(passwordModifiedDate);
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

	@Override
	public void setPasswordUnencrypted(java.lang.String passwordUnencrypted) {
		_user.setPasswordUnencrypted(passwordUnencrypted);
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
	* Sets the primary key of this user.
	*
	* @param primaryKey the primary key of this user
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_user.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_user.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the reminder query question of this user.
	*
	* @param reminderQueryQuestion the reminder query question of this user
	*/
	@Override
	public void setReminderQueryQuestion(java.lang.String reminderQueryQuestion) {
		_user.setReminderQueryQuestion(reminderQueryQuestion);
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
	* Sets the status of this user.
	*
	* @param status the status of this user
	*/
	@Override
	public void setStatus(int status) {
		_user.setStatus(status);
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
	* Sets the user ID of this user.
	*
	* @param userId the user ID of this user
	*/
	@Override
	public void setUserId(long userId) {
		_user.setUserId(userId);
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
	* Sets the uuid of this user.
	*
	* @param uuid the uuid of this user
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_user.setUuid(uuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.User> toCacheModel() {
		return _user.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.User toEscapedModel() {
		return new UserWrapper(_user.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _user.toString();
	}

	@Override
	public com.liferay.portal.model.User toUnescapedModel() {
		return new UserWrapper(_user.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _user.toXmlString();
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

	@Override
	public StagedModelType getStagedModelType() {
		return _user.getStagedModelType();
	}

	@Override
	public User getWrappedModel() {
		return _user;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _user.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _user.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_user.resetOriginalValues();
	}

	private final User _user;
}