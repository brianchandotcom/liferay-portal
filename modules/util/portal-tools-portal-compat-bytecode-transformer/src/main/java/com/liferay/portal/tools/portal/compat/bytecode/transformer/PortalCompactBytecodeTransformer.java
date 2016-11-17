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

import java.io.IOException;
import java.io.OutputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

/**
 * @author Tong Wang
 */
public class PortalCompactBytecodeTransformer {

	public static void main(String[] args) throws IOException {
		Path classesDir = Paths.get(System.getProperty("classes.dir"));

		if (!Files.exists(classesDir)) {
			return;
		}

		Files.walkFileTree(
			classesDir,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(filePath.getFileName());

					if (fileName.endsWith(".class") &&
						!fileName.endsWith("ServiceUtil.class") &&
						!fileName.endsWith("ServiceWrapper.class")) {

						_transform(filePath);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static void _transform(Path path) throws IOException {
		ClassReader classReader = new ClassReader(Files.readAllBytes(path));

		ClassWriter classWriter = new ClassWriter(
			classReader, ClassWriter.COMPUTE_FRAMES);

		classReader.accept(new PortalCompactClassVisitor(classWriter), 0);

		try (OutputStream outputStream = Files.newOutputStream(path)) {
			outputStream.write(classWriter.toByteArray());
		}
	}

}