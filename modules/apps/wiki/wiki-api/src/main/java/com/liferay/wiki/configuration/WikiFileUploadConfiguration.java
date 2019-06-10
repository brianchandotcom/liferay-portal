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

package com.liferay.wiki.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Roberto Díaz
 */
@ExtendedObjectClassDefinition(category = "wiki")
@Meta.OCD(
	id = "com.liferay.wiki.configuration.WikiFileUploadConfiguration",
	localization = "content/Language",
	name = "wiki-file-uploads-configuration-name"
)
public interface WikiFileUploadConfiguration {

	@Meta.AD(
		deflt = "*",
		description = "allowed-wiki-attachment-mime-types-description",
		name = "allowed-wiki-attachment-mime-types", required = false
	)
	public String[] attachmentMimeTypes();

	@Meta.AD(
		deflt = "104857600", name = "maximum-wiki-attachment-size",
		required = false
	)
	public long attachmentMaxSize();

}