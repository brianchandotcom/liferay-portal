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

package com.liferay.semantic.versioning;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Matthew Tambara
 */
@RunWith(Parameterized.class)
public class SemanticVersioningLogTest {

	@Parameterized.Parameters(name = "{0}")
	public static List<String> sampleData() {
		String modulesTestClassGroup = System.getProperty(
			"modules.test.class.group");

		Assert.assertFalse(
			"Missing system property \"modules.test.class.group\"",
			Validator.isNull(modulesTestClassGroup));

		Assert.assertFalse(
			"Missing system property \"project.dir\"",
			Validator.isNull(System.getProperty("project.dir")));

		modulesTestClassGroup = modulesTestClassGroup.replaceAll(
			StringPool.COLON, StringPool.SLASH);

		modulesTestClassGroup = modulesTestClassGroup.replaceAll(
			"/baseline", StringPool.BLANK);

		return StringUtil.split(modulesTestClassGroup, CharPool.SPACE);
	}

	public SemanticVersioningLogTest(String module) {
		_module = module;
	}

	@Test
	public void testSemanticVersioning() throws IOException {
		AtomicReference<Path> logPath = new AtomicReference<>();

		Files.walkFileTree(
			Paths.get(System.getProperty("project.dir"), "modules", _module),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					File file = path.toFile();

					if (Objects.equals(file.getName(), "baseline.log")) {
						logPath.set(path);

						return FileVisitResult.TERMINATE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		String message = null;

		Path path = logPath.get();

		if (path != null) {
			List<String> lines = Files.readAllLines(path);

			StringBundler sb = new StringBundler(lines.size() * 2);

			for (String line : lines) {
				sb.append(line);
				sb.append(StringPool.NEW_LINE);
			}

			message = sb.toString();
		}

		Assert.assertNull(message, path);
	}

	private final String _module;

}