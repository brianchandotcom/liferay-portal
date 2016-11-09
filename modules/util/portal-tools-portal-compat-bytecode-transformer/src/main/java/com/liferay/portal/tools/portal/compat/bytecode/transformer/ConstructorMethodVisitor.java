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

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tom Wang
 */
public class ConstructorMethodVisitor extends MethodVisitor {

	public ConstructorMethodVisitor(
		int api, MethodVisitor mv, String superName) {

		super(api, null);
		_methodMethodVisitor = mv;
		_superName = superName;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return _methodMethodVisitor.visitAnnotation(desc, visible);
	}

	@Override
	public void visitCode() {
		_methodMethodVisitor.visitCode();
		_methodMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		_methodMethodVisitor.visitMethodInsn(
			Opcodes.INVOKESPECIAL, _superName, "<init>", "()V", false);
		_methodMethodVisitor.visitInsn(Opcodes.RETURN);
		_methodMethodVisitor.visitMaxs(1, 1);
		_methodMethodVisitor.visitEnd();
	}

	private final MethodVisitor _methodMethodVisitor;
	private final String _superName;

}