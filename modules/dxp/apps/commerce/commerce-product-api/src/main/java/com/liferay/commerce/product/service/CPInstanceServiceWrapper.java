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
 * Provides a wrapper for {@link CPInstanceService}.
 *
 * @author Marco Leo
 * @see CPInstanceService
 * @generated
 */
@ProviderType
public class CPInstanceServiceWrapper implements CPInstanceService,
	ServiceWrapper<CPInstanceService> {
	public CPInstanceServiceWrapper(CPInstanceService cpInstanceService) {
		_cpInstanceService = cpInstanceService;
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance addCPInstance(
		long cpDefinitionId, java.lang.String sku, java.lang.String ddmContent,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.addCPInstance(cpDefinitionId, sku,
			ddmContent, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance deleteCPInstance(
		com.liferay.commerce.product.model.CPInstance cpInstance)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.deleteCPInstance(cpInstance);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance deleteCPInstance(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.deleteCPInstance(cpInstanceId);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance getCPInstance(
		long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPInstance(cpInstanceId);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updateCPInstance(
		long cpInstanceId, java.lang.String sku, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, int expirationDateMonth, int expirationDateDay,
		int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.updateCPInstance(cpInstanceId, sku,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPInstance updateStatus(
		long userId, long cpInstanceId, int status,
		com.liferay.portal.kernel.service.ServiceContext serviceContext,
		java.util.Map<java.lang.String, java.io.Serializable> workflowContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.updateStatus(userId, cpInstanceId, status,
			serviceContext, workflowContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPInstance> searchCPOptions(
		long companyId, long groupId, long cpDefinitionId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.searchCPOptions(companyId, groupId,
			cpDefinitionId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpInstanceService.search(searchContext);
	}

	@Override
	public int getCPInstancesCount(long cpDefinitionId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPInstancesCount(cpDefinitionId, status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpInstanceService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPInstance> getCPInstances(
		long cpDefinitionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPInstances(cpDefinitionId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPInstance> getCPInstances(
		long cpDefinitionId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPInstance> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpInstanceService.getCPInstances(cpDefinitionId, status, start,
			end, orderByComparator);
	}

	@Override
	public void buildCPInstances(long cpDefinitionId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cpInstanceService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	@Override
	public CPInstanceService getWrappedService() {
		return _cpInstanceService;
	}

	@Override
	public void setWrappedService(CPInstanceService cpInstanceService) {
		_cpInstanceService = cpInstanceService;
	}

	private CPInstanceService _cpInstanceService;
}