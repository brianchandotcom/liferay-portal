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

package com.liferay.portal.kernel.util;

import java.io.File;
import java.io.InputStream;

/**
 * @author Bruno Farache
 */
public class DocumentConversionUtil {

	public static File convert(
			String id, InputStream inputStream, String sourceExtension,
			String targetExtension)
		throws Exception {

		Object returnObj = PortalClassInvoker.invoke(
			_convertMethodKey, id, inputStream, sourceExtension,
			targetExtension);

		if (returnObj != null) {
			return (File)returnObj;
		}
		else {
			return null;
		}
	}

	public static String[] getConversions(String extension) throws Exception {
		Object returnObj = PortalClassInvoker.invoke(
			_getConversionsMethodKey, extension);

		if (returnObj != null) {
			return (String[])returnObj;
		}
		else {
			return null;
		}
	}

	private static final String _CLASS_NAME =
		"com.liferay.portlet.documentlibrary.util.DocumentConversionUtil";

	private static final MethodKey _convertMethodKey = new MethodKey(
		ClassResolverUtil.resolveByPortalClassLoader(_CLASS_NAME), "convert",
		String.class, InputStream.class, String.class, String.class);
	private static final MethodKey _getConversionsMethodKey = new MethodKey(
		ClassResolverUtil.resolveByPortalClassLoader(_CLASS_NAME),
		"getConversions", String.class);

}