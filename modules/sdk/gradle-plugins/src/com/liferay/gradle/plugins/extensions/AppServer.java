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

package com.liferay.gradle.plugins.extensions;

import java.io.File;

import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class AppServer {

	public AppServer(String name, Project project) {
		_name = name;
		_project = project;
	}

	public File getDeployDir() {
		return _project.file(_deployDir);
	}

	public File getDir() {
		return _project.file(_dir);
	}

	public File getLibGlobalDir() {
		return _project.file(_libGlobalDir);
	}

	public String getName() {
		return _name;
	}

	public File getPortalDir() {
		return _project.file(_portalDir);
	}

	public String getVersion() {
		return String.valueOf(_version);
	}

	public String getZipUrl() {
		return String.valueOf(_zipUrl);
	}

	public void setDeployDir(Object deployDir) {
		_deployDir = deployDir;
	}

	public void setDir(Object dir) {
		_dir = dir;
	}

	public void setLibGlobalDir(Object libGlobalDir) {
		_libGlobalDir = libGlobalDir;
	}

	public void setPortalDir(Object portalDir) {
		_portalDir = portalDir;
	}

	public void setVersion(Object version) {
		_version = version;
	}

	public void setZipUrl(Object zipUrl) {
		_zipUrl = zipUrl;
	}

	private Object _deployDir;
	private Object _dir;
	private Object _libGlobalDir;
	private final String _name;
	private Object _portalDir;
	private final Project _project;
	private Object _version;
	private Object _zipUrl;

}