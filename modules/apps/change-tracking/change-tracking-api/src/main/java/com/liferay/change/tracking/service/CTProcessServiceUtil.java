/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.service;

import com.liferay.change.tracking.model.CTProcess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for CTProcess. This utility wraps
 * <code>com.liferay.change.tracking.service.impl.CTProcessServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CTProcessService
 * @generated
 */
public class CTProcessServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.change.tracking.service.impl.CTProcessServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<CTProcess> getCTProcesses(
			long companyId, long userId, String keywords, int status, int type,
			int start, int end, OrderByComparator<CTProcess> orderByComparator)
		throws PortalException {

		return getService().getCTProcesses(
			companyId, userId, keywords, status, type, start, end,
			orderByComparator);
	}

	public static List<CTProcess> getCTProcesses(
			long companyId, long userId, String keywords, int status, int start,
			int end, OrderByComparator<CTProcess> orderByComparator)
		throws PortalException {

		return getService().getCTProcesses(
			companyId, userId, keywords, status, start, end, orderByComparator);
	}

	public static int getCTProcessesCount(
		long companyId, long userId, String keywords, int status) {

		return getService().getCTProcessesCount(
			companyId, userId, keywords, status);
	}

	public static int getCTProcessesCount(
		long companyId, long userId, String keywords, int status, int type) {

		return getService().getCTProcessesCount(
			companyId, userId, keywords, status, type);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CTProcessService getService() {
		return _service;
	}

	public static void setService(CTProcessService service) {
		_service = service;
	}

	private static volatile CTProcessService _service;

}