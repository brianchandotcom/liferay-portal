/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.admin.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Vendel Töreki
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.site.admin.web.internal.configuration.SiteAdminConfiguration",
	localization = "content/Language", name = "site-admin-configuration-name"
)
public interface SiteAdminConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "enable-custom-languages-with-template-propagation-help",
		name = "enable-custom-languages-with-template-propagation",
		required = false
	)
	public boolean enableCustomLanguagesWithTemplatePropagation();

}