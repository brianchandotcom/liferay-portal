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

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.util.RemotePreference;
import com.liferay.portal.model.Address;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.Person;
import com.liferay.portal.model.Phone;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author Carlos Sierra Andrés
 */
public class PersonImpl implements Person {

	private final User _user;

	public PersonImpl(User user) {
		_user = user;
	}

	@Override
	public void addRemotePreference(RemotePreference remotePreference) {
		_user.addRemotePreference(remotePreference);
	}

	@Override
	public List<Address> getAddresses() {
		return _user.getAddresses();
	}

	@Override
	public Date getBirthday() throws PortalException {
		return _user.getBirthday();
	}

	@Override
	public String getCompanyMx() throws PortalException {
		return _user.getCompanyMx();
	}

	@Override
	public Contact getContact() throws PortalException {
		return _user.getContact();
	}

	@Override
	public String getDigest(String password) {
		return _user.getDigest(password);
	}

	@Override
	public String getDisplayEmailAddress() {
		return _user.getDisplayEmailAddress();
	}

	@Override
	@Deprecated()
	public String getDisplayURL(String portalURL, String mainPath) throws PortalException {
		return _user.getDisplayURL(portalURL, mainPath);
	}

	@Override
	@Deprecated()
	public String getDisplayURL(String portalURL, String mainPath, boolean privateLayout) throws PortalException {
		return _user.getDisplayURL(portalURL, mainPath, privateLayout);
	}

	@Override
	public String getDisplayURL(ThemeDisplay themeDisplay) throws PortalException {
		return _user.getDisplayURL(themeDisplay);
	}

	@Override
	public String getDisplayURL(ThemeDisplay themeDisplay, boolean privateLayout) throws PortalException {
		return _user.getDisplayURL(themeDisplay, privateLayout);
	}

	@Override
	public List<EmailAddress> getEmailAddresses() {
		return _user.getEmailAddresses();
	}

	@Override
	public boolean getFemale() throws PortalException {
		return _user.getFemale();
	}

	@Override
	@AutoEscape()
	public String getFullName() {
		return _user.getFullName();
	}

	@Override
	public Group getGroup() throws PortalException {
		return _user.getGroup();
	}

	@Override
	public long getGroupId() throws PortalException {
		return _user.getGroupId();
	}

	@Override
	public long[] getGroupIds() {
		return _user.getGroupIds();
	}

	@Override
	public List<Group> getGroups() {
		return _user.getGroups();
	}

	@Override
	public Locale getLocale() {
		return _user.getLocale();
	}

	@Override
	public String getLogin() throws PortalException {
		return _user.getLogin();
	}

	@Override
	public boolean getMale() throws PortalException {
		return _user.getMale();
	}

	@Override
	public List<Group> getMySiteGroups() throws PortalException {
		return _user.getMySiteGroups();
	}

	@Override
	public List<Group> getMySiteGroups(boolean includeControlPanel, int max) throws PortalException {
		return _user.getMySiteGroups(includeControlPanel, max);
	}

	@Override
	public List<Group> getMySiteGroups(int max) throws PortalException {
		return _user.getMySiteGroups(max);
	}

	@Override
	public List<Group> getMySiteGroups(String[] classNames, boolean includeControlPanel, int max) throws PortalException {
		return _user.getMySiteGroups(classNames, includeControlPanel, max);
	}

	@Override
	public List<Group> getMySiteGroups(String[] classNames, int max) throws PortalException {
		return _user.getMySiteGroups(classNames, max);
	}

	@Override
	@Deprecated()
	public List<Group> getMySites() throws PortalException {
		return _user.getMySites();
	}

	@Override
	@Deprecated()
	public List<Group> getMySites(boolean includeControlPanel, int max) throws PortalException {
		return _user.getMySites(includeControlPanel, max);
	}

	@Override
	@Deprecated()
	public List<Group> getMySites(int max) throws PortalException {
		return _user.getMySites(max);
	}

	@Override
	@Deprecated()
	public List<Group> getMySites(String[] classNames, boolean includeControlPanel, int max) throws PortalException {
		return _user.getMySites(classNames, includeControlPanel, max);
	}

	@Override
	@Deprecated()
	public List<Group> getMySites(String[] classNames, int max) throws PortalException {
		return _user.getMySites(classNames, max);
	}

	@Override
	public long[] getOrganizationIds() throws PortalException {
		return _user.getOrganizationIds();
	}

	@Override
	public long[] getOrganizationIds(boolean includeAdministrative) throws PortalException {
		return _user.getOrganizationIds(includeAdministrative);
	}

	@Override
	public List<Organization> getOrganizations() throws PortalException {
		return _user.getOrganizations();
	}

	@Override
	public List<Organization> getOrganizations(boolean includeAdministrative) throws PortalException {
		return _user.getOrganizations(includeAdministrative);
	}

