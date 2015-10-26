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

package com.liferay.ant.bnd;

import java.io.File;

import java.util.Properties;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * @author Raymond Augé
 */
public class ReleaseToRepoTask extends BaseBndTask {

	public void setDeployRepo(String name) {
		_deployRepo = name;
	}

	public void setFile(File file) {
		_file = file;
	}

	@Override
	protected void doBeforeExecute() throws BuildException {
		super.doBeforeExecute();

		if ((_file == null) || !_file.exists() || _file.isDirectory()) {
			if (_file != null) {
				log(
					"File is either missing or is a directory " +
						_file.getAbsolutePath(),
					Project.MSG_ERR);
			}

			throw new BuildException("file is invalid");
		}
	}

	@Override
	protected void doExecute() throws Exception {
		BaselineProcessor baselineProcessor = new BaselineProcessor();

		Properties properties = baselineProcessor.getProperties();

		properties.putAll(project.getProperties());

		try {
			Deployer deployer = new Deployer(baselineProcessor);

			if (_deployRepo != null) {
				deployer.deploy(_deployRepo, _file);
			}
			else {
				deployer.deploy(_file);
			}
		}
		finally {
			report(baselineProcessor);

			baselineProcessor.close();
		}
	}

	private String _deployRepo;
	private File _file;

}