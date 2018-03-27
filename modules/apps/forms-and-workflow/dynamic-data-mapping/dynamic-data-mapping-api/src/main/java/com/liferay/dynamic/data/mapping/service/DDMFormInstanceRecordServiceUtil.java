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
 * Provides the remote service utility for DDMFormInstanceRecord. This utility wraps
 * {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordService
 * @see com.liferay.dynamic.data.mapping.service.base.DDMFormInstanceRecordServiceBaseImpl
 * @see com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordServiceImpl
 * @generated
 */
@ProviderType
public class DDMFormInstanceRecordServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord addFormInstanceRecord(
		long groupId, long ddmFormInstanceId,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addFormInstanceRecord(groupId, ddmFormInstanceId,
			ddmFormValues, serviceContext);
	}

	public static void deleteFormInstanceRecord(long ddmFormInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteFormInstanceRecord(ddmFormInstanceRecordId);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord getFormInstanceRecord(
		long ddmFormInstanceRecordId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFormInstanceRecord(ddmFormInstanceRecordId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFormInstanceRecords(ddmFormInstanceId);
	}

	public static java.util.List<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord> getFormInstanceRecords(
		long ddmFormInstanceId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getFormInstanceRecords(ddmFormInstanceId, status, start,
			end, orderByComparator);
	}

	public static int getFormInstanceRecordsCount(long ddmFormInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getFormInstanceRecordsCount(ddmFormInstanceId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void revertFormInstanceRecord(long ddmFormInstanceRecordId,
		java.lang.String version,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.revertFormInstanceRecord(ddmFormInstanceRecordId, version,
			serviceContext);
	}

	public static com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord updateFormInstanceRecord(
		long ddmFormInstanceRecordId, boolean majorVersion,
		com.liferay.dynamic.data.mapping.storage.DDMFormValues ddmFormValues,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateFormInstanceRecord(ddmFormInstanceRecordId,
			majorVersion, ddmFormValues, serviceContext);
	}

	public static DDMFormInstanceRecordService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDMFormInstanceRecordService, DDMFormInstanceRecordService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(DDMFormInstanceRecordService.class);

		ServiceTracker<DDMFormInstanceRecordService, DDMFormInstanceRecordService> serviceTracker =
			new ServiceTracker<DDMFormInstanceRecordService, DDMFormInstanceRecordService>(bundle.getBundleContext(),
				DDMFormInstanceRecordService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}