	@Override
	public boolean getPasswordModified() {
		return _user.getPasswordModified();
	}

	@Override
	public PasswordPolicy getPasswordPolicy() throws PortalException {
		return _user.getPasswordPolicy();
	}

	@Override
	public String getPasswordUnencrypted() {
		return _user.getPasswordUnencrypted();
	}

	@Override
	public List<Phone> getPhones() {
		return _user.getPhones();
	}

	@Override
	public String getPortraitURL(ThemeDisplay themeDisplay) throws PortalException {
		return _user.getPortraitURL(themeDisplay);
	}

	@Override
	public int getPrivateLayoutsPageCount() throws PortalException {
		return _user.getPrivateLayoutsPageCount();
	}

	@Override
	public int getPublicLayoutsPageCount() throws PortalException {
		return _user.getPublicLayoutsPageCount();
	}

	@Override
	public Set<String> getReminderQueryQuestions() throws PortalException {
		return _user.getReminderQueryQuestions();
	}

	@Override
	public RemotePreference getRemotePreference(String name) {
		return _user.getRemotePreference(name);
	}

	@Override
	public Iterable<RemotePreference> getRemotePreferences() {
		return _user.getRemotePreferences();
	}

	@Override
	public long[] getRoleIds() {
		return _user.getRoleIds();
	}

	@Override
	public List<Role> getRoles() {
		return _user.getRoles();
	}

	@Override
	public List<Group> getSiteGroups() throws PortalException {
		return _user.getSiteGroups();
	}

	@Override
	public List<Group> getSiteGroups(boolean includeAdministrative) throws PortalException {
		return _user.getSiteGroups(includeAdministrative);
	}

	@Override
	public long[] getTeamIds() {
		return _user.getTeamIds();
	}

	@Override
	public List<Team> getTeams() {
		return _user.getTeams();
	}

	@Override
	public TimeZone getTimeZone() {
		return _user.getTimeZone();
	}

	@Override
	public long[] getUserGroupIds() {
		return _user.getUserGroupIds();
	}

	@Override
	public List<UserGroup> getUserGroups() {
		return _user.getUserGroups();
	}

	@Override
	public List<Website> getWebsites() {
		return _user.getWebsites();
	}

	@Override
	public boolean hasCompanyMx() throws PortalException {
		return _user.hasCompanyMx();
	}

	@Override
	public boolean hasCompanyMx(String emailAddress) throws PortalException {
		return _user.hasCompanyMx(emailAddress);
	}

	@Override
	public boolean hasMySites() throws PortalException {
		return _user.hasMySites();
	}

	@Override
	public boolean hasOrganization() {
		return _user.hasOrganization();
	}

	@Override
	public boolean hasPrivateLayouts() throws PortalException {
		return _user.hasPrivateLayouts();
	}

