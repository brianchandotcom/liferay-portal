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

package com.liferay.portal.kernel.bean;

import aQute.bnd.annotation.ProviderType;

import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Miguel Pastor
 */
@ProviderType
public interface BeanLocator {

	public void destroy();

	public ClassLoader getClassLoader();

	public String[] getNames();

	public Class<?> getType(String name) throws BeanLocatorException;

	public <T> Map<String, T> locate(Class<T> clazz)
		throws BeanLocatorException;

	public Object locate(String name) throws BeanLocatorException;

}