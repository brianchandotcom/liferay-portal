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
 * @author Víctor Galán
 */
@ExtendedObjectClassDefinition(
	category = "pages", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.layout.content.page.editor.web.internal.configuration.PageEditorConfiguration",
	localization = "content/Language", name = "page-editor-configuration-name"
)
public interface PageEditorConfiguration {

	@Meta.AD(
		deflt = "true",
		description = "page-editor-auto-extend-session-enabled-description",
		name = "auto-extend-session-enabled", required = false
	)
	public boolean autoExtendSessionEnabled();

	@Meta.AD(
		deflt = "20",
		description = "page-editor-max-number-of-items-edit-mode-description",
		name = "max-number-of-items-edit-mode", required = false
	)
	public int maxNumberOfItemsEditMode();

}