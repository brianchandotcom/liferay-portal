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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.PasswordPolicy;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PasswordPolicy in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class PasswordPolicyCacheModel
	implements CacheModel<PasswordPolicy>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PasswordPolicyCacheModel)) {
			return false;
		}

		PasswordPolicyCacheModel passwordPolicyCacheModel =
			(PasswordPolicyCacheModel)obj;

		if ((passwordPolicyId == passwordPolicyCacheModel.passwordPolicyId) &&
			(mvccVersion == passwordPolicyCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, passwordPolicyId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(71);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", passwordPolicyId=");
		sb.append(passwordPolicyId);
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
		sb.append(", defaultPolicy=");
		sb.append(defaultPolicy);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", changeable=");
		sb.append(changeable);
		sb.append(", changeRequired=");
		sb.append(changeRequired);
		sb.append(", minAge=");
		sb.append(minAge);
		sb.append(", checkSyntax=");
		sb.append(checkSyntax);
		sb.append(", allowDictionaryWords=");
		sb.append(allowDictionaryWords);
		sb.append(", minAlphanumeric=");
		sb.append(minAlphanumeric);
		sb.append(", minLength=");
		sb.append(minLength);
		sb.append(", minLowerCase=");
		sb.append(minLowerCase);
		sb.append(", minNumbers=");
		sb.append(minNumbers);
		sb.append(", minSymbols=");
		sb.append(minSymbols);
		sb.append(", minUpperCase=");
		sb.append(minUpperCase);
		sb.append(", regex=");
		sb.append(regex);
		sb.append(", history=");
		sb.append(history);
		sb.append(", historyCount=");
		sb.append(historyCount);
		sb.append(", expireable=");
		sb.append(expireable);
		sb.append(", maxAge=");
		sb.append(maxAge);
		sb.append(", warningTime=");
		sb.append(warningTime);
		sb.append(", graceLimit=");
		sb.append(graceLimit);
		sb.append(", lockout=");
		sb.append(lockout);
		sb.append(", maxFailure=");
		sb.append(maxFailure);
		sb.append(", lockoutDuration=");
		sb.append(lockoutDuration);
		sb.append(", requireUnlock=");
		sb.append(requireUnlock);
		sb.append(", resetFailureCount=");
		sb.append(resetFailureCount);
		sb.append(", resetTicketMaxAge=");
		sb.append(resetTicketMaxAge);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PasswordPolicy toEntityModel() {
		PasswordPolicyImpl passwordPolicyImpl = new PasswordPolicyImpl();

		passwordPolicyImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			passwordPolicyImpl.setUuid("");
		}
		else {
			passwordPolicyImpl.setUuid(uuid);
		}

		passwordPolicyImpl.setPasswordPolicyId(passwordPolicyId);
		passwordPolicyImpl.setCompanyId(companyId);
		passwordPolicyImpl.setUserId(userId);

		if (userName == null) {
			passwordPolicyImpl.setUserName("");
		}
		else {
			passwordPolicyImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			passwordPolicyImpl.setCreateDate(null);
		}
		else {
			passwordPolicyImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			passwordPolicyImpl.setModifiedDate(null);
		}
		else {
			passwordPolicyImpl.setModifiedDate(new Date(modifiedDate));
		}

		passwordPolicyImpl.setDefaultPolicy(defaultPolicy);

		if (name == null) {
			passwordPolicyImpl.setName("");
		}
		else {
			passwordPolicyImpl.setName(name);
		}

		if (description == null) {
			passwordPolicyImpl.setDescription("");
		}
		else {
			passwordPolicyImpl.setDescription(description);
		}

		passwordPolicyImpl.setChangeable(changeable);
		passwordPolicyImpl.setChangeRequired(changeRequired);
		passwordPolicyImpl.setMinAge(minAge);
		passwordPolicyImpl.setCheckSyntax(checkSyntax);
		passwordPolicyImpl.setAllowDictionaryWords(allowDictionaryWords);
		passwordPolicyImpl.setMinAlphanumeric(minAlphanumeric);
		passwordPolicyImpl.setMinLength(minLength);
		passwordPolicyImpl.setMinLowerCase(minLowerCase);
		passwordPolicyImpl.setMinNumbers(minNumbers);
		passwordPolicyImpl.setMinSymbols(minSymbols);
		passwordPolicyImpl.setMinUpperCase(minUpperCase);

		if (regex == null) {
			passwordPolicyImpl.setRegex("");
		}
		else {
			passwordPolicyImpl.setRegex(regex);
		}

		passwordPolicyImpl.setHistory(history);
		passwordPolicyImpl.setHistoryCount(historyCount);
		passwordPolicyImpl.setExpireable(expireable);
		passwordPolicyImpl.setMaxAge(maxAge);
		passwordPolicyImpl.setWarningTime(warningTime);
		passwordPolicyImpl.setGraceLimit(graceLimit);
		passwordPolicyImpl.setLockout(lockout);
		passwordPolicyImpl.setMaxFailure(maxFailure);
		passwordPolicyImpl.setLockoutDuration(lockoutDuration);
		passwordPolicyImpl.setRequireUnlock(requireUnlock);
		passwordPolicyImpl.setResetFailureCount(resetFailureCount);
		passwordPolicyImpl.setResetTicketMaxAge(resetTicketMaxAge);

		passwordPolicyImpl.resetOriginalValues();

		return passwordPolicyImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		passwordPolicyId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		defaultPolicy = objectInput.readBoolean();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		changeable = objectInput.readBoolean();

		changeRequired = objectInput.readBoolean();

		minAge = objectInput.readLong();

		checkSyntax = objectInput.readBoolean();

		allowDictionaryWords = objectInput.readBoolean();

		minAlphanumeric = objectInput.readInt();

		minLength = objectInput.readInt();

		minLowerCase = objectInput.readInt();

		minNumbers = objectInput.readInt();

		minSymbols = objectInput.readInt();

		minUpperCase = objectInput.readInt();
		regex = objectInput.readUTF();

		history = objectInput.readBoolean();

		historyCount = objectInput.readInt();

		expireable = objectInput.readBoolean();

		maxAge = objectInput.readLong();

		warningTime = objectInput.readLong();

		graceLimit = objectInput.readInt();

		lockout = objectInput.readBoolean();

		maxFailure = objectInput.readInt();

		lockoutDuration = objectInput.readLong();

		requireUnlock = objectInput.readBoolean();

		resetFailureCount = objectInput.readLong();

		resetTicketMaxAge = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(passwordPolicyId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeBoolean(defaultPolicy);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeBoolean(changeable);

		objectOutput.writeBoolean(changeRequired);

		objectOutput.writeLong(minAge);

		objectOutput.writeBoolean(checkSyntax);

		objectOutput.writeBoolean(allowDictionaryWords);

		objectOutput.writeInt(minAlphanumeric);

		objectOutput.writeInt(minLength);

		objectOutput.writeInt(minLowerCase);

		objectOutput.writeInt(minNumbers);

		objectOutput.writeInt(minSymbols);

		objectOutput.writeInt(minUpperCase);

		if (regex == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(regex);
		}

		objectOutput.writeBoolean(history);

		objectOutput.writeInt(historyCount);

		objectOutput.writeBoolean(expireable);

		objectOutput.writeLong(maxAge);

		objectOutput.writeLong(warningTime);

		objectOutput.writeInt(graceLimit);

		objectOutput.writeBoolean(lockout);

		objectOutput.writeInt(maxFailure);

		objectOutput.writeLong(lockoutDuration);

		objectOutput.writeBoolean(requireUnlock);

		objectOutput.writeLong(resetFailureCount);

		objectOutput.writeLong(resetTicketMaxAge);
	}

	public long mvccVersion;
	public String uuid;
	public long passwordPolicyId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean defaultPolicy;
	public String name;
	public String description;
	public boolean changeable;
	public boolean changeRequired;
	public long minAge;
	public boolean checkSyntax;
	public boolean allowDictionaryWords;
	public int minAlphanumeric;
	public int minLength;
	public int minLowerCase;
	public int minNumbers;
	public int minSymbols;
	public int minUpperCase;
	public String regex;
	public boolean history;
	public int historyCount;
	public boolean expireable;
	public long maxAge;
	public long warningTime;
	public int graceLimit;
	public boolean lockout;
	public int maxFailure;
	public long lockoutDuration;
	public boolean requireUnlock;
	public long resetFailureCount;
	public long resetTicketMaxAge;

}