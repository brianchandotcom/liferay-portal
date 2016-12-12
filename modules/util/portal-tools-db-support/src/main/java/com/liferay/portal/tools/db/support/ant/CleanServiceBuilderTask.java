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

package com.liferay.portal.tools.db.support.ant;

import com.liferay.portal.tools.db.support.commands.CleanServiceBuilderCommand;

import java.io.File;

import org.apache.tools.ant.BuildException;

/**
 * @author Andrea Di Giorgi
 */
public class CleanServiceBuilderTask extends BaseTask {

	@Override
	public void execute() throws BuildException {
		try {
			_cleanServiceBuilderCommand.execute(dbSupportArgs);
		}
		catch (Exception e) {
			throw new BuildException(e);
		}
	}

	public void setServiceXmlFile(File serviceXmlFile) {
		_cleanServiceBuilderCommand.setServiceXmlFile(serviceXmlFile);
	}

	public void setServletContextName(String servletContextName) {
		_cleanServiceBuilderCommand.setServletContextName(servletContextName);
	}

	private final CleanServiceBuilderCommand _cleanServiceBuilderCommand =
		new CleanServiceBuilderCommand();

}