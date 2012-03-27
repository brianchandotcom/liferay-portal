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

package com.liferay.portal.security.pacl;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.spring.aop.ServiceBeanAopProxy;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLPolicyManager {

	public static PACLPolicy buildPACLPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		boolean active = GetterUtil.getBoolean(
			properties.get("security-manager-enabled"));

		PACLPolicy paclPolicy = null;

		if (active) {
			paclPolicy = new ActivePACLPolicy(
				servletContextName, classLoader, properties);
		}
		else {
			paclPolicy = new InactivePACLPolicy(servletContextName, properties);
		}

		return paclPolicy;
	}

	public static int getActiveCount() {
		return _activeCount;
	}

	public static PACLPolicy getDefaultPACLPolicy() {
		return _defaultPACLPolicy;
	}

	public static PACLPolicy getPACLPolicy(ClassLoader classLoader) {
		return _paclPolicies.get(classLoader);
	}

	public static boolean isActive() {
		if (_activeCount > 0) {
			return true;
		}

		return false;
	}

	public static void register(
		ClassLoader classLoader, PACLPolicy paclPolicy) {

		_paclPolicies.put(classLoader, paclPolicy);

		if (!paclPolicy.isActive()) {
			return;
		}

		_activeCount++;

		if ((_activeCount == 1) &&
			(System.getSecurityManager() instanceof PortalSecurityManager)) {

			if (_log.isInfoEnabled()) {
				_log.info("Activating PACL policy manager");
			}

			ServiceBeanAopProxy.clearMethodInterceptorCache();
		}
	}

	public static void unregister(ClassLoader classLoader) {
		PACLPolicy paclPolicy = _paclPolicies.remove(classLoader);

		if ((paclPolicy == null) || !paclPolicy.isActive()) {
			return;
		}

		_activeCount--;

		if ((_activeCount == 0) &&
			(System.getSecurityManager() instanceof PortalSecurityManager)) {

			if (_log.isInfoEnabled()) {
				_log.info("Disabling PACL policy manager");
			}

			ServiceBeanAopProxy.clearMethodInterceptorCache();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PACLPolicyManager.class);

	private static int _activeCount;
	private static PACLPolicy _defaultPACLPolicy = new InactivePACLPolicy(
		StringPool.BLANK, new Properties());
	private static Map<ClassLoader, PACLPolicy> _paclPolicies =
		new HashMap<ClassLoader, PACLPolicy>();

}