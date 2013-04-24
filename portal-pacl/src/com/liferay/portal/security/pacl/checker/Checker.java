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

package com.liferay.portal.security.pacl.checker;

import com.liferay.portal.security.pacl.PACLPolicy;

import java.security.Permission;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public interface Checker {

	public void afterPropertiesSet();

	public AuthorizationProperty generateAuthorizationProperty(
		Object... arguments);

	public ClassLoader getClassLoader();

	public PACLPolicy getPACLPolicy();

	public String getServletContextName();

	public boolean implies(Permission permission);

	public void setPACLPolicy(PACLPolicy paclPolicy);

}