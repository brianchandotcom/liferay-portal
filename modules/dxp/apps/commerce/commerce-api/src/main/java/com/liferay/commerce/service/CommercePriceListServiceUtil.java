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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommercePriceList. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommercePriceListServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListService
 * @see com.liferay.commerce.service.base.CommercePriceListServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommercePriceListServiceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommercePriceListServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommercePriceList addCommercePriceList(
		long commerceCurrencyId, java.lang.String name, double priority,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommercePriceList(commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static void deleteCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommercePriceList(commercePriceListId);
	}

	public static com.liferay.commerce.model.CommercePriceList fetchCommercePriceList(
		long commercePriceListId) {
		return getService().fetchCommercePriceList(commercePriceListId);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceLists(
		long groupId, int start, int end) {
		return getService().getCommercePriceLists(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommercePriceList> getCommercePriceLists(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceList> orderByComparator) {
		return getService()
				   .getCommercePriceLists(groupId, status, start, end,
			orderByComparator);
	}

	public static int getCommercePriceListsCount(long groupId, int status) {
		return getService().getCommercePriceListsCount(groupId, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommercePriceList> searchCommercePriceLists(
		long companyId, long groupId, java.lang.String keywords, int status,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommercePriceLists(companyId, groupId, keywords,
			status, start, end, sort);
	}

	public static com.liferay.commerce.model.CommercePriceList updateCommercePriceList(
		long commercePriceListId, long commerceCurrencyId,
		java.lang.String name, double priority, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommercePriceList(commercePriceListId,
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static CommercePriceListService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListService, CommercePriceListService> _serviceTracker =
		ServiceTrackerFactory.open(CommercePriceListService.class);
}