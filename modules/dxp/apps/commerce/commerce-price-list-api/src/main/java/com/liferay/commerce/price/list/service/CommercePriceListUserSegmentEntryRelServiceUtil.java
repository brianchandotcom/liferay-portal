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
 * Provides the remote service utility for CommercePriceListUserSegmentEntryRel. This utility wraps
 * {@link com.liferay.commerce.price.list.service.impl.CommercePriceListUserSegmentEntryRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRelService
 * @see com.liferay.commerce.price.list.service.base.CommercePriceListUserSegmentEntryRelServiceBaseImpl
 * @see com.liferay.commerce.price.list.service.impl.CommercePriceListUserSegmentEntryRelServiceImpl
 * @generated
 */
@ProviderType
public class CommercePriceListUserSegmentEntryRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.price.list.service.impl.CommercePriceListUserSegmentEntryRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommercePriceListUserSegmentEntryRel(commercePriceListId,
			commerceUserSegmentEntryId, order, serviceContext);
	}

	public static void deleteCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId) {
		return getService()
				   .fetchCommercePriceListUserSegmentEntryRel(commercePriceListId,
			commerceUserSegmentEntryId);
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId) {
		return getService()
				   .getCommercePriceListUserSegmentEntryRels(commercePriceListId);
	}

	public static java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return getService()
				   .getCommercePriceListUserSegmentEntryRels(commercePriceListId,
			start, end, orderByComparator);
	}

	public static int getCommercePriceListUserSegmentEntryRelsCount(
		long commercePriceListId) {
		return getService()
				   .getCommercePriceListUserSegmentEntryRelsCount(commercePriceListId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId,
			order, serviceContext);
	}

	public static CommercePriceListUserSegmentEntryRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommercePriceListUserSegmentEntryRelService, CommercePriceListUserSegmentEntryRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommercePriceListUserSegmentEntryRelService.class);

		ServiceTracker<CommercePriceListUserSegmentEntryRelService, CommercePriceListUserSegmentEntryRelService> serviceTracker =
			new ServiceTracker<CommercePriceListUserSegmentEntryRelService, CommercePriceListUserSegmentEntryRelService>(bundle.getBundleContext(),
				CommercePriceListUserSegmentEntryRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}