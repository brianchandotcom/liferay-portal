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

package com.liferay.translation.translator.azure.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Adolfo Pérez
 */
@ExtendedObjectClassDefinition(
	category = "translation",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.translation.translator.azure.internal.configuration.AzureTranslatorConfiguration",
	localization = "content/Language",
	name = "azure-translator-configuration-name"
)
public interface AzureTranslatorConfiguration {

	@Meta.AD(
		deflt = "false", description = "enabled-description[azure-translation]",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(deflt = "", name = "subscription-key-name", required = false)
	public String subscriptionKey();

	@Meta.AD(deflt = "", name = "resource-location-name", required = false)
	public String resourceLocation();

}