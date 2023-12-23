/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.service;

import com.liferay.dynamic.data.mapping.model.DDMStructureLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DDMStructureLink. This utility wraps
 * <code>com.liferay.dynamic.data.mapping.service.impl.DDMStructureLinkServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLinkService
 * @generated
 */
public class DDMStructureLinkServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dynamic.data.mapping.service.impl.DDMStructureLinkServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static List<com.liferay.dynamic.data.mapping.model.DDMStructure>
			getStructureLinkStructures(
				long classNameId, long classPK, long[] groupIds,
				String keywords, String resourceClassName, int start, int end,
				OrderByComparator<DDMStructureLink> orderByComparator)
		throws PortalException {

		return getService().getStructureLinkStructures(
			classNameId, classPK, groupIds, keywords, resourceClassName, start,
			end, orderByComparator);
	}

	public static int getStructureLinkStructuresCount(
		long classNameId, long classPK, long[] groupIds, String keywords,
		String resourceClassName) {

		return getService().getStructureLinkStructuresCount(
			classNameId, classPK, groupIds, keywords, resourceClassName);
	}

	public static DDMStructureLinkService getService() {
		return _service;
	}

	public static void setService(DDMStructureLinkService service) {
		_service = service;
	}

	private static volatile DDMStructureLinkService _service;

}