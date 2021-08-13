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

import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.internal.util.FileUtil;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.jasper.jspc.CompileJSPTask;
import com.liferay.gradle.plugins.jasper.jspc.JspCPlugin;
import com.liferay.gradle.plugins.util.BndUtil;

import java.io.File;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.FileTree;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.plugins.Convention;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.tasks.Copy;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;
import org.gradle.api.tasks.SourceSetOutput;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.util.PatternFilterable;

/**
 * @author Andrea Di Giorgi
 */
public class JspCDefaultsPlugin extends BaseDefaultsPlugin<JspCPlugin> {

	public static final String COMPILE_JSP_INCLUDE_PROPERTY_NAME =
		"compile.jsp.include";

	public static final Plugin<Project> INSTANCE = new JspCDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(Project project, JspCPlugin jspCPlugin) {

		// Extensions

		ExtensionContainer extensionContainer = project.getExtensions();

		final BundleExtension bundleExtension = BndUtil.getBundleExtension(
			extensionContainer);

		// Conventions

		Convention convention = project.getConvention();

		JavaPluginConvention javaPluginConvention = convention.getPlugin(
			JavaPluginConvention.class);

		SourceSetContainer javaSourceSetContainer =
			javaPluginConvention.getSourceSets();

		SourceSet javaMainSourceSet = javaSourceSetContainer.getByName(
			SourceSet.MAIN_SOURCE_SET_NAME);

		// Tasks

		final TaskProvider<JavaCompile> compileJSPTaskProvider =
			GradleUtil.getTaskProvider(
				project, JspCPlugin.COMPILE_JSP_TASK_NAME, JavaCompile.class);
		final TaskProvider<CompileJSPTask> generateJSPJavaTaskProvider =
			GradleUtil.getTaskProvider(
				project, JspCPlugin.GENERATE_JSP_JAVA_TASK_NAME,
				CompileJSPTask.class);
		TaskProvider<Jar> jarTaskProvider = GradleUtil.getTaskProvider(
			project, JavaPlugin.JAR_TASK_NAME, Jar.class);
		TaskProvider<Copy> processResourcesTaskProvider =
			GradleUtil.getTaskProvider(
				project, JavaPlugin.PROCESS_RESOURCES_TASK_NAME, Copy.class);

		_configureTaskGenerateJSPJavaProvider(
			javaMainSourceSet, generateJSPJavaTaskProvider,
			processResourcesTaskProvider);
		_configureTaskJarProvider(
			project, compileJSPTaskProvider, jarTaskProvider);
		_configureTaskProcessResourcesProvider(
			javaMainSourceSet, processResourcesTaskProvider);

		// Other

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					_configureExtensionBundleAfterEvaluate(
						bundleExtension, compileJSPTaskProvider,
						generateJSPJavaTaskProvider);
				}

			});
	}

	@Override
	protected Class<JspCPlugin> getPluginClass() {
		return JspCPlugin.class;
	}

	private JspCDefaultsPlugin() {
	}

	private void _configureExtensionBundleAfterEvaluate(
		BundleExtension bundleExtension,
		TaskProvider<JavaCompile> compileJSPTaskProvider,
		TaskProvider<CompileJSPTask> generateJSPJavaTaskProvider) {

		StringBuilder sb = new StringBuilder();

		JavaCompile compileJSPJavaCompile = compileJSPTaskProvider.get();

		sb.append(
			FileUtil.getAbsolutePath(
				compileJSPJavaCompile.getDestinationDir()));

		sb.append(',');

		CompileJSPTask generateJSPJavaCompileJSPTask =
			generateJSPJavaTaskProvider.get();

		sb.append(
			FileUtil.getAbsolutePath(
				generateJSPJavaCompileJSPTask.getDestinationDir()));

		bundleExtension.instruction("-add-resource", sb.toString());
	}

	private void _configureTaskGenerateJSPJavaProvider(
		final SourceSet javaMainSourceSet,
		TaskProvider<CompileJSPTask> generateJSPJavaTaskProvider,
		final TaskProvider<Copy> processResourcesTaskProvider) {

		generateJSPJavaTaskProvider.configure(
			new Action<CompileJSPTask>() {

				@Override
				public void execute(
					CompileJSPTask generateJSPJavaCompileJSPTask) {

					generateJSPJavaCompileJSPTask.dependsOn(
						processResourcesTaskProvider);

					generateJSPJavaCompileJSPTask.setWebAppDir(
						new Callable<File>() {

							@Override
							public File call() throws Exception {
								SourceSetOutput sourceSetOutput =
									javaMainSourceSet.getOutput();

								return new File(
									sourceSetOutput.getResourcesDir(),
									"META-INF/resources");
							}

						});
				}

			});
	}

	private void _configureTaskJarProvider(
		final Project project,
		final TaskProvider<JavaCompile> compileJSPTaskProvider,
		TaskProvider<Jar> jarTaskProvider) {

		jarTaskProvider.configure(
			new Action<Jar>() {

				@Override
				public void execute(Jar jar) {
					boolean compileJspInclude = GradleUtil.getProperty(
						project, COMPILE_JSP_INCLUDE_PROPERTY_NAME, false);

					if (compileJspInclude) {
						jar.dependsOn(compileJSPTaskProvider);
					}
				}

			});
	}

	private void _configureTaskProcessResourcesProvider(
		final SourceSet javaMainSourceSet,
		TaskProvider<Copy> processResourcesTaskProvider) {

		processResourcesTaskProvider.configure(
			new Action<Copy>() {

				@Override
				public void execute(Copy processResourcesCopy) {
					SourceDirectorySet sourceDirectorySet =
						javaMainSourceSet.getResources();

					FileTree fileTree = sourceDirectorySet.getAsFileTree();

					fileTree = fileTree.matching(
						new Action<PatternFilterable>() {

							@Override
							public void execute(
								PatternFilterable patternFilterable) {

								patternFilterable.include("**/*.tld");
							}

						});

					processResourcesCopy.from(
						fileTree.getFiles(),
						new Action<CopySpec>() {

							@Override
							public void execute(CopySpec copySpec) {
								copySpec.into("META-INF/resources/WEB-INF");
							}

						});

					Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

					Iterator<File> iterator = srcDirs.iterator();

					if (iterator.hasNext()) {
						File tagsDir = new File(
							iterator.next(), "META-INF/tags");

						if (tagsDir.exists()) {
							processResourcesCopy.from(
								tagsDir,
								new Action<CopySpec>() {

									@Override
									public void execute(CopySpec copySpec) {
										copySpec.into(
											"META-INF/resources/META-INF/tags");
									}

								});
						}
					}
				}

			});
	}

}