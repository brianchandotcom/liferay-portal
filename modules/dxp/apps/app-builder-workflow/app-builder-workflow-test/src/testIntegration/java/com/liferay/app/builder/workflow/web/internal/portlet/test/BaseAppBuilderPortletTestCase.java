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

package com.liferay.app.builder.workflow.web.internal.portlet.test;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.constants.AppBuilderPortletKeys;
import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.rest.dto.v1_0.App;
import com.liferay.app.builder.rest.dto.v1_0.AppDeployment;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflow;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowDataLayoutLink;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowRoleAssignment;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowState;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTask;
import com.liferay.app.builder.workflow.rest.dto.v1_0.AppWorkflowTransition;
import com.liferay.app.builder.workflow.rest.resource.v1_0.AppWorkflowResource;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.dynamic.data.lists.constants.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureLayoutTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayOutputStream;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseAppBuilderPortletTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_group = _groupLocalService.getGroup(TestPropsValues.getGroupId());

		_ddmStructure = _addDDMStructure(_group);

		_ddlRecordSet = _addDDLRecordSet(_ddmStructure);
		_ddmStructureLayout = _addDDMStructureLayout(
			_ddmStructure.getStructureId());
		_deDataListView = _deDataListViewLocalService.addDEDataListView(
			_group.getGroupId(), _company.getCompanyId(),
			_group.getCreatorUserId(), StringPool.BLANK,
			_ddmStructure.getStructureId(), StringPool.BLANK, null,
			StringPool.BLANK);
	}

	protected App addApp() throws Exception {
		return addApp(createAppWorkflow());
	}

	protected App addApp(AppWorkflow appWorkflow) throws Exception {
		return _toDTO(
			App.class,
			_serveResource(
				_addAppBuilderAppMVCResourceCommand,
				HashMapBuilder.<String, Object>put(
					"app", _createApp()
				).put(
					"appWorkflow", appWorkflow
				).put(
					"dataDefinitionId", _ddmStructure.getStructureId()
				).build()));
	}

	protected DataRecord addDataRecord(App app) throws Exception {
		return _toDTO(
			DataRecord.class,
			_serveResource(
				_addDataRecordMVCResourceCommand,
				HashMapBuilder.<String, Object>put(
					"appBuilderAppId", app.getId()
				).put(
					"dataRecord", _createDataRecord(app)
				).build()));
	}

	protected AppWorkflow createAppWorkflow() throws Exception {
		String appWorkflowTaskName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());
		String terminalStateName = StringUtil.toLowerCase(
			RandomTestUtil.randomString());

		return new AppWorkflow() {
			{
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
												_ddmStructureLayout.
													getStructureLayoutId();
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
													_company.getCompanyId(),
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

	protected boolean deleteApp(App app) throws Exception {
		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(
				_serveResource(
					_deleteAppBuilderAppMVCResourceCommand,
					HashMapBuilder.<String, Object>put(
						"appBuilderAppId", app.getId()
					).build()));

		if (httpServletResponse.getStatus() == HttpServletResponse.SC_OK) {
			return true;
		}

		return false;
	}

	protected AppWorkflow getAppWorkflow(App app) throws Exception {
		AppWorkflowResource appWorkflowResource = AppWorkflowResource.builder(
		).user(
			TestPropsValues.getUser()
		).build();

		return appWorkflowResource.getAppWorkflow(app.getId());
	}

	protected App updateApp(App app, AppWorkflow appWorkflow) throws Exception {
		return _toDTO(
			App.class,
			_serveResource(
				_updateAppBuilderAppMVCResourceCommand,
				HashMapBuilder.<String, Object>put(
					"app", app
				).put(
					"appBuilderAppId", app.getId()
				).put(
					"appWorkflow", appWorkflow
				).build()));
	}

	protected DataRecord updateDataRecord(App app, long dataRecordId)
		throws Exception {

		AppWorkflow appWorkflow = getAppWorkflow(app);

		AppWorkflowTask[] appWorkflowTasks = appWorkflow.getAppWorkflowTasks();

		AppWorkflowTask appWorkflowTask = appWorkflowTasks[0];

		AppWorkflowTransition[] appWorkflowTransitions =
			appWorkflowTask.getAppWorkflowTransitions();

		AppWorkflowTransition appWorkflowTransition = appWorkflowTransitions[0];

		return _toDTO(
			DataRecord.class,
			_serveResource(
				_updateDataRecordMVCResourceCommand,
				HashMapBuilder.<String, Object>put(
					"dataRecord", _createDataRecord(app)
				).put(
					"dataRecordId", dataRecordId
				).put(
					"taskName", appWorkflowTask.getName()
				).put(
					"transitionName", appWorkflowTransition.getName()
				).put(
					"workflowInstanceId", _getWorkflowInstanceId(dataRecordId)
				).build()));
	}

	@Inject
	protected AppBuilderAppLocalService appBuilderAppLocalService;

	private DDLRecordSet _addDDLRecordSet(DDMStructure ddmStructure)
		throws Exception {

		return _ddlRecordSetLocalService.addRecordSet(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
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
			StorageType.DEFAULT.getValue());
	}

	private DDMStructureLayout _addDDMStructureLayout(long ddmStructureId)
		throws Exception {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(LocaleUtil.US);

		DDMStructureLayoutTestHelper ddmStructureLayoutTestHelper =
			new DDMStructureLayoutTestHelper(_group);

		return ddmStructureLayoutTestHelper.addStructureLayout(
			ddmStructureId, ddmFormLayout);
	}

	private App _createApp() throws Exception {
		return new App() {
			{
				active = false;
				appDeployments = new AppDeployment[] {
					new AppDeployment() {
						{
							settings = new HashMap<>();
							type = "standalone";
						}
					}
				};
				dataDefinitionId = _ddmStructure.getStructureId();
				dataDefinitionName = _ddmStructure.getName(LocaleUtil.US);
				dataLayoutId = _ddmStructureLayout.getStructureLayoutId();
				dataListViewId = _deDataListView.getDeDataListViewId();
				dataRecordCollectionId = _ddlRecordSet.getRecordSetId();
				scope = AppBuilderAppConstants.SCOPE_STANDARD;
				siteId = _ddmStructure.getGroupId();
				userId = TestPropsValues.getUserId();
			}
		};
	}

	private DataRecord _createDataRecord(App app) {
		return new DataRecord() {
			{
				dataRecordCollectionId = app.getDataRecordCollectionId();
				dataRecordValues = HashMapBuilder.<String, Object>put(
					"MyText",
					HashMapBuilder.put(
						"en_US",
						new String[] {
							RandomTestUtil.randomString(),
							RandomTestUtil.randomString()
						}
					).build()
				).build();
			}
		};
	}

	private MockLiferayResourceRequest _getMockLiferayResourceRequest(
			MockLiferayResourceResponse mockLiferayResourceResponse,
			Map<String, Object> parameterMap)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_CONFIG,
			PortletConfigFactoryUtil.create(
				_portletLocalService.getPortletById(AppBuilderPortletKeys.APPS),
				null));
		mockLiferayResourceRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE, mockLiferayResourceResponse);
		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			new ThemeDisplay() {
				{
					setCompany(_company);
					setPermissionChecker(
						PermissionThreadLocal.getPermissionChecker());
					setRealUser(TestPropsValues.getUser());
					setScopeGroupId(_group.getGroupId());
					setSiteGroupId(_group.getGroupId());
					setUser(TestPropsValues.getUser());
				}
			});

		parameterMap.forEach(
			(key, value) -> mockLiferayResourceRequest.addParameter(
				key, String.valueOf(value)));

		return mockLiferayResourceRequest;
	}

	private long _getWorkflowInstanceId(long dataRecordId) throws Exception {
		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.fetchWorkflowInstanceLink(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				ResourceActionsUtil.getCompositeModelName(
					AppBuilderApp.class.getName(), DDLRecord.class.getName()),
				dataRecordId);

		return workflowInstanceLink.getWorkflowInstanceId();
	}

	private String _read(String fileName) throws Exception {
		return StringUtil.read(
			BaseAppBuilderPortletTestCase.class, "dependencies/" + fileName);
	}

	private MockLiferayResourceResponse _serveResource(
			MVCResourceCommand mvcResourceCommand,
			Map<String, Object> parameterMap)
		throws Exception {

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		mvcResourceCommand.serveResource(
			_getMockLiferayResourceRequest(
				mockLiferayResourceResponse, parameterMap),
			mockLiferayResourceResponse);

		return mockLiferayResourceResponse;
	}

	private <T> T _toDTO(
			Class<T> dtoClass,
			MockLiferayResourceResponse mockLiferayResourceResponse)
		throws Exception {

		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(mockLiferayResourceResponse);

		if (httpServletResponse.getStatus() != HttpServletResponse.SC_OK) {
			return null;
		}

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return ReflectionTestUtil.invoke(
			dtoClass, "toDTO", new Class<?>[] {String.class},
			byteArrayOutputStream.toString());
	}

	@Inject(
		filter = "mvc.command.name=/app_builder_workflow/add_app_builder_app"
	)
	private MVCResourceCommand _addAppBuilderAppMVCResourceCommand;

	@Inject(filter = "mvc.command.name=/app_builder/add_data_record")
	private MVCResourceCommand _addDataRecordMVCResourceCommand;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	private DDLRecordSet _ddlRecordSet;

	@Inject
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	private DDMStructure _ddmStructure;
	private DDMStructureLayout _ddmStructureLayout;
	private DEDataListView _deDataListView;

	@Inject
	private DEDataListViewLocalService _deDataListViewLocalService;

	@Inject(
		filter = "mvc.command.name=/app_builder_workflow/delete_app_builder_app"
	)
	private MVCResourceCommand _deleteAppBuilderAppMVCResourceCommand;

	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private PortletLocalService _portletLocalService;

	@Inject
	private RoleLocalService _roleLocalService;

	@Inject(
		filter = "mvc.command.name=/app_builder_workflow/update_app_builder_app"
	)
	private MVCResourceCommand _updateAppBuilderAppMVCResourceCommand;

	@Inject(
		filter = "mvc.command.name=/app_builder_workflow/update_data_record"
	)
	private MVCResourceCommand _updateDataRecordMVCResourceCommand;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}