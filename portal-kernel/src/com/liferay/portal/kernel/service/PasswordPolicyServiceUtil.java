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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the remote service utility for PasswordPolicy. This utility wraps
 * {@link com.liferay.portal.service.impl.PasswordPolicyServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyService
 * @see com.liferay.portal.service.base.PasswordPolicyServiceBaseImpl
 * @see com.liferay.portal.service.impl.PasswordPolicyServiceImpl
 * @generated
 */
@ProviderType
public class PasswordPolicyServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.PasswordPolicyServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.model.PasswordPolicy addPasswordPolicy(
		java.lang.String name, java.lang.String description,
		boolean changeable, boolean changeRequired, long minAge,
		boolean checkSyntax, boolean allowDictionaryWords, int minAlphanumeric,
		int minLength, int minLowerCase, int minNumbers, int minSymbols,
		int minUpperCase, java.lang.String regex, boolean history,
		int historyCount, boolean expireable, long maxAge, long warningTime,
		int graceLimit, boolean lockout, int maxFailure, long lockoutDuration,
		long resetFailureCount, long resetTicketMaxAge,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addPasswordPolicy(name, description, changeable,
			changeRequired, minAge, checkSyntax, allowDictionaryWords,
			minAlphanumeric, minLength, minLowerCase, minNumbers, minSymbols,
			minUpperCase, regex, history, historyCount, expireable, maxAge,
			warningTime, graceLimit, lockout, maxFailure, lockoutDuration,
			resetFailureCount, resetTicketMaxAge, serviceContext);
	}

	public static com.liferay.portal.kernel.model.PasswordPolicy fetchPasswordPolicy(
		long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().fetchPasswordPolicy(passwordPolicyId);
	}

	public static com.liferay.portal.kernel.model.PasswordPolicy updatePasswordPolicy(
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
		return getService()
				   .updatePasswordPolicy(passwordPolicyId, name, description,
			changeable, changeRequired, minAge, checkSyntax,
			allowDictionaryWords, minAlphanumeric, minLength, minLowerCase,
			minNumbers, minSymbols, minUpperCase, regex, history, historyCount,
			expireable, maxAge, warningTime, graceLimit, lockout, maxFailure,
			lockoutDuration, resetFailureCount, resetTicketMaxAge,
			serviceContext);
	}

	public static int searchCount(long companyId, java.lang.String name) {
		return getService().searchCount(companyId, name);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.portal.kernel.model.PasswordPolicy> search(
		long companyId, java.lang.String name, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.kernel.model.PasswordPolicy> obc) {
		return getService().search(companyId, name, start, end, obc);
	}

	public static void deletePasswordPolicy(long passwordPolicyId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deletePasswordPolicy(passwordPolicyId);
	}

	public static PasswordPolicyService getService() {
		if (_service == null) {
			_service = (PasswordPolicyService)PortalBeanLocatorUtil.locate(PasswordPolicyService.class.getName());

			ReferenceRegistry.registerReference(PasswordPolicyServiceUtil.class,
				"_service");
		}

		return _service;
	}

	private static PasswordPolicyService _service;
}