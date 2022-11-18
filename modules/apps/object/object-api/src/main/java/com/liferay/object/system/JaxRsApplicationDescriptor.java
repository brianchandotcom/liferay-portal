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

package com.liferay.object.system;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

/**
 * @author Carlos Correa
 */
public class JaxRsApplicationDescriptor {

	public JaxRsApplicationDescriptor(
		String applicationName, String applicationPath, String path,
		String version) {

		_applicationName = applicationName;
		_applicationPath = applicationPath;
		_path = path;
		_version = version;
	}

	public String getApplicationName() {
		return _applicationName;
	}

	public String getApplicationPath() {
		return _applicationPath;
	}

	public String getPath() {
		return _path;
	}

	public String getRESTContextPath() {
		return StringBundler.concat(
			_applicationPath, StringPool.SLASH, _version, StringPool.SLASH,
			_path);
	}

	public String getVersion() {
		return _version;
	}

	private final String _applicationName;
	private final String _applicationPath;
	private final String _path;
	private final String _version;

}