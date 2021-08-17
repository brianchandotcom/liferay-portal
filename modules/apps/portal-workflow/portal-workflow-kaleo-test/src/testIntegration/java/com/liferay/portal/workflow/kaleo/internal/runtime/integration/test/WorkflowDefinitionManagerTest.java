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
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowDefinitionManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = WorkflowException.class)
	public void testDeleteSaveWorkflowDefinition() throws Exception {
		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			WorkflowDefinition workflowDefinition = _saveWorkflowDefinition();

			_workflowDefinitionManager.undeployWorkflowDefinition(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				workflowDefinition.getName(), workflowDefinition.getVersion());

			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(), workflowDefinition.getName(),
				workflowDefinition.getVersion());
		}
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

		InputStream inputStream = _getResourceInputStream(
			"single-approver-empty-notification-template-workflow-definition." +
				"xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The review node has a empty notification template", error);
	}

	@Test
	public void testValidateIncomingTransitionInitialStateDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"incoming-initial-state-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The start node cannot have an incoming transition", error);
	}

	@Test
	public void testValidateIncomingTransitionsJoinNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"incoming-transitions-join-1-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork and join node join",
			error);

		inputStream = _getResourceInputStream(
			"incoming-transitions-join-2-workflow-definition.xml");

		error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork1 and join node join1",
			error);

		inputStream = _getResourceInputStream(
			"incoming-transitions-join-3-workflow-definition.xml");

		error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork1 and join node join",
			error);

		inputStream = _getResourceInputStream(
			"incoming-transitions-join-4-workflow-definition.xml");

		error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork and join node join",
			error);

		inputStream = _getResourceInputStream(
			"incoming-transitions-join-5-workflow-definition.xml");

		error = _assertInvalid(inputStream);

		_assertEquals(
			"Fix the errors between the fork node fork and join node fork Join",
			error);

		inputStream = _getResourceInputStream(
			"incoming-transitions-join-6-workflow-definition.xml");

		_assertValid(inputStream);

		inputStream = _getResourceInputStream(
			"incoming-transitions-join-7-workflow-definition.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateJoinXorDefinition() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"join-xor-workflow-definition.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateLegalMarketingDefinition() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"legal-marketing-workflow-definition.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateLessThanTwoOutgoingConditionNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"less-than-two-outgoing-condition-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The condition node must have at least 2 outgoing transitions",
			error);
	}

	@Test
	public void testValidateLessThanTwoOutgoingForkNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"less-than-two-outgoing-fork-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The fork node must have at least 2 outgoing transitions", error);
	}

	@Test
	public void testValidateMatchingForkAndJoins() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"matching-fork-and-join-1-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("Fork fork2 and join join1 nodes must be paired", error);

		inputStream = _getResourceInputStream(
			"matching-fork-and-join-2-workflow-definition.xml");

		error = _assertInvalid(inputStream);

		_assertEquals("Fork fork2 and join join1 nodes must be paired", error);

		inputStream = _getResourceInputStream(
			"matching-fork-and-join-3-workflow-definition.xml");

		error = _assertInvalid(inputStream);

		_assertEquals("Fork fork3 and join join5 nodes must be paired", error);
	}

	@Test
	public void testValidateMultipleInitialStatesDefinedDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"multiple-initial-states-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The workflow has too many start nodes (state nodes start1 and " +
				"start2)",
			error);
	}

	@Test
	public void testValidateNoAssignmentsTaskNodeDefinition() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"no-assignments-task-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"Specify at least one assignment for the task task node", error);
	}

	@Test
	public void testValidateNoIncomingTransitionConditionNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"no-incoming-condition-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The condition node must have an incoming transition", error);
	}

	@Test
	public void testValidateNoIncomingTransitionForkNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"no-incoming-fork-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The fork node must have an incoming transition", error);
	}

	@Test
	public void testValidateNoIncomingTransitionStateNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"no-incoming-state-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The state node must have an incoming transition", error);
	}

	@Test
	public void testValidateNoIncomingTransitionTaskNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"no-incoming-task-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The task node must have an incoming transition", error);
	}

	@Test
	public void testValidateNoInitialStateDefinedDefinition() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"no-initial-state-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("You must define a start node", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionInitialStateDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"no-outgoing-initial-state-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The start node must have an outgoing transition", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionStartNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"no-outgoing-start-node-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The start node must have an outgoing transition", error);
	}

	@Test
	public void testValidateNoOutgoingTransitionTaskNodeDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"no-outgoing-task-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("Unable to parse definition", error);
	}

	@Test
	public void testValidateNoTerminalStatesDefinition() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"no-terminal-states-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("You must define an end node", error);
	}

	@Test
	public void testValidateSingleApproverDefinition() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"single-approver-workflow-definition.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateSingleApproverScriptedAssignmentDefinition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"single-approver-scripted-assignment-workflow-definition.xml");

		_assertValid(inputStream);
	}

	@Test
	public void testValidateTaskWithMoreThanOneDefaultTransition()
		throws Exception {

		InputStream inputStream = _getResourceInputStream(
			"task-with-more-than-one-default-transitions-workflow-" +
				"definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"The task8168 node cannot have more than one default transition",
			error);
	}

	@Test
	public void testValidateTransitions() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"invalid-transition-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals("The end transition must end at a node", error);
	}

	@Test
	public void testValidateUnbalancedForkAndJoinNodes() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"unbalanced-fork-and-join-workflow-definition.xml");

		String error = _assertInvalid(inputStream);

		_assertEquals(
			"Each fork node requires a join node. Make sure all forks and " +
				"joins are properly paired",
			error);
	}

	@Test
	public void testValidateValidDefinition() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"valid-workflow-definition.xml");

		_assertValid(inputStream);
	}

	private void _assertEquals(String expectedMessage, String actualMessage) {
		Assert.assertEquals(expectedMessage, actualMessage);
	}

	private String _assertInvalid(InputStream inputStream) throws Exception {
		byte[] bytes = FileUtil.getBytes(inputStream);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				ProxyMessageListener.class.getName(), LoggerTestUtil.OFF)) {

			_workflowDefinitionManager.validateWorkflowDefinition(bytes);

			Assert.fail();
		}
		catch (WorkflowException workflowException) {
			return workflowException.getMessage();
		}

		return null;
	}

	private void _assertValid(InputStream inputStream) throws Exception {
		_workflowDefinitionManager.validateWorkflowDefinition(
			FileUtil.getBytes(inputStream));
	}

	private InputStream _getResourceInputStream(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/portal/workflow/kaleo/dependencies/" + name);
	}

	private WorkflowDefinition _saveWorkflowDefinition() throws Exception {
		InputStream inputStream = _getResourceInputStream(
			"single-approver-workflow-definition.xml");

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

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

}