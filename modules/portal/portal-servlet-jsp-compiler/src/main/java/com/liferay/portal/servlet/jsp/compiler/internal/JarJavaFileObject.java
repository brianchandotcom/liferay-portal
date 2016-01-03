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

import com.liferay.portal.kernel.zip.ZipFileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

/**
 * @author Shuyang Zhou
 */
public class JarJavaFileObject extends BaseJavaFileObject {

	public JarJavaFileObject(String className, File file, String entryName) {
		super(Kind.CLASS, className);

		_file = file;
		_entryName = entryName;
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