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
 * @author Adam Brandizzi
 */
public class WorkflowDefinitionDescriptionPredicateFilterTest {

	@Test
	public void testFilterWithoutSpace1() {
		WorkflowDefinitionDescriptionPredicateFilter filter =
			new WorkflowDefinitionDescriptionPredicateFilter("Default");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			null, "Single Approver", "Default Single Approver");

		boolean result = filter.filter(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterWithoutSpace2() {
		WorkflowDefinitionDescriptionPredicateFilter filter =
			new WorkflowDefinitionDescriptionPredicateFilter("Def");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			null, "Single Approver", "Default Single Approver");

		boolean result = filter.filter(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterWithoutSpace3() {
		WorkflowDefinitionDescriptionPredicateFilter filter =
			new WorkflowDefinitionDescriptionPredicateFilter("Approver");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			null, "A Different Definition", "Not that one");

		boolean result = filter.filter(workflowDefinition);

		Assert.assertFalse(result);
	}

	@Test
	public void testFilterWithSpace1() {
		WorkflowDefinitionDescriptionPredicateFilter filter =
			new WorkflowDefinitionDescriptionPredicateFilter("Single Approver");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			null, "Single Approver Definition",
			"Single Approver by Default Default ");

		boolean result = filter.filter(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterWithSpace2() {
		WorkflowDefinitionDescriptionPredicateFilter filter =
			new WorkflowDefinitionDescriptionPredicateFilter("Single Approver");

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			null, "A Different Definition", "Not that one");

		boolean result = filter.filter(workflowDefinition);

		Assert.assertFalse(result);
	}

}