/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.editor;

import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class EditorUtil {

	public static String getEditorMode(String langType) {
		String editorMode = "php";

		if (langType.equals("css")) {
			editorMode = "css";
		}
		else if (langType.equals("xml") ||
				 langType.equals("xsl") ||
				 langType.equals("xsd")) {

			editorMode = "xml";
		}

		return editorMode;
	}

	public static String getEditorValue(
		HttpServletRequest request, String editorImpl) {

		if (Validator.isNotNull(editorImpl)) {
			editorImpl = PropsUtil.get(editorImpl);
		}

		if (!BrowserSnifferUtil.isRtf(request)) {
			if (BrowserSnifferUtil.isSafari(request) &&
				BrowserSnifferUtil.isMobile(request)) {

				editorImpl = "simple";
			}
			else if (BrowserSnifferUtil.isSafari(request) &&
					 !editorImpl.contains("simple")) {

				editorImpl = "tinymce_simple";
			}
			else {
				editorImpl = "simple";
			}
		}

		if (Validator.isNull(editorImpl)) {
			editorImpl = _EDITOR_WYSIWYG_DEFAULT;
		}

		return editorImpl;
	}

	private static final String _EDITOR_WYSIWYG_DEFAULT = PropsUtil.get(
		PropsKeys.EDITOR_WYSIWYG_DEFAULT);

}