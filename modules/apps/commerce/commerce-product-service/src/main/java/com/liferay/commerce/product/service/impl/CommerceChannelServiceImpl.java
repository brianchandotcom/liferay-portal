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
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceChannel"
	},
	service = AopService.class
)
public class CommerceChannelServiceImpl extends CommerceChannelServiceBaseImpl {

	@Override
	public CommerceChannel addCommerceChannel(
			String externalReferenceCode, long accountEntryId, long siteGroupId,
			String name, String type,
			UnicodeProperties typeSettingsUnicodeProperties,
			String commerceCurrencyCode, ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceChannelModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null, CPActionKeys.ADD_COMMERCE_CHANNEL);

		return commerceChannelLocalService.addCommerceChannel(
			externalReferenceCode, accountEntryId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			serviceContext);
	}

	@Override
	public CommerceChannel addOrUpdateCommerceChannel(
			String externalReferenceCode, long accountEntryId, long siteGroupId,
			String name, String type,
			UnicodeProperties typeSettingsUnicodeProperties,
			String commerceCurrencyCode, ServiceContext serviceContext)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceChannelModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null, CPActionKeys.ADD_COMMERCE_CHANNEL);

		return commerceChannelLocalService.addOrUpdateCommerceChannel(
			getUserId(), externalReferenceCode, accountEntryId, siteGroupId,
			name, type, typeSettingsUnicodeProperties, commerceCurrencyCode,
			serviceContext);
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
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommerceChannel commerceChannel =
			commerceChannelLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

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
	public List<CommerceChannel> getCommerceChannels(long companyId)
		throws PortalException {

		return commerceChannelPersistence.filterFindByCompanyId(companyId);
	}

	@Override
	public List<CommerceChannel> search(long companyId) throws PortalException {
		return commerceChannelLocalService.search(companyId);
	}

	@Override
	public List<CommerceChannel> search(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		return commerceChannelLocalService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceChannelsCount(long companyId, String keywords)
		throws PortalException {

		return commerceChannelLocalService.searchCommerceChannelsCount(
			companyId, keywords);
	}

	@Override
	public CommerceChannel updateCommerceChannel(
			long commerceChannelId, long accountEntryId, long siteGroupId,
			String name, String type,
			UnicodeProperties typeSettingsUnicodeProperties,
			String commerceCurrencyCode, String priceDisplayType,
			boolean discountsTargetNetPrice)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.UPDATE);

		return commerceChannelLocalService.updateCommerceChannel(
			commerceChannelId, accountEntryId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			priceDisplayType, discountsTargetNetPrice);
	}

	@Override
	public CommerceChannel updateCommerceChannelExternalReferenceCode(
			String externalReferenceCode, long commerceChannelId)
		throws PortalException {

		_commerceChannelModelResourcePermission.check(
			getPermissionChecker(), commerceChannelId, ActionKeys.UPDATE);

		return commerceChannelLocalService.
			updateCommerceChannelExternalReferenceCode(
				externalReferenceCode, commerceChannelId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CommerceChannel)"
	)
	private ModelResourcePermission<CommerceChannel>
		_commerceChannelModelResourcePermission;

}