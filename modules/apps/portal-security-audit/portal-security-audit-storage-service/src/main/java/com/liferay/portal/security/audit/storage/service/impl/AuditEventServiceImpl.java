/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.audit.storage.model.AuditEvent;
import com.liferay.portal.security.audit.storage.service.base.AuditEventServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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
@CTAware
public class AuditEventServiceImpl extends AuditEventServiceBaseImpl {

	@Override
	public List<AuditEvent> getAuditEvents(long companyId, int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!(permissionChecker.isCompanyAdmin() ||
			  _userLocalService.hasRoleUser(
				  companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  permissionChecker.getUserId(), true))) {

			throw new PrincipalException();
		}

		return auditEventLocalService.getAuditEvents(companyId, start, end);
	}

	@Override
	public List<AuditEvent> getAuditEvents(
			long companyId, int start, int end,
			OrderByComparator<AuditEvent> orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!(permissionChecker.isCompanyAdmin() ||
			  _userLocalService.hasRoleUser(
				  companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  permissionChecker.getUserId(), true))) {

			throw new PrincipalException();
		}

		return auditEventLocalService.getAuditEvents(
			companyId, start, end, orderByComparator);
	}

	public List<AuditEvent> getAuditEvents(
			long companyId, long groupId, long userId, String userName,
			Date createDateGT, Date createDateLT, String eventType,
			String className, String classPK, String clientHost,
			String clientIP, String serverName, int serverPort,
			String sessionID, boolean andSearch, int start, int end)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!(permissionChecker.isCompanyAdmin() ||
			  _userLocalService.hasRoleUser(
				  companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  permissionChecker.getUserId(), true))) {

			throw new PrincipalException();
		}

		return auditEventLocalService.getAuditEvents(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch, start, end);
	}

	public List<AuditEvent> getAuditEvents(
			long companyId, long groupId, long userId, String userName,
			Date createDateGT, Date createDateLT, String eventType,
			String className, String classPK, String clientHost,
			String clientIP, String serverName, int serverPort,
			String sessionID, boolean andSearch, int start, int end,
			OrderByComparator<AuditEvent> orderByComparator)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (!(permissionChecker.isCompanyAdmin() ||
			  _userLocalService.hasRoleUser(
				  companyId, RoleConstants.ANALYTICS_ADMINISTRATOR,
				  permissionChecker.getUserId(), true))) {

			throw new PrincipalException();
		}

		return auditEventLocalService.getAuditEvents(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch, start, end, orderByComparator);
	}

	@Override
	public int getAuditEventsCount(long companyId) throws PortalException {
		return auditEventLocalService.getAuditEventsCount(companyId);
	}

	public int getAuditEventsCount(
			long companyId, long groupId, long userId, String userName,
			Date createDateGT, Date createDateLT, String eventType,
			String className, String classPK, String clientHost,
			String clientIP, String serverName, int serverPort,
			String sessionID, boolean andSearch)
		throws PortalException {

		return auditEventLocalService.getAuditEventsCount(
			companyId, groupId, userId, userName, createDateGT, createDateLT,
			eventType, className, classPK, clientHost, clientIP, serverName,
			serverPort, sessionID, andSearch);
	}

	@Reference
	private UserLocalService _userLocalService;

}