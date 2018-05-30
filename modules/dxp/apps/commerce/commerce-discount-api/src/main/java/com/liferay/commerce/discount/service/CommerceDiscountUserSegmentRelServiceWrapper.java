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

package com.liferay.commerce.discount.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDiscountUserSegmentRelService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountUserSegmentRelService
 * @generated
 */
@ProviderType
public class CommerceDiscountUserSegmentRelServiceWrapper
	implements CommerceDiscountUserSegmentRelService,
		ServiceWrapper<CommerceDiscountUserSegmentRelService> {
	public CommerceDiscountUserSegmentRelServiceWrapper(
		CommerceDiscountUserSegmentRelService commerceDiscountUserSegmentRelService) {
		_commerceDiscountUserSegmentRelService = commerceDiscountUserSegmentRelService;
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel addCommerceDiscountUserSegmentRel(
		long commerceDiscountId, long commerceUserSegmentEntryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUserSegmentRelService.addCommerceDiscountUserSegmentRel(commerceDiscountId,
			commerceUserSegmentEntryId, serviceContext);
	}

	@Override
	public void deleteCommerceDiscountUserSegmentRel(
		long commerceDiscountUserSegmentRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceDiscountUserSegmentRelService.deleteCommerceDiscountUserSegmentRel(commerceDiscountUserSegmentRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel> getCommerceDiscountUserSegmentRels(
		long commerceDiscountId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceDiscountUserSegmentRelService.getCommerceDiscountUserSegmentRels(commerceDiscountId,
			start, end, orderByComparator);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountUserSegmentRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceDiscountUserSegmentRelService getWrappedService() {
		return _commerceDiscountUserSegmentRelService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountUserSegmentRelService commerceDiscountUserSegmentRelService) {
		_commerceDiscountUserSegmentRelService = commerceDiscountUserSegmentRelService;
	}

	private CommerceDiscountUserSegmentRelService _commerceDiscountUserSegmentRelService;
}