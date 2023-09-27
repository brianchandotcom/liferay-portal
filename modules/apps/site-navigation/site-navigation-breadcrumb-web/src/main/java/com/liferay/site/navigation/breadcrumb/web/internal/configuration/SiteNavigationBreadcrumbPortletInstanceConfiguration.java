/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.breadcrumb.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(
	category = "breadcrumbs",
	scope = ExtendedObjectClassDefinition.Scope.PORTLET_INSTANCE
)
@Meta.OCD(
	id = "com.liferay.site.navigation.breadcrumb.web.internal.configuration.SiteNavigationBreadcrumbPortletInstanceConfiguration",
	localization = "content/Language",
	name = "site-navigation-breadcrumb-portlet-instance-configuration-name"
)
public interface SiteNavigationBreadcrumbPortletInstanceConfiguration {

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	/**
	 * Set a DDM template ID that starts with the prefix "ddmTemplate_" (i.e.
	 * ddmTemplate_BREADCRUMB-HORIZONTAL-FTL) to use as the display style.
	 */
	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(deflt = "true", name = "show-current-site", required = false)
	public boolean showCurrentGroup();

	@Meta.AD(deflt = "false", name = "show-guest-site", required = false)
	public boolean showGuestGroup();

	@Meta.AD(deflt = "true", name = "show-page", required = false)
	public boolean showLayout();

	@Meta.AD(deflt = "true", name = "show-parent-sites", required = false)
	public boolean showParentGroups();

	@Meta.AD(
		deflt = "true", name = "show-application-breadcrumb", required = false
	)
	public boolean showPortletBreadcrumb();

}