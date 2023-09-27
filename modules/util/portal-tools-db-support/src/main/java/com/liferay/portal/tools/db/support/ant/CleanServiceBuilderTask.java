/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		catch (Exception exception) {
			throw new BuildException(exception);
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