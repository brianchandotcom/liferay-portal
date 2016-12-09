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

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import java.util.Dictionary;
import java.util.Map;

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

		ClassLoader classLoader = interfaceClass.getClassLoader();

		String snapshotClassName = interfaceClass.getName() + "Snapshot";

		Class<T> configurableClass = (Class<T>)configurable.getClass();

		Class<T> snapshotClass = null;

		try {
			synchronized (classLoader) {
				try {
					snapshotClass = (Class<T>)classLoader.loadClass(
						snapshotClassName);
				}
				catch (ClassNotFoundException cnfe) {
				}

				if (snapshotClass == null) {
					Method defineClassMethod = ReflectionUtil.getDeclaredMethod(
						ClassLoader.class, "defineClass", String.class,
						byte[].class, int.class, int.class);

					byte[] snapshotClassData = _generateSnapshotClassData(
						interfaceClass, configurableClass, snapshotClassName);

					snapshotClass = (Class<T>)defineClassMethod.invoke(
						classLoader, snapshotClassName, snapshotClassData, 0,
						snapshotClassData.length);
				}
			}

			Constructor<T> snapshotClassConstructor =
				snapshotClass.getConstructor(configurableClass);

			return snapshotClassConstructor.newInstance(configurable);
		}
		catch (Throwable t) {
			throw new RuntimeException(
				"Unable to create snapshot class instance", t);
		}
	}

	private static <T> byte[] _generateSnapshotClassData(
			Class<T> interfaceClass, Class<T> configurableClass,
			String snapshotClassName)
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
			Type returnType = Type.getType(method.getReturnType());

			FieldVisitor fieldVisitor = classWriter.visitField(
				Opcodes.ACC_PRIVATE + Opcodes.ACC_FINAL, method.getName(),
				returnType.getDescriptor(), null, null);

			fieldVisitor.visitEnd();
		}

		// Constructor

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
			constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			constructorMethodVisitor.visitVarInsn(Opcodes.ALOAD, 1);

			String methodName = method.getName();

			constructorMethodVisitor.visitMethodInsn(
				Opcodes.INVOKEVIRTUAL,
				_getClassBinaryName(configurableClass.getName()), methodName,
				Type.getMethodDescriptor(method), false);

			constructorMethodVisitor.visitFieldInsn(
				Opcodes.PUTFIELD, snapshotClassBinaryName, methodName,
				Type.getDescriptor(method.getReturnType()));
		}

		constructorMethodVisitor.visitInsn(Opcodes.RETURN);
		constructorMethodVisitor.visitMaxs(0, 0);
		constructorMethodVisitor.visitEnd();

		// Methods

		for (Method method : declaredMethods) {
			String methodName = method.getName();
			Class<?> returnTypeClass = method.getReturnType();

			MethodVisitor methodVisitor = classWriter.visitMethod(
				Opcodes.ACC_PUBLIC, methodName,
				Type.getMethodDescriptor(method), null, null);

			methodVisitor.visitCode();

			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);

			methodVisitor.visitFieldInsn(
				Opcodes.GETFIELD, snapshotClassBinaryName, methodName,
				Type.getDescriptor(returnTypeClass));

			Type returnType = Type.getType(returnTypeClass);

			methodVisitor.visitInsn(returnType.getOpcode(Opcodes.IRETURN));

			methodVisitor.visitMaxs(0, 0);
			methodVisitor.visitEnd();
		}

		classWriter.visitEnd();

		return classWriter.toByteArray();
	}

	private static String _getClassBinaryName(String className) {
		return className.replace(CharPool.PERIOD, CharPool.FORWARD_SLASH);
	}

}