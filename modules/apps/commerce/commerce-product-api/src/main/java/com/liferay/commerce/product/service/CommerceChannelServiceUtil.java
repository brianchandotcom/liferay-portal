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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

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
	public static com.liferay.commerce.product.model.CommerceChannel
			addCommerceChannel(
				long siteGroupId, String name, String type,
				com.liferay.portal.kernel.util.UnicodeProperties
					typeSettingsUnicodeProperties,
				String commerceCurrencyCode, String externalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceChannel(
			siteGroupId, name, type, typeSettingsUnicodeProperties,
			commerceCurrencyCode, externalReferenceCode, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			deleteCommerceChannel(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().deleteCommerceChannel(commerceChannelId);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			fetchCommerceChannel(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceChannel(commerceChannelId);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			getCommerceChannel(long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannel(commerceChannelId);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			getCommerceChannelByOrderGroupId(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannelByOrderGroupId(groupId);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannel>
				getCommerceChannels(int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceChannels(start, end);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannel>
				getCommerceChannels(long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

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

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannel>
				searchCommerceChannels(long companyId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceChannels(companyId);
	}

	public static java.util.List
		<com.liferay.commerce.product.model.CommerceChannel>
				searchCommerceChannels(
					long companyId, String keywords, int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceChannels(
			companyId, keywords, start, end, sort);
	}

	public static int searchCommerceChannelsCount(
			long companyId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommerceChannelsCount(companyId, keywords);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			updateCommerceChannel(
				long commerceChannelId, long siteGroupId, String name,
				String type,
				com.liferay.portal.kernel.util.UnicodeProperties
					typeSettingsUnicodeProperties,
				String commerceCurrencyCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			updateCommerceChannel(
				long commerceChannelId, long siteGroupId, String name,
				String type,
				com.liferay.portal.kernel.util.UnicodeProperties
					typeSettingsUnicodeProperties,
				String commerceCurrencyCode, String priceDisplayType,
				boolean discountsTargetNetPrice)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceChannel(
			commerceChannelId, siteGroupId, name, type,
			typeSettingsUnicodeProperties, commerceCurrencyCode,
			priceDisplayType, discountsTargetNetPrice);
	}

	public static com.liferay.commerce.product.model.CommerceChannel
			updateCommerceChannelExternalReferenceCode(
				long commerceChannelId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceChannelExternalReferenceCode(
			commerceChannelId, externalReferenceCode);
	}

	public static CommerceChannelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceChannelService, CommerceChannelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceChannelService.class);

		ServiceTracker<CommerceChannelService, CommerceChannelService>
			serviceTracker =
				new ServiceTracker
					<CommerceChannelService, CommerceChannelService>(
						bundle.getBundleContext(), CommerceChannelService.class,
						null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}