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
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Tom Wang
 */
public class UnsupportedExceptionMethodVisitor extends MethodVisitor {

	public UnsupportedExceptionMethodVisitor(MethodVisitor mv) {
		super(Opcodes.ASM5, null);

		_methodMethodVisitor = mv;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return _methodMethodVisitor.visitAnnotation(desc, visible);
	}

	@Override
	public void visitCode() {
		_methodMethodVisitor.visitCode();
		_methodMethodVisitor.visitTypeInsn(
			Opcodes.NEW,
			Type.getInternalName(UnsupportedOperationException.class));
		_methodMethodVisitor.visitInsn(Opcodes.DUP);
		_methodMethodVisitor.visitMethodInsn(
			Opcodes.INVOKESPECIAL,
			Type.getInternalName(UnsupportedOperationException.class), "<init>",
			"()V", false);
		_methodMethodVisitor.visitInsn(Opcodes.ATHROW);
		_methodMethodVisitor.visitMaxs(2, 0);
		_methodMethodVisitor.visitEnd();
	}

	@Override
	public void visitLocalVariable(
		String name, String desc, String signature, Label start, Label end,
		int index) {

		if (!name.equals("args")) {
			_methodMethodVisitor.visitLocalVariable(
				name, desc, signature, start, end, index);
		}
	}

	private final MethodVisitor _methodMethodVisitor;

}