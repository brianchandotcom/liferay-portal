/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DDMFormInstanceVersion. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersionService
 * @generated
 */
public class DDMFormInstanceVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMFormInstanceVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DDMFormInstanceVersion getFormInstanceVersion(
			long ddmFormInstanceVersionId)
		throws PortalException {

		return getService().getFormInstanceVersion(ddmFormInstanceVersionId);
	}

	public static List<DDMFormInstanceVersion> getFormInstanceVersions(
			long ddmFormInstanceId, int start, int end,
			OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws PortalException {

		return getService().getFormInstanceVersions(
			ddmFormInstanceId, start, end, orderByComparator);
	}

	public static int getFormInstanceVersionsCount(long ddmFormInstanceId)
		throws PortalException {

		return getService().getFormInstanceVersionsCount(ddmFormInstanceId);
	}

	public static DDMFormInstanceVersion getLatestFormInstanceVersion(
			long ddmFormInstanceId)
		throws PortalException {

		return getService().getLatestFormInstanceVersion(ddmFormInstanceId);
	}

	public static DDMFormInstanceVersion getLatestFormInstanceVersion(
			long ddmFormInstanceId, int status)
		throws PortalException {

		return getService().getLatestFormInstanceVersion(
			ddmFormInstanceId, status);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DDMFormInstanceVersionService getService() {
		return _service;
	}

	public static void setService(DDMFormInstanceVersionService service) {
		_service = service;
	}

	private static volatile DDMFormInstanceVersionService _service;

}