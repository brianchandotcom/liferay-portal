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

package com.liferay.portal.upgrade.v7_0_1;

/**
 * @author Roberto Díaz
 */
public class UpgradeModules
	extends com.liferay.portal.upgrade.util.UpgradeModules {

	@Override
	public String[] getBundleSymbolicNames() {
		return _bundleSymbolicNames;
	}

	@Override
	public String[][] getConvertedLegacyModules() {
		return _convertedLegacyModules;
	}

	private static final String[] _bundleSymbolicNames = {
		"com.liferay.announcements.web", "com.liferay.contacts.web",
		"com.liferay.directory.web",
		"com.liferay.invitation.invite.members.web",
		"com.liferay.microblogs.web", "com.liferay.recent.documents.web",
		"com.liferay.social.networking.web",
		"com.liferay.social.privatemessaging.web"
	};
	private static final String[][] _convertedLegacyModules = {
		{
			"notifications-portlet", "com.liferay.notifications.web",
			"Notification"
		}
	};

}