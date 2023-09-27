/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import java.io.IOException;
import java.io.InputStream;

import java.util.function.Consumer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author Tina Tian
 * @author André de Oliveira
 */
public class ClassModificationUtil {

	public static byte[] getModifiedClassBytes(
			String className, String methodName,
			Consumer<MethodVisitor> methodVisitorConsumer,
			ClassLoader classLoader)
		throws ClassNotFoundException, IOException {

		Class<?> clazz = classLoader.loadClass(className);

		try (InputStream inputStream = clazz.getResourceAsStream(
				clazz.getSimpleName() + ".class")) {

			ClassReader classReader = new ClassReader(inputStream);

			ClassWriter classWriter = new ClassWriter(
				classReader, ClassWriter.COMPUTE_MAXS);

			classReader.accept(
				new ClassVisitor(Opcodes.ASM5, classWriter) {

					@Override
					public MethodVisitor visitMethod(
						int access, String name, String description,
						String signature, String[] exceptions) {

						MethodVisitor methodVisitor = super.visitMethod(
							access, name, description, signature, exceptions);

						if (!name.equals(methodName)) {
							return methodVisitor;
						}

						return new MethodVisitor(Opcodes.ASM5) {

							@Override
							public void visitCode() {
								methodVisitorConsumer.accept(methodVisitor);
							}

							@Override
							public void visitMaxs(int maxStack, int maxLocals) {
								methodVisitor.visitMaxs(0, 0);
							}

						};
					}

				},
				0);

			return classWriter.toByteArray();
		}
	}

}