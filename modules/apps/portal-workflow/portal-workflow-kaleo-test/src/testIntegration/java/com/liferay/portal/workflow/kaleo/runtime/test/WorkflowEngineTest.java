/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.model.WorkflowDefinitionLink;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.workflow.RequiredWorkflowDefinitionException;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.runtime.WorkflowEngine;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.io.InputStream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class WorkflowEngineTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ObjectDefinition objectDefinition =
			ObjectDefinitionTestUtil.publishObjectDefinition();
		_workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				FileUtil.getBytes(
					_getResourceInputStream("valid-workflow-definition.xml")),
				TestPropsValues.getCompanyId(), null,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TestPropsValues.getUserId());

		_workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
				TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), 0,
				objectDefinition.getClassName(), 0, 0,
				_workflowDefinition.getName(), 1);
	}

	@After
	public void tearDown() throws Exception {
		KaleoDefinition kaleoDefinition =
			_kaleoDefinitionLocalService.getKaleoDefinition(
				_workflowDefinition.getName(),
				ServiceContextTestUtil.getServiceContext());

		kaleoDefinition.setActive(false);

		_kaleoDefinitionLocalService.updateKaleoDefinition(kaleoDefinition);

		_workflowEngine.deleteWorkflowDefinition(
			_workflowDefinition.getName(), 1,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testDeleteWorkflowDefinition() throws Exception {
		AssertUtils.assertFailure(
			RequiredWorkflowDefinitionException.class, null,
			() -> _workflowEngine.deleteWorkflowDefinition(
				_workflowDefinition.getName(), 1,
				ServiceContextTestUtil.getServiceContext()));

		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
			_workflowDefinitionLink);

		AssertUtils.assertFailure(
			WorkflowException.class,
			"Cannot delete active workflow definition " +
				_workflowDefinition.getWorkflowDefinitionId(),
			() -> _workflowEngine.deleteWorkflowDefinition(
				_workflowDefinition.getName(), 1,
				ServiceContextTestUtil.getServiceContext()));
	}

	@Test
	public void testDeployWorkflowDefinition() throws Exception {
		WorkflowDefinition workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				FileUtil.getBytes(
					_getResourceInputStream("valid-workflow-definition.xml")),
				TestPropsValues.getCompanyId(),
				_workflowDefinition.getExternalReferenceCode(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TestPropsValues.getUserId());

		Assert.assertEquals(
			_workflowDefinition.getName(), workflowDefinition.getName());
		Assert.assertEquals(
			_workflowDefinition.getVersion() + 1,
			workflowDefinition.getVersion());

		WorkflowDefinitionLink workflowDefinitionLink =
			_workflowDefinitionLinkLocalService.getWorkflowDefinitionLink(
				_workflowDefinitionLink.getWorkflowDefinitionLinkId());

		Assert.assertEquals(
			workflowDefinition.getVersion(),
			workflowDefinitionLink.getWorkflowDefinitionVersion());

		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
			workflowDefinitionLink);
	}

	private InputStream _getResourceInputStream(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/portal/workflow/kaleo/dependencies/" + name);
	}

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	private WorkflowDefinition _workflowDefinition;
	private WorkflowDefinitionLink _workflowDefinitionLink;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowDefinitionManager _workflowDefinitionManager;

	@Inject
	private WorkflowEngine _workflowEngine;

}