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

package com.liferay.portal.tools.rest.builder.ant;

import com.liferay.portal.tools.rest.builder.RESTBuilderArgs;
import com.liferay.portal.tools.rest.builder.RESTBuilderInvoker;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 * @author Peter Shin
 */
public class BuildRESTTask extends Task {

	@Override
	public void execute() throws BuildException {
		try {
			Project project = getProject();

			RESTBuilderInvoker.invoke(project.getBaseDir(), _restBuilderArgs);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setCopyrightFileName(String copyrightFileName) {
		_restBuilderArgs.setCopyrightFileName(copyrightFileName);
	}

	public void setRESTConfigFileName(String restConfigFileName) {
		_restBuilderArgs.setRESTConfigFileName(restConfigFileName);
	}

	public void setRESTOpenAPIFileName(String restOpenAPIFileName) {
		_restBuilderArgs.setRESTOpenAPIFileName(restOpenAPIFileName);
	}

	private final RESTBuilderArgs _restBuilderArgs = new RESTBuilderArgs();

}