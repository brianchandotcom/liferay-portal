/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.menu.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "navigation",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.site.navigation.menu.web.internal.configuration.SiteNavigationMenuPortletInstanceConfiguration",
	localization = "content/Language",
	name = "site-navigation-menu-portlet-instance-configuration-name"
)
public interface SiteNavigationMenuPortletInstanceConfiguration {

	@Meta.AD(name = "site-navigation-menu-id", required = false)
	public long siteNavigationMenuId();

	@Meta.AD(name = "site-navigation-menu-name", required = false)
	public String siteNavigationMenuName();

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "absolute", name = "root-menu-item-type", required = false)
	public String rootMenuItemType();

	@Meta.AD(deflt = "0", name = "root-menu-item-level", required = false)
	public int rootMenuItemLevel();

	@Meta.AD(name = "root-menu-item-id", required = false)
	public String rootMenuItemId();

	@Meta.AD(deflt = "-1", name = "site-navigation-menu-type", required = false)
	public int siteNavigationMenuType();

	@Meta.AD(deflt = "0", name = "display-depth", required = false)
	public int displayDepth();

	@Meta.AD(deflt = "auto", name = "expand-sublevels", required = false)
	public String expandedLevels();

	@Meta.AD(deflt = "preview", name = "preview", required = false)
	public boolean preview();

}