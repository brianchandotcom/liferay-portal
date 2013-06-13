/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AuditRouterUtil {

	public static AuditRouter getAuditRouter() {
		PortalRuntimePermission.checkGetBeanProperty(AuditRouterUtil.class);

		return _auditRouter;
	}

	public static boolean isDeployed() {
		return getAuditRouter().isDeployed();
	}

	public static void route(AuditMessage auditMessage) throws AuditException {
		getAuditRouter().route(auditMessage);
	}

	public void setAuditRouter(AuditRouter auditRouter) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_auditRouter = auditRouter;
	}

	private static AuditRouter _auditRouter;

}