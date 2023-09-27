/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel
			getCommerceShippingOptionAccountEntryRel(
				long commerceShippingOptionAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceShippingOptionAccountEntryRelService.
			getCommerceShippingOptionAccountEntryRel(
				commerceShippingOptionAccountEntryRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel>
				getCommerceShippingOptionAccountEntryRels(long accountEntryId)
			throws Exception {

		return _commerceShippingOptionAccountEntryRelService.
			getCommerceShippingOptionAccountEntryRels(accountEntryId);
	}

	@Override
	public int getCommerceShippingOptionAccountEntryRelsCount(
			long accountEntryId)
		throws Exception {

		return _commerceShippingOptionAccountEntryRelService.
			getCommerceShippingOptionAccountEntryRelsCount(accountEntryId);
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