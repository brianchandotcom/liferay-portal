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

package com.liferay.portal.upgrade.v7_2_x;

import com.liferay.portal.kernel.upgrade.BaseUpgradeThemeId;

/**
 * @author Antonio Ortega
 */
public class UpgradeThemeId extends BaseUpgradeThemeId {

	protected String[][] getThemeIds() {
		return new String[][] {
			{
				"userdashboard_WAR_userdashboardtheme",
				"classic_WAR_classictheme"
			},
			{"userprofile_WAR_userprofiletheme", "classic_WAR_classictheme"}
		};
	}

}