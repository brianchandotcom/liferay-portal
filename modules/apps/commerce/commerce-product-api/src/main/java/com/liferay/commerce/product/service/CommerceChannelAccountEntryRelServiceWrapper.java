/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public CommerceChannelAccountEntryRel getCommerceChannelAccountEntryRel(
			long commerceChannelAccountEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceChannelAccountEntryRelService.
			getCommerceChannelAccountEntryRel(commerceChannelAccountEntryRelId);
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
	public java.util.List<CommerceChannelAccountEntryRel>
		getCommerceChannelAccountEntryRels(
			String className, long classPK, long commerceChannelId, int type) {

		return _commerceChannelAccountEntryRelService.
			getCommerceChannelAccountEntryRels(
				className, classPK, commerceChannelId, type);
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