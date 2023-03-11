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

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Eudaldo Alonso
 */
public class PortalSecurityWedeployAuthUpgradeProcess
	extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {"com.liferay.portal.security.wedeploy.auth.web"},
			null,
			new String[] {
				"com_liferay_portal_security_wedeploy_auth_web_internal_" +
					"portlet_WeDeployAuthPortlet",
				"com_liferay_portal_security_wedeploy_auth_web_internal_" +
					"portlet_WeDeployAuthAdminPortlet"
			});

		removeServiceData(
			"WeDeployAuth",
			new String[] {"com.liferay.portal.security.wedeploy.auth.service"},
			new String[] {
				"com.liferay.portal.security.wedeploy.auth.model." +
					"WeDeployAuthApp",
				"com.liferay.portal.security.wedeploy.auth.model." +
					"WeDeployAuthToken"
			},
			new String[] {
				"WeDeployAuth_WeDeployAuthApp", "WeDeployAuth_WeDeployAuthToken"
			});
	}

}