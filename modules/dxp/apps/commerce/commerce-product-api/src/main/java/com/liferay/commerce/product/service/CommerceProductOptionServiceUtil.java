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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceProductOption. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductOptionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceProductOptionService
 * @see com.liferay.commerce.product.service.base.CommerceProductOptionServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductOptionServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductOptionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductOptionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CommerceProductOption addCommerceProductOption(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductOption(nameMap, descriptionMap,
			ddmFormFieldTypeName, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductOption deleteCommerceProductOption(
		com.liferay.commerce.product.model.CommerceProductOption commerceProductOption)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceProductOption(commerceProductOption);
	}

	public static com.liferay.commerce.product.model.CommerceProductOption deleteCommerceProductOption(
		long commerceProductOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteCommerceProductOption(commerceProductOptionId);
	}

	public static com.liferay.commerce.product.model.CommerceProductOption updateCommerceProductOption(
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductOption(commerceProductOptionId,
			nameMap, descriptionMap, ddmFormFieldTypeName, serviceContext);
	}

	public static int getCommerceProductOptionsCount(long groupId) {
		return getService().getCommerceProductOptionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end) {
		return getService().getCommerceProductOptions(groupId, start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOption> getCommerceProductOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductOption> orderByComparator) {
		return getService()
				   .getCommerceProductOptions(groupId, start, end,
			orderByComparator);
	}

	public static CommerceProductOptionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductOptionService, CommerceProductOptionService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductOptionService.class);
}