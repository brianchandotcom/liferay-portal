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

package com.liferay.petra.process;

import java.io.File;
import java.io.Serializable;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Objects;

/**
 * @author Shuyang Zhou
 */
public class PathHolder implements Serializable {

	public PathHolder(Path path) {
		this(path.toString());
	}

	public PathHolder(String pathString) {
		_pathString = pathString;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PathHolder)) {
			return false;
		}

		PathHolder pathHolder = (PathHolder)obj;

		if (Objects.equals(toString(), pathHolder.toString())) {
			return true;
		}

		return false;
	}

	public Path getPath() {
		return Paths.get(toString());
	}

	@Override
	public int hashCode() {
		String toString = toString();

		return toString.hashCode();
	}

	@Override
	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		if (_SEPARATOR_CHAR == File.separatorChar) {
			_toString = _pathString;
		}
		else {
			_toString = _pathString.replace(
				_SEPARATOR_CHAR, File.separatorChar);
		}

		return _toString;
	}

	private static final char _SEPARATOR_CHAR = File.separatorChar;

	private static final long serialVersionUID = 1L;

	private final String _pathString;
	private transient String _toString;

}