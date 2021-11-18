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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
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
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayOutputStream;

import java.util.Date;

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
public class GetContentDashboardItemsXlsMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0,
			"MyScope");
	}

	@Test
	public void testServeResource() throws Exception {
		String originalUserName = System.getProperty("user.name");

		System.setProperty("user.name", "test");

		try {
			FileEntry fileEntry = _addFileEntry();

			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			dlFileEntry.setModifiedDate(new Date(1634902652000L));

			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

			ByteArrayOutputStream byteArrayOutputStream =
				_serveResource(FileEntry.class.getName(), _group.getGroupId());

			Assert.assertArrayEquals(
				FileUtil.getBytes(
					getClass(), "dependencies/expected.xls"),
				byteArrayOutputStream.toByteArray());
		}
		finally {
			System.setProperty("user.name", originalUserName);
		}
	}

	private FileEntry _addFileEntry() throws Exception {
		Date date = new Date(150000);

		return DLAppLocalServiceUtil.addFileEntry(
			"Site", TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "fileName.pdf",
			"application/pdf", new byte[0], date, date,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private ByteArrayOutputStream _serveResource(String className, long groupId)
		throws Exception {

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest();

		mockLiferayResourceRequest.setParameter(
			"groupId", String.valueOf(groupId));
		mockLiferayResourceRequest.setParameter("className", className);

		ThemeDisplay themeDisplay = ContentDashboardTestUtil.getThemeDisplay(
			_group);

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		PortletImpl portletImpl = new PortletImpl();

		PortletAppImpl portletAppImpl = new PortletAppImpl("contextName");

		portletAppImpl.setSpecMajorVersion(1);

		portletImpl.setPortletApp(portletAppImpl);

		mockLiferayResourceRequest.setPortlet(portletImpl);

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

			_mvcResourceCommand.serveResource(
				mockLiferayResourceRequest, mockLiferayResourceResponse);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		return (ByteArrayOutputStream)
			mockLiferayResourceResponse.getPortletOutputStream();
	}

	@Inject(
		filter = "mvc.command.name=/content_dashboard/get_content_dashboard_items_xls"
	)
	private MVCResourceCommand _mvcResourceCommand;

	@DeleteAfterTestRun
	private Group _group;

}