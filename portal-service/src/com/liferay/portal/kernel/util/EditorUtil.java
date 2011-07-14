/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Julio Camarero
 */
public class EditorUtil {

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
				(editorImpl.indexOf("simple") == -1)) {

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

	private static Log _log = LogFactoryUtil.getLog(EditorUtil.class);

}