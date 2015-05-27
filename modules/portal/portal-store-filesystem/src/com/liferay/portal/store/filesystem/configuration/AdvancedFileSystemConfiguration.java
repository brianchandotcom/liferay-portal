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

package com.liferay.portal.store.filesystem.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Manuel de la Peña
 */
@Meta.OCD(
	id = "com.liferay.portal.store.filesystem.configuration.AdvancedFileSystemConfiguration",
	localization = "content/Language", name = "%advanced.filesystem.store.configuration.name"
)
public interface AdvancedFileSystemConfiguration {

	@Meta.AD(deflt = "data/document_library",
		description = "%filesystem.store.configuration.root.dir.description",
		required = true)
	public String rootDir();

}