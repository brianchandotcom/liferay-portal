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
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.journal.model.JournalArticle;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
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
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Dictionary;
import java.util.Locale;
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
 * @author Cristina González
 */
@RunWith(Arquillian.class)
@Sync
public class ContentDashboardItemSubtypeItemSelectorViewTest {

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
		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			"content", "string", "text", true, "textarea",
			new Locale[] {LocaleUtil.US}, LocaleUtil.US);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), 0, ddmForm,
			LocaleUtil.US, ServiceContextTestUtil.getServiceContext());

		Map<String, Object> data = _getData();

		JSONArray contentDashboardItemTypesJSONArray = (JSONArray)data.get(
			"contentDashboardItemTypes");

		JSONObject contentDashboardItemTypeJSONObject =
			contentDashboardItemTypesJSONArray.getJSONObject(0);

		Assert.assertEquals(
			"web-content",
			contentDashboardItemTypeJSONObject.getString("icon"));
		Assert.assertEquals(
			"Web Content Article",
			contentDashboardItemTypeJSONObject.getString("label"));

		JSONArray itemTypesJSONArray =
			contentDashboardItemTypeJSONObject.getJSONArray("itemTypes");

		Assert.assertEquals(2, itemTypesJSONArray.length());

		JSONObject itemTypeJSONObject = itemTypesJSONArray.getJSONObject(0);

		Assert.assertEquals(
			DDMStructure.class.getName(),
			itemTypeJSONObject.getString("className"));
		Assert.assertNotNull(itemTypeJSONObject.getString("classPK"));
		Assert.assertEquals(
			"Basic Web Content", itemTypeJSONObject.getString("label"));

		itemTypeJSONObject = itemTypesJSONArray.getJSONObject(1);

		Assert.assertEquals(
			DDMStructure.class.getName(),
			itemTypeJSONObject.getString("className"));
		Assert.assertEquals(
			ddmStructure.getStructureId(),
			itemTypeJSONObject.getLong("classPK"));
		Assert.assertEquals(
			ddmStructure.getName(LocaleUtil.getDefault()),
			itemTypeJSONObject.getString("label"));

		contentDashboardItemTypeJSONObject =
			contentDashboardItemTypesJSONArray.getJSONObject(1);

		Assert.assertEquals(
			"documents-and-media",
			contentDashboardItemTypeJSONObject.getString("icon"));
		Assert.assertEquals(
			"Document", contentDashboardItemTypeJSONObject.getString("label"));

		itemTypesJSONArray = contentDashboardItemTypeJSONObject.getJSONArray(
			"itemTypes");

		Assert.assertEquals(3, itemTypesJSONArray.length());

		itemTypeJSONObject = itemTypesJSONArray.getJSONObject(0);

		Assert.assertEquals(
			DLFileEntryType.class.getName(),
			itemTypeJSONObject.getString("className"));
		Assert.assertNotNull(itemTypeJSONObject.getString("classPK"));
		Assert.assertEquals(
			"Basic Document", itemTypeJSONObject.getString("label"));

		itemTypeJSONObject = itemTypesJSONArray.getJSONObject(1);

		Assert.assertEquals(
			DLFileEntryType.class.getName(),
			itemTypeJSONObject.getString("className"));
		Assert.assertNotNull(itemTypeJSONObject.getString("classPK"));
		Assert.assertNotNull(itemTypeJSONObject.getString("label"));

		itemTypeJSONObject = itemTypesJSONArray.getJSONObject(2);

		Assert.assertEquals(
			DLFileEntryType.class.getName(),
			itemTypeJSONObject.getString("className"));
		Assert.assertNotNull(itemTypeJSONObject.getString("classPK"));
		Assert.assertNotNull(itemTypeJSONObject.getString("label"));

		Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
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

		_withFFContentDashboardDocumentConfigurationEnabled(
			() -> _contentDashboardItemSubtypeItemSelectorView.renderHTML(
				mockHttpServletRequest, new MockHttpServletResponse(), null,
				new MockLiferayPortletURL(), RandomTestUtil.randomString(),
				true));

		Object contentDashboardItemSubtypeItemSelectorViewDisplayContext =
			mockHttpServletRequest.getAttribute(
				"com.liferay.content.dashboard.web.internal.display.context." +
					"ContentDashboardItemSubtypeItemSelectorViewDisplay" +
						"Context");

		return ReflectionTestUtil.invoke(
			contentDashboardItemSubtypeItemSelectorViewDisplayContext,
			"getData", new Class<?>[0], null);
	}

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setScopeGroupId(_group.getGroupId());

		return themeDisplay;
	}

	private void _withFFContentDashboardDocumentConfigurationEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary =
			HashMapDictionaryBuilder.<String, Object>put(
				"enabled", true
			).build();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.content.dashboard.web.internal." +
						"configuration.FFContentDashboardDocumentConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	@Inject(
		filter = "component.name=*.ContentDashboardItemSubtypeItemSelectorView",
		type = ItemSelectorView.class
	)
	private ItemSelectorView<ItemSelectorCriterion>
		_contentDashboardItemSubtypeItemSelectorView;

	@DeleteAfterTestRun
	private Group _group;

}