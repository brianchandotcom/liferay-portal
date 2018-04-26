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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link PasswordPolicyService}.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyService
 * @generated
 */
@ProviderType
public class PasswordPolicyServiceWrapper implements PasswordPolicyService,
	ServiceWrapper<PasswordPolicyService> {
	public PasswordPolicyServiceWrapper(
		PasswordPolicyService passwordPolicyService) {
		_passwordPolicyService = passwordPolicyService;
	}

	@Override
	public com.liferay.portal.kernel.model.PasswordPolicy addPasswordPolicy(
		String name, String description, boolean changeable,
		boolean changeRequired, long minAge, boolean checkSyntax,
		boolean allowDictionaryWords, int minAlphanumeric, int minLength,
		int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
		String regex, boolean history, int historyCount, boolean expireable,
		long maxAge, long warningTime, int graceLimit, boolean lockout,
		int maxFailure, long lockoutDuration, long resetFailureCount,
		long resetTicketMaxAge, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyService.addPasswordPolicy(name, description,
			changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minAlphanumeric, minLength, minLowerCase,
			minNumbers, minSymbols, minUpperCase, regex, history, historyCount,
			expireable, maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount, resetTicketMaxAge,
			serviceContext);
	}

	@Override
	public void deletePasswordPolicy(long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_passwordPolicyService.deletePasswordPolicy(passwordPolicyId);
	}

	@Override
	public com.liferay.portal.kernel.model.PasswordPolicy fetchPasswordPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyService.fetchPasswordPolicy(passwordPolicyId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _passwordPolicyService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.PasswordPolicy> search(
		long companyId, String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.PasswordPolicy> obc) {
		return _passwordPolicyService.search(companyId, name, start, end, obc);
	}

	@Override
	public int searchCount(long companyId, String name) {
		return _passwordPolicyService.searchCount(companyId, name);
	}

	@Override
	public com.liferay.portal.kernel.model.PasswordPolicy updatePasswordPolicy(
		long passwordPolicyId, String name, String description,
		boolean changeable, boolean changeRequired, long minAge,
		boolean checkSyntax, boolean allowDictionaryWords, int minAlphanumeric,
		int minLength, int minLowerCase, int minNumbers, int minSymbols,
		int minUpperCase, String regex, boolean history, int historyCount,
		boolean expireable, long maxAge, long warningTime, int graceLimit,
		boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount, long resetTicketMaxAge,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _passwordPolicyService.updatePasswordPolicy(passwordPolicyId,
			name, description, changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minAlphanumeric, minLength, minLowerCase,
			minNumbers, minSymbols, minUpperCase, regex, history, historyCount,
			expireable, maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount, resetTicketMaxAge,
			serviceContext);
	}

	@Override
	public PasswordPolicyService getWrappedService() {
		return _passwordPolicyService;
	}

	@Override
	public void setWrappedService(PasswordPolicyService passwordPolicyService) {
		_passwordPolicyService = passwordPolicyService;
	}

	private PasswordPolicyService _passwordPolicyService;
}