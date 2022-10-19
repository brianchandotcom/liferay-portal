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

package com.liferay.content.dashboard.document.library.internal.item.action.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.content.dashboard.item.action.ContentDashboardItemVersionAction;
import com.liferay.content.dashboard.item.action.provider.ContentDashboardItemVersionActionProvider;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.net.URLEncoder;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
public class RevertFileVersionContentDashboardItemVersionActionProviderTest {

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
	public void testGetContentDashboardItemActionOneVersion() throws Exception {
		FileEntry fileEntry = _getFileEntry(1, TestPropsValues.getUserId());

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		Assert.assertNull(
			_contentDashboardItemVersionActionProvider.
				getContentDashboardItemVersionAction(
					fileVersion,
					_getMockHttpServletRequest(TestPropsValues.getUser())));
	}

	@Test
	public void testGetContentDashboardItemActionTwoVersions()
		throws Exception {

		FileEntry fileEntry = _getFileEntry(2, TestPropsValues.getUserId());

		FileVersion fileFirstVersion = fileEntry.getFileVersion("1.0");

		ContentDashboardItemVersionAction contentDashboardItemVersionAction =
			_contentDashboardItemVersionActionProvider.
				getContentDashboardItemVersionAction(
					fileFirstVersion,
					_getMockHttpServletRequest(TestPropsValues.getUser()));

		Assert.assertNotNull(contentDashboardItemVersionAction);

		String url = contentDashboardItemVersionAction.getURL();

		Assert.assertNotNull(url);

		String actionName = URLEncoder.encode(
			"/document_library/edit_file_entry", "UTF-8");

		Assert.assertTrue(
			url.contains(ActionRequest.ACTION_NAME + "=" + actionName));

		Assert.assertTrue(url.contains(Constants.CMD + "=" + Constants.REVERT));

		Assert.assertTrue(
			url.contains("fileEntryId=" + fileEntry.getFileEntryId()));

		Assert.assertTrue(
			url.contains("version=" + fileFirstVersion.getVersion()));
	}

	@Test
	public void testGetIcon() throws Exception {
		FileEntry fileEntry = _getFileEntry(2, TestPropsValues.getUserId());

		FileVersion fileFirstVersion = fileEntry.getFileVersion("1.0");

		ContentDashboardItemVersionAction contentDashboardItemVersionAction =
			_contentDashboardItemVersionActionProvider.
				getContentDashboardItemVersionAction(
					fileFirstVersion,
					_getMockHttpServletRequest(TestPropsValues.getUser()));

		Assert.assertEquals(
			"revert", contentDashboardItemVersionAction.getIcon());
	}

	@Test
	public void testGetLabel() throws Exception {
		FileEntry fileEntry = _getFileEntry(2, TestPropsValues.getUserId());

		FileVersion fileFirstVersion = fileEntry.getFileVersion("1.0");

		ContentDashboardItemVersionAction contentDashboardItemVersionAction =
			_contentDashboardItemVersionActionProvider.
				getContentDashboardItemVersionAction(
					fileFirstVersion,
					_getMockHttpServletRequest(TestPropsValues.getUser()));

		Assert.assertEquals(
			"Revert",
			contentDashboardItemVersionAction.getLabel(
				LocaleUtil.getDefault()));
	}

	@Test
	public void testGetName() throws Exception {
		FileEntry fileEntry = _getFileEntry(2, TestPropsValues.getUserId());

		FileVersion fileFirstVersion = fileEntry.getFileVersion("1.0");

		ContentDashboardItemVersionAction contentDashboardItemVersionAction =
			_contentDashboardItemVersionActionProvider.
				getContentDashboardItemVersionAction(
					fileFirstVersion,
					_getMockHttpServletRequest(TestPropsValues.getUser()));

		Assert.assertEquals(
			"revert", contentDashboardItemVersionAction.getName());
	}

	@Test
	public void testIsShowFileVersionStatusExpired() throws Exception {
		FileEntry fileEntry = _getFileEntry(2, TestPropsValues.getUserId());

		FileVersion fileFirstVersion = fileEntry.getFileVersion("1.0");

		DLFileVersion dlFileVersion =
			_dlFileVersionLocalService.getDLFileVersion(
				fileFirstVersion.getFileVersionId());

		dlFileVersion.setStatus(WorkflowConstants.STATUS_EXPIRED);

		_dlFileVersionLocalService.updateDLFileVersion(dlFileVersion);

		fileFirstVersion = fileEntry.getFileVersion("1.0");

		Assert.assertFalse(
			_contentDashboardItemVersionActionProvider.isShow(
				fileFirstVersion,
				_getMockHttpServletRequest(TestPropsValues.getUser())));
	}

	@Test
	public void testIsShowFirstVersion() throws Exception {
		FileEntry fileEntry = _getFileEntry(2, TestPropsValues.getUserId());

		FileVersion fileFirstVersion = fileEntry.getFileVersion("1.0");

		Assert.assertTrue(
			_contentDashboardItemVersionActionProvider.isShow(
				fileFirstVersion,
				_getMockHttpServletRequest(TestPropsValues.getUser())));
	}

	@Test
	public void testIsShowLastVersion() throws Exception {
		FileEntry fileEntry = _getFileEntry(2, TestPropsValues.getUserId());

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		Assert.assertFalse(
			_contentDashboardItemVersionActionProvider.isShow(
				fileVersion,
				_getMockHttpServletRequest(TestPropsValues.getUser())));
	}

	@Test
	public void testIsShowWithoutPermissions() throws Exception {
		User user1 = UserTestUtil.addUser();

		FileEntry fileEntry = _getFileEntry(1, user1.getUserId());

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		User user2 = UserTestUtil.addUser();

		Assert.assertFalse(
			_contentDashboardItemVersionActionProvider.isShow(
				fileVersion, _getMockHttpServletRequest(user2)));
	}

	private FileEntry _getFileEntry(int numVersions, long userId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			RandomTestUtil.randomString(), userId, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + "." + ContentTypes.IMAGE_JPEG,
			MimeTypesUtil.getExtensionContentType(ContentTypes.IMAGE_JPEG),
			new byte[0], null, null, serviceContext);

		if (numVersions > 1) {
			for (int i = 1; i < numVersions; i++) {
				fileEntry = _dlAppLocalService.updateFileEntry(
					fileEntry.getUserId(), fileEntry.getFileEntryId(),
					fileEntry.getFileName(), fileEntry.getMimeType(),
					fileEntry.getTitle(), StringUtil.randomString(),
					fileEntry.getDescription(), RandomTestUtil.randomString(),
					DLVersionNumberIncrease.MINOR, fileEntry.getContentStream(),
					fileEntry.getSize(), fileEntry.getExpirationDate(),
					fileEntry.getReviewDate(), serviceContext);
			}
		}

		return fileEntry;
	}

	private MockHttpServletRequest _getMockHttpServletRequest(User user)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayResourceResponse());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY,
			_getThemeDisplay(mockHttpServletRequest, user));

		return mockHttpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(
			HttpServletRequest httpServletRequest, User user)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(user));
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(user);

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.content.dashboard.document.library.internal.item.action.provider.RevertFileVersionContentDashboardItemVersionActionProvider"
	)
	private ContentDashboardItemVersionActionProvider<FileVersion>
		_contentDashboardItemVersionActionProvider;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@DeleteAfterTestRun
	private Group _group;

}