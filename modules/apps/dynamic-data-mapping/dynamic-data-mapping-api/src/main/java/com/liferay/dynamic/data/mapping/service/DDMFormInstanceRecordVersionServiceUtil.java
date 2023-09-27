/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DDMFormInstanceRecordVersion. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecordVersionService
 * @generated
 */
public class DDMFormInstanceRecordVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceRecordVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DDMFormInstanceRecordVersion
			fetchLatestFormInstanceRecordVersion(
				long userId, long formInstanceId, String formInstanceVersion,
				int status)
		throws PortalException {

		return getService().fetchLatestFormInstanceRecordVersion(
			userId, formInstanceId, formInstanceVersion, status);
	}

	public static DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			long ddmFormInstanceRecordVersionId)
		throws PortalException {

		return getService().getFormInstanceRecordVersion(
			ddmFormInstanceRecordVersionId);
	}

	public static DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			long ddmFormInstanceRecordId, String version)
		throws PortalException {

		return getService().getFormInstanceRecordVersion(
			ddmFormInstanceRecordId, version);
	}

	public static List<DDMFormInstanceRecordVersion>
			getFormInstanceRecordVersions(long ddmFormInstanceRecordId)
		throws PortalException {

		return getService().getFormInstanceRecordVersions(
			ddmFormInstanceRecordId);
	}

	public static List<DDMFormInstanceRecordVersion>
			getFormInstanceRecordVersions(
				long ddmFormInstanceRecordId, int start, int end,
				OrderByComparator<DDMFormInstanceRecordVersion>
					orderByComparator)
		throws PortalException {

		return getService().getFormInstanceRecordVersions(
			ddmFormInstanceRecordId, start, end, orderByComparator);
	}

	public static int getFormInstanceRecordVersionsCount(
			long ddmFormInstanceRecordId)
		throws PortalException {

		return getService().getFormInstanceRecordVersionsCount(
			ddmFormInstanceRecordId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DDMFormInstanceRecordVersionService getService() {
		return _service;
	}

	public static void setService(DDMFormInstanceRecordVersionService service) {
		_service = service;
	}

	private static volatile DDMFormInstanceRecordVersionService _service;

}