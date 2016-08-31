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

import aQute.lib.io.IO;

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

/**
 * @author Gregory Amerson
 */
public class Archetyper {

	public Archetyper() {
		_logger = new ArchetyperLogger();
	}

	public ArchetypeGenerationResult generateProject(
			ProjectTemplatesArgs projectTemplatesArgs, File destinationDir)
		throws Exception {

		ArchetypeGenerationRequest archetypeGenerationRequest =
			new ArchetypeGenerationRequest();

		String artifactId = projectTemplatesArgs.getName();
		String className = projectTemplatesArgs.getClassName();
		String hostBundleSymbolicName =
			projectTemplatesArgs.getHostBundleSymbolicName();
		String hostBundleVersion = projectTemplatesArgs.getHostBundleVersion();
		String packageName = projectTemplatesArgs.getPackageName();
		String projectType =
			projectTemplatesArgs.getWorkspaceDir() != null ?
				"workspace" : "standalone";
		String service = projectTemplatesArgs.getService();
		String templateName = projectTemplatesArgs.getTemplate();

		archetypeGenerationRequest.setArchetypeArtifactId(
			"com.liferay.project.templates." + templateName);
		archetypeGenerationRequest.setArchetypeGroupId("com.liferay");

		// archetypeVersion is ignored

		archetypeGenerationRequest.setArchetypeVersion("0");
		archetypeGenerationRequest.setArtifactId(artifactId);
		archetypeGenerationRequest.setGroupId(packageName);
		archetypeGenerationRequest.setInteractiveMode(false);
		archetypeGenerationRequest.setVersion("1.0.0");
		archetypeGenerationRequest.setOutputDirectory(destinationDir.getPath());
		archetypeGenerationRequest.setPackage(packageName);

		Properties additionalProperties = new Properties();

		_safePut(additionalProperties, "className", className);
		_safePut(
			additionalProperties, "hostBundleSymbolicName",
			hostBundleSymbolicName);
		_safePut(additionalProperties, "hostBundleVersion", hostBundleVersion);
		_safePut(additionalProperties, "package", packageName);
		_safePut(additionalProperties, "projectType", projectType);
		_safePut(additionalProperties, "serviceClass", service);

		archetypeGenerationRequest.setProperties(additionalProperties);

		ArchetypeGenerationResult result =
			_getArchetypeManager().generateProjectFromArchetype(
				archetypeGenerationRequest);

		return result;
	}

	private static ClassLoader _createArchetypeJarLoader(File archetypeFile) {
		URL[] urls = new URL[1];

		URI uri = archetypeFile.toURI();

		try {
			urls[0] = uri.toURL();
		}
		catch (MalformedURLException murle) {
		}

		return new URLClassLoader(urls, null);
	}

	private static void _safePut(
		Properties properties, String name, String value) {

		if (Validator.isNotNull(value)) {
			properties.put(name, value);
		}
	}

	private ArchetypeArtifactManager _getArchetypeArtifactManager()
		throws Exception {

		if (_archetypeArtifactManager == null) {
			_archetypeArtifactManager =
				new ArchetyperArchetypeArtifactManager();

			ReflectionUtil.setFieldValue(
				_loggerField, _archetypeArtifactManager, _logger);
		}

		return _archetypeArtifactManager;
	}

	private ArchetypeGenerator _getArchetypeGenerator() throws Exception {
		ArchetypeGenerator archetypeGenerator =
			new ArchetyperArchetypeGenerator();

		ArchetypeArtifactManager archetypeArtifactManager =
			_getArchetypeArtifactManager();

		ReflectionUtil.setFieldValue(
			DefaultArchetypeGenerator.class, "archetypeArtifactManager",
			archetypeGenerator, archetypeArtifactManager);

		FilesetArchetypeGenerator filesetGenerator =
			_getFilesetArchetypeGenerator();

		ReflectionUtil.setFieldValue(
			DefaultArchetypeGenerator.class, "filesetGenerator",
			archetypeGenerator, filesetGenerator);

		return archetypeGenerator;
	}

