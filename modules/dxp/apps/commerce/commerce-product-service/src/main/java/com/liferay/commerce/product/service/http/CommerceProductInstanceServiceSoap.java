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

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.service.CommerceProductInstanceServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceProductInstanceServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.product.model.CommerceProductInstanceSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.product.model.CommerceProductInstance}, that is translated to a
 * {@link com.liferay.commerce.product.model.CommerceProductInstanceSoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * @see CommerceProductInstanceServiceHttp
 * @see com.liferay.commerce.product.model.CommerceProductInstanceSoap
 * @see CommerceProductInstanceServiceUtil
 * @generated
 */
@ProviderType
public class CommerceProductInstanceServiceSoap {
	public static com.liferay.commerce.product.model.CommerceProductInstanceSoap addCommerceProductInstance(
		long commerceProductDefinitionId, java.lang.String sku,
		java.lang.String ddmContent, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CommerceProductInstance returnValue =
				CommerceProductInstanceServiceUtil.addCommerceProductInstance(commerceProductDefinitionId,
					sku, ddmContent, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			return com.liferay.commerce.product.model.CommerceProductInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductInstanceSoap deleteCommerceProductInstance(
		com.liferay.commerce.product.model.CommerceProductInstanceSoap commerceProductInstance)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CommerceProductInstance returnValue =
				CommerceProductInstanceServiceUtil.deleteCommerceProductInstance(com.liferay.commerce.product.model.impl.CommerceProductInstanceModelImpl.toModel(
						commerceProductInstance));

			return com.liferay.commerce.product.model.CommerceProductInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductInstanceSoap deleteCommerceProductInstance(
		long commerceProductInstanceId) throws RemoteException {
		try {
			com.liferay.commerce.product.model.CommerceProductInstance returnValue =
				CommerceProductInstanceServiceUtil.deleteCommerceProductInstance(commerceProductInstanceId);

			return com.liferay.commerce.product.model.CommerceProductInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductInstanceSoap[] getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> returnValue =
				CommerceProductInstanceServiceUtil.getCommerceProductInstances(commerceProductDefinitionId,
					start, end);

			return com.liferay.commerce.product.model.CommerceProductInstanceSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductInstanceSoap[] getCommerceProductInstances(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductInstance> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.product.model.CommerceProductInstance> returnValue =
				CommerceProductInstanceServiceUtil.getCommerceProductInstances(commerceProductDefinitionId,
					start, end, orderByComparator);

			return com.liferay.commerce.product.model.CommerceProductInstanceSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceProductInstancesCount(
		long commerceProductDefinitionId) throws RemoteException {
		try {
			int returnValue = CommerceProductInstanceServiceUtil.getCommerceProductInstancesCount(commerceProductDefinitionId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.product.model.CommerceProductInstanceSoap updateCommerceProductInstance(
		long commerceProductInstanceId, java.lang.String sku,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.product.model.CommerceProductInstance returnValue =
				CommerceProductInstanceServiceUtil.updateCommerceProductInstance(commerceProductInstanceId,
					sku, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.product.model.CommerceProductInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceProductInstanceServiceSoap.class);
}