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
 * Provides the remote service utility for DDMFormInstanceRecordVersion. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordVersionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordVersionService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordVersionServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordVersionServiceImpl
 * @generated
 */
@ProviderType
public class DDMFormInstanceRecordVersionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
		long ddmFormInstanceRecordVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormInstanceRecordVersion(ddmFormInstanceRecordVersionId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
		long ddmFormInstanceRecordId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormInstanceRecordVersion(ddmFormInstanceRecordId,
			version);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion> getFormInstanceRecordVersions(
		long ddmFormInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormInstanceRecordVersions(ddmFormInstanceRecordId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion> getFormInstanceRecordVersions(
		long ddmFormInstanceRecordId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormInstanceRecordVersions(ddmFormInstanceRecordId,
			start, end, orderByComparator);
	}

	public static int getFormInstanceRecordVersionsCount(
		long ddmFormInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormInstanceRecordVersionsCount(ddmFormInstanceRecordId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DDMFormInstanceRecordVersionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMFormInstanceRecordVersionService, DDMFormInstanceRecordVersionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMFormInstanceRecordVersionService.class);

		ServiceTracker<DDMFormInstanceRecordVersionService, DDMFormInstanceRecordVersionService> serviceTracker =
			new ServiceTracker<DDMFormInstanceRecordVersionService, DDMFormInstanceRecordVersionService>(bundle.getBundleContext(),
				DDMFormInstanceRecordVersionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}