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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.dashboard.web.test.util.ContentDashboardTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.PortletApp;
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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.impl.PortletAppImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

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
			"Test Site");
	}

	@Test
	public void testServeResource() throws Exception {
		String originalUserName = System.getProperty("user.name");

		System.setProperty("user.name", "test");

		try {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			serviceContext.setCreateDate(new Date(1630509375000L));

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			Date date = new Date(150000);

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				"Site", TestPropsValues.getUserId(), _group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "fileName.pdf",
				"application/pdf", new byte[0], date, date, serviceContext);

			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			dlFileEntry.setModifiedDate(new Date(1634902652000L));

			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

			ByteArrayOutputStream byteArrayOutputStream = _serveResource(
				FileEntry.class.getName(), _group.getGroupId());

			List<String> expectedWorkbookHeaders = new ArrayList<>();

			Collections.addAll(
				expectedWorkbookHeaders, "ID", "Title", "Author", "Type",
				"Subtype", "Site or Asset Library", "Status", "Categories",
				"Tags", "Modified Date", "Review Date", "Description",
				"Extension", "File Name", "Size", "Display Date",
				"Creation Date", "Languages Translated Into");

			List<String> expectedWorkbookValues = new ArrayList<>();

			Collections.addAll(
				expectedWorkbookValues,
				String.valueOf(fileEntry.getFileEntryId()), "fileName.pdf",
				"Test Test", "Document", "Basic Document (Vectorial)",
				"Test Site", "Approved", "", "", "2021-10-22T11:37:32", "-", "",
				"pdf", "fileName.pdf", "0 B", "", "2021-09-01T15:16:15", "");

			_assertWorkbook(
				expectedWorkbookHeaders, expectedWorkbookValues,
				new HSSFWorkbook(
					new ByteArrayInputStream(
						byteArrayOutputStream.toByteArray())));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			System.setProperty("user.name", originalUserName);
		}
	}

	private void _assertWorkbook(
		List<String> expectedWorkbookHeaders,
		List<String> expectedWorkbookValues, Workbook actualWorkbook) {

		Assert.assertEquals(1, actualWorkbook.getNumberOfSheets());

		Sheet actualWorkbookSheet = actualWorkbook.getSheetAt(0);

		Assert.assertEquals(1, actualWorkbookSheet.getLastRowNum());
		_assertWorkbookRow(
			expectedWorkbookHeaders, actualWorkbookSheet.getRow(0));
		_assertWorkbookRow(
			expectedWorkbookValues, actualWorkbookSheet.getRow(1));
	}

	private void _assertWorkbookRow(
		List<String> expectedRowValues, Row workbookRow) {

		for (short i = 0; i < workbookRow.getLastCellNum(); i++) {
			String actualWorkbookCellValue = StringPool.BLANK;
			Cell actualWorkbookCell = workbookRow.getCell(i);

			if (actualWorkbookCell != null) {
				actualWorkbookCellValue =
					actualWorkbookCell.getStringCellValue();
			}

			Assert.assertEquals(
				expectedRowValues.get(i), actualWorkbookCellValue);
		}
	}

	private ByteArrayOutputStream _serveResource(String className, long groupId)
		throws Exception {

		MockLiferayResourceResponse mockLiferayResourceResponse =
			new MockLiferayResourceResponse();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = ContentDashboardTestUtil.getThemeDisplay(
			_group);

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setParameter("scopeId", String.valueOf(groupId));

		serviceContext.setRequest(mockHttpServletRequest);

		try {
			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			MockLiferayResourceRequest mockLiferayResourceRequest =
				new MockLiferayResourceRequest(mockHttpServletRequest);

			mockLiferayResourceRequest.setAttribute(
				WebKeys.THEME_DISPLAY, themeDisplay);
			mockLiferayResourceRequest.setParameter(
				"groupId", String.valueOf(groupId));
			mockLiferayResourceRequest.setParameter("className", className);

			Portlet portlet = new PortletImpl();

			PortletApp portletApp = new PortletAppImpl("contextName");

			portletApp.setSpecMajorVersion(1);

			portlet.setPortletApp(portletApp);

			mockLiferayResourceRequest.setPortlet(portlet);

			_mvcResourceCommand.serveResource(
				mockLiferayResourceRequest, mockLiferayResourceResponse);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		return (ByteArrayOutputStream)
			mockLiferayResourceResponse.getPortletOutputStream();
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/content_dashboard/get_content_dashboard_items_xls"
	)
	private MVCResourceCommand _mvcResourceCommand;

}