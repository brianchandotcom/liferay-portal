/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.price.list.service;

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
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel addCommercePriceListUserSegmentEntryRel(
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
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel fetchCommercePriceListUserSegmentEntryRel(
		long commercePriceListId, long commerceUserSegmentEntryId) {
		return _commercePriceListUserSegmentEntryRelService.fetchCommercePriceListUserSegmentEntryRel(commercePriceListId,
			commerceUserSegmentEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId) {
		return _commercePriceListUserSegmentEntryRelService.getCommercePriceListUserSegmentEntryRels(commercePriceListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> getCommercePriceListUserSegmentEntryRels(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel> orderByComparator) {
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
	public String getOSGiServiceIdentifier() {
		return _commercePriceListUserSegmentEntryRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel updateCommercePriceListUserSegmentEntryRel(
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