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

package com.liferay.app.builder.internal.model.listener.test;

import com.liferay.app.builder.model.AppBuilderApp;
import com.liferay.app.builder.model.AppBuilderAppDataRecordLink;
import com.liferay.app.builder.model.AppBuilderAppVersion;
import com.liferay.app.builder.service.AppBuilderAppDataRecordLinkLocalService;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
import com.liferay.app.builder.service.AppBuilderAppVersionLocalService;
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
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Inácio Nery
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowInstanceLinkModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testRemoveAppBuilderAppDataRecordLink() throws Exception {
		AppBuilderApp appBuilderApp = _addAppBuilderApp();

		AppBuilderAppVersion latestAppBuilderAppVersion =
			_appBuilderAppVersionLocalService.getLatestAppBuilderAppVersion(
				appBuilderApp.getAppBuilderAppId());

		_workflowDefinitionLinkLocalService.addWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(), 0,
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			appBuilderApp.getAppBuilderAppId(), 0, "Single Approver", 1);

		DDLRecord ddlRecord = _ddlRecordLocalService.addRecord(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.nextLong(), appBuilderApp.getDdlRecordSetId(),
			StringPool.BLANK, 0, new ServiceContext());

		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink =
			_appBuilderAppDataRecordLinkLocalService.
				addAppBuilderAppDataRecordLink(
					_group.getGroupId(), TestPropsValues.getCompanyId(),
					appBuilderApp.getAppBuilderAppId(),
					latestAppBuilderAppVersion.getAppBuilderAppVersionId(),
					ddlRecord.getRecordId());

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			TestPropsValues.getCompanyId(), appBuilderApp.getGroupId(),
			TestPropsValues.getUserId(),
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			ddlRecord.getRecordId(), ddlRecord, new ServiceContext(),
			Collections.emptyMap());

		appBuilderAppDataRecordLink =
			_appBuilderAppDataRecordLinkLocalService.
				fetchAppBuilderAppDataRecordLink(
					appBuilderAppDataRecordLink.
						getAppBuilderAppDataRecordLinkId());

		Assert.assertNotNull(appBuilderAppDataRecordLink);

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
			TestPropsValues.getCompanyId(), appBuilderApp.getGroupId(),
			ResourceActionsUtil.getCompositeModelName(
				AppBuilderApp.class.getName(), DDLRecord.class.getName()),
			ddlRecord.getRecordId());

		appBuilderAppDataRecordLink =
			_appBuilderAppDataRecordLinkLocalService.
				fetchAppBuilderAppDataRecordLink(
					appBuilderAppDataRecordLink.
						getAppBuilderAppDataRecordLinkId());

		Assert.assertNull(appBuilderAppDataRecordLink);
	}

	private AppBuilderApp _addAppBuilderApp() throws Exception {
		DDMStructure ddmStructure = _addDDMStructure();

		DDLRecordSet ddlRecordSet = _addDDLRecordSet(ddmStructure);

		DDMStructureLayout ddmStructureLayout = _addDDMStructureLayout(
			ddmStructure.getStructureId());

		DEDataListView deDataListView =
			_deDataListViewLocalService.addDEDataListView(
				_group.getGroupId(), TestPropsValues.getCompanyId(),
				TestPropsValues.getUserId(), StringPool.BLANK,
				ddmStructure.getStructureId(), StringPool.BLANK, null,
				StringPool.BLANK);

		return _appBuilderAppLocalService.addAppBuilderApp(
			_group.getGroupId(), TestPropsValues.getCompanyId(),
			TestPropsValues.getUserId(), true, ddlRecordSet.getRecordSetId(),
			ddmStructure.getStructureId(),
			ddmStructureLayout.getStructureLayoutId(),
			deDataListView.getDeDataListViewId(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomString());
	}

	private DDLRecordSet _addDDLRecordSet(DDMStructure ddmStructure)
		throws Exception {

		return _ddlRecordSetLocalService.addRecordSet(
			TestPropsValues.getUserId(), _group.getGroupId(),
			ddmStructure.getStructureId(), ddmStructure.getStructureKey(),
			ddmStructure.getNameMap(), ddmStructure.getDescriptionMap(), 0,
			DDLRecordSetConstants.SCOPE_DATA_ENGINE, new ServiceContext());
	}

	private DDMStructure _addDDMStructure() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(AppBuilderApp.class.getName()), _group);

		return ddmStructureTestHelper.addStructure(
			_portal.getClassNameId(AppBuilderApp.class.getName()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_read("test-structured-content-structure.json"),
			StorageType.JSON.getValue());
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

	private String _read(String fileName) throws Exception {
		return StringUtil.read(getClass(), "dependencies/" + fileName);
	}

	@Inject
	private AppBuilderAppDataRecordLinkLocalService
		_appBuilderAppDataRecordLinkLocalService;

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	@Inject
	private AppBuilderAppVersionLocalService _appBuilderAppVersionLocalService;

	@Inject
	private DDLRecordLocalService _ddlRecordLocalService;

	@Inject
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Inject
	private DEDataListViewLocalService _deDataListViewLocalService;

	private Group _group;

	@Inject
	private Portal _portal;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}