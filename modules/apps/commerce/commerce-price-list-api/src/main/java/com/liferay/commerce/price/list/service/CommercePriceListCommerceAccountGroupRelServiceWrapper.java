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

import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListCommerceAccountGroupRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListCommerceAccountGroupRelService
 * @generated
 */
public class CommercePriceListCommerceAccountGroupRelServiceWrapper
	implements CommercePriceListCommerceAccountGroupRelService,
			   ServiceWrapper<CommercePriceListCommerceAccountGroupRelService> {

	public CommercePriceListCommerceAccountGroupRelServiceWrapper() {
		this(null);
	}

	public CommercePriceListCommerceAccountGroupRelServiceWrapper(
		CommercePriceListCommerceAccountGroupRelService
			commercePriceListCommerceAccountGroupRelService) {

		_commercePriceListCommerceAccountGroupRelService =
			commercePriceListCommerceAccountGroupRelService;
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel
			addCommercePriceListCommerceAccountGroupRel(
				long commercePriceListId, long commerceAccountGroupId,
				int order,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRelService.
			addCommercePriceListCommerceAccountGroupRel(
				commercePriceListId, commerceAccountGroupId, order,
				serviceContext);
	}

	@Override
	public void deleteCommercePriceListAccountGroupRelsByCommercePriceListId(
			long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListCommerceAccountGroupRelService.
			deleteCommercePriceListAccountGroupRelsByCommercePriceListId(
				commercePriceListId);
	}

	@Override
	public void deleteCommercePriceListCommerceAccountGroupRel(
			long commercePriceListCommerceAccountGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListCommerceAccountGroupRelService.
			deleteCommercePriceListCommerceAccountGroupRel(
				commercePriceListCommerceAccountGroupRelId);
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel
			fetchCommercePriceListCommerceAccountGroupRel(
				long commercePriceListId, long commerceAccountGroupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRelService.
			fetchCommercePriceListCommerceAccountGroupRel(
				commercePriceListId, commerceAccountGroupId);
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel
			getCommercePriceListCommerceAccountGroupRel(
				long commercePriceListCommerceAccoungGroupRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRelService.
			getCommercePriceListCommerceAccountGroupRel(
				commercePriceListCommerceAccoungGroupRelId);
	}

	@Override
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
			getCommercePriceListCommerceAccountGroupRels(
				long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRelService.
			getCommercePriceListCommerceAccountGroupRels(commercePriceListId);
	}

	@Override
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
			getCommercePriceListCommerceAccountGroupRels(
				long commercePriceListId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListCommerceAccountGroupRel>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRelService.
			getCommercePriceListCommerceAccountGroupRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CommercePriceListCommerceAccountGroupRel>
		getCommercePriceListCommerceAccountGroupRels(
			long commercePriceListId, String name, int start, int end) {

		return _commercePriceListCommerceAccountGroupRelService.
			getCommercePriceListCommerceAccountGroupRels(
				commercePriceListId, name, start, end);
	}

	@Override
	public int getCommercePriceListCommerceAccountGroupRelsCount(
			long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRelService.
			getCommercePriceListCommerceAccountGroupRelsCount(
				commercePriceListId);
	}

	@Override
	public int getCommercePriceListCommerceAccountGroupRelsCount(
		long commercePriceListId, String name) {

		return _commercePriceListCommerceAccountGroupRelService.
			getCommercePriceListCommerceAccountGroupRelsCount(
				commercePriceListId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceListCommerceAccountGroupRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel
			updateCommercePriceListCommerceAccountGroupRel(
				long commercePriceListCommerceAccountGroupRelId, int order,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListCommerceAccountGroupRelService.
			updateCommercePriceListCommerceAccountGroupRel(
				commercePriceListCommerceAccountGroupRelId, order,
				serviceContext);
	}

	@Override
	public CommercePriceListCommerceAccountGroupRelService getWrappedService() {
		return _commercePriceListCommerceAccountGroupRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListCommerceAccountGroupRelService
			commercePriceListCommerceAccountGroupRelService) {

		_commercePriceListCommerceAccountGroupRelService =
			commercePriceListCommerceAccountGroupRelService;
	}

	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

}