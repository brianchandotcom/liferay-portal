/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AuditRouterUtil {

	public static AuditRouter getAuditRouter() {
		return _auditRouter;
	}

	public static boolean isDeployed() {
		return _auditRouter.isDeployed();
	}

	public static void route(AuditMessage auditMessage) throws AuditException {
		_auditRouter.route(auditMessage);
	}

	private static volatile AuditRouter _auditRouter =
		ServiceProxyFactory.newServiceTrackedInstance(
			AuditRouter.class, AuditRouterUtil.class, "_auditRouter", false);

}