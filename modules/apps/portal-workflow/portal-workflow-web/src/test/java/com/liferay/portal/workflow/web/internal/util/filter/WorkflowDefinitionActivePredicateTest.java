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

package com.liferay.portal.workflow.web.internal.util.filter;

import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Adam Brandizzi
 */
@RunWith(Parameterized.class)
public class WorkflowDefinitionActivePredicateTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "active={0}, expectedResult={1}, status={2}"
	)
	public static List<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{true, true, WorkflowConstants.STATUS_ANY},
				{false, true, WorkflowConstants.STATUS_ANY},
				{true, false, WorkflowConstants.STATUS_DRAFT},
				{false, true, WorkflowConstants.STATUS_DRAFT},
				{false, false, WorkflowConstants.STATUS_APPROVED},
				{true, true, WorkflowConstants.STATUS_APPROVED}
			});
	}

	@Test
	public void testFilter() {
		WorkflowDefinitionActivePredicate predicate =
			new WorkflowDefinitionActivePredicate(status);

		Assert.assertEquals(
			expectedResult, predicate.test(new WorkflowDefinitionImpl(active)));
	}

	@Parameterized.Parameter
	public boolean active;

	@Parameterized.Parameter(1)
	public boolean expectedResult;

	@Parameterized.Parameter(2)
	public int status;

}