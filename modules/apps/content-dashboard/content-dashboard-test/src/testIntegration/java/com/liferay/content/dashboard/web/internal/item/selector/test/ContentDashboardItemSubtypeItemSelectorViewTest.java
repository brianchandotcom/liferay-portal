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
import com.liferay.content.dashboard.web.test.util.ContentDashboardTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
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
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

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

		JSONArray itemSubtypesJSONArray =
			contentDashboardItemTypeJSONObject.getJSONArray("itemSubtypes");

		Assert.assertEquals(2, itemSubtypesJSONArray.length());

		JSONObject itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(
			0);

		Assert.assertEquals(
			DDMStructure.class.getName(),
			itemSubtypeJSONObject.getString("className"));
		Assert.assertNotNull(itemSubtypeJSONObject.getString("classPK"));
		Assert.assertEquals(
			"Basic Web Content (Global)",
			itemSubtypeJSONObject.getString("label"));

		itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(1);

		Assert.assertEquals(
			DDMStructure.class.getName(),
			itemSubtypeJSONObject.getString("className"));
		Assert.assertEquals(
			ddmStructure.getStructureId(),
			itemSubtypeJSONObject.getLong("classPK"));
		Assert.assertEquals(
			_getLabel(ddmStructure.getName(LocaleUtil.US), _group),
			itemSubtypeJSONObject.getString("label"));

		contentDashboardItemTypeJSONObject =
			contentDashboardItemTypesJSONArray.getJSONObject(1);

		Assert.assertEquals(
			"documents-and-media",
			contentDashboardItemTypeJSONObject.getString("icon"));
		Assert.assertEquals(
			"Document", contentDashboardItemTypeJSONObject.getString("label"));

		itemSubtypesJSONArray = contentDashboardItemTypeJSONObject.getJSONArray(
			"itemSubtypes");

		Assert.assertEquals(2, itemSubtypesJSONArray.length());

		itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(0);

		Assert.assertEquals(
			DLFileEntryType.class.getName(),
			itemSubtypeJSONObject.getString("className"));
		Assert.assertNotNull(itemSubtypeJSONObject.getString("classPK"));
		Assert.assertEquals(
			"Basic Document", itemSubtypeJSONObject.getString("label"));

		itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(1);

		Assert.assertEquals(
			DLFileEntryType.class.getName(),
			itemSubtypeJSONObject.getString("className"));
		Assert.assertNotNull(itemSubtypeJSONObject.getString("classPK"));
		Assert.assertNotNull(itemSubtypeJSONObject.getString("label"));

		Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
	}

	@Test
	public void testGetDataWithDDMStructuresFromSeveralSites()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		try {
			DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
				"content", "string", "text", true, "textarea",
				new Locale[] {LocaleUtil.US}, LocaleUtil.US);

			DDMStructure ddmStructure1 = DDMStructureTestUtil.addStructure(
				_group.getGroupId(), JournalArticle.class.getName(), 0, ddmForm,
				LocaleUtil.US,
				ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

			DDMStructure ddmStructure2 = DDMStructureTestUtil.addStructure(
				group.getGroupId(), JournalArticle.class.getName(), 0, ddmForm,
				LocaleUtil.US,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

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

			JSONArray itemSubtypesJSONArray =
				contentDashboardItemTypeJSONObject.getJSONArray("itemSubtypes");

			Assert.assertEquals(3, itemSubtypesJSONArray.length());

			JSONObject itemSubtypeJSONObject =
				itemSubtypesJSONArray.getJSONObject(0);

			Assert.assertEquals(
				DDMStructure.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertNotNull(itemSubtypeJSONObject.getString("classPK"));
			Assert.assertEquals(
				"Basic Web Content (Global)",
				itemSubtypeJSONObject.getString("label"));

			itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(1);

			Assert.assertEquals(
				DDMStructure.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertEquals(
				ddmStructure1.getStructureId(),
				itemSubtypeJSONObject.getLong("classPK"));
			Assert.assertEquals(
				_getLabel(ddmStructure1.getName(LocaleUtil.US), _group),
				itemSubtypeJSONObject.getString("label"));

			itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(2);

			Assert.assertEquals(
				DDMStructure.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertEquals(
				ddmStructure2.getStructureId(),
				itemSubtypeJSONObject.getLong("classPK"));
			Assert.assertEquals(
				_getLabel(ddmStructure2.getName(LocaleUtil.US), group),
				itemSubtypeJSONObject.getString("label"));

			Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
		}
		finally {
			GroupTestUtil.deleteGroup(group);
		}
	}

	@Test
	public void testGetDataWithDDMStructuresFromTheGuestGroup()
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			TestPropsValues.getCompanyId(), GroupConstants.GUEST);

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			"content", "string", "text", true, "textarea",
			new Locale[] {LocaleUtil.US}, LocaleUtil.US);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName(), 0, ddmForm,
			LocaleUtil.US,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		try {
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

			JSONArray itemSubtypesJSONArray =
				contentDashboardItemTypeJSONObject.getJSONArray("itemSubtypes");

			Assert.assertEquals(2, itemSubtypesJSONArray.length());

			JSONObject itemSubtypeJSONObject =
				itemSubtypesJSONArray.getJSONObject(0);

			Assert.assertEquals(
				DDMStructure.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertNotNull(itemSubtypeJSONObject.getString("classPK"));
			Assert.assertEquals(
				"Basic Web Content (Global)",
				itemSubtypeJSONObject.getString("label"));

			itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(1);

			Assert.assertEquals(
				DDMStructure.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertEquals(
				ddmStructure.getStructureId(),
				itemSubtypeJSONObject.getLong("classPK"));
			Assert.assertTrue(
				itemSubtypeJSONObject.getString(
					"label"
				).contains(
					ddmStructure.getName(LocaleUtil.US)
				));

			Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
		}
		finally {
			DDMStructureLocalServiceUtil.deleteDDMStructure(ddmStructure);
		}
	}

	@Test
	public void testGetDataWithDLFileEntryTypesFromSeveralSites()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		try {
			DLFileEntryType dlFileEntryType1 = _getDlFileEntryType(_group);
			DLFileEntryType dlFileEntryType2 = _getDlFileEntryType(group);

			Map<String, Object> data = _getData();

			JSONArray contentDashboardItemTypesJSONArray = (JSONArray)data.get(
				"contentDashboardItemTypes");

			JSONObject contentDashboardItemTypeJSONObject =
				contentDashboardItemTypesJSONArray.getJSONObject(1);

			Assert.assertEquals(
				"documents-and-media",
				contentDashboardItemTypeJSONObject.getString("icon"));
			Assert.assertEquals(
				"Document",
				contentDashboardItemTypeJSONObject.getString("label"));

			JSONArray itemSubtypesJSONArray =
				contentDashboardItemTypeJSONObject.getJSONArray("itemSubtypes");

			Assert.assertEquals(4, itemSubtypesJSONArray.length());

			JSONObject itemSubtypeJSONObject =
				itemSubtypesJSONArray.getJSONObject(0);

			Assert.assertEquals(
				DLFileEntryType.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertNotNull(itemSubtypeJSONObject.getString("classPK"));
			Assert.assertEquals(
				"Basic Document", itemSubtypeJSONObject.getString("label"));

			itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(1);

			Assert.assertEquals(
				DLFileEntryType.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertNotNull(itemSubtypeJSONObject.getString("classPK"));
			Assert.assertNotNull(itemSubtypeJSONObject.getString("label"));

			itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(2);

			Assert.assertEquals(
				DLFileEntryType.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertEquals(
				String.valueOf(dlFileEntryType1.getFileEntryTypeId()),
				itemSubtypeJSONObject.getString("classPK"));
			Assert.assertEquals(
				_getLabel(dlFileEntryType1.getName(LocaleUtil.US), _group),
				itemSubtypeJSONObject.getString("label"));

			itemSubtypeJSONObject = itemSubtypesJSONArray.getJSONObject(3);

			Assert.assertEquals(
				DLFileEntryType.class.getName(),
				itemSubtypeJSONObject.getString("className"));
			Assert.assertEquals(
				String.valueOf(dlFileEntryType2.getFileEntryTypeId()),
				itemSubtypeJSONObject.getString("classPK"));
			Assert.assertEquals(
				_getLabel(dlFileEntryType2.getName(LocaleUtil.US), group),
				itemSubtypeJSONObject.getString("label"));

			Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
		}
		finally {
			GroupTestUtil.deleteGroup(group);
		}
	}

	@Test
	public void testGetDataWithoutGoogleDriveShortcuts() throws Exception {
		DDMStructureTestUtil.getSampleDDMForm(
			"content", "string", "text", true, "textarea",
			new Locale[] {LocaleUtil.US}, LocaleUtil.US);

		DDMStructureTestUtil.getSampleDDMForm(
			"content", "string", "text", true, "textarea",
			new Locale[] {LocaleUtil.US}, LocaleUtil.US);

		Map<String, Object> data = _getData();

		JSONArray contentDashboardItemTypesJSONArray = (JSONArray)data.get(
			"contentDashboardItemTypes");

		JSONObject contentDashboardItemTypeJSONObject =
			contentDashboardItemTypesJSONArray.getJSONObject(1);

		JSONArray itemSubtypesJSONArray =
			contentDashboardItemTypeJSONObject.getJSONArray("itemSubtypes");

		for (int i = 0; i < itemSubtypesJSONArray.length(); i++) {
			JSONObject jsonObject = itemSubtypesJSONArray.getJSONObject(i);

			String className = jsonObject.getString("className");

			Assert.assertEquals(DLFileEntryType.class.getName(), className);

			String classPK = jsonObject.getString("classPK");

			Assert.assertNotNull(classPK);

			String label = jsonObject.getString("label");

			Assert.assertNotNull(label);
			Assert.assertNotEquals("Google Drive Shortcut", label);
		}
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
			WebKeys.THEME_DISPLAY,
			ContentDashboardTestUtil.getThemeDisplay(_group));

		_contentDashboardItemSubtypeItemSelectorView.renderHTML(
			mockHttpServletRequest, new MockHttpServletResponse(), null,
			new MockLiferayPortletURL(), RandomTestUtil.randomString(), true);

		Object contentDashboardItemSubtypeItemSelectorViewDisplayContext =
			mockHttpServletRequest.getAttribute(
				"com.liferay.content.dashboard.web.internal.display.context." +
					"ContentDashboardItemSubtypeItemSelectorViewDisplay" +
						"Context");

		return ReflectionTestUtil.invoke(
			contentDashboardItemSubtypeItemSelectorViewDisplayContext,
			"getData", new Class<?>[0], null);
	}

	private DLFileEntryType _getDlFileEntryType(Group group) throws Exception {
		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			"content", "string", "text", true, "textarea",
			new Locale[] {LocaleUtil.US}, LocaleUtil.US);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName(), 0, ddmForm,
			LocaleUtil.US,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		return DLFileEntryTypeLocalServiceUtil.addFileEntryType(
			TestPropsValues.getUserId(), group.getGroupId(),
			ddmStructure.getStructureId(), RandomTestUtil.randomString(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private String _getLabel(String name, Group group) {
		return StringBundler.concat(
			name, " (", group.getName(LocaleUtil.US), ")");
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