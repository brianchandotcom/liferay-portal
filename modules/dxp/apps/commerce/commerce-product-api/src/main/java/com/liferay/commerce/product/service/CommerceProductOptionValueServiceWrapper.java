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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceProductOptionValueService}.
 *
 * @author Marco Leo
 * @see CommerceProductOptionValueService
 * @generated
 */
@ProviderType
public class CommerceProductOptionValueServiceWrapper
	implements CommerceProductOptionValueService,
		ServiceWrapper<CommerceProductOptionValueService> {
	public CommerceProductOptionValueServiceWrapper(
		CommerceProductOptionValueService commerceProductOptionValueService) {
		_commerceProductOptionValueService = commerceProductOptionValueService;
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductOptionValue addCommerceProductOptionValue(
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionValueService.addCommerceProductOptionValue(commerceProductOptionId,
			titleMap, priority, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductOptionValue deleteCommerceProductOptionValue(
		com.liferay.commerce.product.model.CommerceProductOptionValue commerceProductOptionValue)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionValueService.deleteCommerceProductOptionValue(commerceProductOptionValue);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductOptionValue deleteCommerceProductOptionValue(
		long commerceProductOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionValueService.deleteCommerceProductOptionValue(commerceProductOptionValueId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductOptionValue updateCommerceProductOptionValue(
		long commerceProductOptionValueId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionValueService.updateCommerceProductOptionValue(commerceProductOptionValueId,
			titleMap, priority, serviceContext);
	}

	@Override
	public int getCommerceProductOptionValuesCount(long commerceProductOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionValueService.getCommerceProductOptionValuesCount(commerceProductOptionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductOptionValueService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductOptionValue> getCommerceProductOptionValues(
		long commerceProductOptionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionValueService.getCommerceProductOptionValues(commerceProductOptionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductOptionValue> getCommerceProductOptionValues(
		long commerceProductOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductOptionValue> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductOptionValueService.getCommerceProductOptionValues(commerceProductOptionId,
			start, end, orderByComparator);
	}

	@Override
	public CommerceProductOptionValueService getWrappedService() {
		return _commerceProductOptionValueService;
	}

	@Override
	public void setWrappedService(
		CommerceProductOptionValueService commerceProductOptionValueService) {
		_commerceProductOptionValueService = commerceProductOptionValueService;
	}

	private CommerceProductOptionValueService _commerceProductOptionValueService;
}