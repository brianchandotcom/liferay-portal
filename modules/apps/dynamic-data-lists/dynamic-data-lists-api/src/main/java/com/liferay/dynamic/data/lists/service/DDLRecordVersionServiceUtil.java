/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.service;

import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DDLRecordVersion. This utility wraps
 * <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordVersionService
 * @generated
 */
public class DDLRecordVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.lists.service.impl.DDLRecordVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	 * Returns the record version matching the ID.
	 *
	 * @param recordVersionId the primary key of the record version
	 * @return the record version with the ID
	 * @throws PortalException if the matching record set could not be found or
	 if the user did not have the required permission to access the
	 record set
	 */
	public static DDLRecordVersion getRecordVersion(long recordVersionId)
		throws PortalException {

		return getService().getRecordVersion(recordVersionId);
	}

	/**
	 * Returns a record version matching the record and version.
	 *
	 * @param recordId the primary key of the record
	 * @param version the version of the record to return
	 * @return the record version macthing the record primary key and version
	 * @throws PortalException if the matching record set is not found or if the
	 user do not have the required permission to access the record set
	 */
	public static DDLRecordVersion getRecordVersion(
			long recordId, String version)
		throws PortalException {

		return getService().getRecordVersion(recordId, version);
	}

	/**
	 * Returns all the record versions matching the record.
	 *
	 * @param recordId the primary key of the record
	 * @return the matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	public static List<DDLRecordVersion> getRecordVersions(long recordId)
		throws PortalException {

		return getService().getRecordVersions(recordId);
	}

	/**
	 * Returns an ordered range of record versions matching the record.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end -
	 * start</code> instances. <code>start</code> and <code>end</code> are not
	 * primary keys, they are indexes in the result set. Thus, <code>0</code>
	 * refers to the first result in the set. Setting both <code>start</code>
	 * and <code>end</code> to <code>QueryUtil.ALL_POS</code> will return the
	 * full result set.
	 * </p>
	 *
	 * @param recordId the primary key of the record
	 * @param start the lower bound of the range of record versions to return
	 * @param end the upper bound of the range of record versions to return
	 (not inclusive)
	 * @param orderByComparator the comparator used to order the record
	 versions
	 * @return the range of matching record versions ordered by the comparator
	 * @throws PortalException if a portal exception occurred
	 */
	public static List<DDLRecordVersion> getRecordVersions(
			long recordId, int start, int end,
			OrderByComparator<DDLRecordVersion> orderByComparator)
		throws PortalException {

		return getService().getRecordVersions(
			recordId, start, end, orderByComparator);
	}

	/**
	 * Returns the number of record versions matching the record.
	 *
	 * @param recordId the primary key of the record
	 * @return the number of matching record versions
	 * @throws PortalException if a portal exception occurred
	 */
	public static int getRecordVersionsCount(long recordId)
		throws PortalException {

		return getService().getRecordVersionsCount(recordId);
	}

	public static DDLRecordVersionService getService() {
		return _service;
	}

	public static void setService(DDLRecordVersionService service) {
		_service = service;
	}

	private static volatile DDLRecordVersionService _service;

}