/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.alloyeditor.web.internal;

import com.liferay.portal.kernel.editor.Editor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = "name=" + AlloyEditorCreoleEditor.EDITOR_NAME,
	service = Editor.class
)
public class AlloyEditorCreoleEditor extends BaseAlloyEditor {

	public static final String EDITOR_NAME = "alloyeditor_creole";

	@Override
	public String getName() {
		return EDITOR_NAME;
	}

}