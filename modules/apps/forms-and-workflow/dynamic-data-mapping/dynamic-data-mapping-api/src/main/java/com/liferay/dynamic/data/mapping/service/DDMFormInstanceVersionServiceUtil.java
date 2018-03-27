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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DDMFormInstanceVersion. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceVersionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersionService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceVersionServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceVersionServiceImpl
 * @generated
 */
@ProviderType
public class DDMFormInstanceVersionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion getFormInstanceVersion(
		long ddmFormInstanceVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFormInstanceVersion(ddmFormInstanceVersionId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion> getFormInstanceVersions(
		long ddmFormInstanceId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormInstanceVersions(ddmFormInstanceId, start, end,
			orderByComparator);
	}

	public static int getFormInstanceVersionsCount(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFormInstanceVersionsCount(ddmFormInstanceId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion getLatestFormInstanceVersion(
		long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestFormInstanceVersion(ddmFormInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DDMFormInstanceVersionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMFormInstanceVersionService, DDMFormInstanceVersionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMFormInstanceVersionService.class);

		ServiceTracker<DDMFormInstanceVersionService, DDMFormInstanceVersionService> serviceTracker =
			new ServiceTracker<DDMFormInstanceVersionService, DDMFormInstanceVersionService>(bundle.getBundleContext(),
				DDMFormInstanceVersionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}