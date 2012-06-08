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

package com.liferay.portal.spring.util;

import com.liferay.portal.security.pacl.PACLClassLoaderUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class FilterClassLoader extends ClassLoader {

	public FilterClassLoader(ClassLoader classLoader) {
		super(classLoader);
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if (name.startsWith("net.sf.ehcache.") ||
			name.startsWith("org.aopalliance.") ||
			name.startsWith("org.hibernate.") ||
			name.startsWith("org.springframework.")) {

			ClassLoader portalClassLoader =
				PACLClassLoaderUtil.getPortalClassLoader();

			return portalClassLoader.loadClass(name);
		}

		return super.loadClass(name);
	}

}