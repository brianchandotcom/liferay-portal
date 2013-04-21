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
import com.liferay.portal.security.lang.PortalSecurityManager;
import com.liferay.portal.security.lang.SecurityManagerUtil;

import java.security.AccessController;
import java.security.BasicPermission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

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

	public static String getServiceInterfaceName(String serviceClassName) {
		int pos = serviceClassName.indexOf(".impl.");

		if (pos != -1) {
			serviceClassName =
				serviceClassName.substring(0, pos + 1) +
					serviceClassName.substring(pos + 6);
		}

		if (serviceClassName.endsWith("Impl")) {
			serviceClassName = serviceClassName.substring(
				0, serviceClassName.length() - 4);
		}

		return serviceClassName;
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

	public static boolean isTrustedCaller(
		Class<?> callerClass, java.security.Permission permission,
		PACLPolicy paclPolicy) {

		ProtectionDomain protectionDomain = AccessController.doPrivileged(
			new ProtectionDomainPrivilegedAction(callerClass));

		return _isTrustedCaller(protectionDomain, permission, paclPolicy);
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

	private static boolean _hasSameOrigin(
		ProtectionDomain protectionDomain,
		PermissionCollection permissionCollection, PACLPolicy paclPolicy) {

		PACLPolicy callerPACLPolicy = null;

		if (permissionCollection instanceof PortalPermissionCollection) {
			PortalPermissionCollection portalPermissionCollection =
				(PortalPermissionCollection)permissionCollection;

			callerPACLPolicy = portalPermissionCollection.getPACLPolicy();
		}
		else {
			callerPACLPolicy = PACLPolicyManager.getPACLPolicy(
				protectionDomain.getClassLoader());
		}

		if (paclPolicy == callerPACLPolicy) {
			return true;
		}

		return false;
	}

	private static boolean _isTrustedCaller(
		ProtectionDomain protectionDomain, java.security.Permission permission,
		PACLPolicy paclPolicy) {

		if (protectionDomain.getClassLoader() == null) {
			return true;
		}

		PortalSecurityManager portalSecurityManager =
			SecurityManagerUtil.getPortalSecurityManager();

		Policy portalPolicy = portalSecurityManager.getPolicy();

		PermissionCollection permissionCollection = portalPolicy.getPermissions(
			protectionDomain);

		if (!_hasSameOrigin(
				protectionDomain, permissionCollection, paclPolicy) &&
			permissionCollection.implies(permission)) {

			return true;
		}

		return false;
	}

	private static class ProtectionDomainPrivilegedAction
		implements PrivilegedAction<ProtectionDomain> {

		public ProtectionDomainPrivilegedAction(Class<?> clazz) {
			_clazz = clazz;
		}

		public ProtectionDomain run() {
			return _clazz.getProtectionDomain();
		}

		private Class<?> _clazz;

	}

}