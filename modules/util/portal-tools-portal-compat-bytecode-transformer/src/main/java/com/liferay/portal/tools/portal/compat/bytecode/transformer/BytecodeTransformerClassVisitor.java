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

package com.liferay.portal.tools.portal.compat.bytecode.transformer;

import java.lang.reflect.Modifier;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tom Wang
 */
public class BytecodeTransformerClassVisitor extends ClassVisitor {

	public BytecodeTransformerClassVisitor(ClassVisitor classVisitor) {
		super(Opcodes.ASM4, classVisitor);
	}

	@Override
	public void visit(
		int version, int access, String name, String signature,
		String superName, String[] interfaces) {

		cv.visit(version, access, name, signature, superName, interfaces);

		_superName = superName;

		if (Modifier.isInterface(access)) {
			_isInterface = true;
		}
		else {
			_isInterface = false;
		}
	}

	@Override
	public FieldVisitor visitField(
		int access, String name, String desc, String signature, Object value) {

		if (Modifier.isPrivate(access)) {
			return null;
		}

		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(
		int access, String name, String desc, String signature,
		String[] exceptions) {

		if (Modifier.isPrivate(access)) {
			return null;
		}

		MethodVisitor methodVisitor = cv.visitMethod(
			access, name, desc, signature, exceptions);

		if (_isInterface) {
			return methodVisitor;
		}

		if (name.equals("<init>")) {
			ConstructorMethodVisitor constructorMethodVisitor =
				new ConstructorMethodVisitor(
					Opcodes.ASM5, methodVisitor, _superName);

			return constructorMethodVisitor;
		}

		if (name.startsWith("set") || name.equals("<clinit>") ||
			name.equals("afterPropertiesSet") || name.equals("destroy")) {

			EmptyBodyMethodVisitor emptyBodyMethodVisitor =
				new EmptyBodyMethodVisitor(Opcodes.ASM5, methodVisitor);

			return emptyBodyMethodVisitor;
		}

		UnsupportedExceptionMethodVisitor unsupportedExceptionMethodVisitor =
			new UnsupportedExceptionMethodVisitor(Opcodes.ASM5, methodVisitor);

		return unsupportedExceptionMethodVisitor;
	}

	private boolean _isInterface;
	private String _superName;

}