/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.support.maven;

import com.liferay.portal.tools.db.support.DBSupportArgs;
import com.liferay.portal.tools.db.support.commands.CleanServiceBuilderCommand;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Clean the Liferay database from the Service Builder tables and rows of a
 * module.
 *
 * @author Andrea Di Giorgi
 * @goal clean-service-builder
 */
public class CleanServiceBuilderMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			_cleanServiceBuilderCommand.execute(_dbSupportArgs);
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
		}
	}

	/**
	 * The user password for connecting to the Liferay database.
	 *
	 * @parameter
	 */
	public void setPassword(String password) {
		_dbSupportArgs.setPassword(password);
	}

	/**
	 * The portal-ext.properties file which contains the JDBC settings for
	 * connecting to the Liferay database.
	 *
	 * @parameter
	 */
	public void setPropertiesFile(File propertiesFile) throws IOException {
		_dbSupportArgs.setPropertiesFile(propertiesFile);
	}

	/**
	 * The service.xml file.
	 *
	 * @parameter default-value="${project.basedir}/service.xml"
	 * @required
	 */
	public void setServiceXmlFile(File serviceXmlFile) {
		_cleanServiceBuilderCommand.setServiceXmlFile(serviceXmlFile);
	}

	/**
	 * The servlet context name (usually the value of the
	 * \"Bundle-Symbolic-Name\" manifest header) of the module.
	 *
	 * @parameter default-value="${project.artifactId}"
	 * @required
	 */
	public void setServletContextName(String servletContextName) {
		_cleanServiceBuilderCommand.setServletContextName(servletContextName);
	}

	/**
	 * The JDBC URL for connecting to the Liferay database.
	 *
	 * @parameter
	 */
	public void setUrl(String url) {
		_dbSupportArgs.setUrl(url);
	}

	/**
	 * The user name for connecting to the Liferay database.
	 *
	 * @parameter
	 */
	public void setUserName(String userName) {
		_dbSupportArgs.setUserName(userName);
	}

	private final CleanServiceBuilderCommand _cleanServiceBuilderCommand =
		new CleanServiceBuilderCommand();
	private final DBSupportArgs _dbSupportArgs = new DBSupportArgs();

}