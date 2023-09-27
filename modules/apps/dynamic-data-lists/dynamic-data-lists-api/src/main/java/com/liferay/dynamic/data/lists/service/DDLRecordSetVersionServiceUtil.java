/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.service;

import com.liferay.dynamic.data.lists.model.DDLRecordSetVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DDLRecordSetVersion. This utility wraps
 * <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordSetVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersionService
 * @generated
 */
public class DDLRecordSetVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordSetVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DDLRecordSetVersion getLatestRecordSetVersion(
			long recordSetId)
		throws PortalException {

		return getService().getLatestRecordSetVersion(recordSetId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DDLRecordSetVersion getRecordSetVersion(
			long recordSetVersionId)
		throws PortalException {

		return getService().getRecordSetVersion(recordSetVersionId);
	}

	public static List<DDLRecordSetVersion> getRecordSetVersions(
			long recordSetId, int start, int end,
			OrderByComparator<DDLRecordSetVersion> orderByComparator)
		throws PortalException {

		return getService().getRecordSetVersions(
			recordSetId, start, end, orderByComparator);
	}

	public static int getRecordSetVersionsCount(long recordSetId)
		throws PortalException {

		return getService().getRecordSetVersionsCount(recordSetId);
	}

	public static DDLRecordSetVersionService getService() {
		return _service;
	}

	public static void setService(DDLRecordSetVersionService service) {
		_service = service;
	}

	private static volatile DDLRecordSetVersionService _service;

}