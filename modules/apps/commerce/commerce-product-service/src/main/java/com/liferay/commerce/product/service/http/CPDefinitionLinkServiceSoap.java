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

package com.liferay.commerce.product.service.http;

import com.liferay.commerce.product.service.CPDefinitionLinkServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CPDefinitionLinkServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.product.model.CPDefinitionLinkSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.product.model.CPDefinitionLink</code>, that is translated to a
 * <code>com.liferay.commerce.product.model.CPDefinitionLinkSoap</code>. Methods that SOAP
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
 * @see CPDefinitionLinkServiceHttp
 * @generated
 */
public class CPDefinitionLinkServiceSoap {

	public static com.liferay.commerce.product.model.CPDefinitionLinkSoap
			addCPDefinitionLink(
				long cpDefinitionId, long cProductId, double priority,
				String type,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinitionLink returnValue =
				CPDefinitionLinkServiceUtil.addCPDefinitionLink(
					cpDefinitionId, cProductId, priority, type, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionLinkSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCPDefinitionLink(long cpDefinitionLinkId)
		throws RemoteException {

		try {
			CPDefinitionLinkServiceUtil.deleteCPDefinitionLink(
				cpDefinitionLinkId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLinkSoap
			fetchCPDefinitionLink(long cpDefinitionLinkId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinitionLink returnValue =
				CPDefinitionLinkServiceUtil.fetchCPDefinitionLink(
					cpDefinitionLinkId);

			return com.liferay.commerce.product.model.CPDefinitionLinkSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLinkSoap
			getCPDefinitionLink(long cpDefinitionLinkId)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinitionLink returnValue =
				CPDefinitionLinkServiceUtil.getCPDefinitionLink(
					cpDefinitionLinkId);

			return com.liferay.commerce.product.model.CPDefinitionLinkSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLinkSoap[]
			getCPDefinitionLinks(long cpDefinitionId)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionLink>
				returnValue = CPDefinitionLinkServiceUtil.getCPDefinitionLinks(
					cpDefinitionId);

			return com.liferay.commerce.product.model.CPDefinitionLinkSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLinkSoap[]
			getCPDefinitionLinks(long cpDefinitionId, int start, int end)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionLink>
				returnValue = CPDefinitionLinkServiceUtil.getCPDefinitionLinks(
					cpDefinitionId, start, end);

			return com.liferay.commerce.product.model.CPDefinitionLinkSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLinkSoap[]
			getCPDefinitionLinks(long cpDefinitionId, String type)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionLink>
				returnValue = CPDefinitionLinkServiceUtil.getCPDefinitionLinks(
					cpDefinitionId, type);

			return com.liferay.commerce.product.model.CPDefinitionLinkSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLinkSoap[]
			getCPDefinitionLinks(
				long cpDefinitionId, String type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.product.model.CPDefinitionLink>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List<com.liferay.commerce.product.model.CPDefinitionLink>
				returnValue = CPDefinitionLinkServiceUtil.getCPDefinitionLinks(
					cpDefinitionId, type, start, end, orderByComparator);

			return com.liferay.commerce.product.model.CPDefinitionLinkSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPDefinitionLinksCount(long cpDefinitionId)
		throws RemoteException {

		try {
			int returnValue =
				CPDefinitionLinkServiceUtil.getCPDefinitionLinksCount(
					cpDefinitionId);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCPDefinitionLinksCount(
			long cpDefinitionId, String type)
		throws RemoteException {

		try {
			int returnValue =
				CPDefinitionLinkServiceUtil.getCPDefinitionLinksCount(
					cpDefinitionId, type);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CPDefinitionLinkSoap
			updateCPDefinitionLink(
				long cpDefinitionLinkId, double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.product.model.CPDefinitionLink returnValue =
				CPDefinitionLinkServiceUtil.updateCPDefinitionLink(
					cpDefinitionLinkId, priority, serviceContext);

			return com.liferay.commerce.product.model.CPDefinitionLinkSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void updateCPDefinitionLinks(
			long cpDefinitionId, long[] cpDefinitionIds2, String type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			CPDefinitionLinkServiceUtil.updateCPDefinitionLinks(
				cpDefinitionId, cpDefinitionIds2, type, serviceContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CPDefinitionLinkServiceSoap.class);

}