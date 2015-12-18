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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.net.URL;

/**
 * @author Raymond Augé
 */
public class RuntimeClasspathResolver implements ClasspathResolver {

	@Override
	public URL[] getClasspathURLs() throws Exception {
		File coreDir = new File(PropsValues.MODULE_FRAMEWORK_BASE_DIR, "core");

		File[] files = coreDir.listFiles();

		if (files == null) {
			throw new IllegalStateException(
				"Missing " + coreDir.getCanonicalPath());
		}

		URL[] urls = new URL[files.length];

		for (int i = 0; i < urls.length; i++) {

			// Ensure URLs are properly composed for Windows environments.
			// Otherwise, we will run into unexpected errors when referencing a
			// class from a JSP. See LPS-61210 for more information.

			String path = StringUtil.replace(
				files[i].getAbsolutePath(), StringPool.BACK_SLASH,
				StringPool.SLASH);

			if (!path.startsWith(StringPool.SLASH)) {
				path = StringPool.SLASH + path;
			}

			urls[i] = new URL("file", null, path);
		}

		return urls;
	}

}