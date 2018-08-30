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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Gregory Amerson
 * @author Simon Jiang
 */
public class CodeTemplatesTest {

	@Test
	public void testGenerateCodeTemplateActionCommand() throws Exception {
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
		completeArgs.add("actioncommand");

		completeArgs.add("-DportletName=ActionCommand");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

		File actionCommandFile = new File(
			projectDir,
			"src/main/java/com/liferay/actioncommand/FooActionCommand.java");

		Assert.assertTrue(actionCommandFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/actioncommand/FooActionCommand.java",
			false, true, "ActionCommand Portlet");

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/actioncommand/FooActionCommand.java",
			false, true, "mvc.command.name=foo");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateConfigurationaction() throws Exception {
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
		completeArgs.add("configurationaction");
		
		completeArgs.add("-DportletName=Configurationaction");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

		File portletKeysFile = new File(
			projectDir,
			"src/main/java/com/liferay/configurationaction/constants" +
				"/FooPortletKeys.java");

		Assert.assertTrue(portletKeysFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/configurationaction/FooPortlet.java",
			false, true, "javax.portlet.display-name=Configurationaction Portlet");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

	@Test
	public void testGenerateCodeTemplateLifecycleAction() throws Exception {
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
		completeArgs.add("lifecycleaction");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

		File lifecycelactionFile = new File(
			projectDir,
			"src/main/java/com/liferay/lifecycleaction/FooAction.java");

		Assert.assertTrue(lifecycelactionFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/lifecycleaction/FooAction.java", false,
			true, "Foo Action");

		File restDir = new File(projectDir, "src/main/java/com/liferay/rest");

		Assert.assertFalse(restDir.exists());
	}

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

		completeArgs.add("-DmodelClassPackage=com.liferay.portal.kernel.model");
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
	public void testGenerateCodeTemplateMVCPortlet() throws Exception {
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
		completeArgs.add("mvcportlet");

		completeArgs.add("-DportletName=MVC");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

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
		completeArgs.add("rendercommand");
		
		completeArgs.add("-DportletName=RenderCommand");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

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
		completeArgs.add("resourcecommand");
		
		completeArgs.add("-DportletName=ResourceCommand");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

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
			"src/main/java/com/liferay/rest/FooApplication.java");

		Assert.assertTrue(restFile.exists());

		_testContainsOrNot(
			projectDir,
			"src/main/java/com/liferay/rest/FooApplication.java",
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
		completeArgs.add("searchkeywordquerycontributor");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

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
		completeArgs.add("searchmodelprefiltercontributor");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

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

		completeArgs.add("-DserviceWrapperClassName=UserLocalServiceWrapper");
		completeArgs.add(
			"-DserviceWrapperClassPackage=com.liferay.portal.kernel.service");

		completeArgs.add("--package-name");
		completeArgs.add("com.liferay");

		completeArgs.add("--template");
		completeArgs.add("servicewrapper");

		CodeTemplates.main(completeArgs.toArray(new String[0]));

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

}