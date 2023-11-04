/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.rest.internal.model;

import java.util.Date;
import java.util.Locale;

/**
 * @author Rafael Praxedes
 */
public class ScimUser {

	public Date getBirthday() {
		return _birthday;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public String getEmailAddress() {
		return _emailAddress;
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public String getFirstName() {
		return _firstName;
	}

	public long[] getGroupIds() {
		return _groupIds;
	}

	public String getId() {
		return _id;
	}

	public String getJobTitle() {
		return _jobTitle;
	}

	public String getLastName() {
		return _lastName;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getMiddleName() {
		return _middleName;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public long[] getOrganizationIds() {
		return _organizationIds;
	}

	public String getPassword() {
		return _password;
	}

	public long[] getRoleIds() {
		return _roleIds;
	}

	public String getScreenName() {
		return _screenName;
	}

	public long[] getUserGroupIds() {
		return _userGroupIds;
	}

	public boolean isActive() {
		return _active;
	}

	public boolean isAutoPassword() {
		return _autoPassword;
	}

	public boolean isAutoScreenName() {
		return _autoScreenName;
	}

	public boolean isMale() {
		return _male;
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

	public void setActive(boolean active) {
		_active = active;
	}

	public void setAutoPassword(boolean autoPassword) {
		_autoPassword = autoPassword;
	}

	public void setAutoScreenName(boolean autoScreenName) {
		_autoScreenName = autoScreenName;
	}

	public void setBirthday(Date birthday) {
		_birthday = birthday;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public void setExternalReferenceCode(String externalReferenceCode) {
		_externalReferenceCode = externalReferenceCode;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public void setGroupIds(long[] groupIds) {
		_groupIds = groupIds;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setMale(boolean male) {
		_male = male;
	}

	public void setMiddleName(String middleName) {
		_middleName = middleName;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public void setOrganizationIds(long[] organizationIds) {
		_organizationIds = organizationIds;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setPasswordReset(boolean passwordReset) {
		_passwordReset = passwordReset;
	}

	public void setRoleIds(long[] roleIds) {
		_roleIds = roleIds;
	}

	public void setScreenName(String screenName) {
		_screenName = screenName;
	}

	public void setSendEmail(boolean sendEmail) {
		_sendEmail = sendEmail;
	}

	public void setUpdatePassword(boolean updatePassword) {
		_updatePassword = updatePassword;
	}

	public void setUserGroupIds(long[] userGroupIds) {
		_userGroupIds = userGroupIds;
	}

	private boolean _active;
	private boolean _autoPassword;
	private boolean _autoScreenName;
	private Date _birthday;
	private long _companyId;
	private Date _createDate;
	private String _emailAddress;
	private String _externalReferenceCode;
	private String _firstName;
	private long[] _groupIds;
	private String _id;
	private String _jobTitle;
	private String _lastName;
	private Locale _locale;
	private boolean _male;
	private String _middleName;
	private Date _modifiedDate;
	private long[] _organizationIds;
	private String _password;
	private boolean _passwordReset;
	private long[] _roleIds;
	private String _screenName;
	private boolean _sendEmail;
	private boolean _updatePassword;
	private long[] _userGroupIds;

}