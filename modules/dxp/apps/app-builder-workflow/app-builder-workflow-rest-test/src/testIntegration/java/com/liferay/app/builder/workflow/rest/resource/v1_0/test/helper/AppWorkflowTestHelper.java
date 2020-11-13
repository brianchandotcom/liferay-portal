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

package com.liferay.app.builder.workflow.rest.resource.v1_0.test.helper;

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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = AppWorkflowTestHelper.class)
public class AppWorkflowTestHelper {

	public AppBuilderApp addAppBuilderApp(long companyId, Group group)
		throws Exception {

		DDMStructure ddmStructure = _addDDMStructure(group);

		DDLRecordSet ddlRecordSet = _addDDLRecordSet(ddmStructure, group);

		DDMStructureLayout ddmStructureLayout = _addDDMStructureLayout(
			ddmStructure.getStructureId(), group);

		DEDataListView deDataListView =
			_deDataListViewLocalService.addDEDataListView(
				group.getGroupId(), companyId, group.getCreatorUserId(),
				StringPool.BLANK, ddmStructure.getStructureId(),
				StringPool.BLANK, null, StringPool.BLANK);

		return _appBuilderAppLocalService.addAppBuilderApp(
			group.getGroupId(), companyId, group.getCreatorUserId(), true,
			ddlRecordSet.getRecordSetId(), ddmStructure.getStructureId(),
			ddmStructureLayout.getStructureLayoutId(),
			deDataListView.getDeDataListViewId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString());
	}

	public DDLRecord addDDLRecord(AppBuilderApp appBuilderApp, Group group)
		throws Exception {

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());
		DDLRecord ddlRecord = _ddlRecordLocalService.addRecord(
			group.getCreatorUserId(), group.getGroupId(),
			RandomTestUtil.nextLong(), appBuilderApp.getDdlRecordSetId(),
			StringPool.BLANK, 0, new ServiceContext());

		_appBuilderAppDataRecordLinkLocalService.addAppBuilderAppDataRecordLink(
			group.getGroupId(), group.getCompanyId(),
			appBuilderApp.getAppBuilderAppId(),
			latestAppBuilderAppVersion.getAppBuilderAppVersionId(),
			ddlRecord.getRecordId());

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			group.getCompanyId(), appBuilderApp.getGroupId(),
			group.getCreatorUserId(),
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			ddlRecord.getRecordId(), ddlRecord, new ServiceContext(),
			Collections.emptyMap());
	}

	public AppWorkflow createAppWorkflow(AppBuilderApp appBuilderApp)
		throws Exception {

		String appWorkflowTaskName1 = StringUtil.toLowerCase(
			RandomTestUtil.randomString());
		String appWorkflowTaskName2 = StringUtil.toLowerCase(
			RandomTestUtil.randomString());
		String terminalStateName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		return new AppWorkflow() {
			{
				appId = appBuilderApp.getAppBuilderAppId();
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
											transitionTo = appWorkflowTaskName1;
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
												appBuilderApp.
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
													appBuilderApp.
														getCompanyId(),
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
											transitionTo = appWorkflowTaskName2;
										}
									}
								};
							name = appWorkflowTaskName1;
						}
					},
					new AppWorkflowTask() {
						{
							appWorkflowDataLayoutLinks =
								new AppWorkflowDataLayoutLink[] {
									new AppWorkflowDataLayoutLink() {
										{
											dataLayoutId =
												appBuilderApp.
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
													appBuilderApp.
														getCompanyId(),
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
									},
									new AppWorkflowTransition() {
										{
											name = StringUtil.toLowerCase(
												RandomTestUtil.randomString());
											primary = false;
											transitionTo = appWorkflowTaskName1;
										}
									}
								};
							name = appWorkflowTaskName2;
						}
					}
				};
			}
		};
	}

	private DDLRecordSet _addDDLRecordSet(
			DDMStructure ddmStructure, Group group)
		throws Exception {

		return _ddlRecordSetLocalService.addRecordSet(
			group.getCreatorUserId(), group.getGroupId(),
			ddmStructure.getStructureId(), ddmStructure.getStructureKey(),
			ddmStructure.getNameMap(), ddmStructure.getDescriptionMap(), 0,
			DDLRecordSetConstants.SCOPE_DATA_ENGINE, new ServiceContext());
	}

	private DDMStructure _addDDMStructure(Group group) throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(AppBuilderApp.class.getName()), group);

		return ddmStructureTestHelper.addStructure(
			_portal.getClassNameId(AppBuilderApp.class.getName()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_read("test-structured-content-structure.json"),
			StorageType.DEFAULT.getValue());
	}

	private DDMStructureLayout _addDDMStructureLayout(
			long ddmStructureId, Group group)
		throws Exception {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(LocaleUtil.US);

		DDMStructureLayoutTestHelper ddmStructureLayoutTestHelper =
			new DDMStructureLayoutTestHelper(group);

		return ddmStructureLayoutTestHelper.addStructureLayout(
			ddmStructureId, ddmFormLayout);
	}

	private String _read(String fileName) throws Exception {
		return StringUtil.read(getClass(), "dependencies/" + fileName);
	}

	@Reference
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Reference
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Reference
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

}