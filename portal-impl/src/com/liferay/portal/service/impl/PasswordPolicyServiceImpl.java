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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.PasswordPolicy;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PasswordPolicyPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.base.PasswordPolicyServiceBaseImpl;

import java.util.List;

/**
 * @author Scott Lee
 */
public class PasswordPolicyServiceImpl extends PasswordPolicyServiceBaseImpl {

	@Override
	public PasswordPolicy addPasswordPolicy(
			String name, String description, boolean changeable,
			boolean changeRequired, long minAge, boolean checkSyntax,
			boolean allowDictionaryWords, int minAlphanumeric, int minLength,
			int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
			String regex, boolean history, int historyCount, boolean expireable,
			long maxAge, long warningTime, int graceLimit, boolean lockout,
			int maxFailure, long lockoutDuration, long resetFailureCount,
			long resetTicketMaxAge, ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.ADD_PASSWORD_POLICY);

		return passwordPolicyLocalService.addPasswordPolicy(
			getUserId(), false, name, description, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minAlphanumeric,
			minLength, minLowerCase, minNumbers, minSymbols, minUpperCase,
			regex, history, historyCount, expireable, maxAge, warningTime,
			graceLimit, lockout, maxFailure, lockoutDuration, resetFailureCount,
			resetTicketMaxAge, serviceContext);
	}

	@Override
	public void deletePasswordPolicy(long passwordPolicyId)
		throws PortalException {

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId, ActionKeys.DELETE);

		passwordPolicyLocalService.deletePasswordPolicy(passwordPolicyId);
	}

	@Override
	public PasswordPolicy fetchPasswordPolicy(long passwordPolicyId)
		throws PortalException {

		PasswordPolicy passwordPolicy =
			passwordPolicyLocalService.fetchPasswordPolicy(passwordPolicyId);

		if (passwordPolicy != null) {
			PasswordPolicyPermissionUtil.check(
				getPermissionChecker(), passwordPolicyId, ActionKeys.VIEW);
		}

		return passwordPolicy;
	}

	@Override
	public List<PasswordPolicy> search(
		long companyId, String name, int start, int end,
		OrderByComparator<PasswordPolicy> obc) {

		return passwordPolicyFinder.filterFindByC_N(
			companyId, name, start, end, obc);
	}

	@Override
	public int searchCount(long companyId, String name) {
		return passwordPolicyFinder.filterCountByC_N(companyId, name);
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

		PasswordPolicyPermissionUtil.check(
			getPermissionChecker(), passwordPolicyId, ActionKeys.UPDATE);

		return passwordPolicyLocalService.updatePasswordPolicy(
			passwordPolicyId, name, description, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minAlphanumeric,
			minLength, minLowerCase, minNumbers, minSymbols, minUpperCase,
			regex, history, historyCount, expireable, maxAge, warningTime,
			graceLimit, lockout, maxFailure, lockoutDuration, resetFailureCount,
			resetTicketMaxAge, serviceContext);
	}

}