	private ArchetypeManager _getArchetypeManager() throws Exception {
		DefaultArchetypeManager archetypeManager =
			new DefaultArchetypeManager();

		ReflectionUtil.setFieldValue(_loggerField, archetypeManager, _logger);

		ArchetypeGenerator archetypeGenerator = _getArchetypeGenerator();

		ReflectionUtil.setFieldValue(
			DefaultArchetypeManager.class, "generator", archetypeManager,
			archetypeGenerator);

		return archetypeManager;
	}

	private FilesetArchetypeGenerator _getFilesetArchetypeGenerator()
		throws Exception {

		FilesetArchetypeGenerator filesetArchetypeGenerator =
			new DefaultFilesetArchetypeGenerator();

		ReflectionUtil.setFieldValue(
			DefaultFilesetArchetypeGenerator.class, "archetypeArtifactManager",
			filesetArchetypeGenerator, _getArchetypeArtifactManager());

		ReflectionUtil.setFieldValue(
			_loggerField, filesetArchetypeGenerator, _logger);

		DefaultArchetypeFilesResolver defaultArchetypeFilesResolver =
			new DefaultArchetypeFilesResolver();

		ReflectionUtil.setFieldValue(
			DefaultFilesetArchetypeGenerator.class, "archetypeFilesResolver",
			filesetArchetypeGenerator, defaultArchetypeFilesResolver);

		DefaultVelocityComponent velocityComponent =
			new DefaultVelocityComponent();

		ReflectionUtil.setFieldValue(_loggerField, velocityComponent, _logger);

		Properties velocityProps = new Properties();

		velocityProps.setProperty(
			RuntimeConstants.RESOURCE_LOADER, "classpath");

		velocityProps.setProperty(
			"classpath.resource.loader.class",
			ClasspathResourceLoader.class.getName());

		ReflectionUtil.setFieldValue(
			DefaultVelocityComponent.class, "properties", velocityComponent,
			velocityProps);

		ReflectionUtil.setFieldValue(
			DefaultFilesetArchetypeGenerator.class, "velocity",
			filesetArchetypeGenerator, velocityComponent);

		velocityComponent.initialize();

		return filesetArchetypeGenerator;
	}

	private static final String _TEMP_ARCHETYPE_PREFIX = "temp-archetype";

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

	private ArchetypeArtifactManager _archetypeArtifactManager;
	private final Logger _logger;

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

			File archetypeJarFile = null;

			try {
				File file = FileUtil.getJarFile();

				if (file.isDirectory()) {
					Path jarDirPath = file.toPath();

					final Path archetypeJarPath = jarDirPath.resolve(
						artifactId + ".jar");

					archetypeJarFile = archetypeJarPath.toFile();
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

							archetypeJarFile = Files.createTempFile(
								_TEMP_ARCHETYPE_PREFIX, null).toFile();

							if (name.startsWith(artifactId)) {
								IO.copy(
									jarFile.getInputStream(jarEntry),
									archetypeJarFile);
								break;
							}
						}
					}
				}
			}
			catch (Exception e) {
			}

			return archetypeJarFile;
		}

		@Override
		public ClassLoader getArchetypeJarLoader(File archetypeFile)
			throws UnknownArchetype {

			return _createArchetypeJarLoader(archetypeFile);
		}

	}

	private static class ArchetyperArchetypeGenerator
		extends DefaultArchetypeGenerator {

		@Override
		public void generateArchetype(
			ArchetypeGenerationRequest request, File archetypeFile,
			ArchetypeGenerationResult result) {

			super.generateArchetype(request, archetypeFile, result);

			if (archetypeFile.getName().startsWith(_TEMP_ARCHETYPE_PREFIX)) {
				archetypeFile.delete();
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