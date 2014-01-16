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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.User;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing User in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see User
 * @generated
 */
public class UserCacheModel implements CacheModel<User>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(83);

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
		sb.append(", ldapServerId=");
		sb.append(ldapServerId);
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
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
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
		userImpl.setLdapServerId(ldapServerId);

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
		userImpl.setMvccVersion(mvccVersion);

		userImpl.resetOriginalValues();

		return userImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();
		userId = objectInput.readLong();
		companyId = objectInput.readLong();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		defaultUser = objectInput.readBoolean();
		contactId = objectInput.readLong();
		password = objectInput.readUTF();
		passwordEncrypted = objectInput.readBoolean();
		passwordReset = objectInput.readBoolean();
		passwordModifiedDate = objectInput.readLong();
		digest = objectInput.readUTF();
		reminderQueryQuestion = objectInput.readUTF();
		reminderQueryAnswer = objectInput.readUTF();
		graceLoginCount = objectInput.readInt();
		screenName = objectInput.readUTF();
		emailAddress = objectInput.readUTF();
		facebookId = objectInput.readLong();
		ldapServerId = objectInput.readLong();
		openId = objectInput.readUTF();
		portraitId = objectInput.readLong();
		languageId = objectInput.readUTF();
		timeZoneId = objectInput.readUTF();
		greeting = objectInput.readUTF();
		comments = objectInput.readUTF();
		firstName = objectInput.readUTF();
		middleName = objectInput.readUTF();
		lastName = objectInput.readUTF();
		jobTitle = objectInput.readUTF();
		loginDate = objectInput.readLong();
		loginIP = objectInput.readUTF();
		lastLoginDate = objectInput.readLong();
		lastLoginIP = objectInput.readUTF();
		lastFailedLoginDate = objectInput.readLong();
		failedLoginAttempts = objectInput.readInt();
		lockout = objectInput.readBoolean();
		lockoutDate = objectInput.readLong();
		agreedToTermsOfUse = objectInput.readBoolean();
		emailAddressVerified = objectInput.readBoolean();
		status = objectInput.readInt();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(userId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeBoolean(defaultUser);
		objectOutput.writeLong(contactId);

		if (password == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(password);
		}

		objectOutput.writeBoolean(passwordEncrypted);
		objectOutput.writeBoolean(passwordReset);
		objectOutput.writeLong(passwordModifiedDate);

		if (digest == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(digest);
		}

		if (reminderQueryQuestion == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(reminderQueryQuestion);
		}

		if (reminderQueryAnswer == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(reminderQueryAnswer);
		}

		objectOutput.writeInt(graceLoginCount);

		if (screenName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(screenName);
		}

		if (emailAddress == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(emailAddress);
		}

		objectOutput.writeLong(facebookId);
		objectOutput.writeLong(ldapServerId);

		if (openId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(openId);
		}

		objectOutput.writeLong(portraitId);

		if (languageId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		if (timeZoneId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(timeZoneId);
		}

		if (greeting == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(greeting);
		}

		if (comments == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(comments);
		}

		if (firstName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(firstName);
		}

		if (middleName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(middleName);
		}

		if (lastName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(lastName);
		}

		if (jobTitle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(jobTitle);
		}

		objectOutput.writeLong(loginDate);

		if (loginIP == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(loginIP);
		}

		objectOutput.writeLong(lastLoginDate);

		if (lastLoginIP == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(lastLoginIP);
		}

		objectOutput.writeLong(lastFailedLoginDate);
		objectOutput.writeInt(failedLoginAttempts);
		objectOutput.writeBoolean(lockout);
		objectOutput.writeLong(lockoutDate);
		objectOutput.writeBoolean(agreedToTermsOfUse);
		objectOutput.writeBoolean(emailAddressVerified);
		objectOutput.writeInt(status);
		objectOutput.writeLong(mvccVersion);
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
	public long ldapServerId;
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
	public long mvccVersion;
}