/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DDMFormInstanceRecord. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordService
 * @generated
 */
public class DDMFormInstanceRecordServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DDMFormInstanceRecord addFormInstanceRecord(
			long groupId, long ddmFormInstanceId,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addFormInstanceRecord(
			groupId, ddmFormInstanceId, ddmFormValues, serviceContext);
	}

	public static void deleteFormInstanceRecord(long ddmFormInstanceRecordId)
		throws PortalException {

		getService().deleteFormInstanceRecord(ddmFormInstanceRecordId);
	}

	public static DDMFormInstanceRecord getFormInstanceRecord(
			long ddmFormInstanceRecordId)
		throws PortalException {

		return getService().getFormInstanceRecord(ddmFormInstanceRecordId);
	}

	public static List<DDMFormInstanceRecord> getFormInstanceRecords(
			long ddmFormInstanceId)
		throws PortalException {

		return getService().getFormInstanceRecords(ddmFormInstanceId);
	}

	public static List<DDMFormInstanceRecord> getFormInstanceRecords(
			long ddmFormInstanceId, int status, int start, int end,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws PortalException {

		return getService().getFormInstanceRecords(
			ddmFormInstanceId, status, start, end, orderByComparator);
	}

	public static int getFormInstanceRecordsCount(long ddmFormInstanceId)
		throws PortalException {

		return getService().getFormInstanceRecordsCount(ddmFormInstanceId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void revertFormInstanceRecord(
			long ddmFormInstanceRecordId, String version,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().revertFormInstanceRecord(
			ddmFormInstanceRecordId, version, serviceContext);
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<DDMFormInstanceRecord> searchFormInstanceRecords(
				long ddmFormInstanceId, String[] notEmptyFields, int status,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws PortalException {

		return getService().searchFormInstanceRecords(
			ddmFormInstanceId, notEmptyFields, status, start, end, sort);
	}

	public static DDMFormInstanceRecord updateFormInstanceRecord(
			long ddmFormInstanceRecordId, boolean majorVersion,
			com.liferay.dynamic.data.mapping.storage.DDMFormValues
				ddmFormValues,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateFormInstanceRecord(
			ddmFormInstanceRecordId, majorVersion, ddmFormValues,
			serviceContext);
	}

	public static DDMFormInstanceRecordService getService() {
		return _service;
	}

	public static void setService(DDMFormInstanceRecordService service) {
		_service = service;
	}

	private static volatile DDMFormInstanceRecordService _service;

}