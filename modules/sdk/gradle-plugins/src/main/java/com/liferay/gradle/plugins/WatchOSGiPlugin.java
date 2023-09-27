/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.internal.util.IncludeResourceCompileIncludeInstruction;
import com.liferay.gradle.plugins.task.ExecuteBndTask;
import com.liferay.gradle.plugins.task.WatchTask;
import com.liferay.gradle.plugins.util.BndUtil;

import java.io.File;
import java.io.FileInputStream;

import java.util.concurrent.Callable;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.file.FileTree;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.Sync;
import org.gradle.api.tasks.TaskOutputs;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Jar;

/**
 * @author Andrea Di Giorgi
 */
public class WatchOSGiPlugin implements Plugin<Project> {

	public static final String BUILD_BUNDLE_DIR_TASK_NAME = "buildBundleDir";

	public static final Plugin<Project> INSTANCE = new WatchOSGiPlugin();

	public static final String JAR_COMPILE_INCLUDE_FRAGMENT_TASK_NAME =
		"jarCompileIncludeFragment";

	public static final String WATCH_TASK_NAME = "watch";

	@Override
	public void apply(Project project) {

		// Extensions

		BundleExtension bundleExtension = BndUtil.getBundleExtension(
			project.getExtensions());

		// Conventions

		Convention convention = project.getConvention();

		JavaPluginConvention javaPluginConvention = convention.getPlugin(
			JavaPluginConvention.class);

		SourceSetContainer javaSourceSetContainer =
			javaPluginConvention.getSourceSets();

		SourceSet javaMainSourceSet = javaSourceSetContainer.getByName(
			SourceSet.MAIN_SOURCE_SET_NAME);

		// Configurations

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		Configuration compileIncludeConfiguration =
			configurationContainer.maybeCreate(
				LiferayOSGiPlugin.COMPILE_INCLUDE_CONFIGURATION_NAME);

		// Tasks

		TaskProvider<Sync> buildBundleDirTaskProvider =
			GradleUtil.addTaskProvider(
				project, BUILD_BUNDLE_DIR_TASK_NAME, Sync.class);
		TaskProvider<ExecuteBndTask> jarCompileIncludeFragmentTaskProvider =
			GradleUtil.addTaskProvider(
				project, JAR_COMPILE_INCLUDE_FRAGMENT_TASK_NAME,
				ExecuteBndTask.class);
		TaskProvider<WatchTask> watchTaskProvider = GradleUtil.addTaskProvider(
			project, WATCH_TASK_NAME, WatchTask.class);

		TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class);

