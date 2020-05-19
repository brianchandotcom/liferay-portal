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

package com.liferay.portal.security.antisamy.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marta Medio
 */
@ExtendedObjectClassDefinition(
	category = "security-tools", factoryInstanceLabelAttribute = "model",
	scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.security.antisamy.configuration.AntiSamyModelConfiguration",
	localization = "content/Language",
	name = "anti-samy-model-configuration-name"
)
public interface AntiSamyModelConfiguration {

	@Meta.AD(deflt = "", name = "model")
	public String model();

	@Meta.AD(
		deflt = "/META-INF/resources/sanitizer-configuration.xml",
		name = "configuration-file-url"
	)
	public String configurationFileURL();

}