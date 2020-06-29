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

package com.liferay.dynamic.data.mapping.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jorge Díaz
 */
@ExtendedObjectClassDefinition(category = "dynamic-data-mapping")
@Meta.OCD(
	id = "com.liferay.dynamic.data.mapping.configuration.DDMIndexerConfiguration",
	localization = "content/Language", name = "ddm-indexer-configuration-name"
)
public interface DDMIndexerConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "enable-legacy-ddm-index-fields-description",
		name = "enable-legacy-ddm-index-fields", required = false
	)
	public boolean enableLegacyDDMIndexFields();

}