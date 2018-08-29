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

package com.liferay.project.templates;

import com.liferay.project.templates.internal.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Gregory Amerson
 */
public class CodeTemplatesTest {

	@Test
	public void testGenerateCodeTemplateModelListener() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(ProjectTemplatesTest.class);

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("--archetypes-dir");

		archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

		completeArgs.add(archetypesDir.getPath());

		completeArgs.add("--author");
		completeArgs.add("fred");

		completeArgs.add("--class-name");
		completeArgs.add("Foo");

		completeArgs.add("--destination");
		completeArgs.add(projectDir.getPath());

		completeArgs.add("-DmodelClassName=Layout");

		completeArgs.add("--package-name");
		completeArgs.add("com.liferay");

		completeArgs.add("--template");
		completeArgs.add("modellistener");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

		File modellistenerFile = new File(
			projectDir,
			"src/main/java/com/liferay/modellistener/FooModelListener.java");

		Assert.assertTrue(modellistenerFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/modellistener/FooModelListener.java",
			false, true, "Layout");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateRest() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(ProjectTemplatesTest.class);

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("--archetypes-dir");

		archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

		completeArgs.add(archetypesDir.getPath());

		completeArgs.add("--author");
		completeArgs.add("fred");

		completeArgs.add("--class-name");
		completeArgs.add("Foo");

		completeArgs.add("--destination");
		completeArgs.add(projectDir.getPath());

		completeArgs.add("--package-name");
		completeArgs.add("com.liferay");

		completeArgs.add("--template");
		completeArgs.add("rest");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

		File restFile = new File(
			projectDir,
			"src/main/java/com/liferay/rest/application/FooApplication.java");

		Assert.assertTrue(restFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/rest/application/FooApplication.java",
			false, true, "fred");

		File modellistenerDir = new File(
			projectDir, "src/main/java/com/liferay/modellistener");

		Assert.assertFalse(modellistenerDir.exists());
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static File _testContainsOrNot(
			File dir, String fileName, boolean regex, boolean contains,
			String... strings)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileUtil.read(file.toPath());

		for (String s : strings) {
			boolean found;

			if (regex) {
				Pattern pattern = Pattern.compile(
					s, Pattern.DOTALL | Pattern.MULTILINE);

				Matcher matcher = pattern.matcher(content);

				found = matcher.matches();
			}
			else {
				found = content.contains(s);
			}

			if (contains) {
				Assert.assertTrue("Not found in " + fileName + ": " + s, found);
			}
			else {
				Assert.assertFalse("Found in " + fileName + ": " + s, found);
			}
		}

		return file;
	}

	private static File _testExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue("Missing " + fileName, file.exists());

		return file;
	}

}