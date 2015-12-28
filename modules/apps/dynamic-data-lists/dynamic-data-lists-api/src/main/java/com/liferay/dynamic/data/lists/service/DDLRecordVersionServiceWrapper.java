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

package com.liferay.dynamic.data.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDLRecordVersionService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionService
 * @generated
 */
@ProviderType
public class DDLRecordVersionServiceWrapper implements DDLRecordVersionService,
	ServiceWrapper<DDLRecordVersionService> {
	public DDLRecordVersionServiceWrapper(
		DDLRecordVersionService ddlRecordVersionService) {
		_ddlRecordVersionService = ddlRecordVersionService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _ddlRecordVersionService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionService.getRecordVersion(recordId, version);
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionService.getRecordVersion(recordVersionId);
	}

	@Override
	public java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionService.getRecordVersions(recordId, start, end,
			orderByComparator);
	}

	@Override
	public int getRecordVersionsCount(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionService.getRecordVersionsCount(recordId);
	}

	@Override
	public DDLRecordVersionService getWrappedService() {
		return _ddlRecordVersionService;
	}

	@Override
	public void setWrappedService(
		DDLRecordVersionService ddlRecordVersionService) {
		_ddlRecordVersionService = ddlRecordVersionService;
	}

	private DDLRecordVersionService _ddlRecordVersionService;
}