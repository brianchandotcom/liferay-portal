/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class FileFilter implements java.io.FileFilter {

	public FileFilter() {
	}

	public FileFilter(String regex) {
		_pattern = Pattern.compile(regex);
	}

	@Override
	public boolean accept(File file) {
		if (file.isFile()) {
			if (_pattern == null) {
				return true;
			}
			else {
				Matcher matcher = _pattern.matcher(file.getName());

				return matcher.matches();
			}
		}
		else {
			return false;
		}
	}

	private Pattern _pattern;

}