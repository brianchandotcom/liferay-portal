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

package com.liferay.portal.service;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ServiceXMLTest {

	@Test
	public void testTXRequired() throws Exception {
		Path portalPath = Paths.get(System.getProperty("user.dir"));

		Files.walkFileTree(
			portalPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path fileNamePath = path.getFileName();

					String fileName = fileNamePath.toString();

					if (fileName.equals("service.xml")) {
						_assertNoTXRequiredElement(path.toFile());

						return FileVisitResult.SKIP_SIBLINGS;
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _assertNoTXRequiredElement(File file) throws IOException {
		try (FileInputStream fileInputStream = new FileInputStream(file);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(
					new InputStreamReader(fileInputStream))) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				Assert.assertFalse(
					"Remove deprecated tx-required element from " +
						file.getPath(),
					line.contains("<tx-required>"));
			}
		}
	}

}