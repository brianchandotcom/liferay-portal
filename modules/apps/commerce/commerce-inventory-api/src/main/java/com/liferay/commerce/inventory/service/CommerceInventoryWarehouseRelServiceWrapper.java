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

package com.liferay.commerce.inventory.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceInventoryWarehouseRelService}.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseRelService
 * @generated
 */
public class CommerceInventoryWarehouseRelServiceWrapper
	implements CommerceInventoryWarehouseRelService,
			   ServiceWrapper<CommerceInventoryWarehouseRelService> {

	public CommerceInventoryWarehouseRelServiceWrapper() {
		this(null);
	}

	public CommerceInventoryWarehouseRelServiceWrapper(
		CommerceInventoryWarehouseRelService
			commerceInventoryWarehouseRelService) {

		_commerceInventoryWarehouseRelService =
			commerceInventoryWarehouseRelService;
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel
			addCommerceInventoryWarehouseRel(
				String className, long classPK,
				long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseRelService.
			addCommerceInventoryWarehouseRel(
				className, classPK, commerceInventoryWarehouseId);
	}

	@Override
	public void deleteCommerceInventoryWarehouseRel(
			long commerceInventoryWarehouseRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceInventoryWarehouseRelService.
			deleteCommerceInventoryWarehouseRel(
				commerceInventoryWarehouseRelId);
	}

	@Override
	public void deleteCommerceInventoryWarehouseRels(
			String className, long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceInventoryWarehouseRelService.
			deleteCommerceInventoryWarehouseRels(
				className, commerceInventoryWarehouseId);
	}

	@Override
	public void
			deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseId(
				long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceInventoryWarehouseRelService.
			deleteCommerceInventoryWarehouseRelsByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel
			fetchCommerceInventoryWarehouseRel(
				String className, long classPK,
				long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseRelService.
			fetchCommerceInventoryWarehouseRel(
				className, classPK, commerceInventoryWarehouseId);
	}

	@Override
	public com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel
			getCommerceInventoryWarehouseRel(
				long commerceInventoryWarehouseRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseRelService.
			getCommerceInventoryWarehouseRel(commerceInventoryWarehouseRelId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getCommerceInventoryWarehouseRels(
					long commerceInventoryWarehouseId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseRelService.
			getCommerceInventoryWarehouseRels(commerceInventoryWarehouseId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getCommerceInventoryWarehouseRels(
					long commerceInventoryWarehouseId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.inventory.model.
							CommerceInventoryWarehouseRel> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseRelService.
			getCommerceInventoryWarehouseRels(
				commerceInventoryWarehouseId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceInventoryWarehouseRelsCount(
			long commerceInventoryWarehouseId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseRelService.
			getCommerceInventoryWarehouseRelsCount(
				commerceInventoryWarehouseId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.inventory.model.CommerceInventoryWarehouseRel>
				getCommerceOrderTypeCommerceInventoryWarehouseRels(
					long commerceInventoryWarehouseId, String keywords,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseRelService.
			getCommerceOrderTypeCommerceInventoryWarehouseRels(
				commerceInventoryWarehouseId, keywords, start, end);
	}

	@Override
	public int getCommerceOrderTypeCommerceInventoryWarehouseRelsCount(
			long commerceInventoryWarehouseId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceInventoryWarehouseRelService.
			getCommerceOrderTypeCommerceInventoryWarehouseRelsCount(
				commerceInventoryWarehouseId, keywords);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceInventoryWarehouseRelService.getOSGiServiceIdentifier();
	}

	@Override
	public CommerceInventoryWarehouseRelService getWrappedService() {
		return _commerceInventoryWarehouseRelService;
	}

	@Override
	public void setWrappedService(
		CommerceInventoryWarehouseRelService
			commerceInventoryWarehouseRelService) {

		_commerceInventoryWarehouseRelService =
			commerceInventoryWarehouseRelService;
	}

	private CommerceInventoryWarehouseRelService
		_commerceInventoryWarehouseRelService;

}