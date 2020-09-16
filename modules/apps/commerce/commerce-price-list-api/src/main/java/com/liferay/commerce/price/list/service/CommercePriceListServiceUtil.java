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

package com.liferay.commerce.price.list.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommercePriceList. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceListServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListService
 * @generated
 */
public class CommercePriceListServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				boolean netPrice, long parentCommercePriceListId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				boolean netPrice, long parentCommercePriceListId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				boolean netPrice, String type, long parentCommercePriceListId,
				boolean catalogBasePriceList, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				long parentCommercePriceListId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId,
				long parentCommercePriceListId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			addCommercePriceList(
				long groupId, long userId, long commerceCurrencyId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceList(
			groupId, userId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

	public static void deleteCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePriceList(commercePriceListId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			fetchCatalogBaseCommercePriceListByType(long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCatalogBaseCommercePriceListByType(
			groupId, type);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceList
			fetchCommerceCatalogBasePriceListByType(long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceCatalogBasePriceListByType(
			groupId, type);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			fetchCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommercePriceList(commercePriceListId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			getCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceList(commercePriceListId);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
				getCommercePriceLists(
					long companyId, int status, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceList> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceLists(
			companyId, status, start, end, orderByComparator);
	}

	public static int getCommercePriceListsCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListsCount(companyId, status);
	}

	public static int getCommercePriceListsCount(
			long commercePricingClassId, String title)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().getCommercePriceListsCount(
			commercePricingClassId, title);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceList>
				searchByCommercePricingClassId(
					long commercePricingClassId, String name, int start,
					int end)
			throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return getService().searchByCommercePricingClassId(
			commercePricingClassId, name, start, end);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.price.list.model.CommercePriceList>
				searchCommercePriceLists(
					long companyId, String keywords, int status, int start,
					int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommercePriceLists(
			companyId, keywords, status, start, end, sort);
	}

	public static int searchCommercePriceListsCount(
			long companyId, String keywords, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommercePriceListsCount(
			companyId, keywords, status);
	}

	public static void setCatalogBasePriceList(
			long groupId, long commercePriceListId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().setCatalogBasePriceList(
			groupId, commercePriceListId, type);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId,
				boolean netPrice, long parentCommercePriceListId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
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
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId,
				long parentCommercePriceListId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, parentCommercePriceListId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateCommercePriceList(
				long commercePriceListId, long commerceCurrencyId, String name,
				double priority, int displayDateMonth, int displayDateDay,
				int displayDateYear, int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			updateExternalReferenceCode(
				com.liferay.commerce.price.list.model.CommercePriceList
					commercePriceList,
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateExternalReferenceCode(
			commercePriceList, companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			upsertCommercePriceList(
				long groupId, long userId, long commercePriceListId,
				long commerceCurrencyId, boolean netPrice, String type,
				long parentCommercePriceListId, boolean catalogBasePriceList,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId, netPrice,
			type, parentCommercePriceListId, catalogBasePriceList, name,
			priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			upsertCommercePriceList(
				long groupId, long userId, long commercePriceListId,
				long commerceCurrencyId, long parentCommercePriceListId,
				String name, double priority, int displayDateMonth,
				int displayDateDay, int displayDateYear, int displayDateHour,
				int displayDateMinute, int expirationDateMonth,
				int expirationDateDay, int expirationDateYear,
				int expirationDateHour, int expirationDateMinute,
				String externalReferenceCode, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, externalReferenceCode,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceList
			upsertCommercePriceList(
				long groupId, long userId, long commercePriceListId,
				long commerceCurrencyId, String name, double priority,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, String externalReferenceCode,
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceList(
			groupId, userId, commercePriceListId, commerceCurrencyId, name,
			priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, externalReferenceCode, neverExpire,
			serviceContext);
	}

	public static CommercePriceListService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceListService, CommercePriceListService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceListService.class);

		ServiceTracker<CommercePriceListService, CommercePriceListService>
			serviceTracker =
				new ServiceTracker
					<CommercePriceListService, CommercePriceListService>(
						bundle.getBundleContext(),
						CommercePriceListService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}