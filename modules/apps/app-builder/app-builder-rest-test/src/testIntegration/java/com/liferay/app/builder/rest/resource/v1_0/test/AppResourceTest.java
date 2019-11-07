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

package com.liferay.app.builder.rest.resource.v1_0.test;

import com.liferay.app.builder.constants.AppBuilderAppConstants;
import com.liferay.app.builder.rest.client.constant.v1_0.DeploymentAction;
import com.liferay.app.builder.rest.client.dto.v1_0.App;
import com.liferay.app.builder.rest.client.dto.v1_0.AppDeployment;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.model.DEDataListView;
import com.liferay.data.engine.service.DEDataListViewLocalService;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureLayoutTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@RunWith(Arquillian.class)
public class AppResourceTest extends BaseAppResourceTestCase {

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_ddmStructure = _addDDMStructure(testGroup);
		_irrelevantDDMStructure = _addDDMStructure(irrelevantGroup);

		_ddmStructureLayout = _addDDMStructureLayout(
			_ddmStructure.getStructureId());

		_deDataListView = _deDataListViewLocalService.addDEDataListView(
			testGroup.getGroupId(), testCompany.getCompanyId(),
			testGroup.getCreatorUserId(), StringPool.BLANK,
			_ddmStructure.getStructureId(), StringPool.BLANK, null,
			StringPool.BLANK);
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteApp() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetApp() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSiteAppsPage() {
	}

	@Override
	public void testPutAppDeployment() throws Exception {
		App postApp = testPutApp_addApp();

		appResource.putAppDeployment(postApp.getId(), DeploymentAction.DEPLOY);

		App getApp = appResource.getApp(postApp.getId());

		Assert.assertEquals(
			getApp.getStatus(),
			AppBuilderAppConstants.Status.DEPLOYED.getLabel());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"dataDefinitionId", "dataLayoutId", "dataListViewId"
		};
	}

	@Override
	protected App randomApp() {
		return new App() {
			{
				appDeployments = new AppDeployment[] {
					new AppDeployment() {
						{
							settings = HashMapBuilder.<String, Object>put(
								"scope", new String[] {"control_panel"}
							).build();
							type = "productMenu";
						}
					},
					new AppDeployment() {
						{
							settings = new HashMap<>();
							type = "standalone";
						}
					},
					new AppDeployment() {
						{
							settings = new HashMap<>();
							type = "widget";
						}
					}
				};
				dataDefinitionId = _ddmStructure.getStructureId();
				dataLayoutId = _ddmStructureLayout.getStructureLayoutId();
				dataListViewId = _deDataListView.getDeDataListViewId();
				siteId = _ddmStructure.getGroupId();
				status = AppBuilderAppConstants.Status.UNDEPLOYED.getLabel();
				userId = testGroup.getCreatorUserId();
			}
		};
	}

	@Override
	protected App randomIrrelevantApp() throws Exception {
		App randomIrrelevantApp = super.randomIrrelevantApp();

		randomIrrelevantApp.setDataDefinitionId(
			_irrelevantDDMStructure.getStructureId());

		return randomIrrelevantApp;
	}

	@Override
	protected App testDeleteApp_addApp() throws Exception {
		return appResource.postDataDefinitionApp(
			_ddmStructure.getStructureId(), randomApp());
	}

	@Override
	protected App testGetApp_addApp() throws Exception {
		return appResource.postDataDefinitionApp(
			_ddmStructure.getStructureId(), randomApp());
	}

	@Override
	protected Long testGetDataDefinitionAppsPage_getDataDefinitionId() {
		return _ddmStructure.getStructureId();
	}

	@Override
	protected App testGetSiteAppsPage_addApp(Long siteId, App app)
		throws Exception {

		return appResource.postDataDefinitionApp(
			app.getDataDefinitionId(), app);
	}

	@Override
	protected App testPutApp_addApp() throws Exception {
		return appResource.postDataDefinitionApp(
			_ddmStructure.getStructureId(), randomApp());
	}

	private DDMStructure _addDDMStructure(Group group) throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(_RESOURCE_NAME), group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(_RESOURCE_NAME),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_read("test-structured-content-structure.json"),
			StorageType.JSON.getValue());
	}

	private DDMStructureLayout _addDDMStructureLayout(long ddmStructureId)
		throws Exception {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(new Locale("en_US"));

		DDMStructureLayoutTestHelper ddmStructureLayoutTestHelper =
			new DDMStructureLayoutTestHelper(testGroup);

		return ddmStructureLayoutTestHelper.addStructureLayout(
			ddmStructureId, ddmFormLayout);
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = AppResourceTest.class;

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private static final String _RESOURCE_NAME =
		"com.liferay.data.engine.rest.internal.model.InternalDataDefinition";

	private DDMStructure _ddmStructure;
	private DDMStructureLayout _ddmStructureLayout;
	private DEDataListView _deDataListView;

	@Inject
	private DEDataListViewLocalService _deDataListViewLocalService;

	private DDMStructure _irrelevantDDMStructure;

}