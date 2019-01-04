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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link PasswordPolicy}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicy
 * @generated
 */
@ProviderType
public class PasswordPolicyWrapper extends BaseModelWrapper<PasswordPolicy>
	implements PasswordPolicy, ModelWrapper<PasswordPolicy> {
	public PasswordPolicyWrapper(PasswordPolicy passwordPolicy) {
		super(passwordPolicy);
	}

	/**
	* Returns the allow dictionary words of this password policy.
	*
	* @return the allow dictionary words of this password policy
	*/
	@Override
	public boolean getAllowDictionaryWords() {
		return model.getAllowDictionaryWords();
	}

	/**
	* Returns the changeable of this password policy.
	*
	* @return the changeable of this password policy
	*/
	@Override
	public boolean getChangeable() {
		return model.getChangeable();
	}

	/**
	* Returns the change required of this password policy.
	*
	* @return the change required of this password policy
	*/
	@Override
	public boolean getChangeRequired() {
		return model.getChangeRequired();
	}

	/**
	* Returns the check syntax of this password policy.
	*
	* @return the check syntax of this password policy
	*/
	@Override
	public boolean getCheckSyntax() {
		return model.getCheckSyntax();
	}

	/**
	* Returns the company ID of this password policy.
	*
	* @return the company ID of this password policy
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this password policy.
	*
	* @return the create date of this password policy
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the default policy of this password policy.
	*
	* @return the default policy of this password policy
	*/
	@Override
	public boolean getDefaultPolicy() {
		return model.getDefaultPolicy();
	}

	/**
	* Returns the description of this password policy.
	*
	* @return the description of this password policy
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the expireable of this password policy.
	*
	* @return the expireable of this password policy
	*/
	@Override
	public boolean getExpireable() {
		return model.getExpireable();
	}

	/**
	* Returns the grace limit of this password policy.
	*
	* @return the grace limit of this password policy
	*/
	@Override
	public int getGraceLimit() {
		return model.getGraceLimit();
	}

	/**
	* Returns the history of this password policy.
	*
	* @return the history of this password policy
	*/
	@Override
	public boolean getHistory() {
		return model.getHistory();
	}

	/**
	* Returns the history count of this password policy.
	*
	* @return the history count of this password policy
	*/
	@Override
	public int getHistoryCount() {
		return model.getHistoryCount();
	}

	/**
	* Returns the lockout of this password policy.
	*
	* @return the lockout of this password policy
	*/
	@Override
	public boolean getLockout() {
		return model.getLockout();
	}

	/**
	* Returns the lockout duration of this password policy.
	*
	* @return the lockout duration of this password policy
	*/
	@Override
	public long getLockoutDuration() {
		return model.getLockoutDuration();
	}

	/**
	* Returns the max age of this password policy.
	*
	* @return the max age of this password policy
	*/
	@Override
	public long getMaxAge() {
		return model.getMaxAge();
	}

	/**
	* Returns the max failure of this password policy.
	*
	* @return the max failure of this password policy
	*/
	@Override
	public int getMaxFailure() {
		return model.getMaxFailure();
	}

	/**
	* Returns the min age of this password policy.
	*
	* @return the min age of this password policy
	*/
	@Override
	public long getMinAge() {
		return model.getMinAge();
	}

	/**
	* Returns the min alphanumeric of this password policy.
	*
	* @return the min alphanumeric of this password policy
	*/
	@Override
	public int getMinAlphanumeric() {
		return model.getMinAlphanumeric();
	}

	/**
	* Returns the min length of this password policy.
	*
	* @return the min length of this password policy
	*/
	@Override
	public int getMinLength() {
		return model.getMinLength();
	}

	/**
	* Returns the min lower case of this password policy.
	*
	* @return the min lower case of this password policy
	*/
	@Override
	public int getMinLowerCase() {
		return model.getMinLowerCase();
	}

	/**
	* Returns the min numbers of this password policy.
	*
	* @return the min numbers of this password policy
	*/
	@Override
	public int getMinNumbers() {
		return model.getMinNumbers();
	}

	/**
	* Returns the min symbols of this password policy.
	*
	* @return the min symbols of this password policy
	*/
	@Override
	public int getMinSymbols() {
		return model.getMinSymbols();
	}

	/**
	* Returns the min upper case of this password policy.
	*
	* @return the min upper case of this password policy
	*/
	@Override
	public int getMinUpperCase() {
		return model.getMinUpperCase();
	}

	/**
	* Returns the modified date of this password policy.
	*
	* @return the modified date of this password policy
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this password policy.
	*
	* @return the mvcc version of this password policy
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the name of this password policy.
	*
	* @return the name of this password policy
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the password policy ID of this password policy.
	*
	* @return the password policy ID of this password policy
	*/
	@Override
	public long getPasswordPolicyId() {
		return model.getPasswordPolicyId();
	}

	/**
	* Returns the primary key of this password policy.
	*
	* @return the primary key of this password policy
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the regex of this password policy.
	*
	* @return the regex of this password policy
	*/
	@Override
	public String getRegex() {
		return model.getRegex();
	}

	/**
	* Returns the require unlock of this password policy.
	*
	* @return the require unlock of this password policy
	*/
	@Override
	public boolean getRequireUnlock() {
		return model.getRequireUnlock();
	}

	/**
	* Returns the reset failure count of this password policy.
	*
	* @return the reset failure count of this password policy
	*/
	@Override
	public long getResetFailureCount() {
		return model.getResetFailureCount();
	}

	/**
	* Returns the reset ticket max age of this password policy.
	*
	* @return the reset ticket max age of this password policy
	*/
	@Override
	public long getResetTicketMaxAge() {
		return model.getResetTicketMaxAge();
	}

	/**
	* Returns the user ID of this password policy.
	*
	* @return the user ID of this password policy
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this password policy.
	*
	* @return the user name of this password policy
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this password policy.
	*
	* @return the user uuid of this password policy
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this password policy.
	*
	* @return the uuid of this password policy
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	* Returns the warning time of this password policy.
	*
	* @return the warning time of this password policy
	*/
	@Override
	public long getWarningTime() {
		return model.getWarningTime();
	}

	/**
	* Returns <code>true</code> if this password policy is allow dictionary words.
	*
	* @return <code>true</code> if this password policy is allow dictionary words; <code>false</code> otherwise
	*/
	@Override
	public boolean isAllowDictionaryWords() {
		return model.isAllowDictionaryWords();
	}

	/**
	* Returns <code>true</code> if this password policy is changeable.
	*
	* @return <code>true</code> if this password policy is changeable; <code>false</code> otherwise
	*/
	@Override
	public boolean isChangeable() {
		return model.isChangeable();
	}

	/**
	* Returns <code>true</code> if this password policy is change required.
	*
	* @return <code>true</code> if this password policy is change required; <code>false</code> otherwise
	*/
	@Override
	public boolean isChangeRequired() {
		return model.isChangeRequired();
	}

	/**
	* Returns <code>true</code> if this password policy is check syntax.
	*
	* @return <code>true</code> if this password policy is check syntax; <code>false</code> otherwise
	*/
	@Override
	public boolean isCheckSyntax() {
		return model.isCheckSyntax();
	}

	/**
	* Returns <code>true</code> if this password policy is default policy.
	*
	* @return <code>true</code> if this password policy is default policy; <code>false</code> otherwise
	*/
	@Override
	public boolean isDefaultPolicy() {
		return model.isDefaultPolicy();
	}

	/**
	* Returns <code>true</code> if this password policy is expireable.
	*
	* @return <code>true</code> if this password policy is expireable; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpireable() {
		return model.isExpireable();
	}

	/**
	* Returns <code>true</code> if this password policy is history.
	*
	* @return <code>true</code> if this password policy is history; <code>false</code> otherwise
	*/
	@Override
	public boolean isHistory() {
		return model.isHistory();
	}

	/**
	* Returns <code>true</code> if this password policy is lockout.
	*
	* @return <code>true</code> if this password policy is lockout; <code>false</code> otherwise
	*/
	@Override
	public boolean isLockout() {
		return model.isLockout();
	}

	/**
	* Returns <code>true</code> if this password policy is require unlock.
	*
	* @return <code>true</code> if this password policy is require unlock; <code>false</code> otherwise
	*/
	@Override
	public boolean isRequireUnlock() {
		return model.isRequireUnlock();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets whether this password policy is allow dictionary words.
	*
	* @param allowDictionaryWords the allow dictionary words of this password policy
	*/
	@Override
	public void setAllowDictionaryWords(boolean allowDictionaryWords) {
		model.setAllowDictionaryWords(allowDictionaryWords);
	}

	/**
	* Sets whether this password policy is changeable.
	*
	* @param changeable the changeable of this password policy
	*/
	@Override
	public void setChangeable(boolean changeable) {
		model.setChangeable(changeable);
	}

	/**
	* Sets whether this password policy is change required.
	*
	* @param changeRequired the change required of this password policy
	*/
	@Override
	public void setChangeRequired(boolean changeRequired) {
		model.setChangeRequired(changeRequired);
	}

	/**
	* Sets whether this password policy is check syntax.
	*
	* @param checkSyntax the check syntax of this password policy
	*/
	@Override
	public void setCheckSyntax(boolean checkSyntax) {
		model.setCheckSyntax(checkSyntax);
	}

	/**
	* Sets the company ID of this password policy.
	*
	* @param companyId the company ID of this password policy
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this password policy.
	*
	* @param createDate the create date of this password policy
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets whether this password policy is default policy.
	*
	* @param defaultPolicy the default policy of this password policy
	*/
	@Override
	public void setDefaultPolicy(boolean defaultPolicy) {
		model.setDefaultPolicy(defaultPolicy);
	}

	/**
	* Sets the description of this password policy.
	*
	* @param description the description of this password policy
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets whether this password policy is expireable.
	*
	* @param expireable the expireable of this password policy
	*/
	@Override
	public void setExpireable(boolean expireable) {
		model.setExpireable(expireable);
	}

	/**
	* Sets the grace limit of this password policy.
	*
	* @param graceLimit the grace limit of this password policy
	*/
	@Override
	public void setGraceLimit(int graceLimit) {
		model.setGraceLimit(graceLimit);
	}

	/**
	* Sets whether this password policy is history.
	*
	* @param history the history of this password policy
	*/
	@Override
	public void setHistory(boolean history) {
		model.setHistory(history);
	}

	/**
	* Sets the history count of this password policy.
	*
	* @param historyCount the history count of this password policy
	*/
	@Override
	public void setHistoryCount(int historyCount) {
		model.setHistoryCount(historyCount);
	}

	/**
	* Sets whether this password policy is lockout.
	*
	* @param lockout the lockout of this password policy
	*/
	@Override
	public void setLockout(boolean lockout) {
		model.setLockout(lockout);
	}

	/**
	* Sets the lockout duration of this password policy.
	*
	* @param lockoutDuration the lockout duration of this password policy
	*/
	@Override
	public void setLockoutDuration(long lockoutDuration) {
		model.setLockoutDuration(lockoutDuration);
	}

	/**
	* Sets the max age of this password policy.
	*
	* @param maxAge the max age of this password policy
	*/
	@Override
	public void setMaxAge(long maxAge) {
		model.setMaxAge(maxAge);
	}

	/**
	* Sets the max failure of this password policy.
	*
	* @param maxFailure the max failure of this password policy
	*/
	@Override
	public void setMaxFailure(int maxFailure) {
		model.setMaxFailure(maxFailure);
	}

	/**
	* Sets the min age of this password policy.
	*
	* @param minAge the min age of this password policy
	*/
	@Override
	public void setMinAge(long minAge) {
		model.setMinAge(minAge);
	}

	/**
	* Sets the min alphanumeric of this password policy.
	*
	* @param minAlphanumeric the min alphanumeric of this password policy
	*/
	@Override
	public void setMinAlphanumeric(int minAlphanumeric) {
		model.setMinAlphanumeric(minAlphanumeric);
	}

	/**
	* Sets the min length of this password policy.
	*
	* @param minLength the min length of this password policy
	*/
	@Override
	public void setMinLength(int minLength) {
		model.setMinLength(minLength);
	}

	/**
	* Sets the min lower case of this password policy.
	*
	* @param minLowerCase the min lower case of this password policy
	*/
	@Override
	public void setMinLowerCase(int minLowerCase) {
		model.setMinLowerCase(minLowerCase);
	}

	/**
	* Sets the min numbers of this password policy.
	*
	* @param minNumbers the min numbers of this password policy
	*/
	@Override
	public void setMinNumbers(int minNumbers) {
		model.setMinNumbers(minNumbers);
	}

	/**
	* Sets the min symbols of this password policy.
	*
	* @param minSymbols the min symbols of this password policy
	*/
	@Override
	public void setMinSymbols(int minSymbols) {
		model.setMinSymbols(minSymbols);
	}

	/**
	* Sets the min upper case of this password policy.
	*
	* @param minUpperCase the min upper case of this password policy
	*/
	@Override
	public void setMinUpperCase(int minUpperCase) {
		model.setMinUpperCase(minUpperCase);
	}

	/**
	* Sets the modified date of this password policy.
	*
	* @param modifiedDate the modified date of this password policy
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this password policy.
	*
	* @param mvccVersion the mvcc version of this password policy
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this password policy.
	*
	* @param name the name of this password policy
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the password policy ID of this password policy.
	*
	* @param passwordPolicyId the password policy ID of this password policy
	*/
	@Override
	public void setPasswordPolicyId(long passwordPolicyId) {
		model.setPasswordPolicyId(passwordPolicyId);
	}

	/**
	* Sets the primary key of this password policy.
	*
	* @param primaryKey the primary key of this password policy
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the regex of this password policy.
	*
	* @param regex the regex of this password policy
	*/
	@Override
	public void setRegex(String regex) {
		model.setRegex(regex);
	}

	/**
	* Sets whether this password policy is require unlock.
	*
	* @param requireUnlock the require unlock of this password policy
	*/
	@Override
	public void setRequireUnlock(boolean requireUnlock) {
		model.setRequireUnlock(requireUnlock);
	}

	/**
	* Sets the reset failure count of this password policy.
	*
	* @param resetFailureCount the reset failure count of this password policy
	*/
	@Override
	public void setResetFailureCount(long resetFailureCount) {
		model.setResetFailureCount(resetFailureCount);
	}

	/**
	* Sets the reset ticket max age of this password policy.
	*
	* @param resetTicketMaxAge the reset ticket max age of this password policy
	*/
	@Override
	public void setResetTicketMaxAge(long resetTicketMaxAge) {
		model.setResetTicketMaxAge(resetTicketMaxAge);
	}

	/**
	* Sets the user ID of this password policy.
	*
	* @param userId the user ID of this password policy
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this password policy.
	*
	* @param userName the user name of this password policy
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this password policy.
	*
	* @param userUuid the user uuid of this password policy
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this password policy.
	*
	* @param uuid the uuid of this password policy
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	* Sets the warning time of this password policy.
	*
	* @param warningTime the warning time of this password policy
	*/
	@Override
	public void setWarningTime(long warningTime) {
		model.setWarningTime(warningTime);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected PasswordPolicyWrapper wrap(PasswordPolicy passwordPolicy) {
		return new PasswordPolicyWrapper(passwordPolicy);
	}
}