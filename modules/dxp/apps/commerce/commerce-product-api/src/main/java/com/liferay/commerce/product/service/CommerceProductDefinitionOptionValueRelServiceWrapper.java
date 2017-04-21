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
 * Provides a wrapper for {@link CommerceProductDefinitionOptionValueRelService}.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionValueRelService
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionValueRelServiceWrapper
	implements CommerceProductDefinitionOptionValueRelService,
		ServiceWrapper<CommerceProductDefinitionOptionValueRelService> {
	public CommerceProductDefinitionOptionValueRelServiceWrapper(
		CommerceProductDefinitionOptionValueRelService commerceProductDefinitionOptionValueRelService) {
		_commerceProductDefinitionOptionValueRelService = commerceProductDefinitionOptionValueRelService;
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel addCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelService.addCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionRelId,
			titleMap, priority, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel commerceProductDefinitionOptionValueRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelService.deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRel);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel deleteCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelService.deleteCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel getCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelService.getCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel updateCommerceProductDefinitionOptionValueRel(
		long commerceProductDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelService.updateCommerceProductDefinitionOptionValueRel(commerceProductDefinitionOptionValueRelId,
			titleMap, priority, serviceContext);
	}

	@Override
	public int getCommerceProductDefinitionOptionValueRelsCount(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelService.getCommerceProductDefinitionOptionValueRelsCount(commerceProductDefinitionOptionRelId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductDefinitionOptionValueRelService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelService.getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> getCommerceProductDefinitionOptionValueRels(
		long commerceProductDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionValueRelService.getCommerceProductDefinitionOptionValueRels(commerceProductDefinitionOptionRelId,
			start, end, orderByComparator);
	}

	@Override
	public CommerceProductDefinitionOptionValueRelService getWrappedService() {
		return _commerceProductDefinitionOptionValueRelService;
	}

	@Override
	public void setWrappedService(
		CommerceProductDefinitionOptionValueRelService commerceProductDefinitionOptionValueRelService) {
		_commerceProductDefinitionOptionValueRelService = commerceProductDefinitionOptionValueRelService;
	}

	private CommerceProductDefinitionOptionValueRelService _commerceProductDefinitionOptionValueRelService;
}