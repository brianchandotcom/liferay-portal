/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		name = "max-number-of-items-in-edit-mode", required = false
	)
	public int maxNumberOfItemsInEditMode();

}