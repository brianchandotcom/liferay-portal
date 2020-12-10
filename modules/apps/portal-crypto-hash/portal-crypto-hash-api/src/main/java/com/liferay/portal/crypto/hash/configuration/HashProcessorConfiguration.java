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

package com.liferay.portal.crypto.hash.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marta Medio
 */
@ExtendedObjectClassDefinition(
	category = "security-tools",
	factoryInstanceLabelAttribute = "configuration.name"
)
@Meta.OCD(
	description = "hash-processor-configuration-description", factory = true,
	id = "com.liferay.portal.crypto.hash.configuration.HashProcessorConfiguration",
	localization = "content/Language", name = "hash-processor"
)
public interface HashProcessorConfiguration {

	@Meta.AD(id = "configuration.name", name = "hash-processor-name")
	public String name();

	@Meta.AD(
		description = "hash-pepper-storage-select-description",
		id = "HashPepperStorage.target", name = "hash-pepper-storage-select"
	)
	public String hashPepperStorageSelect();

}