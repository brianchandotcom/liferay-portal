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

package com.liferay.portal.search.elasticsearch7.internal.test.util.microcontainer;

import java.io.IOException;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;

/**
 * @author André de Oliveira
 */
public class ASMUtil {

	public static ClassNode getClassNode(Class<?> clazz) {
		return getClassNode(clazz.getName());
	}

	public static ClassNode getClassNode(ClassReader classReader) {
		ClassNode classNode = new ClassNode();

		classReader.accept(classNode, ClassReader.SKIP_CODE);

		return classNode;
	}

	public static ClassReader getClassReader(String name) {
		try {
			return new ClassReader(name);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	protected static ClassNode getClassNode(String name) {
		return getClassNode(getClassReader(name));
	}

}