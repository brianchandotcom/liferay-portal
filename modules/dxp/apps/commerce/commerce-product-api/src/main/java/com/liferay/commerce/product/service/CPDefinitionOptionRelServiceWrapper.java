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
 * Provides a wrapper for {@link CPDefinitionOptionRelService}.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionRelService
 * @generated
 */
@ProviderType
public class CPDefinitionOptionRelServiceWrapper
	implements CPDefinitionOptionRelService,
		ServiceWrapper<CPDefinitionOptionRelService> {
	public CPDefinitionOptionRelServiceWrapper(
		CPDefinitionOptionRelService cpDefinitionOptionRelService) {
		_cpDefinitionOptionRelService = cpDefinitionOptionRelService;
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.addCPDefinitionOptionRel(cpDefinitionId,
			cpOptionId, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel addCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.addCPDefinitionOptionRel(cpDefinitionId,
			cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
			priority, facetable, skuContributor, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		com.liferay.commerce.product.model.CPDefinitionOptionRel cpDefinitionOptionRel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.deleteCPDefinitionOptionRel(cpDefinitionOptionRel);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel deleteCPDefinitionOptionRel(
		long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.deleteCPDefinitionOptionRel(cpDefinitionOptionRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel fetchCPDefinitionOptionRel(
		long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.fetchCPDefinitionOptionRel(cpDefinitionOptionRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel getCPDefinitionOptionRel(
		long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getCPDefinitionOptionRel(cpDefinitionOptionRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel updateCPDefinitionOptionRel(
		long cpDefinitionOptionRelId, long cpOptionId,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		java.lang.String ddmFormFieldTypeName, int priority, boolean facetable,
		boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.updateCPDefinitionOptionRel(cpDefinitionOptionRelId,
			cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
			priority, facetable, skuContributor, serviceContext);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getCPDefinitionOptionRelsCount(cpDefinitionId);
	}

	@Override
	public int getSkuContributorCPDefinitionOptionRelCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getSkuContributorCPDefinitionOptionRelCount(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionOptionRelService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getCPDefinitionOptionRels(cpDefinitionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getCPDefinitionOptionRels(cpDefinitionId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getSkuContributorCPDefinitionOptionRels(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getSkuContributorCPDefinitionOptionRels(cpDefinitionId);
	}

	@Override
	public CPDefinitionOptionRelService getWrappedService() {
		return _cpDefinitionOptionRelService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionOptionRelService cpDefinitionOptionRelService) {
		_cpDefinitionOptionRelService = cpDefinitionOptionRelService;
	}

	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;
}