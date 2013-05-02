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

package com.liferay.portal.security.lang;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.kernel.util.ServiceLoader;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Zsolt Berentey
 */
public class SecurityManagerUtil {

	public static PortalSecurityManager getPortalSecurityManager() {
		return _portalSecurityManager;
	}

	public static void init() {
		if (_portalSecurityManagerStrategy != null) {
			return;
		}

		if (!PropsValues.TCK_URL) {
			_portalSecurityManagerStrategy =
				PortalSecurityManagerStrategy.parse(
					PropsValues.PORTAL_SECURITY_MANAGER_STRATEGY);
		}
		else {
			_portalSecurityManagerStrategy = PortalSecurityManagerStrategy.NONE;
		}

		if ((_portalSecurityManagerStrategy ==
				PortalSecurityManagerStrategy.LIFERAY) ||
			(_portalSecurityManagerStrategy ==
				PortalSecurityManagerStrategy.SMART)) {

			loadPortalSecurityManager();
		}

		if (_portalSecurityManager == null) {
			_portalSecurityManagerStrategy =
				PortalSecurityManagerStrategy.DEFAULT;

			if (_log.isInfoEnabled()) {
				_log.info(
					"No portal security manager implementation was located. " +
						"Continuing with the default security strategy.");
			}

			return;
		}
		else if (_portalSecurityManagerStrategy ==
					PortalSecurityManagerStrategy.LIFERAY) {

			System.setSecurityManager((SecurityManager)_portalSecurityManager);
		}
		else if (_portalSecurityManagerStrategy ==
					PortalSecurityManagerStrategy.NONE) {

			System.setSecurityManager(null);
		}
	}

	public static boolean isActive() {
		if (_portalSecurityManager == null) {
			return false;
		}

		return _portalSecurityManager.isActive();
	}

	public static boolean isDefault() {
		init();

		if (_portalSecurityManagerStrategy ==
				PortalSecurityManagerStrategy.DEFAULT) {

			return true;
		}

		return false;
	}

	public static boolean isLiferay() {
		init();

		if (_portalSecurityManagerStrategy ==
				PortalSecurityManagerStrategy.LIFERAY) {

			return true;
		}

		return false;
	}

	public static boolean isNone() {
		init();

		if (_portalSecurityManagerStrategy ==
				PortalSecurityManagerStrategy.NONE) {

			return true;
		}

		return false;
	}

	public static boolean isPACLDisabled() {
		if (isDefault() || isNone()) {
			return true;
		}

		return false;
	}

	public static boolean isSmart() {
		init();

		if (_portalSecurityManagerStrategy ==
				PortalSecurityManagerStrategy.SMART) {

			return true;
		}

		return false;
	}

	private static void loadPortalSecurityManager() {
		try {
			List<PortalSecurityManager> portalSecurityManagers =
				ServiceLoader.load(PortalSecurityManager.class);

			if (portalSecurityManagers.isEmpty()) {
				return;
			}

			_portalSecurityManager = portalSecurityManagers.get(0);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SecurityManagerUtil.class);

	private static PortalSecurityManager _portalSecurityManager;
	private static PortalSecurityManagerStrategy _portalSecurityManagerStrategy;

	private enum PortalSecurityManagerStrategy {

		DEFAULT, LIFERAY, NONE, SMART;

		public static PortalSecurityManagerStrategy parse(String value) {
			if (value.equals("default")) {
				return DEFAULT;
			}
			else if (value.equals("liferay")) {
				return LIFERAY;
			}
			else if (value.equals("smart")) {
				if (ServerDetector.isWebSphere()) {
					return NONE;
				}

				return SMART;
			}

			return NONE;
		}

	}

}