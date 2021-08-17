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

package com.liferay.commerce.service.http;

import com.liferay.commerce.service.CommerceOrderTypeRelServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommerceOrderTypeRelServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.model.CommerceOrderTypeRelSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.model.CommerceOrderTypeRel</code>, that is translated to a
 * <code>com.liferay.commerce.model.CommerceOrderTypeRelSoap</code>. Methods that SOAP
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
 * @author Alessio Antonio Rendina
 * @see CommerceOrderTypeRelServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommerceOrderTypeRelServiceSoap {

	public static com.liferay.commerce.model.CommerceOrderTypeRelSoap
			addCommerceOrderTypeRel(
				String className, long classPK, long commerceOrderTypeId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrderTypeRel returnValue =
				CommerceOrderTypeRelServiceUtil.addCommerceOrderTypeRel(
					className, classPK, commerceOrderTypeId, serviceContext);

			return com.liferay.commerce.model.CommerceOrderTypeRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeRelSoap
			deleteCommerceOrderTypeRel(long commerceOrderTypeRelId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrderTypeRel returnValue =
				CommerceOrderTypeRelServiceUtil.deleteCommerceOrderTypeRel(
					commerceOrderTypeRelId);

			return com.liferay.commerce.model.CommerceOrderTypeRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommerceOrderTypeRels(
			String className, long commerceOrderTypeId)
		throws RemoteException {

		try {
			CommerceOrderTypeRelServiceUtil.deleteCommerceOrderTypeRels(
				className, commerceOrderTypeId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeRelSoap[]
			getCommerceOrderTypeCommerceChannelRels(
				long commerceOrderTypeId, String keywords, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrderTypeRel>
				returnValue =
					CommerceOrderTypeRelServiceUtil.
						getCommerceOrderTypeCommerceChannelRels(
							commerceOrderTypeId, keywords, start, end);

			return com.liferay.commerce.model.CommerceOrderTypeRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderTypeCommerceChannelRelsCount(
			long commerceOrderTypeId, String keywords)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderTypeRelServiceUtil.
					getCommerceOrderTypeCommerceChannelRelsCount(
						commerceOrderTypeId, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeRelSoap
			getCommerceOrderTypeRel(long commerceOrderTypeRelId)
		throws RemoteException {

		try {
			com.liferay.commerce.model.CommerceOrderTypeRel returnValue =
				CommerceOrderTypeRelServiceUtil.getCommerceOrderTypeRel(
					commerceOrderTypeRelId);

			return com.liferay.commerce.model.CommerceOrderTypeRelSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.model.CommerceOrderTypeRelSoap[]
			getCommerceOrderTypeRels(
				String className, long classPK, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceOrderTypeRel>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.model.CommerceOrderTypeRel>
				returnValue =
					CommerceOrderTypeRelServiceUtil.getCommerceOrderTypeRels(
						className, classPK, start, end, orderByComparator);

			return com.liferay.commerce.model.CommerceOrderTypeRelSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommerceOrderTypeRelsCount(
			String className, long classPK)
		throws RemoteException {

		try {
			int returnValue =
				CommerceOrderTypeRelServiceUtil.getCommerceOrderTypeRelsCount(
					className, classPK);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommerceOrderTypeRelServiceSoap.class);

}