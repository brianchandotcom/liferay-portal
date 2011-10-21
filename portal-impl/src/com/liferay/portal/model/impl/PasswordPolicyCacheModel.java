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
import com.liferay.portal.model.PasswordPolicy;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing PasswordPolicy in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicy
 * @generated
 */
public class PasswordPolicyCacheModel implements CacheModel<PasswordPolicy>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(65);

		sb.append("{passwordPolicyId=");
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

	public PasswordPolicy toEntityModel() {
		PasswordPolicyImpl passwordPolicyImpl = new PasswordPolicyImpl();

		passwordPolicyImpl.setPasswordPolicyId(passwordPolicyId);
		passwordPolicyImpl.setCompanyId(companyId);
		passwordPolicyImpl.setUserId(userId);

		if (userName == null) {
			passwordPolicyImpl.setUserName(StringPool.BLANK);
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
			passwordPolicyImpl.setName(StringPool.BLANK);
		}
		else {
			passwordPolicyImpl.setName(name);
		}

		if (description == null) {
			passwordPolicyImpl.setDescription(StringPool.BLANK);
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