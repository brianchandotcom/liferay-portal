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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.lang.PortalSecurityManagerThreadLocal;
import com.liferay.portal.spring.aop.ServiceBeanAopCacheManagerUtil;
import com.liferay.portal.util.PropsValues;

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
			paclPolicy = new InactivePACLPolicy(
				servletContextName, classLoader, properties);
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

		if (_activeCount == 1) {
			if (_log.isInfoEnabled()) {
				_log.info("Activating PACL policy manager");
			}

			_overridePortalSecurityManager();

			ServiceBeanAopCacheManagerUtil.reset();
		}
	}

	public static void unregister(ClassLoader classLoader) {
		PACLPolicy paclPolicy = _paclPolicies.remove(classLoader);

		if ((paclPolicy == null) || !paclPolicy.isActive()) {
			return;
		}

		_activeCount--;

		if (_activeCount == 0) {
			if (_log.isInfoEnabled()) {
				_log.info("Disabling PACL policy manager");
			}

			_resetPortalSecurityManager();

			ServiceBeanAopCacheManagerUtil.reset();
		}
	}

	private static void _overridePortalSecurityManager() {
		_originalSecurityManager = System.getSecurityManager();

		if (_originalSecurityManager instanceof PortalSecurityManager) {
			return;
		}

		String portalSecurityManagerStrategy =
			PropsValues.PORTAL_SECURITY_MANAGER_STRATEGY;

		if (!portalSecurityManagerStrategy.equals("smart")) {
			if (_log.isInfoEnabled()) {
				StringBundler sb = new StringBundler(4);

				sb.append("Plugin security management is not enabled. To ");
				sb.append("enable plugin security management, set the ");
				sb.append("property \"portal.security.manager.strategy\" in ");
				sb.append("portal.properties to \"liferay\" or \"smart\".");

				_log.info(sb.toString());
			}

			return;
		}

		try {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Overriding the current security manager to enable " +
						"plugin security management");
			}

			System.setSecurityManager(new PortalSecurityManager());
		}
		catch (SecurityException se) {
			_log.error(
				"Unable to override the current security manager. Plugin " +
					"security management is not enabled.");

			throw se;
		}
	}

	private static void _resetPortalSecurityManager() {
		if (_originalSecurityManager instanceof PortalSecurityManager) {
			return;
		}

		String portalSecurityManagerStrategy =
			PropsValues.PORTAL_SECURITY_MANAGER_STRATEGY;

		if (!portalSecurityManagerStrategy.equals("smart")) {
			return;
		}

		try {
			if (_log.isInfoEnabled()) {
				_log.info("Resetting to the original security manager");
			}

			boolean enabled = PortalSecurityManagerThreadLocal.isEnabled();

			try {
				PortalSecurityManagerThreadLocal.setEnabled(false);

				System.setSecurityManager(_originalSecurityManager);
			}
			finally {
				PortalSecurityManagerThreadLocal.setEnabled(enabled);
			}
		}
		catch (SecurityException se) {
			_log.error("Unable to reset to the original security manager");

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PACLPolicyManager.class);

	private static int _activeCount;
	private static PACLPolicy _defaultPACLPolicy = new InactivePACLPolicy(
		StringPool.BLANK, PACLPolicyManager.class.getClassLoader(),
		new Properties());
	private static SecurityManager _originalSecurityManager;
	private static Map<ClassLoader, PACLPolicy> _paclPolicies =
		new HashMap<ClassLoader, PACLPolicy>();

}