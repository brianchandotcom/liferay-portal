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

package com.liferay.layout.admin.web.upgrade.v_1_0_0;

import com.liferay.layout.admin.web.constants.LayoutAdminPortletKeys;

/**
 * @author Jürgen Kappler
 */
public class UpgradePortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {
				"88", LayoutAdminPortletKeys.LAYOUT_ADMIN
			},
			new String[] {"140", LayoutAdminPortletKeys.MY_PAGES},
			new String[] {"156", LayoutAdminPortletKeys.GROUP_PAGES}
		};
	}

}