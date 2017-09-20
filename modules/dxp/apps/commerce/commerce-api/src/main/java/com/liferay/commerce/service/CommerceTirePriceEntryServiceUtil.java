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
 * Provides the remote service utility for CommerceTirePriceEntry. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceTirePriceEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntryService
 * @see com.liferay.commerce.service.base.CommerceTirePriceEntryServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceTirePriceEntryServiceImpl
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceTirePriceEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceTirePriceEntry addCommerceTirePriceEntry(
		long commercePriceEntryId, double price, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceTirePriceEntry(commercePriceEntryId, price,
			minQuantity, serviceContext);
	}

	public static void deleteCommerceTirePriceEntry(
		long commerceTirePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceTirePriceEntry(commerceTirePriceEntryId);
	}

	public static com.liferay.commerce.model.CommerceTirePriceEntry fetchCommerceTirePriceEntry(
		long commerceTirePriceEntryId) {
		return getService().fetchCommerceTirePriceEntry(commerceTirePriceEntryId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end) {
		return getService()
				   .getCommerceTirePriceEntries(commercePriceEntryId, start, end);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTirePriceEntry> orderByComparator) {
		return getService()
				   .getCommerceTirePriceEntries(commercePriceEntryId, start,
			end, orderByComparator);
	}

	public static int getCommerceTirePriceEntriesCount(
		long commercePriceEntryId) {
		return getService()
				   .getCommerceTirePriceEntriesCount(commercePriceEntryId);
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

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceTirePriceEntry> searchCommerceTirePriceEntries(
		long companyId, long groupId, long commercePriceEntryId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommerceTirePriceEntries(companyId, groupId,
			commercePriceEntryId, keywords, start, end, sort);
	}

	public static com.liferay.commerce.model.CommerceTirePriceEntry updateCommerceTirePriceEntry(
		long commerceTirePriceEntryId, double price, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceTirePriceEntry(commerceTirePriceEntryId,
			price, minQuantity, serviceContext);
	}

	public static CommerceTirePriceEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceTirePriceEntryService, CommerceTirePriceEntryService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceTirePriceEntryService.class);
}