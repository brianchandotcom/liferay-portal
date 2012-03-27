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

import java.lang.reflect.Method;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class InactivePACLPolicy extends BasePACLPolicy {

	public InactivePACLPolicy(
		String servletContextName, Properties properties) {

		super(servletContextName, properties);
	}

	public boolean hasFileDeletePermission(String fileName) {
		return true;
	}

	public boolean hasFileExecutePermission(String fileName) {
		return true;
	}

	public boolean hasFileReadPermission(String fileName) {
		return true;
	}

	public boolean hasFileWritePermission(String fileName) {
		return true;
	}

	public boolean hasServicePermission(Object object, Method method) {
		return true;
	}

	public boolean hasSocketConnectPermission(String host, int port) {
		return true;
	}

	public boolean hasSocketListenPermission(int port) {
		return true;
	}

	public boolean isActive() {
		return false;
	}

}