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

package com.liferay.site.navigation.breadcrumb.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Juergen Kappler
 */
@Meta.OCD(
	id = "com.liferay.site.navigation.breadcrumb.web.configuration.BreadcrumbPortletInstanceConfiguration"
)
public interface BreadcrumbPortletInstanceConfiguration {

	/**
	 * Set a DDM template ID that starts with the prefix "ddmTemplate_" (i.e.
	 * ddmTemplate_BREADCRUMB-HORIZONTAL-FTL) to use as the display
	 * style.
	 */
	@Meta.AD(deflt = "", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "0", required = false)
	public long displayStyleGroupId();

	@Meta.AD(deflt = "true", required = false)
	public boolean showCurrentGroup();

	@Meta.AD(deflt = "false", required = false)
	public boolean showGuestGroup();

	@Meta.AD(deflt = "true", required = false)
	public boolean showLayout();

	@Meta.AD(deflt = "true", required = false)
	public boolean showParentGroups();

	@Meta.AD(deflt = "true", required = false)
	public boolean showPortletBreadcrumb();

}