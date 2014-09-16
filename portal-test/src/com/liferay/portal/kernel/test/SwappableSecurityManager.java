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

package com.liferay.portal.kernel.test;

import java.io.Closeable;

import java.security.Permission;

/**
 * @author Shuyang Zhou
 */
public class SwappableSecurityManager
	extends SecurityManager implements Closeable {

	public SwappableSecurityManager() {
		_securityManager = System.getSecurityManager();
	}

	@Override
	public void checkPermission(Permission permission) {
	}

	@Override
	public void close() {
		System.setSecurityManager(_securityManager);
	}

	public void install() {
		System.setSecurityManager(this);
	}

	private final SecurityManager _securityManager;

}