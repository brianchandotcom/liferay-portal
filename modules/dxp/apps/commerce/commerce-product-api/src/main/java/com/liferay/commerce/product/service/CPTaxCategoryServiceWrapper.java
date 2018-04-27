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
 * Provides a wrapper for {@link CPTaxCategoryService}.
 *
 * @author Marco Leo
 * @see CPTaxCategoryService
 * @generated
 */
@ProviderType
public class CPTaxCategoryServiceWrapper implements CPTaxCategoryService,
	ServiceWrapper<CPTaxCategoryService> {
	public CPTaxCategoryServiceWrapper(
		CPTaxCategoryService cpTaxCategoryService) {
		_cpTaxCategoryService = cpTaxCategoryService;
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory addCPTaxCategory(
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryService.addCPTaxCategory(nameMap, descriptionMap,
			serviceContext);
	}

	@Override
	public void deleteCPTaxCategory(long cpTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpTaxCategoryService.deleteCPTaxCategory(cpTaxCategoryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryService.getCPTaxCategories(groupId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPTaxCategory> getCPTaxCategories(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPTaxCategory> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryService.getCPTaxCategories(groupId, start, end,
			orderByComparator);
	}

	@Override
	public int getCPTaxCategoriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryService.getCPTaxCategoriesCount(groupId);
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory getCPTaxCategory(
		long cpTaxCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryService.getCPTaxCategory(cpTaxCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpTaxCategoryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.product.model.CPTaxCategory updateCPTaxCategory(
		long cpTaxCategoryId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpTaxCategoryService.updateCPTaxCategory(cpTaxCategoryId,
			nameMap, descriptionMap);
	}

	@Override
	public CPTaxCategoryService getWrappedService() {
		return _cpTaxCategoryService;
	}

	@Override
	public void setWrappedService(CPTaxCategoryService cpTaxCategoryService) {
		_cpTaxCategoryService = cpTaxCategoryService;
	}

	private CPTaxCategoryService _cpTaxCategoryService;
}