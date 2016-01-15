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

import com.liferay.portal.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Lino Alves
 */
@ExtendedObjectClassDefinition(
	category = "productivity", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.dynamic.data.mapping.configuration.DDMServiceConfiguration",
	localization = "content/Language", name = "%ddm.service.configuration.name"
)
public interface DDMServiceConfiguration {

	@Meta.AD(
		deflt = ".gif|.jpeg|.jpg|.png",
		description ="%small.image.extensions.description", required = false
	)
	public String[] smallImageExtensions();

	@Meta.AD(
		deflt = "51200", description ="%small.image.max.size.description",
		required = false
	)
	public int smallImageMaxSize();

}