/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.baseline.internal.util;

import com.liferay.gradle.util.FileUtil;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Iterator;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.artifacts.ArtifactRepositoryContainer;
import org.gradle.api.artifacts.dsl.RepositoryHandler;
import org.gradle.api.artifacts.repositories.ArtifactRepository;
import org.gradle.api.artifacts.repositories.MavenArtifactRepository;
import org.gradle.api.file.SourceDirectorySet;

/**
 * @author Andrea Di Giorgi
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static File getSrcDir(SourceDirectorySet sourceDirectorySet) {
		Set<File> srcDirs = sourceDirectorySet.getSrcDirs();

		Iterator<File> iterator = srcDirs.iterator();

		return iterator.next();
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

	public static boolean toBoolean(Object object) {
		object = toObject(object);

		if (object instanceof Boolean) {
			return (Boolean)object;
		}

		if (object instanceof String) {
			return Boolean.parseBoolean((String)object);
		}

		return false;
	}

}