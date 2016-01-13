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

package com.liferay.portal.kernel.struts;

import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class LastPath implements Serializable {

	public LastPath(String contextPath, String path) {
		this(contextPath, path, Collections.<String, String[]>emptyMap());
	}

	public LastPath(
		String contextPath, String path, Map<String, String[]> parameterMap) {

		_contextPath = contextPath;
		_path = path;
		_parameterMap = new LinkedHashMap<>(parameterMap);
	}

	public String getContextPath() {
		return _contextPath;
	}

	public Map<String, String[]> getParameterMap() {
		return _parameterMap;
	}

	public String getPath() {
		return _path;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{contextPath=");
		sb.append(_contextPath);
		sb.append(", path=");
		sb.append(_path);
		sb.append("}");

		return sb.toString();
	}

	private final String _contextPath;
	private final Map<String, String[]> _parameterMap;
	private final String _path;

}