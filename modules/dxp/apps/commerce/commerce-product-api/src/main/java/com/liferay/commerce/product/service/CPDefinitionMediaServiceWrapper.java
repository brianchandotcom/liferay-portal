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
 * Provides a wrapper for {@link CPDefinitionMediaService}.
 *
 * @author Marco Leo
 * @see CPDefinitionMediaService
 * @generated
 */
@ProviderType
public class CPDefinitionMediaServiceWrapper implements CPDefinitionMediaService,
	ServiceWrapper<CPDefinitionMediaService> {
	public CPDefinitionMediaServiceWrapper(
		CPDefinitionMediaService cpDefinitionMediaService) {
		_cpDefinitionMediaService = cpDefinitionMediaService;
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia addCPDefinitionMedia(
		long cpDefinitionId, long fileEntryId, java.lang.String ddmContent,
		int priority, long cpMediaTypeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaService.addCPDefinitionMedia(cpDefinitionId,
			fileEntryId, ddmContent, priority, cpMediaTypeId, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinitionMedia updateCPDefinitionMedia(
		long cpDefinitionMediaId, java.lang.String ddmContent, int priority,
		long cpMediaTypeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaService.updateCPDefinitionMedia(cpDefinitionMediaId,
			ddmContent, priority, cpMediaTypeId, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaService.deleteCPMediaType(cpMediaTypeId);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.product.model.CPDefinitionMedia> searchCPDefinitionMedias(
		long companyId, long groupId, long cpDefinitionId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinitionMediaService.searchCPDefinitionMedias(companyId,
			groupId, cpDefinitionId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _cpDefinitionMediaService.search(searchContext);
	}

	@Override
	public int getDefinitionMediasCount(long cpDefinitionId) {
		return _cpDefinitionMediaService.getDefinitionMediasCount(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpDefinitionMediaService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getDefinitionMedias(
		long cpDefinitionId, int start, int end) {
		return _cpDefinitionMediaService.getDefinitionMedias(cpDefinitionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPDefinitionMedia> getDefinitionMedias(
		long cpDefinitionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPDefinitionMedia> orderByComparator) {
		return _cpDefinitionMediaService.getDefinitionMedias(cpDefinitionId,
			start, end, orderByComparator);
	}

	@Override
	public CPDefinitionMediaService getWrappedService() {
		return _cpDefinitionMediaService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionMediaService cpDefinitionMediaService) {
		_cpDefinitionMediaService = cpDefinitionMediaService;
	}

	private CPDefinitionMediaService _cpDefinitionMediaService;
}