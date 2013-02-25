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

package com.liferay.portal.security.lang;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.pacl.PACLPolicy;
import com.liferay.portal.security.pacl.PACLPolicyManager;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;

/**
 * This is the portal's implementation of a security manager. The goal is to
 * protect portal resources from plugins and prevent security issues by forcing
 * plugin developers to openly declare their requirements. Where a
 * SecurityManager exists, we set that as the parent and delegate to it as a
 * fallback. This class will not delegate checks to super when there is no
 * parent so as to avoid forcing the need for a default policy.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Zsolt Berentey
 */
public class SecurityManagerUtil {

	public static void init() {
		readState(PropsValues.PORTAL_SECURITY_MANAGER_STRATEGY);
	}

	public static void initPolicy(
		String servletContextName, ClassLoader classLoader,
		Properties properties) {

		if (isDefault() || isNone()) {
			return;
		}

		PACLPolicy paclPolicy = PACLPolicyManager.buildPACLPolicy(
			servletContextName, classLoader, properties);

		PACLPolicyManager.register(classLoader, paclPolicy);
	}

	public static boolean isDefault() {
		init();

		return _state == State.DEFAULT;
	}

	public static boolean isLiferay() {
		init();

		return _state == State.LIFERAY;
	}

	public static boolean isPACLDisabled() {
		return isDefault() || isNone();
	}

	public static boolean isNone() {
		init();

		return _state == State.NONE;
	}

	public static boolean isSmart() {
		init();

		return _state == State.SMART;
	}

	public static State getState() {
		init();

		return _state;
	}

	public static void unregister(ClassLoader classLoader) {
		if (isDefault() || isNone()) {
			return;
		}

		PACLPolicyManager.unregister(classLoader);
	}

	protected static void readState(String state) {
		if (_state != null) {
			return;
		}

		_state = State.read(state);

		if (_state == State.LIFERAY) {
			System.setSecurityManager(new PortalSecurityManager());
		}
		else if (_state == State.NONE) {
			System.setSecurityManager(null);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		SecurityManagerUtil.class.getName());

	private static State _state;

	public enum State {
		DEFAULT, LIFERAY, NONE, SMART;

		public static State read(String state) {
			if (state.equals("default")) {
				return DEFAULT;
			}
			else if (state.equals("liferay")) {
				return LIFERAY;
			}
			else if (state.equals("smart")) {
				if (ServerDetector.isWebSphere()) {
					return NONE;
				}

				return SMART;
			}

			return NONE;
		}
	}

}