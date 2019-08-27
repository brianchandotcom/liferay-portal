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

package com.liferay.gradle.plugins.workspace.configurators;

import com.liferay.ant.bnd.metatype.MetatypePlugin;
import com.liferay.gradle.plugins.JspCDefaultsPlugin;
import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.extensions.LiferayOSGiExtension;
import com.liferay.gradle.plugins.service.builder.ServiceBuilderPlugin;
import com.liferay.gradle.plugins.test.integration.TestIntegrationBasePlugin;
import com.liferay.gradle.plugins.workspace.FrontendPlugin;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.FileUtil;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.json.JsonSlurper;

import groovy.lang.Closure;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.ConfigurableFileCollection;
import org.gradle.api.file.CopySourceSpec;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.DeleteSpec;
import org.gradle.api.initialization.Settings;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.ExtraPropertiesExtension;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.jvm.tasks.Jar;
import org.gradle.language.base.plugins.LifecycleBasePlugin;

/**
 * @author Andrea Di Giorgi
 * @author David Truong
 */
public class ModulesProjectConfigurator extends BaseProjectConfigurator {

	public ModulesProjectConfigurator(Settings settings) {
		super(settings);

		_defaultRepositoryEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME +
				".default.repository.enabled",
			_DEFAULT_REPOSITORY_ENABLED);
		_jspPrecompileEnabled = GradleUtil.getProperty(
			settings,
			WorkspacePlugin.PROPERTY_PREFIX + NAME + ".jsp.precompile.enabled",
			_DEFAULT_JSP_PRECOMPILE_ENABLED);
	}

	@Override
	public void apply(Project project) {
		if (isDefaultRepositoryEnabled()) {
			GradleUtil.addDefaultRepositories(project);
		}

		Object jarSourcePath = null;

		File bndBndFile = project.file("bnd.bnd");
		File buildGradleFile = project.file("build.gradle");
		File pomXmlFile = project.file("pom.xml");

		if (bndBndFile.exists() &&
			(buildGradleFile.exists() || pomXmlFile.exists())) {

			if (!project.hasProperty(
					JspCDefaultsPlugin.COMPILE_JSP_INCLUDE_PROPERTY_NAME)) {

				ExtensionContainer extensionContainer = project.getExtensions();

				ExtraPropertiesExtension extraPropertiesExtension =
					extensionContainer.getExtraProperties();

				extraPropertiesExtension.set(
					JspCDefaultsPlugin.COMPILE_JSP_INCLUDE_PROPERTY_NAME,
					isJspPrecompileEnabled());
			}

			GradleUtil.applyPlugin(project, LiferayOSGiPlugin.class);

			if (FileUtil.exists(project, "service.xml")) {
				GradleUtil.applyPlugin(project, ServiceBuilderPlugin.class);
			}

			Jar jar = (Jar)GradleUtil.getTask(
				project, JavaPlugin.JAR_TASK_NAME);

			_configureLiferayOSGi(project);

			_configureRootTaskDistBundle(jar);

			project.afterEvaluate(
				new Action<Project>() {

					@Override
					public void execute(Project project) {
						_configureTaskTestIntegration(project);
					}

				});

			jarSourcePath = jar;
		}
		else {
			File packageJsonFile = project.file("package.json");

			if (packageJsonFile.exists() &&
				_hasNpmBuildScript(packageJsonFile.toPath())) {

				GradleUtil.applyPlugin(project, FrontendPlugin.class);

				final Task buildTask = GradleUtil.getTask(
					project, LifecycleBasePlugin.BUILD_TASK_NAME);

				_configureRootTaskDistBundle(buildTask);

				jarSourcePath = new Callable<ConfigurableFileCollection>() {

					@Override
					public ConfigurableFileCollection call() throws Exception {
						Project project = buildTask.getProject();

						ConfigurableFileCollection configurableFileCollection =
							project.files(_getJarFile(project));

						return configurableFileCollection.builtBy(buildTask);
					}

				};
			}
		}

		final WorkspaceExtension workspaceExtension = _getWorkspaceExtension(
			project);

		_configureLiferay(project, workspaceExtension);

		addTaskDockerDeploy(project, jarSourcePath, workspaceExtension);
	}

	@Override
	public String getName() {
		return NAME;
	}

	public boolean isDefaultRepositoryEnabled() {
		return _defaultRepositoryEnabled;
	}

	public boolean isJspPrecompileEnabled() {
		return _jspPrecompileEnabled;
	}

	public void setDefaultRepositoryEnabled(boolean defaultRepositoryEnabled) {
		_defaultRepositoryEnabled = defaultRepositoryEnabled;
	}

	public void setJspPrecompileEnabled(boolean jspPrecompileEnabled) {
		_jspPrecompileEnabled = jspPrecompileEnabled;
	}

	@Override
	protected Iterable<File> doGetProjectDirs(File rootDir) throws Exception {
		final Set<File> projectDirs = new HashSet<>();

		Files.walkFileTree(
			rootDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (Files.exists(dirPath.resolve("bnd.bnd"))) {
						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					Path dirNamePath = dirPath.getFileName();

					String dirName = dirNamePath.toString();

					if (dirName.equals("build") || dirName.equals("dist") ||
						dirName.equals("node_modules") ||
						dirName.equals("node_modules_cache")) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					Path packageJsonPath = dirPath.resolve("package.json");

					if (Files.exists(packageJsonPath) &&
						_hasNpmBuildScript(packageJsonPath)) {

						projectDirs.add(dirPath.toFile());

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return projectDirs;
	}

	protected static final String NAME = "modules";

	private static File _getResourcesDir(SourceSet sourceSet) {
		SourceSetOutput sourceSetOutput = sourceSet.getOutput();

		return sourceSetOutput.getResourcesDir();
	}

	private void _configureLiferay(
		Project project, WorkspaceExtension workspaceExtension) {

		LiferayExtension liferayExtension = GradleUtil.getExtension(
			project, LiferayExtension.class);

		liferayExtension.setAppServerParentDir(workspaceExtension.getHomeDir());
	}

	private void _configureLiferayOSGi(Project project) {
		LiferayOSGiExtension liferayOSGiExtension = GradleUtil.getExtension(
			project, LiferayOSGiExtension.class);

		Map<String, String> bundleDefaultInstructions = new HashMap<>();

		bundleDefaultInstructions.put(
			"-plugin.metatype", MetatypePlugin.class.getName());

		liferayOSGiExtension.bundleDefaultInstructions(
			bundleDefaultInstructions);
	}

	@SuppressWarnings("serial")
	private void _configureRootTaskDistBundle(final Jar jar) {
		final Project project = jar.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.into(
			"osgi/modules",
			new Closure<Void>(project) {

				@SuppressWarnings("unused")
				public void doCall(CopySourceSpec copySourceSpec) {
					copySourceSpec.from(jar);
				}

			});
	}

	private void _configureRootTaskDistBundle(final Task buildTask) {
		Project project = buildTask.getProject();

		Copy copy = (Copy)GradleUtil.getTask(
			project.getRootProject(),
			RootProjectConfigurator.DIST_BUNDLE_TASK_NAME);

		copy.dependsOn(buildTask);

		copy.into("osgi/modules", _copyJarClosure(project, buildTask));
	}

	private void _configureTaskTestIntegration(Project project) {
		final File testClassesIntegrationDir = project.file(
			"test-classes/integration");
		Task testIntegrationClassesTask = GradleUtil.getTask(
			project,
			TestIntegrationBasePlugin.TEST_INTEGRATION_TASK_NAME + "Classes");

		testIntegrationClassesTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					project.sync(
						new Action<CopySpec>() {

							@Override
							public void execute(CopySpec copySpec) {
								SourceSet sourceSet = GradleUtil.getSourceSet(
									project,
									TestIntegrationBasePlugin.
										TEST_INTEGRATION_SOURCE_SET_NAME);

								copySpec.from(
									FileUtil.getJavaClassesDir(sourceSet));
								copySpec.from(_getResourcesDir(sourceSet));

								copySpec.into(testClassesIntegrationDir);
							}

						});
				}

			});

		Task cleanTask = GradleUtil.getTask(
			project, LifecycleBasePlugin.CLEAN_TASK_NAME);

		cleanTask.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					project.delete(
						new Action<DeleteSpec>() {

							@Override
							public void execute(DeleteSpec deleteSpec) {
								deleteSpec.delete(
									testClassesIntegrationDir.getParentFile());
							}

						});
				}

			});
	}

	@SuppressWarnings({"rawtypes", "serial", "unused"})
	private Closure _copyJarClosure(Project project, final Task assembleTask) {
		return new Closure<Void>(project) {

			public void doCall(CopySpec copySpec) {
				Project project = assembleTask.getProject();

				File jarFile = _getJarFile(project);

				ConfigurableFileCollection configurableFileCollection =
					project.files(jarFile);

				configurableFileCollection.builtBy(assembleTask);

				copySpec.from(jarFile);
			}

		};
	}

	private File _getJarFile(Project project) {
		return project.file(
			"dist/" + GradleUtil.getArchivesBaseName(project) + "-" +
				project.getVersion() + ".jar");
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> _getPackageJsonMap(File packageJsonFile) {
		if (!packageJsonFile.exists()) {
			return Collections.emptyMap();
		}

		JsonSlurper jsonSlurper = new JsonSlurper();

		return (Map<String, Object>)jsonSlurper.parse(packageJsonFile);
	}

	private WorkspaceExtension _getWorkspaceExtension(Project project) {
		return GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);
	}

	@SuppressWarnings("unchecked")
	private boolean _hasNpmBuildScript(Path packageJsonPath) {
		Map<String, Object> packageJsonMap = _getPackageJsonMap(
			packageJsonPath.toFile());

		Map<String, Object> scripts = (Map<String, Object>)packageJsonMap.get(
			"scripts");

		if ((scripts != null) && (scripts.get("build") != null)) {
			return true;
		}

		return false;
	}

	private static final boolean _DEFAULT_JSP_PRECOMPILE_ENABLED = false;

	private static final boolean _DEFAULT_REPOSITORY_ENABLED = true;

	private boolean _defaultRepositoryEnabled;
	private boolean _jspPrecompileEnabled;

}