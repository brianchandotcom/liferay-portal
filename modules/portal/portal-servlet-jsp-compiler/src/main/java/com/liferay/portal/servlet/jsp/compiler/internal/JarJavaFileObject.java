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

package com.liferay.portal.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.jar.JarFile;

/**
 * @author Shuyang Zhou
 */
public class JarJavaFileObject extends BaseJavaFileObject {

	public JarJavaFileObject(String className, URL url, String entryName)
		throws IOException {

		super(Kind.CLASS, className);

		_entryName = entryName;

		_jarURLConnection =(JarURLConnection)url.openConnection();
	}

	@Override
	public InputStream openInputStream() throws IOException {
		JarFile jarFile = _jarURLConnection.getJarFile();

		return jarFile.getInputStream(jarFile.getJarEntry(_entryName));
	}

	@Override
	public URI toUri() {
		try {
			URL url = _jarURLConnection.getJarFileURL();

			return url.toURI();
		}
		catch (URISyntaxException urise) {
			return ReflectionUtil.throwException(urise);
		}
	}

	private final String _entryName;
	private final JarURLConnection _jarURLConnection;

}