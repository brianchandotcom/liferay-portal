/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.dashboard.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.dashboard.web.test.util.ContentDashboardTestUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testServeResource() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Date createDate = new Date();

		serviceContext.setCreateDate(createDate);

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			"Site", TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "fileName.pdf",
			"application/pdf", new byte[0], createDate, createDate,
			serviceContext);

		List<String> expectedWorkbookHeaders = ListUtil.fromArray(
			"ID", "Title", "Author", "Type", "Subtype", "Site or Asset Library",
			"Status", "Categories", "Tags", "Modified Date", "Review Date",
			"Description", "Extension", "File Name", "Size", "Display Date",
			"Creation Date", "Languages Translated Into");

		List<String> expectedWorkbookValues = ListUtil.fromArray(
			String.valueOf(fileEntry.getFileEntryId()), "fileName.pdf",
			"Test Test", "Document", "Basic Document (Vectorial)",
			_group.getName(serviceContext.getLocale()), "Approved", "", "",
			_toString(fileEntry.getModifiedDate()), _toString(createDate), "",
			"pdf", "fileName.pdf", "0 B", "", _toString(createDate), "");

		ByteArrayOutputStream byteArrayOutputStream = _serveResource(
			FileEntry.class.getName(), _group.getGroupId());

		_assertWorkbook(
			expectedWorkbookHeaders, expectedWorkbookValues,
			new HSSFWorkbook(
				new ByteArrayInputStream(byteArrayOutputStream.toByteArray())));
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

		MockLiferayResourceRequest mockLiferayResourceRequest =
			new MockLiferayResourceRequest(mockHttpServletRequest);

		mockLiferayResourceRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);
		mockLiferayResourceRequest.setParameter(
			"groupId", String.valueOf(groupId));
		mockLiferayResourceRequest.setParameter("className", className);

		_mvcResourceCommand.serveResource(
			mockLiferayResourceRequest, mockLiferayResourceResponse);

		return (ByteArrayOutputStream)
			mockLiferayResourceResponse.getPortletOutputStream();
	}

	private String _toString(Date date) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

		return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "mvc.command.name=/content_dashboard/get_content_dashboard_items_xls"
	)
	private MVCResourceCommand _mvcResourceCommand;

}