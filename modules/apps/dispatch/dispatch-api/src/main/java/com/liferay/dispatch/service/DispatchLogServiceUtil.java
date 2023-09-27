/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.service;

import com.liferay.dispatch.model.DispatchLog;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for DispatchLog. This utility wraps
 * <code>com.liferay.dispatch.service.impl.DispatchLogServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Matija Petanjek
 * @see DispatchLogService
 * @generated
 */
public class DispatchLogServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.dispatch.service.impl.DispatchLogServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteDispatchLog(long dispatchLogId)
		throws PortalException {

		getService().deleteDispatchLog(dispatchLogId);
	}

	public static DispatchLog getDispatchLog(long dispatchLogId)
		throws PortalException {

		return getService().getDispatchLog(dispatchLogId);
	}

	public static List<DispatchLog> getDispatchLogs(
			long dispatchTriggerId, int start, int end)
		throws PortalException {

		return getService().getDispatchLogs(dispatchTriggerId, start, end);
	}

	public static List<DispatchLog> getDispatchLogs(
			long dispatchTriggerId, int start, int end,
			OrderByComparator<DispatchLog> orderByComparator)
		throws PortalException {

		return getService().getDispatchLogs(
			dispatchTriggerId, start, end, orderByComparator);
	}

	public static int getDispatchLogsCount(long dispatchTriggerId)
		throws PortalException {

		return getService().getDispatchLogsCount(dispatchTriggerId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static DispatchLogService getService() {
		return _service;
	}

	public static void setService(DispatchLogService service) {
		_service = service;
	}

	private static volatile DispatchLogService _service;

}