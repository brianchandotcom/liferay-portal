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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for Definition. This utility wraps
 * {@link com.liferay.portal.reports.engine.console.service.impl.DefinitionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DefinitionService
 * @see com.liferay.portal.reports.engine.console.service.base.DefinitionServiceBaseImpl
 * @see com.liferay.portal.reports.engine.console.service.impl.DefinitionServiceImpl
 * @generated
 */
@ProviderType
public class DefinitionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.reports.engine.console.service.impl.DefinitionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.reports.engine.console.model.Definition addDefinition(
		long groupId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, long sourceId,
		String reportParameters, String fileName,
		java.io.InputStream inputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addDefinition(groupId, nameMap, descriptionMap, sourceId,
			reportParameters, fileName, inputStream, serviceContext);
	}

	public static com.liferay.portal.reports.engine.console.model.Definition deleteDefinition(
		long definitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDefinition(definitionId);
	}

	public static com.liferay.portal.reports.engine.console.model.Definition getDefinition(
		long definitionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDefinition(definitionId);
	}

	public static java.util.List<com.liferay.portal.reports.engine.console.model.Definition> getDefinitions(
		long groupId, String definitionName, String description,
		String sourceId, String reportName, boolean andSearch, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getDefinitions(groupId, definitionName, description,
			sourceId, reportName, andSearch, start, end, orderByComparator);
	}

	public static int getDefinitionsCount(long groupId, String definitionName,
		String description, String sourceId, String reportName,
		boolean andSearch) {
		return getService()
				   .getDefinitionsCount(groupId, definitionName, description,
			sourceId, reportName, andSearch);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.reports.engine.console.model.Definition updateDefinition(
		long definitionId, java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap, long sourceId,
		String reportParameters, String fileName,
		java.io.InputStream inputStream,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateDefinition(definitionId, nameMap, descriptionMap,
			sourceId, reportParameters, fileName, inputStream, serviceContext);
	}

	public static DefinitionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DefinitionService, DefinitionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DefinitionService.class);

		ServiceTracker<DefinitionService, DefinitionService> serviceTracker = new ServiceTracker<DefinitionService, DefinitionService>(bundle.getBundleContext(),
				DefinitionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}