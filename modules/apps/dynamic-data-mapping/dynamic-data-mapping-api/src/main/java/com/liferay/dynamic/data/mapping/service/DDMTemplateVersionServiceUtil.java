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

package com.liferay.dynamic.data.mapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DDMTemplateVersion. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateVersionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMTemplateVersionServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMTemplateVersionServiceImpl
 * @generated
 */
@ProviderType
public class DDMTemplateVersionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMTemplateVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getLatestTemplateVersion(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestTemplateVersion(templateId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.dynamic.data.mapping.model.DDMTemplateVersion getTemplateVersion(
		long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateVersion(templateVersionId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion> getTemplateVersions(
		long templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMTemplateVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getTemplateVersions(templateId, start, end,
			orderByComparator);
	}

	public static int getTemplateVersionsCount(long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateVersionsCount(templateId);
	}

	public static DDMTemplateVersionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMTemplateVersionService, DDMTemplateVersionService> _serviceTracker =
		ServiceTrackerFactory.open(DDMTemplateVersionService.class);
}