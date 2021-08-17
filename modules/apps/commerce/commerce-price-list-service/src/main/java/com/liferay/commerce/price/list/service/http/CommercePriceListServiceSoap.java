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

import com.liferay.commerce.price.list.service.CommercePriceListServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.rmi.RemoteException;

/**
 * Provides the SOAP utility for the
 * <code>CommercePriceListServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.commerce.price.list.model.CommercePriceListSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.commerce.price.list.model.CommercePriceList</code>, that is translated to a
 * <code>com.liferay.commerce.price.list.model.CommercePriceListSoap</code>. Methods that SOAP
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
 * @see CommercePriceListServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class CommercePriceListServiceSoap {

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			addCommercePriceList(
				String externalReferenceCode, long groupId,
				long commerceCurrencyId, boolean netPrice, String type,
				long parentCommercePriceListId, boolean catalogBasePriceList,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue = CommercePriceListServiceUtil.addCommercePriceList(
					externalReferenceCode, groupId, commerceCurrencyId,
					netPrice, type, parentCommercePriceListId,
					catalogBasePriceList, name, priority, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			addOrUpdateCommercePriceList(
				String externalReferenceCode, long groupId,
				long commercePriceListId, long commerceCurrencyId,
				boolean netPrice, String type, long parentCommercePriceListId,
				boolean catalogBasePriceList, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue =
					CommercePriceListServiceUtil.addOrUpdateCommercePriceList(
						externalReferenceCode, groupId, commercePriceListId,
						commerceCurrencyId, netPrice, type,
						parentCommercePriceListId, catalogBasePriceList, name,
						priority, displayDateMonth, displayDateDay,
						displayDateYear, displayDateHour, displayDateMinute,
						expirationDateMonth, expirationDateDay,
						expirationDateYear, expirationDateHour,
						expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteCommercePriceList(long commercePriceListId)
		throws RemoteException {

		try {
			CommercePriceListServiceUtil.deleteCommercePriceList(
				commercePriceListId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue =
					CommercePriceListServiceUtil.fetchByExternalReferenceCode(
						externalReferenceCode, companyId);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			fetchCatalogBaseCommercePriceListByType(long groupId, String type)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue =
					CommercePriceListServiceUtil.
						fetchCatalogBaseCommercePriceListByType(groupId, type);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			fetchCommerceCatalogBasePriceListByType(long groupId, String type)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue =
					CommercePriceListServiceUtil.
						fetchCommerceCatalogBasePriceListByType(groupId, type);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			fetchCommercePriceList(long commercePriceListId)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue =
					CommercePriceListServiceUtil.fetchCommercePriceList(
						commercePriceListId);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			getCommercePriceList(long commercePriceListId)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue = CommercePriceListServiceUtil.getCommercePriceList(
					commercePriceListId);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap[]
			getCommercePriceLists(
				long companyId, int status, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.price.list.model.CommercePriceList>
						orderByComparator)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.CommercePriceList>
					returnValue =
						CommercePriceListServiceUtil.getCommercePriceLists(
							companyId, status, start, end, orderByComparator);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePriceListsCount(long companyId, int status)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceListServiceUtil.getCommercePriceListsCount(
					companyId, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getCommercePriceListsCount(
			long commercePricingClassId, String title)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceListServiceUtil.getCommercePriceListsCount(
					commercePricingClassId, title);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap[]
			searchByCommercePricingClassId(
				long commercePricingClassId, String name, int start, int end)
		throws RemoteException {

		try {
			java.util.List
				<com.liferay.commerce.price.list.model.CommercePriceList>
					returnValue =
						CommercePriceListServiceUtil.
							searchByCommercePricingClassId(
								commercePricingClassId, name, start, end);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCommercePriceListsCount(
			long companyId, String keywords, int status)
		throws RemoteException {

		try {
			int returnValue =
				CommercePriceListServiceUtil.searchCommercePriceListsCount(
					companyId, keywords, status);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void setCatalogBasePriceList(
			long groupId, long commercePriceListId, String type)
		throws RemoteException {

		try {
			CommercePriceListServiceUtil.setCatalogBasePriceList(
				groupId, commercePriceListId, type);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId,
				boolean netPrice, long parentCommercePriceListId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue =
					CommercePriceListServiceUtil.updateCommercePriceList(
						commercePriceListId, commerceCurrencyId, netPrice,
						parentCommercePriceListId, name, priority,
						displayDateMonth, displayDateDay, displayDateYear,
						displayDateHour, displayDateMinute, expirationDateMonth,
						expirationDateDay, expirationDateYear,
						expirationDateHour, expirationDateMinute, neverExpire,
						serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId,
				boolean netPrice, String type, long parentCommercePriceListId,
				boolean catalogBasePriceList, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue =
					CommercePriceListServiceUtil.updateCommercePriceList(
						commercePriceListId, commerceCurrencyId, netPrice, type,
						parentCommercePriceListId, catalogBasePriceList, name,
						priority, displayDateMonth, displayDateDay,
						displayDateYear, displayDateHour, displayDateMinute,
						expirationDateMonth, expirationDateDay,
						expirationDateYear, expirationDateHour,
						expirationDateMinute, neverExpire, serviceContext);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListSoap
			updateExternalReferenceCode(
				com.liferay.commerce.price.list.model.CommercePriceListSoap
					commercePriceList,
				String externalReferenceCode, long companyId)
		throws RemoteException {

		try {
			com.liferay.commerce.price.list.model.CommercePriceList
				returnValue =
					CommercePriceListServiceUtil.updateExternalReferenceCode(
						com.liferay.commerce.price.list.model.impl.
							CommercePriceListModelImpl.toModel(
								commercePriceList),
						externalReferenceCode, companyId);

			return com.liferay.commerce.price.list.model.CommercePriceListSoap.
				toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		CommercePriceListServiceSoap.class);

}