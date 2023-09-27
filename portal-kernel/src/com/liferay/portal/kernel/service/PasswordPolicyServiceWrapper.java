/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link PasswordPolicyService}.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyService
 * @generated
 */
public class PasswordPolicyServiceWrapper
	implements PasswordPolicyService, ServiceWrapper<PasswordPolicyService> {

	public PasswordPolicyServiceWrapper() {
		this(null);
	}

	public PasswordPolicyServiceWrapper(
		PasswordPolicyService passwordPolicyService) {

		_passwordPolicyService = passwordPolicyService;
	}

	@Override
	public com.liferay.portal.kernel.model.PasswordPolicy addPasswordPolicy(
			java.lang.String name, java.lang.String description,
			boolean changeable, boolean changeRequired, long minAge,
			boolean checkSyntax, boolean allowDictionaryWords,
			int minAlphanumeric, int minLength, int minLowerCase,
			int minNumbers, int minSymbols, int minUpperCase,
			java.lang.String regex, boolean history, int historyCount,
			boolean expireable, long maxAge, long warningTime, int graceLimit,
			boolean lockout, int maxFailure, long lockoutDuration,
			long resetFailureCount, long resetTicketMaxAge,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordPolicyService.addPasswordPolicy(
			name, description, changeable, changeRequired, minAge, checkSyntax,
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
	public java.lang.String getOSGiServiceIdentifier() {
		return _passwordPolicyService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.PasswordPolicy>
		search(
			long companyId, java.lang.String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.PasswordPolicy>
					orderByComparator) {

		return _passwordPolicyService.search(
			companyId, name, start, end, orderByComparator);
	}

	@Override
	public int searchCount(long companyId, java.lang.String name) {
		return _passwordPolicyService.searchCount(companyId, name);
	}

	@Override
	public com.liferay.portal.kernel.model.PasswordPolicy updatePasswordPolicy(
			long passwordPolicyId, java.lang.String name,
			java.lang.String description, boolean changeable,
			boolean changeRequired, long minAge, boolean checkSyntax,
			boolean allowDictionaryWords, int minAlphanumeric, int minLength,
			int minLowerCase, int minNumbers, int minSymbols, int minUpperCase,
			java.lang.String regex, boolean history, int historyCount,
			boolean expireable, long maxAge, long warningTime, int graceLimit,
			boolean lockout, int maxFailure, long lockoutDuration,
			long resetFailureCount, long resetTicketMaxAge,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _passwordPolicyService.updatePasswordPolicy(
			passwordPolicyId, name, description, changeable, changeRequired,
			minAge, checkSyntax, allowDictionaryWords, minAlphanumeric,
			minLength, minLowerCase, minNumbers, minSymbols, minUpperCase,
			regex, history, historyCount, expireable, maxAge, warningTime,
			graceLimit, lockout, maxFailure, lockoutDuration, resetFailureCount,
			resetTicketMaxAge, serviceContext);
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