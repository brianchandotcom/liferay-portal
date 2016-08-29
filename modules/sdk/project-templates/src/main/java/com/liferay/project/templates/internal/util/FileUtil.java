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

package com.liferay.project.templates.internal.util;

import com.liferay.project.templates.ProjectTemplates;

import java.io.File;

import java.net.URL;

import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			ProjectTemplates.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

}