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

package com.liferay.commerce.price.list.service.http;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.price.list.service.CommercePriceListServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * {@link CommercePriceListServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.commerce.price.list.model.CommercePriceListSoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.commerce.price.list.model.CommercePriceList}, that is translated to a
 * {@link com.liferay.commerce.price.list.model.CommercePriceListSoap}. Methods that SOAP cannot
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
 * @see CommercePriceListServiceHttp
 * @see com.liferay.commerce.price.list.model.CommercePriceListSoap
 * @see CommercePriceListServiceUtil
 * @generated
 */
@ProviderType
public class CommercePriceListServiceSoap {
	public static com.liferay.commerce.price.list.model.CommercePriceListSoap addCommercePriceList(
		long commerceCurrencyId, String name, double priority,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.price.list.model.CommercePriceList returnValue = CommercePriceListServiceUtil.addCommercePriceList(commerceCurrencyId,
					name, priority, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteCommercePriceList(long commercePriceListId)
		throws RemoteException {
		try {
			CommercePriceListServiceUtil.deleteCommercePriceList(commercePriceListId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap fetchCommercePriceList(
		long commercePriceListId) throws RemoteException {
		try {
			com.liferay.commerce.price.list.model.CommercePriceList returnValue = CommercePriceListServiceUtil.fetchCommercePriceList(commercePriceListId);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap[] getCommercePriceLists(
		long groupId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.model.CommercePriceList> returnValue =
				CommercePriceListServiceUtil.getCommercePriceLists(groupId,
					start, end);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap[] getCommercePriceLists(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceList> orderByComparator)
		throws RemoteException {
		try {
			java.util.List<com.liferay.commerce.price.list.model.CommercePriceList> returnValue =
				CommercePriceListServiceUtil.getCommercePriceLists(groupId,
					status, start, end, orderByComparator);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCommercePriceListsCount(long groupId, int status)
		throws RemoteException {
		try {
			int returnValue = CommercePriceListServiceUtil.getCommercePriceListsCount(groupId,
					status);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap updateCommercePriceList(
		long commercePriceListId, long commerceCurrencyId, String name,
		double priority, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.commerce.price.list.model.CommercePriceList returnValue = CommercePriceListServiceUtil.updateCommercePriceList(commercePriceListId,
					commerceCurrencyId, name, priority, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CommercePriceListServiceSoap.class);
}