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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.base.CommerceChannelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
public class CommerceChannelServiceImpl extends CommerceChannelServiceBaseImpl {

	@Override
	public CommerceChannel addCommerceChannel(
			long siteGroupId, String name, String type,
			UnicodeProperties typeSettingsUnicodeProperties,
			String commerceCurrencyCode, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), CPActionKeys.ADD_COMMERCE_CHANNEL);

		return commerceChannelLocalService.addCommerceChannel(
			siteGroupId, name, type, typeSettingsUnicodeProperties,
			commerceCurrencyCode, externalReferenceCode, serviceContext);
	}

	@Override
	public CommerceChannel deleteCommerceChannel(long commerceChannelId)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.DELETE);

		return commerceChannelLocalService.deleteCommerceChannel(
			commerceChannelId);
	}

	@Override
	public CommerceChannel fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		CommerceChannel commerceChannel =
			commerceChannelLocalService.fetchByExternalReferenceCode(
				companyId, externalReferenceCode);

		if (commerceChannel != null) {
			_commerceChannelModelResourcePermission.check(
				getPermissionChecker(), commerceChannel.getCommerceChannelId(),
				ActionKeys.VIEW);
		}

		return commerceChannel;
	}

	@Override
	public CommerceChannel fetchCommerceChannel(long commerceChannelId)
		throws PortalException {

		CommerceChannel commerceChannel =
			commerceChannelLocalService.fetchCommerceChannel(commerceChannelId);

		if (commerceChannel != null) {
			_commerceChannelModelResourcePermission.check(
				getPermissionChecker(), commerceChannelId, ActionKeys.VIEW);
		}

		return commerceChannel;
	}

	@Override
	public CommerceChannel getCommerceChannel(long commerceChannelId)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.VIEW);

		return commerceChannelLocalService.getCommerceChannel(
			commerceChannelId);
	}

	@Override
	public CommerceChannel getCommerceChannelByOrderGroupId(long groupId)
		throws PortalException {

		CommerceChannel commerceChannel =
			commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				groupId);

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannel, ActionKeys.VIEW);

		return commerceChannel;
	}

	@Override
	public List<CommerceChannel> getCommerceChannels(int start, int end)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), CPActionKeys.VIEW_COMMERCE_CHANNELS);

		return commerceChannelLocalService.getCommerceChannels(start, end);
	}

	@Override
	public List<CommerceChannel> getCommerceChannels(long companyId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), CPActionKeys.VIEW_COMMERCE_CHANNELS);

		return commerceChannelLocalService.getCommerceChannels(companyId);
	}

	@Override
	public List<CommerceChannel> searchCommerceChannels(long companyId)
		throws PortalException {

		return commerceChannelLocalService.searchCommerceChannels(companyId);
	}

	@Override
	public List<CommerceChannel> searchCommerceChannels(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), CPActionKeys.VIEW_COMMERCE_CHANNELS);

		return commerceChannelLocalService.searchCommerceChannels(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceChannelsCount(long companyId, String keywords)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), CPActionKeys.VIEW_COMMERCE_CHANNELS);

		return commerceChannelLocalService.searchCommerceChannelsCount(
			companyId, keywords);
	}

	@Override
	public CommerceChannel updateCommerceChannel(
			long commerceChannelId, long siteGroupId, String name, String type,
			UnicodeProperties typeSettingsUnicodeProperties,
			String commerceCurrencyCode)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.UPDATE);

		return commerceChannelLocalService.updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode);
	}

	@Override
	public CommerceChannel updateCommerceChannel(
			long commerceChannelId, long siteGroupId, String name, String type,
			UnicodeProperties typeSettingsUnicodeProperties,
			String commerceCurrencyCode, String priceDisplayType,
			boolean discountsTargetNetPrice)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.UPDATE);

		return commerceChannelLocalService.updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			priceDisplayType, discountsTargetNetPrice);
	}

	@Override
	public CommerceChannel updateCommerceChannelExternalReferenceCode(
			long commerceChannelId, String externalReferenceCode)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.UPDATE);

		return commerceChannelLocalService.
			updateCommerceChannelExternalReferenceCode(
				commerceChannelId, externalReferenceCode);
	}

	private static volatile ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceChannelServiceImpl.class,
				"_commerceChannelModelResourcePermission",
				CommerceChannel.class);

}