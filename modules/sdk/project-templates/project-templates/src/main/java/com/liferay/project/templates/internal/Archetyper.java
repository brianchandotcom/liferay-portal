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

import com.liferay.project.templates.ProjectTemplateCustomizer;
import com.liferay.project.templates.ProjectTemplates;
import com.liferay.project.templates.ProjectTemplatesArgs;
import com.liferay.project.templates.WorkspaceUtil;
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
import java.nio.file.StandardCopyOption;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ServiceLoader;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.maven.archetype.ArchetypeGenerationRequest;
import org.apache.maven.archetype.ArchetypeGenerationResult;
import org.apache.maven.archetype.ArchetypeManager;
import org.apache.maven.archetype.DefaultArchetypeManager;
import org.apache.maven.archetype.common.ArchetypeArtifactManager;
import org.apache.maven.archetype.common.DefaultArchetypeArtifactManager;
import org.apache.maven.archetype.common.DefaultArchetypeFilesResolver;
import org.apache.maven.archetype.common.DefaultPomManager;
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

		List<File> archetypesDirs = projectTemplatesArgs.getArchetypesDirs();
		String artifactId = projectTemplatesArgs.getName();
		String author = projectTemplatesArgs.getAuthor();
		String className = projectTemplatesArgs.getClassName();
		String groupId = projectTemplatesArgs.getGroupId();
		String liferayVersion = projectTemplatesArgs.getLiferayVersion();
		String packageName = projectTemplatesArgs.getPackageName();

		if (Objects.isNull(groupId)) {
			groupId = packageName;
		}

		File workspaceDir = WorkspaceUtil.getWorkspaceDir(destinationDir);

		String projectType = "standalone";

		if (workspaceDir != null) {
			projectType = WorkspaceUtil.WORKSPACE;
		}

		String template = projectTemplatesArgs.getTemplate();

		ArchetypeGenerationRequest archetypeGenerationRequest =
			new ArchetypeGenerationRequest();

		archetypeGenerationRequest.setArchetypeArtifactId(
			ProjectTemplates.TEMPLATE_BUNDLE_PREFIX +
				template.replace('-', '.'));
		archetypeGenerationRequest.setArchetypeGroupId("com.liferay");

		// archetypeVersion is ignored

		archetypeGenerationRequest.setArchetypeVersion("0");

		archetypeGenerationRequest.setArtifactId(artifactId);
		archetypeGenerationRequest.setGroupId(groupId);
		archetypeGenerationRequest.setInteractiveMode(false);
		archetypeGenerationRequest.setOutputDirectory(destinationDir.getPath());
		archetypeGenerationRequest.setPackage(packageName);

		Properties properties = new Properties();

		String buildType = "gradle";

		if (projectTemplatesArgs.isMaven()) {
			buildType = "maven";
		}

		_setProperty(properties, "author", author);
		_setProperty(properties, "buildType", buildType);
		_setProperty(properties, "className", className);
		_setProperty(properties, "liferayVersion", liferayVersion);
		_setProperty(properties, "package", packageName);
		_setProperty(properties, "projectType", projectType);

		archetypeGenerationRequest.setProperties(properties);

		archetypeGenerationRequest.setVersion("1.0.0");

		ArchetypeArtifactManager archetypeArtifactManager =
			_createArchetypeArtifactManager(archetypesDirs);

		ProjectTemplateCustomizer projectTemplateCustomizer =
			_getProjectTemplateCustomizer(
				archetypeArtifactManager.getArchetypeFile(
					archetypeGenerationRequest.getArchetypeGroupId(),
					archetypeGenerationRequest.getArchetypeArtifactId(),
					archetypeGenerationRequest.getArchetypeVersion(), null,
					null, null));

		if (projectTemplateCustomizer != null) {
			projectTemplateCustomizer.onBeforeGenerateProject(
				projectTemplatesArgs, archetypeGenerationRequest);
		}

		ArchetypeManager archetypeManager = _createArchetypeManager(
			archetypesDirs);

		ArchetypeGenerationResult archetypeGenerationResult =
			archetypeManager.generateProjectFromArchetype(
				archetypeGenerationRequest);

		if (projectTemplateCustomizer != null) {
			projectTemplateCustomizer.onAfterGenerateProject(
				projectTemplatesArgs, destinationDir,
				archetypeGenerationResult);
		}

		return archetypeGenerationResult;
	}

	private ArchetypeArtifactManager _createArchetypeArtifactManager(
			List<File> archetypesDirs)
		throws Exception {

		ArchetypeArtifactManager archetypeArtifactManager =
			new ArchetyperArchetypeArtifactManager(archetypesDirs);

		ReflectionUtil.setFieldValue(
			_loggerField, archetypeArtifactManager, _logger);

		return archetypeArtifactManager;
	}

	private ArchetypeGenerator _createArchetypeGenerator(
			List<File> archetypesDirs)
		throws Exception {

		ArchetypeGenerator archetypeGenerator = new DefaultArchetypeGenerator();

		ArchetypeArtifactManager archetypeArtifactManager =
			_createArchetypeArtifactManager(archetypesDirs);

		ReflectionUtil.setFieldValue(
			DefaultArchetypeGenerator.class, "archetypeArtifactManager",
			archetypeGenerator, archetypeArtifactManager);
		ReflectionUtil.setFieldValue(
			DefaultArchetypeGenerator.class, "filesetGenerator",
			archetypeGenerator,
			_createFilesetArchetypeGenerator(archetypeArtifactManager));

		return archetypeGenerator;
	}

	private ArchetypeManager _createArchetypeManager(List<File> archetypesDirs)
		throws Exception {

		DefaultArchetypeManager archetypeManager =
			new DefaultArchetypeManager();

		ReflectionUtil.setFieldValue(_loggerField, archetypeManager, _logger);
		ReflectionUtil.setFieldValue(
			DefaultArchetypeManager.class, "generator", archetypeManager,
			_createArchetypeGenerator(archetypesDirs));

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
			DefaultFilesetArchetypeGenerator.class, "pomManager",
			filesetArchetypeGenerator, new DefaultPomManager());
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

	private ProjectTemplateCustomizer _getProjectTemplateCustomizer(
			File archetypeFile)
		throws MalformedURLException {

		URI uri = archetypeFile.toURI();

		URLClassLoader urlClassLoader = new URLClassLoader(
			new URL[] {uri.toURL()});

		ServiceLoader<ProjectTemplateCustomizer> serviceLoader =
			ServiceLoader.load(ProjectTemplateCustomizer.class, urlClassLoader);

		Iterator<ProjectTemplateCustomizer> iterator = serviceLoader.iterator();

		if (iterator.hasNext()) {
			return iterator.next();
		}

		return null;
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

		public ArchetyperArchetypeArtifactManager(List<File> archetypesDirs) {
			_archetypesDirs = archetypesDirs;

			if (_archetypesDirs.isEmpty()) {
				try {
					_archetypesDirs.add(FileUtil.getJarFile(Archetyper.class));
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
						try (JarFile jarFile = new JarFile(archetypesFile)) {
							Enumeration<JarEntry> enumeration =
								jarFile.entries();

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
									jarFile.getInputStream(jarEntry),
									archetypePath,
									StandardCopyOption.REPLACE_EXISTING);

								archetypeFile = archetypePath.toFile();

								archetypeFile.deleteOnExit();

								break;
							}
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

		private final List<File> _archetypesDirs;

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