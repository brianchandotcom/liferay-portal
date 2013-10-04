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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.security.AccessController;
import java.security.PrivilegedAction;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PACLPolicyManager {

	public static PACLPolicy buildPACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		String value = properties.getProperty(
			"security-manager-enabled", "false");

		if (value.equals("generate")) {
			return new GeneratingPACLPolicy(
				servletContextName, classLoader, properties);
		}

		if (GetterUtil.getBoolean(value)) {
			return new ActivePACLPolicy(
				servletContextName, classLoader, properties);
		}
		else {
			return new InactivePACLPolicy(
				servletContextName, classLoader, properties);
		}
	}

	public static PACLPolicy getDefaultPACLPolicy() {
		return _defaultPACLPolicy;
	}

	public static PACLPolicy getPACLPolicy(ClassLoader classLoader) {
		return AccessController.doPrivileged(
			new PACLPolicyPrivilegedAction(classLoader));
	}

	public static void register(
		ClassLoader classLoader, PACLPolicy paclPolicy) {

		_paclPolicies.put(classLoader, paclPolicy);
	}

	public static void unregister(ClassLoader classLoader) {
		PACLPolicy paclPolicy = _paclPolicies.remove(classLoader);

		if ((paclPolicy == null) || !paclPolicy.isActive()) {
			return;
		}
	}

	private static PACLPolicy _defaultPACLPolicy = new InactivePACLPolicy(
		StringPool.BLANK, PACLPolicyManager.class.getClassLoader(),
		new Properties());
	private static Map<ClassLoader, PACLPolicy> _paclPolicies =
		new HashMap<ClassLoader, PACLPolicy>();

	private static class PACLPolicyPrivilegedAction
		implements PrivilegedAction<PACLPolicy> {

		public PACLPolicyPrivilegedAction(ClassLoader classLoader) {
			_classLoader = classLoader;
		}

		@Override
		public PACLPolicy run() {
			PACLPolicy paclPolicy = _paclPolicies.get(_classLoader);

			while ((paclPolicy == null) && (_classLoader.getParent() != null)) {
				_classLoader = _classLoader.getParent();

				paclPolicy = _paclPolicies.get(_classLoader);
			}

			return paclPolicy;
		}

		private ClassLoader _classLoader;

	}

}