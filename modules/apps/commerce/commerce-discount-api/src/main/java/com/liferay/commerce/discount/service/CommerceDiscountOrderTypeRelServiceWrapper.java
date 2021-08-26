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

package com.liferay.commerce.discount.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceDiscountOrderTypeRelService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountOrderTypeRelService
 * @generated
 */
public class CommerceDiscountOrderTypeRelServiceWrapper
	implements CommerceDiscountOrderTypeRelService,
			   ServiceWrapper<CommerceDiscountOrderTypeRelService> {

	public CommerceDiscountOrderTypeRelServiceWrapper(
		CommerceDiscountOrderTypeRelService
			commerceDiscountOrderTypeRelService) {

		_commerceDiscountOrderTypeRelService =
			commerceDiscountOrderTypeRelService;
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel
			addCommerceDiscountOrderTypeRel(
				long commerceDiscountId, long commerceOrderTypeId, int priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountOrderTypeRelService.
			addCommerceDiscountOrderTypeRel(
				commerceDiscountId, commerceOrderTypeId, priority,
				serviceContext);
	}

	@Override
	public void deleteCommerceDiscountOrderTypeRel(
			long commerceDiscountOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceDiscountOrderTypeRelService.deleteCommerceDiscountOrderTypeRel(
			commerceDiscountOrderTypeRelId);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel
			fetchCommerceDiscountOrderTypeRel(
				long commerceDiscountId, long commerceOrderTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountOrderTypeRelService.
			fetchCommerceDiscountOrderTypeRel(
				commerceDiscountId, commerceOrderTypeId);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel
			getCommerceDiscountOrderTypeRel(long commerceDiscountOrderTypeRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountOrderTypeRelService.
			getCommerceDiscountOrderTypeRel(commerceDiscountOrderTypeRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel>
				getCommerceDiscountOrderTypeRels(
					long commerceDiscountId, String name, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.discount.model.
							CommerceDiscountOrderTypeRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountOrderTypeRelService.
			getCommerceDiscountOrderTypeRels(
				commerceDiscountId, name, start, end, orderByComparator);
	}

	@Override
	public int getCommerceDiscountOrderTypeRelsCount(
			long commerceDiscountId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountOrderTypeRelService.
			getCommerceDiscountOrderTypeRelsCount(commerceDiscountId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountOrderTypeRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceDiscountOrderTypeRelService getWrappedService() {
		return _commerceDiscountOrderTypeRelService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountOrderTypeRelService
			commerceDiscountOrderTypeRelService) {

		_commerceDiscountOrderTypeRelService =
			commerceDiscountOrderTypeRelService;
	}

	private CommerceDiscountOrderTypeRelService
		_commerceDiscountOrderTypeRelService;

}