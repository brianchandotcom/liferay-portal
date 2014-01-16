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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.PasswordPolicyServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.http.PasswordPolicyServiceSoap
 * @generated
 */
public class PasswordPolicySoap implements Serializable {
	public static PasswordPolicySoap toSoapModel(PasswordPolicy model) {
		PasswordPolicySoap soapModel = new PasswordPolicySoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setPasswordPolicyId(model.getPasswordPolicyId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDefaultPolicy(model.getDefaultPolicy());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setChangeable(model.getChangeable());
		soapModel.setChangeRequired(model.getChangeRequired());
		soapModel.setMinAge(model.getMinAge());
		soapModel.setCheckSyntax(model.getCheckSyntax());
		soapModel.setAllowDictionaryWords(model.getAllowDictionaryWords());
		soapModel.setMinAlphanumeric(model.getMinAlphanumeric());
		soapModel.setMinLength(model.getMinLength());
		soapModel.setMinLowerCase(model.getMinLowerCase());
		soapModel.setMinNumbers(model.getMinNumbers());
		soapModel.setMinSymbols(model.getMinSymbols());
		soapModel.setMinUpperCase(model.getMinUpperCase());
		soapModel.setRegex(model.getRegex());
		soapModel.setHistory(model.getHistory());
		soapModel.setHistoryCount(model.getHistoryCount());
		soapModel.setExpireable(model.getExpireable());
		soapModel.setMaxAge(model.getMaxAge());
		soapModel.setWarningTime(model.getWarningTime());
		soapModel.setGraceLimit(model.getGraceLimit());
		soapModel.setLockout(model.getLockout());
		soapModel.setMaxFailure(model.getMaxFailure());
		soapModel.setLockoutDuration(model.getLockoutDuration());
		soapModel.setRequireUnlock(model.getRequireUnlock());
		soapModel.setResetFailureCount(model.getResetFailureCount());
		soapModel.setResetTicketMaxAge(model.getResetTicketMaxAge());
		soapModel.setMvccVersion(model.getMvccVersion());

		return soapModel;
	}

	public static PasswordPolicySoap[] toSoapModels(PasswordPolicy[] models) {
		PasswordPolicySoap[] soapModels = new PasswordPolicySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PasswordPolicySoap[][] toSoapModels(PasswordPolicy[][] models) {
		PasswordPolicySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PasswordPolicySoap[models.length][models[0].length];
		}
		else {
			soapModels = new PasswordPolicySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PasswordPolicySoap[] toSoapModels(List<PasswordPolicy> models) {
		List<PasswordPolicySoap> soapModels = new ArrayList<PasswordPolicySoap>(models.size());

		for (PasswordPolicy model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PasswordPolicySoap[soapModels.size()]);
	}

	public PasswordPolicySoap() {
	}

	public long getPrimaryKey() {
		return _passwordPolicyId;
	}

	public void setPrimaryKey(long pk) {
		setPasswordPolicyId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getPasswordPolicyId() {
		return _passwordPolicyId;
	}

	public void setPasswordPolicyId(long passwordPolicyId) {
		_passwordPolicyId = passwordPolicyId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public boolean getDefaultPolicy() {
		return _defaultPolicy;
	}

	public boolean isDefaultPolicy() {
		return _defaultPolicy;
	}

	public void setDefaultPolicy(boolean defaultPolicy) {
		_defaultPolicy = defaultPolicy;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public boolean getChangeable() {
		return _changeable;
	}

	public boolean isChangeable() {
		return _changeable;
	}

	public void setChangeable(boolean changeable) {
		_changeable = changeable;
	}

	public boolean getChangeRequired() {
		return _changeRequired;
	}

	public boolean isChangeRequired() {
		return _changeRequired;
	}

	public void setChangeRequired(boolean changeRequired) {
		_changeRequired = changeRequired;
	}

	public long getMinAge() {
		return _minAge;
	}

	public void setMinAge(long minAge) {
		_minAge = minAge;
	}

	public boolean getCheckSyntax() {
		return _checkSyntax;
	}

	public boolean isCheckSyntax() {
		return _checkSyntax;
	}

	public void setCheckSyntax(boolean checkSyntax) {
		_checkSyntax = checkSyntax;
	}

	public boolean getAllowDictionaryWords() {
		return _allowDictionaryWords;
	}

	public boolean isAllowDictionaryWords() {
		return _allowDictionaryWords;
	}

	public void setAllowDictionaryWords(boolean allowDictionaryWords) {
		_allowDictionaryWords = allowDictionaryWords;
	}

	public int getMinAlphanumeric() {
		return _minAlphanumeric;
	}

	public void setMinAlphanumeric(int minAlphanumeric) {
		_minAlphanumeric = minAlphanumeric;
	}

	public int getMinLength() {
		return _minLength;
	}

	public void setMinLength(int minLength) {
		_minLength = minLength;
	}

	public int getMinLowerCase() {
		return _minLowerCase;
	}

	public void setMinLowerCase(int minLowerCase) {
		_minLowerCase = minLowerCase;
	}

	public int getMinNumbers() {
		return _minNumbers;
	}

	public void setMinNumbers(int minNumbers) {
		_minNumbers = minNumbers;
	}

	public int getMinSymbols() {
		return _minSymbols;
	}

	public void setMinSymbols(int minSymbols) {
		_minSymbols = minSymbols;
	}

	public int getMinUpperCase() {
		return _minUpperCase;
	}

	public void setMinUpperCase(int minUpperCase) {
		_minUpperCase = minUpperCase;
	}

	public String getRegex() {
		return _regex;
	}

	public void setRegex(String regex) {
		_regex = regex;
	}

	public boolean getHistory() {
		return _history;
	}

	public boolean isHistory() {
		return _history;
	}

	public void setHistory(boolean history) {
		_history = history;
	}

	public int getHistoryCount() {
		return _historyCount;
	}

	public void setHistoryCount(int historyCount) {
		_historyCount = historyCount;
	}

	public boolean getExpireable() {
		return _expireable;
	}

	public boolean isExpireable() {
		return _expireable;
	}

	public void setExpireable(boolean expireable) {
		_expireable = expireable;
	}

	public long getMaxAge() {
		return _maxAge;
	}

	public void setMaxAge(long maxAge) {
		_maxAge = maxAge;
	}

	public long getWarningTime() {
		return _warningTime;
	}

	public void setWarningTime(long warningTime) {
		_warningTime = warningTime;
	}

	public int getGraceLimit() {
		return _graceLimit;
	}

	public void setGraceLimit(int graceLimit) {
		_graceLimit = graceLimit;
	}

	public boolean getLockout() {
		return _lockout;
	}

	public boolean isLockout() {
		return _lockout;
	}

	public void setLockout(boolean lockout) {
		_lockout = lockout;
	}

	public int getMaxFailure() {
		return _maxFailure;
	}

	public void setMaxFailure(int maxFailure) {
		_maxFailure = maxFailure;
	}

	public long getLockoutDuration() {
		return _lockoutDuration;
	}

	public void setLockoutDuration(long lockoutDuration) {
		_lockoutDuration = lockoutDuration;
	}

	public boolean getRequireUnlock() {
		return _requireUnlock;
	}

	public boolean isRequireUnlock() {
		return _requireUnlock;
	}

	public void setRequireUnlock(boolean requireUnlock) {
		_requireUnlock = requireUnlock;
	}

	public long getResetFailureCount() {
		return _resetFailureCount;
	}

	public void setResetFailureCount(long resetFailureCount) {
		_resetFailureCount = resetFailureCount;
	}

	public long getResetTicketMaxAge() {
		return _resetTicketMaxAge;
	}

	public void setResetTicketMaxAge(long resetTicketMaxAge) {
		_resetTicketMaxAge = resetTicketMaxAge;
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	private String _uuid;
	private long _passwordPolicyId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _defaultPolicy;
	private String _name;
	private String _description;
	private boolean _changeable;
	private boolean _changeRequired;
	private long _minAge;
	private boolean _checkSyntax;
	private boolean _allowDictionaryWords;
	private int _minAlphanumeric;
	private int _minLength;
	private int _minLowerCase;
	private int _minNumbers;
	private int _minSymbols;
	private int _minUpperCase;
	private String _regex;
	private boolean _history;
	private int _historyCount;
	private boolean _expireable;
	private long _maxAge;
	private long _warningTime;
	private int _graceLimit;
	private boolean _lockout;
	private int _maxFailure;
	private long _lockoutDuration;
	private boolean _requireUnlock;
	private long _resetFailureCount;
	private long _resetTicketMaxAge;
	private long _mvccVersion;
}