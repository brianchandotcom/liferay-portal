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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTaxCategoryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTaxCategoryService
 * @generated
 */
@ProviderType
public class CommerceTaxCategoryServiceWrapper
	implements CommerceTaxCategoryService,
		ServiceWrapper<CommerceTaxCategoryService> {
	public CommerceTaxCategoryServiceWrapper(
		CommerceTaxCategoryService commerceTaxCategoryService) {
		_commerceTaxCategoryService = commerceTaxCategoryService;
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategory addCommerceTaxCategory(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryService.addCommerceTaxCategory(nameMap,
			descriptionMap, serviceContext);
	}

	@Override
	public void deleteCommerceTaxCategory(long commerceTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTaxCategoryService.deleteCommerceTaxCategory(commerceTaxCategoryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTaxCategory> getCommerceTaxCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTaxCategory> orderByComparator) {
		return _commerceTaxCategoryService.getCommerceTaxCategories(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceTaxCategoriesCount(long groupId) {
		return _commerceTaxCategoryService.getCommerceTaxCategoriesCount(groupId);
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategory getCommerceTaxCategory(
		long commerceTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryService.getCommerceTaxCategory(commerceTaxCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTaxCategoryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.model.CommerceTaxCategory updateCommerceTaxCategory(
		long commerceTaxCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTaxCategoryService.updateCommerceTaxCategory(commerceTaxCategoryId,
			nameMap, descriptionMap);
	}

	@Override
	public CommerceTaxCategoryService getWrappedService() {
		return _commerceTaxCategoryService;
	}

	@Override
	public void setWrappedService(
		CommerceTaxCategoryService commerceTaxCategoryService) {
		_commerceTaxCategoryService = commerceTaxCategoryService;
	}

	private CommerceTaxCategoryService _commerceTaxCategoryService;
}