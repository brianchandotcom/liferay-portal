/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.web.internal;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.audit.AuditEvent;
import com.liferay.portal.security.audit.AuditEventManager;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
public class AuditEventManagerUtil {

	public static AuditEvent addAuditEvent(AuditMessage auditMessage) {
		AuditEventManager auditEventManager = _auditEventManagerSnapshot.get();

		return auditEventManager.addAuditEvent(auditMessage);
	}

	public static AuditEvent fetchAuditEvent(long auditEventId) {
		AuditEventManager auditEventManager = _auditEventManagerSnapshot.get();

		return auditEventManager.fetchAuditEvent(auditEventId);
	}

	public static List<AuditEvent> getAuditEvents(
		long companyId, int start, int end,
		OrderByComparator
			<com.liferay.portal.security.audit.storage.model.AuditEvent>
				orderByComparator) {

		AuditEventManager auditEventManager = _auditEventManagerSnapshot.get();

		return auditEventManager.getAuditEvents(
			companyId, start, end, orderByComparator);
	}

	public static List<AuditEvent> getAuditEvents(
		long companyId, long groupId, long userId, String userName,
		Date createDateGT, Date createDateLT, String eventType,
		String className, String classPK, String clientHost, String clientIP,
		String serverName, int serverPort, String sessionID, boolean andSearch,
		int start, int end,
		OrderByComparator
			<com.liferay.portal.security.audit.storage.model.AuditEvent>
				orderByComparator) {

		AuditEventManager auditEventManager = _auditEventManagerSnapshot.get();

		return auditEventManager.getAuditEvents(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch, start, end, orderByComparator);
	}

	public static int getAuditEventsCount(long companyId) {
		AuditEventManager auditEventManager = _auditEventManagerSnapshot.get();

		return auditEventManager.getAuditEventsCount(companyId);
	}

	public static int getAuditEventsCount(
		long companyId, long groupId, long userId, String userName,
		Date createDateGT, Date createDateLT, String eventType,
		String className, String classPK, String clientHost, String clientIP,
		String serverName, int serverPort, String sessionID,
		boolean andSearch) {

		AuditEventManager auditEventManager = _auditEventManagerSnapshot.get();

		return auditEventManager.getAuditEventsCount(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch);
	}

	private static final Snapshot<AuditEventManager>
		_auditEventManagerSnapshot = new Snapshot<>(
			AuditEventManagerUtil.class, AuditEventManager.class);

}