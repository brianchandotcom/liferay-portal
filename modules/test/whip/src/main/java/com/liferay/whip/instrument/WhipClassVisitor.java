/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.whip.instrument;

import com.liferay.whip.coveragedata.ClassData;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Shuyang Zhou
 */
public class WhipClassVisitor extends ClassVisitor {

	public WhipClassVisitor(ClassData classData, ClassVisitor classVisitor) {
		super(Opcodes.ASM5, classVisitor);

		_classData = classData;
	}

	@Override
	public void visit(
		int version, int access, String name, String signature,
		String superName, String[] interfaces) {

		if ((access & (Opcodes.ACC_INTERFACE | Opcodes.ACC_SYNTHETIC)) == 0) {
			_instrument = true;
		}

		super.visit(version, access, name, signature, superName, interfaces);
	}

	@Override
	public void visitEnd() {
		if (_instrument && (_classData.getNumberOfValidLines() == 0)) {
			System.err.println(
				"No line number information found for class " +
					_classData.getName() +
						". Please recompile with debug info.");
		}
	}

	@Override
	public MethodVisitor visitMethod(
		int access, String name, String desc, String signature,
		String[] exceptions) {

		MethodVisitor methodVisitor = cv.visitMethod(
			access, name, desc, signature, exceptions);

		if (!_instrument || (methodVisitor == null) ||
			((access & Opcodes.ACC_BRIDGE) != 0)) {

			return methodVisitor;
		}

		return new OutlineMethodVisitor(
			_classData, methodVisitor, access, name, desc, signature,
			exceptions);
	}

	private final ClassData _classData;
	private boolean _instrument;

}