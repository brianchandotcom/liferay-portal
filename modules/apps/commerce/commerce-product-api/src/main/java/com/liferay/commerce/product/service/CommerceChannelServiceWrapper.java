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

import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceChannelService}.
 *
 * @author Marco Leo
 * @see CommerceChannelService
 * @generated
 */
public class CommerceChannelServiceWrapper
	implements CommerceChannelService, ServiceWrapper<CommerceChannelService> {

	public CommerceChannelServiceWrapper() {
		this(null);
	}

	public CommerceChannelServiceWrapper(
		CommerceChannelService commerceChannelService) {

		_commerceChannelService = commerceChannelService;
	}

	@Override
	public CommerceChannel addCommerceChannel(
			String externalReferenceCode, long siteGroupId, String name,
			String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			String commerceCurrencyCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.addCommerceChannel(
			externalReferenceCode, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			serviceContext);
	}

	@Override
	public CommerceChannel addOrUpdateCommerceChannel(
			String externalReferenceCode, long siteGroupId, String name,
			String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			String commerceCurrencyCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.addOrUpdateCommerceChannel(
			externalReferenceCode, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			serviceContext);
	}

	@Override
	public CommerceChannel deleteCommerceChannel(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.deleteCommerceChannel(commerceChannelId);
	}

	@Override
	public CommerceChannel fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public CommerceChannel fetchCommerceChannel(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.fetchCommerceChannel(commerceChannelId);
	}

	@Override
	public CommerceChannel getCommerceChannel(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.getCommerceChannel(commerceChannelId);
	}

	@Override
	public CommerceChannel getCommerceChannelByOrderGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.getCommerceChannelByOrderGroupId(
			groupId);
	}

	@Override
	public java.util.List<CommerceChannel> getCommerceChannels(
			int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.getCommerceChannels(start, end);
	}

	@Override
	public java.util.List<CommerceChannel> getCommerceChannels(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.getCommerceChannels(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceChannelService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<CommerceChannel> search(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.search(companyId);
	}

	@Override
	public java.util.List<CommerceChannel> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.search(
			companyId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceChannelsCount(long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.searchCommerceChannelsCount(
			companyId, keywords);
	}

	@Override
	public CommerceChannel updateCommerceChannel(
			long commerceChannelId, long siteGroupId, String name, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			String commerceCurrencyCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode);
	}

	@Override
	public CommerceChannel updateCommerceChannel(
			long commerceChannelId, long siteGroupId, String name, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			String commerceCurrencyCode, String priceDisplayType,
			boolean discountsTargetNetPrice)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			priceDisplayType, discountsTargetNetPrice);
	}

	@Override
	public CommerceChannel updateCommerceChannelExternalReferenceCode(
			String externalReferenceCode, long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelService.
			updateCommerceChannelExternalReferenceCode(
				externalReferenceCode, commerceChannelId);
	}

	@Override
	public CommerceChannelService getWrappedService() {
		return _commerceChannelService;
	}

	@Override
	public void setWrappedService(
		CommerceChannelService commerceChannelService) {

		_commerceChannelService = commerceChannelService;
	}

	private CommerceChannelService _commerceChannelService;

}