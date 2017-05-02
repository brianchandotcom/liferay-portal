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
 * Provides a wrapper for {@link CPMediaTypeService}.
 *
 * @author Marco Leo
 * @see CPMediaTypeService
 * @generated
 */
@ProviderType
public class CPMediaTypeServiceWrapper implements CPMediaTypeService,
	ServiceWrapper<CPMediaTypeService> {
	public CPMediaTypeServiceWrapper(CPMediaTypeService cpMediaTypeService) {
		_cpMediaTypeService = cpMediaTypeService;
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType addCPMediaType(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeService.addCPMediaType(titleMap, descriptionMap,
			priority, serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		com.liferay.commerce.product.model.CPMediaType cpMediaType)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeService.deleteCPMediaType(cpMediaType);
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType deleteCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeService.deleteCPMediaType(cpMediaTypeId);
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType fetchCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeService.fetchCPMediaType(cpMediaTypeId);
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType getCPMediaType(
		long cpMediaTypeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeService.getCPMediaType(cpMediaTypeId);
	}

	@Override
	public com.liferay.commerce.product.model.CPMediaType updateCPMediaType(
		long cpMediaTypeId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		java.util.Map<java.util.Locale, java.lang.String> descriptionMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpMediaTypeService.updateCPMediaType(cpMediaTypeId, titleMap,
			descriptionMap, priority, serviceContext);
	}

	@Override
	public int getCPMediaTypesCount(long groupId) {
		return _cpMediaTypeService.getCPMediaTypesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpMediaTypeService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMediaType> getCPMediaTypes(
		long groupId, int start, int end) {
		return _cpMediaTypeService.getCPMediaTypes(groupId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.product.model.CPMediaType> getCPMediaTypes(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPMediaType> orderByComparator) {
		return _cpMediaTypeService.getCPMediaTypes(groupId, start, end,
			orderByComparator);
	}

	@Override
	public CPMediaTypeService getWrappedService() {
		return _cpMediaTypeService;
	}

	@Override
	public void setWrappedService(CPMediaTypeService cpMediaTypeService) {
		_cpMediaTypeService = cpMediaTypeService;
	}

	private CPMediaTypeService _cpMediaTypeService;
}