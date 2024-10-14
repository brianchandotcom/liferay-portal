/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import com.liferay.jenkins.results.parser.test.clazz.TestClass;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JUnitAxisTestClassGroup extends AxisTestClassGroup {

	@Override
	public long getAverageTotalTestTaskDuration() {
		if (_averageTotalTestTaskDuration != null) {
			return _averageTotalTestTaskDuration;
		}

		_averageTotalTestTaskDuration = 0L;

		Set<String> testTaskDurations = new HashSet<>();

		for (TestClass testClass : getTestClasses()) {
			String testTaskName = testClass.getTestTaskName();

			if (testTaskDurations.contains(testTaskName)) {
				continue;
			}

			testTaskDurations.add(testTaskName);

			_averageTotalTestTaskDuration +=
				testClass.getAverageTestTaskDuration();
		}

		return _averageTotalTestTaskDuration;
	}

	protected JUnitAxisTestClassGroup(
		JSONObject jsonObject, SegmentTestClassGroup segmentTestClassGroup) {

		super(jsonObject, segmentTestClassGroup);
	}

	protected JUnitAxisTestClassGroup(
		JUnitBatchTestClassGroup jUnitBatchTestClassGroup) {

		super(jUnitBatchTestClassGroup);
	}

	private Long _averageTotalTestTaskDuration;

}