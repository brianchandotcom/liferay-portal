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

import com.liferay.petra.reflect.ReflectionUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Shuyang Zhou
 */
public class BundleJavaFileObject extends BaseJavaFileObject {

	public BundleJavaFileObject(String className, URL url) {
		super(Kind.CLASS, className);

		_url = url;
	}

	@Override
	public InputStream openInputStream() throws IOException {
		return _url.openStream();
	}

	@Override
	public URI toUri() {
		try {
			return _url.toURI();
		}
		catch (URISyntaxException urise) {
			return ReflectionUtil.throwException(urise);
		}
	}

	private final URL _url;

}