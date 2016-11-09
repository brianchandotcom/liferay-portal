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

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * @author Tom Wang
 */
public class BytecodeTransformer {

	public static void main(String[] args) throws IOException {
		Files.walkFileTree(
			Paths.get(System.getProperty("classes.dir")),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(filePath.getFileName());

					if (fileName.endsWith("ServiceWrapper.class") ||
						fileName.endsWith("ServiceUtil.class") ||
						!fileName.endsWith(".class")) {

						return FileVisitResult.CONTINUE;
					}

					_transform(filePath);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _transform(Path path) throws IOException {
		ClassReader classReader = new ClassReader(Files.readAllBytes(path));

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

		BytecodeTransformerClassVisitor bytecodeTransformerClassVisitor =
			new BytecodeTransformerClassVisitor(classWriter);

		classReader.accept(bytecodeTransformerClassVisitor, 0);

		DataOutputStream dataOutputStream = new DataOutputStream(
			new FileOutputStream(path.toFile()));

		dataOutputStream.write(classWriter.toByteArray());
	}

}