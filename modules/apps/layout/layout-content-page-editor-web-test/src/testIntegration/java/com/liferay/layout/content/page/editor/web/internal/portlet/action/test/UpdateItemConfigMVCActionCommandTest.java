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

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.servlet.PortletServlet;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockActionRequest;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.InputStream;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Pavel Savinov
 */
@RunWith(Arquillian.class)
@Sync
public class UpdateItemConfigMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_objectMapper = new ObjectMapper() {
			{
				configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			}
		};

		_layout = LayoutTestUtil.addTypeContentLayout(_group);
	}

	@Test
	public void testUpdateColumnItemConfigResponsive() throws Exception {
		_testUpdateColumnItemConfigResponsive(
			"column_item_config_responsive.json");
	}

	@Test
	public void testUpdateColumnItemConfigResponsiveExtra() throws Exception {
		_testUpdateColumnItemConfigResponsive(
			"column_item_config_responsive_extra.json");
	}

	@Test
	public void testUpdateColumnItemConfigResponsiveIncomplete()
		throws Exception {

		_testUpdateColumnItemConfigResponsive(
			"column_item_config_responsive_incomplete.json");
	}

	@Test
	public void testUpdateRowItemConfigResponsive() throws Exception {
		_testUpdateRowItemConfigResponsive("row_item_config_responsive.json");
	}

	@Test
	public void testUpdateRowItemConfigResponsiveExtra() throws Exception {
		_testUpdateRowItemConfigResponsive(
			"row_item_config_responsive_extra.json");
	}

	@Test
	public void testUpdateRowItemConfigResponsiveIncomplete() throws Exception {
		_testUpdateRowItemConfigResponsive(
			"row_item_config_responsive_incomplete.json");
	}

	private LayoutStructure _getLayoutStructure() throws Exception {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_layout.getGroupId(), _layout.getPlid());

		return LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));
	}

	private MockActionRequest _getMockActionrequest() {
		MockLiferayPortletActionRequest mockActionRequest =
			new MockLiferayPortletActionRequest();

		mockActionRequest.setAttribute(
			PortletServlet.PORTLET_SERVLET_REQUEST,
			new MockHttpServletRequest());

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setScopeGroupId(_group.getGroupId());

		mockActionRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		mockActionRequest.setParameter(
			"segmentsExperienceId",
			String.valueOf(
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid())));

		return mockActionRequest;
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	private void _testUpdateColumnItemConfigResponsive(String itemConfigfile)
		throws Exception {

		MockActionRequest mockActionRequest = _getMockActionrequest();

		LayoutStructure layoutStructure = _getLayoutStructure();

		LayoutStructureItem layoutStructureItem =
			layoutStructure.addColumnLayoutStructureItem(
				layoutStructure.getMainItemId(), 0);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_layout.getGroupId(), _layout.getPlid(),
				layoutStructure.toString());

		JSONObject jsonObject = layoutStructureItem.getItemConfigJSONObject();

		Assert.assertEquals(
			_objectMapper.readTree(_read("column_item_config.json")),
			_objectMapper.readTree(jsonObject.toString()));

		mockActionRequest.setParameter("itemConfig", _read(itemConfigfile));
		mockActionRequest.setParameter(
			"itemId", layoutStructureItem.getItemId());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "doProcessAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockActionRequest, new MockLiferayPortletActionResponse());

		layoutStructure = _getLayoutStructure();

		layoutStructureItem = layoutStructure.getLayoutStructureItem(
			layoutStructureItem.getItemId());

		jsonObject = layoutStructureItem.getItemConfigJSONObject();

		Assert.assertEquals(
			_objectMapper.readTree(_read("column_item_config_responsive.json")),
			_objectMapper.readTree(jsonObject.toString()));
	}

	private void _testUpdateRowItemConfigResponsive(String itemConfigFile)
		throws Exception {

		MockActionRequest mockActionRequest = _getMockActionrequest();

		LayoutStructure layoutStructure = _getLayoutStructure();

		LayoutStructureItem layoutStructureItem =
			layoutStructure.addRowStyledLayoutStructureItem(
				layoutStructure.getMainItemId(), 0, 6);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_layout.getGroupId(), _layout.getPlid(),
				layoutStructure.toString());

		JSONObject jsonObject = layoutStructureItem.getItemConfigJSONObject();

		Assert.assertEquals(
			_objectMapper.readTree(_read("row_item_config.json")),
			_objectMapper.readTree(jsonObject.toString()));

		mockActionRequest.setParameter("itemConfig", _read(itemConfigFile));
		mockActionRequest.setParameter(
			"itemId", layoutStructureItem.getItemId());

		ReflectionTestUtil.invoke(
			_mvcActionCommand, "doProcessAction",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			mockActionRequest, new MockLiferayPortletActionResponse());

		layoutStructure = _getLayoutStructure();

		layoutStructureItem = layoutStructure.getLayoutStructureItem(
			layoutStructureItem.getItemId());

		jsonObject = layoutStructureItem.getItemConfigJSONObject();

		Assert.assertEquals(
			_objectMapper.readTree(_read("row_item_config_responsive.json")),
			_objectMapper.readTree(jsonObject.toString()));
	}

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/update_item_config"
	)
	private MVCActionCommand _mvcActionCommand;

	private ObjectMapper _objectMapper;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}