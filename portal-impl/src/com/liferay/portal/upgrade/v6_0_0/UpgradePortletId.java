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

package com.liferay.portal.upgrade.v6_0_0;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"7", "1_WAR_biblegatewayportlet"
			},
			new String[] {"21", "1_WAR_randombibleverseportlet"},
			new String[] {"46", "1_WAR_gospelforasiaportlet"},
			new String[] {"1_WAR_wolportlet", "1_WAR_socialnetworkingportlet"},
			new String[] {"2_WAR_wolportlet", "1_WAR_socialcodingportlet"},
			new String[] {"3_WAR_wolportlet", "2_WAR_socialcodingportlet"},
			new String[] {"4_WAR_wolportlet", "2_WAR_socialnetworkingportlet"},
			new String[] {"5_WAR_wolportlet", "3_WAR_socialnetworkingportlet"},
			new String[] {"6_WAR_wolportlet", "4_WAR_socialnetworkingportlet"},
			new String[] {"7_WAR_wolportlet", "5_WAR_socialnetworkingportlet"},
			new String[] {"8_WAR_wolportlet", "6_WAR_socialnetworkingportlet"},
			new String[] {"9_WAR_wolportlet", "7_WAR_socialnetworkingportlet"},
			new String[] {"10_WAR_wolportlet", "8_WAR_socialnetworkingportlet"}
		};
	}

}