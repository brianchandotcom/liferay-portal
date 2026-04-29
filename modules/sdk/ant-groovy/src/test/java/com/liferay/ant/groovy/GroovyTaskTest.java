/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.groovy;

import java.lang.reflect.Field;

import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Kenji Heigel
 */
public class GroovyTaskTest {

	@Before
	public void setUp() throws Exception {
		_project = new Project();

		_project.init();

		Field mapsField = GroovyTask.class.getDeclaredField("_maps");

		mapsField.setAccessible(true);

		Map<?, ?> maps = (Map<?, ?>)mapsField.get(null);

		maps.clear();
	}

	@Test
	public void testCatchesCompileStaticTypo() throws Exception {
		GroovyTask groovyTask = _newGroovyTask();

		groovyTask.addText(
			"String s = \"hello\"\n// Method does not exist on String\n" +
				"s.doesNotExist()\n");

		try {
			groovyTask.execute();

			Assert.fail("Expected BuildException for unknown method");
		}
		catch (BuildException buildException) {
			String message = buildException.getMessage();

			Assert.assertTrue(
				"Expected compile failure to mention missing method, got: " +
					message,
				message.contains("doesNotExist"));
		}
	}

	@Test
	public void testCatchesUnresolvedImport() throws Exception {
		GroovyTask groovyTask = _newGroovyTask();

		groovyTask.addText(
			"import com.does.not.exist.Bogus\n\nBogus b = new Bogus()\n");

		try {
			groovyTask.execute();

			Assert.fail("Expected BuildException for unresolved import");
		}
		catch (BuildException buildException) {
			String message = buildException.getMessage();

			Assert.assertTrue(
				"Expected compile failure to mention unresolved class, got: " +
					message,
				message.contains("Bogus") ||
				message.contains("unable to resolve"));
		}
	}

	@Test
	public void testCompileOnlySkipsExecution() throws Exception {
		GroovyTask groovyTask = _newGroovyTask();

		groovyTask.setCompileOnly(true);
		groovyTask.addText(
			"project.setProperty(\"should.not.set\", \"true\")\n" +
				"throw new IllegalStateException(\"must not run\")\n");

		groovyTask.execute();

		Assert.assertNull(_project.getProperty("should.not.set"));
	}

	@Test
	public void testCompileOnlyStillCatchesCompileErrors() throws Exception {
		GroovyTask groovyTask = _newGroovyTask();

		groovyTask.setCompileOnly(true);
		groovyTask.addText("String s = \"x\"\ns.doesNotExist()\n");

		try {
			groovyTask.execute();

			Assert.fail("Expected BuildException during compile-only");
		}
		catch (BuildException buildException) {
			String message = buildException.getMessage();

			Assert.assertTrue(message.contains("doesNotExist"));
		}
	}

	@Test
	public void testInlineMethodDeclaration() throws Exception {
		GroovyTask groovyTask = _newGroovyTask();

		groovyTask.addText(
			"public String greet(String name) {\n\treturn \"hi \" + name\n}\n" +
				"\nproject.setProperty(\"greeting\", greet(\"world\"))\n");

		groovyTask.execute();

		Assert.assertEquals("hi world", _project.getProperty("greeting"));
	}

	@Test
	public void testInlineMethodUsesStaticFields() throws Exception {
		GroovyTask groovyTask = _newGroovyTask();

		groovyTask.setMapId("ns");
		groovyTask.addText(
			"public String readMap(String key) {\n" +
				"\treturn (String)groovyMap.get(key)\n}\n" +
					"\npublic void writeMap(String key, String value) {\n" +
						"\tgroovyMap.put(key, value)\n}\n\n" +
							"writeMap(\"k\", \"v\")\nproject.setProperty(" +
								"\"echoed\", readMap(\"k\"))\n");

		groovyTask.execute();

		Assert.assertEquals("v", _project.getProperty("echoed"));
	}

	@Test
	public void testMapIdSharesStateAcrossInvocations() throws Exception {
		GroovyTask writer = _newGroovyTask();

		writer.setMapId("shared");
		writer.addText("groovyMap.put(\"k\", \"v\")\n");

		writer.execute();

		GroovyTask reader = _newGroovyTask();

		reader.setMapId("shared");
		reader.addText(
			"String value = (String)groovyMap.get(\"k\")\n" +
				"project.setProperty(\"probed\", value)\n");

		reader.execute();

		Assert.assertEquals("v", _project.getProperty("probed"));
	}

	@Test
	public void testProjectPropertyRoundTrip() throws Exception {
		_project.setProperty("env.GREETING", "hello");

		GroovyTask groovyTask = _newGroovyTask();

		groovyTask.addText(
			"String greeting = project.getProperty(\"env.GREETING\")\n" +
				"project.setProperty(\"echoed\", greeting + \" world\")\n");

		groovyTask.execute();

		Assert.assertEquals("hello world", _project.getProperty("echoed"));
	}

	@Test
	public void testSimpleScriptRuns() throws Exception {
		GroovyTask groovyTask = _newGroovyTask();

		groovyTask.addText("project.setProperty(\"ran\", \"true\")\n");

		groovyTask.execute();

		Assert.assertEquals("true", _project.getProperty("ran"));
	}

	@Test
	public void testUnsharedMapsPerMapId() throws Exception {
		GroovyTask writerA = _newGroovyTask();

		writerA.setMapId("A");
		writerA.addText("groovyMap.put(\"k\", \"from-A\")\n");

		writerA.execute();

		GroovyTask readerB = _newGroovyTask();

		readerB.setMapId("B");
		readerB.addText(
			"Object value = groovyMap.get(\"k\")\n" +
				"project.setProperty(\"probedB\", String.valueOf(value))\n");

		readerB.execute();

		Assert.assertEquals("null", _project.getProperty("probedB"));
	}

	private GroovyTask _newGroovyTask() {
		GroovyTask groovyTask = new GroovyTask();

		groovyTask.setProject(_project);

		return groovyTask;
	}

	private Project _project;

}