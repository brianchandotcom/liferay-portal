/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Alejandro Tardín
 */
public class TwitterUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		removePortletData(
			new String[] {"com.liferay.twitter.web"},
			new String[] {"1_WAR_twitterportlet"},
			new String[] {"com_liferay_twitter_web_portlet_TwitterPortlet"});

		removeServiceData(
			"Twitter", new String[] {"com.liferay.twitter.service"},
			new String[] {"com.liferay.twitter.model.Feed"},
			new String[] {"Twitter_Feed"});
	}

}