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

package com.liferay.portal.configuration.metatype.bnd.util;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Shuyang Zhou
 */
public class ConfigurableUtil {

	public static <T> T createConfigurable(
		Class<T> clazz, Dictionary<?, ?> properties) {

		return _createConfigurableSnapshot(
			clazz, Configurable.createConfigurable(clazz, properties));
	}

	public static <T> T createConfigurable(
		Class<T> clazz, Map<?, ?> properties) {

		return _createConfigurableSnapshot(
			clazz, Configurable.createConfigurable(clazz, properties));
	}

	private static <T> T _createConfigurableSnapshot(
		Class<T> interfaceClass, T configurable) {

		String snapshotClassName = interfaceClass.getName().concat("Snapshot");

		snapshotClassName = snapshotClassName.concat(
			String.valueOf(_counter.getAndIncrement()));

		try {
			byte[] snapshotClassData = _generateSnapshotClassData(
				interfaceClass, snapshotClassName, configurable);

			Class<T> snapshotClass = (Class<T>)_defineClassMethod.invoke(
				interfaceClass.getClassLoader(), snapshotClassName,
				snapshotClassData, 0, snapshotClassData.length);

			Constructor<T> snapshotClassConstructor =
				snapshotClass.getConstructor(configurable.getClass());

			return snapshotClassConstructor.newInstance(configurable);
		}
		catch (Throwable t) {
			throw new RuntimeException(
				"Unable to create snapshot class for " + interfaceClass, t);
		}
	}

	private static <T> byte[] _generateSnapshotClassData(
			Class<T> interfaceClass, String snapshotClassName, T configurable)
		throws Exception {

		String snapshotClassBinaryName = _getClassBinaryName(snapshotClassName);
		String objectClassBinaryName = _getClassBinaryName(
			Object.class.getName());

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

		classWriter.visit(
			Opcodes.V1_6, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER,
			snapshotClassBinaryName, null, objectClassBinaryName,
			new String[] {_getClassBinaryName(interfaceClass.getName())});

		Method[] declaredMethods = interfaceClass.getDeclaredMethods();

		// Fields

		for (Method method : declaredMethods) {
			Class<?> returnType = method.getReturnType();

			if (returnType.isPrimitive() || returnType.isEnum() ||
				(returnType == String.class)) {

				continue;
			}

			FieldVisitor fieldVisitor = classWriter.visitField(
				Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL, method.getName(),
				Type.getDescriptor(returnType), null, null);

			fieldVisitor.visitEnd();
		}

		// Constructor

		Class<?> configurableClass = configurable.getClass();

		MethodVisitor constructorMethodVisitor = classWriter.visitMethod(
			Opcodes.ACC_PUBLIC, "<init>",
			Type.getMethodDescriptor(
				Type.VOID_TYPE, Type.getType(configurableClass)),
			null, null);

		constructorMethodVisitor.visitCode();

		constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		constructorMethodVisitor.visitMethodInsn(
			Opcodes.INVOKESPECIAL, objectClassBinaryName, "<init>", "()V",
			false);

		for (Method method : declaredMethods) {
			Class<?> returnType = method.getReturnType();

			if (returnType.isPrimitive() || returnType.isEnum() ||
				(returnType == String.class)) {

				continue;
			}

			constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 1);

			String methodName = method.getName();

			constructorMethodVisitor.visitMethodInsn(
				Opcodes.INVOKEVIRTUAL,
				_getClassBinaryName(configurableClass.getName()), methodName,
				Type.getMethodDescriptor(method), false);

			constructorMethodVisitor.visitFieldInsn(
				Opcodes.PUTFIELD, snapshotClassBinaryName, methodName,
				Type.getDescriptor(returnType));
		}

		constructorMethodVisitor.visitInsn(Opcodes.RETURN);

		constructorMethodVisitor.visitMaxs(0, 0);

		constructorMethodVisitor.visitEnd();

		// Methods

		for (Method method : declaredMethods) {
			String methodName = method.getName();
			Class<?> returnType = method.getReturnType();

			MethodVisitor methodVisitor = classWriter.visitMethod(
				Opcodes.ACC_PUBLIC, methodName,
				Type.getMethodDescriptor(method), null, null);

			methodVisitor.visitCode();

			method.setAccessible(true);

			if (returnType.isPrimitive() || (returnType == String.class)) {
				Object result = method.invoke(configurable);

				if (result == null) {
					methodVisitor.visitInsn(Opcodes.ACONST_NULL);
				}
				else {
					methodVisitor.visitLdcInsn(result);
				}

				Type returnValueType = Type.getType(returnType);

				methodVisitor.visitInsn(
					returnValueType.getOpcode(Opcodes.IRETURN));
			}
			else if (returnType.isEnum()) {
				Enum<?> result = (Enum<?>)method.invoke(configurable);

				String fieldName = result.name();

				Field enumField = ReflectionUtil.getDeclaredField(
					returnType, fieldName);

				methodVisitor.visitFieldInsn(
					Opcodes.GETSTATIC,
					_getClassBinaryName(returnType.getName()), fieldName,
					Type.getDescriptor(enumField.getType()));

				methodVisitor.visitInsn(Opcodes.ARETURN);
			}
			else {
				methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

				methodVisitor.visitFieldInsn(
					Opcodes.GETFIELD, snapshotClassBinaryName, methodName,
					Type.getDescriptor(returnType));

				methodVisitor.visitInsn(Opcodes.ARETURN);
			}

			methodVisitor.visitMaxs(0, 0);

			methodVisitor.visitEnd();
		}

		classWriter.visitEnd();

		return classWriter.toByteArray();
	}

	private static String _getClassBinaryName(String className) {
		return className.replace(CharPool.PERIOD, CharPool.FORWARD_SLASH);
	}

	private static final AtomicLong _counter = new AtomicLong();
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