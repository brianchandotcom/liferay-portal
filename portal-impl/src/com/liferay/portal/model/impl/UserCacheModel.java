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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.User;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing User in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see User
 * @generated
 */
public class UserCacheModel implements CacheModel<User>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(79);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", defaultUser=");
		sb.append(defaultUser);
		sb.append(", contactId=");
		sb.append(contactId);
		sb.append(", password=");
		sb.append(password);
		sb.append(", passwordEncrypted=");
		sb.append(passwordEncrypted);
		sb.append(", passwordReset=");
		sb.append(passwordReset);
		sb.append(", passwordModifiedDate=");
		sb.append(passwordModifiedDate);
		sb.append(", digest=");
		sb.append(digest);
		sb.append(", reminderQueryQuestion=");
		sb.append(reminderQueryQuestion);
		sb.append(", reminderQueryAnswer=");
		sb.append(reminderQueryAnswer);
		sb.append(", graceLoginCount=");
		sb.append(graceLoginCount);
		sb.append(", screenName=");
		sb.append(screenName);
		sb.append(", emailAddress=");
		sb.append(emailAddress);
		sb.append(", facebookId=");
		sb.append(facebookId);
		sb.append(", openId=");
		sb.append(openId);
		sb.append(", portraitId=");
		sb.append(portraitId);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", timeZoneId=");
		sb.append(timeZoneId);
		sb.append(", greeting=");
		sb.append(greeting);
		sb.append(", comments=");
		sb.append(comments);
		sb.append(", firstName=");
		sb.append(firstName);
		sb.append(", middleName=");
		sb.append(middleName);
		sb.append(", lastName=");
		sb.append(lastName);
		sb.append(", jobTitle=");
		sb.append(jobTitle);
		sb.append(", loginDate=");
		sb.append(loginDate);
		sb.append(", loginIP=");
		sb.append(loginIP);
		sb.append(", lastLoginDate=");
		sb.append(lastLoginDate);
		sb.append(", lastLoginIP=");
		sb.append(lastLoginIP);
		sb.append(", lastFailedLoginDate=");
		sb.append(lastFailedLoginDate);
		sb.append(", failedLoginAttempts=");
		sb.append(failedLoginAttempts);
		sb.append(", lockout=");
		sb.append(lockout);
		sb.append(", lockoutDate=");
		sb.append(lockoutDate);
		sb.append(", agreedToTermsOfUse=");
		sb.append(agreedToTermsOfUse);
		sb.append(", emailAddressVerified=");
		sb.append(emailAddressVerified);
		sb.append(", status=");
		sb.append(status);
		sb.append("}");

		return sb.toString();
	}

	public User toEntityModel() {
		UserImpl userImpl = new UserImpl();

		if (uuid == null) {
			userImpl.setUuid(StringPool.BLANK);
		}
		else {
			userImpl.setUuid(uuid);
		}

		userImpl.setUserId(userId);
		userImpl.setCompanyId(companyId);

		if (createDate == Long.MIN_VALUE) {
			userImpl.setCreateDate(null);
		}
		else {
			userImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			userImpl.setModifiedDate(null);
		}
		else {
			userImpl.setModifiedDate(new Date(modifiedDate));
		}

		userImpl.setDefaultUser(defaultUser);
		userImpl.setContactId(contactId);

		if (password == null) {
			userImpl.setPassword(StringPool.BLANK);
		}
		else {
			userImpl.setPassword(password);
		}

		userImpl.setPasswordEncrypted(passwordEncrypted);
		userImpl.setPasswordReset(passwordReset);

		if (passwordModifiedDate == Long.MIN_VALUE) {
			userImpl.setPasswordModifiedDate(null);
		}
		else {
			userImpl.setPasswordModifiedDate(new Date(passwordModifiedDate));
		}

		if (digest == null) {
			userImpl.setDigest(StringPool.BLANK);
		}
		else {
			userImpl.setDigest(digest);
		}

		if (reminderQueryQuestion == null) {
			userImpl.setReminderQueryQuestion(StringPool.BLANK);
		}
		else {
			userImpl.setReminderQueryQuestion(reminderQueryQuestion);
		}

		if (reminderQueryAnswer == null) {
			userImpl.setReminderQueryAnswer(StringPool.BLANK);
		}
		else {
			userImpl.setReminderQueryAnswer(reminderQueryAnswer);
		}

		userImpl.setGraceLoginCount(graceLoginCount);

		if (screenName == null) {
			userImpl.setScreenName(StringPool.BLANK);
		}
		else {
			userImpl.setScreenName(screenName);
		}

		if (emailAddress == null) {
			userImpl.setEmailAddress(StringPool.BLANK);
		}
		else {
			userImpl.setEmailAddress(emailAddress);
		}

		userImpl.setFacebookId(facebookId);

		if (openId == null) {
			userImpl.setOpenId(StringPool.BLANK);
		}
		else {
			userImpl.setOpenId(openId);
		}

		userImpl.setPortraitId(portraitId);

		if (languageId == null) {
			userImpl.setLanguageId(StringPool.BLANK);
		}
		else {
			userImpl.setLanguageId(languageId);
		}

		if (timeZoneId == null) {
			userImpl.setTimeZoneId(StringPool.BLANK);
		}
		else {
			userImpl.setTimeZoneId(timeZoneId);
		}

		if (greeting == null) {
			userImpl.setGreeting(StringPool.BLANK);
		}
		else {
			userImpl.setGreeting(greeting);
		}

		if (comments == null) {
			userImpl.setComments(StringPool.BLANK);
		}
		else {
			userImpl.setComments(comments);
		}

		if (firstName == null) {
			userImpl.setFirstName(StringPool.BLANK);
		}
		else {
			userImpl.setFirstName(firstName);
		}

		if (middleName == null) {
			userImpl.setMiddleName(StringPool.BLANK);
		}
		else {
			userImpl.setMiddleName(middleName);
		}

		if (lastName == null) {
			userImpl.setLastName(StringPool.BLANK);
		}
		else {
			userImpl.setLastName(lastName);
		}

		if (jobTitle == null) {
			userImpl.setJobTitle(StringPool.BLANK);
		}
		else {
			userImpl.setJobTitle(jobTitle);
		}

		if (loginDate == Long.MIN_VALUE) {
			userImpl.setLoginDate(null);
		}
		else {
			userImpl.setLoginDate(new Date(loginDate));
		}

		if (loginIP == null) {
			userImpl.setLoginIP(StringPool.BLANK);
		}
		else {
			userImpl.setLoginIP(loginIP);
		}

		if (lastLoginDate == Long.MIN_VALUE) {
			userImpl.setLastLoginDate(null);
		}
		else {
			userImpl.setLastLoginDate(new Date(lastLoginDate));
		}

		if (lastLoginIP == null) {
			userImpl.setLastLoginIP(StringPool.BLANK);
		}
		else {
			userImpl.setLastLoginIP(lastLoginIP);
		}

		if (lastFailedLoginDate == Long.MIN_VALUE) {
			userImpl.setLastFailedLoginDate(null);
		}
		else {
			userImpl.setLastFailedLoginDate(new Date(lastFailedLoginDate));
		}

		userImpl.setFailedLoginAttempts(failedLoginAttempts);
		userImpl.setLockout(lockout);

		if (lockoutDate == Long.MIN_VALUE) {
			userImpl.setLockoutDate(null);
		}
		else {
			userImpl.setLockoutDate(new Date(lockoutDate));
		}

		userImpl.setAgreedToTermsOfUse(agreedToTermsOfUse);
		userImpl.setEmailAddressVerified(emailAddressVerified);
		userImpl.setStatus(status);

		userImpl.resetOriginalValues();

		return userImpl;
	}

	public String uuid;
	public long userId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
	public boolean defaultUser;
	public long contactId;
	public String password;
	public boolean passwordEncrypted;
	public boolean passwordReset;
	public long passwordModifiedDate;
	public String digest;
	public String reminderQueryQuestion;
	public String reminderQueryAnswer;
	public int graceLoginCount;
	public String screenName;
	public String emailAddress;
	public long facebookId;
	public String openId;
	public long portraitId;
	public String languageId;
	public String timeZoneId;
	public String greeting;
	public String comments;
	public String firstName;
	public String middleName;
	public String lastName;
	public String jobTitle;
	public long loginDate;
	public String loginIP;
	public long lastLoginDate;
	public String lastLoginIP;
	public long lastFailedLoginDate;
	public int failedLoginAttempts;
	public boolean lockout;
	public long lockoutDate;
	public boolean agreedToTermsOfUse;
	public boolean emailAddressVerified;
	public int status;
}