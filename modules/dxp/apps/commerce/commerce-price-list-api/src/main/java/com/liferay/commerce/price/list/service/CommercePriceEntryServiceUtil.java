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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommercePriceEntry. This utility wraps
 * {@link com.liferay.commerce.price.list.service.impl.CommercePriceEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryService
 * @see com.liferay.commerce.price.list.service.base.CommercePriceEntryServiceBaseImpl
 * @see com.liferay.commerce.price.list.service.impl.CommercePriceEntryServiceImpl
 * @generated
 */
@ProviderType
public class CommercePriceEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.price.list.service.impl.CommercePriceEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceEntry addCommercePriceEntry(
		long cpInstanceId, long commercePriceListId,
		java.math.BigDecimal price, java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommercePriceEntry(cpInstanceId, commercePriceListId,
			price, promoPrice, serviceContext);
	}

	public static void deleteCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommercePriceEntry(commercePriceEntryId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceEntryId) {
		return getService().fetchCommercePriceEntry(commercePriceEntryId);
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end) {
		return getService()
				   .getCommercePriceEntries(commercePriceListId, start, end);
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator) {
		return getService()
				   .getCommercePriceEntries(commercePriceListId, start, end,
			orderByComparator);
	}

	public static int getCommercePriceEntriesCount(long commercePriceListId) {
		return getService().getCommercePriceEntriesCount(commercePriceListId);
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end) {
		return getService()
				   .getInstanceCommercePriceEntries(cpInstanceId, start, end);
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceEntry> orderByComparator) {
		return getService()
				   .getInstanceCommercePriceEntries(cpInstanceId, start, end,
			orderByComparator);
	}

	public static int getInstanceCommercePriceEntriesCount(long cpInstanceId) {
		return getService().getInstanceCommercePriceEntriesCount(cpInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return getService().search(searchContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.price.list.model.CommercePriceEntry> searchCommercePriceEntries(
		long companyId, long groupId, long commercePriceListId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .searchCommercePriceEntries(companyId, groupId,
			commercePriceListId, keywords, start, end, sort);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry updateCommercePriceEntry(
		long commercePriceEntryId, java.math.BigDecimal price,
		java.math.BigDecimal promoPrice,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommercePriceEntry(commercePriceEntryId, price,
			promoPrice, serviceContext);
	}

	public static CommercePriceEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceEntryService, CommercePriceEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceEntryService.class);

		ServiceTracker<CommercePriceEntryService, CommercePriceEntryService> serviceTracker =
			new ServiceTracker<CommercePriceEntryService, CommercePriceEntryService>(bundle.getBundleContext(),
				CommercePriceEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}