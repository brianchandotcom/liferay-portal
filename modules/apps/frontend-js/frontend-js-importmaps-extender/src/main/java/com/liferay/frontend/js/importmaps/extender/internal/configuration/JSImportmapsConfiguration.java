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

package com.liferay.frontend.js.importmaps.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera Avellón
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "frontend-js-importmaps-description",
	id = "com.liferay.frontend.js.importmaps.extender.internal.configuration.JSImportmapsConfiguration",
	localization = "content/Language",
	name = "frontend-js-importmaps-configuration-name"
)
public interface JSImportmapsConfiguration {

	@Meta.AD(deflt = "true", name = "enable-importmaps", required = false)
	public boolean enableImportmaps();

	@Meta.AD(
		deflt = "true", description = "enable-es-module-shims-help",
		name = "enable-es-module-shims", required = false
	)
	public boolean enableESModuleShims();

}