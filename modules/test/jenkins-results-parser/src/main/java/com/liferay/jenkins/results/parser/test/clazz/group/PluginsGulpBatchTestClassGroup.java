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

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;
import com.liferay.jenkins.results.parser.PluginsGitWorkingDirectory;
import com.liferay.jenkins.results.parser.PortalTestClassJob;
import com.liferay.jenkins.results.parser.test.clazz.TestClassFactory;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsGulpBatchTestClassGroup extends BatchTestClassGroup {

	@Override
	public JSONObject getJSONObject() {
		if (jsonObject != null) {
			return jsonObject;
		}

		jsonObject = super.getJSONObject();

		jsonObject.put("modified_files_list", _modifiedFilesList);

		return jsonObject;
	}

	public List<File> getTestBaseDirNames() {
		List<File> testBaseDirNames = new ArrayList<>();

		for (File modifiedFile : _modifiedFilesList) {
			File parentDir = new File(modifiedFile.getPath());

			while (parentDir != null) {
				File gulpFile = new File(parentDir, "gulpfile.js");

				if (gulpFile.exists()) {
					testBaseDirNames.add(gulpFile.getParentFile());
				}

				parentDir = parentDir.getParentFile();
			}
		}

		return testBaseDirNames;
	}

	protected PluginsGulpBatchTestClassGroup(
		JSONObject jsonObject, PortalTestClassJob portalTestClassJob) {

		super(jsonObject, portalTestClassJob);

		_modifiedFilesList = new ArrayList<>();

		JSONArray modifiedFilesJSONArray = jsonObject.optJSONArray(
			"modified_files_list");

		if ((modifiedFilesJSONArray == null) ||
			modifiedFilesJSONArray.isEmpty()) {

			return;
		}

		for (int i = 0; i < modifiedFilesJSONArray.length(); i++) {
			String modifiedFilePath = modifiedFilesJSONArray.getString(i);

			if (JenkinsResultsParserUtil.isNullOrEmpty(modifiedFilePath)) {
				continue;
			}

			_modifiedFilesList.add(new File(modifiedFilePath));
		}
	}

	protected PluginsGulpBatchTestClassGroup(
		String batchName, PortalTestClassJob portalTestClassJob) {

		super(batchName, portalTestClassJob);

		_modifiedFilesList = portalGitWorkingDirectory.getModifiedFilesList();

		PluginsGitWorkingDirectory pluginsGitWorkingDirectory =
			portalGitWorkingDirectory.getPluginsGitWorkingDirectory();

		_modifiedFilesList.addAll(
			pluginsGitWorkingDirectory.getModifiedFilesList());

		_setTestClasses();

		setAxisTestClassGroups();

		setSegmentTestClassGroups();
	}

	@Override
	protected int getAxisMaxSize() {
		return 1;
	}

	@Override
	protected int getSegmentMaxChildren() {
		return 1;
	}

	private void _setTestClasses() {
		for (File testBaseDirName : getTestBaseDirNames()) {
			testClasses.add(
				TestClassFactory.newTestClass(this, testBaseDirName));
		}

		Collections.sort(testClasses);
	}

	private final List<File> _modifiedFilesList;

}