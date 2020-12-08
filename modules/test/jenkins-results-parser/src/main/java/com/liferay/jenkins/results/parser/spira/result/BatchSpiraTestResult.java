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

package com.liferay.jenkins.results.parser.spira.result;

import com.liferay.jenkins.results.parser.test.clazz.group.AxisTestClassGroup;

/**
 * @author Michael Hashimoto
 */
public class BatchSpiraTestResult extends BaseSpiraTestResult {

	@Override
	public String getTestName() {
		return getAxisName();
	}

	protected BatchSpiraTestResult(
		SpiraBuildResult spiraBuildResult,
		AxisTestClassGroup axisTestClassGroup) {

		super(spiraBuildResult, axisTestClassGroup);
	}

}