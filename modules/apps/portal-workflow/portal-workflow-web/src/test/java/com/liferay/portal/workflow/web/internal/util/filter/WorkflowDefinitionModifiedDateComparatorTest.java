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

import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.web.internal.util.comparator.WorkflowDefinitionModifiedDateComparator;

import java.util.Arrays;
import java.util.Calendar;
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
public class WorkflowDefinitionModifiedDateComparatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "ascending={0}, expectedResult={1}, increaseDate={2}, reverseComparator{3}"
	)
	public static List<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{true, 0, false, false}, {false, 0, false, false},
				{true, 1, true, true}, {false, -1, true, true},
				{true, -1, true, false}, {false, 1, true, false}
			});
	}

	@Test
	public void testCompare() {
		WorkflowDefinitionModifiedDateComparator comparator =
			new WorkflowDefinitionModifiedDateComparator(ascending);

		Calendar calendar = Calendar.getInstance();

		WorkflowDefinition workflowDefinition1 = new WorkflowDefinitionImpl(
			calendar.getTime());

		if (increaseDate) {
			calendar.add(Calendar.DATE, 1);
		}

		WorkflowDefinition workflowDefinition2 = new WorkflowDefinitionImpl(
			calendar.getTime());

		if (reverseComparator) {
			Assert.assertEquals(
				expectedResult,
				comparator.compare(workflowDefinition2, workflowDefinition1));
		}
		else {
			Assert.assertEquals(
				expectedResult,
				comparator.compare(workflowDefinition1, workflowDefinition2));
		}
	}

	@Parameterized.Parameter
	public boolean ascending;

	@Parameterized.Parameter(1)
	public int expectedResult;

	@Parameterized.Parameter(2)
	public boolean increaseDate;

	@Parameterized.Parameter(3)
	public boolean reverseComparator;

}