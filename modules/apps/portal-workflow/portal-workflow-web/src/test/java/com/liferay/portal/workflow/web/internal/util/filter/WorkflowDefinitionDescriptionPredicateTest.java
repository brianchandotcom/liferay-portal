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
 * @author Adam Brandizzi
 */
@RunWith(Parameterized.class)
public class WorkflowDefinitionDescriptionPredicateTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "description={0}, expectedResult={1}, keywords={2}, title={3}"
	)
	public static List<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"Default Single Approver", true, "Default", "Single Approver"},
				{"Default Single Approver", true, "Def", "Single Approver"},
				{"Not that one", false, "Approver", "A Different Definition"},
				{
					"Single Approver by Default Default ", true,
					"Single Approver", "Single Approver Definition"
				},
				{
					"Not that one", false, "Single Approver",
					"A Different Definition"
				}
			});
	}

	@Test
	public void testFilter() {
		WorkflowDefinitionDescriptionPredicate predicate =
			new WorkflowDefinitionDescriptionPredicate(keywords);

		Assert.assertEquals(
			expectedResult,
			predicate.test(
				new WorkflowDefinitionImpl(null, title, description)));
	}

	@Parameterized.Parameter
	public String description;

	@Parameterized.Parameter(1)
	public boolean expectedResult;

	@Parameterized.Parameter(2)
	public String keywords;

	@Parameterized.Parameter(3)
	public String title;

}