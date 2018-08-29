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

import java.io.File;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.maven.archetype.common.DefaultArchetypeArtifactManager;
import org.apache.maven.archetype.exception.UnknownArchetype;
import org.apache.maven.artifact.repository.ArtifactRepository;

/**
 * @author Gregory Amerson
 */
public class ArchetyperArchetypeArtifactManager
	extends DefaultArchetypeArtifactManager {

	public ArchetyperArchetypeArtifactManager(List<File> archetypesDirs)
		throws Exception {

		_archetypesDirs = archetypesDirs;

		if (_archetypesDirs.isEmpty()) {
			try {
				_archetypesDirs.add(
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

		for (File archetypesFile : _archetypesDirs) {
			try {
				if (archetypesFile.isDirectory()) {
					Path archetypePath = FileUtil.getFile(
						archetypesFile.toPath(), artifactId + "-*.jar");

					if (archetypePath != null) {
						archetypeFile = archetypePath.toFile();
					}
				}
				else {
					archetypeFile = _getArchetypeFile(
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

	private static File _getArchetypeFile(String artifactId, File file)
		throws IOException {

		try (JarFile jarFile = new JarFile(file)) {
			Enumeration<JarEntry> enumeration = jarFile.entries();

			while (enumeration.hasMoreElements()) {
				JarEntry jarEntry = enumeration.nextElement();

				if (jarEntry.isDirectory()) {
					continue;
				}

				String name = jarEntry.getName();

				if (!name.startsWith(artifactId + "-")) {
					continue;
				}

				Path archetypePath = Files.createTempFile(
					"temp-archetype", null);

				Files.copy(
					jarFile.getInputStream(jarEntry), archetypePath,
					StandardCopyOption.REPLACE_EXISTING);

				File archetypeFile = archetypePath.toFile();

				archetypeFile.deleteOnExit();

				return archetypeFile;
			}
		}

		return null;
	}

	private final List<File> _archetypesDirs;

}