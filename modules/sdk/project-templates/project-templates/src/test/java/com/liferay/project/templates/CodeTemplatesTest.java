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
import com.liferay.project.templates.internal.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
public class CodeTemplatesTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		ProjectTemplatesTest.setUpClass();
	}

	@Test
	public void testGenerateCodeTemplateActionCommand() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setAddtionalParameters(
			Collections.unmodifiableMap(
				Stream.of(
					new AbstractMap.SimpleEntry<>(
						"portletName", "ActionCommand")
				).collect(
					Collectors.toMap(
						AbstractMap.SimpleEntry::getKey,
						AbstractMap.SimpleEntry::getValue)
				)));
		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));
		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("actioncommand");

		new CodeTemplates(codeTemplatesArgs);

		File javaDir = new File(projectDir, "src/main/java");

		File actionCommandFile = new File(
			javaDir, "com/liferay/actioncommand/FooActionCommand.java");

		Assert.assertTrue(actionCommandFile.exists());

		_testContainsOrNot(
			javaDir, "com/liferay/actioncommand/FooActionCommand.java", false,
			true, "mvc.command.name=foo");

		File restDir = new File(javaDir, "com/liferay/rest");

		Assert.assertFalse(restDir.exists());

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testGenerateCodeTemplateLifecycleAction() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("lifecycleaction");

		new CodeTemplates(codeTemplatesArgs);

		File javaDir = new File(projectDir, "src/main/java");

		File lifecycelactionFile = new File(
			javaDir, "com/liferay/lifecycleaction/FooAction.java");

		Assert.assertTrue(lifecycelactionFile.exists());

		_testContainsOrNot(
			javaDir, "com/liferay/lifecycleaction/FooAction.java", false, true,
			"FooAction login.events.pre=");

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testGenerateCodeTemplateModelListener() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setAddtionalParameters(
			Collections.unmodifiableMap(
				Stream.of(
					new AbstractMap.SimpleEntry<>(
						"modelClassPackage", "com.liferay.portal.kernel.model"),
					new AbstractMap.SimpleEntry<>("modelClassName", "Layout")
				).collect(
					Collectors.toMap(
						AbstractMap.SimpleEntry::getKey,
						AbstractMap.SimpleEntry::getValue)
				)));
		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));
		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("modellistener");

		new CodeTemplates(codeTemplatesArgs);

		File javaDir = new File(projectDir, "src/main/java");

		File modellistenerFile = new File(
			javaDir, "com/liferay/modellistener/FooModelListener.java");

		Assert.assertTrue(modellistenerFile.exists());

		_testContainsOrNot(
			javaDir, "com/liferay/modellistener/FooModelListener.java", false,
			true, "Layout");

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testGenerateCodeTemplateRenderCommand() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setAddtionalParameters(
			Collections.unmodifiableMap(
				Stream.of(
					new AbstractMap.SimpleEntry<>(
						"portletName", "RenderCommand")
				).collect(
					Collectors.toMap(
						AbstractMap.SimpleEntry::getKey,
						AbstractMap.SimpleEntry::getValue)
				)));
		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));
		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("rendercommand");

		new CodeTemplates(codeTemplatesArgs);

		File javaDir = new File(projectDir, "src/main/java");

		File renderCommandFile = new File(
			javaDir, "com/liferay/rendercommand/FooMVCRenderCommand.java");

		Assert.assertTrue(renderCommandFile.exists());

		_testContainsOrNot(
			javaDir, "com/liferay/rendercommand/FooMVCRenderCommand.java",
			false, true, "mvc.command.name=/rendercommand/foo");

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testGenerateCodeTemplateResourceCommand() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setAddtionalParameters(
			Collections.unmodifiableMap(
				Stream.of(
					new AbstractMap.SimpleEntry<>(
						"portletName", "ResourceCommand")
				).collect(
					Collectors.toMap(
						AbstractMap.SimpleEntry::getKey,
						AbstractMap.SimpleEntry::getValue)
				)));
		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));
		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("resourcecommand");

		new CodeTemplates(codeTemplatesArgs);

		File javaDir = new File(projectDir, "src/main/java");

		File mvcResourceCommandFile = new File(
			javaDir, "com/liferay/resourcecommand/FooMVCResourceCommand.java");

		Assert.assertTrue(mvcResourceCommandFile.exists());

		_testContainsOrNot(
			javaDir, "com/liferay/resourcecommand/FooMVCResourceCommand.java",
			false, true, "mvc.command.name=/resourcecommand/foo");

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			File buildGradle = new File(projectDir, "build.gradle");

			String content = new String(
				Files.readAllBytes(buildGradle.toPath()));

			String dependencies =
				"compileOnly group: \"com.liferay\", name: " +
					"\"com.liferay.captcha.api\", version: \"1.1.0\"\n" +
						"compileOnly group: \"org.osgi\", name: " +
							"\"osgi.core\", version: \"6.0.0\"\ncompileOnly";

			content = content.replaceFirst("compileOnly", dependencies);

			Files.write(buildGradle.toPath(), content.getBytes());

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testGenerateCodeTemplateRest() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));
		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("rest");

		File javaDir = new File(projectDir, "src/main/java");

		new CodeTemplates(codeTemplatesArgs);

		File restFile = new File(
			javaDir, "com/liferay/rest/FooApplication.java");

		Assert.assertTrue(restFile.exists());

		_testContainsOrNot(
			javaDir, "com/liferay/rest/FooApplication.java", false, true,
			"fred");

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			File buildGradle = new File(projectDir, "build.gradle");

			String content = new String(
				Files.readAllBytes(buildGradle.toPath()));

			String dependencies =
				"compileOnly group: \"javax.ws.rs\", name: " +
					"\"javax.ws.rs-api\", version: \"2.0.1\"\ncompileOnly";

			content = content.replaceFirst("compileOnly", dependencies);

			Files.write(buildGradle.toPath(), content.getBytes());

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testGenerateCodeTemplateSearchKeywordQueryContributor()
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));
		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("searchkeywordquerycontributor");

		new CodeTemplates(codeTemplatesArgs);

		File javaDir = new File(projectDir, "src/main/java");

		File searchKeywordFile = new File(
			javaDir,
			"com/liferay/searchkeywordquerycontributor" +
				"/FooKeywordQueryContributor.java");

		Assert.assertTrue(searchKeywordFile.exists());

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			File buildGradle = new File(projectDir, "build.gradle");

			String content = new String(
				Files.readAllBytes(buildGradle.toPath()));

			String dependencies =
				"compileOnly group: \"com.liferay\", name: " +
					"\"com.liferay.portal.search.api\", version: \"2.0.0\"\n" +
						"compileOnly group: \"com.liferay\", name: " +
							"\"com.liferay.portal.search.spi\",  version: " +
								"\"2.0.0\"\ncompileOnly";

			content = content.replaceFirst("compileOnly", dependencies);

			Files.write(buildGradle.toPath(), content.getBytes());

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testGenerateCodeTemplateSearchModelFilterContributor()
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));
		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("searchmodelprefiltercontributor");

		new CodeTemplates(codeTemplatesArgs);

		File javaDir = new File(projectDir, "src/main/java");

		File searchKeywordModelPreFilterContributorFile = new File(
			javaDir,
			"com/liferay/searchmodelprefiltercontributor" +
				"/FooModelPreFilterContributor.java");

		Assert.assertTrue(searchKeywordModelPreFilterContributorFile.exists());

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			File buildGradle = new File(projectDir, "build.gradle");

			String content = new String(
				Files.readAllBytes(buildGradle.toPath()));

			String dependencies =
				"compileOnly group: \"com.liferay\", name: " +
					"\"com.liferay.portal.search.api\", version: \"2.0.0\"\n" +
						"compileOnly group: \"com.liferay\", name: " +
							"\"com.liferay.portal.search.spi\", version: " +
								"\"2.0.0\"\ncompileOnly";

			content = content.replaceFirst("compileOnly", dependencies);

			Files.write(buildGradle.toPath(), content.getBytes());

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
	}

	@Test
	public void testGenerateCodeTemplateServiceWrapper() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		Path jarPath = FileUtil.getJarPath();

		File archetypesDir = jarPath.toFile();

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setAddtionalParameters(
			Collections.unmodifiableMap(
				Stream.of(
					new AbstractMap.SimpleEntry<>(
						"serviceWrapperClassName", "UserLocalServiceWrapper"),
					new AbstractMap.SimpleEntry<>(
						"serviceWrapperClassPackage",
						"com.liferay.portal.kernel.service")
				).collect(
					Collectors.toMap(
						AbstractMap.SimpleEntry::getKey,
						AbstractMap.SimpleEntry::getValue)
				)));
		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));
		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("servicewrapper");

		new CodeTemplates(codeTemplatesArgs);

		File javaDir = new File(projectDir, "src/main/java");

		File serviceWrapperFile = new File(
			javaDir, "com/liferay/servicewrapper/FooServiceWrapper.java");

		Assert.assertTrue(serviceWrapperFile.exists());

		_testContainsOrNot(
			javaDir, "com/liferay/servicewrapper/FooServiceWrapper.java", false,
			true, "extends UserLocalServiceWrapper");

		if (Validator.isNotNull(ProjectTemplatesTest.BUILD_PROJECTS) &&
			ProjectTemplatesTest.BUILD_PROJECTS.equals("true")) {

			ProjectTemplatesTest.executeGradle(
				projectDir, _GRADLE_TASK_PATH_BUILD);
		}
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

	private static final String _GRADLE_TASK_PATH_BUILD = ":build";

}