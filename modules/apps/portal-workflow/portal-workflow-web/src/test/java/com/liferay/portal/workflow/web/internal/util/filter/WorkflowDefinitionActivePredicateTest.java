/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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