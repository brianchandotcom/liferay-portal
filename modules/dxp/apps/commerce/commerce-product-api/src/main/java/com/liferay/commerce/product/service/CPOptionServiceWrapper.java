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
 * Provides a wrapper for {@link CPOptionService}.
 *
 * @author Marco Leo
 * @see CPOptionService
 * @generated
 */
@ProviderType
public class CPOptionServiceWrapper implements CPOptionService,
	ServiceWrapper<CPOptionService> {
	public CPOptionServiceWrapper(CPOptionService cpOptionService) {
		_cpOptionService = cpOptionService;
	}

	@Override
	public com.liferay.commerce.product.model.CPOption addCPOption(
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionService.addCPOption(nameMap, descriptionMap,
			ddmFormFieldTypeName, facetable, skuContributor, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption deleteCPOption(
		com.liferay.commerce.product.model.CPOption cpOption)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionService.deleteCPOption(cpOption);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption deleteCPOption(
		long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionService.deleteCPOption(cpOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption fetchCPOption(
		long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionService.fetchCPOption(cpOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption getCPOption(
		long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionService.getCPOption(cpOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOption updateCPOption(
		long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionService.updateCPOption(cpOptionId, nameMap,
			descriptionMap, ddmFormFieldTypeName, facetable, skuContributor,
			serviceContext);
	}

	@Override
	public int getCPOptionsCount(long groupId) {
		return _cpOptionService.getCPOptionsCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpOptionService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		long groupId, int start, int end) {
		return _cpOptionService.getCPOptions(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOption> getCPOptions(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOption> orderByComparator) {
		return _cpOptionService.getCPOptions(groupId, start, end,
			orderByComparator);
	}

	@Override
	public CPOptionService getWrappedService() {
		return _cpOptionService;
	}

	@Override
	public void setWrappedService(CPOptionService cpOptionService) {
		_cpOptionService = cpOptionService;
	}

	private CPOptionService _cpOptionService;
}