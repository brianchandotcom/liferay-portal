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

package com.liferay.portal.workflow.kaleo.runtime.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.Collection;
import java.util.Iterator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class WorkflowDefinitionManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowDefinitionManagerTest.class);

		_bundleContext = bundle.getBundleContext();

		int count = 0;

		do {
			Collection<ServiceReference<WorkflowDefinitionManager>>
				serviceReferences = _bundleContext.getServiceReferences(
					WorkflowDefinitionManager.class, "(proxy.bean=false)");

			if (serviceReferences.isEmpty()) {
				count++;

				if (count >= 5) {
					throw new IllegalStateException(
						"Unable to get the reference to workflow definition " +
							"manager");
				}

				Thread.sleep(500);
			}

			Iterator<ServiceReference<WorkflowDefinitionManager>> iterator =
				serviceReferences.iterator();

			_serviceReference = iterator.next();
		}
		while (_serviceReference == null);

		_workflowDefinitionManager = _bundleContext.getService(
			_serviceReference);
	}

	@After
	public void tearDown() throws Exception {
		_bundleContext.ungetService(_serviceReference);

		_bundleContext = null;
	}

	@Test
	public void testValidateIncomingTransitionInitialStateDefinition()
		throws Exception {

		InputStream inputStream = getResource("incoming-initial-state.xml");

		String error = assertInvalid(inputStream);

		assertEquals(
			"An incoming transition was found for initial state start", error);
	}

	@Test
	public void testValidateIncomingTransitionsJoinNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource(
			"incoming-transitions-join-1.xml");

		String error = assertInvalid(inputStream);

		assertEquals("There are errors between fork fork and join join", error);

		inputStream = getResource("incoming-transitions-join-2.xml");

		error = assertInvalid(inputStream);

		assertEquals(
			"There are errors between fork fork1 and join join1", error);

		inputStream = getResource("incoming-transitions-join-3.xml");

		error = assertInvalid(inputStream);

		assertEquals(
			"There are errors between fork fork1 and join join", error);

		inputStream = getResource("incoming-transitions-join-4.xml");

		error = assertInvalid(inputStream);

		assertEquals("There are errors between fork fork and join join", error);

		inputStream = getResource("incoming-transitions-join-5.xml");

		error = assertInvalid(inputStream);

		assertEquals(
			"There are errors between fork fork and join fork Join", error);

		inputStream = getResource("incoming-transitions-join-6.xml");

		assertValid(inputStream);

		inputStream = getResource("incoming-transitions-join-7.xml");

		assertValid(inputStream);
	}

	@Test
	public void testValidateLegalMarketingDefinition() throws Exception {
		InputStream inputStream = getResource("legal-marketing-definition.xml");

		assertValid(inputStream);
	}

	@Test
	public void testValidateLessThanTwoOutgoingConditionNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource(
			"less-than-two-outgoing-condition.xml");

		String error = assertInvalid(inputStream);

		assertEquals(
			"Less than 2 outgoing transitions found for condition condition",
			error);
	}

	@Test
	public void testValidateLessThanTwoOutgoingForkNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource(
			"less-than-two-outgoing-fork.xml");

		String error = assertInvalid(inputStream);

		assertEquals(
			"Less than 2 outgoing transitions found for fork fork", error);
	}

	@Test
	public void testValidateMatchingForkAndJoins() throws Exception {
		InputStream inputStream = getResource("matching-fork-and-join-1.xml");

		String error = assertInvalid(inputStream);

		assertEquals("Fork fork2 and join join1 are not paired", error);

		inputStream = getResource("matching-fork-and-join-2.xml");

		error = assertInvalid(inputStream);

		assertEquals("Fork fork2 and join join1 are not paired", error);

		inputStream = getResource("matching-fork-and-join-3.xml");

		error = assertInvalid(inputStream);

		assertEquals("Fork fork3 and join join6 are not paired", error);
	}

	@Test
	public void testValidateMultipleInitialStatesDefinedDefinition()
		throws Exception {

		InputStream inputStream = getResource("multiple-initial-states.xml");

		String error = assertInvalid(inputStream);

		assertEquals("Multiple initial states start1 and start2", error);
	}

	@Test
	public void testValidateNoAssignmentsTaskNodeDefinition() throws Exception {
		InputStream inputStream = getResource("no-assignments-task.xml");

		String error = assertInvalid(inputStream);

		assertEquals("No assignments for task task", error);
	}

	@Test
	public void testValidateNoIncomingTransitionConditionNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource("no-incoming-condition.xml");

		String error = assertInvalid(inputStream);

		assertEquals(
			"No incoming transition found for condition condition", error);
	}

	@Test
	public void testValidateNoIncomingTransitionForkNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource("no-incoming-fork.xml");

		String error = assertInvalid(inputStream);

		assertEquals("No incoming transition found for fork fork", error);
	}

	@Test
	public void testValidateNoIncomingTransitionStateNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource("no-incoming-state.xml");

		String error = assertInvalid(inputStream);

		assertEquals("No incoming transition found for state state", error);
	}

	@Test
	public void testValidateNoIncomingTransitionTaskNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource("no-incoming-task.xml");

		String error = assertInvalid(inputStream);

		assertEquals("No incoming transition found for task task", error);
	}

	@Test
	public void testValidateNoInitialStateDefinedDefinition() throws Exception {
		InputStream inputStream = getResource("no-initial-state.xml");

		String error = assertInvalid(inputStream);

		assertEquals("No initial state defined", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionInitialStateDefinition()
		throws Exception {

		InputStream inputStream = getResource("no-outgoing-initial-state.xml");

		String error = assertInvalid(inputStream);

		assertEquals(
			"No outgoing transition found for initial state start", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionStartNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource("no-outgoing-start-node.xml");

		String error = assertInvalid(inputStream);

		assertEquals(
			"No outgoing transition found for initial state start", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionTaskNodeDefinition()
		throws Exception {

		InputStream inputStream = getResource("no-outgoing-task.xml");

		String error = assertInvalid(inputStream);

		assertEquals("Unable to parse definition", error);
	}

	@Test
	public void testValidateNoTerminalStatesDefinition() throws Exception {
		InputStream inputStream = getResource("no-terminal-states.xml");

		String error = assertInvalid(inputStream);

		assertEquals("No terminal states defined", error);
	}

	@Test
	public void testValidateSingleApproverDefinition() throws Exception {
		InputStream inputStream = getResource("single-approver-definition.xml");

		assertValid(inputStream);
	}

	@Test
	public void testValidateSingleApproverScriptedAssignmentDefinition()
		throws Exception {

		InputStream inputStream = getResource(
			"single-approver-definition-scripted-assignment.xml");

		assertValid(inputStream);
	}

	@Test
	public void testValidateTransitions() throws Exception {
		InputStream inputStream = getResource("invalid-transition.xml");

		String error = assertInvalid(inputStream);

		assertEquals("Unable to find target node for transition end", error);
	}

	@Test
	public void testValidateUnbalancedForkAndJoinNodes() throws Exception {
		InputStream inputStream = getResource("unbalanced-fork-and-join.xml");

		String error = assertInvalid(inputStream);

		assertEquals("There are unbalanced fork and join nodes", error);
	}

	@Test
	public void testValidateValidDefinition() throws Exception {
		InputStream inputStream = getResource("valid-definition.xml");

		assertValid(inputStream);
	}

	protected void assertEquals(String expectedMessage, String actualMessage) {
		Assert.assertEquals(expectedMessage, actualMessage);
	}

	protected String assertInvalid(InputStream inputStream) throws Exception {
		byte[] bytes = FileUtil.getBytes(inputStream);

		try {
			_workflowDefinitionManager.validateWorkflowDefinition(bytes);

			Assert.fail();
		}
		catch (WorkflowException we) {
			Throwable throwable = we.getCause();

			return throwable.getMessage();
		}

		return null;
	}

	protected void assertValid(InputStream inputStream) throws Exception {
		byte[] bytes = FileUtil.getBytes(inputStream);

		_workflowDefinitionManager.validateWorkflowDefinition(bytes);
	}

	protected InputStream getResource(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/portal/workflow/kaleo/runtime/dependencies/" + name);
	}

	private BundleContext _bundleContext;
	private ServiceReference<WorkflowDefinitionManager> _serviceReference;
	private WorkflowDefinitionManager _workflowDefinitionManager;

}