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

package com.liferay.portlet.documentselector.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.util.AudioProcessorUtil;
import com.liferay.portlet.documentlibrary.util.ImageProcessorUtil;
import com.liferay.portlet.documentlibrary.util.VideoProcessorUtil;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DocumentSelectorUtil {

	public static String getCKEditorFuncNum(HttpServletRequest request) {
		String ckEditorFuncNum = ParamUtil.getString(
			request, "ckEditorFuncNum");

		HttpServletRequest originalRequest =
			PortalUtil.getOriginalServletRequest(request);

		return ParamUtil.getString(
			originalRequest, "CKEditorFuncNum", ckEditorFuncNum);
	}

	public static String[] getMimeTypes(HttpServletRequest request) {
		HttpServletRequest originalRequest =
			PortalUtil.getOriginalServletRequest(request);

		String type = ParamUtil.getString(originalRequest, "Type");

		Set<String> mimeTypes = _getMimeTypes(type);

		return ArrayUtil.toStringArray(mimeTypes.toArray());
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

}