/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.navigation.language.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Juergen Kappler
 */
@ExtendedObjectClassDefinition(category = "localization")
@Meta.OCD(
	id = "com.liferay.site.navigation.language.web.internal.configuration.SiteNavigationLanguageWebTemplateConfiguration",
	localization = "content/Language",
	name = "site-navigation-language-web-template-configuration-name"
)
public interface SiteNavigationLanguageWebTemplateConfiguration {

	@Meta.AD(
		deflt = "language-icon-menu-ftl", name = "ddm-template-key",
		required = false
	)
	public String ddmTemplateKey();

}