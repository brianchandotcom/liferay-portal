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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
@Sync
public class AddItemReactMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_layout = LayoutTestUtil.addLayout(_group);

		_layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				addLayoutPageTemplateStructure(
					TestPropsValues.getUserId(), _group.getGroupId(),
					_portal.getClassNameId(Layout.class.getName()),
					_layout.getPlid(), _read("layout_data.json"),
					ServiceContextTestUtil.getServiceContext(
						_group, TestPropsValues.getUserId()));
	}

	@Test
	public void testAddItemToLayoutData() throws Exception {
		MockLiferayPortletActionRequest actionRequest = _getMockActionRequest();

		actionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_CONTAINER);
		actionRequest.addParameter("parentItemId", "root");
		actionRequest.addParameter("position", "0");

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "addItemToLayoutData",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		JSONObject rootItemJSONObject = itemsJSONObject.getJSONObject("root");

		JSONArray childrenJSONArray = rootItemJSONObject.getJSONArray(
			"children");

		String itemId = childrenJSONArray.getString(0);

		JSONObject newItemJSONObject = itemsJSONObject.getJSONObject(itemId);

		Assert.assertEquals("root", newItemJSONObject.getString("parentId"));
		Assert.assertEquals(
			LayoutDataItemTypeConstants.TYPE_CONTAINER,
			newItemJSONObject.getString("type"));
	}

	@Test
	public void testAddItemToLayoutDataAtPosition() throws Exception {
		MockLiferayPortletActionRequest actionRequest = _getMockActionRequest();

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructure(
				_group.getGroupId(),
				_portal.getClassNameId(Layout.class.getName()),
				_layout.getPlid(), _read("layout_data_with_children.json"));

		actionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_CONTAINER);
		actionRequest.addParameter("parentItemId", "root");
		actionRequest.addParameter("position", "1");

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "addItemToLayoutData",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		JSONObject itemsJSONObject = layoutDataJSONObject.getJSONObject(
			"items");

		JSONObject rootItemJSONObject = itemsJSONObject.getJSONObject("root");

		JSONArray childrenJSONArray = rootItemJSONObject.getJSONArray(
			"children");

		String itemId = childrenJSONArray.getString(1);

		JSONObject newItemJSONObject = itemsJSONObject.getJSONObject(itemId);

		Assert.assertEquals("root", newItemJSONObject.getString("parentId"));
		Assert.assertEquals(
			LayoutDataItemTypeConstants.TYPE_CONTAINER,
			newItemJSONObject.getString("type"));
	}

	private MockActionRequest _getMockActionRequest() throws PortalException {
		MockActionRequest mockActionRequest = new MockActionRequest();

		mockActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));

		return mockActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@DeleteAfterTestRun
	private LayoutPageTemplateStructure _layoutPageTemplateStructure;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(filter = "mvc.command.name=/content_layout/add_item_react")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private static class MockActionRequest
		extends MockLiferayPortletActionRequest {

		@Override
		public HttpServletRequest getHttpServletRequest() {
			return new MockHttpServletRequest();
		}

	}

}