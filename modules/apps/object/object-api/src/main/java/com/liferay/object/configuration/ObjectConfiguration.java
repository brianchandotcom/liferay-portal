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

package com.liferay.object.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Rodrigo Paulino
 */
@ExtendedObjectClassDefinition(
	category = "object", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.object.configuration.ObjectConfiguration",
	localization = "content/Language", name = "object-configuration-name"
)
public interface ObjectConfiguration {

	@Meta.AD(
		deflt = "100",
		description = "guest-maximum-entry-submissions-per-object-help",
		name = "guest-maximum-entry-submissions-per-object", required = false
	)
	public int guestMaximumEntrySubmissionsPerObject();

	@Meta.AD(
		deflt = "25", description = "guest-maximum-file-size-help",
		name = "guest-maximum-file-size", required = false
	)
	public int guestMaximumFileSize();

}