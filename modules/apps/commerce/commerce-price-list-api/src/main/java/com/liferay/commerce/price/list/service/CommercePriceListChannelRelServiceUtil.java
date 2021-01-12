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
 * Provides the remote service utility for CommercePriceListChannelRel. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceListChannelRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListChannelRelService
 * @generated
 */
public class CommercePriceListChannelRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceListChannelRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				addCommercePriceListChannelRel(
					long commercePriceListId, long commerceChannelId, int order,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceListChannelRel(
			commercePriceListId, commerceChannelId, order, serviceContext);
	}

	public static void deleteCommercePriceListChannelRel(
			long commercePriceListChannelRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePriceListChannelRel(
			commercePriceListChannelRelId);
	}

	public static void deleteCommercePriceListChannelRelsByCommercePriceListId(
			long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePriceListChannelRelsByCommercePriceListId(
			commercePriceListId);
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				fetchCommercePriceListChannelRel(
					long commerceChannelId, long commercePriceListId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommercePriceListChannelRel(
			commerceChannelId, commercePriceListId);
	}

	public static
		com.liferay.commerce.price.list.model.CommercePriceListChannelRel
				getCommercePriceListChannelRel(
					long commercePriceListChannelRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListChannelRel(
			commercePriceListChannelRelId);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListChannelRel>
				getCommercePriceListChannelRels(long commercePriceListId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListChannelRels(
			commercePriceListId);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListChannelRel>
				getCommercePriceListChannelRels(
					long commercePriceListId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceListChannelRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListChannelRels(
			commercePriceListId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceListChannelRel>
			getCommercePriceListChannelRels(
				long commercePriceListId, String name, int start, int end) {

		return getService().getCommercePriceListChannelRels(
			commercePriceListId, name, start, end);
	}

	public static int getCommercePriceListChannelRelsCount(
			long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceListChannelRelsCount(
			commercePriceListId);
	}

	public static int getCommercePriceListChannelRelsCount(
		long commercePriceListId, String name) {

		return getService().getCommercePriceListChannelRelsCount(
			commercePriceListId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommercePriceListChannelRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceListChannelRelService, CommercePriceListChannelRelService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceListChannelRelService.class);

		ServiceTracker
			<CommercePriceListChannelRelService,
			 CommercePriceListChannelRelService> serviceTracker =
				new ServiceTracker
					<CommercePriceListChannelRelService,
					 CommercePriceListChannelRelService>(
						 bundle.getBundleContext(),
						 CommercePriceListChannelRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}