/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
		java.util.Map<java.util.Locale, String> valueMap, double priority,
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
	public com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue fetchCPDefinitionSpecificationOptionValue(
		long cpDefinitionId, long cpSpecificationOptionId) {
		return _cpDefinitionSpecificationOptionValueService.fetchCPDefinitionSpecificationOptionValue(cpDefinitionId,
			cpSpecificationOptionId);
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
		long cpDefinitionId, long cpOptionCategoryId) {
		return _cpDefinitionSpecificationOptionValueService.getCPDefinitionSpecificationOptionValues(cpDefinitionId,
			cpOptionCategoryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionSpecificationOptionValueService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue updateCPDefinitionSpecificationOptionValue(
		long cpDefinitionSpecificationOptionValueId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> valueMap, double priority,
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