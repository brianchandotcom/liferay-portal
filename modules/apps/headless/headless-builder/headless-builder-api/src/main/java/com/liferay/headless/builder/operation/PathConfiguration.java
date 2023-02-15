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

package com.liferay.headless.builder.operation;

import com.liferay.headless.builder.util.URLUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Carlos Correa
 */
public class PathConfiguration {

	public PathConfiguration(String path) {
		_path = path;

		_pattern = URLUtil.getPattern(path);

		_pathParameterNames = URLUtil.getPathParameterNames(path, _pattern);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if ((object == null) || (getClass() != object.getClass())) {
			return false;
		}

		PathConfiguration that = (PathConfiguration)object;

		if (Objects.equals(_path, that._path) &&
			Objects.equals(_pattern, that._pattern)) {

			return true;
		}

		return false;
	}

	public String getPath() {
		return _path;
	}

	public List<String> getPathParameterNames() {
		return _pathParameterNames;
	}

	public Pattern getPattern() {
		return _pattern;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_path, _pattern);
	}

	private final String _path;
	private final List<String> _pathParameterNames;
	private final Pattern _pattern;

}