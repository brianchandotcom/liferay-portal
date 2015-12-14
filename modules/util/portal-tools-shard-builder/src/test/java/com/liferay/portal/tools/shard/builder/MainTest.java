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

package com.liferay.portal.tools.shard.builder;

import java.io.File;
import java.io.FileNotFoundException;

import java.net.URL;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Manuel de la Peña
 */
public class MainTest {

	@Test
	public void testValidateEmptyArguments() throws Exception {
		try {
			Main.main(new String[0]);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Arguments cannot be null", iae.getMessage());
		}
	}

	@Test(expected = FileNotFoundException.class)
	public void testValidateNonExistingDatabaseFile() throws Exception {
		String[] args = {
			"-P", "foobar.properties", "-S", _DEFAULT_SCHEMA_NAME, "-C",
			_DEFAULT_COMPANY_ID, "-O", "neverMindPath"
		};

		Main.main(args);
	}

	@Test(expected = FileNotFoundException.class)
	public void testValidateNonExistingOutputFolder() throws Exception {
		URL url = getClass().getResource("/mysql.properties");

		String[] args = {
			"-P", url.getFile(), "-S", _DEFAULT_SCHEMA_NAME, "-C",
			_DEFAULT_COMPANY_ID, "-O", "foo"
		};

		Main.main(args);
	}

	@Test
	public void testValidateNonValidCompanyId() throws Exception {
		URL url = getClass().getResource("/mysql.properties");

		String[] args = {
			"-P", url.getFile(), "-S", _DEFAULT_SCHEMA_NAME, "-C", "foo", "-O",
			"neverMindPath"
		};

		try {
			Main.main(args);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"CompanyID is not a valid number", iae.getMessage());
		}
	}

	@Test
	public void testValidateNullArguments() throws Exception {
		try {
			Main.main(null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Arguments cannot be null", iae.getMessage());
		}
	}

	@Test
	public void testValidateReadOnlyOutputFolder() throws Exception {
		URL url = getClass().getResource("/mysql.properties");

		Assume.assumeNotNull(url);

		File readOnlyFolder = temporaryFolder.newFolder();

		readOnlyFolder.setReadable(false);
		readOnlyFolder.setWritable(false);

		String[] args = {
			"-P", url.getFile(), "-S", _DEFAULT_SCHEMA_NAME, "-C",
			_DEFAULT_COMPANY_ID, "-O", readOnlyFolder.getAbsolutePath()
		};

		try {
			Main.main(args);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Output directory is read-only", iae.getMessage());
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static final String _DEFAULT_COMPANY_ID = "20156";

	private static final String _DEFAULT_SCHEMA_NAME = "lportal";

}