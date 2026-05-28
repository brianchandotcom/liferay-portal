/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Eudaldo Alonso
 */
@ExtendedObjectClassDefinition(
	category = "seo", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "layout-seo-general-configuration-description",
	id = "com.liferay.layout.seo.internal.configuration.LayoutSEOGeneralGroupConfiguration",
	localization = "content/Language",
	name = "layout-seo-general-configuration-name"
)
public interface LayoutSEOGeneralGroupConfiguration {

	@Meta.AD(
		deflt = "true", name = "include-the-instance-name-in-the-html-title",
		required = false
	)
	public boolean includeInstanceName();

	@Meta.AD(
		deflt = "true", name = "include-the-site-name-in-the-html-title",
		required = false
	)
	public boolean includeSiteName();

}