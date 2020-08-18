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

package com.liferay.commerce.account.service.http;

import com.liferay.commerce.account.service.CommerceAccountGroupServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceAccountGroupServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.account.model.CommerceAccountGroupSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.account.model.CommerceAccountGroup</code>, that is translated to a
 * <code>com.liferay.commerce.account.model.CommerceAccountGroupSoap</code>. Methods that SOAP
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
 * @author Marco Leo
 * @see CommerceAccountGroupServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceAccountGroupServiceSoap {

	public static com.liferay.commerce.account.model.CommerceAccountGroupSoap
			addCommerceAccountGroup(
				long companyId, String name, int type,
				String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccountGroup
				returnValue =
					CommerceAccountGroupServiceUtil.addCommerceAccountGroup(
						companyId, name, type, externalReferenceCode,
						serviceContext);

			return com.liferay.commerce.account.model.CommerceAccountGroupSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceAccountGroup(long commerceAccountGroupId)
		throws RemoteException {

		try {
			CommerceAccountGroupServiceUtil.deleteCommerceAccountGroup(
				commerceAccountGroupId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroupSoap
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccountGroup
				returnValue =
					CommerceAccountGroupServiceUtil.
						fetchByExternalReferenceCode(
							companyId, externalReferenceCode);

			return com.liferay.commerce.account.model.CommerceAccountGroupSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroupSoap
			getCommerceAccountGroup(long commerceAccountGroupId)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccountGroup
				returnValue =
					CommerceAccountGroupServiceUtil.getCommerceAccountGroup(
						commerceAccountGroupId);

			return com.liferay.commerce.account.model.CommerceAccountGroupSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroupSoap[]
			getCommerceAccountGroups(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.account.model.CommerceAccountGroup>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.account.model.CommerceAccountGroup>
					returnValue =
						CommerceAccountGroupServiceUtil.
							getCommerceAccountGroups(
								companyId, start, end, orderByComparator);

			return com.liferay.commerce.account.model.CommerceAccountGroupSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceAccountGroupsCount(long companyId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAccountGroupServiceUtil.getCommerceAccountGroupsCount(
					companyId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroupSoap[]
			searchCommerceAccountGroups(
				long companyId, String keywords, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.account.model.CommerceAccountGroup>
					returnValue =
						CommerceAccountGroupServiceUtil.
							searchCommerceAccountGroups(
								companyId, keywords, start, end, sort);

			return com.liferay.commerce.account.model.CommerceAccountGroupSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCommerceAccountsGroupCount(
			long companyId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAccountGroupServiceUtil.
					searchCommerceAccountsGroupCount(companyId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.account.model.CommerceAccountGroupSoap
			updateCommerceAccountGroup(
				long commerceAccountGroupId, String name,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccountGroup
				returnValue =
					CommerceAccountGroupServiceUtil.updateCommerceAccountGroup(
						commerceAccountGroupId, name, serviceContext);

			return com.liferay.commerce.account.model.CommerceAccountGroupSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceAccountGroupServiceSoap.class);

}