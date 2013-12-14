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
 * @author     Raymond Augé
 * @author     Zsolt Berentey
 * @deprecated As of 6.2.0
 */
@Deprecated
public class CheckMemberAccessPermission extends BasicPermission {

	public CheckMemberAccessPermission(
		String name, Class<?> callerClass, ClassLoader callerClassLoader,
		Class<?> subjectClass, ClassLoader subjectClassLoader) {

		super(name);
	}

}