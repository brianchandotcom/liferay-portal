/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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