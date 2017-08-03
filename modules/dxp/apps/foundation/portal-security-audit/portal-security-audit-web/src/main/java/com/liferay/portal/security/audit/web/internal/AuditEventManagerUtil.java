/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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