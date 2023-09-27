/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor.web.internal;

import com.liferay.frontend.editor.EditorRenderer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "name=" + CKEditorEditor.EDITOR_NAME,
	service = EditorRenderer.class
)
public class CKEditorEditorRenderer extends BaseCKEditorRenderer {

	@Override
	public String getJspPath() {
		return "/ckeditor.jsp";
	}

	@Override
	public String getResourcesJspPath() {
		return "/resources.jsp";
	}

}