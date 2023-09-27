/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace;

import com.liferay.gradle.plugins.LiferayBasePlugin;
import com.liferay.gradle.plugins.extensions.LiferayExtension;
import com.liferay.gradle.plugins.task.BuildExtInfoTask;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.Collections;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileTreeElement;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.WarPlugin;
import org.gradle.api.plugins.WarPluginConvention;
import org.gradle.api.provider.Property;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.bundling.War;
import org.gradle.util.GUtil;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class LiferayExtPlugin implements Plugin<Project> {

	public static final String BUILD_EXT_INFO_BASE_DIR_TASK_NAME =
		"buildExtInfoBaseDir";

	public static final String BUILD_EXT_INFO_TASK_NAME = "buildExtInfo";

	public static final String EXT_IMPL_SOURCE_SET_NAME = "extImpl";

	public static final String EXT_KERNEL_SOURCE_SET_NAME = "extKernel";

	public static final String EXT_UTIL_BRIDGES_SOURCE_SET_NAME =
		"extUtilBridges";

	public static final String EXT_UTIL_JAVA_SOURCE_SET_NAME = "extUtilJava";

	public static final String EXT_UTIL_TAGLIB_SOURCE_SET_NAME =
		"extUtilTaglib";

	@Override
	public void apply(Project project) {

		// Plugins

		_applyPlugins(project);

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		LiferayExtension liferayExtension = extensionContainer.getByType(
			LiferayExtension.class);

		_configureExtensionLiferay(liferayExtension);

		// Conventions

		Convention convention = project.getConvention();

		JavaPluginConvention javaPluginConvention = convention.getPlugin(
			JavaPluginConvention.class);
		WarPluginConvention warPluginConvention = convention.getPlugin(
			WarPluginConvention.class);

		SourceSetContainer sourceSetContainer =
			javaPluginConvention.getSourceSets();

		SourceSet extImplSourceSet = sourceSetContainer.create(
			EXT_IMPL_SOURCE_SET_NAME);
		SourceSet extKernelSourceSet = sourceSetContainer.create(
			EXT_KERNEL_SOURCE_SET_NAME);
		SourceSet extUtilBridgesSourceSet = sourceSetContainer.create(
			EXT_UTIL_BRIDGES_SOURCE_SET_NAME);
		SourceSet extUtilJavaSourceSet = sourceSetContainer.create(
			EXT_UTIL_JAVA_SOURCE_SET_NAME);
		SourceSet extUtilTaglibSourceSet = sourceSetContainer.create(
			EXT_UTIL_TAGLIB_SOURCE_SET_NAME);

		_configureSourceSetExt(warPluginConvention, extImplSourceSet);
		_configureSourceSetExt(warPluginConvention, extKernelSourceSet);
		_configureSourceSetExt(warPluginConvention, extUtilBridgesSourceSet);
		_configureSourceSetExt(warPluginConvention, extUtilJavaSourceSet);
		_configureSourceSetExt(warPluginConvention, extUtilTaglibSourceSet);

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration portalConfiguration = configurationContainer.getByName(
			LiferayBasePlugin.PORTAL_CONFIGURATION_NAME);

		// Tasks

		TaskProvider<Sync> buildExtInfoBaseDirTaskProvider =
			GradleUtil.addTaskProvider(
				project, BUILD_EXT_INFO_BASE_DIR_TASK_NAME, Sync.class);
		TaskProvider<BuildExtInfoTask> buildExtInfoTaskProvider =
			GradleUtil.addTaskProvider(
				project, BUILD_EXT_INFO_TASK_NAME, BuildExtInfoTask.class);
		TaskProvider<Jar> extImplJarTaskProvider = GradleUtil.addTaskProvider(
			project, extImplSourceSet.getJarTaskName(), Jar.class);
		TaskProvider<Jar> extKernelJarTaskProvider = GradleUtil.addTaskProvider(
			project, extKernelSourceSet.getJarTaskName(), Jar.class);
		TaskProvider<Jar> extUtilBridgesJarTaskProvider =
			GradleUtil.addTaskProvider(
				project, extUtilBridgesSourceSet.getJarTaskName(), Jar.class);
		TaskProvider<Jar> extUtilJavaJarTaskProvider =
			GradleUtil.addTaskProvider(
				project, extUtilJavaSourceSet.getJarTaskName(), Jar.class);
		TaskProvider<Jar> extUtilTaglibJarTaskProvider =
			GradleUtil.addTaskProvider(
				project, extUtilTaglibSourceSet.getJarTaskName(), Jar.class);

		TaskProvider<Copy> deployTaskProvider = GradleUtil.getTaskProvider(
			project, LiferayBasePlugin.DEPLOY_TASK_NAME, Copy.class);
		TaskProvider<War> warTaskProvider = GradleUtil.getTaskProvider(
			project, WarPlugin.WAR_TASK_NAME, War.class);

		_configureTaskBuildExtInfoBaseDirProvider(
			project, buildExtInfoBaseDirTaskProvider, buildExtInfoTaskProvider,
			warTaskProvider);
		_configureTaskBuildExtInfoProvider(
			buildExtInfoBaseDirTaskProvider, buildExtInfoTaskProvider,
			warTaskProvider, portalConfiguration);
		_configureTaskDeployProvider(deployTaskProvider, warTaskProvider);
		_configureTaskExtImplJarProvider(
			project, extImplJarTaskProvider, extUtilBridgesJarTaskProvider,
			extUtilJavaJarTaskProvider, extUtilTaglibJarTaskProvider);
		_configureTaskExtJarProvider(extImplSourceSet, extImplJarTaskProvider);
		_configureTaskExtJarProvider(
			extKernelSourceSet, extKernelJarTaskProvider);
		_configureTaskExtJarProvider(
			extUtilBridgesSourceSet, extUtilBridgesJarTaskProvider);
		_configureTaskExtJarProvider(
			extUtilJavaSourceSet, extUtilJavaJarTaskProvider);
		_configureTaskExtJarProvider(
			extUtilTaglibSourceSet, extUtilTaglibJarTaskProvider);
		_configureTaskWarProvider(
			project, buildExtInfoTaskProvider, warTaskProvider,
			extImplJarTaskProvider, extKernelJarTaskProvider,
			extUtilBridgesJarTaskProvider, extUtilJavaJarTaskProvider,
			extUtilTaglibJarTaskProvider);

		// Other

		FileCollection fileCollection = project.files(
			extKernelJarTaskProvider, extUtilBridgesJarTaskProvider,
			extUtilJavaJarTaskProvider, extUtilTaglibJarTaskProvider);

		extImplSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));

		fileCollection = project.files(Collections.emptyList());

		extKernelSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));

		fileCollection = project.files(extKernelJarTaskProvider);

		extUtilBridgesSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));

		fileCollection = project.files(
			extKernelJarTaskProvider, extUtilBridgesJarTaskProvider);

		extUtilJavaSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));

		fileCollection = project.files(
			extKernelJarTaskProvider, extUtilBridgesJarTaskProvider,
			extUtilJavaJarTaskProvider);

		extUtilTaglibSourceSet.setCompileClasspath(
			portalConfiguration.plus(fileCollection));
	}

	private void _applyPlugins(Project project) {
		GradleUtil.applyPlugin(project, LiferayBasePlugin.class);
		GradleUtil.applyPlugin(project, WarPlugin.class);
	}

	private void _configureExtensionLiferay(
		final LiferayExtension liferayExtension) {

		liferayExtension.setDeployDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						liferayExtension.getAppServerParentDir(), "deploy");
				}

			});
	}

	private void _configureSourceSetExt(
		final WarPluginConvention warPluginConvention, SourceSet extSourceSet) {

		SourceDirectorySet javaSourceDirectorySet = extSourceSet.getJava();

		javaSourceDirectorySet.srcDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					String s = GUtil.toWords(extSourceSet.getName(), '-');

					return new File(
						warPluginConvention.getWebAppDir(),
						"WEB-INF/" + s + "/src");
				}

			});

		SourceDirectorySet resourcesDirectorySet = extSourceSet.getResources();

		resourcesDirectorySet.srcDir(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					String s = GUtil.toWords(extSourceSet.getName(), '-');

					return new File(
						warPluginConvention.getWebAppDir(),
						"WEB-INF/" + s + "/src");
				}

			});
	}

	private void _configureTaskBuildExtInfoBaseDirProvider(
		final Project project,
		TaskProvider<Sync> buildExtInfoBaseDirTaskProvider,
		final TaskProvider<BuildExtInfoTask> buildExtInfoTaskProvider,
		final TaskProvider<War> warTaskProvider) {

		buildExtInfoBaseDirTaskProvider.configure(
			new Action<Sync>() {

				@Override
				public void execute(Sync buildExtInfoBaseDirSync) {
					buildExtInfoBaseDirSync.exclude(
						new Spec<FileTreeElement>() {

							@Override
							public boolean isSatisfiedBy(
								FileTreeElement fileTreeElement) {

								BuildExtInfoTask buildExtInfoTask =
									buildExtInfoTaskProvider.get();

								File outputFile =
									buildExtInfoTask.getOutputFile();

								String outputFileName = outputFile.getName();

								if (outputFileName.equals(
										fileTreeElement.getPath())) {

									return true;
								}

								return false;
							}

						});

					buildExtInfoBaseDirSync.into(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return new File(
									project.getBuildDir(), "build-ext-info");
							}

						});

					buildExtInfoBaseDirSync.setDescription(
						"Copies the exploded war archive into a temporary " +
							"directory.");
					buildExtInfoBaseDirSync.with(warTaskProvider.get());
				}

			});
	}

	private void _configureTaskBuildExtInfoProvider(
		final TaskProvider<Sync> buildExtInfoBaseDirTaskProvider,
		TaskProvider<BuildExtInfoTask> buildExtInfoTaskProvider,
		final TaskProvider<War> warTaskProvider,
		final FileCollection classpath) {

		buildExtInfoTaskProvider.configure(
			new Action<BuildExtInfoTask>() {

				@Override
				public void execute(BuildExtInfoTask buildExtInfoTask) {
					final Sync buildExtInfoBaseDirSync =
						buildExtInfoBaseDirTaskProvider.get();

					buildExtInfoTask.dependsOn(buildExtInfoBaseDirSync);

					buildExtInfoTask.setBaseDir(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return new File(
									buildExtInfoBaseDirSync.getDestinationDir(),
									"WEB-INF");
							}

						});

					buildExtInfoTask.setClasspath(classpath);
					buildExtInfoTask.setDescription(
						"Generates the ext information xml file.");

					buildExtInfoTask.setOutputDir(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return buildExtInfoTask.getTemporaryDir();
							}

						});

					buildExtInfoTask.setServletContextName(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								War war = warTaskProvider.get();

								Property<String> archiveAppendixProperty =
									war.getArchiveAppendix();
								Property<String> archiveBaseNameProperty =
									war.getArchiveBaseName();

								String servletContextName =
									archiveBaseNameProperty.get();

								if (archiveAppendixProperty.isPresent()) {
									servletContextName +=
										'-' + archiveAppendixProperty.get();
								}

								return servletContextName;
							}

						});
				}

			});
	}

	private void _configureTaskDeployProvider(
		TaskProvider<Copy> deployTaskProvider,
		final TaskProvider<War> warTaskProvider) {

		deployTaskProvider.configure(
			new Action<Copy>() {

				@Override
				public void execute(Copy deployCopy) {
					deployCopy.from(warTaskProvider);
				}

			});
	}

	private void _configureTaskExtImplJarProvider(
		final Project project, TaskProvider<Jar> extImplJarTaskProvider,
		final TaskProvider<Jar>... extJarTaskProviders) {

		extImplJarTaskProvider.configure(
			new Action<Jar>() {

				@Override
				public void execute(Jar extImplJar) {
					for (final TaskProvider<Jar> extJarTaskProvider :
							extJarTaskProviders) {

						extImplJar.into(
							"com/liferay/portal/deploy/dependencies",
							new Closure<Void>(project) {

								@SuppressWarnings("unused")
								public void doCall(CopySpec copySpec) {
									Jar extJar = extJarTaskProvider.get();

									Property<String> archiveAppendixProperty =
										extJar.getArchiveAppendix();

									final String archiveAppendix =
										archiveAppendixProperty.get();

									Property<String> archiveExtensionProperty =
										extJar.getArchiveExtension();

									final String archiveExtension =
										archiveExtensionProperty.get();

									copySpec.from(extJar);
									copySpec.rename(
										new Closure<String>(project) {

											public String doCall(
												String fileName) {

												StringBuilder sb =
													new StringBuilder();

												sb.append("ext-");
												sb.append(project.getName());
												sb.append('-');
												sb.append(archiveAppendix);
												sb.append('.');
												sb.append(archiveExtension);

												return sb.toString();
											}

										});
								}

							});
					}
				}

			});
	}

	private void _configureTaskExtJarProvider(
		final SourceSet extSourceSet, TaskProvider<Jar> extJarTaskProvider) {

		extJarTaskProvider.configure(
			new Action<Jar>() {

				@Override
				public void execute(Jar extJar) {
					extJar.from(extSourceSet.getOutput());

					Property<String> archiveAppendixProperty =
						extJar.getArchiveAppendix();

					String archiveAppendix = GUtil.toWords(
						extSourceSet.getName(), '-');

					archiveAppendixProperty.set(archiveAppendix);

					extJar.setDescription(
						"Assembles a jar archive containing the " +
							archiveAppendix + " classes.");
				}

			});
	}

	private void _configureTaskWarProvider(
		final Project project,
		final TaskProvider<BuildExtInfoTask> buildExtInfoTaskProvider,
		TaskProvider<War> warTaskProvider,
		final TaskProvider<Jar>... extJarTaskProviders) {

		warTaskProvider.configure(
			new Action<War>() {

				@Override
				public void execute(War war) {
					String name = project.getName();

					if (!name.endsWith("-ext")) {
						Property<String> archiveAppendixProperty =
							war.getArchiveAppendix();

						archiveAppendixProperty.set("ext");
					}

					CopySpec copySpec = war.getWebInf();

					war.dependsOn(buildExtInfoTaskProvider);

					copySpec.from(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								BuildExtInfoTask buildExtInfoTask =
									buildExtInfoTaskProvider.get();

								return buildExtInfoTask.getOutputFile();
							}

						});

					for (TaskProvider<Jar> extJarTaskProvider :
							extJarTaskProviders) {

						Jar extJar = extJarTaskProvider.get();

						Property<String> archiveAppendixProperty =
							extJar.getArchiveAppendix();

						final String archiveAppendix =
							archiveAppendixProperty.get();

						Property<String> archiveExtensionProperty =
							extJar.getArchiveExtension();

						final String archiveExtension =
							archiveExtensionProperty.get();

						copySpec.from(
							extJarTaskProvider,
							new Closure<Void>(project) {

								public void doCall(CopySpec copySpec) {
									copySpec.into(archiveAppendix);

									copySpec.rename(
										new Closure<String>(project) {

											public String doCall(
												String fileName) {

												return archiveAppendix + '.' +
													archiveExtension;
											}

										});
								}

							});
					}
				}

			});
	}

}