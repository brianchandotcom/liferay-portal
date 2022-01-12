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

import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListAccountRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListAccountRelService
 * @generated
 */
public class CommercePriceListAccountRelServiceWrapper
	implements CommercePriceListAccountRelService,
			   ServiceWrapper<CommercePriceListAccountRelService> {

	public CommercePriceListAccountRelServiceWrapper() {
		this(null);
	}

	public CommercePriceListAccountRelServiceWrapper(
		CommercePriceListAccountRelService commercePriceListAccountRelService) {

		_commercePriceListAccountRelService =
			commercePriceListAccountRelService;
	}

	@Override
	public CommercePriceListAccountRel addCommercePriceListAccountRel(
			long commercePriceListId, long commerceAccountId, int order,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListAccountRelService.
			addCommercePriceListAccountRel(
				commercePriceListId, commerceAccountId, order, serviceContext);
	}

	@Override
	public void deleteCommercePriceListAccountRel(
			long commercePriceListAccountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListAccountRelService.deleteCommercePriceListAccountRel(
			commercePriceListAccountRelId);
	}

	@Override
	public void deleteCommercePriceListAccountRelsByCommercePriceListId(
			long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListAccountRelService.
			deleteCommercePriceListAccountRelsByCommercePriceListId(
				commercePriceListId);
	}

	@Override
	public CommercePriceListAccountRel fetchCommercePriceListAccountRel(
			long commercePriceListId, long commerceAccountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListAccountRelService.
			fetchCommercePriceListAccountRel(
				commercePriceListId, commerceAccountId);
	}

	@Override
	public CommercePriceListAccountRel getCommercePriceListAccountRel(
			long commercePriceListAccountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListAccountRelService.
			getCommercePriceListAccountRel(commercePriceListAccountRelId);
	}

	@Override
	public java.util.List<CommercePriceListAccountRel>
			getCommercePriceListAccountRels(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListAccountRelService.
			getCommercePriceListAccountRels(commercePriceListId);
	}

	@Override
	public java.util.List<CommercePriceListAccountRel>
			getCommercePriceListAccountRels(
				long commercePriceListId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<CommercePriceListAccountRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListAccountRelService.
			getCommercePriceListAccountRels(
				commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<CommercePriceListAccountRel>
		getCommercePriceListAccountRels(
			long commercePriceListId, String name, int start, int end) {

		return _commercePriceListAccountRelService.
			getCommercePriceListAccountRels(
				commercePriceListId, name, start, end);
	}

	@Override
	public int getCommercePriceListAccountRelsCount(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListAccountRelService.
			getCommercePriceListAccountRelsCount(commercePriceListId);
	}

	@Override
	public int getCommercePriceListAccountRelsCount(
		long commercePriceListId, String name) {

		return _commercePriceListAccountRelService.
			getCommercePriceListAccountRelsCount(commercePriceListId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceListAccountRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommercePriceListAccountRelService getWrappedService() {
		return _commercePriceListAccountRelService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListAccountRelService commercePriceListAccountRelService) {

		_commercePriceListAccountRelService =
			commercePriceListAccountRelService;
	}

	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

}