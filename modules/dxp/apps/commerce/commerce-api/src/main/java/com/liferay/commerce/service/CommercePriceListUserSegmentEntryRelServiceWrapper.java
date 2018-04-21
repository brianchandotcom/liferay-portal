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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListUserSegmentEntryRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListUserSegmentEntryRelService
 * @generated
 */
@ProviderType
public class CommercePriceListUserSegmentEntryRelServiceWrapper
	implements CommercePriceListUserSegmentEntryRelService,
		ServiceWrapper<CommercePriceListUserSegmentEntryRelService> {
	public CommercePriceListUserSegmentEntryRelServiceWrapper(
		CommercePriceListUserSegmentEntryRelService commercePriceListUserSegmentEntryRelService) {
		_commercePriceListUserSegmentEntryRelService = commercePriceListUserSegmentEntryRelService;
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelService.addCommercePriceListUserSegmentEntryRel(commercePriceListId,
			commerceUserSegmentEntryId, order, serviceContext);
	}

	@Override
	public void deleteCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceListUserSegmentEntryRelService.deleteCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId) {
		return _commercePriceListUserSegmentEntryRelService.fetchCommercePriceListUserSegmentEntryRel(commercePriceListId,
			commerceUserSegmentEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId) {
		return _commercePriceListUserSegmentEntryRelService.getCommercePriceListUserSegmentEntryRels(commercePriceListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel> orderByComparator) {
		return _commercePriceListUserSegmentEntryRelService.getCommercePriceListUserSegmentEntryRels(commercePriceListId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListUserSegmentEntryRelsCount(
		long commercePriceListId) {
		return _commercePriceListUserSegmentEntryRelService.getCommercePriceListUserSegmentEntryRelsCount(commercePriceListId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceListUserSegmentEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
		long commercePriceListUserSegmentEntryRelId, int order,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListUserSegmentEntryRelService.updateCommercePriceListUserSegmentEntryRel(commercePriceListUserSegmentEntryRelId,
			order, serviceContext);
	}

	@Override
	public CommercePriceListUserSegmentEntryRelService getWrappedService() {
		return _commercePriceListUserSegmentEntryRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListUserSegmentEntryRelService commercePriceListUserSegmentEntryRelService) {
		_commercePriceListUserSegmentEntryRelService = commercePriceListUserSegmentEntryRelService;
	}

	private CommercePriceListUserSegmentEntryRelService _commercePriceListUserSegmentEntryRelService;
}