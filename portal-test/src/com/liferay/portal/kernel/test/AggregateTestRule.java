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

package com.liferay.portal.kernel.test;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Shuyang Zhou
 */
public class AggregateTestRule implements TestRule {

	public AggregateTestRule(TestRule... testRules) {
		if (testRules == null) {
			throw new NullPointerException("Test rules is null");
		}

		if (testRules.length < 2) {
			throw new IllegalArgumentException(
				"Rule number " + testRules.length + " is less than 2");
		}

		_testRules = testRules;
	}

	@Override
	public Statement apply(Statement statement, Description description) {
		for (int i = _testRules.length - 1; i >= 0; i--) {
			statement = _testRules[i].apply(statement, description);
		}

		return statement;
	}

	private final TestRule[] _testRules;

}