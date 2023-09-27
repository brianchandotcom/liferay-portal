/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.audit.storage.model.AuditEvent;

import java.util.List;

/**
 * Provides the remote service utility for AuditEvent. This utility wraps
 * <code>com.liferay.portal.security.audit.storage.service.impl.AuditEventServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventService
 * @generated
 */
public class AuditEventServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.security.audit.storage.service.impl.AuditEventServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<AuditEvent> getAuditEvents(
			long companyId, int start, int end)
		throws PortalException {

		return getService().getAuditEvents(companyId, start, end);
	}

	public static List<AuditEvent> getAuditEvents(
			long companyId, int start, int end,
			OrderByComparator<AuditEvent> orderByComparator)
		throws PortalException {

		return getService().getAuditEvents(
			companyId, start, end, orderByComparator);
	}

	public static List<AuditEvent> getAuditEvents(
			long companyId, long groupId, long userId, String userName,
			java.util.Date createDateGT, java.util.Date createDateLT,
			String eventType, String className, String classPK,
			String clientHost, String clientIP, String serverName,
			int serverPort, String sessionID, boolean andSearch, int start,
			int end)
		throws PortalException {

		return getService().getAuditEvents(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch, start, end);
	}

	public static List<AuditEvent> getAuditEvents(
			long companyId, long groupId, long userId, String userName,
			java.util.Date createDateGT, java.util.Date createDateLT,
			String eventType, String className, String classPK,
			String clientHost, String clientIP, String serverName,
			int serverPort, String sessionID, boolean andSearch, int start,
			int end, OrderByComparator<AuditEvent> orderByComparator)
		throws PortalException {

		return getService().getAuditEvents(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch, start, end, orderByComparator);
	}

	public static int getAuditEventsCount(long companyId)
		throws PortalException {

		return getService().getAuditEventsCount(companyId);
	}

	public static int getAuditEventsCount(
			long companyId, long groupId, long userId, String userName,
			java.util.Date createDateGT, java.util.Date createDateLT,
			String eventType, String className, String classPK,
			String clientHost, String clientIP, String serverName,
			int serverPort, String sessionID, boolean andSearch)
		throws PortalException {

		return getService().getAuditEventsCount(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AuditEventService getService() {
		return _service;
	}

	public static void setService(AuditEventService service) {
		_service = service;
	}

	private static volatile AuditEventService _service;

}