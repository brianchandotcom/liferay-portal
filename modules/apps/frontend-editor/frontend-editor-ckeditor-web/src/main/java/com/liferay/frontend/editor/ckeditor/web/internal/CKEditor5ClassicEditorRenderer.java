/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor.web.internal;

import com.liferay.frontend.editor.EditorRenderer;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marko Cikos
 */
@Component(
	property = "name=" + CKEditor5ClassicEditor.EDITOR_NAME,
	service = EditorRenderer.class
)
public class CKEditor5ClassicEditorRenderer extends BaseCKEditorRenderer {

	@Override
	public String getJspPath() {
		return "/ckeditor5_classic.jsp";
	}

}