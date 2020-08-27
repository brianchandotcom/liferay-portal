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
 * Provides the remote service utility for CommercePriceListDiscountRel. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceListDiscountRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListDiscountRelService
 * @generated
 */
public class CommercePriceListDiscountRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListDiscountRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				addCommercePriceListDiscountRel(
					long commercePriceListId, long commerceDiscountId,
					int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceListDiscountRel(
			commercePriceListId, commerceDiscountId, order, serviceContext);
	}

	public static void deleteCommercePriceListDiscountRel(
			long commercePriceListDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePriceListDiscountRel(
			commercePriceListDiscountRelId);
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				fetchCommercePriceListDiscountRel(
					long commercePriceListId, long commerceDiscountId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommercePriceListDiscountRel(
			commercePriceListId, commerceDiscountId);
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListDiscountRel
				getCommercePriceListDiscountRel(
					long commercePriceListDiscountRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListDiscountRel(
			commercePriceListDiscountRelId);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListDiscountRel>
				getCommercePriceListDiscountRels(long commercePriceListId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListDiscountRels(
			commercePriceListId);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListDiscountRel>
				getCommercePriceListDiscountRels(
					long commercePriceListId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceListDiscountRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListDiscountRels(
			commercePriceListId, start, end, orderByComparator);
	}

	public static int getCommercePriceListDiscountRelsCount(
			long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListDiscountRelsCount(
			commercePriceListId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommercePriceListDiscountRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceListDiscountRelService,
		 CommercePriceListDiscountRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceListDiscountRelService.class);

		ServiceTracker
			<CommercePriceListDiscountRelService,
			 CommercePriceListDiscountRelService> serviceTracker =
				new ServiceTracker
					<CommercePriceListDiscountRelService,
					 CommercePriceListDiscountRelService>(
						 bundle.getBundleContext(),
						 CommercePriceListDiscountRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}