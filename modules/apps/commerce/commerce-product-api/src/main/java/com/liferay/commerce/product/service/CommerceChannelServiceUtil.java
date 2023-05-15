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
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CommerceChannel. This utility wraps
 * <code>com.liferay.commerce.product.service.impl.CommerceChannelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceChannelService
 * @generated
 */
public class CommerceChannelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.service.impl.CommerceChannelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceChannel addCommerceChannel(
			String externalReferenceCode, long siteGroupId, String name,
			String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			String commerceCurrencyCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addCommerceChannel(
			externalReferenceCode, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			serviceContext);
	}

	public static CommerceChannel addOrUpdateCommerceChannel(
			String externalReferenceCode, long siteGroupId, String name,
			String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			String commerceCurrencyCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addOrUpdateCommerceChannel(
			externalReferenceCode, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			serviceContext);
	}

	public static CommerceChannel deleteCommerceChannel(long commerceChannelId)
		throws PortalException {

		return getService().deleteCommerceChannel(commerceChannelId);
	}

	public static CommerceChannel fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static CommerceChannel fetchCommerceChannel(long commerceChannelId)
		throws PortalException {

		return getService().fetchCommerceChannel(commerceChannelId);
	}

	public static CommerceChannel getCommerceChannel(long commerceChannelId)
		throws PortalException {

		return getService().getCommerceChannel(commerceChannelId);
	}

	public static CommerceChannel getCommerceChannelByOrderGroupId(long groupId)
		throws PortalException {

		return getService().getCommerceChannelByOrderGroupId(groupId);
	}

	public static List<CommerceChannel> getCommerceChannels(long companyId)
		throws PortalException {

		return getService().getCommerceChannels(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<CommerceChannel> search(long companyId)
		throws PortalException {

		return getService().search(companyId);
	}

	public static List<CommerceChannel> search(
			long companyId, String keywords, int start, int end,
			com.liferay.portal.kernel.search.Sort sort)
		throws PortalException {

		return getService().search(companyId, keywords, start, end, sort);
	}

	public static int searchCommerceChannelsCount(
			long companyId, String keywords)
		throws PortalException {

		return getService().searchCommerceChannelsCount(companyId, keywords);
	}

	public static CommerceChannel updateCommerceChannel(
			long commerceChannelId, long siteGroupId, String name, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			String commerceCurrencyCode)
		throws PortalException {

		return getService().updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode);
	}

	public static CommerceChannel updateCommerceChannel(
			long commerceChannelId, long siteGroupId, String name, String type,
			com.liferay.portal.kernel.util.UnicodeProperties
				typeSettingsUnicodeProperties,
			String commerceCurrencyCode, String priceDisplayType,
			boolean discountsTargetNetPrice)
		throws PortalException {

		return getService().updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			priceDisplayType, discountsTargetNetPrice);
	}

	public static CommerceChannel updateCommerceChannelExternalReferenceCode(
			String externalReferenceCode, long commerceChannelId)
		throws PortalException {

		return getService().updateCommerceChannelExternalReferenceCode(
			externalReferenceCode, commerceChannelId);
	}

	public static CommerceChannelService getService() {
		return _service;
	}

	public static void setService(CommerceChannelService service) {
		_service = service;
	}

	private static volatile CommerceChannelService _service;

}