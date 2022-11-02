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

package com.liferay.headless.admin.workflow.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.admin.workflow.dto.v1_0.WorkflowDefinition;
import com.liferay.headless.admin.workflow.resource.v1_0.WorkflowDefinitionResource;
import com.liferay.headless.admin.workflow.resource.v1_0.test.util.WorkflowDefinitionTestUtil;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class WorkflowDefinitionVulcanBatchEngineTaskItemDelegateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule liferayIntegrationTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_workflowDefinitionResource.setContextAcceptLanguage(
			new AcceptLanguage() {

				@Override
				public List<Locale> getLocales() {
					return Arrays.asList(LocaleUtil.getDefault());
				}

				@Override
				public String getPreferredLanguageId() {
					return LocaleUtil.toLanguageId(LocaleUtil.getDefault());
				}

				@Override
				public Locale getPreferredLocale() {
					return LocaleUtil.getDefault();
				}

			});
		_workflowDefinitionResource.setContextCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		User user = TestPropsValues.getUser();

		_workflowDefinitionResource.setContextUser(user);

		_originalName = PrincipalThreadLocal.getName();
		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(user));

		PrincipalThreadLocal.setName(user.getUserId());
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);

		PrincipalThreadLocal.setName(_originalName);

		for (WorkflowDefinition workflowDefinition :
				_workflowDefinitions.values()) {

			_deleteWorkflowDefinition(workflowDefinition.getName());
		}

		_workflowDefinitions.clear();
	}

	@Test
	public void testCreateInsert() throws Exception {
		WorkflowDefinition workflowDefinition1 = _createWorkflowDefinition();
		WorkflowDefinition workflowDefinition2 = _createWorkflowDefinition();

		VulcanBatchEngineTaskItemDelegate<WorkflowDefinition>
			vulcanBatchEngineTaskItemDelegate =
				(VulcanBatchEngineTaskItemDelegate<WorkflowDefinition>)
					_workflowDefinitionResource;

		vulcanBatchEngineTaskItemDelegate.create(
			Arrays.asList(workflowDefinition1, workflowDefinition2),
			Collections.emptyMap());

		_assertWorkflowDefinition(
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(), workflowDefinition1.getName(),
				1));
		_assertWorkflowDefinition(
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(), workflowDefinition2.getName(),
				1));
	}

	@Test
	public void testCreateUpsert() throws Exception {
		WorkflowDefinition workflowDefinition1 = _createWorkflowDefinition();
		WorkflowDefinition workflowDefinition2 =
			_workflowDefinitionResource.postWorkflowDefinitionDeploy(
				_createWorkflowDefinition());

		VulcanBatchEngineTaskItemDelegate<WorkflowDefinition>
			vulcanBatchEngineTaskItemDelegate =
				(VulcanBatchEngineTaskItemDelegate<WorkflowDefinition>)
					_workflowDefinitionResource;

		vulcanBatchEngineTaskItemDelegate.create(
			Arrays.asList(workflowDefinition1, workflowDefinition2),
			Collections.singletonMap("createStrategy", "UPSERT"));

		_assertWorkflowDefinition(
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(), workflowDefinition1.getName(),
				1));
		_assertWorkflowDefinition(
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(), workflowDefinition2.getName(),
				2));
	}

	@Test
	public void testDelete() throws Exception {
		WorkflowDefinition workflowDefinition1 =
			_workflowDefinitionResource.postWorkflowDefinitionDeploy(
				_createWorkflowDefinition());
		WorkflowDefinition workflowDefinition2 =
			_workflowDefinitionResource.postWorkflowDefinitionDeploy(
				_createWorkflowDefinition());

		VulcanBatchEngineTaskItemDelegate<WorkflowDefinition>
			vulcanBatchEngineTaskItemDelegate =
				(VulcanBatchEngineTaskItemDelegate<WorkflowDefinition>)
					_workflowDefinitionResource;

		vulcanBatchEngineTaskItemDelegate.delete(
			Arrays.asList(workflowDefinition1, workflowDefinition2),
			Collections.emptyMap());

		_workflowDefinitions.clear();

		_assertException(
			() -> _workflowDefinitionResource.getWorkflowDefinition(
				workflowDefinition1.getId()));
		_assertException(
			() -> _workflowDefinitionResource.getWorkflowDefinition(
				workflowDefinition2.getId()));
	}

	@Test
	public void testUpdate() throws Exception {
		WorkflowDefinition workflowDefinition1 =
			_workflowDefinitionResource.postWorkflowDefinitionDeploy(
				_createWorkflowDefinition());
		WorkflowDefinition workflowDefinition2 =
			_workflowDefinitionResource.postWorkflowDefinitionDeploy(
				_createWorkflowDefinition());

		VulcanBatchEngineTaskItemDelegate<WorkflowDefinition>
			vulcanBatchEngineTaskItemDelegate =
				(VulcanBatchEngineTaskItemDelegate<WorkflowDefinition>)
					_workflowDefinitionResource;

		vulcanBatchEngineTaskItemDelegate.update(
			Arrays.asList(workflowDefinition1, workflowDefinition2),
			Collections.emptyMap());

		_assertWorkflowDefinition(
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(), workflowDefinition1.getName(),
				2));
		_assertWorkflowDefinition(
			_workflowDefinitionManager.getWorkflowDefinition(
				TestPropsValues.getCompanyId(), workflowDefinition2.getName(),
				2));
	}

	private void _assertException(
		UnsafeSupplier<WorkflowDefinition, Exception> unsafeSupplier) {

		try {
			unsafeSupplier.get();

			Assert.fail();
		}
		catch (Exception exception) {
			Assert.assertTrue(exception instanceof NoSuchModelException);

			Class<? extends Exception> clazz = exception.getClass();

			Assert.assertEquals(
				"NoSuchDefinitionException", clazz.getSimpleName());
		}
	}

	private void _assertWorkflowDefinition(
		com.liferay.portal.kernel.workflow.WorkflowDefinition
			workflowDefinition) {

		Assert.assertNotNull(workflowDefinition);
		Assert.assertTrue(workflowDefinition.isActive());
	}

	private WorkflowDefinition _createWorkflowDefinition() throws Exception {
		String workflowDefinitionName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		WorkflowDefinition workflowDefinition = new WorkflowDefinition() {
			{
				content = WorkflowDefinitionTestUtil.getContent(
					StringUtil.toLowerCase(RandomTestUtil.randomString()),
					"workflow-definition.xml", workflowDefinitionName);
				name = workflowDefinitionName;
				version = "1";
			}
		};

		_workflowDefinitions.put(workflowDefinitionName, workflowDefinition);

		return workflowDefinition;
	}

	private void _deleteWorkflowDefinition(String workflowDefinitionName)
		throws Exception {

		List<com.liferay.portal.kernel.workflow.WorkflowDefinition>
			workflowDefinitions =
				_workflowDefinitionManager.getActiveWorkflowDefinitions(
					TestPropsValues.getCompanyId(), workflowDefinitionName,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (workflowDefinitions.isEmpty()) {
			return;
		}

		for (com.liferay.portal.kernel.workflow.WorkflowDefinition
				workflowDefinition : workflowDefinitions) {

			_workflowDefinitionManager.updateActive(
				workflowDefinition.getCompanyId(),
				workflowDefinition.getUserId(), workflowDefinitionName,
				workflowDefinition.getVersion(), false);

			_workflowDefinitionManager.undeployWorkflowDefinition(
				workflowDefinition.getCompanyId(),
				workflowDefinition.getUserId(), workflowDefinitionName,
				workflowDefinition.getVersion());
		}
	}

	@Inject
	private static WorkflowDefinitionManager _workflowDefinitionManager;

	@Inject
	private CompanyLocalService _companyLocalService;

	private String _originalName;
	private PermissionChecker _originalPermissionChecker;

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	@Inject
	private WorkflowDefinitionResource _workflowDefinitionResource;

	private final Map<String, WorkflowDefinition> _workflowDefinitions =
		new HashMap<>();

}