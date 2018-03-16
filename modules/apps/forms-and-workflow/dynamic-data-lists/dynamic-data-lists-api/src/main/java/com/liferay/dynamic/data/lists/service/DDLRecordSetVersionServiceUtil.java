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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for DDLRecordSetVersion. This utility wraps
 * {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetVersionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersionService
 * @see com.liferay.dynamic.data.lists.service.base.DDLRecordSetVersionServiceBaseImpl
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordSetVersionServiceImpl
 * @generated
 */
@ProviderType
public class DDLRecordSetVersionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetVersionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion getLatestRecordSetVersion(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestRecordSetVersion(recordSetId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion getRecordSetVersion(
		long recordSetVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordSetVersion(recordSetVersionId);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSetVersion> getRecordSetVersions(
		long recordSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSetVersion> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getRecordSetVersions(recordSetId, start, end,
			orderByComparator);
	}

	public static int getRecordSetVersionsCount(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordSetVersionsCount(recordSetId);
	}

	public static DDLRecordSetVersionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDLRecordSetVersionService, DDLRecordSetVersionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDLRecordSetVersionService.class);

		ServiceTracker<DDLRecordSetVersionService, DDLRecordSetVersionService> serviceTracker =
			new ServiceTracker<DDLRecordSetVersionService, DDLRecordSetVersionService>(bundle.getBundleContext(),
				DDLRecordSetVersionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}