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

package com.liferay.commerce.discount.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.service.CommerceDiscountRelServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceDiscountRelServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.discount.model.CommerceDiscountRelSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.discount.model.CommerceDiscountRel}, that is translated to a
 * {@link com.liferay.commerce.discount.model.CommerceDiscountRelSoap}. Methods that SOAP cannot
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
 * @see CommerceDiscountRelServiceHttp
 * @see com.liferay.commerce.discount.model.CommerceDiscountRelSoap
 * @see CommerceDiscountRelServiceUtil
 * @generated
 */
@ProviderType
public class CommerceDiscountRelServiceSoap {
	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap addCommerceDiscountRel(
		long commerceDiscountId, String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.discount.model.CommerceDiscountRel returnValue = CommerceDiscountRelServiceUtil.addCommerceDiscountRel(commerceDiscountId,
					className, classPK, serviceContext);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceDiscountRel(long commerceDiscountRelId)
		throws RemoteException {
		try {
			CommerceDiscountRelServiceUtil.deleteCommerceDiscountRel(commerceDiscountRelId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static long[] getClassPKs(long commerceDiscountId, String className)
		throws RemoteException {
		try {
			long[] returnValue = CommerceDiscountRelServiceUtil.getClassPKs(commerceDiscountId,
					className);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap getCommerceDiscountRel(
		long commerceDiscountRelId) throws RemoteException {
		try {
			com.liferay.commerce.discount.model.CommerceDiscountRel returnValue = CommerceDiscountRelServiceUtil.getCommerceDiscountRel(commerceDiscountRelId);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap[] getCommerceDiscountRels(
		long commerceDiscountId, String className) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel> returnValue =
				CommerceDiscountRelServiceUtil.getCommerceDiscountRels(commerceDiscountId,
					className);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRelSoap[] getCommerceDiscountRels(
		long commerceDiscountId, String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscountRel> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel> returnValue =
				CommerceDiscountRelServiceUtil.getCommerceDiscountRels(commerceDiscountId,
					className, start, end, orderByComparator);

			return com.liferay.commerce.discount.model.CommerceDiscountRelSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceDiscountRelsCount(long commerceDiscountId,
		String className) throws RemoteException {
		try {
			int returnValue = CommerceDiscountRelServiceUtil.getCommerceDiscountRelsCount(commerceDiscountId,
					className);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceDiscountRelServiceSoap.class);
}