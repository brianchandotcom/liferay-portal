/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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