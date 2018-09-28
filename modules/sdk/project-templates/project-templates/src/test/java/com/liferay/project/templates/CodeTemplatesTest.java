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

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		File actionCommandFile = new File(
			projectDir,
			"src/main/java/com/liferay/actioncommand/FooActionCommand.java");

		Assert.assertTrue(actionCommandFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/actioncommand/FooActionCommand.java",
			false, true, "mvc.command.name=foo");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

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

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

		codeTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		codeTemplatesArgs.setAuthor("fred");
		codeTemplatesArgs.setClassName("Foo");
		codeTemplatesArgs.setDestinationDir(projectDir);
		codeTemplatesArgs.setPackageName("com.liferay");
		codeTemplatesArgs.setTemplate("lifecycleaction");

		new CodeTemplates(codeTemplatesArgs);

		File lifecycelactionFile = new File(
			projectDir,
			"src/main/java/com/liferay/lifecycleaction/FooAction.java");

		Assert.assertTrue(lifecycelactionFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/lifecycleaction/FooAction.java", false,
			true, "FooAction login.events.pre=");

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

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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
	public void testGenerateCodeTemplateMVCPortlet() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

		projectTemplatesArgs.setArchetypesDirs(
			Collections.singletonList(archetypesDir));

		projectTemplatesArgs.setDestinationDir(destinationDir);
		projectTemplatesArgs.setLiferayVersion("7.1");
		projectTemplatesArgs.setName("foo");
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setTemplate("mvc-portlet");

		new ProjectTemplates(projectTemplatesArgs);

		File projectDir = new File(destinationDir, "foo");

		archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

		CodeTemplatesArgs codeTemplatesArgs = new CodeTemplatesArgs();

		codeTemplatesArgs.setAddtionalParameters(
			Collections.unmodifiableMap(
				Stream.of(
					new AbstractMap.SimpleEntry<>("portletName", "MVC")
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
		codeTemplatesArgs.setTemplate("mvcportlet");

		new CodeTemplates(codeTemplatesArgs);

		File mvcportletFile = new File(
			projectDir, "src/main/java/com/liferay/mvcportlet/FooPortlet.java");

		Assert.assertTrue(mvcportletFile.exists());

		_testContainsOrNot(
			projectDir, "src/main/java/com/liferay/mvcportlet/FooPortlet.java",
			false, true, "MVC Portlet");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateRenderCommand() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		File renderCommandFile = new File(
			projectDir,
			"src/main/java/com/liferay/rendercommand/FooMVCRenderCommand.java");

		Assert.assertTrue(renderCommandFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/rendercommand/FooMVCRenderCommand.java",
			false, true, "mvc.command.name=/foo/render");

		File renderPortletFile = new File(
			projectDir,
			"src/main/java/com/liferay/rendercommand/FooRenderPortlet.java");

		Assert.assertTrue(renderPortletFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/rendercommand/FooRenderPortlet.java",
			false, true, "RenderCommand Render Portlet");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateResourceCommand() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		File resourceCommandFile = new File(
			projectDir,
			"src/main/java/com/liferay/resourcecommand" +
				"/FooResourceCommand.java");

		Assert.assertTrue(resourceCommandFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/resourcecommand/FooResourceCommand.java",
			false, true, "mvc.command.name=/foo/captcha");

		File portletFile = new File(
			projectDir,
			"src/main/java/com/liferay/resourcecommand/FooPortlet.java");

		Assert.assertTrue(portletFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/resourcecommand/FooPortlet.java", false,
			true, "ResourceCommand Portlet");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateRest() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		new CodeTemplates(codeTemplatesArgs);

		File restFile = new File(
			projectDir, "src/main/java/com/liferay/rest/FooApplication.java");

		Assert.assertTrue(restFile.exists());

		_testContainsOrNot(
			projectDir, "src/main/java/com/liferay/rest/FooApplication.java",
			false, true, "fred");

		File modellistenerDir = new File(
			projectDir, "src/main/java/com/liferay/modellistener");

		Assert.assertFalse(modellistenerDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateSearchKeywordQueryContributor()
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		File searchKeywordFile = new File(
			projectDir,
			"src/main/java/com/liferay/searchkeywordquerycontributor" +
				"/FooKeywordQueryContributor.java");

		Assert.assertTrue(searchKeywordFile.exists());

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateSearchModelFilterContributor()
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		File searchKeywordModelPreFilterContributorFile = new File(
			projectDir,
			"src/main/java/com/liferay/searchmodelprefiltercontributor" +
				"/FooModelPreFilterContributor.java");

		Assert.assertTrue(searchKeywordModelPreFilterContributorFile.exists());

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateServiceWrapper() throws Exception {
		File destinationDir = temporaryFolder.newFolder("code");

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		File archetypesDir = FileUtil.getJarFile(CodeTemplatesTest.class);

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

		File serviceWrapperFile = new File(
			projectDir,
			"src/main/java/com/liferay/servicewrapper/FooServiceWrapper.java");

		Assert.assertTrue(serviceWrapperFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/servicewrapper/FooServiceWrapper.java",
			false, true, "extends UserLocalServiceWrapper");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
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