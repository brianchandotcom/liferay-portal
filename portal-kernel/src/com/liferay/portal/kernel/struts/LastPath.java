/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.struts;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 */
public class LastPath implements Serializable {

	public LastPath(String contextPath, String path) {
		this(contextPath, path, StringPool.BLANK);
	}

	public LastPath(String contextPath, String path, String parameters) {
		_contextPath = contextPath;
		_path = path;
		_parameters = parameters;
	}

	public String getContextPath() {
		return _contextPath;
	}

	public String getParameters() {
		return _parameters;
	}

	public String getPath() {
		return _path;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{contextPath=", _contextPath, ", path=", _path, "}");
	}

	private final String _contextPath;
	private final String _parameters;
	private final String _path;

}