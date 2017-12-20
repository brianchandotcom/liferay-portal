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

package com.liferay.commerce.shipping.engine.fixed.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionServiceImpl
	extends CommerceShippingFixedOptionServiceBaseImpl {

	@Override
	public CommerceShippingFixedOption addCommerceShippingFixedOption(
			long commerceShippingMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, double amount, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return commerceShippingFixedOptionLocalService.
			addCommerceShippingFixedOption(
				commerceShippingMethodId, nameMap, descriptionMap, amount,
				priority, serviceContext);
	}

	@Override
	public void deleteCommerceShippingFixedOption(
			long commerceShippingFixedOptionId)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(commerceShippingFixedOptionId);

		CommercePermission.check(
			getPermissionChecker(), commerceShippingFixedOption.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		commerceShippingFixedOptionLocalService.
			deleteCommerceShippingFixedOption(commerceShippingFixedOption);
	}

	@Override
	public CommerceShippingFixedOption fetchCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) {

		return commerceShippingFixedOptionLocalService.
			fetchCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end) {

		return commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptions(
				commerceShippingMethodId, start, end);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {

		return commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptions(
				commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionsCount(
		long commerceShippingMethodId) {

		return commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptionsCount(commerceShippingMethodId);
	}

	@Override
	public CommerceShippingFixedOption updateCommerceShippingFixedOption(
			long commerceShippingFixedOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, double amount, double priority)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(commerceShippingFixedOptionId);

		CommercePermission.check(
			getPermissionChecker(), commerceShippingFixedOption.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return commerceShippingFixedOptionLocalService.
			updateCommerceShippingFixedOption(
				commerceShippingFixedOptionId, nameMap, descriptionMap, amount,
				priority);
	}

}