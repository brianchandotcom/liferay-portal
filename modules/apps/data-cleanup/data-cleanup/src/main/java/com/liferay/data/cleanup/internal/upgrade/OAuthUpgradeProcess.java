/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Eudaldo Alonso
 */
public class OAuthUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {"com.liferay.oauth.web"},
			new String[] {
				"1_WAR_oauthportlet", "2_WAR_oauthportlet", "3_WAR_oauthportlet"
			},
			new String[] {
				"com_liferay_oauth_web_internal_portlet_AdminPortlet",
				"com_liferay_oauth_web_internal_portlet_AuthorizationsPortlet",
				"com_liferay_oauth_web_internal_portlet_AuthorizePortlet"
			});

		removeServiceData(
			"OAuth", new String[] {"com.liferay.oauth.service"},
			new String[] {
				"com.liferay.oauth.model.OAuthApplication",
				"com.liferay.oauth.model.OAuthUser"
			},
			new String[] {"OAuth_OAuthApplication", "OAuth_OAuthUser"});
	}

}