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

package com.liferay.project.templates.internal;

import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.ProjectTemplatesUtil;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Path;

import java.util.List;

import org.apache.maven.archetype.common.DefaultArchetypeArtifactManager;
import org.apache.maven.archetype.exception.UnknownArchetype;
import org.apache.maven.artifact.repository.ArtifactRepository;

/**
 * @author Gregory Amerson
 */
public class ArchetyperArchetypeArtifactManager
	extends DefaultArchetypeArtifactManager {

	public ArchetyperArchetypeArtifactManager(List<File> archetypesFiles) {
		_archetypesFiles = archetypesFiles;

		if (_archetypesFiles.isEmpty()) {
			try {
				_archetypesFiles.add(
					FileUtil.getJarFile(ProjectGenerator.class));
			}
			catch (Exception e) {
			}
		}
	}

	@Override
	public boolean exists(
		String archetypeGroupId, String archetypeArtifactId,
		String archetypeVersion, ArtifactRepository archetypeRepository,
		ArtifactRepository localRepository,
		List<ArtifactRepository> remoteRepositories) {

		return true;
	}

	@Override
	public File getArchetypeFile(
			String groupId, String artifactId, String version,
			ArtifactRepository archetypeRepository,
			ArtifactRepository localRepository,
			List<ArtifactRepository> repositories)
		throws UnknownArchetype {

		File archetypeFile = null;

		for (File archetypesFile : _archetypesFiles) {
			try {
				if (archetypesFile.isDirectory()) {
					Path archetypePath = FileUtil.getFile(
						archetypesFile.toPath(), artifactId + "-*.jar",
						".*-[0-9]+\\.[0-9]+\\.[0-9]+\\.jar");

					if (archetypePath != null) {
						archetypeFile = archetypePath.toFile();
					}
				}
				else {
					archetypeFile = ProjectTemplatesUtil.getArchetypeFile(
						artifactId, archetypesFile);

					if (archetypeFile != null) {
						break;
					}
				}
			}
			catch (Exception e) {
				continue;
			}
		}

		if (archetypeFile == null) {
			throw new UnknownArchetype();
		}

		return archetypeFile;
	}

	@Override
	public ClassLoader getArchetypeJarLoader(File archetypeFile)
		throws UnknownArchetype {

		try {
			URI uri = archetypeFile.toURI();

			return new URLClassLoader(new URL[] {uri.toURL()}, null);
		}
		catch (MalformedURLException murle) {
			throw new UnknownArchetype(murle);
		}
	}

	private final List<File> _archetypesFiles;

}