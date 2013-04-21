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

package com.liferay.portal.kernel.security.pacl.permission;

import java.security.BasicPermission;

/**
 * This permission implementation is only tested directly against the PACLPolicy
 * and so it never passes through the security API.
 *
 * @author Raymond Augé
 */
public class PortalHookPermission extends BasicPermission {

	public static void checkPermission(
		String name, ClassLoader portletClassLoader, Object subject) {

		_pacl.checkPermission(name, portletClassLoader, subject);
	}

	public PortalHookPermission(
		String name, ClassLoader classLoader, Object subject) {

		super(name);

		_classLoader = classLoader;
		_subject = subject;
	}

	public ClassLoader getClassLoader() {
		return _classLoader;
	}

	public Object getSubject() {
		return _subject;
	}

	private static PACL _pacl = new NoPACL();

	private transient ClassLoader _classLoader;
	private transient Object _subject;

	private static class NoPACL implements PACL {

		public void checkPermission(
			String name, ClassLoader portletClassLoader, Object subject) {
		}

	}

	public static interface PACL {

		public void checkPermission(
			String name, ClassLoader portletClassLoader, Object subject);

	}

}