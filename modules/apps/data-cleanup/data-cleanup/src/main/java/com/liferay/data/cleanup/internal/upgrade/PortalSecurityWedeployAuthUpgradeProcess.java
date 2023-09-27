/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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