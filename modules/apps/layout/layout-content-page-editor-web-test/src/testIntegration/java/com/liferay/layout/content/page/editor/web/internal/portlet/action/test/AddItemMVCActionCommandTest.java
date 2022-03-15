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
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.responsive.ViewportSize;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.ColumnLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RowStyledLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.props.test.util.PropsTemporarySwapper;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;

import javax.portlet.ActionRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
@Sync
public class AddItemMVCActionCommandTest {

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

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), _layout.getPlid());

		String data = layoutPageTemplateStructure.getData(
			SegmentsExperienceConstants.ID_DEFAULT);

		_layoutStructure = LayoutStructure.of(data);
	}

	@Test
	public void testAddItemToLayoutData() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_CONTAINER);
		mockLiferayPortletActionRequest.addParameter(
			"parentItemId", _layoutStructure.getMainItemId());
		mockLiferayPortletActionRequest.addParameter("position", "0");

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_addItemToLayoutData",
			new Class<?>[] {ActionRequest.class},
			mockLiferayPortletActionRequest);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutDataJSONObject.toString());

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				layoutStructure.getMainItemId());

		List<String> childrenItemIds =
			rootLayoutStructureItem.getChildrenItemIds();

		String itemId = childrenItemIds.get(0);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		Assert.assertEquals(
			_layoutStructure.getMainItemId(),
			layoutStructureItem.getParentItemId());
		Assert.assertEquals(
			LayoutDataItemTypeConstants.TYPE_CONTAINER,
			layoutStructureItem.getItemType());
		Assert.assertTrue(
			layoutStructureItem instanceof ContainerStyledLayoutStructureItem);
	}

	@Test
	public void testAddItemToLayoutDataAtPosition() throws Exception {
		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		_layoutStructure.addContainerStyledLayoutStructureItem(
			_layoutStructure.getMainItemId(), 0);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_group.getGroupId(), _layout.getPlid(),
				_layoutStructure.toString());

		mockLiferayPortletActionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_CONTAINER);
		mockLiferayPortletActionRequest.addParameter(
			"parentItemId", _layoutStructure.getMainItemId());
		mockLiferayPortletActionRequest.addParameter("position", "1");

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_addItemToLayoutData",
			new Class<?>[] {ActionRequest.class},
			mockLiferayPortletActionRequest);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutDataJSONObject.toString());

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				layoutStructure.getMainItemId());

		List<String> childrenItemIds =
			rootLayoutStructureItem.getChildrenItemIds();

		String itemId = childrenItemIds.get(1);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		Assert.assertEquals(
			_layoutStructure.getMainItemId(),
			layoutStructureItem.getParentItemId());
		Assert.assertEquals(
			LayoutDataItemTypeConstants.TYPE_CONTAINER,
			layoutStructureItem.getItemType());
		Assert.assertTrue(
			layoutStructureItem instanceof ContainerStyledLayoutStructureItem);
	}

	@Test
	public void testAddItemToLayoutDataItemTypeRowMobileLandscapeConfig()
		throws Exception {

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper("feature.flag.LPS-119551", "false")) {

			_assertMobileLandscapeConfig(null, null);
		}

		try (PropsTemporarySwapper propsTemporarySwapper =
				new PropsTemporarySwapper("feature.flag.LPS-119551", "true")) {

			_assertMobileLandscapeConfig(1, 12);
		}
	}

	private void _assertMobileLandscapeConfig(
			Integer expectedModulesPerRow,
			Integer expectedColumnMobileLandscapeSize)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			_getMockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_ROW);
		mockLiferayPortletActionRequest.addParameter(
			"parentItemId", _layoutStructure.getMainItemId());
		mockLiferayPortletActionRequest.addParameter("position", "0");

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_addItemToLayoutData",
			new Class<?>[] {ActionRequest.class},
			mockLiferayPortletActionRequest);

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutDataJSONObject.toString());

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(
				layoutStructure.getMainItemId());

		List<String> childrenItemIds =
			rootLayoutStructureItem.getChildrenItemIds();

		String itemId = childrenItemIds.get(0);

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(itemId);

		Assert.assertEquals(
			_layoutStructure.getMainItemId(),
			layoutStructureItem.getParentItemId());
		Assert.assertEquals(
			LayoutDataItemTypeConstants.TYPE_ROW,
			layoutStructureItem.getItemType());
		Assert.assertTrue(
			layoutStructureItem instanceof RowStyledLayoutStructureItem);

		RowStyledLayoutStructureItem rowStyledLayoutStructureItem =
			(RowStyledLayoutStructureItem)layoutStructureItem;

		JSONObject itemConfigJSONObject =
			rowStyledLayoutStructureItem.getItemConfigJSONObject();

		JSONObject mobileLandscapeConfigJSONObject =
			itemConfigJSONObject.getJSONObject(
				ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId());

		Assert.assertNotNull(mobileLandscapeConfigJSONObject);
		Assert.assertEquals(
			expectedModulesPerRow,
			mobileLandscapeConfigJSONObject.get("modulesPerRow"));

		List<String> columnsItemIds =
			rowStyledLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			columnsItemIds.toString(), _DEFAULT_ROW_COLUMNS,
			columnsItemIds.size());

		for (int i = 0; i < _DEFAULT_ROW_COLUMNS; i++) {
			String columnItemId = columnsItemIds.get(i);

			ColumnLayoutStructureItem columnLayoutStructureItem =
				(ColumnLayoutStructureItem)
					layoutStructure.getLayoutStructureItem(columnItemId);

			itemConfigJSONObject =
				columnLayoutStructureItem.getItemConfigJSONObject();

			mobileLandscapeConfigJSONObject =
				itemConfigJSONObject.getJSONObject(
					ViewportSize.MOBILE_LANDSCAPE.getViewportSizeId());

			Assert.assertNotNull(mobileLandscapeConfigJSONObject);
			Assert.assertEquals(
				expectedColumnMobileLandscapeSize,
				mobileLandscapeConfigJSONObject.get("size"));
		}
	}

	private MockLiferayPortletActionRequest
			_getMockLiferayPortletActionRequest()
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockLiferayPortletActionRequest.addParameter(
			"segmentsExperienceId",
			String.valueOf(
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid())));

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
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

	private static final int _DEFAULT_ROW_COLUMNS = 3;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private LayoutStructure _layoutStructure;

	@Inject(filter = "mvc.command.name=/layout_content_page_editor/add_item")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}