	@Override
	public boolean hasPublicLayouts() throws PortalException {
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
	public boolean isEmailAddressComplete() {
		return _user.isEmailAddressComplete();
	}

	@Override
	public boolean isEmailAddressVerificationComplete() {
		return _user.isEmailAddressVerificationComplete();
	}

	@Override
	public boolean isFemale() throws PortalException {
		return _user.isFemale();
	}

	@Override
	public boolean isMale() throws PortalException {
		return _user.isMale();
	}

	@Override
	public boolean isPasswordModified() {
		return _user.isPasswordModified();
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
	public void setPasswordModified(boolean passwordModified) {
		_user.setPasswordModified(passwordModified);
	}

	@Override
	public void setPasswordUnencrypted(String passwordUnencrypted) {
		_user.setPasswordUnencrypted(passwordUnencrypted);
	}

	@Override
	public long getPrimaryKey() {
		return _user.getPrimaryKey();
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		_user.setPrimaryKey(primaryKey);
	}

	@Override
	public long getMvccVersion() {
		return _user.getMvccVersion();
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_user.setMvccVersion(mvccVersion);
	}

	@AutoEscape
	@Override
	public String getUuid() {
		return _user.getUuid();
	}

	@Override
	public void setUuid(String uuid) {
		_user.setUuid(uuid);
	}

	@Override
	public long getUserId() {
		return _user.getUserId();
	}

	@Override
	public void setUserId(long userId) {
		_user.setUserId(userId);
	}

	@Override
	public String getUserUuid() {
		return _user.getUserUuid();
	}

	@Override
	public void setUserUuid(String userUuid) {
		_user.setUserUuid(userUuid);
	}

	@Override
	public long getCompanyId() {
		return _user.getCompanyId();
	}

	@Override
	public void setCompanyId(long companyId) {
		_user.setCompanyId(companyId);
	}

	@Override
	public Date getCreateDate() {
		return _user.getCreateDate();
	}

	@Override
	public void setCreateDate(Date createDate) {
		_user.setCreateDate(createDate);
	}

	@Override
	public Date getModifiedDate() {
		return _user.getModifiedDate();
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_user.setModifiedDate(modifiedDate);
	}

	@Override
	public boolean getDefaultUser() {
		return _user.getDefaultUser();
	}

	@Override
	public boolean isDefaultUser() {
		return _user.isDefaultUser();
	}

	@Override
	public void setDefaultUser(boolean defaultUser) {
		_user.setDefaultUser(defaultUser);
	}

	@Override
	public long getContactId() {
		return _user.getContactId();
	}

	@Override
	public void setContactId(long contactId) {
		_user.setContactId(contactId);
	}

	@Override
	@AutoEscape
	public String getPassword() {
		return _user.getPassword();
	}

	@Override
	public void setPassword(String password) {
		_user.setPassword(password);
	}

	@Override
	public boolean getPasswordEncrypted() {
		return _user.getPasswordEncrypted();
	}

	@Override
	public boolean isPasswordEncrypted() {
		return _user.isPasswordEncrypted();
	}

	@Override
	public void setPasswordEncrypted(boolean passwordEncrypted) {
		_user.setPasswordEncrypted(passwordEncrypted);
	}

	@Override
	public boolean getPasswordReset() {
		return _user.getPasswordReset();
	}

	@Override
	public boolean isPasswordReset() {
		return _user.isPasswordReset();
	}

	@Override
	public void setPasswordReset(boolean passwordReset) {
		_user.setPasswordReset(passwordReset);
	}

	@Override
	public Date getPasswordModifiedDate() {
		return _user.getPasswordModifiedDate();
	}

	@Override
	public void setPasswordModifiedDate(Date passwordModifiedDate) {
		_user.setPasswordModifiedDate(passwordModifiedDate);
	}

	@Override
	@AutoEscape
	public String getDigest() {
		return _user.getDigest();
	}

	@Override
	public void setDigest(String digest) {
		_user.setDigest(digest);
	}

	@Override
	@AutoEscape
	public String getReminderQueryQuestion() {
		return _user.getReminderQueryQuestion();
	}

	@Override
	public void setReminderQueryQuestion(String reminderQueryQuestion) {
		_user.setReminderQueryQuestion(reminderQueryQuestion);
	}

	@Override
	@AutoEscape
	public String getReminderQueryAnswer() {
		return _user.getReminderQueryAnswer();
	}

	@Override
	public void setReminderQueryAnswer(String reminderQueryAnswer) {
		_user.setReminderQueryAnswer(reminderQueryAnswer);
	}

	@Override
	public int getGraceLoginCount() {
		return _user.getGraceLoginCount();
	}

	@Override
	public void setGraceLoginCount(int graceLoginCount) {
		_user.setGraceLoginCount(graceLoginCount);
	}

	@Override
	@AutoEscape
	public String getScreenName() {
		return _user.getScreenName();
	}

	@Override
	public void setScreenName(String screenName) {
		_user.setScreenName(screenName);
	}

	@Override
	@AutoEscape
	public String getEmailAddress() {
		return _user.getEmailAddress();
	}

	@Override
	public void setEmailAddress(String emailAddress) {
		_user.setEmailAddress(emailAddress);
	}

	@Override
	public long getFacebookId() {
		return _user.getFacebookId();
	}

	@Override
	public void setFacebookId(long facebookId) {
		_user.setFacebookId(facebookId);
	}

	@Override
	public long getLdapServerId() {
		return _user.getLdapServerId();
	}

	@Override
	public void setLdapServerId(long ldapServerId) {
		_user.setLdapServerId(ldapServerId);
	}

	@Override
	@AutoEscape
	public String getOpenId() {
		return _user.getOpenId();
	}

	@Override
	public void setOpenId(String openId) {
		_user.setOpenId(openId);
	}

	@Override
	public long getPortraitId() {
		return _user.getPortraitId();
	}

	@Override
	public void setPortraitId(long portraitId) {
		_user.setPortraitId(portraitId);
	}

	@Override
	@AutoEscape
	public String getLanguageId() {
		return _user.getLanguageId();
	}

	@Override
	public void setLanguageId(String languageId) {
		_user.setLanguageId(languageId);
	}

	@Override
	@AutoEscape
	public String getTimeZoneId() {
		return _user.getTimeZoneId();
	}

	@Override
	public void setTimeZoneId(String timeZoneId) {
		_user.setTimeZoneId(timeZoneId);
	}

	@Override
	@AutoEscape
	public String getGreeting() {
		return _user.getGreeting();
	}

	@Override
	public void setGreeting(String greeting) {
		_user.setGreeting(greeting);
	}

	@Override
	@AutoEscape
	public String getComments() {
		return _user.getComments();
	}

	@Override
	public void setComments(String comments) {
		_user.setComments(comments);
	}

	@Override
	@AutoEscape
	public String getFirstName() {
		return _user.getFirstName();
	}

	@Override
	public void setFirstName(String firstName) {
		_user.setFirstName(firstName);
	}

	@Override
	@AutoEscape
	public String getMiddleName() {
		return _user.getMiddleName();
	}

	@Override
	public void setMiddleName(String middleName) {
		_user.setMiddleName(middleName);
	}

	@Override
	@AutoEscape
	public String getLastName() {
		return _user.getLastName();
	}

	@Override
	public void setLastName(String lastName) {
		_user.setLastName(lastName);
	}

	@Override
	@AutoEscape
	public String getJobTitle() {
		return _user.getJobTitle();
	}

	@Override
	public void setJobTitle(String jobTitle) {
		_user.setJobTitle(jobTitle);
	}

	@Override
	public Date getLoginDate() {
		return _user.getLoginDate();
	}

	@Override
	public void setLoginDate(Date loginDate) {
		_user.setLoginDate(loginDate);
	}

	@Override
	@AutoEscape
	public String getLoginIP() {
		return _user.getLoginIP();
	}

	@Override
	public void setLoginIP(String loginIP) {
		_user.setLoginIP(loginIP);
	}

	@Override
	public Date getLastLoginDate() {
		return _user.getLastLoginDate();
	}

	@Override
	public void setLastLoginDate(Date lastLoginDate) {
		_user.setLastLoginDate(lastLoginDate);
	}

	@Override
	@AutoEscape
	public String getLastLoginIP() {
		return _user.getLastLoginIP();
	}

	@Override
	public void setLastLoginIP(String lastLoginIP) {
		_user.setLastLoginIP(lastLoginIP);
	}

	@Override
	public Date getLastFailedLoginDate() {
		return _user.getLastFailedLoginDate();
	}

	@Override
	public void setLastFailedLoginDate(Date lastFailedLoginDate) {
		_user.setLastFailedLoginDate(lastFailedLoginDate);
	}

	@Override
	public int getFailedLoginAttempts() {
		return _user.getFailedLoginAttempts();
	}

	@Override
	public void setFailedLoginAttempts(int failedLoginAttempts) {
		_user.setFailedLoginAttempts(failedLoginAttempts);
	}

	@Override
	public boolean getLockout() {
		return _user.getLockout();
	}

	@Override
	public boolean isLockout() {
		return _user.isLockout();
	}

	@Override
	public void setLockout(boolean lockout) {
		_user.setLockout(lockout);
	}

	@Override
	public Date getLockoutDate() {
		return _user.getLockoutDate();
	}

	@Override
	public void setLockoutDate(Date lockoutDate) {
		_user.setLockoutDate(lockoutDate);
	}

	@Override
	public boolean getAgreedToTermsOfUse() {
		return _user.getAgreedToTermsOfUse();
	}

	@Override
	public boolean isAgreedToTermsOfUse() {
		return _user.isAgreedToTermsOfUse();
	}

	@Override
	public void setAgreedToTermsOfUse(boolean agreedToTermsOfUse) {
		_user.setAgreedToTermsOfUse(agreedToTermsOfUse);
	}

	@Override
	public boolean getEmailAddressVerified() {
		return _user.getEmailAddressVerified();
	}

	@Override
	public boolean isEmailAddressVerified() {
		return _user.isEmailAddressVerified();
	}

	@Override
	public void setEmailAddressVerified(boolean emailAddressVerified) {
		_user.setEmailAddressVerified(emailAddressVerified);
	}

	@Override
	public int getStatus() {
		return _user.getStatus();
	}

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
	public Serializable getPrimaryKeyObj() {
		return _user.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_user.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _user.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_user.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_user.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_user.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public Object clone() {
		return _user.clone();
	}

	@Override
	public int compareTo(User user) {
		return _user.compareTo(user);
	}

	@Override
	public int hashCode() {
		return _user.hashCode();
	}

	@Override
	public CacheModel<User> toCacheModel() {
		return _user.toCacheModel();
	}

	@Override
	public User toEscapedModel() {
		return _user.toEscapedModel();
	}

	@Override
	public User toUnescapedModel() {
		return _user.toUnescapedModel();
	}

	@Override
	public String toString() {
		return _user.toString();
	}

	@Override
	public String toXmlString() {
		return _user.toXmlString();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		return _user.getModelAttributes();
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

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		_user.setModelAttributes(attributes);
	}

	@Override
	public Class<?> getModelClass() {
		return _user.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _user.getModelClassName();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _user.getStagedModelType();
	}

	@Override
	public void persist() {
		_user.persist();
	}
}
