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
@ExtendedObjectClassDefinition(category = "navigation")
@Meta.OCD(
	id = "com.liferay.site.navigation.menu.web.internal.configuration.SiteNavigationMenuWebTemplateConfiguration",
	localization = "content/Language",
	name = "site-navigation-menu-web-template-configuration-name"
)
public interface SiteNavigationMenuWebTemplateConfiguration {

	@Meta.AD(
		deflt = "navbar-blank-ftl", name = "ddm-template-key-default",
		required = false
	)
	public String ddmTemplateKeyDefault();

}