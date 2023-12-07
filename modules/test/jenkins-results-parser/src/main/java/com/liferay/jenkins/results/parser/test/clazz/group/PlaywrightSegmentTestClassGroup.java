/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.test.clazz.group;

import org.json.JSONObject;

/**
 * @author Kenji Heigel
 */
public class PlaywrightSegmentTestClassGroup extends SegmentTestClassGroup {

	@Override
	public String getTestCasePropertiesContent() {
		StringBuilder sb = new StringBuilder();

		sb.append(super.getTestCasePropertiesContent());

		int axisCount = getAxisCount();

		if (axisCount > 1) {
			for (int axisIndex = 0; axisIndex < getAxisCount(); axisIndex++) {
				sb.append("PLAYWRIGHT_ARGS_");
				sb.append(axisIndex);
				sb.append("=--shard=");
				sb.append(axisIndex + 1);
				sb.append("/");
				sb.append(axisCount);
				sb.append("\n");
			}
		}

		return sb.toString();
	}

	protected PlaywrightSegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup) {

		super(parentBatchTestClassGroup);
	}

	protected PlaywrightSegmentTestClassGroup(
		BatchTestClassGroup parentBatchTestClassGroup, JSONObject jsonObject) {

		super(parentBatchTestClassGroup, jsonObject);
	}

}