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

package com.liferay.portal.security.ldap;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.service.ServiceContext;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Edward Han
 * @author Brian Wing Shun Chan
 */
public class LDAPUser {

	public String getAimSn() {
		return _contact.getAimSn();
	}

	public Date getBirthday() {
		return _contact.getBirthday();
	}

	public String getComments() {
		return _user.getComments();
	}

	public Contact getContact() {
		return _contact;
	}

	public Map<String, String[]> getContactExpandoAttributes() {
		return _contactExpandoAttributes;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public String getEmailAddress() {
		return _user.getEmailAddress();
	}

	public long getFacebookId() {
		return _user.getFacebookId();
	}

	public String getFacebookSn() {
		return _contact.getFacebookSn();
	}

	public String getFirstName() {
		return _user.getFirstName();
	}

	public String getGreeting() {
		return _user.getGreeting();
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public String getIcqSn() {
		return _contact.getIcqSn();
	}

	public String getJabberSn() {
		return _contact.getJabberSn();
	}

	public String getJobTitle() {
		return _user.getJobTitle();
	}

	public String getLanguageId() {
		return _user.getLanguageId();
	}

	public String getLastName() {
		return _user.getLastName();
	}

	public Locale getLocale() {
		return _user.getLocale();
	}

	public String getMiddleName() {
		return _user.getMiddleName();
	}

	public String getMsnSn() {
		return _contact.getMsnSn();
	}

	public String getMySpaceSn() {
		return _contact.getMySpaceSn();
	}

	public String getOpenId() {
		return _user.getOpenId();
	}

	public long[] getOrganizationIds() {
		return _organizationIds;
	}

	public byte[] getPortraitBytes() {
		return _portraitBytes;
	}

	public int getPrefixId() {
		return _contact.getPrefixId();
	}

	public String getReminderQueryAnswer() {
		return _user.getReminderQueryAnswer();
	}

	public String getReminderQueryQuestion() {
		return _user.getReminderQueryQuestion();
	}

	public long[] getRoleIds() {
		return _roleIds;
	}

	public String getScreenName() {
		return _user.getScreenName();
	}

	public ServiceContext getServiceContext() {
		return _serviceContext;
	}

	public String getSkypeSn() {
		return _contact.getSkypeSn();
	}

	public String getSmsSn() {
		return _contact.getSmsSn();
	}

	public int getStatus() {
		return _user.getStatus();
	}

	public int getSuffixId() {
		return _contact.getSuffixId();
	}

	public String getTimeZoneId() {
		return _user.getTimeZoneId();
	}

	public String getTwitterSn() {
		return _contact.getTwitterSn();
	}

	public User getUser() {
		return _user;
	}

	public Map<String, String[]> getUserExpandoAttributes() {
		return _userExpandoAttributes;
	}

	public long[] getUserGroupIds() {
		return _userGroupIds;
	}

	public List<UserGroupRole> getUserGroupRoles() {
		return _userGroupRoles;
	}

	public String getYmSn() {
		return _contact.getYmSn();
	}

	public boolean isAutoPassword() {
		return _autoPassword;
	}

	public boolean isAutoScreenName() {
		return _autoScreenName;
	}

	public boolean isMale() {
		return _contact.isMale();
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #setMale(boolean)}
	 */
	public void isMale(boolean male) {
		setMale(male);
	}

	public boolean isPasswordReset() {
		return _passwordReset;
	}

	public boolean isSendEmail() {
		return _sendEmail;
	}

	public boolean isUpdatePassword() {
		return _updatePassword;
	}

	public boolean isUpdatePortrait() {
		return _updatePortrait;
	}

	public void setAimSn(String aimSn) {
		_contact.setAimSn(aimSn);
	}

	public void setAutoPassword(boolean autoPassword) {
		_autoPassword = autoPassword;
	}

	public void setAutoScreenName(boolean autoScreenName) {
		_autoScreenName = autoScreenName;
	}

	public void setBirthday(Date birthday) {
		_contact.setBirthday(birthday);
	}

	public void setComments(String comments) {
		_user.setComments(comments);
	}

	public void setContact(Contact contact) {
		_contact = contact;
	}

	public void setContactExpandoAttributes(
		Map<String, String[]> contactExpandoAttributes) {

		_contactExpandoAttributes = contactExpandoAttributes;
	}

	public void setCreatorUserId(long creatorUserId) {
		_creatorUserId = creatorUserId;
	}

	public void setEmailAddress(String emailAddress) {
		_user.setEmailAddress(emailAddress);
	}

	public void setFacebookId(long facebookId) {
		_user.setFacebookId(facebookId);
	}

	public void setFacebookSn(String facebookSn) {
		_contact.setFacebookSn(facebookSn);
	}

	public void setFirstName(String firstName) {
		_user.setFirstName(firstName);
	}

	public void setGreeting(String greeting) {
		_user.setGreeting(greeting);
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setIcqSn(String icqSn) {
		_contact.setIcqSn(icqSn);
	}

	public void setJabberSn(String jabberSn) {
		_contact.setJabberSn(jabberSn);
	}

	public void setJobTitle(String jobTitle) {
		_user.setJobTitle(jobTitle);
	}

	public void setLanguageId(String languageId) {
		_user.setLanguageId(languageId);
	}

	public void setLastName(String lastName) {
		_user.setLastName(lastName);
	}

	public void setLocale(Locale locale) {
		_user.setLanguageId(LocaleUtil.toLanguageId(locale));
	}

	public void setMale(boolean male) {
		_contact.setMale(male);
	}

	public void setMiddleName(String middleName) {
		_user.setMiddleName(middleName);
	}

	public void setMsnSn(String msnSn) {
		_contact.setMsnSn(msnSn);
	}

	public void setMySpaceSn(String mySpaceSn) {
		_contact.setMySpaceSn(mySpaceSn);
	}

	public void setOpenId(String openId) {
		_user.setOpenId(openId);
	}

	public void setOrganizationIds(long[] organizationIds) {
		_organizationIds = organizationIds;
	}

	public void setPasswordReset(boolean passwordReset) {
		_passwordReset = passwordReset;
	}

	public void setPortraitBytes(byte[] portraitBytes) {
		_portraitBytes = portraitBytes;
	}

	public void setPrefixId(int prefixId) {
		_contact.setPrefixId(prefixId);
	}

	public void setReminderQueryAnswer(String reminderQueryAnswer) {
		_user.setReminderQueryAnswer(reminderQueryAnswer);
	}

	public void setReminderQueryQuestion(String reminderQueryQuestion) {
		_user.setReminderQueryQuestion(reminderQueryQuestion);
	}

	public void setRoleIds(long[] roleIds) {
		_roleIds = roleIds;
	}

	public void setScreenName(String screenName) {
		_user.setScreenName(screenName);
	}

	public void setSendEmail(boolean sendEmail) {
		_sendEmail = sendEmail;
	}

	public void setServiceContext(ServiceContext serviceContext) {
		_serviceContext = serviceContext;
	}

	public void setSkypeSn(String skypeSn) {
		_contact.setSkypeSn(skypeSn);
	}

	public void setSmsSn(String smsSn) {
		_contact.setSmsSn(smsSn);
	}

	public void setStatus(int status) {
		_user.setStatus(status);
	}

	public void setSuffixId(int suffixId) {
		_contact.setSuffixId(suffixId);
	}

	public void setTimeZoneId(String timeZoneId) {
		_user.setTimeZoneId(timeZoneId);
	}

	public void setTwitterSn(String twitterSn) {
		_contact.setTwitterSn(twitterSn);
	}

	public void setUpdatePassword(boolean updatePassword) {
		_updatePassword = updatePassword;
	}

	public void setUpdatePortrait(boolean updatePortrait) {
		_updatePortrait = updatePortrait;
	}

	public void setUser(User user) {
		_user = user;
	}

	public void setUserExpandoAttributes(
		Map<String, String[]> userExpandoAttributes) {

		_userExpandoAttributes = userExpandoAttributes;
	}

	public void setUserGroupIds(long[] userGroupIds) {
		_userGroupIds = userGroupIds;
	}

	public void setUserGroupRoles(List<UserGroupRole> userGroupRoles) {
		_userGroupRoles = userGroupRoles;
	}

	public void setYmSn(String ymSn) {
		_contact.setYmSn(ymSn);
	}

	private boolean _autoPassword;
	private boolean _autoScreenName;
	private Contact _contact;
	private Map<String, String[]> _contactExpandoAttributes;
	private long _creatorUserId;
	private long[] _groupIds;
	private long[] _organizationIds;
	private boolean _passwordReset;
	private byte[] _portraitBytes;
	private long[] _roleIds;
	private boolean _sendEmail;
	private ServiceContext _serviceContext;
	private boolean _updatePassword;
	private boolean _updatePortrait;
	private User _user;
	private Map<String, String[]> _userExpandoAttributes;
	private long[] _userGroupIds;
	private List<UserGroupRole> _userGroupRoles;

}