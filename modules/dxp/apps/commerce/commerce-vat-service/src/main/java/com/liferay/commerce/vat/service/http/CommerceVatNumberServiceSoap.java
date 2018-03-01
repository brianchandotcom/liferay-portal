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

package com.liferay.commerce.vat.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.vat.service.CommerceVatNumberServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommerceVatNumberServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.vat.model.CommerceVatNumberSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.vat.model.CommerceVatNumber}, that is translated to a
 * {@link com.liferay.commerce.vat.model.CommerceVatNumberSoap}. Methods that SOAP cannot
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
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberServiceHttp
 * @see com.liferay.commerce.vat.model.CommerceVatNumberSoap
 * @see CommerceVatNumberServiceUtil
 * @generated
 */
@ProviderType
public class CommerceVatNumberServiceSoap {
	public static com.liferay.commerce.vat.model.CommerceVatNumberSoap addCommerceVatNumber(
		java.lang.String className, long classPK, java.lang.String vatNumber,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.vat.model.CommerceVatNumber returnValue = CommerceVatNumberServiceUtil.addCommerceVatNumber(className,
					classPK, vatNumber, serviceContext);

			return com.liferay.commerce.vat.model.CommerceVatNumberSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommerceVatNumber(long commerceVatNumberId)
		throws RemoteException {
		try {
			CommerceVatNumberServiceUtil.deleteCommerceVatNumber(commerceVatNumberId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumberSoap fetchCommerceVatNumber(
		long commerceVatNumberId) throws RemoteException {
		try {
			com.liferay.commerce.vat.model.CommerceVatNumber returnValue = CommerceVatNumberServiceUtil.fetchCommerceVatNumber(commerceVatNumberId);

			return com.liferay.commerce.vat.model.CommerceVatNumberSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumberSoap fetchCommerceVatNumber(
		long groupId, java.lang.String className, long classPK)
		throws RemoteException {
		try {
			com.liferay.commerce.vat.model.CommerceVatNumber returnValue = CommerceVatNumberServiceUtil.fetchCommerceVatNumber(groupId,
					className, classPK);

			return com.liferay.commerce.vat.model.CommerceVatNumberSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumberSoap[] getCommerceVatNumbers(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.vat.model.CommerceVatNumber> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.vat.model.CommerceVatNumber> returnValue =
				CommerceVatNumberServiceUtil.getCommerceVatNumbers(groupId,
					start, end, orderByComparator);

			return com.liferay.commerce.vat.model.CommerceVatNumberSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommerceVatNumbersCount(long groupId)
		throws RemoteException {
		try {
			int returnValue = CommerceVatNumberServiceUtil.getCommerceVatNumbersCount(groupId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.vat.model.CommerceVatNumberSoap updateCommerceVatNumber(
		long commerceVatNumberId, java.lang.String vatNumber)
		throws RemoteException {
		try {
			com.liferay.commerce.vat.model.CommerceVatNumber returnValue = CommerceVatNumberServiceUtil.updateCommerceVatNumber(commerceVatNumberId,
					vatNumber);

			return com.liferay.commerce.vat.model.CommerceVatNumberSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommerceVatNumberServiceSoap.class);
}