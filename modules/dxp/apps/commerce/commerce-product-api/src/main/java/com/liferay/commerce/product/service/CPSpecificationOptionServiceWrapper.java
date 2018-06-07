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
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean facetable, String key,
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
	public com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOption(
		long cpSpecificationOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionService.fetchCPSpecificationOption(cpSpecificationOptionId);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption fetchCPSpecificationOption(
		long groupId, String key)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionService.fetchCPSpecificationOption(groupId,
			key);
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
	public String getOSGiServiceIdentifier() {
		return _cpSpecificationOptionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPSpecificationOption> searchCPSpecificationOptions(
		long companyId, long groupId, Boolean facetable, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpSpecificationOptionService.searchCPSpecificationOptions(companyId,
			groupId, facetable, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPSpecificationOption updateCPSpecificationOption(
		long cpSpecificationOptionId, long cpOptionCategoryId,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		boolean facetable, String key,
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