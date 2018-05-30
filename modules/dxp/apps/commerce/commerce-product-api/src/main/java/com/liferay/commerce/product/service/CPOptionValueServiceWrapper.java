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
 * Provides a wrapper for {@link CPOptionValueService}.
 *
 * @author Marco Leo
 * @see CPOptionValueService
 * @generated
 */
@ProviderType
public class CPOptionValueServiceWrapper implements CPOptionValueService,
	ServiceWrapper<CPOptionValueService> {
	public CPOptionValueServiceWrapper(
		CPOptionValueService cpOptionValueService) {
		_cpOptionValueService = cpOptionValueService;
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue addCPOptionValue(
		long cpOptionId, java.util.Map<java.util.Locale, String> titleMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.addCPOptionValue(cpOptionId, titleMap,
			priority, key, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue deleteCPOptionValue(
		com.liferay.commerce.product.model.CPOptionValue cpOptionValue)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.deleteCPOptionValue(cpOptionValue);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue deleteCPOptionValue(
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.deleteCPOptionValue(cpOptionValueId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue fetchCPOptionValue(
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.fetchCPOptionValue(cpOptionValueId);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue getCPOptionValue(
		long cpOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.getCPOptionValue(cpOptionValueId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.getCPOptionValues(cpOptionId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPOptionValue> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.getCPOptionValues(cpOptionId, start, end,
			orderByComparator);
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.getCPOptionValuesCount(cpOptionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpOptionValueService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpOptionValueService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPOptionValue> searchCPOptionValues(
		long companyId, long groupId, long cpOptionId, String keywords,
		int start, int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.searchCPOptionValues(companyId, groupId,
			cpOptionId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPOptionValue updateCPOptionValue(
		long cpOptionValueId, java.util.Map<java.util.Locale, String> titleMap,
		double priority, String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpOptionValueService.updateCPOptionValue(cpOptionValueId,
			titleMap, priority, key, serviceContext);
	}

	@Override
	public CPOptionValueService getWrappedService() {
		return _cpOptionValueService;
	}

	@Override
	public void setWrappedService(CPOptionValueService cpOptionValueService) {
		_cpOptionValueService = cpOptionValueService;
	}

	private CPOptionValueService _cpOptionValueService;
}