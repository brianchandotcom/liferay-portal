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

import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.audit.AuditEvent;
import com.liferay.portal.security.audit.AuditEventManager;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@Component(immediate = true)
public class AuditEventManagerUtil {

	public static AuditEvent addAuditEvent(AuditMessage auditMessage) {
		return _auditEventManager.addAuditEvent(auditMessage);
	}

	public static AuditEvent fetchAuditEvent(long auditEventId) {
		return _auditEventManager.fetchAuditEvent(auditEventId);
	}

	public static List<AuditEvent> getAuditEvents(
		long companyId, int start, int end,
		OrderByComparator orderByComparator) {

		return _auditEventManager.getAuditEvents(
			companyId, start, end, orderByComparator);
	}

	public static List<AuditEvent> getAuditEvents(
		long companyId, long userId, String userName, Date createDateGT,
		Date createDateLT, String eventType, String className, String classPK,
		String clientHost, String clientIP, String serverName, int serverPort,
		String sessionID, boolean andSearch, int start, int end,
		OrderByComparator orderByComparator) {

		return _auditEventManager.getAuditEvents(
			companyId, userId, userName, createDateGT, createDateLT, eventType,
			className, classPK, clientHost, clientIP, serverName, serverPort,
			sessionID, andSearch, start, end, orderByComparator);
	}

	public static int getAuditEventsCount(long companyId) {
		return _auditEventManager.getAuditEventsCount(companyId);
	}

	public static int getAuditEventsCount(
		long companyId, long userId, String userName, Date createDateGT,
		Date createDateLT, String eventType, String className, String classPK,
		String clientHost, String clientIP, String serverName, int serverPort,
		String sessionID, boolean andSearch) {

		return _auditEventManager.getAuditEventsCount(
			companyId, userId, userName, createDateGT, createDateLT, eventType,
			className, classPK, clientHost, clientIP, serverName, serverPort,
			sessionID, andSearch);
	}

	@Reference(unbind = "-")
	protected void set_auditEventManager(AuditEventManager auditEventManager) {
		_auditEventManager = auditEventManager;
	}

	private static AuditEventManager _auditEventManager;

}