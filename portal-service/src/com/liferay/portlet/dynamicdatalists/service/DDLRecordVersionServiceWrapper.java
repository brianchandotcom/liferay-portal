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

package com.liferay.portlet.dynamicdatalists.service;

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
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _ddlRecordVersionService.getBeanIdentifier();
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		long recordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionService.getRecordVersion(recordId, version);
	}

	@Override
	public com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion getRecordVersion(
		long recordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionService.getRecordVersion(recordVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> getRecordVersions(
		long recordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionService.getRecordVersions(recordId, start, end,
			orderByComparator);
	}

	@Override
	public int getRecordVersionsCount(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddlRecordVersionService.getRecordVersionsCount(recordId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddlRecordVersionService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDLRecordVersionService getWrappedDDLRecordVersionService() {
		return _ddlRecordVersionService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDLRecordVersionService(
		DDLRecordVersionService ddlRecordVersionService) {
		_ddlRecordVersionService = ddlRecordVersionService;
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