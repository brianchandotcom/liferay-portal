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
 * Provides a wrapper for {@link CommerceDiscountAccountRelService}.
 *
 * @author Marco Leo
 * @see CommerceDiscountAccountRelService
 * @generated
 */
public class CommerceDiscountAccountRelServiceWrapper
	implements CommerceDiscountAccountRelService,
			   ServiceWrapper<CommerceDiscountAccountRelService> {

	public CommerceDiscountAccountRelServiceWrapper(
		CommerceDiscountAccountRelService commerceDiscountAccountRelService) {

		_commerceDiscountAccountRelService = commerceDiscountAccountRelService;
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountAccountRel
			addCommerceDiscountAccountRel(
				long commerceDiscountId, long commerceAccountId,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountAccountRelService.addCommerceDiscountAccountRel(
			commerceDiscountId, commerceAccountId, serviceContext);
	}

	@Override
	public void deleteCommerceDiscountAccountRel(
			long commerceDiscountAccountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceDiscountAccountRelService.deleteCommerceDiscountAccountRel(
			commerceDiscountAccountRelId);
	}

	@Override
	public void deleteCommerceDiscountAccountRelsByCommerceDiscountId(
			long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceDiscountAccountRelService.
			deleteCommerceDiscountAccountRelsByCommerceDiscountId(
				commerceDiscountId);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountAccountRel
			fetchCommerceDiscountAccountRel(
				long commerceAccountId, long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountAccountRelService.
			fetchCommerceDiscountAccountRel(
				commerceAccountId, commerceDiscountId);
	}

	@Override
	public com.liferay.commerce.discount.model.CommerceDiscountAccountRel
			getCommerceDiscountAccountRel(long commerceDiscountAccountRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountAccountRelService.getCommerceDiscountAccountRel(
			commerceDiscountAccountRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountAccountRel>
				getCommerceDiscountAccountRels(
					long commerceDiscountId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.discount.model.
							CommerceDiscountAccountRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountAccountRelService.
			getCommerceDiscountAccountRels(
				commerceDiscountId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.discount.model.CommerceDiscountAccountRel>
			getCommerceDiscountAccountRels(
				long commerceDiscountId, String name, int start, int end) {

		return _commerceDiscountAccountRelService.
			getCommerceDiscountAccountRels(
				commerceDiscountId, name, start, end);
	}

	@Override
	public int getCommerceDiscountAccountRelsCount(long commerceDiscountId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceDiscountAccountRelService.
			getCommerceDiscountAccountRelsCount(commerceDiscountId);
	}

	@Override
	public int getCommerceDiscountAccountRelsCount(
		long commerceDiscountId, String name) {

		return _commerceDiscountAccountRelService.
			getCommerceDiscountAccountRelsCount(commerceDiscountId, name);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceDiscountAccountRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceDiscountAccountRelService getWrappedService() {
		return _commerceDiscountAccountRelService;
	}

	@Override
	public void setWrappedService(
		CommerceDiscountAccountRelService commerceDiscountAccountRelService) {

		_commerceDiscountAccountRelService = commerceDiscountAccountRelService;
	}

	private CommerceDiscountAccountRelService
		_commerceDiscountAccountRelService;

}