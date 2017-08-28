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

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class WorkflowDefinitionNamePredicateFilterTest {

	@Test
	public void testFilterWithoutSpace1() {
		WorkflowDefinitionNamePredicateFilter filter =
			new WorkflowDefinitionNamePredicateFilter("Single");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			"Single Approver", null);

		boolean result = filter.filter(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterWithoutSpace2() {
		WorkflowDefinitionNamePredicateFilter filter =
			new WorkflowDefinitionNamePredicateFilter("Appr");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			"Single Approver", null);

		boolean result = filter.filter(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterWithoutSpace3() {
		WorkflowDefinitionNamePredicateFilter filter =
			new WorkflowDefinitionNamePredicateFilter("Approver");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			"A Different Definition", null);

		boolean result = filter.filter(workflowDefinition);

		Assert.assertFalse(result);
	}

	@Test
	public void testFilterWithSpace1() {
		WorkflowDefinitionNamePredicateFilter filter =
			new WorkflowDefinitionNamePredicateFilter("Single Approver");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			"Single Approver Definition", null);

		boolean result = filter.filter(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterWithSpace2() {
		WorkflowDefinitionNamePredicateFilter filter =
			new WorkflowDefinitionNamePredicateFilter("Single Approver");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			"A Different Definition", null);

		boolean result = filter.filter(workflowDefinition);

		Assert.assertFalse(result);
	}

}