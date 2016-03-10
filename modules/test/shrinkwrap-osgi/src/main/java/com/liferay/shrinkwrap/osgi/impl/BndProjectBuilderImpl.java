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
	public BndProjectBuilder addClassPath(File file) {
		_classPath.add(file);

		return this;
	}

	@Override
	public BndProjectBuilder addProjectPropertiesFile(File file) {
		_projectPropertiesFiles.add(file);

		return this;
	}

	@Override
	public BndProjectBuilder addWorkspacePropertiesFile(File file) {
		_workspacePropertiesFiles.add(file);

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
			Workspace workspace = new Workspace(_workspaceFile);

			Properties workspaceProperties = buildProperties(
				workspace, null,
				_workspacePropertiesFiles.toArray(new File[0]));

			workspace.setProperties(workspaceProperties);

			Project project = new Project(workspace, _projectFile);

			Properties projectProperties = buildProperties(
				project, _bndFile,
				_projectPropertiesFiles.toArray(new File[0]));

			project.setProperties(projectProperties);

			ProjectBuilder projectBuilder = new ProjectBuilder(project);

			projectBuilder.setBase(_baseFile);

			for (File file : _classPath) {
				projectBuilder.addClasspath(file);
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
	public BndProjectBuilder setBase(File base) {
		if (_workspaceFile == null)setWorkspace(base);

		if (_projectFile == null)setProject(base);
		_baseFile = base;

		return this;
	}

	@Override
	public BndProjectBuilder setBndFile(File bnd) {
		File bndParentDir = bnd.getAbsoluteFile().getParentFile();

		if (_workspaceFile == null)setWorkspace(bndParentDir);

		if (_projectFile == null)setProject(bndParentDir);

		if (_baseFile == null)setBase(bndParentDir);

		_bndFile = bnd;

		return this;
	}

	@Override
	public BndProjectBuilder setProject(File project) {
		if (_workspaceFile == null)setWorkspace(project);

		_projectFile = project;

		return this;
	}

	@Override
	public BndProjectBuilder setWorkspace(File workspace) {
		_workspaceFile = workspace;

		return this;
	}

	protected Properties buildProperties(
			Processor processor, File propertiesFile, File ... extraFiles)
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

	private File _baseFile = null;
	private File _bndFile = null;
	private final Collection<File> _classPath = new ArrayList<>();
	private boolean _generateManifest = true;
	private File _projectFile = null;
	private final List<File> _projectPropertiesFiles = new ArrayList<>();
	private File _workspaceFile = null;
	private final List<File> _workspacePropertiesFiles = new ArrayList<>();

}