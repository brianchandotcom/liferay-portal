/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.internal.agent.test;

import com.liferay.ai.hub.agent.AgentContext;
import com.liferay.ai.hub.agent.AgentsFactory;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import java.io.InputStream;

import java.util.Collections;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author João Victor Alves
 */
@RunWith(Arquillian.class)
public class AgentsFactoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		_workflowDefinition =
			_workflowDefinitionManager.deployWorkflowDefinition(
				null, TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), StringUtil.randomId(),
				"Workflow Definition", "ai",
				_getContentBytes("workflow-definition.json"));
	}

	@Test
	public void testCreate() throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setLanguageId(
			LocaleUtil.toLanguageId(LocaleUtil.getSiteDefault()));

		AgentContext agentContext = AgentContext.builder(
		).companyId(
			TestPropsValues.getCompanyId()
		).dtoConverterContext(
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				LocaleUtil.getDefault(), null, TestPropsValues.getUser())
		).serviceContext(
			serviceContext
		).build();

		Assert.assertTrue(
			ArrayUtil.isNotEmpty(_agentsFactory.create(agentContext)));

		_workflowDefinitionManager.updateActive(
			_workflowDefinition.getCompanyId(), _workflowDefinition.getUserId(),
			_workflowDefinition.getName(), _workflowDefinition.getVersion(),
			false);

		Assert.assertTrue(
			ArrayUtil.isEmpty(_agentsFactory.create(agentContext)));
	}

	private static byte[] _getContentBytes(String fileName) throws Exception {
		InputStream inputStream = AgentsFactoryTest.class.getResourceAsStream(
			"dependencies/" + fileName);

		String content = StringUtil.read(inputStream);

		return content.getBytes();
	}

	private static WorkflowDefinition _workflowDefinition;

	@Inject
	private static WorkflowDefinitionManager _workflowDefinitionManager;

	@Inject
	private AgentsFactory _agentsFactory;

	@Inject
	private DTOConverterRegistry _dtoConverterRegistry;

}