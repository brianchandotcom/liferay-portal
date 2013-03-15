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

import com.liferay.portal.kernel.cache.CacheRegistryItem;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.security.BasicPermission;

/**
 * @author Raymond Augé
 */
public class PACLUtil {

	public static Class<?> getClass(Object object) {
		Class<?> clazz = object.getClass();

		if (object instanceof Class) {
			clazz = (Class<?>)object;
		}

		if (ProxyUtil.isProxyClass(clazz) || !clazz.isInterface()) {
			Class<?>[] interfaces = clazz.getInterfaces();

			if (interfaces.length > 0) {
				clazz = interfaces[0];

				if (clazz.equals(CacheRegistryItem.class) &&
					(interfaces.length > 1)) {

					clazz = interfaces[1];
				}
			}
		}

		return clazz;
	}

	public static PACLPolicy getPACLPolicy() {
		if (!PACLPolicyManager.isActive()) {
			return null;
		}

		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager == null) {
			return null;
		}

		try {
			java.security.Permission permission = new PACLUtil.Permission();

			securityManager.checkPermission(permission);
		}
		catch (SecurityException se) {
			if (!(se instanceof PACLUtil.Exception)) {
				throw se;
			}

			PACLUtil.Exception paclUtilException = (PACLUtil.Exception)se;

			return paclUtilException.getPaclPolicy();
		}

		return null;
	}

	public static class Exception extends SecurityException {

		public Exception(PACLPolicy paclPolicy) {
			_paclPolicy = paclPolicy;
		}

		public PACLPolicy getPaclPolicy() {
			return _paclPolicy;
		}

		private PACLPolicy _paclPolicy;

	}

	public static class Permission extends BasicPermission {

		public Permission() {
			super("getPACLPolicy");
		}

	}

}