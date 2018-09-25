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

package com.liferay.site.navigation.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.service.SiteNavigationMenuItemLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class SiteNavigationMenuItemImpl extends SiteNavigationMenuItemBaseImpl {

	public SiteNavigationMenuItemImpl() {
	}

	/**
	 * Returns all parent site navigation menu items of the current site
	 * navigation menu item. The list is retrieved recursively with the direct
	 * parent site navigation menu item listed first, and most distant parent
	 * listed last.
	 *
	 * @return the current site navigation menu item's list of parent site
	 * navigation menu items
	 */
	@Override
	public List<SiteNavigationMenuItem> getAncestors() throws PortalException {
		List<SiteNavigationMenuItem> siteNavigationMenuItems =
			Collections.emptyList();

		SiteNavigationMenuItem siteNavigationMenuItem = this;

		while (!siteNavigationMenuItem.isRootSiteNavigationMenuItem()) {
			siteNavigationMenuItem =
				SiteNavigationMenuItemLocalServiceUtil.
					getSiteNavigationMenuItem(
						siteNavigationMenuItem.
							getParentSiteNavigationMenuItemId());

			if (siteNavigationMenuItems.isEmpty()) {
				siteNavigationMenuItems = new ArrayList<>();
			}

			siteNavigationMenuItems.add(siteNavigationMenuItem);
		}

		return siteNavigationMenuItems;
	}

	/**
	 * Returns <code>true</code> if the current site navigation menu item is
	 * the root site navigation menu item.
	 *
	 * @return <code>true</code> if the current site navigation menu item is
	 * the root site navigation menu item;
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean isRootSiteNavigationMenuItem() {
		if (getParentSiteNavigationMenuItemId() == 0) {
			return true;
		}

		return false;
	}

}