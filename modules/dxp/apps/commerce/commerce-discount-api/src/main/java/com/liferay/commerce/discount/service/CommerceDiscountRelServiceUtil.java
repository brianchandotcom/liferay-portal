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

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceDiscountRel. This utility wraps
 * {@link com.liferay.commerce.discount.service.impl.CommerceDiscountRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceDiscountRelService
 * @see com.liferay.commerce.discount.service.base.CommerceDiscountRelServiceBaseImpl
 * @see com.liferay.commerce.discount.service.impl.CommerceDiscountRelServiceImpl
 * @generated
 */
@ProviderType
public class CommerceDiscountRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.discount.service.impl.CommerceDiscountRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.discount.model.CommerceDiscountRel addCommerceDiscountRel(
		long commerceDiscountId, String className, long classPK,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceDiscountRel(commerceDiscountId, className,
			classPK, serviceContext);
	}

	public static void deleteCommerceDiscountRel(long commerceDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceDiscountRel(commerceDiscountRelId);
	}

	public static long[] getClassPKs(long commerceDiscountId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getClassPKs(commerceDiscountId, className);
	}

	public static com.liferay.commerce.discount.model.CommerceDiscountRel getCommerceDiscountRel(
		long commerceDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCommerceDiscountRel(commerceDiscountRelId);
	}

	public static java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceDiscountRels(commerceDiscountId, className);
	}

	public static java.util.List<com.liferay.commerce.discount.model.CommerceDiscountRel> getCommerceDiscountRels(
		long commerceDiscountId, String className, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscountRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceDiscountRels(commerceDiscountId, className,
			start, end, orderByComparator);
	}

	public static int getCommerceDiscountRelsCount(long commerceDiscountId,
		String className)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceDiscountRelsCount(commerceDiscountId, className);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceDiscountRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceDiscountRelService, CommerceDiscountRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceDiscountRelService.class);

		ServiceTracker<CommerceDiscountRelService, CommerceDiscountRelService> serviceTracker =
			new ServiceTracker<CommerceDiscountRelService, CommerceDiscountRelService>(bundle.getBundleContext(),
				CommerceDiscountRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}