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
 * Provides the remote service utility for CommerceDiscountCommerceAccountGroupRel. This utility wraps
 * <code>com.liferay.commerce.discount.service.impl.CommerceDiscountCommerceAccountGroupRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceDiscountCommerceAccountGroupRelService
 * @generated
 */
public class CommerceDiscountCommerceAccountGroupRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.discount.service.impl.CommerceDiscountCommerceAccountGroupRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					addCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountId, long commerceAccountGroupId,
						com.liferay.portal.kernel.service.ServiceContext
							serviceContext)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountId, commerceAccountGroupId, serviceContext);
	}

	public static void deleteCommerceDiscountCommerceAccountGroupRel(
			long commerceDiscountCommerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRelId);
	}

	public static void
			deleteCommerceDiscountCommerceAccountGroupRelsByCommerceDiscountId(
				long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().
			deleteCommerceDiscountCommerceAccountGroupRelsByCommerceDiscountId(
				commerceDiscountId);
	}

	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					fetchCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountId, long commerceAccountGroupId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountId, commerceAccountGroupId);
	}

	public static
		com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel
					getCommerceDiscountCommerceAccountGroupRel(
						long commerceDiscountCommerceAccountGroupRelId)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscountCommerceAccountGroupRel(
			commerceDiscountCommerceAccountGroupRelId);
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel>
					getCommerceDiscountCommerceAccountGroupRels(
						long commerceDiscountId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.discount.model.
								CommerceDiscountCommerceAccountGroupRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscountCommerceAccountGroupRels(
			commerceDiscountId, start, end, orderByComparator);
	}

	public static java.util.List
		<com.liferay.commerce.discount.model.
			CommerceDiscountCommerceAccountGroupRel>
				getCommerceDiscountCommerceAccountGroupRels(
					long commerceDiscountId, String name, int start, int end) {

		return getService().getCommerceDiscountCommerceAccountGroupRels(
			commerceDiscountId, name, start, end);
	}

	public static int getCommerceDiscountCommerceAccountGroupRelsCount(
			long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceDiscountCommerceAccountGroupRelsCount(
			commerceDiscountId);
	}

	public static int getCommerceDiscountCommerceAccountGroupRelsCount(
		long commerceDiscountId, String name) {

		return getService().getCommerceDiscountCommerceAccountGroupRelsCount(
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

	public static CommerceDiscountCommerceAccountGroupRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceDiscountCommerceAccountGroupRelService,
		 CommerceDiscountCommerceAccountGroupRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceDiscountCommerceAccountGroupRelService.class);

		ServiceTracker
			<CommerceDiscountCommerceAccountGroupRelService,
			 CommerceDiscountCommerceAccountGroupRelService> serviceTracker =
				new ServiceTracker
					<CommerceDiscountCommerceAccountGroupRelService,
					 CommerceDiscountCommerceAccountGroupRelService>(
						 bundle.getBundleContext(),
						 CommerceDiscountCommerceAccountGroupRelService.class,
						 null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}