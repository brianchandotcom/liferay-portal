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
import com.liferay.portal.workflow.web.internal.util.comparator.WorkflowDefinitionModifiedDateComparator;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class WorkflowDefinitionModifiedDateComparatorTest {

	@Test
	public void testCompareEqualsAscending() {
		WorkflowDefinitionModifiedDateComparator comparator =
			new WorkflowDefinitionModifiedDateComparator(true);

		Calendar calendar = Calendar.getInstance();

		WorkflowDefinition workflowDefinition1 = new WorkflowDefinitionImpl(
			calendar.getTime());

		WorkflowDefinition workflowDefinition2 = new WorkflowDefinitionImpl(
			calendar.getTime());

		int result = comparator.compare(
			workflowDefinition2, workflowDefinition1);

		Assert.assertEquals(0, result);
	}

	@Test
	public void testCompareEqualsDescending() {
		WorkflowDefinitionModifiedDateComparator comparator =
			new WorkflowDefinitionModifiedDateComparator(false);

		Calendar calendar = Calendar.getInstance();

		WorkflowDefinition workflowDefinition1 = new WorkflowDefinitionImpl(
			calendar.getTime());

		WorkflowDefinition workflowDefinition2 = new WorkflowDefinitionImpl(
			calendar.getTime());

		int result = comparator.compare(
			workflowDefinition2, workflowDefinition1);

		Assert.assertEquals(0, result);
	}

	@Test
	public void testCompareNewerOlderAscending() {
		WorkflowDefinitionModifiedDateComparator comparator =
			new WorkflowDefinitionModifiedDateComparator(true);

		Calendar calendar = Calendar.getInstance();

		WorkflowDefinition workflowDefinition1 = new WorkflowDefinitionImpl(
			calendar.getTime());

		calendar.add(Calendar.DATE, 1);

		WorkflowDefinition workflowDefinition2 = new WorkflowDefinitionImpl(
			calendar.getTime());

		int result = comparator.compare(
			workflowDefinition2, workflowDefinition1);

		Assert.assertEquals(1, result);
	}

	@Test
	public void testCompareNewerOlderDescending() {
		WorkflowDefinitionModifiedDateComparator comparator =
			new WorkflowDefinitionModifiedDateComparator(false);

		Calendar calendar = Calendar.getInstance();

		WorkflowDefinition workflowDefinition1 = new WorkflowDefinitionImpl(
			calendar.getTime());

		calendar.add(Calendar.DATE, 1);

		WorkflowDefinition workflowDefinition2 = new WorkflowDefinitionImpl(
			calendar.getTime());

		int result = comparator.compare(
			workflowDefinition2, workflowDefinition1);

		Assert.assertEquals(-1, result);
	}

	@Test
	public void testCompareOlderNewerAscending() {
		WorkflowDefinitionModifiedDateComparator comparator =
			new WorkflowDefinitionModifiedDateComparator(true);

		Calendar calendar = Calendar.getInstance();

		WorkflowDefinition workflowDefinition1 = new WorkflowDefinitionImpl(
			calendar.getTime());

		calendar.add(Calendar.DATE, 1);

		WorkflowDefinition workflowDefinition2 = new WorkflowDefinitionImpl(
			calendar.getTime());

		int result = comparator.compare(
			workflowDefinition1, workflowDefinition2);

		Assert.assertEquals(-1, result);
	}

	@Test
	public void testCompareOlderNewerDescending() {
		WorkflowDefinitionModifiedDateComparator comparator =
			new WorkflowDefinitionModifiedDateComparator(false);

		Calendar calendar = Calendar.getInstance();

		WorkflowDefinition workflowDefinition1 = new WorkflowDefinitionImpl(
			calendar.getTime());

		calendar.add(Calendar.DATE, 1);

		WorkflowDefinition workflowDefinition2 = new WorkflowDefinitionImpl(
			calendar.getTime());

		int result = comparator.compare(
			workflowDefinition1, workflowDefinition2);

		Assert.assertEquals(1, result);
	}

}