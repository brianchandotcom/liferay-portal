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