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

package com.liferay.portal.tools.db.virtual.instance.migration.internal.validation;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Luis Ortiz
 */
public class ValidatorRecorderTest {

	@Before
	public void setUp() {
		System.setOut(new PrintStream(_testOutByteArrayOutputStream));
	}

	@After
	public void tearDown() {
		System.setOut(_originalOut);
	}

	@Test
	public void testError() {
		ValidatorRecorder recorder = new ValidatorRecorder();

		recorder.registerError("A simple message");

		recorder.printMessages();

		Assert.assertFalse(recorder.hasRegisteredWarnings());
		Assert.assertTrue(recorder.hasRegisteredErrors());

		Assert.assertEquals(
			"ERROR: A simple message\n",
			_testOutByteArrayOutputStream.toString());
	}

	@Test
	public void testMultipleError() {
		ValidatorRecorder recorder = new ValidatorRecorder();

		List<String> modules = Arrays.asList("module1", "module2", "module3");

		recorder.registerErrors(modules, "simple message");

		recorder.printMessages();

		Assert.assertFalse(recorder.hasRegisteredWarnings());
		Assert.assertTrue(recorder.hasRegisteredErrors());

		String outputString = _testOutByteArrayOutputStream.toString();

		for (String module : modules) {
			Assert.assertTrue(
				outputString.contains(
					"ERROR: Module " + module + " simple message"));
		}
	}

	@Test
	public void testMultipleWarning() {
		ValidatorRecorder recorder = new ValidatorRecorder();

		List<String> modules = Arrays.asList("module1", "module2", "module3");

		recorder.registerWarnings(modules, "simple message");

		recorder.printMessages();

		Assert.assertTrue(recorder.hasRegisteredWarnings());
		Assert.assertFalse(recorder.hasRegisteredErrors());

		String outputString = _testOutByteArrayOutputStream.toString();

		for (String module : modules) {
			Assert.assertTrue(
				outputString.contains(
					"WARNING: Module " + module + " simple message"));
		}
	}

	@Test
	public void testPrintingOrder() {
		ValidatorRecorder recorder = new ValidatorRecorder();

		recorder.registerWarning("A simple warning message");
		recorder.registerError("A simple error message");
		recorder.printMessages();

		Assert.assertTrue(recorder.hasRegisteredWarnings());
		Assert.assertTrue(recorder.hasRegisteredErrors());

		String outputString = _testOutByteArrayOutputStream.toString();

		Assert.assertTrue(
			outputString.contains("WARNING: A simple warning message"));
		Assert.assertTrue(
			outputString.contains("ERROR: A simple error message"));

		Assert.assertTrue(
			outputString.indexOf("ERROR:") < outputString.indexOf("WARNING:"));
	}

	@Test
	public void testWarning() {
		ValidatorRecorder recorder = new ValidatorRecorder();

		recorder.registerWarning("A simple message");
		recorder.printMessages();

		Assert.assertTrue(recorder.hasRegisteredWarnings());
		Assert.assertFalse(recorder.hasRegisteredErrors());

		Assert.assertEquals(
			"WARNING: A simple message\n",
			_testOutByteArrayOutputStream.toString());
	}

	private final PrintStream _originalOut = System.out;
	private final ByteArrayOutputStream _testOutByteArrayOutputStream =
		new ByteArrayOutputStream();

}