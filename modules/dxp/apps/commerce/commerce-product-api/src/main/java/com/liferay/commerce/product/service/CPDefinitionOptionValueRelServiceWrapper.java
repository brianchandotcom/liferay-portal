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
 * Provides a wrapper for {@link CPDefinitionOptionValueRelService}.
 *
 * @author Marco Leo
 * @see CPDefinitionOptionValueRelService
 * @generated
 */
@ProviderType
public class CPDefinitionOptionValueRelServiceWrapper
	implements CPDefinitionOptionValueRelService,
		ServiceWrapper<CPDefinitionOptionValueRelService> {
	public CPDefinitionOptionValueRelServiceWrapper(
		CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService) {
		_cpDefinitionOptionValueRelService = cpDefinitionOptionValueRelService;
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel addCPDefinitionOptionValueRel(
		long cpDefinitionOptionRelId,
		java.util.Map<java.util.Locale, String> nameMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelService.addCPDefinitionOptionValueRel(cpDefinitionOptionRelId,
			nameMap, priority, key, serviceContext);
	}

	@Override
	public void deleteCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpDefinitionOptionValueRelService.deleteCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel fetchCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelService.fetchCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel getCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRels(cpDefinitionOptionRelId,
			start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels(
		long groupId, String key, int start, int end) {
		return _cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRels(groupId,
			key, start, end);
	}

	@Override
	public int getCPDefinitionOptionValueRelsCount(long cpDefinitionOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRelsCount(cpDefinitionOptionRelId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionOptionValueRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionOptionValueRel> searchCPDefinitionOptionValueRels(
		long companyId, long groupId, long cpDefinitionOptionRelId,
		String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelService.searchCPDefinitionOptionValueRels(companyId,
			groupId, cpDefinitionOptionRelId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
		long cpDefinitionOptionValueRelId,
		java.util.Map<java.util.Locale, String> nameMap, double priority,
		String key,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionOptionValueRelService.updateCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId,
			nameMap, priority, key, serviceContext);
	}

	@Override
	public CPDefinitionOptionValueRelService getWrappedService() {
		return _cpDefinitionOptionValueRelService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService) {
		_cpDefinitionOptionValueRelService = cpDefinitionOptionValueRelService;
	}

	private CPDefinitionOptionValueRelService _cpDefinitionOptionValueRelService;
}