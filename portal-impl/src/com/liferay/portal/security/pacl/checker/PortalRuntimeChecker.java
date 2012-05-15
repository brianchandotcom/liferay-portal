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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.security.Permission;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalRuntimeChecker extends BaseChecker {

	public void afterPropertiesSet() {
		initClassNames();
	}

	public void checkPermission(Permission permission) {
		PortalRuntimePermission portalRuntimePermission =
			(PortalRuntimePermission)permission;

		String name = portalRuntimePermission.getName();
		Object subject = portalRuntimePermission.getSubject();

		if (name.equals(PORTAL_RUNTIME_PERMISSION_SET_BEAN_PROPERTY)) {
			Class<?> clazz = (Class<?>)subject;

			if (!_classNames.contains(clazz.getName())) {
				throwSecurityException(
					_log, "Attempted to set bean property on " + clazz);
			}
		}
	}

	protected void initClassNames() {
		_classNames = getPropertySet("security-manager-set-bean-property");

		if (_log.isDebugEnabled()) {
			Set<String> classNames = new TreeSet<String>(_classNames);

			for (String className : classNames) {
				_log.debug(
					"Allowing set bean property name on class " + className);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortalRuntimeChecker.class);

	private Set<String> _classNames;

}