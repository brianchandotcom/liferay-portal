/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;

import java.security.Permission;
import java.security.Policy;

/**
 * @author Raymond Augé
 */
public class PortalPermissionCollection extends LenientPermissionCollection {

	public PortalPermissionCollection(PACLPolicy paclPolicy) {
		_paclPolicy = paclPolicy;
	}

	public ClassLoader getClassLoader() {
		return _paclPolicy.getClassLoader();
	}

	public PACLPolicy getPACLPolicy() {
		return _paclPolicy;
	}

	public Policy getPolicy() {
		return _paclPolicy.getPolicy();
	}

	@Override
	public boolean implies(Permission permission) {
		if (!_paclPolicy.isActive()) {
			return true;
		}

		if (permission instanceof PACLUtil.Permission) {
			PACLPolicyThreadLocal.set(_paclPolicy);

			return true;
		}

		if (_paclPolicy.implies(permission)) {
			return true;
		}

		return false;
	}

	@Override
	public String toString() {
		Class<?> clazz = getClass();

		String className = clazz.getSimpleName();

		return className.concat(StringPool.POUND).concat(
			_paclPolicy.toString());
	}

	private final PACLPolicy _paclPolicy;

}