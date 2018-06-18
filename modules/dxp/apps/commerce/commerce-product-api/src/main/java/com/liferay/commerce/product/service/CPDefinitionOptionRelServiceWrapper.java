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
	public void deleteCPDefinitionOptionRel(long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionOptionRelService.deleteCPDefinitionOptionRel(cpDefinitionOptionRelId);
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
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, boolean skuContributor)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getCPDefinitionOptionRels(cpDefinitionId,
			skuContributor);
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
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getCPDefinitionOptionRelsCount(cpDefinitionId);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId,
		boolean skuContributor)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.getCPDefinitionOptionRelsCount(cpDefinitionId,
			skuContributor);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionOptionRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionRel> searchCPDefinitionOptionRels(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.searchCPDefinitionOptionRels(companyId,
			groupId, cpDefinitionId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionRel updateCPDefinitionOptionRel(
		long cpDefinitionOptionRelId, long cpOptionId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String ddmFormFieldTypeName, double priority, boolean facetable,
		boolean required, boolean skuContributor,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionRelService.updateCPDefinitionOptionRel(cpDefinitionOptionRelId,
			cpOptionId, nameMap, descriptionMap, ddmFormFieldTypeName,
			priority, facetable, required, skuContributor, serviceContext);
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