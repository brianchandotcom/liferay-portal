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

package com.liferay.layout.content.page.editor.web.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * The functionality enabled by this configuration is experimental and not
 * covered by Liferay's support services
 * @author Pavel Savinov
 */
@ExtendedObjectClassDefinition(
	category = "pages", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.layout.content.page.editor.web.internal.configuration.ContentCreationContentPageEditorConfiguration",
	localization = "content/Language",
	name = "content-page-editor-configuration-name"
)
public interface ContentCreationContentPageEditorConfiguration {

	/**
	 * Enables option to create content from the editable fields selection.
	 */
	@Meta.AD(
		deflt = "false", description = "content-creation-enabled-description",
		name = "content-creation-enabled", required = false
	)
	public boolean contentCreationEnabled();

}