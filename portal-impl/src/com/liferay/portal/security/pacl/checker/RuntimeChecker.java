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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.permission.pacl.PACLConstants;

import java.security.Permission;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Raymond Augé
 */
public class RuntimeChecker extends FileChecker {

	public RuntimeChecker(PACLPolicy paclPolicy) {
		super(paclPolicy);

		initClassLoaderPortletIds();
	}

	@Override
	public void checkPermission(Permission permission) {
		String name = permission.getName();

		if (name.startsWith(PACLConstants.ACCESS_CLASS_IN_PACKAGE)) {
			int pos = name.indexOf(StringPool.PERIOD);

			String pkg = name.substring(pos + 1);

			if (!hasPackageAccess(pkg)) {
				throw new SecurityException(
					"Attempted to access package " + pkg);
			}
		}
		else if (name.equals(PACLConstants.GET_CLASSLOADER) &&
				 PortalSecurityManagerThreadLocal.isClassLoaderCheckingEnabled()) {

			String portletId = null;

			int pos = name.indexOf(StringPool.PERIOD);

			if (pos != -1) {
				portletId = name.substring(pos + 1);
			}

			if (Validator.isNull(portletId)) {
				portletId = "foreign";
			}

			if (!hasGetClassLoader(portletId)) {
				Thread.dumpStack();

				throw new SecurityException(
					"Attempted to get an external class loader " + portletId);
			}
		}
		else if (name.equals(PACLConstants.SET_SECURITY_MANAGER)) {
			throw new SecurityException(
				"Attempted to set another security manager");
		}
		else if (name.equals(PACLConstants.EXIT_VM)) {
			Thread.dumpStack();

			throw new SecurityException("Attempted to shutdown the VM!");
		}
	}

	public boolean hasGetClassLoader(String portletId) {
		if (isJSPCompiler(portletId, "compile JSP")) {
			return true;
		}

		return _classLoaderPortletIds.contains(portletId);
	}

	public boolean hasPackageAccess(String pkg) {
		// TODO

		System.out.println("hasPackageAccess: " + pkg);

		if (pkg.startsWith("sun.reflect")) {
		}

		return true;
	}

	protected void initClassLoaderPortletIds() {
		_classLoaderPortletIds = getPropertySet(
			"security-manager-get-class-loader");

		if (_log.isDebugEnabled()) {
			Set<String> indexers = new TreeSet<String>(_classLoaderPortletIds);

			for (String indexer : indexers) {
				_log.debug("Allowing access to class loaders from " + indexer);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RuntimeChecker.class);

	private Set<String> _classLoaderPortletIds;

}