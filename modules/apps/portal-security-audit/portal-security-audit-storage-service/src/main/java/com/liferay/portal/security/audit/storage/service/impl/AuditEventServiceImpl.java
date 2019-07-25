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

package com.liferay.portal.security.audit.storage.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.audit.storage.model.AuditEvent;
import com.liferay.portal.security.audit.storage.service.base.AuditEventServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=audit",
		"json.web.service.context.path=AuditEvent"
	},
	service = AopService.class
)
public class AuditEventServiceImpl extends AuditEventServiceBaseImpl {

	@Override
	public List<AuditEvent> getAuditEvents(long companyId, int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!(permissionChecker.isCompanyAdmin() ||
			  userLocalService.hasRoleUser(
				  companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  permissionChecker.getUserId(), true))) {

			throw new PrincipalException();
		}

		return auditEventLocalService.getAuditEvents(companyId, start, end);
	}

	@Override
	public List<AuditEvent> getAuditEvents(
			long companyId, int start, int end,
			OrderByComparator orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!(permissionChecker.isCompanyAdmin() ||
			  userLocalService.hasRoleUser(
				  companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  permissionChecker.getUserId(), true))) {

			throw new PrincipalException();
		}

		return auditEventLocalService.getAuditEvents(
			companyId, start, end, orderByComparator);
	}

	@Override
	public List<AuditEvent> getAuditEvents(
			long companyId, long userId, String userName, Date createDateGT,
			Date createDateLT, String eventType, String className,
			String classPK, String clientHost, String clientIP,
			String serverName, int serverPort, String sessionID,
			boolean andSearch, int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!(permissionChecker.isCompanyAdmin() ||
			  userLocalService.hasRoleUser(
				  companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  permissionChecker.getUserId(), true))) {

			throw new PrincipalException();
		}

		return auditEventLocalService.getAuditEvents(
			companyId, userId, userName, createDateGT, createDateLT, eventType,
			className, classPK, clientHost, clientIP, serverName, serverPort,
			sessionID, andSearch, start, end);
	}

	@Override
	public List<AuditEvent> getAuditEvents(
			long companyId, long userId, String userName, Date createDateGT,
			Date createDateLT, String eventType, String className,
			String classPK, String clientHost, String clientIP,
			String serverName, int serverPort, String sessionID,
			boolean andSearch, int start, int end,
			OrderByComparator orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!(permissionChecker.isCompanyAdmin() ||
			  userLocalService.hasRoleUser(
				  companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  permissionChecker.getUserId(), true))) {

			throw new PrincipalException();
		}

		return auditEventLocalService.getAuditEvents(
			companyId, userId, userName, createDateGT, createDateLT, eventType,
			className, classPK, clientHost, clientIP, serverName, serverPort,
			sessionID, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getAuditEventsCount(long companyId) throws PortalException {
		return auditEventLocalService.getAuditEventsCount(companyId);
	}

	@Override
	public int getAuditEventsCount(
			long companyId, long userId, String userName, Date createDateGT,
			Date createDateLT, String eventType, String className,
			String classPK, String clientHost, String clientIP,
			String serverName, int serverPort, String sessionID,
			boolean andSearch)
		throws PortalException {

		return auditEventLocalService.getAuditEventsCount(
			companyId, userId, userName, createDateGT, createDateLT, eventType,
			className, classPK, clientHost, clientIP, serverName, serverPort,
			sessionID, andSearch);
	}

}