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

package com.liferay.portal.test.rule;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CompanyProviderClassTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRunMethodTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.rule.TimeoutTestRule;

import java.util.ArrayList;
import java.util.List;

import org.junit.rules.TestRule;

/**
 * @author Shuyang Zhou
 */
public class LiferayIntegrationTestRule extends AggregateTestRule {

	public LiferayIntegrationTestRule() {
		super(false, _getTestRules());
	}

	private static TestRule[] _getTestRules() {
		List<TestRule> testRules = new ArrayList<>();

		if (System.getenv("JENKINS_HOME") != null) {
			testRules.add(TimeoutTestRule.INSTANCE);
		}

		testRules.add(LogAssertionTestRule.INSTANCE);
		testRules.add(SybaseDumpTransactionLogTestRule.INSTANCE);
		testRules.add(ClearThreadLocalClassTestRule.INSTANCE);
		testRules.add(UniqueStringRandomizerBumperClassTestRule.INSTANCE);
		testRules.add(DestinationAwaitClassTestRule.INSTANCE);
		testRules.add(CompanyProviderClassTestRule.INSTANCE);
		testRules.add(DeleteAfterTestRunMethodTestRule.INSTANCE);
		testRules.add(SynchronousDestinationTestRule.INSTANCE);
		testRules.add(InjectTestRule.INSTANCE);

		return testRules.toArray(new TestRule[0]);
	}

}