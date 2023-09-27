/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SourceService}.
 *
 * @author Brian Wing Shun Chan
 * @see SourceService
 * @generated
 */
public class SourceServiceWrapper
	implements ServiceWrapper<SourceService>, SourceService {

	public SourceServiceWrapper() {
		this(null);
	}

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

		return _sourceService.addSource(
			groupId, nameMap, driverClassName, driverUrl, driverUserName,
			driverPassword, serviceContext);
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
	public java.util.List
		<com.liferay.portal.reports.engine.console.model.Source> getSources(
				long groupId, String name, String driverUrl, boolean andSearch,
				int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.reports.engine.console.model.Source>
						orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _sourceService.getSources(
			groupId, name, driverUrl, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getSourcesCount(
		long groupId, String name, String driverUrl, boolean andSearch) {

		return _sourceService.getSourcesCount(
			groupId, name, driverUrl, andSearch);
	}

	@Override
	public com.liferay.portal.reports.engine.console.model.Source updateSource(
			long sourceId, java.util.Map<java.util.Locale, String> nameMap,
			String driverClassName, String driverUrl, String driverUserName,
			String driverPassword,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sourceService.updateSource(
			sourceId, nameMap, driverClassName, driverUrl, driverUserName,
			driverPassword, serviceContext);
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