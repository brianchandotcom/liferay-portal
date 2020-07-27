/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.rest.resource.v1_0.test;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowDataLayoutLink;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowRoleAssignment;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowState;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.client.dto.v1_0.AppWorkflowTransition;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureLayoutTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.Inject;

import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class AppWorkflowResourceTest extends BaseAppWorkflowResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		DDMStructure ddmStructure = _addDDMStructure(testGroup);

		_ddlRecordSet = _addDDLRecordSet(ddmStructure);
		DDMStructureLayout ddmStructureLayout = _addDDMStructureLayout(
			ddmStructure.getStructureId());
		DEDataListView deDataListView =
			_deDataListViewLocalService.addDEDataListView(
				testGroup.getGroupId(), testCompany.getCompanyId(),
				testGroup.getCreatorUserId(), StringPool.BLANK,
				ddmStructure.getStructureId(), StringPool.BLANK, null,
				StringPool.BLANK);

		_appBuilderApp = _appBuilderAppLocalService.addAppBuilderApp(
			testGroup.getGroupId(), testCompany.getCompanyId(),
			testGroup.getCreatorUserId(), true, _ddlRecordSet.getRecordSetId(),
			ddmStructure.getStructureId(),
			ddmStructureLayout.getStructureLayoutId(),
			deDataListView.getDeDataListViewId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString());
	}

	@Override
	@Test
	public void testGetAppWorkflow() throws Exception {
		AppWorkflow postAppWorkflow = testPostAppWorkflow_addAppWorkflow(
			randomAppWorkflow());

		AppWorkflow getAppWorkflow = appWorkflowResource.getAppWorkflow(
			postAppWorkflow.getAppId());

		assertEquals(postAppWorkflow, getAppWorkflow);
		assertValid(getAppWorkflow);

		Assert.assertNotNull(
			_workflowDefinitionLinkLocalService.fetchWorkflowDefinitionLink(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				getAppWorkflow.getAppId(), 0));

		DDLRecord ddlRecord = _addDDLRecord();

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				ddlRecord.getRecordId());

		Assert.assertNotNull(workflowInstanceLink);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, ddlRecord.getStatus());

		AppWorkflowTask[] appWorkflowTasks =
			getAppWorkflow.getAppWorkflowTasks();

		Assert.assertEquals(
			appWorkflowTasks.toString(), 1, appWorkflowTasks.length);

		AppWorkflowTask appWorkflowTask = appWorkflowTasks[0];

		List<WorkflowTask> workflowTasks =
			_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
				testCompany.getCompanyId(), null,
				workflowInstanceLink.getWorkflowInstanceId(), false,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(workflowTasks.toString(), 1, workflowTasks.size());

		WorkflowTask workflowTask = workflowTasks.get(0);

		Assert.assertEquals(appWorkflowTask.getName(), workflowTask.getName());

		_ddlRecordLocalService.deleteDDLRecord(ddlRecord);

		Assert.assertNull(
			_appBuilderAppDataRecordLinkLocalService.
				fetchDDLRecordAppBuilderAppDataRecordLink(
					ddlRecord.getRecordId()));

		Assert.assertNull(
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				testCompany.getCompanyId(), testGroup.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				ddlRecord.getRecordId()));
	}

	@Override
	@Test
	public void testPutAppWorkflow() throws Exception {
		AppWorkflow postAppWorkflow = testPostAppWorkflow_addAppWorkflow(
			randomAppWorkflow());

		AppWorkflow randomAppWorkflow = randomAppWorkflow();

		AppWorkflow putAppWorkflow = appWorkflowResource.putAppWorkflow(
			postAppWorkflow.getAppId(), randomAppWorkflow);

		assertEquals(randomAppWorkflow, putAppWorkflow);
		assertValid(putAppWorkflow);

		AppWorkflow getAppWorkflow = appWorkflowResource.getAppWorkflow(
			putAppWorkflow.getAppId());

		assertEquals(randomAppWorkflow, getAppWorkflow);
		assertValid(getAppWorkflow);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"appId", "appWorkflowStates", "appWorkflowTasks"};
	}

	@Override
	protected AppWorkflow randomAppWorkflow() throws Exception {
		String appWorkflowTaskName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());
		String terminalStateName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		return new AppWorkflow() {
			{
				appId = _appBuilderApp.getAppBuilderAppId();
				appWorkflowStates = new AppWorkflowState[] {
					new AppWorkflowState() {
						{
							appWorkflowTransitions =
								new AppWorkflowTransition[] {
									new AppWorkflowTransition() {
										{
											name = StringUtil.toLowerCase(
												RandomTestUtil.randomString());
											primary = true;
											transitionTo = appWorkflowTaskName;
										}
									}
								};
							initial = true;
							name = StringUtil.toLowerCase(
								RandomTestUtil.randomString());
						}
					},
					new AppWorkflowState() {
						{
							appWorkflowTransitions =
								new AppWorkflowTransition[0];
							initial = false;
							name = terminalStateName;
						}
					}
				};
				appWorkflowTasks = new AppWorkflowTask[] {
					new AppWorkflowTask() {
						{
							appWorkflowDataLayoutLinks =
								new AppWorkflowDataLayoutLink[] {
									new AppWorkflowDataLayoutLink() {
										{
											dataLayoutId =
												_appBuilderApp.
													getDdmStructureLayoutId();
											readOnly = false;
										}
									}
								};
							appWorkflowRoleAssignments =
								new AppWorkflowRoleAssignment[] {
									new AppWorkflowRoleAssignment() {
										{
											Role role =
												_roleLocalService.getRole(
													testCompany.getCompanyId(),
													RoleConstants.
														PORTAL_CONTENT_REVIEWER);

											roleId = role.getRoleId();
											roleName = role.getName();
										}
									}
								};
							appWorkflowTransitions =
								new AppWorkflowTransition[] {
									new AppWorkflowTransition() {
										{
											name = StringUtil.toLowerCase(
												RandomTestUtil.randomString());
											primary = true;
											transitionTo = terminalStateName;
										}
									}
								};
							name = appWorkflowTaskName;
						}
					}
				};
			}
		};
	}

	@Override
	protected AppWorkflow testPostAppWorkflow_addAppWorkflow(
			AppWorkflow appWorkflow)
		throws Exception {

		return appWorkflowResource.postAppWorkflow(
			appWorkflow.getAppId(), appWorkflow);
	}

	private DDLRecord _addDDLRecord() throws Exception {
		DDLRecord ddlRecord = _ddlRecordLocalService.addRecord(
			testGroup.getCreatorUserId(), _ddlRecordSet.getGroupId(),
			RandomTestUtil.nextLong(), _appBuilderApp.getDdlRecordSetId(),
			StringPool.BLANK, 0, new ServiceContext());

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				_appBuilderApp.getAppBuilderAppId());

		_appBuilderAppDataRecordLinkLocalService.addAppBuilderAppDataRecordLink(
			testGroup.getGroupId(), testGroup.getCompanyId(),
			_appBuilderApp.getAppBuilderAppId(),
			latestAppBuilderAppVersion.getAppBuilderAppVersionId(),
			ddlRecord.getRecordId());

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			testGroup.getCompanyId(), _appBuilderApp.getGroupId(),
			testGroup.getCreatorUserId(),
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			ddlRecord.getRecordId(), ddlRecord, new ServiceContext(),
			Collections.emptyMap());
	}

	private DDLRecordSet _addDDLRecordSet(DDMStructure ddmStructure)
		throws Exception {

		return _ddlRecordSetLocalService.addRecordSet(
			testGroup.getCreatorUserId(), testGroup.getGroupId(),
			ddmStructure.getStructureId(), ddmStructure.getStructureKey(),
			ddmStructure.getNameMap(), ddmStructure.getDescriptionMap(), 0,
			DDLRecordSetConstants.SCOPE_DATA_ENGINE, new ServiceContext());
	}

	private DDMStructure _addDDMStructure(Group group) throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(AppBuilderApp.class.getName()),
				group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(AppBuilderApp.class.getName()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_read("test-structured-content-structure.json"),
			StorageType.JSON.getValue());
	}

	private DDMStructureLayout _addDDMStructureLayout(long ddmStructureId)
		throws Exception {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(LocaleUtil.US);

		DDMStructureLayoutTestHelper ddmStructureLayoutTestHelper =
			new DDMStructureLayoutTestHelper(testGroup);

		return ddmStructureLayoutTestHelper.addStructureLayout(
			ddmStructureId, ddmFormLayout);
	}

	private String _read(String fileName) throws Exception {
		return StringUtil.read(getClass(), "dependencies/" + fileName);
	}

	private AppBuilderApp _appBuilderApp;

	@Inject
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Inject
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Inject
	private DDLRecordLocalService _ddlRecordLocalService;

	private DDLRecordSet _ddlRecordSet;

	@Inject
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Inject
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}