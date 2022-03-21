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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShippingOptionAccountEntryRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingOptionAccountEntryRelService
 * @generated
 */
public class CommerceShippingOptionAccountEntryRelServiceWrapper
	implements CommerceShippingOptionAccountEntryRelService,
			   ServiceWrapper<CommerceShippingOptionAccountEntryRelService> {

	public CommerceShippingOptionAccountEntryRelServiceWrapper() {
		this(null);
	}

	public CommerceShippingOptionAccountEntryRelServiceWrapper(
		CommerceShippingOptionAccountEntryRelService
			commerceShippingOptionAccountEntryRelService) {

		_commerceShippingOptionAccountEntryRelService =
			commerceShippingOptionAccountEntryRelService;
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel
			addCommerceShippingOptionAccountEntryRel(
				long accountEntryId, long commerceChannelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingOptionAccountEntryRelService.
			addCommerceShippingOptionAccountEntryRel(
				accountEntryId, commerceChannelId, commerceShippingMethodKey,
				commerceShippingOptionKey);
	}

	@Override
	public void deleteCommerceShippingOptionAccountEntryRel(
			long commerceShippingOptionAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceShippingOptionAccountEntryRelService.
			deleteCommerceShippingOptionAccountEntryRel(
				commerceShippingOptionAccountEntryRelId);
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel
			fetchCommerceShippingOptionAccountEntryRel(
				long accountEntryId, long commerceChannelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingOptionAccountEntryRelService.
			fetchCommerceShippingOptionAccountEntryRel(
				accountEntryId, commerceChannelId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShippingOptionAccountEntryRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel
			updateCommerceShippingOptionAccountEntryRel(
				long commerceShippingOptionAccountEntryRelId,
				String commerceShippingMethodKey,
				String commerceShippingOptionKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingOptionAccountEntryRelService.
			updateCommerceShippingOptionAccountEntryRel(
				commerceShippingOptionAccountEntryRelId,
				commerceShippingMethodKey, commerceShippingOptionKey);
	}

	@Override
	public CommerceShippingOptionAccountEntryRelService getWrappedService() {
		return _commerceShippingOptionAccountEntryRelService;
	}

	@Override
	public void setWrappedService(
		CommerceShippingOptionAccountEntryRelService
			commerceShippingOptionAccountEntryRelService) {

		_commerceShippingOptionAccountEntryRelService =
			commerceShippingOptionAccountEntryRelService;
	}

	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

}