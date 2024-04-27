/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.module.framework;

/**
 * @author Miguel Ángel Pastor Olivar
 */
public interface ModuleServiceLifecycle {

	public String LICENSE_INSTALL =
		"(module.service.lifecycle=license.install)";

	public String PORTAL_INITIALIZED =
		"(module.service.lifecycle=portal.initialized)";

	public String PORTLETS_INITIALIZED =
		"(module.service.lifecycle=portlets.initialized)";

}