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

package com.liferay.portal.store.file.system.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Manuel de la Peña
 */
@ExtendedObjectClassDefinition(category = "file-storage")
@Meta.OCD(
	id = "com.liferay.portal.store.file.system.configuration.FileSystemStoreConfiguration",
	localization = "content/Language",
	name = "file-system-store-configuration-name"
)
public interface FileSystemStoreConfiguration {

	@Meta.AD(
		deflt = "data/document_library",
		description = "file-system-store-configuration-root-dir-description",
		name = "root-dir", required = false
	)
	public String rootDir();

	@Meta.AD(
		deflt = "false",
		description = "file-system-store-configuration-use-hard-links-description",
		name = "use-hard-links", required = false
	)
	public boolean useHardLinks();

}