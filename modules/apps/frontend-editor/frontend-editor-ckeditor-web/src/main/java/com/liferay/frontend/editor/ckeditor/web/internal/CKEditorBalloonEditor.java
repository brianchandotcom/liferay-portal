/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.editor.ckeditor.web.internal;

import com.liferay.portal.kernel.editor.Editor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Julien Castelain
 */
@Component(
	property = "name=" + CKEditorBalloonEditor.EDITOR_NAME,
	service = Editor.class
)
public class CKEditorBalloonEditor extends BaseCKEditor {

	public static final String EDITOR_NAME = "ballooneditor";

	@Override
	public String getJspPath() {
		return "/ckeditor_balloon.jsp";
	}

	@Override
	public String getName() {
		return EDITOR_NAME;
	}

}