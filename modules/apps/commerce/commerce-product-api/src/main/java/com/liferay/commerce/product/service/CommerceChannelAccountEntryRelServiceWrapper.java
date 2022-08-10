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

import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceChannelAccountEntryRelService}.
 *
 * @author Marco Leo
 * @see CommerceChannelAccountEntryRelService
 * @generated
 */
public class CommerceChannelAccountEntryRelServiceWrapper
	implements CommerceChannelAccountEntryRelService,
			   ServiceWrapper<CommerceChannelAccountEntryRelService> {

	public CommerceChannelAccountEntryRelServiceWrapper() {
		this(null);
	}

	public CommerceChannelAccountEntryRelServiceWrapper(
		CommerceChannelAccountEntryRelService
			commerceChannelAccountEntryRelService) {

		_commerceChannelAccountEntryRelService =
			commerceChannelAccountEntryRelService;
	}

	@Override
	public CommerceChannelAccountEntryRel addCommerceChannelAccountEntryRel(
			long accountEntryId, String className, long classPK,
			long commerceChannelId, boolean overrideEligibility,
			double priority, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelAccountEntryRelService.
			addCommerceChannelAccountEntryRel(
				accountEntryId, className, classPK, commerceChannelId,
				overrideEligibility, priority, type);
	}

	@Override
	public void deleteCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceChannelAccountEntryRelService.
			deleteCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRelId);
	}

	@Override
	public CommerceChannelAccountEntryRel fetchCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelAccountEntryRelService.
			fetchCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRelId);
	}

	@Override
	public CommerceChannelAccountEntryRel fetchCommerceChannelAccountEntryRel(
			long accountEntryId, long commerceChannelId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelAccountEntryRelService.
			fetchCommerceChannelAccountEntryRel(
				accountEntryId, commerceChannelId, type);
	}

	@Override
	public java.util.List<CommerceChannelAccountEntryRel>
			getCommerceChannelAccountEntryRels(
				long accountEntryId, int type, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommerceChannelAccountEntryRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelAccountEntryRelService.
			getCommerceChannelAccountEntryRels(
				accountEntryId, type, start, end, orderByComparator);
	}

	@Override
	public int getCommerceChannelAccountEntryRelsCount(
			long accountEntryId, int type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelAccountEntryRelService.
			getCommerceChannelAccountEntryRelsCount(accountEntryId, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceChannelAccountEntryRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommerceChannelAccountEntryRel updateCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId, long commerceChannelId,
			long classPK, boolean overrideEligibility, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelAccountEntryRelService.
			updateCommerceChannelAccountEntryRel(
				commerceChannelAccountEntryRelId, commerceChannelId, classPK,
				overrideEligibility, priority);
	}

	@Override
	public CommerceChannelAccountEntryRelService getWrappedService() {
		return _commerceChannelAccountEntryRelService;
	}

	@Override
	public void setWrappedService(
		CommerceChannelAccountEntryRelService
			commerceChannelAccountEntryRelService) {

		_commerceChannelAccountEntryRelService =
			commerceChannelAccountEntryRelService;
	}

	private CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;

}