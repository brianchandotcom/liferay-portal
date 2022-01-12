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

package com.liferay.commerce.price.list.service;

import com.liferay.commerce.price.list.model.CommercePriceListDiscountRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListDiscountRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListDiscountRelService
 * @generated
 */
public class CommercePriceListDiscountRelServiceWrapper
	implements CommercePriceListDiscountRelService,
			   ServiceWrapper<CommercePriceListDiscountRelService> {

	public CommercePriceListDiscountRelServiceWrapper() {
		this(null);
	}

	public CommercePriceListDiscountRelServiceWrapper(
		CommercePriceListDiscountRelService
			commercePriceListDiscountRelService) {

		_commercePriceListDiscountRelService =
			commercePriceListDiscountRelService;
	}

	@Override
	public CommercePriceListDiscountRel addCommercePriceListDiscountRel(
			long commercePriceListId, long commerceDiscountId, int order,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListDiscountRelService.
			addCommercePriceListDiscountRel(
				commercePriceListId, commerceDiscountId, order, serviceContext);
	}

	@Override
	public void deleteCommercePriceListDiscountRel(
			long commercePriceListDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListDiscountRelService.deleteCommercePriceListDiscountRel(
			commercePriceListDiscountRelId);
	}

	@Override
	public CommercePriceListDiscountRel fetchCommercePriceListDiscountRel(
			long commercePriceListId, long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListDiscountRelService.
			fetchCommercePriceListDiscountRel(
				commercePriceListId, commerceDiscountId);
	}

	@Override
	public CommercePriceListDiscountRel getCommercePriceListDiscountRel(
			long commercePriceListDiscountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListDiscountRelService.
			getCommercePriceListDiscountRel(commercePriceListDiscountRelId);
	}

	@Override
	public java.util.List<CommercePriceListDiscountRel>
			getCommercePriceListDiscountRels(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListDiscountRelService.
			getCommercePriceListDiscountRels(commercePriceListId);
	}

	@Override
	public java.util.List<CommercePriceListDiscountRel>
			getCommercePriceListDiscountRels(
				long commercePriceListId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListDiscountRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListDiscountRelService.
			getCommercePriceListDiscountRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListDiscountRelsCount(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListDiscountRelService.
			getCommercePriceListDiscountRelsCount(commercePriceListId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceListDiscountRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommercePriceListDiscountRelService getWrappedService() {
		return _commercePriceListDiscountRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListDiscountRelService
			commercePriceListDiscountRelService) {

		_commercePriceListDiscountRelService =
			commercePriceListDiscountRelService;
	}

	private CommercePriceListDiscountRelService
		_commercePriceListDiscountRelService;

}