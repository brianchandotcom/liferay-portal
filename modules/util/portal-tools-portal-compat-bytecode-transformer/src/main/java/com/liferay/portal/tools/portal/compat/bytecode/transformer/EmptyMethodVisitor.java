/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.portal.compat.bytecode.transformer;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tong Wang
 */
public class EmptyMethodVisitor extends MethodVisitor {

	public EmptyMethodVisitor(MethodVisitor methodVisitor, String superName) {
		super(Opcodes.ASM5, null);

		_methodVisitor = methodVisitor;
		_superName = superName;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return _methodVisitor.visitAnnotation(desc, visible);
	}

	@Override
	public void visitCode() {
		_methodVisitor.visitCode();

		if (_superName != null) {
			_methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

			_methodVisitor.visitMethodInsn(
				Opcodes.INVOKESPECIAL, _superName, "<init>", "()V", false);
		}

		_methodVisitor.visitInsn(Opcodes.RETURN);

		_methodVisitor.visitMaxs(0, 0);

		_methodVisitor.visitEnd();
	}

	private final MethodVisitor _methodVisitor;
	private final String _superName;

}