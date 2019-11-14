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

package com.liferay.portal.workflow.kaleo.internal.runtime.integration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
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
						"Unable to get reference to a workflow definition " +
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

	@Test(expected = WorkflowException.class)
	public void testDeleteSaveWorkflowDefinition() throws Exception {
		WorkflowDefinition workflowDefinition = _saveWorkflowDefinition();

		_workflowDefinitionManager.undeployWorkflowDefinition(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			workflowDefinition.getName(), workflowDefinition.getVersion());

		_workflowDefinitionManager.getWorkflowDefinition(
			TestPropsValues.getCompanyId(), workflowDefinition.getName(),
			workflowDefinition.getVersion());
	}

	@Test
	public void testDeployWorkflowDraftDefinition() throws Exception {
		WorkflowDefinition workflowDefinition = _saveWorkflowDefinition();

		Assert.assertFalse(workflowDefinition.isActive());

		String content = workflowDefinition.getContent();

		WorkflowDefinition deployedWorkflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				TestPropsValues.getCompanyId(), workflowDefinition.getUserId(),
				workflowDefinition.getTitle(), workflowDefinition.getName(),
				content.getBytes());

		Assert.assertEquals(
			workflowDefinition.getName(), deployedWorkflowDefinition.getName());
	}

	@Test
	public void testSaveWorkflowDefinition() throws Exception {
		WorkflowDefinition workflowDefinition = _saveWorkflowDefinition();

		Assert.assertNotNull(workflowDefinition);
	}

	@Test
	public void testSaveWorkflowDefinitionIsNotActive() throws Exception {
		WorkflowDefinition workflowDefinition = _saveWorkflowDefinition();

		Assert.assertFalse(workflowDefinition.isActive());
	}

	@Test
	public void testSaveWorkflowDefinitionWithoutTitleAndContent()
		throws Exception {

		WorkflowDefinition workflowDefinition = _saveWorkflowDefinition(
			StringPool.BLANK, StringPool.BLANK.getBytes());

		Assert.assertNotNull(workflowDefinition);
	}

	@Test
	public void testValidateEmptyNotificationTemplateDefinition()
		throws Exception {

		InputStream inputStream = _getResource(
			"single-approver-definition-empty-notification-template.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The review node has a empty notification template", error);
	}

	@Test
	public void testValidateIncomingTransitionInitialStateDefinition()
		throws Exception {

		InputStream inputStream = _getResource("incoming-initial-state.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The start node cannot have an incoming transition", error);
	}

	@Test
	public void testValidateIncomingTransitionsJoinNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource(
			"incoming-transitions-join-1.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork and join node join",
			error);

		inputStream = _getResource("incoming-transitions-join-2.xml");

		error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork1 and join node join1",
			error);

		inputStream = _getResource("incoming-transitions-join-3.xml");

		error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork1 and join node join",
			error);

		inputStream = _getResource("incoming-transitions-join-4.xml");

		error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork and join node join",
			error);

		inputStream = _getResource("incoming-transitions-join-5.xml");

		error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork and join node fork Join",
			error);

		inputStream = _getResource("incoming-transitions-join-6.xml");

		_assertValid(inputStream);

		inputStream = _getResource("incoming-transitions-join-7.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateJoinXorDefinition() throws Exception {
		InputStream inputStream = _getResource("join-xor-definition.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateLegalMarketingDefinition() throws Exception {
		InputStream inputStream = _getResource(
			"legal-marketing-definition.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateLessThanTwoOutgoingConditionNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource(
			"less-than-two-outgoing-condition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The condition node must have at least 2 outgoing transitions",
			error);
	}

	@Test
	public void testValidateLessThanTwoOutgoingForkNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource(
			"less-than-two-outgoing-fork.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The fork node must have at least 2 outgoing transitions", error);
	}

	@Test
	public void testValidateMatchingForkAndJoins() throws Exception {
		InputStream inputStream = _getResource("matching-fork-and-join-1.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("Fork fork2 and join join1 nodes must be paired", error);

		inputStream = _getResource("matching-fork-and-join-2.xml");

		error = _assertInvalid(inputStream);

		_assertEquals("Fork fork2 and join join1 nodes must be paired", error);

		inputStream = _getResource("matching-fork-and-join-3.xml");

		error = _assertInvalid(inputStream);

		_assertEquals("Fork fork3 and join join6 nodes must be paired", error);
	}

	@Test
	public void testValidateMultipleInitialStatesDefinedDefinition()
		throws Exception {

		InputStream inputStream = _getResource("multiple-initial-states.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The workflow has too many start nodes (state nodes start1 and " +
				"start2)",
			error);
	}

	@Test
	public void testValidateNoAssignmentsTaskNodeDefinition() throws Exception {
		InputStream inputStream = _getResource("no-assignments-task.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"Specify at least one assignment for the task task node", error);
	}

	@Test
	public void testValidateNoIncomingTransitionConditionNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource("no-incoming-condition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The condition node must have an incoming transition", error);
	}

	@Test
	public void testValidateNoIncomingTransitionForkNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource("no-incoming-fork.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The fork node must have an incoming transition", error);
	}

	@Test
	public void testValidateNoIncomingTransitionStateNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource("no-incoming-state.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The state node must have an incoming transition", error);
	}

	@Test
	public void testValidateNoIncomingTransitionTaskNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource("no-incoming-task.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The task node must have an incoming transition", error);
	}

	@Test
	public void testValidateNoInitialStateDefinedDefinition() throws Exception {
		InputStream inputStream = _getResource("no-initial-state.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("You must define a start node", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionInitialStateDefinition()
		throws Exception {

		InputStream inputStream = _getResource("no-outgoing-initial-state.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The start node must have an outgoing transition", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionStartNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource("no-outgoing-start-node.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The start node must have an outgoing transition", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionTaskNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResource("no-outgoing-task.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("Unable to parse definition", error);
	}

	@Test
	public void testValidateNoTerminalStatesDefinition() throws Exception {
		InputStream inputStream = _getResource("no-terminal-states.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("You must define an end node", error);
	}

	@Test
	public void testValidateSingleApproverDefinition() throws Exception {
		InputStream inputStream = _getResource(
			"single-approver-definition.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateSingleApproverScriptedAssignmentDefinition()
		throws Exception {

		InputStream inputStream = _getResource(
			"single-approver-definition-scripted-assignment.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateTransitions() throws Exception {
		InputStream inputStream = _getResource("invalid-transition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The end transition must end at a node", error);
	}

	@Test
	public void testValidateUnbalancedForkAndJoinNodes() throws Exception {
		InputStream inputStream = _getResource("unbalanced-fork-and-join.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"Each fork node requires a join node. Make sure all forks and" +
				"joins are properly paired",
			error);
	}

	@Test
	public void testValidateValidDefinition() throws Exception {
		InputStream inputStream = _getResource("valid-definition.xml");

		_assertValid(inputStream);
	}

	private void _assertEquals(String expectedMessage, String actualMessage) {
		Assert.assertEquals(expectedMessage, actualMessage);
	}

	private String _assertInvalid(InputStream inputStream) throws Exception {
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

	private void _assertValid(InputStream inputStream) throws Exception {
		byte[] bytes = FileUtil.getBytes(inputStream);

		_workflowDefinitionManager.validateWorkflowDefinition(bytes);
	}

	private InputStream _getResource(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/portal/workflow/kaleo/dependencies/" + name);
	}

	private WorkflowDefinition _saveWorkflowDefinition() throws Exception {
		InputStream inputStream = _getResource(
			"single-approver-definition.xml");

		byte[] content = FileUtil.getBytes(inputStream);

		return _saveWorkflowDefinition(StringUtil.randomId(), content);
	}

	private WorkflowDefinition _saveWorkflowDefinition(
			String title, byte[] bytes)
		throws Exception {

		return _workflowDefinitionManager.saveWorkflowDefinition(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), title,
			StringUtil.randomId(), bytes);
	}

	private BundleContext _bundleContext;
	private ServiceReference<WorkflowDefinitionManager> _serviceReference;
	private WorkflowDefinitionManager _workflowDefinitionManager;

}