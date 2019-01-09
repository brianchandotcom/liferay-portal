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
import com.liferay.portal.workflow.web.internal.constants.WorkflowDefinitionConstants;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Adam Brandizzi
 */
public class WorkflowDefinitionActivePredicateTest {

	@Test
	public void testFilterAllIncludeActive() {
		WorkflowDefinitionActivePredicate predicate =
			new WorkflowDefinitionActivePredicate(
				WorkflowDefinitionConstants.STATUS_ALL);

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			true);

		boolean result = predicate.test(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterAllIncludeInactive() {
		WorkflowDefinitionActivePredicate predicate =
			new WorkflowDefinitionActivePredicate(
				WorkflowDefinitionConstants.STATUS_ALL);

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			false);

		boolean result = predicate.test(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterNotPublishedExcludeActive() {
		WorkflowDefinitionActivePredicate predicate =
			new WorkflowDefinitionActivePredicate(
				WorkflowDefinitionConstants.STATUS_NOT_PUBLISHED);

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			true);

		boolean result = predicate.test(workflowDefinition);

		Assert.assertFalse(result);
	}

	@Test
	public void testFilterNotPublishedIncludeInactive() {
		WorkflowDefinitionActivePredicate predicate =
			new WorkflowDefinitionActivePredicate(
				WorkflowDefinitionConstants.STATUS_NOT_PUBLISHED);

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			false);

		boolean result = predicate.test(workflowDefinition);

		Assert.assertTrue(result);
	}

	@Test
	public void testFilterPublishedExcludeInactive() {
		WorkflowDefinitionActivePredicate predicate =
			new WorkflowDefinitionActivePredicate(
				WorkflowDefinitionConstants.STATUS_PUBLISHED);

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			false);

		boolean result = predicate.test(workflowDefinition);

		Assert.assertFalse(result);
	}

	@Test
	public void testFilterPublishedIncludeActive() {
		WorkflowDefinitionActivePredicate predicate =
			new WorkflowDefinitionActivePredicate(
				WorkflowDefinitionConstants.STATUS_PUBLISHED);

		WorkflowDefinition workflowDefinition = new WorkflowDefinitionImpl(
			true);

		boolean result = predicate.test(workflowDefinition);

		Assert.assertTrue(result);
	}

}