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

package com.liferay.portal.service.impl;

import com.liferay.portal.DuplicatePasswordPolicyException;
import com.liferay.portal.PasswordPolicyNameException;
import com.liferay.portal.RequiredPasswordPolicyException;
import com.liferay.portal.kernel.cache.ThreadLocalCachable;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.PasswordPolicy;
import com.liferay.portal.model.PasswordPolicyRel;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.security.ldap.LDAPSettingsUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.PasswordPolicyLocalServiceBaseImpl;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Scott Lee
 */
public class PasswordPolicyLocalServiceImpl
	extends PasswordPolicyLocalServiceBaseImpl {

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addPasswordPolicy(long,
	 *             boolean, String, String, boolean, boolean, long, boolean,
	 *             boolean, int, int, int, int, int, int, String, boolean, int,
	 *             boolean, long, long, int, boolean, int, long, long, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public PasswordPolicy addPasswordPolicy(
			long userId, boolean defaultPolicy, String name, String description,
			boolean changeable, boolean changeRequired, long minAge,
			boolean checkSyntax, boolean allowDictionaryWords,
			int minAlphanumeric, int minLength, int minLowerCase,
			int minNumbers, int minSymbols, int minUpperCase, boolean history,
			int historyCount, boolean expireable, long maxAge, long warningTime,
			int graceLimit, boolean lockout, int maxFailure,
			long lockoutDuration, long resetFailureCount,
			long resetTicketMaxAge)
		throws PortalException {

		return addPasswordPolicy(
			userId, defaultPolicy, name, description, changeable,
			changeRequired, minAge, checkSyntax, allowDictionaryWords,
			minAlphanumeric, minLength, minLowerCase, minNumbers, minSymbols,
			minUpperCase, null, history, historyCount, expireable, maxAge,
			warningTime, graceLimit, lockout, maxFailure, lockoutDuration,
			resetFailureCount, resetTicketMaxAge, new ServiceContext());
	}

	@Override
	public PasswordPolicy addPasswordPolicy(
			long userId, boolean defaultPolicy, String name, String description,
			boolean changeable, boolean changeRequired, long minAge,
			boolean checkSyntax, boolean allowDictionaryWords,
			int minAlphanumeric, int minLength, int minLowerCase,
			int minNumbers, int minSymbols, int minUpperCase, String regex,
			boolean history, int historyCount, boolean expireable, long maxAge,
			long warningTime, int graceLimit, boolean lockout, int maxFailure,
			long lockoutDuration, long resetFailureCount,
			long resetTicketMaxAge, ServiceContext serviceContext)
		throws PortalException {

		// Password policy

		User user = userPersistence.findByPrimaryKey(userId);

		validate(0, user.getCompanyId(), name);

		long passwordPolicyId = counterLocalService.increment();

		PasswordPolicy passwordPolicy = passwordPolicyPersistence.create(
			passwordPolicyId);

		passwordPolicy.setUuid(serviceContext.getUuid());
		passwordPolicy.setCompanyId(user.getCompanyId());
		passwordPolicy.setUserId(userId);
		passwordPolicy.setUserName(user.getFullName());
		passwordPolicy.setDefaultPolicy(defaultPolicy);
		passwordPolicy.setName(name);
		passwordPolicy.setDescription(description);
		passwordPolicy.setChangeable(changeable);
		passwordPolicy.setChangeRequired(changeRequired);
		passwordPolicy.setMinAge(minAge);
		passwordPolicy.setCheckSyntax(checkSyntax);
		passwordPolicy.setAllowDictionaryWords(allowDictionaryWords);
		passwordPolicy.setMinAlphanumeric(minAlphanumeric);
		passwordPolicy.setMinLength(minLength);
		passwordPolicy.setMinLowerCase(minLowerCase);
		passwordPolicy.setMinNumbers(minNumbers);
		passwordPolicy.setMinSymbols(minSymbols);
		passwordPolicy.setMinUpperCase(minUpperCase);
		passwordPolicy.setRegex(regex);
		passwordPolicy.setHistory(history);
		passwordPolicy.setHistoryCount(historyCount);
		passwordPolicy.setExpireable(expireable);
		passwordPolicy.setMaxAge(maxAge);
		passwordPolicy.setWarningTime(warningTime);
		passwordPolicy.setGraceLimit(graceLimit);
		passwordPolicy.setLockout(lockout);
		passwordPolicy.setMaxFailure(maxFailure);
		passwordPolicy.setLockoutDuration(lockoutDuration);
		passwordPolicy.setRequireUnlock(lockoutDuration == 0);
		passwordPolicy.setResetFailureCount(resetFailureCount);
		passwordPolicy.setResetTicketMaxAge(resetTicketMaxAge);
		passwordPolicy.setExpandoBridgeAttributes(serviceContext);

		passwordPolicyPersistence.update(passwordPolicy);

		// Resources

		if (!user.isDefaultUser()) {
			resourceLocalService.addResources(
				user.getCompanyId(), 0, userId, PasswordPolicy.class.getName(),
				passwordPolicy.getPasswordPolicyId(), false, false, false);
		}

		return passwordPolicy;
	}

	@Override
	public void checkDefaultPasswordPolicy(long companyId)
		throws PortalException {

		String defaultPasswordPolicyName =
			PropsValues.PASSWORDS_DEFAULT_POLICY_NAME;

		PasswordPolicy defaultPasswordPolicy =
			passwordPolicyPersistence.fetchByC_N(
				companyId, defaultPasswordPolicyName);

		if (defaultPasswordPolicy == null) {
			long defaultUserId = userLocalService.getDefaultUserId(companyId);

			addPasswordPolicy(
				defaultUserId, true, defaultPasswordPolicyName,
				defaultPasswordPolicyName,
				PropsValues.PASSWORDS_DEFAULT_POLICY_CHANGEABLE,
				PropsValues.PASSWORDS_DEFAULT_POLICY_CHANGE_REQUIRED,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_AGE,
				PropsValues.PASSWORDS_DEFAULT_POLICY_CHECK_SYNTAX,
				PropsValues.PASSWORDS_DEFAULT_POLICY_ALLOW_DICTIONARY_WORDS,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_ALPHANUMERIC,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_LENGTH,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_LOWERCASE,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_NUMBERS,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_SYMBOLS,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MIN_UPPERCASE,
				PropsValues.PASSWORDS_DEFAULT_POLICY_REGEX,
				PropsValues.PASSWORDS_DEFAULT_POLICY_HISTORY,
				PropsValues.PASSWORDS_DEFAULT_POLICY_HISTORY_COUNT,
				PropsValues.PASSWORDS_DEFAULT_POLICY_EXPIREABLE,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MAX_AGE,
				PropsValues.PASSWORDS_DEFAULT_POLICY_WARNING_TIME,
				PropsValues.PASSWORDS_DEFAULT_POLICY_GRACE_LIMIT,
				PropsValues.PASSWORDS_DEFAULT_POLICY_LOCKOUT,
				PropsValues.PASSWORDS_DEFAULT_POLICY_MAX_FAILURE,
				PropsValues.PASSWORDS_DEFAULT_POLICY_LOCKOUT_DURATION,
				PropsValues.PASSWORDS_DEFAULT_POLICY_RESET_FAILURE_COUNT,
				PropsValues.PASSWORDS_DEFAULT_POLICY_RESET_TICKET_MAX_AGE,
				new ServiceContext());
		}
	}

	@Override
	public void deleteNondefaultPasswordPolicies(long companyId)
		throws PortalException {

		List<PasswordPolicy> passwordPolicies =
			passwordPolicyPersistence.findByCompanyId(companyId);

		for (PasswordPolicy passwordPolicy : passwordPolicies) {
			if (!passwordPolicy.isDefaultPolicy()) {
				passwordPolicyLocalService.deletePasswordPolicy(passwordPolicy);
			}
		}
	}

	@Override
	public PasswordPolicy deletePasswordPolicy(long passwordPolicyId)
		throws PortalException {

		PasswordPolicy passwordPolicy =
			passwordPolicyPersistence.findByPrimaryKey(passwordPolicyId);

		return passwordPolicyLocalService.deletePasswordPolicy(passwordPolicy);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public PasswordPolicy deletePasswordPolicy(PasswordPolicy passwordPolicy)
		throws PortalException {

		if (passwordPolicy.isDefaultPolicy() &&
			!CompanyThreadLocal.isDeleteInProcess()) {

			throw new RequiredPasswordPolicyException();
		}

		// Password policy relations

		passwordPolicyRelLocalService.deletePasswordPolicyRels(
			passwordPolicy.getPasswordPolicyId());

		// Resources

		resourceLocalService.deleteResource(
			passwordPolicy.getCompanyId(), PasswordPolicy.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			passwordPolicy.getPasswordPolicyId());

		// Password policy

		return passwordPolicyPersistence.remove(passwordPolicy);
	}

	@Override
	public PasswordPolicy fetchPasswordPolicy(long companyId, String name) {
		return passwordPolicyPersistence.fetchByC_N(companyId, name);
	}

	@Override
	public PasswordPolicy getDefaultPasswordPolicy(long companyId)
		throws PortalException {

		if (LDAPSettingsUtil.isPasswordPolicyEnabled(companyId)) {
			return null;
		}

		return passwordPolicyPersistence.findByC_DP(companyId, true);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	public PasswordPolicy getPasswordPolicy(
			long companyId, long organizationId, long locationId)
		throws PortalException {

		return getPasswordPolicy(
			companyId, new long[] {organizationId, locationId});
	}

	@Override
	public PasswordPolicy getPasswordPolicy(
			long companyId, long[] organizationIds)
		throws PortalException {

		if (LDAPSettingsUtil.isPasswordPolicyEnabled(companyId)) {
			return null;
		}

		if (ArrayUtil.isEmpty(organizationIds)) {
			return getDefaultPasswordPolicy(companyId);
		}

		long classNameId = classNameLocalService.getClassNameId(
			Organization.class.getName());

		PasswordPolicyRel passwordPolicyRel = null;

		for (int i = 0; i < organizationIds.length; i++) {
			long organizationId = organizationIds[i];

			passwordPolicyRel = passwordPolicyRelPersistence.fetchByC_C(
				classNameId, organizationId);

			if (passwordPolicyRel != null) {
				return passwordPolicyPersistence.findByPrimaryKey(
					passwordPolicyRel.getPasswordPolicyId());
			}
		}

		return getDefaultPasswordPolicy(companyId);
	}

	@Override
	@ThreadLocalCachable
	public PasswordPolicy getPasswordPolicyByUserId(long userId)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (LDAPSettingsUtil.isPasswordPolicyEnabled(user.getCompanyId())) {
			return null;
		}

		long classNameId = classNameLocalService.getClassNameId(
			User.class.getName());

		PasswordPolicyRel passwordPolicyRel =
			passwordPolicyRelPersistence.fetchByC_C(classNameId, userId);

		if (passwordPolicyRel != null) {
			return getPasswordPolicy(passwordPolicyRel.getPasswordPolicyId());
		}

		long[] organizationIds = userPersistence.getOrganizationPrimaryKeys(
			userId);

		if (organizationIds.length == 0) {
			return passwordPolicyPersistence.findByC_DP(
				user.getCompanyId(), true);
		}

		return getPasswordPolicy(user.getCompanyId(), organizationIds);
	}

	@Override
	public List<PasswordPolicy> search(
		long companyId, String name, int start, int end,
		OrderByComparator<PasswordPolicy> obc) {

		return passwordPolicyFinder.findByC_N(companyId, name, start, end, obc);
	}

	@Override
	public int searchCount(long companyId, String name) {
		return passwordPolicyFinder.countByC_N(companyId, name);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updatePasswordPolicy(long,
	 *             String, String, boolean, boolean, long, boolean, boolean,
	 *             int, int, int, int, int, int, String, boolean, int, boolean,
	 *             long, long, int, boolean, int, long, long, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public PasswordPolicy updatePasswordPolicy(
			long passwordPolicyId, String name, String description,
			boolean changeable, boolean changeRequired, long minAge,
			boolean checkSyntax, boolean allowDictionaryWords,
			int minAlphanumeric, int minLength, int minLowerCase,
			int minNumbers, int minSymbols, int minUpperCase, boolean history,
			int historyCount, boolean expireable, long maxAge, long warningTime,
			int graceLimit, boolean lockout, int maxFailure,
			long lockoutDuration, long resetFailureCount,
			long resetTicketMaxAge)
		throws PortalException {

		return updatePasswordPolicy(
			passwordPolicyId, name, description, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minAlphanumeric,
			minLength, minLowerCase, minNumbers, minSymbols, minUpperCase, null,
			history, historyCount, expireable, maxAge, warningTime, graceLimit,
			lockout, maxFailure, lockoutDuration, resetFailureCount,
			resetTicketMaxAge, new ServiceContext());
	}

	@Override
	public PasswordPolicy updatePasswordPolicy(
			long passwordPolicyId, String name, String description,
			boolean changeable, boolean changeRequired, long minAge,
			boolean checkSyntax, boolean allowDictionaryWords,
			int minAlphanumeric, int minLength, int minLowerCase,
			int minNumbers, int minSymbols, int minUpperCase, String regex,
			boolean history, int historyCount, boolean expireable, long maxAge,
			long warningTime, int graceLimit, boolean lockout, int maxFailure,
			long lockoutDuration, long resetFailureCount,
			long resetTicketMaxAge, ServiceContext serviceContext)
		throws PortalException {

		PasswordPolicy passwordPolicy =
			passwordPolicyPersistence.findByPrimaryKey(passwordPolicyId);

		if (!passwordPolicy.getDefaultPolicy()) {
			validate(passwordPolicyId, passwordPolicy.getCompanyId(), name);

			passwordPolicy.setName(name);
		}

		passwordPolicy.setDescription(description);
		passwordPolicy.setChangeable(changeable);
		passwordPolicy.setChangeRequired(changeRequired);
		passwordPolicy.setMinAge(minAge);
		passwordPolicy.setCheckSyntax(checkSyntax);
		passwordPolicy.setAllowDictionaryWords(allowDictionaryWords);
		passwordPolicy.setMinAlphanumeric(minAlphanumeric);
		passwordPolicy.setMinLength(minLength);
		passwordPolicy.setMinLowerCase(minLowerCase);
		passwordPolicy.setMinNumbers(minNumbers);
		passwordPolicy.setMinSymbols(minSymbols);
		passwordPolicy.setMinUpperCase(minUpperCase);
		passwordPolicy.setRegex(regex);
		passwordPolicy.setHistory(history);
		passwordPolicy.setHistoryCount(historyCount);
		passwordPolicy.setExpireable(expireable);
		passwordPolicy.setMaxAge(maxAge);
		passwordPolicy.setWarningTime(warningTime);
		passwordPolicy.setGraceLimit(graceLimit);
		passwordPolicy.setLockout(lockout);
		passwordPolicy.setMaxFailure(maxFailure);
		passwordPolicy.setLockoutDuration(lockoutDuration);
		passwordPolicy.setRequireUnlock(lockoutDuration == 0);
		passwordPolicy.setResetFailureCount(resetFailureCount);
		passwordPolicy.setResetTicketMaxAge(resetTicketMaxAge);
		passwordPolicy.setExpandoBridgeAttributes(serviceContext);

		passwordPolicyPersistence.update(passwordPolicy);

		return passwordPolicy;
	}

	protected void validate(long passwordPolicyId, long companyId, String name)
		throws PortalException {

		if (Validator.isNull(name) || Validator.isNumber(name) ||
			(name.indexOf(CharPool.COMMA) != -1) ||
			(name.indexOf(CharPool.STAR) != -1)) {

			throw new PasswordPolicyNameException();
		}

		PasswordPolicy passwordPolicy = passwordPolicyPersistence.fetchByC_N(
			companyId, name);

		if ((passwordPolicy != null) &&
			(passwordPolicy.getPasswordPolicyId() != passwordPolicyId)) {

			throw new DuplicatePasswordPolicyException(
				"{passwordPolicyId=" + passwordPolicyId + "}");
		}
	}

}