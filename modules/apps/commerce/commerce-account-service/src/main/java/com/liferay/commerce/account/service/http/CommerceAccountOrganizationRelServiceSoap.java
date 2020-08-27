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

import com.liferay.commerce.account.service.CommerceAccountOrganizationRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceAccountOrganizationRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.account.model.CommerceAccountOrganizationRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.account.model.CommerceAccountOrganizationRel</code>, that is translated to a
 * <code>com.liferay.commerce.account.model.CommerceAccountOrganizationRelSoap</code>. Methods that SOAP
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
 * @see CommerceAccountOrganizationRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceAccountOrganizationRelServiceSoap {

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRelSoap
				addCommerceAccountOrganizationRel(
					long commerceAccountId, long organizationId,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				returnValue =
					CommerceAccountOrganizationRelServiceUtil.
						addCommerceAccountOrganizationRel(
							commerceAccountId, organizationId, serviceContext);

			return com.liferay.commerce.account.model.
				CommerceAccountOrganizationRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void addCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			CommerceAccountOrganizationRelServiceUtil.
				addCommerceAccountOrganizationRels(
					commerceAccountId, organizationIds, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceAccountOrganizationRel(
			long commerceAccountId, long organizationId)
		throws RemoteException {

		try {
			CommerceAccountOrganizationRelServiceUtil.
				deleteCommerceAccountOrganizationRel(
					commerceAccountId, organizationId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceAccountOrganizationRels(
			long commerceAccountId, long[] organizationIds)
		throws RemoteException {

		try {
			CommerceAccountOrganizationRelServiceUtil.
				deleteCommerceAccountOrganizationRels(
					commerceAccountId, organizationIds);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRelSoap
				fetchCommerceAccountOrganizationRel(
					com.liferay.commerce.account.service.persistence.
						CommerceAccountOrganizationRelPK
							commerceAccountOrganizationRelPK)
			throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				returnValue =
					CommerceAccountOrganizationRelServiceUtil.
						fetchCommerceAccountOrganizationRel(
							commerceAccountOrganizationRelPK);

			return com.liferay.commerce.account.model.
				CommerceAccountOrganizationRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRelSoap
				getCommerceAccountOrganizationRel(
					com.liferay.commerce.account.service.persistence.
						CommerceAccountOrganizationRelPK
							commerceAccountOrganizationRelPK)
			throws RemoteException {

		try {
			com.liferay.commerce.account.model.CommerceAccountOrganizationRel
				returnValue =
					CommerceAccountOrganizationRelServiceUtil.
						getCommerceAccountOrganizationRel(
							commerceAccountOrganizationRelPK);

			return com.liferay.commerce.account.model.
				CommerceAccountOrganizationRelSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRelSoap[]
				getCommerceAccountOrganizationRels(long commerceAccountId)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.account.model.
					CommerceAccountOrganizationRel> returnValue =
						CommerceAccountOrganizationRelServiceUtil.
							getCommerceAccountOrganizationRels(
								commerceAccountId);

			return com.liferay.commerce.account.model.
				CommerceAccountOrganizationRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRelSoap[]
				getCommerceAccountOrganizationRels(
					long commerceAccountId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.account.model.
					CommerceAccountOrganizationRel> returnValue =
						CommerceAccountOrganizationRelServiceUtil.
							getCommerceAccountOrganizationRels(
								commerceAccountId, start, end);

			return com.liferay.commerce.account.model.
				CommerceAccountOrganizationRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.commerce.account.model.CommerceAccountOrganizationRelSoap[]
				getCommerceAccountOrganizationRelsByOrganizationId(
					long organizationId, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.account.model.
					CommerceAccountOrganizationRel> returnValue =
						CommerceAccountOrganizationRelServiceUtil.
							getCommerceAccountOrganizationRelsByOrganizationId(
								organizationId, start, end);

			return com.liferay.commerce.account.model.
				CommerceAccountOrganizationRelSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceAccountOrganizationRelsByOrganizationIdCount(
			long organizationId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAccountOrganizationRelServiceUtil.
					getCommerceAccountOrganizationRelsByOrganizationIdCount(
						organizationId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceAccountOrganizationRelsCount(
			long commerceAccountId)
		throws RemoteException {

		try {
			int returnValue =
				CommerceAccountOrganizationRelServiceUtil.
					getCommerceAccountOrganizationRelsCount(commerceAccountId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceAccountOrganizationRelServiceSoap.class);

}