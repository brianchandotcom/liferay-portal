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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.PasswordPolicyServiceUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>PasswordPolicyServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portal.kernel.model.PasswordPolicySoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.portal.kernel.model.PasswordPolicy</code>, that is translated to a
 * <code>com.liferay.portal.kernel.model.PasswordPolicySoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicyServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class PasswordPolicyServiceSoap {

	public static com.liferay.portal.kernel.model.PasswordPolicySoap
			addPasswordPolicy(
				String name, String description, boolean changeable,
				boolean changeRequired, long minAge, boolean checkSyntax,
				boolean allowDictionaryWords, int minAlphanumeric,
				int minLength, int minLowerCase, int minNumbers, int minSymbols,
				int minUpperCase, String regex, boolean history,
				int historyCount, boolean expireable, long maxAge,
				long warningTime, int graceLimit, boolean lockout,
				int maxFailure, long lockoutDuration, long resetFailureCount,
				long resetTicketMaxAge,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.PasswordPolicy returnValue =
				PasswordPolicyServiceUtil.addPasswordPolicy(
					name, description, changeable, changeRequired, minAge,
					checkSyntax, allowDictionaryWords, minAlphanumeric,
					minLength, minLowerCase, minNumbers, minSymbols,
					minUpperCase, regex, history, historyCount, expireable,
					maxAge, warningTime, graceLimit, lockout, maxFailure,
					lockoutDuration, resetFailureCount, resetTicketMaxAge,
					serviceContext);

			return com.liferay.portal.kernel.model.PasswordPolicySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deletePasswordPolicy(long passwordPolicyId)
		throws RemoteException {

		try {
			PasswordPolicyServiceUtil.deletePasswordPolicy(passwordPolicyId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.PasswordPolicySoap
			fetchPasswordPolicy(long passwordPolicyId)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.PasswordPolicy returnValue =
				PasswordPolicyServiceUtil.fetchPasswordPolicy(passwordPolicyId);

			return com.liferay.portal.kernel.model.PasswordPolicySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.PasswordPolicySoap[] search(
			long companyId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.PasswordPolicy>
					orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.portal.kernel.model.PasswordPolicy>
				returnValue = PasswordPolicyServiceUtil.search(
					companyId, name, start, end, orderByComparator);

			return com.liferay.portal.kernel.model.PasswordPolicySoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCount(long companyId, String name)
		throws RemoteException {

		try {
			int returnValue = PasswordPolicyServiceUtil.searchCount(
				companyId, name);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.portal.kernel.model.PasswordPolicySoap
			updatePasswordPolicy(
				long passwordPolicyId, String name, String description,
				boolean changeable, boolean changeRequired, long minAge,
				boolean checkSyntax, boolean allowDictionaryWords,
				int minAlphanumeric, int minLength, int minLowerCase,
				int minNumbers, int minSymbols, int minUpperCase, String regex,
				boolean history, int historyCount, boolean expireable,
				long maxAge, long warningTime, int graceLimit, boolean lockout,
				int maxFailure, long lockoutDuration, long resetFailureCount,
				long resetTicketMaxAge,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.portal.kernel.model.PasswordPolicy returnValue =
				PasswordPolicyServiceUtil.updatePasswordPolicy(
					passwordPolicyId, name, description, changeable,
					changeRequired, minAge, checkSyntax, allowDictionaryWords,
					minAlphanumeric, minLength, minLowerCase, minNumbers,
					minSymbols, minUpperCase, regex, history, historyCount,
					expireable, maxAge, warningTime, graceLimit, lockout,
					maxFailure, lockoutDuration, resetFailureCount,
					resetTicketMaxAge, serviceContext);

			return com.liferay.portal.kernel.model.PasswordPolicySoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PasswordPolicyServiceSoap.class);

}