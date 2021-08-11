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