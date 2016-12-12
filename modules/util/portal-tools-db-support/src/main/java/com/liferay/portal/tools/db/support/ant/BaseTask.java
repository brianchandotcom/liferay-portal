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

import com.liferay.portal.tools.db.support.DBSupportArgs;

import java.io.File;
import java.io.IOException;

import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseTask extends Task {

	public void setPassword(String password) {
		dbSupportArgs.setPassword(password);
	}

	public void setPropertiesFile(File propertiesFile) throws IOException {
		dbSupportArgs.setPropertiesFile(propertiesFile);
	}

	public void setUrl(String url) {
		dbSupportArgs.setUrl(url);
	}

	public void setUserName(String userName) {
		dbSupportArgs.setUserName(userName);
	}

	protected final DBSupportArgs dbSupportArgs = new DBSupportArgs();

}