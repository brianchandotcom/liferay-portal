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

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.service.builder.BuildServiceTask;
import com.liferay.gradle.plugins.tasks.DirectDeployTask;
import com.liferay.gradle.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;
import com.liferay.gradle.util.copy.RenameDependencyClosure;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DuplicatesStrategy;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileCopyDetails;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.RelativePath;
import org.gradle.api.internal.file.copy.CopySpecInternal;
import org.gradle.api.internal.file.copy.CopySpecResolver;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskInputs;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayWebAppPlugin extends LiferayJavaPlugin {

	public static final String DIRECT_DEPLOY_TASK_NAME = "directDeploy";

	@Override
	public void apply(Project project) {
		super.apply(project);

		configureDependencies(project);
	}

	protected Task addTaskBuildServiceCompile(
		BuildServiceTask buildServiceTask) {

		Project project = buildServiceTask.getProject();

		JavaCompile javaCompile = GradleUtil.addTask(
			project, buildServiceTask.getName() + "Compile", JavaCompile.class);

		javaCompile.dependsOn(buildServiceTask);
		javaCompile.dependsOn(
			BasePlugin.CLEAN_TASK_NAME +
				StringUtil.capitalize(javaCompile.getName()));

		FileCollection fileCollection = buildServiceTask.getClasspath();

		final File serviceJarFile = getServiceJarFile(project);

		fileCollection = fileCollection.filter(
			new Spec<File>() {

				@Override
				public boolean isSatisfiedBy(File file) {
					if (file.equals(serviceJarFile)) {
						return false;
					}

					return true;
				}

			});

		javaCompile.setClasspath(fileCollection);

		File destinationDir = new File(
			project.getBuildDir(), javaCompile.getName());

		javaCompile.setDestinationDir(destinationDir);

		javaCompile.setSource(buildServiceTask.getApiDir());

		return javaCompile;
	}

	protected Task addTaskBuildServiceJar(
		BuildServiceTask buildServiceTask, Task buildServiceCompileTask) {

		Project project = buildServiceTask.getProject();

		Jar jar = GradleUtil.addTask(
			project, buildServiceTask.getName() + "Jar", Jar.class);

		jar.from(buildServiceCompileTask.getOutputs());

		jar.setDescription("Assembles the service JAR file.");

		File serviceJarFile = getServiceJarFile(project);

		jar.setArchiveName(serviceJarFile.getName());
		jar.setDestinationDir(serviceJarFile.getParentFile());

		return jar;
	}

	protected void addTaskBuildServiceTasks(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildServiceTask.class,
			new Action<BuildServiceTask>() {

				@Override
				public void execute(BuildServiceTask buildServiceTask) {
					Task buildServiceCompileTask = addTaskBuildServiceCompile(
						buildServiceTask);

					Task buildServiceJarTask = addTaskBuildServiceJar(
						buildServiceTask, buildServiceCompileTask);

					buildServiceTask.finalizedBy(buildServiceJarTask);
				}

			});
	}

	@Override
	protected Copy addTaskDeploy(Project project) {
		Copy copy = super.addTaskDeploy(project);

		copy.setGroup(WarPlugin.WEB_APP_GROUP);

		return copy;
	}

	protected DirectDeployTask addTaskDirectDeploy(Project project) {
		final DirectDeployTask directDeployTask = GradleUtil.addTask(
			project, DIRECT_DEPLOY_TASK_NAME, DirectDeployTask.class);

		directDeployTask.dependsOn(WarPlugin.WAR_TASK_NAME);

		directDeployTask.setDescription(
			"Assembles the project into a WAR file and directly deploys it " +
				"to Liferay, skipping the auto deploy directory.");
		directDeployTask.setGroup(WarPlugin.WEB_APP_GROUP);

		TaskInputs taskInputs = directDeployTask.getInputs();

		taskInputs.dir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return directDeployTask.getWebAppFile();
				}

			});

		return directDeployTask;
	}

	@Override
	protected void addTasks(Project project) {
		super.addTasks(project);

		addTaskDirectDeploy(project);
	}

	@Override
	protected void applyPlugins(Project project) {
		super.applyPlugins(project);

		GradleUtil.applyPlugin(project, WarPlugin.class);
	}

	protected void configureDependencies(Project project) {
		configureDependenciesCompile(project);
	}

	protected void configureDependenciesCompile(Project project) {
		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					File serviceJarFile = getServiceJarFile(project);

					if (serviceJarFile.exists()) {
						GradleUtil.addDependency(
							project, JavaPlugin.COMPILE_CONFIGURATION_NAME,
							serviceJarFile);
					}
				}

			});
	}

	@Override
	protected void configureProperties(Project project) {
		super.configureProperties(project);

		configureWebAppDirName(project);
	}

	@Override
	protected void configureSourceSetMain(Project project) {
		File classesDir = project.file("docroot/WEB-INF/classes");
		File srcDir = project.file("docroot/WEB-INF/src");

		configureSourceSet(
			project, SourceSet.MAIN_SOURCE_SET_NAME, classesDir, srcDir);
	}

	@Override
	protected void configureTaskDeployFrom(Copy copy) {
		Project project = copy.getProject();

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		copy.from(war);

		addCleanDeployedFile(project, war.getArchivePath());
	}

	protected void configureTaskDirectDeploy(
		Project project, LiferayExtension liferayExtension) {

		DirectDeployTask directDeployTask =
			(DirectDeployTask)GradleUtil.getTask(
				project, DIRECT_DEPLOY_TASK_NAME);

		configureTaskDirectDeployAppServerDeployDir(
			directDeployTask, liferayExtension);
		configureTaskDirectDeployWebAppFile(directDeployTask);
		configureTaskDirectDeployWebAppType(directDeployTask);
	}

	protected void configureTaskDirectDeployAppServerDeployDir(
		DirectDeployTask directDeployTask, LiferayExtension liferayExtension) {

		if (directDeployTask.getAppServerDeployDir() == null) {
			directDeployTask.setAppServerDeployDir(
				liferayExtension.getAppServerDeployDir());
		}
	}

	protected void configureTaskDirectDeployWebAppFile(
		DirectDeployTask directDeployTask) {

		if (directDeployTask.getWebAppFile() != null) {
			return;
		}

		War war = (War)GradleUtil.getTask(
			directDeployTask.getProject(), WarPlugin.WAR_TASK_NAME);

		directDeployTask.setWebAppFile(war.getArchivePath());
	}

	protected void configureTaskDirectDeployWebAppType(
		DirectDeployTask directDeployTask) {

		if (Validator.isNull(directDeployTask.getWebAppType())) {
			directDeployTask.setWebAppType(
				getWebAppType(directDeployTask.getProject()));
		}
	}

	@Override
	protected void configureTasks(
		Project project, LiferayExtension liferayExtension) {

		super.configureTasks(project, liferayExtension);

		configureTaskDirectDeploy(project, liferayExtension);
		configureTaskWar(project, liferayExtension);

		addTaskBuildServiceTasks(project);
	}

	protected void configureTaskWar(
		Project project, LiferayExtension liferayExtension) {

		War war = (War)GradleUtil.getTask(project, WarPlugin.WAR_TASK_NAME);

		configureTaskWarAlloyPortlet(war);
		configureTaskWarDuplicatesStrategy(war);
		configureTaskWarExcludeManifest(war);
		configureTaskWarFilesMatching(war);
		configureTaskWarOutputs(war);
		configureTaskWarRenameDependencies(war);
	}

	protected void configureTaskWarAlloyPortlet(final War war) {
		Project project = war.getProject();

		String projectName = project.getName();

		if (!projectName.endsWith("-portlet")) {
			return;
		}

		File webAppDir = getWebAppDir(project);

		String portletXml = "";

		try {
			File portletXmlFile = new File(webAppDir, "WEB-INF/portlet.xml");

			if (portletXmlFile.exists()) {
				portletXml = new String(
					Files.readAllBytes(portletXmlFile.toPath()),
					StandardCharsets.UTF_8);
			}
		}
		catch (Exception e) {
			throw new GradleException("Unable to read portlet.xml", e);
		}

		if (!portletXml.contains("com.liferay.alloy.mvc.AlloyPortlet")) {
			return;
		}

		war.doFirst(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					ClassLoader classLoader =
						LiferayWebAppPlugin.class.getClassLoader();

					try (InputStream inputStream =
							classLoader.getResourceAsStream(
								"com/liferay/gradle/plugins/dependencies" +
									"/touch.jsp")) {

						File touchJspFile = new File(
							task.getTemporaryDir(), "touch.jsp");

						Files.copy(
							inputStream, touchJspFile.toPath(),
							StandardCopyOption.REPLACE_EXISTING);
					}
					catch (Exception e) {
						throw new GradleException(
							"Unable to copy touch.jsp", e);
					}
				}

			});

		war.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					Project project = task.getProject();

					File touchJspFile = new File(
						task.getTemporaryDir(), "touch.jsp");

					project.delete(touchJspFile);
				}

			});

		File jspDir = new File(webAppDir, "WEB-INF/jsp");

		DirectoryStream.Filter<Path> filter =
			new DirectoryStream.Filter<Path>() {

				@Override
				public boolean accept(Path path) throws IOException {
					if (!Files.isDirectory(path)) {
						return false;
					}

					Path viewsDirPath = path.resolve("views");

					if (!Files.exists(viewsDirPath)) {
						return false;
					}

					return true;
				}

			};

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				jspDir.toPath(), filter)) {

			Closure<Void> closure = new Closure<Void>(null) {

				@SuppressWarnings("unused")
				public void doCall(CopySpec copySpec) {
					File touchJspFile = new File(
						war.getTemporaryDir(), "touch.jsp");

					copySpec.from(touchJspFile);
				}

			};

			for (Path path : directoryStream) {
				war.into(
					FileUtil.relativize(path.toFile(), webAppDir), closure);
			}
		}
		catch (Exception e) {
			throw new GradleException(e.getMessage(), e);
		}
	}

	protected void configureTaskWarDuplicatesStrategy(War war) {
		war.setDuplicatesStrategy(DuplicatesStrategy.EXCLUDE);
	}

	protected void configureTaskWarExcludeManifest(War war) {
		CopySpecInternal copySpecInternal = war.getRootSpec();

		for (CopySpecInternal childCopySpecInternal :
				copySpecInternal.getChildren()) {

			CopySpecResolver copySpecResolver =
				childCopySpecInternal.buildRootResolver();

			RelativePath destRelativePath = copySpecResolver.getDestPath();

			String destRelativePathString = destRelativePath.getPathString();

			if (destRelativePathString.equals("META-INF")) {
				childCopySpecInternal.exclude("**");

				return;
			}
		}
	}

	protected void configureTaskWarFilesMatching(War war) {
		final Project project = war.getProject();

		final Closure<String> closure = new Closure<String>(null) {

			@SuppressWarnings("unused")
			public String doCall(String line) {
				if (!line.contains("content/Language*.properties")) {
					return line;
				}

				StringBuilder sb = new StringBuilder();

				SourceSet sourceSet = GradleUtil.getSourceSet(
					project, SourceSet.MAIN_SOURCE_SET_NAME);

				FileTree fileTree = GradleUtil.getFilteredFileTree(
					sourceSet.getResources(), null,
					new String[] {"content/Language*.properties"});

				Iterator<File> iterator = fileTree.iterator();

				while (iterator.hasNext()) {
					File file = iterator.next();

					sb.append("\t<language-properties>content/");
					sb.append(file.getName());
					sb.append("</language-properties>");
					sb.append("\n");
				}

				if (sb.length() > 0) {
					sb.setLength(sb.length() - 1);
				}

				return sb.toString();
			}

		};

		war.filesMatching(
			"WEB-INF/liferay-hook.xml",
			new Action<FileCopyDetails>() {

				@Override
				public void execute(FileCopyDetails fileCopyDetails) {
					fileCopyDetails.filter(closure);
				}

			});
	}

	protected void configureTaskWarOutputs(War war) {
		TaskOutputs taskOutputs = war.getOutputs();

		taskOutputs.file(war.getArchivePath());
	}

	protected void configureTaskWarRenameDependencies(War war) {
		Closure<String> closure = new RenameDependencyClosure(
			war.getProject(), JavaPlugin.RUNTIME_CONFIGURATION_NAME);

		CopySpecInternal copySpecInternal = war.getRootSpec();

		for (CopySpecInternal childCopySpecInternal :
				copySpecInternal.getChildren()) {

			childCopySpecInternal.rename(closure);
		}
	}

	@Override
	protected void configureVersion(
		Project project, LiferayExtension liferayExtension) {

		Object versionObj = project.getVersion();

		if (!versionObj.equals(Project.DEFAULT_VERSION)) {
			super.configureVersion(project, liferayExtension);

			return;
		}

		File pluginPackagePropertiesFile = new File(
			getWebAppDir(project), "WEB-INF/liferay-plugin-package.properties");

		Properties pluginPackageProperties = null;

		try {
			pluginPackageProperties = FileUtil.readProperties(
				pluginPackagePropertiesFile);
		}
		catch (Exception e) {
			throw new GradleException(
				"Unable to read " + pluginPackagePropertiesFile, e);
		}

		String version = pluginPackageProperties.getProperty(
			"module-full-version");

		if (Validator.isNull(version)) {
			version = pluginPackageProperties.getProperty(
				"module-incremental-version");

			version = liferayExtension.getVersionPrefix() + "." + version;
		}

		project.setVersion(version);
	}

	protected void configureWebAppDirName(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		warPluginConvention.setWebAppDirName("docroot");
	}

	@Override
	protected File getLibDir(Project project) {
		return new File(getWebAppDir(project), "WEB-INF/lib");
	}

	protected File getServiceJarFile(Project project) {
		return new File(getLibDir(project), project.getName() + "-service.jar");
	}

	protected File getWebAppDir(Project project) {
		WarPluginConvention warPluginConvention = GradleUtil.getConvention(
			project, WarPluginConvention.class);

		return warPluginConvention.getWebAppDir();
	}

	protected String getWebAppType(Project project) {
		String projectName = project.getName();

		int index = projectName.lastIndexOf("-");

		return projectName.substring(index + 1);
	}

}