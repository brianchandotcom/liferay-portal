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
import com.liferay.portal.model.Contact;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Contact in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Contact
 * @generated
 */
public class ContactCacheModel implements CacheModel<Contact>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(69);

		sb.append("{contactId=");
		sb.append(contactId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", accountId=");
		sb.append(accountId);
		sb.append(", parentContactId=");
		sb.append(parentContactId);
		sb.append(", emailAddress=");
		sb.append(emailAddress);
		sb.append(", firstName=");
		sb.append(firstName);
		sb.append(", middleName=");
		sb.append(middleName);
		sb.append(", lastName=");
		sb.append(lastName);
		sb.append(", prefixId=");
		sb.append(prefixId);
		sb.append(", suffixId=");
		sb.append(suffixId);
		sb.append(", male=");
		sb.append(male);
		sb.append(", birthday=");
		sb.append(birthday);
		sb.append(", smsSn=");
		sb.append(smsSn);
		sb.append(", aimSn=");
		sb.append(aimSn);
		sb.append(", facebookSn=");
		sb.append(facebookSn);
		sb.append(", icqSn=");
		sb.append(icqSn);
		sb.append(", jabberSn=");
		sb.append(jabberSn);
		sb.append(", msnSn=");
		sb.append(msnSn);
		sb.append(", mySpaceSn=");
		sb.append(mySpaceSn);
		sb.append(", skypeSn=");
		sb.append(skypeSn);
		sb.append(", twitterSn=");
		sb.append(twitterSn);
		sb.append(", ymSn=");
		sb.append(ymSn);
		sb.append(", employeeStatusId=");
		sb.append(employeeStatusId);
		sb.append(", employeeNumber=");
		sb.append(employeeNumber);
		sb.append(", jobTitle=");
		sb.append(jobTitle);
		sb.append(", jobClass=");
		sb.append(jobClass);
		sb.append(", hoursOfOperation=");
		sb.append(hoursOfOperation);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Contact toEntityModel() {
		ContactImpl contactImpl = new ContactImpl();

		contactImpl.setContactId(contactId);
		contactImpl.setCompanyId(companyId);
		contactImpl.setUserId(userId);

		if (userName == null) {
			contactImpl.setUserName(StringPool.BLANK);
		}
		else {
			contactImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			contactImpl.setCreateDate(null);
		}
		else {
			contactImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			contactImpl.setModifiedDate(null);
		}
		else {
			contactImpl.setModifiedDate(new Date(modifiedDate));
		}

		contactImpl.setClassNameId(classNameId);
		contactImpl.setClassPK(classPK);
		contactImpl.setAccountId(accountId);
		contactImpl.setParentContactId(parentContactId);

		if (emailAddress == null) {
			contactImpl.setEmailAddress(StringPool.BLANK);
		}
		else {
			contactImpl.setEmailAddress(emailAddress);
		}

		if (firstName == null) {
			contactImpl.setFirstName(StringPool.BLANK);
		}
		else {
			contactImpl.setFirstName(firstName);
		}

		if (middleName == null) {
			contactImpl.setMiddleName(StringPool.BLANK);
		}
		else {
			contactImpl.setMiddleName(middleName);
		}

		if (lastName == null) {
			contactImpl.setLastName(StringPool.BLANK);
		}
		else {
			contactImpl.setLastName(lastName);
		}

		contactImpl.setPrefixId(prefixId);
		contactImpl.setSuffixId(suffixId);
		contactImpl.setMale(male);

		if (birthday == Long.MIN_VALUE) {
			contactImpl.setBirthday(null);
		}
		else {
			contactImpl.setBirthday(new Date(birthday));
		}

		if (smsSn == null) {
			contactImpl.setSmsSn(StringPool.BLANK);
		}
		else {
			contactImpl.setSmsSn(smsSn);
		}

		if (aimSn == null) {
			contactImpl.setAimSn(StringPool.BLANK);
		}
		else {
			contactImpl.setAimSn(aimSn);
		}

		if (facebookSn == null) {
			contactImpl.setFacebookSn(StringPool.BLANK);
		}
		else {
			contactImpl.setFacebookSn(facebookSn);
		}

		if (icqSn == null) {
			contactImpl.setIcqSn(StringPool.BLANK);
		}
		else {
			contactImpl.setIcqSn(icqSn);
		}

		if (jabberSn == null) {
			contactImpl.setJabberSn(StringPool.BLANK);
		}
		else {
			contactImpl.setJabberSn(jabberSn);
		}

		if (msnSn == null) {
			contactImpl.setMsnSn(StringPool.BLANK);
		}
		else {
			contactImpl.setMsnSn(msnSn);
		}

		if (mySpaceSn == null) {
			contactImpl.setMySpaceSn(StringPool.BLANK);
		}
		else {
			contactImpl.setMySpaceSn(mySpaceSn);
		}

		if (skypeSn == null) {
			contactImpl.setSkypeSn(StringPool.BLANK);
		}
		else {
			contactImpl.setSkypeSn(skypeSn);
		}

		if (twitterSn == null) {
			contactImpl.setTwitterSn(StringPool.BLANK);
		}
		else {
			contactImpl.setTwitterSn(twitterSn);
		}

		if (ymSn == null) {
			contactImpl.setYmSn(StringPool.BLANK);
		}
		else {
			contactImpl.setYmSn(ymSn);
		}

		if (employeeStatusId == null) {
			contactImpl.setEmployeeStatusId(StringPool.BLANK);
		}
		else {
			contactImpl.setEmployeeStatusId(employeeStatusId);
		}

		if (employeeNumber == null) {
			contactImpl.setEmployeeNumber(StringPool.BLANK);
		}
		else {
			contactImpl.setEmployeeNumber(employeeNumber);
		}

		if (jobTitle == null) {
			contactImpl.setJobTitle(StringPool.BLANK);
		}
		else {
			contactImpl.setJobTitle(jobTitle);
		}

		if (jobClass == null) {
			contactImpl.setJobClass(StringPool.BLANK);
		}
		else {
			contactImpl.setJobClass(jobClass);
		}

		if (hoursOfOperation == null) {
			contactImpl.setHoursOfOperation(StringPool.BLANK);
		}
		else {
			contactImpl.setHoursOfOperation(hoursOfOperation);
		}

		contactImpl.setMvccVersion(mvccVersion);

		contactImpl.resetOriginalValues();

		return contactImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		contactId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
		accountId = objectInput.readLong();
		parentContactId = objectInput.readLong();
		emailAddress = objectInput.readUTF();
		firstName = objectInput.readUTF();
		middleName = objectInput.readUTF();
		lastName = objectInput.readUTF();
		prefixId = objectInput.readInt();
		suffixId = objectInput.readInt();
		male = objectInput.readBoolean();
		birthday = objectInput.readLong();
		smsSn = objectInput.readUTF();
		aimSn = objectInput.readUTF();
		facebookSn = objectInput.readUTF();
		icqSn = objectInput.readUTF();
		jabberSn = objectInput.readUTF();
		msnSn = objectInput.readUTF();
		mySpaceSn = objectInput.readUTF();
		skypeSn = objectInput.readUTF();
		twitterSn = objectInput.readUTF();
		ymSn = objectInput.readUTF();
		employeeStatusId = objectInput.readUTF();
		employeeNumber = objectInput.readUTF();
		jobTitle = objectInput.readUTF();
		jobClass = objectInput.readUTF();
		hoursOfOperation = objectInput.readUTF();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(contactId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
		objectOutput.writeLong(accountId);
		objectOutput.writeLong(parentContactId);

		if (emailAddress == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(emailAddress);
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

		objectOutput.writeInt(prefixId);
		objectOutput.writeInt(suffixId);
		objectOutput.writeBoolean(male);
		objectOutput.writeLong(birthday);

		if (smsSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(smsSn);
		}

		if (aimSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(aimSn);
		}

		if (facebookSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(facebookSn);
		}

		if (icqSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(icqSn);
		}

		if (jabberSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(jabberSn);
		}

		if (msnSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(msnSn);
		}

		if (mySpaceSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(mySpaceSn);
		}

		if (skypeSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(skypeSn);
		}

		if (twitterSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(twitterSn);
		}

		if (ymSn == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(ymSn);
		}

		if (employeeStatusId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(employeeStatusId);
		}

		if (employeeNumber == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(employeeNumber);
		}

		if (jobTitle == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(jobTitle);
		}

		if (jobClass == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(jobClass);
		}

		if (hoursOfOperation == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(hoursOfOperation);
		}

		objectOutput.writeLong(mvccVersion);
	}

	public long contactId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long accountId;
	public long parentContactId;
	public String emailAddress;
	public String firstName;
	public String middleName;
	public String lastName;
	public int prefixId;
	public int suffixId;
	public boolean male;
	public long birthday;
	public String smsSn;
	public String aimSn;
	public String facebookSn;
	public String icqSn;
	public String jabberSn;
	public String msnSn;
	public String mySpaceSn;
	public String skypeSn;
	public String twitterSn;
	public String ymSn;
	public String employeeStatusId;
	public String employeeNumber;
	public String jobTitle;
	public String jobClass;
	public String hoursOfOperation;
	public long mvccVersion;
}