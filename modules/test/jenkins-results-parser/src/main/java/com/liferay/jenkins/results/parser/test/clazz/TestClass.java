/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.TestHistory;

import java.io.File;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface TestClass extends Comparable<TestClass> {

	public void addTestClassMethod(TestClassMethod testClassMethod);

	public long getAverageDuration();

	public long getAverageOverheadDuration();

	public long getAverageTestTaskDuration();

	public JSONObject getJSONObject();

	public String getName();

	public File getTestClassFile();

	public List<TestClassMethod> getTestClassMethods();

	public TestHistory getTestHistory();

	public String getTestTaskName();

	public boolean hasTestClassMethods();

	public boolean isIgnored();

}