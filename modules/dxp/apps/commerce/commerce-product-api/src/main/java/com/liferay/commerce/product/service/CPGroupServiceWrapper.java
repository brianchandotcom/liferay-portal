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
 * Provides a wrapper for {@link CPGroupService}.
 *
 * @author Marco Leo
 * @see CPGroupService
 * @generated
 */
@ProviderType
public class CPGroupServiceWrapper implements CPGroupService,
	ServiceWrapper<CPGroupService> {
	public CPGroupServiceWrapper(CPGroupService cpGroupService) {
		_cpGroupService = cpGroupService;
	}

	@Override
	public com.liferay.commerce.product.model.CPGroup addCPGroup(
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupService.addCPGroup(serviceContext);
	}

	@Override
	public com.liferay.commerce.product.model.CPGroup deleteCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupService.deleteCPGroupByGroupId(groupId);
	}

	@Override
	public com.liferay.commerce.product.model.CPGroup fetchCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupService.fetchCPGroupByGroupId(groupId);
	}

	@Override
	public com.liferay.commerce.product.model.CPGroup getCPGroupByGroupId(
		long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpGroupService.getCPGroupByGroupId(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cpGroupService.getOSGiServiceIdentifier();
	}

	@Override
	public CPGroupService getWrappedService() {
		return _cpGroupService;
	}

	@Override
	public void setWrappedService(CPGroupService cpGroupService) {
		_cpGroupService = cpGroupService;
	}

	private CPGroupService _cpGroupService;
}