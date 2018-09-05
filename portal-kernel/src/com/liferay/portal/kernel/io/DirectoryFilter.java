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

package com.liferay.portal.kernel.io;

import java.io.File;
import java.io.FileFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author     Brian Wing Shun Chan
 * @author     Alexander Chow
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class DirectoryFilter implements FileFilter {

	public DirectoryFilter() {
	}

	public DirectoryFilter(String regex) {
		_pattern = Pattern.compile(regex);
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			if (_pattern == null) {
				return true;
			}

			Matcher matcher = _pattern.matcher(file.getName());

			return matcher.matches();
		}

		return false;
	}

	private Pattern _pattern;

}