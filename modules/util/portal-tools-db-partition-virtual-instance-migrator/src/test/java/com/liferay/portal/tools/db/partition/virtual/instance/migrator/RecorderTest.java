/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migrator;

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
public class RecorderTest {

	@Before
	public void setUp() {
		System.setOut(new PrintStream(_byteArrayOutputStream));
	}

	@After
	public void tearDown() {
		System.setOut(_originalOut);
	}

	@Test
	public void testPrintingMessages() {
		Recorder recorder = new Recorder();

		recorder.registerWarning("A simple warning message");

		recorder.registerError("A simple error message");

		recorder.printMessages();

		Assert.assertTrue(recorder.hasErrors());
		Assert.assertTrue(recorder.hasWarnings());

		String string = _byteArrayOutputStream.toString();

		Assert.assertTrue(string.contains("[ERROR] A simple error message"));
		Assert.assertTrue(string.contains("[WARN] A simple warning message"));
		Assert.assertTrue(string.indexOf("[ERROR]") < string.indexOf("[WARN]"));
	}

	@Test
	public void testRegisterError() {
		Recorder recorder = new Recorder();

		recorder.registerError("A simple message");

		recorder.printMessages();

		Assert.assertTrue(recorder.hasErrors());
		Assert.assertFalse(recorder.hasWarnings());

		Assert.assertEquals(
			"[ERROR] A simple message\n", _byteArrayOutputStream.toString());
	}

	@Test
	public void testRegisterErrors() {
		Recorder recorder = new Recorder();

		List<String> modules = Arrays.asList("module1", "module2", "module3");

		recorder.registerErrors("simple message", modules);

		recorder.printMessages();

		Assert.assertTrue(recorder.hasErrors());
		Assert.assertFalse(recorder.hasWarnings());

		String string = _byteArrayOutputStream.toString();

		for (String module : modules) {
			Assert.assertTrue(
				string.contains(
					"[ERROR] Module " + module + " simple message"));
		}
	}

	@Test
	public void testRegisterWarning() {
		Recorder recorder = new Recorder();

		recorder.registerWarning("A simple message");

		recorder.printMessages();

		Assert.assertFalse(recorder.hasErrors());
		Assert.assertTrue(recorder.hasWarnings());

		Assert.assertEquals(
			"[WARN] A simple message\n", _byteArrayOutputStream.toString());
	}

	@Test
	public void testRegisterWarnings() {
		Recorder recorder = new Recorder();

		List<String> modules = Arrays.asList("module1", "module2", "module3");

		recorder.registerWarnings("simple message", modules);

		recorder.printMessages();

		Assert.assertFalse(recorder.hasErrors());
		Assert.assertTrue(recorder.hasWarnings());

		String outputString = _byteArrayOutputStream.toString();

		for (String module : modules) {
			Assert.assertTrue(
				outputString.contains(
					"[WARN] Module " + module + " simple message"));
		}
	}

	private final ByteArrayOutputStream _byteArrayOutputStream =
		new ByteArrayOutputStream();
	private final PrintStream _originalOut = System.out;

}