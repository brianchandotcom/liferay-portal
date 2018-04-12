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
 * Provides a wrapper for {@link CPSpecificationOptionService}.
 *
 * @author Marco Leo
 * @see CPSpecificationOptionService
 * @generated
 */
@ProviderType
public class CPSpecificationOptionServiceWrapper
	implements CPSpecificationOptionService,
		ServiceWrapper<CPSpecificationOptionService> {
	public CPSpecificationOptionServiceWrapper(
		CPSpecificationOptionService cpSpecificationOptionService) {
		_cpSpecificationOptionService = cpSpecificationOptionService;
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption addCPSpecificationOption(
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean facetable, java.lang.String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionService.addCPSpecificationOption(cpOptionCategoryId,
			titleMap, descriptionMap, facetable, key, serviceContext);
	}

	@Override
	public void deleteCPSpecificationOption(long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpSpecificationOptionService.deleteCPSpecificationOption(cpSpecificationOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption getCPSpecificationOption(
		long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionService.getCPSpecificationOption(cpSpecificationOptionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPSpecificationOption> getCPSpecificationOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPSpecificationOption> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionService.getCPSpecificationOptions(groupId,
			start, end, orderByComparator);
	}

	@Override
	public int getCPSpecificationOptionsCount(long groupId) {
		return _cpSpecificationOptionService.getCPSpecificationOptionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpSpecificationOptionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPSpecificationOption> searchCPSpecificationOptions(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionService.searchCPSpecificationOptions(companyId,
			groupId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption updateCPSpecificationOption(
		long cpSpecificationOptionId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		boolean facetable, java.lang.String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionService.updateCPSpecificationOption(cpSpecificationOptionId,
			cpOptionCategoryId, titleMap, descriptionMap, facetable, key,
			serviceContext);
	}

	@Override
	public CPSpecificationOptionService getWrappedService() {
		return _cpSpecificationOptionService;
	}

	@Override
	public void setWrappedService(
		CPSpecificationOptionService cpSpecificationOptionService) {
		_cpSpecificationOptionService = cpSpecificationOptionService;
	}

	private CPSpecificationOptionService _cpSpecificationOptionService;
}