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

package com.liferay.gradle.plugins.util;

import com.liferay.gradle.plugins.BasePortalToolDefaultsPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ArtifactRepositoryContainer;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.CopySpec;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.internal.file.copy.CopySpecInternal;
import org.gradle.api.internal.file.copy.DefaultCopySpec;

/**
 * @author Andrea Di Giorgi
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static final String PORTAL_TOOL_GROUP = "com.liferay";

	public static String getPortalToolVersion(
		Project project, String portalToolName) {

		String portalToolVersion = _portalToolVersions.getProperty(
			portalToolName);

		return GradleUtil.getProperty(
			project, portalToolName + ".version", portalToolVersion);
	}

	public static File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
	}

	/**
	 * Copied from <code>com.liferay.portal.kernel.util.ThreadUtil</code>.
	 */
	public static Thread[] getThreads() {
		Thread currentThread = Thread.currentThread();

		ThreadGroup threadGroup = currentThread.getThreadGroup();

		while (threadGroup.getParent() != null) {
			threadGroup = threadGroup.getParent();
		}

		int threadCountGuess = threadGroup.activeCount();

		Thread[] threads = new Thread[threadCountGuess];

		int threadCountActual = threadGroup.enumerate(threads);

		while (threadCountActual == threadCountGuess) {
			threadCountGuess *= 2;

			threads = new Thread[threadCountGuess];

			threadCountActual = threadGroup.enumerate(threads);
		}

		return threads;
	}

	public static boolean isFromMavenLocal(Project project, File file) {
		RepositoryHandler repositoryHandler = project.getRepositories();

		ArtifactRepository artifactRepository = repositoryHandler.findByName(
			ArtifactRepositoryContainer.DEFAULT_MAVEN_LOCAL_REPO_NAME);

		if (!(artifactRepository instanceof MavenArtifactRepository)) {
			return false;
		}

		MavenArtifactRepository mavenArtifactRepository =
			(MavenArtifactRepository)artifactRepository;

		Path repositoryPath = Paths.get(mavenArtifactRepository.getUrl());

		if (FileUtil.isChild(file, repositoryPath.toFile())) {
			return true;
		}

		return false;
	}

	public static boolean isRunningInsideDaemon() {
		for (Thread thread : getThreads()) {
			if (thread == null) {
				continue;
			}

			String name = thread.getName();

			if (name.startsWith("Daemon worker")) {
				return true;
			}
		}

		return false;
	}

	public static boolean replaceCopySpecSourcePath(
		CopySpec copySpec, Object oldSourcePath, Object newSourcePath) {

		if (copySpec instanceof DefaultCopySpec) {
			DefaultCopySpec defaultCopySpec = (DefaultCopySpec)copySpec;

			Set<Object> sourcePaths = defaultCopySpec.getSourcePaths();

			if (sourcePaths.remove(oldSourcePath)) {
				sourcePaths.add(newSourcePath);

				return true;
			}
		}

		if (copySpec instanceof CopySpecInternal) {
			CopySpecInternal copySpecInternal = (CopySpecInternal)copySpec;

			for (CopySpec childCopySpec : copySpecInternal.getChildren()) {
				boolean replaced = replaceCopySpecSourcePath(
					childCopySpec, oldSourcePath, newSourcePath);

				if (replaced) {
					return true;
				}
			}
		}

		return false;
	}

	public static Map<String, String> toStringMap(Map<String, ?> map) {
		Map<String, String> stringMap = new HashMap<>();

		for (Map.Entry<String, ?> entry : map.entrySet()) {
			String key = entry.getKey();
			String value = toString(entry.getValue());

			stringMap.put(key, value);
		}

		return stringMap;
	}

	private static final Properties _portalToolVersions = new Properties();

	static {
		ClassLoader classLoader =
			BasePortalToolDefaultsPlugin.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/gradle/plugins/dependencies" +
					"/portal-tools.properties")) {

			_portalToolVersions.load(inputStream);
		}
		catch (IOException ioe) {
			throw new ExceptionInInitializerError(ioe);
		}
	}

}