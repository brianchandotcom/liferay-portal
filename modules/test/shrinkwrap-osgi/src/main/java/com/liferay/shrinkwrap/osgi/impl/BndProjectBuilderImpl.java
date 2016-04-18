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

package com.liferay.shrinkwrap.osgi.impl;

import aQute.bnd.build.Project;
import aQute.bnd.build.ProjectBuilder;
import aQute.bnd.build.Workspace;
import aQute.bnd.osgi.Processor;

import com.liferay.shrinkwrap.osgi.api.BndArchive;
import com.liferay.shrinkwrap.osgi.api.BndProjectBuilder;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Assignable;

/**
 * @author Carlos Sierra Andrés
 */
public class BndProjectBuilderImpl implements BndProjectBuilder {

	public BndProjectBuilderImpl(Archive<?> archive) {
	}

	@Override
	public BndProjectBuilder addClassPath(File classPathFile) {
		_classPathFiles.add(classPathFile);

		return this;
	}

	@Override
	public BndProjectBuilder addProjectPropertiesFile(
		File projectPropertiesFile) {

		_projectPropertiesFiles.add(projectPropertiesFile);

		return this;
	}

	@Override
	public BndProjectBuilder addWorkspacePropertiesFile(
		File workspacePropertiesFile) {

		_workspacePropertiesFiles.add(workspacePropertiesFile);

		return this;
	}

	@Override
	public <TYPE extends Assignable> TYPE as(Class<TYPE> typeClass) {
		try {
			BndArchive bndArchive = asBndJar();

			return bndArchive.as(typeClass);
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public BndArchive asBndJar() {
		try {
			Workspace workspace = new Workspace(_workspaceDir);

			Properties workspaceProperties = buildProperties(
				workspace, null,
				_workspacePropertiesFiles.toArray(new File[0]));

			workspace.setProperties(workspaceProperties);

			Project project = new Project(workspace, _projectDir);

			Properties projectProperties = buildProperties(
				project, _bndFile,
				_projectPropertiesFiles.toArray(new File[0]));

			project.setProperties(projectProperties);

			ProjectBuilder projectBuilder = new ProjectBuilder(project);

			projectBuilder.setBase(_baseDir);

			for (File classPathFile : _classPathFiles) {
				projectBuilder.addClasspath(classPathFile);
			}

			if (!_generateManifest) {
				projectBuilder.setProperty(ProjectBuilder.NOMANIFEST, "true");
			}

			return new BndArchive(projectBuilder.build());
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public BndProjectBuilder generateManifest(boolean generateManifest) {
		_generateManifest = generateManifest;

		return this;
	}

	@Override
	public BndProjectBuilder setBaseDir(File baseDir) {
		_baseDir = baseDir;

		if (_projectDir == null) {
			setProjectDir(baseDir);
		}

		if (_workspaceDir == null) {
			setWorkspaceDir(baseDir);
		}

		return this;
	}

	@Override
	public BndProjectBuilder setBndFile(File bndFile) {
		_bndFile = bndFile;

		File absoluteBndFile = bndFile.getAbsoluteFile();

		File parentBndDir = absoluteBndFile.getParentFile();

		if (_baseDir == null) {
			setBaseDir(parentBndDir);
		}

		if (_projectDir == null) {
			setProjectDir(parentBndDir);
		}

		if (_workspaceDir == null) {
			setWorkspaceDir(parentBndDir);
		}

		return this;
	}

	@Override
	public BndProjectBuilder setProjectDir(File projectDir) {
		if (_workspaceDir == null) {
			setWorkspaceDir(projectDir);
		}

		_projectDir = projectDir;

		return this;
	}

	@Override
	public BndProjectBuilder setWorkspaceDir(File workspaceDir) {
		_workspaceDir = workspaceDir;

		return this;
	}

	protected Properties buildProperties(
			Processor processor, File propertiesFile, File... extraFiles)
		throws IOException {

		Properties properties = new Properties();

		for (File extraFile : extraFiles) {
			properties.putAll(processor.loadProperties(extraFile));
		}

		if (propertiesFile != null) {
			properties.putAll(processor.loadProperties(propertiesFile));
		}

		return properties;
	}

	private File _baseDir;
	private File _bndFile;
	private final Collection<File> _classPathFiles = new ArrayList<>();
	private boolean _generateManifest = true;
	private File _projectDir;
	private final List<File> _projectPropertiesFiles = new ArrayList<>();
	private File _workspaceDir;
	private final List<File> _workspacePropertiesFiles = new ArrayList<>();

}