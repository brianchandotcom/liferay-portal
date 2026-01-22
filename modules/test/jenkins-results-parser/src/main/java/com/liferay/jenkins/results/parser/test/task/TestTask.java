/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.task;

import com.liferay.jenkins.results.parser.WeightedItem;
import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public interface TestTask extends WeightedItem {

	public void addTestClass(TestClass testClass);

	public long getAverageDuration();

	public long getAverageOverheadDuration();

	public long getAverageTotalDuration();

	public JSONObject getJSONObject();

	public long getLongestDuration();

	public String getName();

	public List<TestClass> getTestClasses();

	public boolean isSplit();

	public void setSplit(boolean split);

}