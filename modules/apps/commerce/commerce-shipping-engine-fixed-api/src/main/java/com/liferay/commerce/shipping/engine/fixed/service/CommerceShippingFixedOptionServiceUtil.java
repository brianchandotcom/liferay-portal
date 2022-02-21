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

package com.liferay.commerce.shipping.engine.fixed.service;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for CommerceShippingFixedOption. This utility wraps
 * <code>com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionService
 * @generated
 */
public class CommerceShippingFixedOptionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceShippingFixedOption addCommerceShippingFixedOption(
			long groupId, long commerceShippingMethodId,
			java.math.BigDecimal amount,
			Map<java.util.Locale, String> descriptionMap,
			Map<java.util.Locale, String> nameMap, double priority)
		throws PortalException {

		return getService().addCommerceShippingFixedOption(
			groupId, commerceShippingMethodId, amount, descriptionMap, nameMap,
			priority);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static CommerceShippingFixedOption addCommerceShippingFixedOption(
			long commerceShippingMethodId,
			Map<java.util.Locale, String> nameMap,
			Map<java.util.Locale, String> descriptionMap,
			java.math.BigDecimal amount, double priority,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceShippingFixedOption(
			commerceShippingMethodId, nameMap, descriptionMap, amount, priority,
			serviceContext);
	}

	public static void deleteCommerceShippingFixedOption(
			long commerceShippingFixedOptionId)
		throws PortalException {

		getService().deleteCommerceShippingFixedOption(
			commerceShippingFixedOptionId);
	}

	public static CommerceShippingFixedOption fetchCommerceShippingFixedOption(
			long commerceShippingFixedOptionId)
		throws PortalException {

		return getService().fetchCommerceShippingFixedOption(
			commerceShippingFixedOptionId);
	}

	public static List<CommerceShippingFixedOption>
			getCommerceShippingFixedOptions(
				long commerceShippingMethodId, int start, int end)
		throws PortalException {

		return getService().getCommerceShippingFixedOptions(
			commerceShippingMethodId, start, end);
	}

	public static List<CommerceShippingFixedOption>
			getCommerceShippingFixedOptions(
				long commerceShippingMethodId, int start, int end,
				OrderByComparator<CommerceShippingFixedOption>
					orderByComparator)
		throws PortalException {

		return getService().getCommerceShippingFixedOptions(
			commerceShippingMethodId, start, end, orderByComparator);
	}

	public static List<CommerceShippingFixedOption>
			getCommerceShippingFixedOptions(
				long companyId, long groupId, long commerceShippingMethodId,
				String keywords, int start, int end)
		throws PortalException {

		return getService().getCommerceShippingFixedOptions(
			companyId, groupId, commerceShippingMethodId, keywords, start, end);
	}

	public static int getCommerceShippingFixedOptionsCount(
			long commerceShippingMethodId)
		throws PortalException {

		return getService().getCommerceShippingFixedOptionsCount(
			commerceShippingMethodId);
	}

	public static long getCommerceShippingFixedOptionsCount(
			long companyId, long groupId, long commerceShippingMethodId,
			String keywords)
		throws PortalException {

		return getService().getCommerceShippingFixedOptionsCount(
			companyId, groupId, commerceShippingMethodId, keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceShippingFixedOption updateCommerceShippingFixedOption(
			long commerceShippingFixedOptionId, java.math.BigDecimal amount,
			Map<java.util.Locale, String> descriptionMap,
			Map<java.util.Locale, String> nameMap, double priority)
		throws PortalException {

		return getService().updateCommerceShippingFixedOption(
			commerceShippingFixedOptionId, amount, descriptionMap, nameMap,
			priority);
	}

	public static CommerceShippingFixedOptionService getService() {
		return _service;
	}

	private static volatile CommerceShippingFixedOptionService _service;

}