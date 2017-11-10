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

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Minhchau Dang
 */
public class RegularJavaFileObject extends BaseJavaFileObject {

	public RegularJavaFileObject(String className, Path path) {
		super(Kind.CLASS, className);

		_path = path;
	}

	@Override
	public InputStream openInputStream() throws IOException {
		return Files.newInputStream(_path);
	}

	@Override
	public URI toUri() {
		return _path.toUri();
	}

	private final Path _path;

}