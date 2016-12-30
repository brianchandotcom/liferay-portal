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

package com.liferay.portal.asm;

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Matthew Tambara
 */
public class ASMWrapperUtil {

	public static <T> T createASMWrapper(
		ClassLoader classLoader, Class<T> interfaceClass, Object delegateObject,
		T defaultObject) {

		if (!interfaceClass.isInterface()) {
			throw new IllegalArgumentException(
				interfaceClass + " is not an interface");
		}

		Class<?> clazz = delegateObject.getClass();

		Package pkg = clazz.getPackage();

		StringBundler sb = new StringBundler(4);

		sb.append(pkg.getName());
		sb.append(StringPool.PERIOD);
		sb.append(interfaceClass.getSimpleName());
		sb.append("ASMWrapper");

		String asmWrapperClassName = sb.toString();

		Class<?> asmWrapperClass = null;

		synchronized (classLoader) {
			try {
				try {
					asmWrapperClass = classLoader.loadClass(
						asmWrapperClassName);
				}
				catch (ClassNotFoundException cnfe) {
					byte[] classData = _generateASMWrapperClassData(
						asmWrapperClassName.replace('.', '/'), interfaceClass,
						delegateObject, defaultObject);

					asmWrapperClass = (Class<?>)_defineClassMethod.invoke(
						classLoader, asmWrapperClassName, classData, 0,
						classData.length);
				}

				Constructor<?> constructor =
					asmWrapperClass.getDeclaredConstructor(
						delegateObject.getClass(), defaultObject.getClass());

				constructor.setAccessible(true);

				return (T)constructor.newInstance(
					delegateObject, defaultObject);
			}
			catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}

	private static <T> byte[] _generateASMWrapperClassData(
		String asmWrapperClassBinaryName, Class<T> interfaceClass,
		Object delegateObject, T defaultObject) {

		String interfaceClassBinaryName = _getClassBinaryName(interfaceClass);

		Class<?> delegateObjectClass = delegateObject.getClass();

		String delegateObjectClassDescriptor = Type.getDescriptor(
			delegateObjectClass);

		String defaultObjectClassDescriptor = Type.getDescriptor(
			defaultObject.getClass());

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

		classWriter.visit(
			Opcodes.V1_7, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER,
			asmWrapperClassBinaryName, null, _getClassBinaryName(Object.class),
			new String[] {interfaceClassBinaryName});

		// Fields

		FieldVisitor fieldVisitor = classWriter.visitField(
			Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL, "_delegate",
			delegateObjectClassDescriptor, null, null);

		fieldVisitor.visitEnd();

		fieldVisitor = classWriter.visitField(
			Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL, "_default",
			defaultObjectClassDescriptor, null, null);

		fieldVisitor.visitEnd();

		// Constructor

		MethodVisitor methodVisitor = classWriter.visitMethod(
			Opcodes.ACC_PRIVATE, "<init>",
			Type.getMethodDescriptor(
				Type.VOID_TYPE, Type.getType(delegateObjectClass),
				Type.getType(defaultObjectClassDescriptor)),
			null, null);

		methodVisitor.visitCode();

		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

		methodVisitor.visitMethodInsn(
			Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);

		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

		methodVisitor.visitVarInsn(Opcodes.ALOAD, 1);

		methodVisitor.visitFieldInsn(
			Opcodes.PUTFIELD, asmWrapperClassBinaryName, "_delegate",
			delegateObjectClassDescriptor);

		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

		methodVisitor.visitVarInsn(Opcodes.ALOAD, 2);

		methodVisitor.visitFieldInsn(
			Opcodes.PUTFIELD, asmWrapperClassBinaryName, "_default",
			defaultObjectClassDescriptor);

		methodVisitor.visitInsn(Opcodes.RETURN);

		methodVisitor.visitMaxs(0, 0);

		methodVisitor.visitEnd();

		// Delegate and fallback methods

		for (Method method : interfaceClass.getMethods()) {
			try {
				Method delegateMethod = delegateObjectClass.getMethod(
					method.getName(), method.getParameterTypes());

				_generateMethod(
					classWriter, delegateMethod, asmWrapperClassBinaryName,
					"_delegate", delegateObjectClassDescriptor,
					_getClassBinaryName(delegateObjectClass));
			}
			catch (NoSuchMethodException nsme) {
				_generateMethod(
					classWriter, method, asmWrapperClassBinaryName, "_default",
					defaultObjectClassDescriptor,
					_getClassBinaryName(defaultObject.getClass()));
			}
		}

		classWriter.visitEnd();

		return classWriter.toByteArray();
	}

	private static void _generateMethod(
		ClassWriter classWriter, Method method,
		String asmWrapperClassBinaryName, String fieldName,
		String targetClassDescriptor, String targetClassBinaryName) {

		Class<?>[] exceptions = method.getExceptionTypes();

		String[] exceptionsBinaryClassNames = new String[exceptions.length];

		for (int i = 0; i < exceptions.length; i++) {
			exceptionsBinaryClassNames[i] = _getClassBinaryName(exceptions[i]);
		}

		MethodVisitor methodVisitor = classWriter.visitMethod(
			Opcodes.ACC_PUBLIC, method.getName(),
			Type.getMethodDescriptor(method), null, exceptionsBinaryClassNames);

		methodVisitor.visitCode();

		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

		methodVisitor.visitFieldInsn(
			Opcodes.GETFIELD, asmWrapperClassBinaryName, fieldName,
			targetClassDescriptor);

		int i = 1;

		for (Class<?> parameterClass : method.getParameterTypes()) {
			Type type = Type.getType(parameterClass);

			methodVisitor.visitVarInsn(type.getOpcode(Opcodes.ILOAD), i);

			i += type.getSize();
		}

		methodVisitor.visitMethodInsn(
			Opcodes.INVOKEVIRTUAL, targetClassBinaryName, method.getName(),
			Type.getMethodDescriptor(method), false);

		Type type = Type.getType(method.getReturnType());

		methodVisitor.visitInsn(type.getOpcode(Opcodes.IRETURN));

		methodVisitor.visitMaxs(0, 0);

		methodVisitor.visitEnd();
	}

	private static String _getClassBinaryName(Class<?> clazz) {
		String className = clazz.getName();

		return className.replace('.', '/');
	}

	private ASMWrapperUtil() {
	}

	private static final Method _defineClassMethod;

	static {
		try {
			_defineClassMethod = ReflectionUtil.getDeclaredMethod(
				ClassLoader.class, "defineClass", String.class, byte[].class,
				int.class, int.class);
		}
		catch (Throwable t) {
			throw new ExceptionInInitializerError(t);
		}
	}

}