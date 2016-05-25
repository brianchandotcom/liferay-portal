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
	extends com.liferay.portal.upgrade.v7_0_0.UpgradeModules {

	@Override
	public String[] getBundleSymbolicNames() {
		return _BUNDLE_SYMBOLIC_NAMES;
	}

	@Override
	public String[][] getConvertedLegacyModules() {
		return _CONVERTED_LEGACY_MODULES;
	}

	private static final String[] _BUNDLE_SYMBOLIC_NAMES = {};

	private static final String[][] _CONVERTED_LEGACY_MODULES = {
		{
			"com.liferay.announcements.web", "com.liferay.announcements.web",
			"Announcements"
		},
		{"com.liferay.contacts.web", "com.liferay.contacts.web", "Contacts"},
		{"com.liferay.directory.web", "com.liferay.directory.web", "Directory"},
		{
			"com.liferay.invitation.invite.members.web",
			"com.liferay.invitation.invite.members.web", "InviteMembers"
		},
		{
			"com.liferay.microblogs.web", "com.liferay.microblogs.web",
			"Microblogs"
		},
		{
			"com.liferay.recent.documents.web",
			"com.liferay.recent.documents.web", "RecentDocuments"
		},
		{
			"com.liferay.social.networking.web",
			"com.liferay.social.networking.web", "SN"
		},
		{
			"com.liferay.social.privatemessaging.web",
			"com.liferay.social.privatemessaging.web", "PM"
		},
		{"knowledge-base-portlet", "com.liferay.knowledge.base.service", "KB"},
		{
			"notifications-portlet", "com.liferay.notifications.web",
			"Notification"
		},
		{"sync-web", "com.liferay.sync.service", "Sync"}
	};

}