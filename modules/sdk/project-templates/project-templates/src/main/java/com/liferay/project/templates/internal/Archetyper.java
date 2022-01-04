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

import com.liferay.project.templates.internal.util.ReflectionUtil;

import java.io.File;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Properties;

import org.apache.maven.archetype.ArchetypeManager;
import org.apache.maven.archetype.DefaultArchetypeManager;
import org.apache.maven.archetype.common.ArchetypeArtifactManager;
import org.apache.maven.archetype.common.DefaultArchetypeFilesResolver;
import org.apache.maven.archetype.common.DefaultPomManager;
import org.apache.maven.archetype.generator.ArchetypeGenerator;
import org.apache.maven.archetype.generator.DefaultArchetypeGenerator;
import org.apache.maven.archetype.generator.DefaultFilesetArchetypeGenerator;
import org.apache.maven.archetype.generator.FilesetArchetypeGenerator;
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

	public Archetyper(List<File> archetypesDirs) {
		_archetypesDirs = archetypesDirs;
	}

	public ArchetypeArtifactManager createArchetypeArtifactManager()
		throws Exception {

		ArchetypeArtifactManager archetypeArtifactManager =
			_newArchetypeArtifactManager();

		ReflectionUtil.setFieldValue(
			_loggerField, archetypeArtifactManager, _logger);

		return archetypeArtifactManager;
	}

	public ArchetypeManager createArchetypeManager() throws Exception {
		DefaultArchetypeManager archetypeManager =
			new DefaultArchetypeManager();

		ReflectionUtil.setFieldValue(_loggerField, archetypeManager, _logger);
		ReflectionUtil.setFieldValue(
			DefaultArchetypeManager.class, "generator", archetypeManager,
			_createArchetypeGenerator());

		return archetypeManager;
	}

	public VelocityComponent createVelocityComponent() throws Exception {
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

	private ArchetypeGenerator _createArchetypeGenerator() throws Exception {
		ArchetypeGenerator archetypeGenerator = new DefaultArchetypeGenerator();

		ArchetypeArtifactManager archetypeArtifactManager =
			createArchetypeArtifactManager();

		ReflectionUtil.setFieldValue(
			DefaultArchetypeGenerator.class, "archetypeArtifactManager",
			archetypeGenerator, archetypeArtifactManager);
		ReflectionUtil.setFieldValue(
			DefaultArchetypeGenerator.class, "filesetGenerator",
			archetypeGenerator,
			_createFilesetArchetypeGenerator(archetypeArtifactManager));

		return archetypeGenerator;
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
			filesetArchetypeGenerator, createVelocityComponent());

		return filesetArchetypeGenerator;
	}

	private ArchetypeArtifactManager _newArchetypeArtifactManager() {
		return new ArchetyperArchetypeArtifactManager(_archetypesDirs);
	}

	private static final Logger _logger = new ArchetyperLogger();

	private static final Field _loggerField;

	static {
		try {
			_loggerField = ReflectionUtil.getField(
				AbstractLogEnabled.class, "logger");
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private final List<File> _archetypesDirs;

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