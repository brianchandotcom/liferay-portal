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

package com.liferay.portal.kernel.io.resource;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.URL;

/**
 * @author Miguel Pastor
 */
public class ClassPathResource implements Resource {

	public ClassPathResource(String location, ClassLoader classLoader) {
		_location = location;
		_classLoader = classLoader;
	}

	public boolean exists() {
		URL url = _getURL();

		if (url != null) {
			return true;
		}

		return false;
	}

	public InputStream getInputStream() {
		return _classLoader.getResourceAsStream(_location);
	}

	public String getName() {
		return _location;
	}

	public URL getURL() {
		return _getURL();
	}

	public String readContent() {
		InputStream inputStream = getInputStream();

		String s = null;

		try {
			s = StringUtil.read(inputStream);
		}
		catch (Exception e) {
			s = StringPool.BLANK;
		}

		return s;
	}

	private URL _getURL() {
		return _classLoader.getResource(_location);
	}

	private final ClassLoader _classLoader;
	private final String _location;

}