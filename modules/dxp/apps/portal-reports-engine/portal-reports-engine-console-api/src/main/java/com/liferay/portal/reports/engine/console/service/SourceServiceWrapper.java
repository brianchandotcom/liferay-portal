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

package com.liferay.portal.reports.engine.console.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SourceService}.
 *
 * @author Brian Wing Shun Chan
 * @see SourceService
 * @generated
 */
@ProviderType
public class SourceServiceWrapper implements SourceService,
	ServiceWrapper<SourceService> {
	public SourceServiceWrapper(SourceService sourceService) {
		_sourceService = sourceService;
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Source addSource(
		long groupId, java.util.Map<java.util.Locale, String> nameMap,
		String driverClassName, String driverUrl, String driverUserName,
		String driverPassword,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sourceService.addSource(groupId, nameMap, driverClassName,
			driverUrl, driverUserName, driverPassword, serviceContext);
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Source deleteSource(
		long sourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sourceService.deleteSource(sourceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _sourceService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Source getSource(
		long sourceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sourceService.getSource(sourceId);
	}

	@Override
	public java.util.List<com.liferay.portal.reports.engine.console.model.Source> getSources(
		long groupId, String name, String driverUrl, boolean andSearch,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sourceService.getSources(groupId, name, driverUrl, andSearch,
			start, end, orderByComparator);
	}

	@Override
	public int getSourcesCount(long groupId, String name, String driverUrl,
		boolean andSearch) {
		return _sourceService.getSourcesCount(groupId, name, driverUrl,
			andSearch);
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Source updateSource(
		long sourceId, java.util.Map<java.util.Locale, String> nameMap,
		String driverClassName, String driverUrl, String driverUserName,
		String driverPassword,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _sourceService.updateSource(sourceId, nameMap, driverClassName,
			driverUrl, driverUserName, driverPassword, serviceContext);
	}

	@Override
	public SourceService getWrappedService() {
		return _sourceService;
	}

	@Override
	public void setWrappedService(SourceService sourceService) {
		_sourceService = sourceService;
	}

	private SourceService _sourceService;
}