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
 * Provides a wrapper for {@link CommerceProductDefinitionService}.
 *
 * @author Marco Leo
 * @see CommerceProductDefinitionService
 * @generated
 */
@ProviderType
public class CommerceProductDefinitionServiceWrapper
	implements CommerceProductDefinitionService,
		ServiceWrapper<CommerceProductDefinitionService> {
	public CommerceProductDefinitionServiceWrapper(
		CommerceProductDefinitionService commerceProductDefinitionService) {
		_commerceProductDefinitionService = commerceProductDefinitionService;
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition addCommerceProductDefinition(
		java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String productTypeName, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionService.addCommerceProductDefinition(baseSKU,
			titleMap, descriptionMap, productTypeName, ddmStructureKey,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition deleteCommerceProductDefinition(
		com.liferay.commerce.product.model.CommerceProductDefinition commerceProductDefinition)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionService.deleteCommerceProductDefinition(commerceProductDefinition);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition deleteCommerceProductDefinition(
		long commerceProductDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionService.deleteCommerceProductDefinition(commerceProductDefinitionId);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition updateCommerceProductDefinition(
		long commerceProductDefinitionId, java.lang.String baseSKU,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String productTypeName, java.lang.String ddmStructureKey,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionService.updateCommerceProductDefinition(commerceProductDefinitionId,
			baseSKU, titleMap, descriptionMap, productTypeName,
			ddmStructureKey, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CommerceProductDefinition updateStatus(
		long userId, long commerceProductDefinitionId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceProductDefinitionService.updateStatus(userId,
			commerceProductDefinitionId, status, serviceContext, workflowContext);
	}

	@Override
	public int getCommerceProductDefinitionsCount(long groupId) {
		return _commerceProductDefinitionService.getCommerceProductDefinitionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceProductDefinitionService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end) {
		return _commerceProductDefinitionService.getCommerceProductDefinitions(groupId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CommerceProductDefinition> getCommerceProductDefinitions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductDefinition> orderByComparator) {
		return _commerceProductDefinitionService.getCommerceProductDefinitions(groupId,
			start, end, orderByComparator);
	}

	@Override
	public CommerceProductDefinitionService getWrappedService() {
		return _commerceProductDefinitionService;
	}

	@Override
	public void setWrappedService(
		CommerceProductDefinitionService commerceProductDefinitionService) {
		_commerceProductDefinitionService = commerceProductDefinitionService;
	}

	private CommerceProductDefinitionService _commerceProductDefinitionService;
}