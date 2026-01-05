/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.resource.v1_0.test;

import com.liferay.ai.hub.rest.client.dto.v1_0.TaskDefinition;
import com.liferay.ai.hub.rest.client.pagination.Page;
import com.liferay.ai.hub.rest.client.pagination.Pagination;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.site.initializer.SiteInitializer;
import com.liferay.site.initializer.SiteInitializerRegistry;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author João Victor Alves
 */
@FeatureFlag("LPD-62272")
@RunWith(Arquillian.class)
public class TaskDefinitionResourceTest
	extends BaseTaskDefinitionResourceTestCase {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_originalName = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId()));

		SiteInitializer siteInitializer =
			_siteInitializerRegistry.getSiteInitializer(
				"com.liferay.ai.hub.site.initializer");

		siteInitializer.initialize(TestPropsValues.getGroupId());
	}

	@AfterClass
	public static void tearDownClass() {
		PrincipalThreadLocal.setName(_originalName);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testGetTaskDefinitionsPage() throws Exception {
		Page<TaskDefinition> page =
			taskDefinitionResource.getTaskDefinitionsPage(
				null, null, Pagination.of(1, 10), null);

		AssertUtils.assertEquals(
			List.of(
				WorkflowDefinitionConstants.NAME_CHANGE_TONE,
				WorkflowDefinitionConstants.NAME_CHAT_MESSAGE_PIPELINE,
				WorkflowDefinitionConstants.NAME_FIX_SPELLING_AND_GRAMMAR,
				WorkflowDefinitionConstants.NAME_IMPROVE_WRITING,
				WorkflowDefinitionConstants.NAME_MAKE_LONGER,
				WorkflowDefinitionConstants.NAME_MAKE_SHORTER),
			TransformUtil.transform(page.getItems(), TaskDefinition::getName));
	}

	@Ignore
	@Override
	@Test
	public void testGetTaskDefinitionsPageWithPagination() {
	}

	@Override
	protected TaskDefinition testGetTaskDefinitionsPage_addTaskDefinition(
		TaskDefinition taskDefinition) {

		return taskDefinition;
	}

	private static String _originalName;

	@Inject
	private static SiteInitializerRegistry _siteInitializerRegistry;

}