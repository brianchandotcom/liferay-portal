/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class AuditRouterUtil {

	public static AuditRouter getAuditRouter() {
		return _auditRouterSnapshot.get();
	}

	public static boolean isDeployed() {
		AuditRouter auditRouter = _auditRouterSnapshot.get();

		return auditRouter.isDeployed();
	}

	public static void route(AuditMessage auditMessage) throws AuditException {
		AuditRouter auditRouter = _auditRouterSnapshot.get();

		auditRouter.route(auditMessage);
	}

	private static final Snapshot<AuditRouter> _auditRouterSnapshot =
		new Snapshot<>(AuditRouterUtil.class, AuditRouter.class);

}