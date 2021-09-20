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
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetData() throws Exception {
		_addFileEntry("java");
		_addFileEntry("jpg");
		_addFileEntry("liferay");
		_addFileEntry("mp3");
		_addFileEntry("mp4");
		_addFileEntry("pdf");
		_addFileEntry("ppt");
		_addFileEntry("txt");
		_addFileEntry("xls");
		_addFileEntry("zip");

		Map<String, Object> data = _getData();

		JSONArray fileExtensionGroupsJSONArray = (JSONArray)data.get(
			"fileExtensionGroups");

		Assert.assertEquals(10, fileExtensionGroupsJSONArray.length());

		_assertExtensionGroupJSONObject(
			"mp3", "document-multimedia", "Audio",
			fileExtensionGroupsJSONArray.getJSONObject(0));
		_assertExtensionGroupJSONObject(
			"java", "document-code", "Code",
			fileExtensionGroupsJSONArray.getJSONObject(1));
		_assertExtensionGroupJSONObject(
			"zip", "document-compressed", "Compressed",
			fileExtensionGroupsJSONArray.getJSONObject(2));
		_assertExtensionGroupJSONObject(
			"jpg", "document-image", "Image",
			fileExtensionGroupsJSONArray.getJSONObject(3));
		_assertExtensionGroupJSONObject(
			"ppt", "document-presentation", "Presentation",
			fileExtensionGroupsJSONArray.getJSONObject(4));
		_assertExtensionGroupJSONObject(
			"xls", "document-table", "Spreadsheet",
			fileExtensionGroupsJSONArray.getJSONObject(5));
		_assertExtensionGroupJSONObject(
			"txt", "document-text", "Text",
			fileExtensionGroupsJSONArray.getJSONObject(6));
		_assertExtensionGroupJSONObject(
			"pdf", "document-vector", "Vectorial",
			fileExtensionGroupsJSONArray.getJSONObject(7));
		_assertExtensionGroupJSONObject(
			"mp4", "document-multimedia", "Video",
			fileExtensionGroupsJSONArray.getJSONObject(8));
		_assertExtensionGroupJSONObject(
			"liferay", "document-default", "Other",
			fileExtensionGroupsJSONArray.getJSONObject(9));

		Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
	}

	@Test
	public void testGetDataWithNoFileEntries() throws Exception {
		Map<String, Object> data = _getData();

		JSONArray fileExtensionGroupsJSONArray = (JSONArray)data.get(
			"fileExtensionGroups");

		Assert.assertEquals(0, fileExtensionGroupsJSONArray.length());

		Assert.assertNotNull(data.get("itemSelectorSaveEvent"));
	}

	private FileEntry _addFileEntry(String fileExtension) throws Exception {
		return DLAppLocalServiceUtil.addFileEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + "." + fileExtension,
			MimeTypesUtil.getExtensionContentType(fileExtension), new byte[0],
			null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private void _assertExtensionGroupJSONObject(
		String expectedFileExtension, String expectedIcon, String expectedLabel,
		JSONObject fileExtensionGroupJSONObject) {

		Assert.assertEquals(
			expectedIcon, fileExtensionGroupJSONObject.getString("icon"));
		Assert.assertEquals(
			expectedLabel, fileExtensionGroupJSONObject.getString("label"));

		JSONArray fileExtensionsJSONArray =
			fileExtensionGroupJSONObject.getJSONArray("fileExtensions");

		Assert.assertEquals(1, fileExtensionsJSONArray.length());

		JSONObject fileExtensionJSONObject =
			fileExtensionsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			expectedFileExtension,
			fileExtensionJSONObject.getString("fileExtension"));
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

		ThemeDisplay themeDisplay = ContentDashboardTestUtil.getThemeDisplay(
			_group);

		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

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

	@Inject(
		filter = "component.name=*.ContentDashboardFileExtensionItemSelectorView",
		type = ItemSelectorView.class
	)
	private ItemSelectorView<ItemSelectorCriterion>
		_contentDashboardFileExtensionItemSelectorView;

	@DeleteAfterTestRun
	private Group _group;

}