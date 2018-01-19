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

package com.liferay.petra.process;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class PathHolderTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testEquals() {
		PathHolder pathHolder = new PathHolder(Paths.get("testFile"));

		Assert.assertTrue(pathHolder.equals(pathHolder));
		Assert.assertFalse(pathHolder.equals(new Object()));
		Assert.assertTrue(
			pathHolder.equals(new PathHolder(Paths.get("testFile"))));
		Assert.assertFalse(
			pathHolder.equals(new PathHolder(Paths.get("anotherFile"))));
	}

	@Test
	public void testGetPath() {
		Path path = Paths.get("testFile");

		PathHolder pathHolder = new PathHolder(path);

		Assert.assertEquals(path, pathHolder.getPath());
	}

	@Test
	public void testHashCode() {
		Path path = Paths.get("testFile");

		String pathString = path.toString();

		PathHolder pathHolder = new PathHolder(path);

		Assert.assertEquals(pathString.hashCode(), pathHolder.hashCode());
	}

	@Test
	public void testToString() {
		Path path = Paths.get("testFile");

		PathHolder pathHolder = new PathHolder(path);

		String toString = pathHolder.toString();

		Assert.assertEquals(path.toString(), toString);
		Assert.assertEquals(
			ReflectionTestUtil.getFieldValue(pathHolder, "_pathString"),
			toString);
		Assert.assertSame(toString, path.toString());
	}

	@Test
	public void testToStringSwitchSeparator() {
		PathHolder pathHolder = new PathHolder(Paths.get(""));

		char foreignSeparatorChar = CharPool.SLASH;

		char separatorChar = File.separatorChar;

		if (separatorChar == CharPool.SLASH) {
			foreignSeparatorChar = CharPool.BACK_SLASH;
		}

		ReflectionTestUtil.setFieldValue(
			pathHolder, "_pathString",
			"testFolder" + foreignSeparatorChar + "testFile");

		char originalSeparatorChar = ReflectionTestUtil.getAndSetFieldValue(
			pathHolder, "_SEPARATOR_CHAR", foreignSeparatorChar);

		try {
			Assert.assertEquals(
				"testFolder" + separatorChar + "testFile",
				pathHolder.toString());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				pathHolder, "_SEPARATOR_CHAR", originalSeparatorChar);
		}
	}

}