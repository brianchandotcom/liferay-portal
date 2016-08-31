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

import com.liferay.project.templates.ProjectTemplates;
import com.liferay.project.templates.ProjectTemplatesArgs;
import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.ReflectionUtil;
import com.liferay.project.templates.internal.util.Validator;

import java.io.File;

import java.lang.reflect.Field;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;
import org.apache.maven.archetype.ArchetypeManager;
import org.apache.maven.archetype.DefaultArchetypeManager;
import org.apache.maven.archetype.common.ArchetypeArtifactManager;
import org.apache.maven.archetype.common.DefaultArchetypeArtifactManager;
import org.apache.maven.archetype.common.DefaultArchetypeFilesResolver;
import org.apache.maven.archetype.exception.UnknownArchetype;
import org.apache.maven.archetype.generator.ArchetypeGenerator;
import org.apache.maven.archetype.generator.DefaultArchetypeGenerator;
import org.apache.maven.archetype.generator.DefaultFilesetArchetypeGenerator;
import org.apache.maven.archetype.generator.FilesetArchetypeGenerator;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.logging.AbstractLogger;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.velocity.DefaultVelocityComponent;
import org.codehaus.plexus.velocity.VelocityComponent;

/**
 * @author Gregory Amerson
 */
public class Archetyper {

