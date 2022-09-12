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

package com.liferay.portal.vulcan.internal.multipart;

import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Parameter;

import javax.servlet.http.Part;

import javax.ws.rs.core.HttpHeaders;

/**
 * @author Javier Gamarra
 */
public class MultipartUtil {

	public static String getFileName(Part part) {
		String header = part.getHeader(HttpHeaders.CONTENT_DISPOSITION);

		if (header == null) {
			return part.getName();
		}

		String string = "filename=\"";

		int index = header.indexOf(string);

		if (index == -1) {
			return null;
		}

		return header.substring(index + string.length(), header.length() - 1);
	}

	public static boolean isMultipartBody(Parameter parameter) {
		Class<?> clazz = parameter.getType();

		return StringUtil.contains(clazz.getTypeName(), "MultipartBody");
	}

}