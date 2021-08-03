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

package com.liferay.content.dashboard.web.internal.portlet.action.test;

/**
 * @author Yurena Cabrera
 */
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.dashboard.web.test.util.ContentDashboardTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Yurena Cabrera
 */
@RunWith(Arquillian.class)
public class GetContentDashboardItemInfoMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);
	}

	@Test
	public void testServeResource() throws Exception {
		FileEntry fileEntry = _addFileEntry();

		JSONObject jsonObject = _serveResource(
			FileEntry.class.getName(), fileEntry.getFileEntryId(),
			_group.getGroupId());

		Assert.assertEquals(
			FileEntry.class.getName(), jsonObject.getString("className"));
		Assert.assertEquals(
			String.valueOf(fileEntry.getFileEntryId()),
			jsonObject.getString("classPK"));
		Assert.assertEquals("en-US", jsonObject.getString("languageTag"));

		JSONObject specificFieldsJSONObject = jsonObject.getJSONObject(
			"specificFields");

		Assert.assertNotNull(specificFieldsJSONObject.getString("downloadURL"));
		Assert.assertEquals(
			"pdf", specificFieldsJSONObject.getString("extension"));
		Assert.assertEquals(
			"FileName.pdf", specificFieldsJSONObject.getString("fileName"));
		Assert.assertNotNull(
			specificFieldsJSONObject.getString("previewImageURL"));
		Assert.assertNotNull(specificFieldsJSONObject.getString("previewURL"));
		Assert.assertNotNull(specificFieldsJSONObject.getString("viewURL"));

		Assert.assertEquals("Basic Document", jsonObject.getString("subType"));
		Assert.assertEquals("FileName.pdf", jsonObject.getString("title"));

		JSONArray versionsJSONArray = jsonObject.getJSONArray("versions");

		Assert.assertEquals(1, versionsJSONArray.length());

		JSONObject versionJSONObject = versionsJSONArray.getJSONObject(0);

		Assert.assertEquals(
			"Approved", versionJSONObject.getString("statusLabel"));
		Assert.assertEquals(
			"success", versionJSONObject.getString("statusStyle"));
		Assert.assertEquals("1.0", versionJSONObject.getString("version"));
	}

	private FileEntry _addFileEntry() throws Exception {
		return DLAppLocalServiceUtil.addFileEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"FileName.pdf", RandomTestUtil.randomString(), new byte[0], null,
			null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private JSONObject _serveResource(
			String className, long classPK, long groupId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setParameter(
			"groupId", String.valueOf(groupId));
		mockLiferayResourceRequest.setParameter("className", className);
		mockLiferayResourceRequest.setParameter(
			"classPK", String.valueOf(classPK));

		ThemeDisplay themeDisplay = ContentDashboardTestUtil.getThemeDisplay(
			_group);

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		serviceContext.setRequest(mockHttpServletRequest);

		try {
			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			_getContentDashboardItemInfoMVCResourceCommand.serveResource(
				mockLiferayResourceRequest, mockLiferayResourceResponse);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		ByteArrayOutputStream byteArrayOutputStream =
			(ByteArrayOutputStream)
				mockLiferayResourceResponse.getPortletOutputStream();

		return JSONFactoryUtil.createJSONObject(
			new String(byteArrayOutputStream.toByteArray()));
	}

	@Inject(
		filter = "mvc.command.name=/content_dashboard/get_content_dashboard_item_info"
	)
	private MVCResourceCommand _getContentDashboardItemInfoMVCResourceCommand;

	@DeleteAfterTestRun
	private Group _group;

}