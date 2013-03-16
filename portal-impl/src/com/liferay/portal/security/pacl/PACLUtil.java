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

		return isTrustedCaller(protectionDomain, permission, paclPolicy);
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

	private static boolean hasSameOrigin(
		ProtectionDomain protectionDomain,
		PermissionCollection permissionCollection, PACLPolicy paclPolicy) {

		PACLPolicy callerPaclPolicy = null;

		if (permissionCollection instanceof PortalPermissionCollection) {
			PortalPermissionCollection portalPermissionCollection =
				(PortalPermissionCollection)permissionCollection;

			callerPaclPolicy = portalPermissionCollection.getPaclPolicy();
		}
		else {
			callerPaclPolicy = PACLPolicyManager.getPACLPolicy(
				protectionDomain.getClassLoader());
		}

		if (paclPolicy == callerPaclPolicy) {
			return true;
		}

		return false;
	}

	private static boolean isTrustedCaller(
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

		boolean hasSameOrigin = hasSameOrigin(
			protectionDomain, permissionCollection, paclPolicy);

		if (!hasSameOrigin && permissionCollection.implies(permission)) {
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