/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Iván Zaera Avellón
 */
@ExtendedObjectClassDefinition(category = "infrastructure", generateUI = false)
@Meta.OCD(
	description = "frontend-js-import-maps-description",
	id = "com.liferay.frontend.js.importmaps.extender.internal.configuration.JSImportMapsConfiguration",
	localization = "content/Language",
	name = "frontend-js-import-maps-configuration-name"
)
public interface JSImportMapsConfiguration {

	@Meta.AD(deflt = "true", name = "enable-import-maps", required = false)
	public boolean enableImportMaps();

	@Meta.AD(
		deflt = "false", description = "enable-es-module-shims-help",
		name = "enable-es-module-shims", required = false
	)
	public boolean enableESModuleShims();

}