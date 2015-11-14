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

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Juergen Kappler
 */
@ConfigurationAdmin(category = "web-experience-management")
@Meta.OCD(
	id = "com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuWebConfiguration"
)
public interface SiteNavigationMenuWebConfiguration {

	@Meta.AD(deflt = "blank-navigation-menu-ftl", required = false)
	public String ddmTemplateKeyDefault();

	@Meta.AD(deflt = "dots", required = false)
	public String defaultBulletStyle();

	@Meta.AD(deflt = "relative-with-breadcrumb", required = false)
	public String defaultDisplayStyle();

	@Meta.AD(
		deflt = "relative-with-breadcrumb,from-level-2-with-title,from-level-1-with-title,from-level-1,from-level-1-to-all-sublevels,from-level-0",
		required = false
	)
	public String[] displayStyleOptions();

}