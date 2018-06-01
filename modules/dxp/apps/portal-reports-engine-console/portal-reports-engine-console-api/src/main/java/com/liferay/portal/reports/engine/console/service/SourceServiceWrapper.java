/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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