	public ArchetypeGenerationResult generateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir)
		throws Exception {

		String artifactId = projectTemplatesArgs.getName();
		String className = projectTemplatesArgs.getClassName();
		String hostBundleSymbolicName =
			projectTemplatesArgs.getHostBundleSymbolicName();
		String hostBundleVersion = projectTemplatesArgs.getHostBundleVersion();
		String packageName = projectTemplatesArgs.getPackageName();

		String projectType = "standalone";

		if (projectTemplatesArgs.getWorkspaceDir() != null) {
			projectType = "workspace";
		}

		String service = projectTemplatesArgs.getService();
		String templateName = projectTemplatesArgs.getTemplate();

		ArchetypeGenerationRequest archetypeGenerationRequest =
			new ArchetypeGenerationRequest();

		archetypeGenerationRequest.setArchetypeArtifactId(
			ProjectTemplates.TEMPLATE_BUNDLE_PREFIX + templateName);
		archetypeGenerationRequest.setArchetypeGroupId("com.liferay");

		// archetypeVersion is ignored

		archetypeGenerationRequest.setArchetypeVersion("0");

		archetypeGenerationRequest.setArtifactId(artifactId);
		archetypeGenerationRequest.setGroupId(packageName);
		archetypeGenerationRequest.setInteractiveMode(false);
		archetypeGenerationRequest.setOutputDirectory(destinationDir.getPath());
		archetypeGenerationRequest.setPackage(packageName);

		Properties properties = new Properties();

		_setProperty(properties, "className", className);
		_setProperty(
			properties, "hostBundleSymbolicName", hostBundleSymbolicName);
		_setProperty(properties, "hostBundleVersion", hostBundleVersion);
		_setProperty(properties, "package", packageName);
		_setProperty(properties, "projectType", projectType);
		_setProperty(properties, "serviceClass", service);

		archetypeGenerationRequest.setProperties(properties);

		archetypeGenerationRequest.setVersion("1.0.0");

		ArchetypeManager archetypeManager = _createArchetypeManager();

		return archetypeManager.generateProjectFromArchetype(
			archetypeGenerationRequest);
	}

	private ArchetypeArtifactManager _createArchetypeArtifactManager()
		throws Exception {

		ArchetypeArtifactManager archetypeArtifactManager =
			new ArchetyperArchetypeArtifactManager();

		ReflectionUtil.setFieldValue(
			_loggerField, archetypeArtifactManager, _logger);

		return archetypeArtifactManager;
	}

	private ArchetypeGenerator _createArchetypeGenerator() throws Exception {
		ArchetypeGenerator archetypeGenerator = new DefaultArchetypeGenerator();

		ArchetypeArtifactManager archetypeArtifactManager =
			_createArchetypeArtifactManager();

		ReflectionUtil.setFieldValue(
			DefaultArchetypeGenerator.class, "archetypeArtifactManager",
			archetypeGenerator, archetypeArtifactManager);
		ReflectionUtil.setFieldValue(
			DefaultArchetypeGenerator.class, "filesetGenerator",
			archetypeGenerator,
			_createFilesetArchetypeGenerator(archetypeArtifactManager));

		return archetypeGenerator;
	}

	private ArchetypeManager _createArchetypeManager() throws Exception {
		DefaultArchetypeManager archetypeManager =
			new DefaultArchetypeManager();

		ReflectionUtil.setFieldValue(_loggerField, archetypeManager, _logger);
		ReflectionUtil.setFieldValue(
			DefaultArchetypeManager.class, "generator", archetypeManager,
			_createArchetypeGenerator());

		return archetypeManager;
	}

	private FilesetArchetypeGenerator _createFilesetArchetypeGenerator(
			ArchetypeArtifactManager archetypeArtifactManager)
		throws Exception {

		FilesetArchetypeGenerator filesetArchetypeGenerator =
			new DefaultFilesetArchetypeGenerator();

		ReflectionUtil.setFieldValue(
			_loggerField, filesetArchetypeGenerator, _logger);
		ReflectionUtil.setFieldValue(
			DefaultFilesetArchetypeGenerator.class, "archetypeArtifactManager",
			filesetArchetypeGenerator, archetypeArtifactManager);
		ReflectionUtil.setFieldValue(
			DefaultFilesetArchetypeGenerator.class, "archetypeFilesResolver",
			filesetArchetypeGenerator, new DefaultArchetypeFilesResolver());
		ReflectionUtil.setFieldValue(
			DefaultFilesetArchetypeGenerator.class, "velocity",
			filesetArchetypeGenerator, _createVelocityComponent());

		return filesetArchetypeGenerator;
	}

	private VelocityComponent _createVelocityComponent() throws Exception {
		DefaultVelocityComponent defaultVelocityComponent =
			new DefaultVelocityComponent();

		ReflectionUtil.setFieldValue(
			_loggerField, defaultVelocityComponent, _logger);

		Properties properties = new Properties();

		properties.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		properties.setProperty(
			"classpath.resource.loader.class",
			ClasspathResourceLoader.class.getName());

		ReflectionUtil.setFieldValue(
			DefaultVelocityComponent.class, "properties",
			defaultVelocityComponent, properties);

		defaultVelocityComponent.initialize();

		return defaultVelocityComponent;
	}

	private void _setProperty(
		Properties properties, String name, String value) {

		if (Validator.isNotNull(value)) {
			properties.setProperty(name, value);
		}
	}

	private static final Field _loggerField;

	static {
		try {
			_loggerField = ReflectionUtil.getField(
				AbstractLogEnabled.class, "logger");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final Logger _logger = new ArchetyperLogger();

	private static class ArchetyperArchetypeArtifactManager
		extends DefaultArchetypeArtifactManager {

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

			String archetypeFileName = artifactId + ".jar";

			try {
				File file = FileUtil.getJarFile();

				if (file.isDirectory()) {
					Path jarDirPath = file.toPath();

					Path archetypePath = jarDirPath.resolve(archetypeFileName);

					archetypeFile = archetypePath.toFile();
				}
				else {
					try (JarFile jarFile = new JarFile(file)) {
						Enumeration<JarEntry> enumeration = jarFile.entries();

						while (enumeration.hasMoreElements()) {
							JarEntry jarEntry = enumeration.nextElement();

							if (jarEntry.isDirectory()) {
								continue;
							}

							String name = jarEntry.getName();

							if (!name.equals(archetypeFileName)) {
								continue;
							}

							Path archetypePath = Files.createTempFile(
								"temp-archetype", null);

							Files.copy(
								jarFile.getInputStream(jarEntry),
								archetypePath);

							archetypeFile = archetypePath.toFile();

							archetypeFile.deleteOnExit();

							break;
						}
					}
				}
			}
			catch (Exception e) {
				throw new UnknownArchetype(e);
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

	}

	private static class ArchetyperLogger extends AbstractLogger {

		public ArchetyperLogger() {
			super(0, "archetyper");
		}

		@Override
		public void debug(String message, Throwable throwable) {
		}

		@Override
		public void error(String message, Throwable throwable) {
		}

		@Override
		public void fatalError(String message, Throwable throwable) {
		}

		@Override
		public Logger getChildLogger(String name) {
			return this;
		}

		@Override
		public void info(String message, Throwable throwable) {
		}

		@Override
		public void warn(String message, Throwable throwable) {
		}

	}

}