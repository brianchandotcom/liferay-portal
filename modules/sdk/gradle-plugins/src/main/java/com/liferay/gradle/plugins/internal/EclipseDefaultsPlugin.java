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

package com.liferay.gradle.plugins.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import groovy.util.Node;

import java.io.File;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.XmlProvider;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.plugins.ide.api.FileContentMerger;
import org.gradle.plugins.ide.api.XmlFileContentMerger;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.AbstractClasspathEntry;
import org.gradle.plugins.ide.eclipse.model.Classpath;
import org.gradle.plugins.ide.eclipse.model.ClasspathEntry;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;
import org.gradle.plugins.ide.eclipse.model.EclipseProject;

/**
 * @author Andrea Di Giorgi
 */
public class EclipseDefaultsPlugin extends BaseDefaultsPlugin<EclipsePlugin> {

	public static final Plugin<Project> INSTANCE = new EclipseDefaultsPlugin();

	@Override
	protected void configureDefaults(
		Project project, EclipsePlugin eclipsePlugin) {

		final File portalRootDir = GradleUtil.getRootDir(
			project.getRootProject(), "portal-impl");

		_configureEclipseClasspathFile(project);
		_configureEclipseProject(project, portalRootDir);
		_configureTaskEclipse(project);
	}

	@Override
	protected Class<EclipsePlugin> getPluginClass() {
		return EclipsePlugin.class;
	}

	private EclipseDefaultsPlugin() {
	}

	private void _configureEclipseClasspathFile(final Project project) {
		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		final EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		FileContentMerger fileContentMerger = eclipseClasspath.getFile();

		@SuppressWarnings("serial")
		Closure<Void> closure = new Closure<Void>(project) {

			@SuppressWarnings("unused")
			public void doCall(Classpath classpath) {
				List<ClasspathEntry> classpathEntries = classpath.getEntries();

				Iterator<ClasspathEntry> iterator = classpathEntries.iterator();

				while (iterator.hasNext()) {
					ClasspathEntry classpathEntry = iterator.next();

					if (!(classpathEntry instanceof AbstractClasspathEntry)) {
						continue;
					}

					AbstractClasspathEntry abstractClasspathEntry =
						(AbstractClasspathEntry)classpathEntry;

					String kind = abstractClasspathEntry.getKind();
					String path = abstractClasspathEntry.getPath();

					if (kind.equals("lib") && path.endsWith(".pom")) {
						iterator.remove();
					}
				}
			}

		};

		fileContentMerger.whenMerged(closure);

		PluginContainer pluginContainer = project.getPlugins();

		pluginContainer.withType(
			JavaPlugin.class,
			new Action<JavaPlugin>() {

				@Override
				public void execute(JavaPlugin javaPlugin) {
					_configureEclipseClasspathFileForJavaPlugin(
						project, eclipseClasspath);
				}

			});
	}

	private void _configureEclipseClasspathFileForJavaPlugin(
		Project project, EclipseClasspath eclipseClasspath) {

		Collection<Configuration> configurations =
			eclipseClasspath.getPlusConfigurations();

		Configuration configuration = GradleUtil.getConfiguration(
			project, JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME);

		configurations.add(configuration);
	}

	private void _configureEclipseProject(Project project, File portalRootDir) {
		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseProject eclipseProject = eclipseModel.getProject();

		String name = project.getName();

		Task task = GradleUtil.getTask(project, _ECLIPSE_TASK_NAME);

		String gitWorkingBranch = GradleUtil.getTaskPrefixedProperty(
			task, "git.working.branch");

		if (Boolean.parseBoolean(gitWorkingBranch) && (portalRootDir != null) &&
			portalRootDir.exists()) {

			String gitWorkingBranchName = GradleUtil.getProperty(
				project, "git.working.branch.name", (String)null);

			if (Validator.isNotNull(gitWorkingBranchName)) {
				name = name + '-' + gitWorkingBranchName;
			}
		}

		eclipseProject.setName(name);

		List<String> natures = eclipseProject.getNatures();

		natures.add("com.liferay.ide.core.liferayNature");

		Action<XmlProvider> action = new Action<XmlProvider>() {

			@Override
			public void execute(XmlProvider xmlProvider) {
				Node projectDescriptionNode = xmlProvider.asNode();

				Node filteredResourcesNode = projectDescriptionNode.appendNode(
					"filteredResources");

				Node filterNode = filteredResourcesNode.appendNode("filter");

				filterNode.appendNode("id", System.currentTimeMillis());
				filterNode.appendNode("name");
				filterNode.appendNode("type", "26");

				Node matcherNode = filterNode.appendNode("matcher");

				matcherNode.appendNode(
					"id", "org.eclipse.ui.ide.orFilterMatcher");

				Node argumentsNode = matcherNode.appendNode("arguments");

				for (String filteredDirName : _FILTERED_DIR_NAMES) {
					Node curMatcherNode = argumentsNode.appendNode("matcher");

					curMatcherNode.appendNode(
						"arguments",
						"1.0-name-matches-false-false-" + filteredDirName);
					curMatcherNode.appendNode(
						"id", "org.eclipse.ui.ide.multiFilter");
				}
			}

		};

		XmlFileContentMerger xmlFileContentMerger = eclipseProject.getFile();

		xmlFileContentMerger.withXml(action);
	}

	private void _configureTaskEclipse(Project project) {
		Task task = GradleUtil.getTask(project, _ECLIPSE_TASK_NAME);

		task.dependsOn(_CLEAN_ECLIPSE_TASK_NAME);
	}

	private static final String _CLEAN_ECLIPSE_TASK_NAME = "cleanEclipse";

	private static final String _ECLIPSE_TASK_NAME = "eclipse";

	private static final String[] _FILTERED_DIR_NAMES = {
		".git", ".gradle", "build", "node_modules", "node_modules_cache", "tmp"
	};

}