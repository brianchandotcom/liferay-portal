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

package com.liferay.item.selector.web.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.documentlibrary.util.AudioProcessorUtil;
import com.liferay.portlet.documentlibrary.util.ImageProcessorUtil;
import com.liferay.portlet.documentlibrary.util.VideoProcessorUtil;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class ItemSelectorUtil {

	public static String getCKEditorFuncNum(HttpServletRequest request) {
		String ckEditorFuncNum = ParamUtil.getString(
			request, "ckEditorFuncNum");

		return ParamUtil.getString(request, "CKEditorFuncNum", ckEditorFuncNum);
	}

	public static String[] getMimeTypes(HttpServletRequest request) {
		Set<String> mimeTypes = _getMimeTypes(getType(request));

		return ArrayUtil.toStringArray(mimeTypes.toArray());
	}

	public static String[] getTabs1Names(HttpServletRequest request) {
		String tabs1Names = ParamUtil.getString(request, "tabs1Names");

		if (Validator.isNotNull(tabs1Names)) {
			return StringUtil.split(tabs1Names);
		}

		if (Validator.isNotNull(getType(request))) {
			return new String[] {_TYPE_DOCUMENTS};
		}

		return new String[] {_TYPE_DOCUMENTS, _TYPE_PAGES};
	}

	public static String getType(HttpServletRequest request) {
		return ParamUtil.getString(request, "type");
	}

	private static Set<String> _getMimeTypes(String type) {
		if (StringUtil.equalsIgnoreCase(type, "audio")) {
			return AudioProcessorUtil.getAudioMimeTypes();
		}
		else if (StringUtil.equalsIgnoreCase(type, "image")) {
			return ImageProcessorUtil.getImageMimeTypes();
		}
		else if (StringUtil.equalsIgnoreCase(type, "video")) {
			return VideoProcessorUtil.getVideoMimeTypes();
		}
		else {
			return Collections.emptySet();
		}
	}

	private static final String _TYPE_DOCUMENTS = "documents";

	private static final String _TYPE_PAGES = "pages";

}