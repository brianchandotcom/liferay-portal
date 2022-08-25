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

package com.liferay.translation.translator.deepl.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Yasuyuki Takeo
 */
@ExtendedObjectClassDefinition(
	category = "translation",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration",
	localization = "content/Language",
	name = "deepl-translator-configuration-name"
)
public interface DeepLTranslatorConfiguration {

	@Meta.AD(
		deflt = "false", description = "enabled-description[deepl-translation]",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(name = "token", required = false)
	public String authKey();

	@Meta.AD(
		deflt = "https://api-free.deepl.com/v2/translate", name = "api-url",
		required = false
	)
	public String url();

}