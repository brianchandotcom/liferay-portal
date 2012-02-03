/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Miguel Pastor
 */
public class UpgradeCommunityProperties extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		for (int i = 0; i < _OLD_PORTLET_PREFERENCES.length; i++) {
			updatePreferences(
				"PortletPreferences", _OLD_PORTLET_PREFERENCES[i],
				_NEW_PORTLET_PREFERENCES[i]);
		}

		for (int i = 0; i < _OLD_PORTAL_PREFERENCES.length; i++) {
			updatePreferences(
				"PortalPreferences", _OLD_PORTAL_PREFERENCES[i],
				_NEW_PORTAL_PREFERENCES[i]);
		}
	}

	protected void updatePreferences(
			String tableName, String oldValue, String newValue)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set preferences = replace(CAST_TEXT(preferences), '");
		sb.append(oldValue);
		sb.append("', '");
		sb.append(newValue);
		sb.append("') where preferences like '%");
		sb.append(oldValue);
		sb.append("%'");

		runSQL(sb.toString());
	}

	private static final String[] _NEW_PORTAL_PREFERENCES = {
		PropsKeys.COMPANY_SECURITY_SITE_LOGO,
		PropsKeys.SITES_EMAIL_FROM_ADDRESS, PropsKeys.SITES_EMAIL_FROM_NAME,
		PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_BODY,
		PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_SUBJECT,
		PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_BODY,
		PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT
	};

	private static final String[] _NEW_PORTLET_PREFERENCES = {
		"site-role", "[$SITE_NAME$]"
	};

	private static final String[] _OLD_PORTAL_PREFERENCES = {
		"company.security.community.logo", "communities.email.from.address",
		"communities.email.from.name",
		"communities.email.membership.reply.body",
		"communities.email.membership.reply.subject",
		"communities.email.membership.request.body",
		"communities.email.membership.request.subject",
	};

	private static final String[] _OLD_PORTLET_PREFERENCES = {
		"community-role", "[$COMMUNITY_NAME$]"
	};

}