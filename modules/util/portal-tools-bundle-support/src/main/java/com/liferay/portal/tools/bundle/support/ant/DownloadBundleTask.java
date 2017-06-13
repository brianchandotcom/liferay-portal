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

package com.liferay.portal.tools.bundle.support.ant;

import com.liferay.portal.tools.bundle.support.commands.DownloadBundleCommand;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * @author David Truong
 */
public class DownloadBundleTask extends DownloadBundleCommand {

	@Override
	public void execute() {
		try {
			super.execute();
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setProject(Project project) {
		_project = project;
	}

	@Override
	protected void onProgress(String message) {
		_project.log(message);
	}

	@Override
	protected void onStarted(String message) {
		_project.log(message);
	}

	private Project _project;

}