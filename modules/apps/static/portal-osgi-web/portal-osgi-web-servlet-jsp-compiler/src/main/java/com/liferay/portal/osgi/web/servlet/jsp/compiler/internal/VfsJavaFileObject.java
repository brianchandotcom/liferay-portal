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

package com.liferay.portal.osgi.web.servlet.jsp.compiler.internal;

import com.liferay.portal.kernel.zip.ZipFileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * @author Shuyang Zhou
 */
public class VfsJavaFileObject extends BaseJavaFileObject {

	public VfsJavaFileObject(String className, URL url, String entryName)
		throws MalformedURLException {

		super(Kind.CLASS, className);

		_entryName = entryName;

		String file = url.getFile();

		int index = file.indexOf(".jar");

		if (index < 0) {
			throw new MalformedURLException(
				url + " does not denote a jar file");
		}

		_file = new File(file.substring(0, index + 4));
	}

	@Override
	public InputStream openInputStream() throws IOException {
		return ZipFileUtil.openInputStream(_file, _entryName);
	}

	@Override
	public URI toUri() {
		return _file.toURI();
	}

	private final String _entryName;
	private final File _file;

}