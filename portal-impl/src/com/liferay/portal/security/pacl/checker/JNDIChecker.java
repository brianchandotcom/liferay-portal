/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pacl.checker;

import java.security.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class JNDIChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initNames();
	}

	public void checkPermission(Permission permission) {
		throw new UnsupportedOperationException();
	}

	public boolean hasJNDI(String name) {
		for (Pattern pattern : _patterns) {
			Matcher matcher = pattern.matcher(name);

			if (matcher.matches()) {
				return true;
			}
		}

		return false;
	}

	protected void initNames() {
		Set<String> names = getPropertySet("security-manager-jndi-names");

		_patterns = new ArrayList<Pattern>(names.size());

		for (String name : names) {
			Pattern pattern = Pattern.compile(name);

			_patterns.add(pattern);
		}
	}

	private List<Pattern> _patterns;

}