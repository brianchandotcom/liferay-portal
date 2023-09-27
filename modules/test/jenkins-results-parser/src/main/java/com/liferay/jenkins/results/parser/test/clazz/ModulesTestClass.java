/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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