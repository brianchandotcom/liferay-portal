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

/**
 * @author Brian Wing Shun Chan
 */
public interface PACLPolicy {

	public String getServletContextName();

	public boolean hasDynamicQueryPermission(Class<?> clazz);

	public boolean hasFileDeletePermission(String fileName);

	public boolean hasFileExecutePermission(String fileName);

	public boolean hasFileReadPermission(String fileName);

	public boolean hasFileWritePermission(String fileName);

	public boolean hasHookPortalProperty(String key);

	public boolean hasHookService(String className);

	public boolean hasServicePermission(Object object, Method method);

	public boolean hasSocketConnectPermission(String host, int port);

	public boolean hasSocketListenPermission(int port);

	public boolean isActive();

}