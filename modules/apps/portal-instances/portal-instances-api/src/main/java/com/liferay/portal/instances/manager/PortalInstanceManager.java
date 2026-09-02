/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.manager;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Luis Ortiz
 */
public interface PortalInstanceManager {

	public long addPortalInstance(
			boolean active, String defaultAdminEmailAddress,
			String defaultAdminFirstName, String defaultAdminLastName,
			String defaultAdminMiddleName, String defaultAdminPassword,
			String defaultAdminScreenName, int maxUsers, String mx,
			String siteInitializerKey, long userId, String virtualHostname,
			String webId)
		throws PortalException;

}