/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jonathan McCann
 */
@ExtendedObjectClassDefinition(
	category = "aeo-seo", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.site.internal.configuration.LLMSGroupConfiguration",
	localization = "content/Language", name = "llms-txt-configuration-name"
)
public interface LLMSGroupConfiguration {

	@Meta.AD(deflt = "", name = "llms-txt-content", required = false)
	public String content();

	@Meta.AD(deflt = "false", name = "enable-llms-txt", required = false)
	public boolean enabled();

}