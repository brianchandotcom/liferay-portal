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

package com.liferay.portlet.documentlibrary.google.docs.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Iván Zaera
 */
public class ResourceUtil {

	public static String get(Class<?> clazz, String resource) {
		InputStream is = null;

		Bundle bundle = FrameworkUtil.getBundle(clazz);

		Package classPackage = clazz.getPackage();

		String packageName = classPackage.getName();

		String path = packageName.replaceAll("\\.", "/") + "/" + resource;

		try {
			URL entry = bundle.getEntry(path);

			is = entry.openStream();

			return StringUtil.read(is);
		}
		catch (IOException e) {
			throw new SystemException(
				"Unable to get content of entry " + path + " in bundle " +
					bundle.getSymbolicName(), e);
		}
		finally {
			StreamUtil.cleanUp(is);
		}
	}

	public static String get(Object owner, String resource) {
		return get(owner.getClass(), resource);
	}

}