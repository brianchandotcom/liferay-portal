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

package com.liferay.shrinkwrap.osgi.api;

import java.io.File;

import org.jboss.shrinkwrap.api.Assignable;

/**
 * @author Carlos Sierra Andrés
 */
public interface BndProjectBuilder extends Assignable {

	public BndProjectBuilder addClassPath(File file);

	public BndProjectBuilder addProjectPropertiesFile(File file);

	public BndProjectBuilder addWorkspacePropertiesFile(File file);

	public BndArchive asBndJar();

	public BndProjectBuilder generateManifest(boolean enableAnalyze);

	public BndProjectBuilder setBase(File file);

	public BndProjectBuilder setBndFile(File file);

	public BndProjectBuilder setProject(File file);

	public BndProjectBuilder setWorkspace(File file);

}