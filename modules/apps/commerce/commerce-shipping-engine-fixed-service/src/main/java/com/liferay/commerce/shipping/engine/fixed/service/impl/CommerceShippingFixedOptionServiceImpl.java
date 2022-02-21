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

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.math.BigDecimal;

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
			long groupId, long commerceShippingMethodId, BigDecimal amount,
			Map<Locale, String> descriptionMap, Map<Locale, String> nameMap,
			double priority)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceShippingFixedOptionLocalService.
			addCommerceShippingFixedOption(
				getUserId(), groupId, commerceShippingMethodId, amount,
				descriptionMap, nameMap, priority);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceShippingFixedOption addCommerceShippingFixedOption(
			long commerceShippingMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, BigDecimal amount,
			double priority, ServiceContext serviceContext)
		throws PortalException {

		return commerceShippingFixedOptionService.
			addCommerceShippingFixedOption(
				serviceContext.getScopeGroupId(), commerceShippingMethodId,
				amount, descriptionMap, nameMap, priority);
	}

	@Override
	public void deleteCommerceShippingFixedOption(
			long commerceShippingFixedOptionId)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(commerceShippingFixedOptionId);

		_checkCommerceChannel(commerceShippingFixedOption.getGroupId());

		commerceShippingFixedOptionLocalService.
			deleteCommerceShippingFixedOption(commerceShippingFixedOption);
	}

	@Override
	public CommerceShippingFixedOption fetchCommerceShippingFixedOption(
			long commerceShippingFixedOptionId)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				fetchCommerceShippingFixedOption(commerceShippingFixedOptionId);

		if (commerceShippingFixedOption != null) {
			_checkCommerceChannel(commerceShippingFixedOption.getGroupId());
		}

		return commerceShippingFixedOption;
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
			long commerceShippingMethodId, int start, int end)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingMethodId);

		_checkCommerceChannel(commerceShippingMethod.getGroupId());

		return commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptions(
				commerceShippingMethod.getCommerceShippingMethodId(), start,
				end);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
			long commerceShippingMethodId, int start, int end,
			OrderByComparator<CommerceShippingFixedOption> orderByComparator)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingMethodId);

		_checkCommerceChannel(commerceShippingMethod.getGroupId());

		return commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptions(
				commerceShippingMethod.getCommerceShippingMethodId(), start,
				end, orderByComparator);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
			long companyId, long groupId, long commerceShippingMethodId,
			String keywords, int start, int end)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptions(
				companyId, groupId, commerceShippingMethodId, keywords, start,
				end);
	}

	@Override
	public int getCommerceShippingFixedOptionsCount(
			long commerceShippingMethodId)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingMethodId);

		_checkCommerceChannel(commerceShippingMethod.getGroupId());

		return commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptionsCount(
				commerceShippingMethod.getCommerceShippingMethodId());
	}

	@Override
	public long getCommerceShippingFixedOptionsCount(
			long companyId, long groupId, long commerceShippingMethodId,
			String keywords)
		throws PortalException {

		_checkCommerceChannel(groupId);

		return commerceShippingFixedOptionLocalService.
			getCommerceShippingFixedOptionsCount(
				companyId, groupId, commerceShippingMethodId, keywords);
	}

	@Override
	public CommerceShippingFixedOption updateCommerceShippingFixedOption(
			long commerceShippingFixedOptionId, BigDecimal amount,
			Map<Locale, String> descriptionMap, Map<Locale, String> nameMap,
			double priority)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(commerceShippingFixedOptionId);

		_checkCommerceChannel(commerceShippingFixedOption.getGroupId());

		return commerceShippingFixedOptionLocalService.
			updateCommerceShippingFixedOption(
				commerceShippingFixedOptionId, amount, descriptionMap, nameMap,
				priority);
	}

	private void _checkCommerceChannel(long groupId) throws PortalException {
		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(groupId);

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannel, ActionKeys.UPDATE);
	}

	private static volatile ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceShippingFixedOptionServiceImpl.class,
				"_commerceChannelModelResourcePermission",
				CommerceChannel.class);

	@ServiceReference(type = CommerceChannelLocalService.class)
	private CommerceChannelLocalService _commerceChannelLocalService;

	@ServiceReference(type = CommerceShippingMethodService.class)
	private CommerceShippingMethodService _commerceShippingMethodService;

}