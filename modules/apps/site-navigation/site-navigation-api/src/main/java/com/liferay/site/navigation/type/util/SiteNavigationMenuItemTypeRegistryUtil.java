/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.type.util;

import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.site.navigation.model.SiteNavigationMenuItem;
import com.liferay.site.navigation.type.SiteNavigationMenuItemType;
import com.liferay.site.navigation.type.SiteNavigationMenuItemTypeRegistry;

import java.util.List;

/**
 * @author Lourdes Fernández Besada
 */
public class SiteNavigationMenuItemTypeRegistryUtil {

	public static SiteNavigationMenuItemType getSiteNavigationMenuItemType(
		SiteNavigationMenuItem siteNavigationMenuItem) {

		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry =
			_siteNavigationMenuItemTypeRegistrySnapshot.get();

		return siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
			siteNavigationMenuItem);
	}

	public static SiteNavigationMenuItemType getSiteNavigationMenuItemType(
		String type) {

		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry =
			_siteNavigationMenuItemTypeRegistrySnapshot.get();

		return siteNavigationMenuItemTypeRegistry.getSiteNavigationMenuItemType(
			type);
	}

	public static List<SiteNavigationMenuItemType>
		getSiteNavigationMenuItemTypes() {

		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry =
			_siteNavigationMenuItemTypeRegistrySnapshot.get();

		return siteNavigationMenuItemTypeRegistry.
			getSiteNavigationMenuItemTypes();
	}

	public static String[] getTypes() {
		SiteNavigationMenuItemTypeRegistry siteNavigationMenuItemTypeRegistry =
			_siteNavigationMenuItemTypeRegistrySnapshot.get();

		return siteNavigationMenuItemTypeRegistry.getTypes();
	}

	private static final Snapshot<SiteNavigationMenuItemTypeRegistry>
		_siteNavigationMenuItemTypeRegistrySnapshot = new Snapshot<>(
			SiteNavigationMenuItemTypeRegistryUtil.class,
			SiteNavigationMenuItemTypeRegistry.class);

}