/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.site.map.web.internal.configuration;

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
	id = "com.liferay.site.navigation.site.map.web.internal.configuration.SiteNavigationSiteMapPortletInstanceConfiguration",
	localization = "content/Language",
	name = "site-navigation-site-map-portlet-instance-configuration-name"
)
public interface SiteNavigationSiteMapPortletInstanceConfiguration {

	@Meta.AD(deflt = "0", name = "display-style-group-id", required = false)
	public long displayStyleGroupId();

	@Meta.AD(name = "display-style", required = false)
	public String displayStyle();

	@Meta.AD(name = "root-layout-uuid", required = false)
	public String rootLayoutUuid();

	@Meta.AD(deflt = "0", name = "display-depth", required = false)
	public int displayDepth();

	@Meta.AD(deflt = "false", name = "include-root-in-tree", required = false)
	public boolean includeRootInTree();

	@Meta.AD(deflt = "false", name = "show-current-page", required = false)
	public boolean showCurrentPage();

	@Meta.AD(deflt = "false", name = "use-html-title", required = false)
	public boolean useHtmlTitle();

	@Meta.AD(deflt = "false", name = "show-hidden-pages", required = false)
	public boolean showHiddenPages();

}