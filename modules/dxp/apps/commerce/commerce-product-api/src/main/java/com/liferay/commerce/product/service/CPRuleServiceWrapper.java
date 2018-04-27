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
 * Provides a wrapper for {@link CPRuleService}.
 *
 * @author Marco Leo
 * @see CPRuleService
 * @generated
 */
@ProviderType
public class CPRuleServiceWrapper implements CPRuleService,
	ServiceWrapper<CPRuleService> {
	public CPRuleServiceWrapper(CPRuleService cpRuleService) {
		_cpRuleService = cpRuleService;
	}

	@Override
	public com.liferay.commerce.product.model.CPRule addCPRule(String name,
		boolean active, String type, String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleService.addCPRule(name, active, type, typeSettings,
			serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPRule deleteCPRule(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleService.deleteCPRule(cpRuleId);
	}

	@Override
	public com.liferay.commerce.product.model.CPRule getCPRule(long cpRuleId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleService.getCPRule(cpRuleId);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPRule> getCPRules(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPRule> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleService.getCPRules(groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPRulesCount(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleService.getCPRulesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpRuleService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPRule> searchCPRules(
		long companyId, long groupId, String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleService.searchCPRules(companyId, groupId, keywords,
			start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPRule> searchCPRules(
		com.liferay.portal.kernel.search.SearchContext searchContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleService.searchCPRules(searchContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPRule updateCPRule(
		long cpRuleId, String name, boolean active, String type,
		String typeSettings,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpRuleService.updateCPRule(cpRuleId, name, active, type,
			typeSettings, serviceContext);
	}

	@Override
	public CPRuleService getWrappedService() {
		return _cpRuleService;
	}

	@Override
	public void setWrappedService(CPRuleService cpRuleService) {
		_cpRuleService = cpRuleService;
	}

	private CPRuleService _cpRuleService;
}