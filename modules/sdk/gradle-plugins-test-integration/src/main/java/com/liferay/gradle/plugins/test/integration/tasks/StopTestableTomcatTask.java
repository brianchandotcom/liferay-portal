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

package com.liferay.gradle.plugins.test.integration.tasks;

import com.liferay.gradle.plugins.test.integration.internal.util.GradleUtil;

import java.io.File;

import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;

/**
 * @author Andrea Di Giorgi
 */
public class StopTestableTomcatTask
	extends StopAppServerTask implements ModuleFrameworkBaseDirSpec {

	@Input
	@Override
	public File getModuleFrameworkBaseDir() {
		return GradleUtil.toFile(getProject(), _moduleFrameworkBaseDir);
	}

	@Input
	public boolean isDeleteTestModules() {
		return _deleteTestModules;
	}

	public void setDeleteTestModules(boolean deleteTestModules) {
		_deleteTestModules = deleteTestModules;
	}

	@Override
	public void setModuleFrameworkBaseDir(Object moduleFrameworkBaseDir) {
		_moduleFrameworkBaseDir = moduleFrameworkBaseDir;
	}

	@Override
	public void stopAppServer() throws Exception {
		super.stopAppServer();

		if (isDeleteTestModules()) {
			_deleteTestModules();
		}
	}

	private void _deleteTestModules() {
		Logger logger = getLogger();
		File moduleFrameworkBaseDir = getModuleFrameworkBaseDir();

		File modulesDir = new File(moduleFrameworkBaseDir, "modules");
		File testDir = new File(moduleFrameworkBaseDir, "test");

		for (File file : testDir.listFiles()) {
			File moduleFile = new File(modulesDir, file.getName());

			if (!moduleFile.exists()) {
				continue;
			}

			boolean deleted = moduleFile.delete();

			if (!deleted && logger.isWarnEnabled()) {
				logger.warn("Unable to delete {}", moduleFile);
			}
		}
	}

	private boolean _deleteTestModules = true;
	private Object _moduleFrameworkBaseDir;

}