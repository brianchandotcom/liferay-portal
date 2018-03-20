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

package com.liferay.jenkins.results.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Michael Hashimoto
 */
public class TestBatchGroup {

	public TestBatchGroup(
		GitWorkingDirectory gitWorkingDirectory, String batchName) {

		_gitWorkingDirectory = gitWorkingDirectory;

		_batchName = batchName;

		_portalTestProperties =
			_gitWorkingDirectory.getGitWorkingDirectoryProperties(
				"test.properties");
	}

	public String getBatchName() {
		return _batchName;
	}

	public GitWorkingDirectory getGitWorkingDirectory() {
		return _gitWorkingDirectory;
	}

	public Properties getPortalTestProperties() {
		return _portalTestProperties;
	}

	public List<String> getTestClassList(int i) {
		List<String> list = new ArrayList<>();

		list.add(
			"com/liferay/portlet/SecurityPortletContainerWrapperTest.class");
		list.add(
			"com/liferay/portal/kernel/cal/RecurrenceSerializerTest.class");
		list.add("com/liferay/portal/util/HtmlImplTest.class");

		return list;
	}

	public int getTestClassListCount() {
		return 3;
	}

	private final String _batchName;
	private final GitWorkingDirectory _gitWorkingDirectory;
	private final Properties _portalTestProperties;

}