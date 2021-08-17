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
public class ContentDashboardFileExtensionItemSelectorViewTest {

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

		JSONArray fileExtensionGroupsJSONArray = (JSONArray)data.get(
			"fileExtensionGroups");

		Assert.assertEquals(9, fileExtensionGroupsJSONArray.length());

		_assertExtensionGroupJSONObject(
			"document-multimedia", "Audio",
			fileExtensionGroupsJSONArray.getJSONObject(0));
		_assertExtensionGroupJSONObject(
			"document-code", "Code",
			fileExtensionGroupsJSONArray.getJSONObject(1));
		_assertExtensionGroupJSONObject(
			"document-compressed", "compressed",
			fileExtensionGroupsJSONArray.getJSONObject(2));
		_assertExtensionGroupJSONObject(
			"document-image", "Image",
			fileExtensionGroupsJSONArray.getJSONObject(3));
		_assertExtensionGroupJSONObject(
			"document-presentation", "presentation",
			fileExtensionGroupsJSONArray.getJSONObject(4));
		_assertExtensionGroupJSONObject(
			"document-table", "spreadsheet",
			fileExtensionGroupsJSONArray.getJSONObject(5));
		_assertExtensionGroupJSONObject(
			"document-text", "Text",
			fileExtensionGroupsJSONArray.getJSONObject(6));
		_assertExtensionGroupJSONObject(
			"document-pdf", "vectorial",
			fileExtensionGroupsJSONArray.getJSONObject(7));
		_assertExtensionGroupJSONObject(
			"document-multimedia", "Video",
			fileExtensionGroupsJSONArray.getJSONObject(8));

		Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
	}

	private void _assertExtensionGroupJSONObject(
		String expectedIcon, String expectedLabel,
		JSONObject fileExtensionGroupJSONObject) {

		Assert.assertEquals(
			expectedIcon, fileExtensionGroupJSONObject.getString("icon"));
		Assert.assertEquals(
			expectedLabel, fileExtensionGroupJSONObject.getString("label"));

		JSONArray fileExtensionsJSONArray =
			fileExtensionGroupJSONObject.getJSONArray("fileExtensions");

		Assert.assertTrue(fileExtensionsJSONArray.length() > 0);
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

		_contentDashboardFileExtensionItemSelectorView.renderHTML(
			mockHttpServletRequest, new MockHttpServletResponse(), null,
			new MockLiferayPortletURL(), RandomTestUtil.randomString(), true);

		Object contentDashboardFileExtensionItemSelectorViewDisplayContext =
			mockHttpServletRequest.getAttribute(
				"com.liferay.content.dashboard.web.internal.display.context." +
					"ContentDashboardFileExtensionItemSelectorViewDisplay" +
						"Context");

		return ReflectionTestUtil.invoke(
			contentDashboardFileExtensionItemSelectorViewDisplayContext,
			"getData", new Class<?>[0], null);
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setScopeGroupId(_group.getGroupId());

		return themeDisplay;
	}

	@Inject(
		filter = "component.name=*.ContentDashboardFileExtensionItemSelectorView",
		type = ItemSelectorView.class
	)
	private ItemSelectorView<ItemSelectorCriterion>
		_contentDashboardFileExtensionItemSelectorView;

	@DeleteAfterTestRun
	private Group _group;

}