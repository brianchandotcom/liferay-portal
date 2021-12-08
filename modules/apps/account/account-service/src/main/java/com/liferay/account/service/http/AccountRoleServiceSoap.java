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

package com.liferay.account.service.http;

import com.liferay.account.service.AccountRoleServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>AccountRoleServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.account.model.AccountRoleSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.account.model.AccountRole</code>, that is translated to a
 * <code>com.liferay.account.model.AccountRoleSoap</code>. Methods that SOAP
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
 * @see AccountRoleServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class AccountRoleServiceSoap {

	public static com.liferay.account.model.AccountRoleSoap addAccountRole(
			long accountEntryId, String name, String[] titleMapLanguageIds,
			String[] titleMapValues, String[] descriptionMapLanguageIds,
			String[] descriptionMapValues)
		throws RemoteException {

		try {
			Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
				titleMapLanguageIds, titleMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.account.model.AccountRole returnValue =
				AccountRoleServiceUtil.addAccountRole(
					accountEntryId, name, titleMap, descriptionMap);

			return com.liferay.account.model.AccountRoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void associateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws RemoteException {

		try {
			AccountRoleServiceUtil.associateUser(
				accountEntryId, accountRoleId, userId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void associateUser(
			long accountEntryId, long[] accountRoleIds, long userId)
		throws RemoteException {

		try {
			AccountRoleServiceUtil.associateUser(
				accountEntryId, accountRoleIds, userId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.account.model.AccountRoleSoap deleteAccountRole(
			com.liferay.account.model.AccountRoleSoap accountRole)
		throws RemoteException {

		try {
			com.liferay.account.model.AccountRole returnValue =
				AccountRoleServiceUtil.deleteAccountRole(
					com.liferay.account.model.impl.AccountRoleModelImpl.toModel(
						accountRole));

			return com.liferay.account.model.AccountRoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.account.model.AccountRoleSoap deleteAccountRole(
			long accountRoleId)
		throws RemoteException {

		try {
			com.liferay.account.model.AccountRole returnValue =
				AccountRoleServiceUtil.deleteAccountRole(accountRoleId);

			return com.liferay.account.model.AccountRoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.account.model.AccountRoleSoap
			getAccountRoleByRoleId(long roleId)
		throws RemoteException {

		try {
			com.liferay.account.model.AccountRole returnValue =
				AccountRoleServiceUtil.getAccountRoleByRoleId(roleId);

			return com.liferay.account.model.AccountRoleSoap.toSoapModel(
				returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void setUserAccountRoles(
			long accountEntryId, long[] accountRoleIds, long userId)
		throws RemoteException {

		try {
			AccountRoleServiceUtil.setUserAccountRoles(
				accountEntryId, accountRoleIds, userId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void unassociateUser(
			long accountEntryId, long accountRoleId, long userId)
		throws RemoteException {

		try {
			AccountRoleServiceUtil.unassociateUser(
				accountEntryId, accountRoleId, userId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AccountRoleServiceSoap.class);

}