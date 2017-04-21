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
 * Provides a wrapper for {@link CommerceProductDefinitionOptionRelService}.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionOptionRelService
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionOptionRelServiceWrapper
	implements CommerceProductDefinitionOptionRelService,
		ServiceWrapper<CommerceProductDefinitionOptionRelService> {
	public CommerceProductDefinitionOptionRelServiceWrapper(
		CommerceProductDefinitionOptionRelService commerceProductDefinitionOptionRelService) {
		_commerceProductDefinitionOptionRelService = commerceProductDefinitionOptionRelService;
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel addCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionId, long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelService.addCommerceProductDefinitionOptionRel(commerceProductDefinitionId,
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel commerceProductDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelService.deleteCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRel);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel deleteCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelService.deleteCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel getCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelService.getCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel updateCommerceProductDefinitionOptionRel(
		long commerceProductDefinitionOptionRelId,
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelService.updateCommerceProductDefinitionOptionRel(commerceProductDefinitionOptionRelId,
			commerceProductOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, serviceContext);
	}

	@Override
	public int getCommerceProductDefinitionOptionRelsCount(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelService.getCommerceProductDefinitionOptionRelsCount(commerceProductDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductDefinitionOptionRelService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelService.getCommerceProductDefinitionOptionRels(commerceProductDefinitionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> getCommerceProductDefinitionOptionRels(
		long commerceProductDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinitionOptionRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionOptionRelService.getCommerceProductDefinitionOptionRels(commerceProductDefinitionId,
			start, end, orderByComparator);
	}

	@Override
	public CommerceProductDefinitionOptionRelService getWrappedService() {
		return _commerceProductDefinitionOptionRelService;
	}

	@Override
	public void setWrappedService(
		CommerceProductDefinitionOptionRelService commerceProductDefinitionOptionRelService) {
		_commerceProductDefinitionOptionRelService = commerceProductDefinitionOptionRelService;
	}

	private CommerceProductDefinitionOptionRelService _commerceProductDefinitionOptionRelService;
}