/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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