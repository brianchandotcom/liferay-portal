/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.client.dto.v1_0.WorkflowDefinitionLink;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionLocalService;

import java.util.Collections;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowDefinitionLinkResourceTest
	extends BaseWorkflowDefinitionLinkResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(
				UserLocalServiceUtil.getUser(TestPropsValues.getUserId())));

		_objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_workflowDefinition =
			WorkflowDefinitionTestUtil.addWorkflowDefinition();
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		if (_objectDefinition != null) {
			_objectDefinitionLocalService.deleteObjectDefinition(
				_objectDefinition.getObjectDefinitionId());
		}

		if (_workflowDefinition != null) {
			_kaleoDefinitionLocalService.deleteKaleoDefinition(
				_workflowDefinition.getId());
		}
	}

	@Override
	protected WorkflowDefinitionLink randomWorkflowDefinitionLink()
		throws Exception {

		return new WorkflowDefinitionLink() {
			{
				className = _objectDefinition.getClassName();
				groupId = testGroup.getGroupId();
			}
		};
	}

	@Override
	protected WorkflowDefinitionLink
			testGetWorkflowDefinitionByExternalReferenceCodeWorkflowDefinitionLinksPage_addWorkflowDefinitionLink(
				String externalReferenceCode,
				WorkflowDefinitionLink workflowDefinitionLink)
		throws Exception {

		return workflowDefinitionLinkResource.
			postWorkflowDefinitionByExternalReferenceCodeWorkflowDefinitionLink(
				externalReferenceCode, workflowDefinitionLink);
	}

	@Override
	protected String
			testGetWorkflowDefinitionByExternalReferenceCodeWorkflowDefinitionLinksPage_getExternalReferenceCode()
		throws Exception {

		return _workflowDefinition.getExternalReferenceCode();
	}

	@Override
	protected Map<String, Map<String, String>>
			testGetWorkflowDefinitionWorkflowDefinitionLinksPage_getExpectedActions(
				Long workflowDefinitionId)
		throws Exception {

		return Collections.emptyMap();
	}

	@Override
	protected Long
			testGetWorkflowDefinitionWorkflowDefinitionLinksPage_getWorkflowDefinitionId()
		throws Exception {

		return _workflowDefinition.getId();
	}

	@Override
	protected WorkflowDefinitionLink
			testPostWorkflowDefinitionByExternalReferenceCodeWorkflowDefinitionLink_addWorkflowDefinitionLink(
				WorkflowDefinitionLink workflowDefinitionLink)
		throws Exception {

		return workflowDefinitionLinkResource.
			postWorkflowDefinitionWorkflowDefinitionLink(
				_workflowDefinition.getId(), workflowDefinitionLink);
	}

	private static ObjectDefinition _objectDefinition;
	private static WorkflowDefinition _workflowDefinition;

	@Inject
	private KaleoDefinitionLocalService _kaleoDefinitionLocalService;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}