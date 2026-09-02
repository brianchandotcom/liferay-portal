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
			long userId, String webId, String virtualHostname, String mx,
			int maxUsers, boolean active, String defaultAdminPassword,
			String defaultAdminScreenName, String defaultAdminEmailAddress,
			String defaultAdminFirstName, String defaultAdminMiddleName,
			String defaultAdminLastName, String siteInitializerKey)
		throws PortalException;

}