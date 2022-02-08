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

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionQualifierServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionQualifierServiceImpl
	extends CommerceShippingFixedOptionQualifierServiceBaseImpl {

	@Override
	public CommerceShippingFixedOptionQualifier
			addCommerceShippingFixedOptionQualifier(
				String className, long classPK,
				long commerceShippingFixedOptionId)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			addCommerceShippingFixedOptionQualifier(
				getUserId(), className, classPK, commerceShippingFixedOptionId);
	}

	@Override
	public void deleteCommerceShippingFixedOptionQualifier(
			long commerceShippingFixedOptionQualifierId)
		throws PortalException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				commerceShippingFixedOptionQualifierLocalService.
					getCommerceShippingFixedOptionQualifier(
						commerceShippingFixedOptionQualifierId);

		_checkCommerceChannel(
			commerceShippingFixedOptionQualifier.
				getCommerceShippingFixedOptionId());

		commerceShippingFixedOptionQualifierLocalService.
			deleteCommerceShippingFixedOptionQualifier(
				commerceShippingFixedOptionQualifier);
	}

	@Override
	public void deleteCommerceShippingFixedOptionQualifiers(
			long commerceShippingFixedOptionId)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		commerceShippingFixedOptionQualifierLocalService.
			deleteCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId);
	}

	@Override
	public void deleteCommerceShippingFixedOptionQualifiers(
			String className, long commerceShippingFixedOptionId)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		commerceShippingFixedOptionQualifierLocalService.
			deleteCommerceShippingFixedOptionQualifiers(
				className, commerceShippingFixedOptionId);
	}

	@Override
	public CommerceShippingFixedOptionQualifier
			fetchCommerceShippingFixedOptionQualifier(
				String className, long classPK,
				long commerceShippingFixedOptionId)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			fetchCommerceShippingFixedOptionQualifier(
				className, classPK, commerceShippingFixedOptionId);
	}

	@Override
	public List<CommerceShippingFixedOptionQualifier>
			getCommerceOrderTypeCommerceShippingFixedOptionQualifiers(
				long commerceShippingFixedOptionId, String keywords, int start,
				int end)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			getCommerceOrderTypeCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCount(
			long commerceShippingFixedOptionId, String keywords)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			getCommerceOrderTypeCommerceShippingFixedOptionQualifiersCount(
				commerceShippingFixedOptionId, keywords);
	}

	@Override
	public CommerceShippingFixedOptionQualifier
			getCommerceShippingFixedOptionQualifier(
				long commerceShippingFixedOptionQualifierId)
		throws PortalException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				commerceShippingFixedOptionQualifierLocalService.
					getCommerceShippingFixedOptionQualifier(
						commerceShippingFixedOptionQualifierId);

		_checkCommerceChannel(
			commerceShippingFixedOptionQualifier.
				getCommerceShippingFixedOptionId());

		return commerceShippingFixedOptionQualifier;
	}

	@Override
	public List<CommerceShippingFixedOptionQualifier>
			getCommerceShippingFixedOptionQualifiers(
				long commerceShippingFixedOptionId)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			getCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId);
	}

	@Override
	public List<CommerceShippingFixedOptionQualifier>
			getCommerceShippingFixedOptionQualifiers(
				long commerceShippingFixedOptionId, int start, int end,
				OrderByComparator<CommerceShippingFixedOptionQualifier>
					orderByComparator)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			getCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionQualifiersCount(
			long commerceShippingFixedOptionId)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			getCommerceShippingFixedOptionQualifiersCount(
				commerceShippingFixedOptionId);
	}

	@Override
	public List<CommerceShippingFixedOptionQualifier>
			getCommerceTermEntryCommerceShippingFixedOptionQualifiers(
				long commerceShippingFixedOptionId, String keywords, int start,
				int end)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			getCommerceTermEntryCommerceShippingFixedOptionQualifiers(
				commerceShippingFixedOptionId, keywords, start, end);
	}

	@Override
	public int getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount(
			long commerceShippingFixedOptionId, String keywords)
		throws PortalException {

		_checkCommerceChannel(commerceShippingFixedOptionId);

		return commerceShippingFixedOptionQualifierLocalService.
			getCommerceTermEntryCommerceShippingFixedOptionQualifiersCount(
				commerceShippingFixedOptionId, keywords);
	}

	private void _checkCommerceChannel(long commerceShippingFixedOptionId)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOption(commerceShippingFixedOptionId);

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByGroupId(
				commerceShippingFixedOption.getGroupId());

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

}