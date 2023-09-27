/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DDMStructureVersion. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionService
 * @generated
 */
public class DDMStructureVersionServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMStructureVersionServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static DDMStructureVersion getLatestStructureVersion(
			long structureId)
		throws PortalException {

		return getService().getLatestStructureVersion(structureId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DDMStructureVersion getStructureVersion(
			long structureVersionId)
		throws PortalException {

		return getService().getStructureVersion(structureVersionId);
	}

	public static List<DDMStructureVersion> getStructureVersions(
			long structureId, int start, int end,
			OrderByComparator<DDMStructureVersion> orderByComparator)
		throws PortalException {

		return getService().getStructureVersions(
			structureId, start, end, orderByComparator);
	}

	public static int getStructureVersionsCount(long structureId)
		throws PortalException {

		return getService().getStructureVersionsCount(structureId);
	}

	public static DDMStructureVersionService getService() {
		return _service;
	}

	public static void setService(DDMStructureVersionService service) {
		_service = service;
	}

	private static volatile DDMStructureVersionService _service;

}