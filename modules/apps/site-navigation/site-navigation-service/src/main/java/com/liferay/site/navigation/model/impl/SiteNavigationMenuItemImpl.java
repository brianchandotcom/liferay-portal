/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.model.impl;

import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteNavigationMenuItemImpl extends SiteNavigationMenuItemBaseImpl {

	@Override
	public List<SiteNavigationMenuItem> getAncestors() {
		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			new ArrayList<>();

		SiteNavigationMenuItem siteNavigationMenuItem = this;

		while (siteNavigationMenuItem.getParentSiteNavigationMenuItemId() !=
					0) {

			siteNavigationMenuItem =
				SiteNavigationMenuItemLocalServiceUtil.
					fetchSiteNavigationMenuItem(
						siteNavigationMenuItem.
							getParentSiteNavigationMenuItemId());

			siteNavigationMenuItems.add(siteNavigationMenuItem);
		}

		return siteNavigationMenuItems;
	}

}