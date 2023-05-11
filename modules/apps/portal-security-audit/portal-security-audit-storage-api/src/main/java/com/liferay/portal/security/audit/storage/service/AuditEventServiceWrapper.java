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

package com.liferay.portal.security.audit.storage.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AuditEventService}.
 *
 * @author Brian Wing Shun Chan
 * @see AuditEventService
 * @generated
 */
public class AuditEventServiceWrapper
	implements AuditEventService, ServiceWrapper<AuditEventService> {

	public AuditEventServiceWrapper() {
		this(null);
	}

	public AuditEventServiceWrapper(AuditEventService auditEventService) {
		_auditEventService = auditEventService;
	}

	@Override
	public java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
				getAuditEvents(long companyId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventService.getAuditEvents(companyId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
				getAuditEvents(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.security.audit.storage.model.
							AuditEvent> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventService.getAuditEvents(
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
				getAuditEvents(
					long companyId, long groupId, long userId, String userName,
					java.util.Date createDateGT, java.util.Date createDateLT,
					String eventType, String className, String classPK,
					String clientHost, String clientIP, String serverName,
					int serverPort, String sessionID, boolean andSearch,
					int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventService.getAuditEvents(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.security.audit.storage.model.AuditEvent>
				getAuditEvents(
					long companyId, long groupId, long userId, String userName,
					java.util.Date createDateGT, java.util.Date createDateLT,
					String eventType, String className, String classPK,
					String clientHost, String clientIP, String serverName,
					int serverPort, String sessionID, boolean andSearch,
					int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.portal.security.audit.storage.model.
							AuditEvent> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventService.getAuditEvents(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getAuditEventsCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventService.getAuditEventsCount(companyId);
	}

	@Override
	public int getAuditEventsCount(
			long companyId, long groupId, long userId, String userName,
			java.util.Date createDateGT, java.util.Date createDateLT,
			String eventType, String className, String classPK,
			String clientHost, String clientIP, String serverName,
			int serverPort, String sessionID, boolean andSearch)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _auditEventService.getAuditEventsCount(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _auditEventService.getOSGiServiceIdentifier();
	}

	@Override
	public AuditEventService getWrappedService() {
		return _auditEventService;
	}

	@Override
	public void setWrappedService(AuditEventService auditEventService) {
		_auditEventService = auditEventService;
	}

	private AuditEventService _auditEventService;

}