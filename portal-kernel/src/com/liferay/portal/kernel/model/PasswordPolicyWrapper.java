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

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PasswordPolicy}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicy
 * @generated
 */
public class PasswordPolicyWrapper
	extends BaseModelWrapper<PasswordPolicy>
	implements ModelWrapper<PasswordPolicy>, PasswordPolicy {

	public PasswordPolicyWrapper(PasswordPolicy passwordPolicy) {
		super(passwordPolicy);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("passwordPolicyId", getPasswordPolicyId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("defaultPolicy", isDefaultPolicy());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("changeable", isChangeable());
		attributes.put("changeRequired", isChangeRequired());
		attributes.put("minAge", getMinAge());
		attributes.put("checkSyntax", isCheckSyntax());
		attributes.put("allowDictionaryWords", isAllowDictionaryWords());
		attributes.put("minAlphanumeric", getMinAlphanumeric());
		attributes.put("minLength", getMinLength());
		attributes.put("minLowerCase", getMinLowerCase());
		attributes.put("minNumbers", getMinNumbers());
		attributes.put("minSymbols", getMinSymbols());
		attributes.put("minUpperCase", getMinUpperCase());
		attributes.put("regex", getRegex());
		attributes.put("history", isHistory());
		attributes.put("historyCount", getHistoryCount());
		attributes.put("expireable", isExpireable());
		attributes.put("maxAge", getMaxAge());
		attributes.put("warningTime", getWarningTime());
		attributes.put("graceLimit", getGraceLimit());
		attributes.put("lockout", isLockout());
		attributes.put("maxFailure", getMaxFailure());
		attributes.put("lockoutDuration", getLockoutDuration());
		attributes.put("requireUnlock", isRequireUnlock());
		attributes.put("resetFailureCount", getResetFailureCount());
		attributes.put("resetTicketMaxAge", getResetTicketMaxAge());

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

		Long passwordPolicyId = (Long)attributes.get("passwordPolicyId");

		if (passwordPolicyId != null) {
			setPasswordPolicyId(passwordPolicyId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Boolean defaultPolicy = (Boolean)attributes.get("defaultPolicy");

		if (defaultPolicy != null) {
			setDefaultPolicy(defaultPolicy);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Boolean changeable = (Boolean)attributes.get("changeable");

		if (changeable != null) {
			setChangeable(changeable);
		}

		Boolean changeRequired = (Boolean)attributes.get("changeRequired");

		if (changeRequired != null) {
			setChangeRequired(changeRequired);
		}

		Long minAge = (Long)attributes.get("minAge");

		if (minAge != null) {
			setMinAge(minAge);
		}

		Boolean checkSyntax = (Boolean)attributes.get("checkSyntax");

		if (checkSyntax != null) {
			setCheckSyntax(checkSyntax);
		}

		Boolean allowDictionaryWords = (Boolean)attributes.get(
			"allowDictionaryWords");

		if (allowDictionaryWords != null) {
			setAllowDictionaryWords(allowDictionaryWords);
		}

		Integer minAlphanumeric = (Integer)attributes.get("minAlphanumeric");

		if (minAlphanumeric != null) {
			setMinAlphanumeric(minAlphanumeric);
		}

		Integer minLength = (Integer)attributes.get("minLength");

		if (minLength != null) {
			setMinLength(minLength);
		}

		Integer minLowerCase = (Integer)attributes.get("minLowerCase");

		if (minLowerCase != null) {
			setMinLowerCase(minLowerCase);
		}

		Integer minNumbers = (Integer)attributes.get("minNumbers");

		if (minNumbers != null) {
			setMinNumbers(minNumbers);
		}

		Integer minSymbols = (Integer)attributes.get("minSymbols");

		if (minSymbols != null) {
			setMinSymbols(minSymbols);
		}

		Integer minUpperCase = (Integer)attributes.get("minUpperCase");

		if (minUpperCase != null) {
			setMinUpperCase(minUpperCase);
		}

		String regex = (String)attributes.get("regex");

		if (regex != null) {
			setRegex(regex);
		}

		Boolean history = (Boolean)attributes.get("history");

		if (history != null) {
			setHistory(history);
		}

		Integer historyCount = (Integer)attributes.get("historyCount");

		if (historyCount != null) {
			setHistoryCount(historyCount);
		}

		Boolean expireable = (Boolean)attributes.get("expireable");

		if (expireable != null) {
			setExpireable(expireable);
		}

		Long maxAge = (Long)attributes.get("maxAge");

		if (maxAge != null) {
			setMaxAge(maxAge);
		}

		Long warningTime = (Long)attributes.get("warningTime");

		if (warningTime != null) {
			setWarningTime(warningTime);
		}

		Integer graceLimit = (Integer)attributes.get("graceLimit");

		if (graceLimit != null) {
			setGraceLimit(graceLimit);
		}

		Boolean lockout = (Boolean)attributes.get("lockout");

		if (lockout != null) {
			setLockout(lockout);
		}

		Integer maxFailure = (Integer)attributes.get("maxFailure");

		if (maxFailure != null) {
			setMaxFailure(maxFailure);
		}

		Long lockoutDuration = (Long)attributes.get("lockoutDuration");

		if (lockoutDuration != null) {
			setLockoutDuration(lockoutDuration);
		}

		Boolean requireUnlock = (Boolean)attributes.get("requireUnlock");

		if (requireUnlock != null) {
			setRequireUnlock(requireUnlock);
		}

		Long resetFailureCount = (Long)attributes.get("resetFailureCount");

		if (resetFailureCount != null) {
			setResetFailureCount(resetFailureCount);
		}

		Long resetTicketMaxAge = (Long)attributes.get("resetTicketMaxAge");

		if (resetTicketMaxAge != null) {
			setResetTicketMaxAge(resetTicketMaxAge);
		}
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

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a password policy model instance should use the <code>PasswordPolicy</code> interface instead.
	 */
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