		_configureTaskBuildBundleDirProvider(
			project, buildBundleDirTaskProvider, jarTaskProvider);
		_configureTaskJarCompileIncludeFragmentProvider(
			project, javaMainSourceSet, bundleExtension,
			compileIncludeConfiguration, jarCompileIncludeFragmentTaskProvider);
		_configureTaskWatchProvider(
			buildBundleDirTaskProvider, jarCompileIncludeFragmentTaskProvider,
			watchTaskProvider);
	}

	private WatchOSGiPlugin() {
	}

	private void _configureTaskBuildBundleDirProvider(
		final Project project, TaskProvider<Sync> buildBundleDirTaskProvider,
		final TaskProvider<Jar> jarTaskProvider) {

		buildBundleDirTaskProvider.configure(
			new Action<Sync>() {

				@Override
				public void execute(Sync buildBundleDirSync) {
					buildBundleDirSync.dependsOn(jarTaskProvider);

					buildBundleDirSync.from(
						new Callable<FileTree>() {

							@Override
							public FileTree call() throws Exception {
								Jar jar = jarTaskProvider.get();

								return project.zipTree(jar.getArchivePath());
							}

						});

					buildBundleDirSync.into(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return new File(
									project.getBuildDir(),
									BUILD_BUNDLE_DIR_TASK_NAME);
							}

						});

					buildBundleDirSync.setDescription(
						"Unzips the project's JAR file into a temporary " +
							"directory.");
				}

			});
	}

	private void _configureTaskJarCompileIncludeFragmentProvider(
		final Project project, final SourceSet javaMainSourceSet,
		final BundleExtension bundleExtension,
		final Configuration compileIncludeConfiguration,
		TaskProvider<ExecuteBndTask> jarCompileIncludeFragmentTaskProvider) {

		jarCompileIncludeFragmentTaskProvider.configure(
			new Action<ExecuteBndTask>() {

				@Override
				public void execute(
					ExecuteBndTask jarCompileIncludeFragmentExecuteBndTask) {

					jarCompileIncludeFragmentExecuteBndTask.property(
						Constants.BUNDLE_NAME,
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								String instruction =
									bundleExtension.getInstruction(
										Constants.BUNDLE_NAME);

								return instruction + " Libs";
							}

						});

					jarCompileIncludeFragmentExecuteBndTask.property(
						Constants.BUNDLE_SYMBOLICNAME,
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								String instruction =
									bundleExtension.getInstruction(
										Constants.BUNDLE_SYMBOLICNAME);

								return instruction + ".libs";
							}

						});

					jarCompileIncludeFragmentExecuteBndTask.property(
						Constants.BUNDLE_VERSION, "1.0.0");

					jarCompileIncludeFragmentExecuteBndTask.property(
						Constants.FRAGMENT_HOST,
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								return bundleExtension.getInstruction(
									Constants.BUNDLE_SYMBOLICNAME);
							}

						});

					jarCompileIncludeFragmentExecuteBndTask.property(
						Constants.INCLUDERESOURCE,
						new IncludeResourceCompileIncludeInstruction(
							new Callable<Iterable<File>>() {

								@Override
								public Iterable<File> call() throws Exception {
									return compileIncludeConfiguration;
								}

							},
							new Callable<Boolean>() {

								@Override
								public Boolean call() throws Exception {
									return Boolean.FALSE;
								}

							}));

					jarCompileIncludeFragmentExecuteBndTask.onlyIf(
						new Spec<Task>() {

							@Override
							public boolean isSatisfiedBy(Task task) {
								return !compileIncludeConfiguration.isEmpty();
							}

						});

					jarCompileIncludeFragmentExecuteBndTask.setClasspath(
						javaMainSourceSet.getCompileClasspath());

					jarCompileIncludeFragmentExecuteBndTask.setDescription(
						"Generates an OSGi fragment containing all " +
							"dependencies of " +
								LiferayOSGiPlugin.
									COMPILE_INCLUDE_CONFIGURATION_NAME + ".");
					jarCompileIncludeFragmentExecuteBndTask.setGroup(
						BasePlugin.BUILD_GROUP);

					jarCompileIncludeFragmentExecuteBndTask.setOutputFile(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return new File(
									project.getBuildDir(),
									project.getName() + "-libs." +
										Jar.DEFAULT_EXTENSION);
							}

						});

					jarCompileIncludeFragmentExecuteBndTask.setResourceDirs(
						project.files());
					jarCompileIncludeFragmentExecuteBndTask.setSourceDirs(
						project.files());
				}

			});
	}

	private void _configureTaskWatchProvider(
		final TaskProvider<Sync> buildBundleDirTaskProvider,
		final TaskProvider<ExecuteBndTask>
			jarCompileIncludeFragmentTaskProvider,
		TaskProvider<WatchTask> watchTaskProvider) {

		watchTaskProvider.configure(
			new Action<WatchTask>() {

				@Override
				public void execute(WatchTask watchTask) {
					Sync buildBundleDirSync = buildBundleDirTaskProvider.get();

					watchTask.dependsOn(buildBundleDirSync);

					watchTask.setBundleDir(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								return buildBundleDirSync.getDestinationDir();
							}

						});

					watchTask.setBundleSymbolicName(
						new Callable<String>() {

							@Override
							public String call() throws Exception {
								File manifestFile = new File(
									buildBundleDirSync.getDestinationDir(),
									"META-INF/MANIFEST.MF");

								if (manifestFile.exists()) {
									try (FileInputStream fileInputStream =
											new FileInputStream(manifestFile)) {

										Manifest manifest = new Manifest(
											fileInputStream);

										Attributes attributes =
											manifest.getMainAttributes();

										return attributes.getValue(
											"Bundle-SymbolicName");
									}
								}

								return null;
							}

						});

					watchTask.setDescription(
						"Continuously redeploys the project's OSGi bundle.");
					watchTask.setGroup(BasePlugin.BUILD_GROUP);

					ExecuteBndTask jarCompileIncludeFragmentExecuteBndTask =
						jarCompileIncludeFragmentTaskProvider.get();

					TaskOutputs taskOutputs =
						jarCompileIncludeFragmentExecuteBndTask.getOutputs();

					watchTask.setFragments(taskOutputs.getFiles());
				}

			});
	}

}