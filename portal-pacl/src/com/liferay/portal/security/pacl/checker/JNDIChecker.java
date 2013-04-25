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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.security.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class JNDIChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initNames();
	}

	@Override
	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments) {

		if ((arguments == null) || (arguments.length != 1) ||
			!(arguments[0] instanceof String)) {

			return null;
		}

		AuthorizationProperty authorizationProperty =
			new AuthorizationProperty();

		authorizationProperty.setKey("security-manager-jndi-names");
		authorizationProperty.setValue((String)arguments[0]);

		return authorizationProperty;
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

	public boolean implies(Permission permission) {
		throw new UnsupportedOperationException();
	}

	protected void initNames() {
		Set<String> names = getPropertySet("security-manager-jndi-names");

		_patterns = new ArrayList<Pattern>(names.size());

		for (String name : names) {
			Pattern pattern = Pattern.compile(name);

			_patterns.add(pattern);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Allowing access to JNDI names that match the regular " +
						"expression " + name);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JNDIChecker.class);

	private List<Pattern> _patterns;

}