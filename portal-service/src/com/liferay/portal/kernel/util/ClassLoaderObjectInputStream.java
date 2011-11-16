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

package com.liferay.portal.kernel.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

/**
 * @author Brian Wing Shun Chan
 */
public class ClassLoaderObjectInputStream extends ObjectInputStream {

	public ClassLoaderObjectInputStream(InputStream is, ClassLoader classLoader)
		throws IOException {

		super(is);

		_classLoader = classLoader;
	}

	@Override
	protected Class<?> resolveClass(ObjectStreamClass osc)
		throws ClassNotFoundException {

		return Class.forName(osc.getName(), true, _classLoader);
	}

	private ClassLoader _classLoader;

}