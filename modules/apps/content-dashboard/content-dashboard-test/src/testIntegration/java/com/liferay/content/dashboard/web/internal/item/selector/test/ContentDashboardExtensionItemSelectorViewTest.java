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

package com.liferay.content.dashboard.web.internal.item.selector.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
@Sync
public class ContentDashboardExtensionItemSelectorViewTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetData() throws Exception {
		Map<String, Object> data = _getData();

		JSONArray extensionGroupsJSONArray = (JSONArray)data.get(
			"extensionGroups");

		Assert.assertEquals(9, extensionGroupsJSONArray.length());

		_assertExtensionGroupJSONObject(
			"document-multimedia", "Audio",
			extensionGroupsJSONArray.getJSONObject(0));
		_assertExtensionGroupJSONObject(
			"document-code", "Code", extensionGroupsJSONArray.getJSONObject(1));
		_assertExtensionGroupJSONObject(
			"document-compressed", "compressed",
			extensionGroupsJSONArray.getJSONObject(2));
		_assertExtensionGroupJSONObject(
			"document-image", "Image",
			extensionGroupsJSONArray.getJSONObject(3));
		_assertExtensionGroupJSONObject(
			"document-presentation", "presentation",
			extensionGroupsJSONArray.getJSONObject(4));
		_assertExtensionGroupJSONObject(
			"document-table", "spreadsheet",
			extensionGroupsJSONArray.getJSONObject(5));
		_assertExtensionGroupJSONObject(
			"document-text", "Text", extensionGroupsJSONArray.getJSONObject(6));
		_assertExtensionGroupJSONObject(
			"document-pdf", "vectorial",
			extensionGroupsJSONArray.getJSONObject(7));
		_assertExtensionGroupJSONObject(
			"document-multimedia", "Video",
			extensionGroupsJSONArray.getJSONObject(8));

		Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
	}

	private void _assertExtensionGroupJSONObject(
		String expectedIcon, String expectedLabel,
		JSONObject extensionGroupJSONObject) {

		Assert.assertEquals(
			expectedIcon, extensionGroupJSONObject.getString("icon"));
		Assert.assertEquals(
			expectedLabel, extensionGroupJSONObject.getString("label"));

		JSONArray extensionsJSONArray = extensionGroupJSONObject.getJSONArray(
			"extensions");

		Assert.assertTrue(extensionsJSONArray.length() > 0);
	}

	private Map<String, Object> _getData() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		mockLiferayPortletRenderRequest.setAttribute(
			"null-" + WebKeys.CURRENT_PORTLET_URL, new MockLiferayPortletURL());

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST,
			mockLiferayPortletRenderRequest);

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletRenderResponse());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		_contentDashboardExtensionItemSelectorView.renderHTML(
			mockHttpServletRequest, new MockHttpServletResponse(), null,
			new MockLiferayPortletURL(), RandomTestUtil.randomString(), true);

		Object contentDashboardExtensionItemSelectorViewDisplayContext =
			mockHttpServletRequest.getAttribute(
				"com.liferay.content.dashboard.web.internal.display.context." +
					"ContentDashboardExtensionItemSelectorViewDisplayContext");

		return ReflectionTestUtil.invoke(
			contentDashboardExtensionItemSelectorViewDisplayContext, "getData",
			new Class<?>[0], null);
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setScopeGroupId(_group.getGroupId());

		return themeDisplay;
	}

	@Inject(
		filter = "component.name=*.ContentDashboardExtensionItemSelectorView",
		type = ItemSelectorView.class
	)
	private ItemSelectorView<ItemSelectorCriterion>
		_contentDashboardExtensionItemSelectorView;

	@DeleteAfterTestRun
	private Group _group;

}