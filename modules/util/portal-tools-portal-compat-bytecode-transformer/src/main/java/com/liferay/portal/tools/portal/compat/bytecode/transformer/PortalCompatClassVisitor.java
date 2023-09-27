/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.portal.compat.bytecode.transformer;

import java.lang.reflect.Modifier;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Tong Wang
 */
public class PortalCompatClassVisitor extends ClassVisitor {

	public PortalCompatClassVisitor(ClassVisitor classVisitor) {
		super(Opcodes.ASM5, classVisitor);
	}

	@Override
	public void visit(
		int version, int access, String name, String signature,
		String superName, String[] interfaces) {

		cv.visit(version, access, name, signature, superName, interfaces);

		if (name.endsWith("ServiceUtil") || name.endsWith("ServiceWrapper")) {
			_annotationOnly = true;
		}

		_superName = superName;

		_interface = Modifier.isInterface(access);
	}

	@Override
	public AnnotationVisitor visitAnnotation(
		String descriptor, boolean visible) {

		if (!_interface &&
			descriptor.equals("LaQute/bnd/annotation/ProviderType;")) {

			return null;
		}

		return super.visitAnnotation(descriptor, visible);
	}

	@Override
	public FieldVisitor visitField(
		int access, String name, String desc, String signature, Object value) {

		if (!_annotationOnly && Modifier.isPrivate(access)) {
			return null;
		}

		return super.visitField(access, name, desc, signature, value);
	}

	@Override
	public MethodVisitor visitMethod(
		int access, String name, String desc, String signature,
		String[] exceptions) {

		MethodVisitor methodVisitor = cv.visitMethod(
			access, name, desc, signature, exceptions);

		if (_annotationOnly) {
			return methodVisitor;
		}

		if (Modifier.isPrivate(access)) {
			return null;
		}

		if (_interface) {
			return methodVisitor;
		}

		if (name.equals("<init>")) {
			return new EmptyMethodVisitor(methodVisitor, _superName);
		}

		if (name.equals("<clinit>") || name.equals("afterPropertiesSet") ||
			name.equals("destroy") || name.startsWith("set")) {

			return new EmptyMethodVisitor(methodVisitor, null);
		}

		int argumentsSize = Type.getArgumentsAndReturnSizes(desc) >> 2;

		if (Modifier.isStatic(access)) {
			argumentsSize--;
		}

		return new UnsupportedExceptionMethodVisitor(
			methodVisitor, argumentsSize);
	}

	private boolean _annotationOnly;
	private boolean _interface;
	private String _superName;

}