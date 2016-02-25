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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.util.ClassLoaderPool;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author Shuyang Zhou
 */
public class AnnotatedObjectOutputStream extends ObjectOutputStream {

	public AnnotatedObjectOutputStream(OutputStream outputStream)
		throws IOException {

		super(outputStream);
	}

	@Override
	protected void annotateClass(Class<?> clazz) throws IOException {
		ClassLoader classLoader = clazz.getClassLoader();

		String contextName = ClassLoaderPool.getContextName(classLoader);

		writeUTF(contextName);
	}

}