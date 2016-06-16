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

import com.liferay.gradle.plugins.util.GradleUtil;

import groovy.lang.Closure;

import java.util.Iterator;
import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.plugins.ide.api.FileContentMerger;
import org.gradle.plugins.ide.eclipse.EclipsePlugin;
import org.gradle.plugins.ide.eclipse.model.AbstractClasspathEntry;
import org.gradle.plugins.ide.eclipse.model.Classpath;
import org.gradle.plugins.ide.eclipse.model.ClasspathEntry;
import org.gradle.plugins.ide.eclipse.model.EclipseClasspath;
import org.gradle.plugins.ide.eclipse.model.EclipseModel;

/**
 * @author Andrea Di Giorgi
 */
public class EclipseDefaultsPlugin extends BaseDefaultsPlugin<EclipsePlugin> {

	@Override
	protected void configureDefaults(
		Project project, EclipsePlugin eclipsePlugin) {

		configureEclipseClasspathFile(project);
		configureTaskEclipse(project, eclipsePlugin);
	}

	protected void configureEclipseClasspathFile(Project project) {
		EclipseModel eclipseModel = GradleUtil.getExtension(
			project, EclipseModel.class);

		EclipseClasspath eclipseClasspath = eclipseModel.getClasspath();

		FileContentMerger fileContentMerger = eclipseClasspath.getFile();

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
	}

	protected void configureTaskEclipse(
		Project project, EclipsePlugin eclipsePlugin) {

		Task task = eclipsePlugin.getLifecycleTask();

		task.dependsOn(eclipsePlugin.getCleanTask());
	}

	@Override
	protected Class<EclipsePlugin> getPluginClass() {
		return EclipsePlugin.class;
	}

}