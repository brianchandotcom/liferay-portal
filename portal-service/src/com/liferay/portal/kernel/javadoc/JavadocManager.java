/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.javadoc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author Igor Spasic
 */
public interface JavadocManager {

	public void load(String servletContextName, ClassLoader classLoader);

	public JavadocMethod lookupJavadoc(Method method);

	public JavadocType lookupJavadoc(Class type);

	public JavadocConstructor lookupJavadoc(Constructor type);

	public JavadocMethod lookupServiceUtilMethodJavadoc(Method method);

	public void unload(String servletContextName);

}