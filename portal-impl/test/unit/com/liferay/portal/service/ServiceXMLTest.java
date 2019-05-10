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

import java.io.IOException;

import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Objects;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class ServiceXMLTest {

	@Test
	public void testTXRequired() throws Exception {
		Stream<Path> stream = Files.find(
			Paths.get(System.getProperty("user.dir")), Integer.MAX_VALUE,
			ServiceXMLTest::_isServiceXml, FileVisitOption.FOLLOW_LINKS);

		stream.forEach(ServiceXMLTest::_assertNoTXRequiredElement);
	}

	private static void _assertNoTXRequiredElement(Path path) {
		try {
			Stream<String> stream = Files.lines(path);

			Assert.assertFalse(
				"Remove deprecated tx-required element from " + path,
				stream.anyMatch(line -> line.contains("<tx-required>")));
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	private static boolean _isServiceXml(
		Path path, BasicFileAttributes basicFileAttributes) {

		Path fileNamePath = path.getFileName();

		if (Objects.equals(fileNamePath.toString(), "service.xml")) {
			return true;
		}

		return false;
	}

}