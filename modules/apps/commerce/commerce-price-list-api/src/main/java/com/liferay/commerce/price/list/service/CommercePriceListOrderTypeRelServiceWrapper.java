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

import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListOrderTypeRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListOrderTypeRelService
 * @generated
 */
public class CommercePriceListOrderTypeRelServiceWrapper
	implements CommercePriceListOrderTypeRelService,
			   ServiceWrapper<CommercePriceListOrderTypeRelService> {

	public CommercePriceListOrderTypeRelServiceWrapper() {
		this(null);
	}

	public CommercePriceListOrderTypeRelServiceWrapper(
		CommercePriceListOrderTypeRelService
			commercePriceListOrderTypeRelService) {

		_commercePriceListOrderTypeRelService =
			commercePriceListOrderTypeRelService;
	}

	@Override
	public CommercePriceListOrderTypeRel addCommercePriceListOrderTypeRel(
			long commercePriceListId, long commerceOrderTypeId, int priority,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			addCommercePriceListOrderTypeRel(
				commercePriceListId, commerceOrderTypeId, priority,
				serviceContext);
	}

	@Override
	public void deleteCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListOrderTypeRelService.
			deleteCommercePriceListOrderTypeRel(
				commercePriceListOrderTypeRelId);
	}

	@Override
	public CommercePriceListOrderTypeRel fetchCommercePriceListOrderTypeRel(
			long commercePriceListId, long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			fetchCommercePriceListOrderTypeRel(
				commercePriceListId, commerceOrderTypeId);
	}

	@Override
	public CommercePriceListOrderTypeRel getCommercePriceListOrderTypeRel(
			long commercePriceListOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			getCommercePriceListOrderTypeRel(commercePriceListOrderTypeRelId);
	}

	@Override
	public java.util.List<CommercePriceListOrderTypeRel>
			getCommercePriceListOrderTypeRels(
				long commercePriceListId, String name, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListOrderTypeRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			getCommercePriceListOrderTypeRels(
				commercePriceListId, name, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListOrderTypeRelsCount(
			long commercePriceListId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListOrderTypeRelService.
			getCommercePriceListOrderTypeRelsCount(commercePriceListId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceListOrderTypeRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommercePriceListOrderTypeRelService getWrappedService() {
		return _commercePriceListOrderTypeRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListOrderTypeRelService
			commercePriceListOrderTypeRelService) {

		_commercePriceListOrderTypeRelService =
			commercePriceListOrderTypeRelService;
	}

	private CommercePriceListOrderTypeRelService
		_commercePriceListOrderTypeRelService;

}