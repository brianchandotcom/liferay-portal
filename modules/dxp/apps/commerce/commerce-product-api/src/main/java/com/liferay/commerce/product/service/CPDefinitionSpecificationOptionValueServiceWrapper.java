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
 * Provides a wrapper for {@link CPDefinitionSpecificationOptionValueService}.
 *
 * @author Marco Leo
 * @see CPDefinitionSpecificationOptionValueService
 * @generated
 */
@ProviderType
public class CPDefinitionSpecificationOptionValueServiceWrapper
	implements CPDefinitionSpecificationOptionValueService,
		ServiceWrapper<CPDefinitionSpecificationOptionValueService> {
	public CPDefinitionSpecificationOptionValueServiceWrapper(
		CPDefinitionSpecificationOptionValueService cpDefinitionSpecificationOptionValueService) {
		_cpDefinitionSpecificationOptionValueService = cpDefinitionSpecificationOptionValueService;
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue addCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId,
		long cpOptionCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> valueMap,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValueService.addCPDefinitionSpecificationOptionValue(cpDefinitionId,
			cpSpecificationOptionId, cpOptionCategoryId, valueMap, priority,
			serviceContext);
	}

	@Override
	public void deleteCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionSpecificationOptionValueService.deleteCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValueService.fetchCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue getCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValueService.getCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValueService.getCPDefinitionSpecificationOptionValues(cpDefinitionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues(
		long cpDefinitionId, long cpOptionCategoryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValueService.getCPDefinitionSpecificationOptionValues(cpDefinitionId,
			cpOptionCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionSpecificationOptionValueService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, java.lang.String> valueMap,
		double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionSpecificationOptionValueService.updateCPDefinitionSpecificationOptionValue(cpDefinitionSpecificationOptionValueId,
			cpOptionCategoryId, valueMap, priority, serviceContext);
	}

	@Override
	public CPDefinitionSpecificationOptionValueService getWrappedService() {
		return _cpDefinitionSpecificationOptionValueService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionSpecificationOptionValueService cpDefinitionSpecificationOptionValueService) {
		_cpDefinitionSpecificationOptionValueService = cpDefinitionSpecificationOptionValueService;
	}

	private CPDefinitionSpecificationOptionValueService _cpDefinitionSpecificationOptionValueService;
}