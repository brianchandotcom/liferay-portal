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

package com.liferay.cobertura.instrument;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * @author Shuyang Zhou
 */
public class ContextAwareClassWriter extends ClassWriter {

	public ContextAwareClassWriter(int flags) {
		super(flags);
	}

	@Override
	protected String getCommonSuperClass(String type1, String type2) {
		if (type1.equals(type2)) {
			return type1;
		}

		List<String> list1 = new ArrayList<>();

		list1.add(type1);

		List<String> list2 = new ArrayList<>();

		list2.add(type2);

		while (true) {
			type1 = getSuperName(type1);

			if (type1 != null) {
				if (list2.contains(type1)) {
					return type1;
				}

				list1.add(type1);
			}

			type2 = getSuperName(type2);

			if (type2 != null) {
				if (list1.contains(type2)) {
					return type2;
				}

				list2.add(type2);
			}
		}
	}

	protected String getSuperName(String type) {
		if (type == null) {
			return null;
		}

		String resourceName = type.concat(".class");

		ClassLoader classLoader =
			ContextAwareClassWriter.class.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(resourceName);

		if (inputStream == null) {
			Thread currentThread = Thread.currentThread();

			classLoader = currentThread.getContextClassLoader();

			inputStream = classLoader.getResourceAsStream(resourceName);
		}

		if (inputStream == null) {
			throw new RuntimeException(
				"Unable to locate class definition for " + type);
		}

		try {
			ClassReader classReader = new ClassReader(inputStream);

			return classReader.getSuperName();
		}
		catch (IOException ioe) {
			throw new RuntimeException(
				"Unable to parse class definition for " + type, ioe);
		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException ioe) {
			}
		}
	}

}