/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ant.groovy;

import java.io.File;

import java.nio.file.Files;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Kenji Heigel
 */
public class GroovyValidateFileTaskTest {

	@Before
	public void setUp() throws Exception {
		_project = new Project();

		_project.init();
	}

	@Test
	public void testCatchesCompileError() throws Exception {
		File xmlFile = _writeFile(
			"<project>\n<groovy>\n<![CDATA[\nString s = \"x\";\n" +
				"s.doesNotExist();\n]]>\n</groovy>\n</project>",
			"compile-error.xml");

		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(xmlFile);

		try {
			groovyValidateFileTask.execute();

			Assert.fail("Expected BuildException for compile error");
		}
		catch (BuildException buildException) {
			String message = buildException.getMessage();

			Assert.assertTrue(
				"Expected message to mention failure, got: " + message,
				message.contains("Groovy validation failed"));
		}
	}

	@Test
	public void testCatchesMissingSemicolon() throws Exception {
		File xmlFile = _writeFile(
			"<project>\n<groovy>\n<![CDATA[\nString s = \"no semicolon\"\n" +
				"]]>\n</groovy>\n</project>",
			"missing-semicolon.xml");

		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(xmlFile);

		try {
			groovyValidateFileTask.execute();

			Assert.fail("Expected BuildException for missing semicolon");
		}
		catch (BuildException buildException) {
			String message = buildException.getMessage();

			Assert.assertTrue(
				"Expected message to mention failure, got: " + message,
				message.contains("Groovy validation failed"));
		}
	}

	@Test
	public void testCatchesUnescapedDollarBraceInterpolation()
		throws Exception {

		File xmlFile = _writeFile(
			"<project>\n<groovy>\n<![CDATA[\n" +
				"String url = \"https://ci/${jobName}/build\";\n" +
					"]]>\n</groovy>\n</project>",
			"unescaped-dollar-brace.xml");

		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(xmlFile);

		try {
			groovyValidateFileTask.execute();

			Assert.fail("Expected BuildException for unescaped GString");
		}
		catch (BuildException buildException) {
			String message = buildException.getMessage();

			Assert.assertTrue(
				"Expected message to mention failure, got: " + message,
				message.contains("Groovy validation failed"));
		}
	}

	@Test
	public void testMissingFileThrows() throws Exception {
		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(new File("/does/not/exist.xml"));

		try {
			groovyValidateFileTask.execute();

			Assert.fail("Expected BuildException for missing file");
		}
		catch (BuildException buildException) {
			String message = buildException.getMessage();

			Assert.assertTrue(message.contains("non-existent"));
		}
	}

	@Test
	public void testNoFileNorFilesetThrows() throws Exception {
		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		try {
			groovyValidateFileTask.execute();

			Assert.fail("Expected BuildException for no input");
		}
		catch (BuildException buildException) {
			String message = buildException.getMessage();

			Assert.assertTrue(message.contains("Specify"));
		}
	}

	@Test
	public void testToleratesBareDollarIdentifier() throws Exception {
		File xmlFile = _writeFile(
			"<project>\n<groovy>\n<![CDATA[\nString s = \"java.lang.String" +
				"$CaseInsensitiveComparator\";\n]]>\n</groovy>\n</project>",
			"bare-dollar-identifier.xml");

		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(xmlFile);

		groovyValidateFileTask.execute();
	}

	@Test
	public void testToleratesBareDollarOutsideInterpolation() throws Exception {
		File xmlFile = _writeFile(
			"<project>\n<groovy>\n<![CDATA[\nString s = \"x\".replaceAll(" +
				"\"a\", \"$1\");\nString literal = \"$\";\n]]>\n</groovy>\n" +
					"</project>",
			"bare-dollar.xml");

		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(xmlFile);

		groovyValidateFileTask.execute();
	}

	@Test
	public void testToleratesEscapedDollarIdentifier() throws Exception {
		File xmlFile = _writeFile(
			"<project>\n<groovy>\n<![CDATA[\nString s = \"prefix-\\$jobName" +
				"\";\n]]>\n</groovy>\n</project>",
			"escaped-dollar.xml");

		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(xmlFile);

		groovyValidateFileTask.execute();
	}

	@Test
	public void testValidFileSucceeds() throws Exception {
		File xmlFile = _writeFile(
			"<project>\n<groovy>\n<![CDATA[\nproject.setProperty(\"x\", " +
				"\"y\");\n]]>\n</groovy>\n</project>",
			"valid.xml");

		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(xmlFile);

		groovyValidateFileTask.execute();
	}

	@Test
	public void testXmlWithoutGroovyBlocksSucceeds() throws Exception {
		File xmlFile = _writeFile("<project></project>", "no-groovy.xml");

		GroovyValidateFileTask groovyValidateFileTask =
			_newGroovyValidateFileTask();

		groovyValidateFileTask.setFile(xmlFile);

		groovyValidateFileTask.execute();
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private GroovyValidateFileTask _newGroovyValidateFileTask() {
		GroovyValidateFileTask groovyValidateFileTask =
			new GroovyValidateFileTask();

		groovyValidateFileTask.setProject(_project);

		return groovyValidateFileTask;
	}

	private File _writeFile(String content, String name) throws Exception {
		File file = temporaryFolder.newFile(name);

		Files.write(file.toPath(), content.getBytes());

		return file;
	}

	private Project _project;

}