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

import com.beust.jcommander.ParameterException;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Manuel de la Peña
 */
public class MainTest {

	@Test(expected = IllegalArgumentException.class)
	public void testValidateEmptyArguments() throws Exception {
		Main.main(new String[0]);
	}

	@Test(expected = ParameterException.class)
	public void testValidateNonExistingDatabaseFile() throws Exception {
		String[] args = {
			"-P", "foobar.properties", "-S", _DEFAULT_SCHEMA_NAME, "-C",
			_DEFAULT_COMPANY_ID, "-O", "neverMindPath"
		};

		Main.main(args);
	}

	@Test(expected = ParameterException.class)
	public void testValidateNonExistingOutputFolder() throws Exception {
		String[] args = {
			"-P", "foo.properties", "-S", _DEFAULT_SCHEMA_NAME, "-C",
			_DEFAULT_COMPANY_ID, "-O", "foo"
		};

		Main.main(args);
	}

	@Test(expected = ParameterException.class)
	public void testValidateNonValidCompanyId() throws Exception {
		String[] args = {
			"-P", "foo.properties", "-S", _DEFAULT_SCHEMA_NAME, "-C", "foo",
			"-O", "neverMindPath"
		};

		Main.main(args);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testValidateNullArguments() throws Exception {
		Main.main(null);
	}

	@Test(expected = ParameterException.class)
	public void testValidateReadOnlyOutputFolder() throws Exception {
		File readOnlyFolder = temporaryFolder.newFolder();

		readOnlyFolder.setReadable(false);
		readOnlyFolder.setWritable(false);

		String[] args = {
			"-P", "foo.properties", "-S", _DEFAULT_SCHEMA_NAME, "-C",
			_DEFAULT_COMPANY_ID, "-O", readOnlyFolder.getAbsolutePath()
		};

		Main.main(args);
	}

	@Test
	public void testValidateRequiredArguments() throws Exception {
		String[][] requiredArguments = {
			{"-C", ""}, {"-O", ""}, {"-P", ""}, {"-S", ""}
		};

		for (String[] requiredArgument : requiredArguments) {
			try {
				Main.main(requiredArgument);
			}
			catch (ParameterException pe) {
			}
		}
	}

	@Test(expected = ParameterException.class)
	public void testValidateWrongOptionArguments() throws Exception {
		Main.main(new String[] {"-X", "arg"});
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static final String _DEFAULT_COMPANY_ID = "20156";

	private static final String _DEFAULT_SCHEMA_NAME = "lportal";

}