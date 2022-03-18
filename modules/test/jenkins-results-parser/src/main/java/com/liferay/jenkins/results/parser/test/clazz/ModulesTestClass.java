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

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PortalGitWorkingDirectory;
import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;

import java.nio.file.Path;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class ModulesTestClass extends BaseTestClass {

	@Override
	public JSONObject getJSONObject() {
		JSONObject jsonObject = super.getJSONObject();

		jsonObject.put("task_name", _taskName);

		return jsonObject;
	}

	protected ModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, File moduleBaseDir,
		String taskName) {

		super(batchTestClassGroup, moduleBaseDir);

		_taskName = taskName;

		for (File modulesProjectDir : getModulesProjectDirs()) {
			String path = JenkinsResultsParserUtil.getPathRelativeTo(
				modulesProjectDir, getPortalModulesBaseDir());

			String moduleTaskCall = JenkinsResultsParserUtil.combine(
				":", path.replaceAll("/", ":"), ":", getTaskName());

			addTestClassMethod(moduleTaskCall);
		}
	}

	protected ModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);

		_taskName = jsonObject.getString("task_name");
	}

	protected File getModuleBaseDir() {
		return getTestClassFile();
	}

	protected Path getModuleBaseDirPath() {
		File testClassFile = getTestClassFile();

		return testClassFile.toPath();
	}

	protected abstract List<File> getModulesProjectDirs();

	protected File getPortalModulesBaseDir() {
		PortalGitWorkingDirectory portalGitWorkingDirectory =
			getPortalGitWorkingDirectory();

		return new File(
			portalGitWorkingDirectory.getWorkingDirectory(), "modules");
	}

	protected String getTaskName() {
		return _taskName;
	}

	private final String _taskName;

}