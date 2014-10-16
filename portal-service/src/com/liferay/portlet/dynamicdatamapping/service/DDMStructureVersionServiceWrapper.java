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

package com.liferay.portlet.dynamicdatamapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link DDMStructureVersionService}.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionService
 * @generated
 */
@ProviderType
public class DDMStructureVersionServiceWrapper
	implements DDMStructureVersionService,
		ServiceWrapper<DDMStructureVersionService> {
	public DDMStructureVersionServiceWrapper(
		DDMStructureVersionService ddmStructureVersionService) {
		_ddmStructureVersionService = ddmStructureVersionService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _ddmStructureVersionService.getBeanIdentifier();
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion getDDMStructureVersion(
		long ddmStructureVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionService.getDDMStructureVersion(ddmStructureVersionId);
	}

	@Override
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> getDDMStructureVersions(
		long ddmStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionService.getDDMStructureVersions(ddmStructureId,
			start, end, orderByComparator);
	}

	@Override
	public int getDDMStructureVersionsCount(long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionService.getDDMStructureVersionsCount(ddmStructureId);
	}

	@Override
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion getLatestVersion(
		long ddmStructureId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _ddmStructureVersionService.getLatestVersion(ddmStructureId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmStructureVersionService.setBeanIdentifier(beanIdentifier);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public DDMStructureVersionService getWrappedDDMStructureVersionService() {
		return _ddmStructureVersionService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedDDMStructureVersionService(
		DDMStructureVersionService ddmStructureVersionService) {
		_ddmStructureVersionService = ddmStructureVersionService;
	}

	@Override
	public DDMStructureVersionService getWrappedService() {
		return _ddmStructureVersionService;
	}

	@Override
	public void setWrappedService(
		DDMStructureVersionService ddmStructureVersionService) {
		_ddmStructureVersionService = ddmStructureVersionService;
	}

	private DDMStructureVersionService _ddmStructureVersionService;
}