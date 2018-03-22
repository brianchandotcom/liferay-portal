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

package com.liferay.site.navigation.menu.web.configuration;

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
	id = "com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuPortletInstanceConfiguration",
	localization = "content/Language",
	name = "site-navigation-menu-portlet-instance-configuration-name"
)
public interface SiteNavigationMenuPortletInstanceConfiguration {

	@Meta.AD(name = "site-navigation-menu-id", required = false)
	public long siteNavigationMenuId();

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "absolute", name = "root-menu-item-type", required = false)
	public String rootMenuItemType();

	/**
	 * @deprecated As of 2.0.0, replaced by {@link #rootMenuItemType()}
	 */
	@Deprecated
	@Meta.AD(name = "root-layout-type", required = false)
	public String rootLayoutType();

	@Meta.AD(deflt = "0", name = "root-menu-item-level", required = false)
	public int rootMenuItemLevel();

	/**
	 * @deprecated As of 2.0.0, replaced by {@link #rootMenuItemLevel()}
	 */
	@Deprecated
	@Meta.AD(deflt = "0", name = "root-layout-level", required = false)
	public int rootLayoutLevel();

	@Meta.AD(name = "root-menu-item-id", required = false)
	public String rootMenuItemId();

	/**
	 * @deprecated As of 2.0.0, with no direct replacement
	 */
	@Deprecated
	@Meta.AD(name = "root-layout-uuid", required = false)
	public String rootLayoutUuid();

	@Meta.AD(deflt = "0", name = "site-navigation-menu-type", required = false)
	public int siteNavigationMenuType();

	@Meta.AD(deflt = "0", name = "display-depth", required = false)
	public int displayDepth();

	@Meta.AD(deflt = "auto", name = "expand-sublevels", required = false)
	public String expandedLevels();

	/**
	 * @deprecated As of 2.0.0, replaced by {@link #expandedLevels()}
	 */
	@Deprecated
	@Meta.AD(deflt = "current", name = "included-layouts", required = false)
	public String includedLayouts();

	@Meta.AD(deflt = "preview", name = "preview", required = false)
	public boolean preview();

}