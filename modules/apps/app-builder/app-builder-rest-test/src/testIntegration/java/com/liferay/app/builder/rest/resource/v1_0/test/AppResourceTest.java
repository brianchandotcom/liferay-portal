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

import com.liferay.app.builder.rest.client.dto.v1_0.App;
import com.liferay.app.builder.rest.client.dto.v1_0.AppDeployment;
import com.liferay.app.builder.rest.client.pagination.Page;
import com.liferay.app.builder.rest.client.pagination.Pagination;
import com.liferay.app.builder.service.AppBuilderAppLocalService;
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
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.io.InputStream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Gabriel Albuquerque
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
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

	@Override
	public void testGetAppsPage() throws Exception {
		super.testGetAppsPage();

		App app1 = randomApp();

		app1.setActive(true);
		app1.setAppDeployments(
			new AppDeployment[] {
				new AppDeployment() {
					{
						settings = HashMapBuilder.<String, Object>put(
							"scope", new String[] {"control_panel"}
						).build();
						type = "productMenu";
					}
				}
			});

		app1 = testGetAppsPage_addApp(app1);

		App app2 = randomApp();

		app2.setActive(true);
		app2.setAppDeployments(
			new AppDeployment[] {
				new AppDeployment() {
					{
						type = "standalone";
					}
				}
			});

		app2 = testGetAppsPage_addApp(app2);

		App app3 = randomApp();

		app3.setActive(false);
		app3.setAppDeployments(
			new AppDeployment[] {
				new AppDeployment() {
					{
						type = "standalone";
					}
				},
				new AppDeployment() {
					{
						type = "widget";
					}
				}
			});

		app3 = testGetAppsPage_addApp(app3);

		Page<App> page = appResource.getAppsPage(
			true, new String[] {"productMenu"}, StringPool.BLANK,
			new Long[] {testGroup.getCreatorUserId()}, Pagination.of(1, 10),
			null);

		Assert.assertEquals(1, page.getTotalCount());

		assertEquals(Arrays.asList(app1), (List<App>)page.getItems());
		assertValid(page);

		page = appResource.getAppsPage(
			false, new String[] {"productMenu"}, StringPool.BLANK,
			new Long[] {testGroup.getCreatorUserId()}, Pagination.of(1, 10),
			null);

		Assert.assertEquals(0, page.getTotalCount());

		page = appResource.getAppsPage(
			null, new String[] {"productMenu", "standalone"}, StringPool.BLANK,
			null, Pagination.of(1, 10), null);

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2, app3), (List<App>)page.getItems());

		page = appResource.getAppsPage(
			null, null, StringPool.BLANK,
			new Long[] {TestPropsValues.getUserId()}, Pagination.of(1, 10),
			null);

		Assert.assertEquals(3, page.getTotalCount());

		assertEqualsIgnoringOrder(
			Arrays.asList(app1, app2, app3), (List<App>)page.getItems());

		page = appResource.getAppsPage(
			null, null, StringPool.BLANK, new Long[] {1L}, Pagination.of(1, 10),
			null);

		Assert.assertEquals(0, page.getTotalCount());
	}

	@Override
	public void testPutAppDeploy() throws Exception {
		App postApp = testPutApp_addApp();

		appResource.putAppDeploy(postApp.getId());

		App getApp = appResource.getApp(postApp.getId());

		Assert.assertEquals(getApp.getActive(), true);
	}

	@Override
	public void testPutAppUndeploy() throws Exception {
		App postApp = testPutApp_addApp();

		appResource.putAppUndeploy(postApp.getId());

		App getApp = appResource.getApp(postApp.getId());

		Assert.assertEquals(getApp.getActive(), false);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"dataDefinitionId", "dataDefinitionName", "dataLayoutId",
			"dataListViewId", "userId"
		};
	}

	@Override
	protected App randomApp() {
		return new App() {
			{
				active = false;
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
				dataDefinitionName = _ddmStructure.getName(LocaleUtil.US);
				dataLayoutId = _ddmStructureLayout.getStructureLayoutId();
				dataListViewId = _deDataListView.getDeDataListViewId();
				siteId = _ddmStructure.getGroupId();
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
		return testGetApp_addApp();
	}

	@Override
	protected App testGetApp_addApp() throws Exception {
		return testGetAppsPage_addApp(randomApp());
	}

	@Override
	protected App testGetAppsPage_addApp(App app) throws Exception {
		return testGetDataDefinitionAppsPage_addApp(
			app.getDataDefinitionId(), app);
	}

	@Override
	protected App testGetDataDefinitionAppsPage_addApp(
			Long dataDefinitionId, App app)
		throws Exception {

		return appResource.postDataDefinitionApp(dataDefinitionId, app);
	}

	@Override
	protected Long testGetDataDefinitionAppsPage_getDataDefinitionId() {
		return _ddmStructure.getStructureId();
	}

	@Override
	protected App testGetSiteAppsPage_addApp(Long siteId, App app)
		throws Exception {

		return testGetDataDefinitionAppsPage_addApp(
			app.getDataDefinitionId(), app);
	}

	@Override
	protected App testGraphQLApp_addApp() throws Exception {
		return testGetApp_addApp();
	}

	@Override
	protected App testPostDataDefinitionApp_addApp(App app) throws Exception {
		return testGetAppsPage_addApp(app);
	}

	@Override
	protected App testPutApp_addApp() throws Exception {
		return testGetApp_addApp();
	}

	private DDMStructure _addDDMStructure(Group group) throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(
					"com.liferay.app.builder.model.AppBuilderApp"),
				group);

		return ddmStructureTestHelper.addStructure(
			PortalUtil.getClassNameId(
				"com.liferay.app.builder.model.AppBuilderApp"),
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
		Class<?> clazz = AppResourceTest.class;

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	@Inject
	private AppBuilderAppLocalService _appBuilderAppLocalService;

	private DDMStructure _ddmStructure;
	private DDMStructureLayout _ddmStructureLayout;
	private DEDataListView _deDataListView;

	@Inject
	private DEDataListViewLocalService _deDataListViewLocalService;

	private DDMStructure _irrelevantDDMStructure;

}