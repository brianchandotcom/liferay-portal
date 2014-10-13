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

package com.liferay.portal.upgrade.v6_0_12_to_6_1_0;

/**
 * @author Alexander Chow
 * @author Connor McKay
 */
public class UpgradePermission
	extends com.liferay.portal.upgrade.v6_1_0.UpgradePermission {

	@Override
	protected void doUpgrade() throws Exception {
		convertResourcePermissions(
			"com.liferay.portlet.bookmarks.model.BookmarksEntry",
			"BookmarksEntry", "entryId");
		convertResourcePermissions(
			"com.liferay.portlet.bookmarks.model.BookmarksFolder",
			"BookmarksFolder", "folderId");
	}

}