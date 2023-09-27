/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.alloyeditor.web.internal;

import com.liferay.portal.kernel.editor.Editor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Raymond Augé
 * @author Roberto Díaz
 */
@Component(
	property = "name=" + AlloyEditorEditor.EDITOR_NAME, service = Editor.class
)
public class AlloyEditorEditor extends BaseAlloyEditor {

	public static final String EDITOR_NAME = "alloyeditor";

	@Override
	public String getName() {
		return EDITOR_NAME;
	}

}