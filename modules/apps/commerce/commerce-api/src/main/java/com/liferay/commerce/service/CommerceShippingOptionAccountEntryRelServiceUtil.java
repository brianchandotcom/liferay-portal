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

package com.liferay.commerce.service;

import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for CommerceShippingOptionAccountEntryRel. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceShippingOptionAccountEntryRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingOptionAccountEntryRelService
 * @generated
 */
public class CommerceShippingOptionAccountEntryRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceShippingOptionAccountEntryRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CommerceShippingOptionAccountEntryRel
			addCommerceShippingOptionAccountEntryRel(
				long accountEntryId, long commerceChannelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws PortalException {

		return getService().addCommerceShippingOptionAccountEntryRel(
			accountEntryId, commerceChannelId, commerceShippingMethodKey,
			commerceShippingOptionKey);
	}

	public static void deleteCommerceShippingOptionAccountEntryRel(
			long commerceShippingOptionAccountEntryRelId)
		throws PortalException {

		getService().deleteCommerceShippingOptionAccountEntryRel(
			commerceShippingOptionAccountEntryRelId);
	}

	public static CommerceShippingOptionAccountEntryRel
			fetchCommerceShippingOptionAccountEntryRel(
				long accountEntryId, long commerceChannelId)
		throws PortalException {

		return getService().fetchCommerceShippingOptionAccountEntryRel(
			accountEntryId, commerceChannelId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CommerceShippingOptionAccountEntryRel
			updateCommerceShippingOptionAccountEntryRel(
				long commerceShippingOptionAccountEntryRelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws PortalException {

		return getService().updateCommerceShippingOptionAccountEntryRel(
			commerceShippingOptionAccountEntryRelId, commerceShippingMethodKey,
			commerceShippingOptionKey);
	}

	public static CommerceShippingOptionAccountEntryRelService getService() {
		return _service;
	}

	private static volatile CommerceShippingOptionAccountEntryRelService
		_service;

}