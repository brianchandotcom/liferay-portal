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

package com.liferay.commerce.discount.service;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceDiscountAccountRel. This utility wraps
 * <code>com.liferay.commerce.discount.service.impl.CommerceDiscountAccountRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceDiscountAccountRelService
 * @generated
 */
public class CommerceDiscountAccountRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.discount.service.impl.CommerceDiscountAccountRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.discount.model.CommerceDiscountAccountRel
			addCommerceDiscountAccountRel(
				long commerceDiscountId, long commerceAccountId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceDiscountAccountRel(
			commerceDiscountId, commerceAccountId, serviceContext);
	}

	public static void deleteCommerceDiscountAccountRel(
			long commerceDiscountAccountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceDiscountAccountRel(
			commerceDiscountAccountRelId);
	}

	public static void deleteCommerceDiscountAccountRelsByCommerceDiscountId(
			long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceDiscountAccountRelsByCommerceDiscountId(
			commerceDiscountId);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountAccountRel
			fetchCommerceDiscountAccountRel(
				long commerceDiscountId, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceDiscountAccountRel(
			commerceDiscountId, commerceAccountId);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountAccountRel
			getCommerceDiscountAccountRel(long commerceDiscountAccountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscountAccountRel(
			commerceDiscountAccountRelId);
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountAccountRel>
				getCommerceDiscountAccountRels(
					long commerceDiscountId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.discount.model.
							CommerceDiscountAccountRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscountAccountRels(
			commerceDiscountId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountAccountRel>
			getCommerceDiscountAccountRels(
				long commerceDiscountId, String name, int start, int end) {

		return getService().getCommerceDiscountAccountRels(
			commerceDiscountId, name, start, end);
	}

	public static int getCommerceDiscountAccountRelsCount(
			long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscountAccountRelsCount(
			commerceDiscountId);
	}

	public static int getCommerceDiscountAccountRelsCount(
		long commerceDiscountId, String name) {

		return getService().getCommerceDiscountAccountRelsCount(
			commerceDiscountId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceDiscountAccountRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDiscountAccountRelService, CommerceDiscountAccountRelService>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDiscountAccountRelService.class);

		ServiceTracker
			<CommerceDiscountAccountRelService,
			 CommerceDiscountAccountRelService> serviceTracker =
				new ServiceTracker
					<CommerceDiscountAccountRelService,
					 CommerceDiscountAccountRelService>(
						 bundle.getBundleContext(),
						 CommerceDiscountAccountRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}