/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.util.filter;

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
 * @author Leonardo Barros
 */
@RunWith(Parameterized.class)
public class WorkflowDefinitionTitlePredicateTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "expectedResult={0}, keywords={1}, title={2}"
	)
	public static List<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{true, "Single", "Single Approver"},
				{true, "Appr", "Single Approver"},
				{false, "Approver", "A Different Definition"},
				{true, "Single Approver", "Single Approver Definition"},
				{false, "Single Approver", "A Different Definition"}
			});
	}

	@Test
	public void testFilter() {
		WorkflowDefinitionTitlePredicate predicate =
			new WorkflowDefinitionTitlePredicate(keywords);

		Assert.assertEquals(
			expectedResult,
			predicate.test(new WorkflowDefinitionImpl(null, title)));
	}

	@Parameterized.Parameter
	public boolean expectedResult;

	@Parameterized.Parameter(1)
	public String keywords;

	@Parameterized.Parameter(2)
	public String title;

}