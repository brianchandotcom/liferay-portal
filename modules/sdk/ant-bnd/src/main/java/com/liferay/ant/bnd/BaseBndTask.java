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

import aQute.bnd.ant.BaseTask;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * @author Raymond Augé
 */
public abstract class BaseBndTask extends BaseTask {

	@Override
	public void execute() throws BuildException {
		try {
			project = getProject();

			doBeforeExecute();
			doExecute();
		}
		catch (BuildException be) {
			throw be;
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public File getBndRootFile() {
		return _bndRootFile;
	}

	public void setBndRootFile(File bndRootFile) {
		_bndRootFile = bndRootFile;
	}

	protected void doBeforeExecute() throws BuildException {
		if ((_bndRootFile == null) || !_bndRootFile.exists() ||
			_bndRootFile.isDirectory()) {

			if (_bndRootFile != null) {
				log(
					"bndRootFile is either missing or is a directory " +
						_bndRootFile.getAbsolutePath(),
					Project.MSG_ERR);
			}

			throw new BuildException("bndRootFile is invalid");
		}

		_bndRootFile = _bndRootFile.getAbsoluteFile();
	}

	protected abstract void doExecute() throws Exception;

	protected Project project;

	private File _bndRootFile;

}