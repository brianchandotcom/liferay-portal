/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2024-09
 */

package com.liferay.portal.webserver.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.webserver.WebServerServlet;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class WebServerServletTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user = UserLocalServiceUtil.getGuestUser(_group.getCompanyId());
	}

	@Test
	public void testGetStatus() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.login.web.internal.configuration." +
						"AuthLoginConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"promptEnabled", false
					).build())) {

			_testGetStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.login.web.internal.configuration." +
						"AuthLoginConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"promptEnabled", true
					).build())) {

			_testGetStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
		}
	}

	@Test
	public void testFaviconFileAccess() throws Exception {
		FileEntry faviconFileEntry = _createFileEntry("favicon.ico", "image/x-icon");

		_layoutSetLocalService.updateFaviconFileEntryId(
			_group.getGroupId(), false, faviconFileEntry.getFileEntryId());

		_removeAllDownloadPermissions(faviconFileEntry.getFileEntryId());

		int status = _testFileAccess(faviconFileEntry);

		Assert.assertEquals(HttpServletResponse.SC_OK, status);
	}

	@Test
	public void testNonFaviconFileAccessDenied() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.login.web.internal.configuration." +
						"AuthLoginConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"promptEnabled", false
					).build())) {

			FileEntry regularFileEntry = _createFileEntry("regular.txt", ContentTypes.TEXT_PLAIN);

			_removeAllDownloadPermissions(regularFileEntry.getFileEntryId());

			int status = _testFileAccess(regularFileEntry);

			Assert.assertEquals(HttpServletResponse.SC_NOT_FOUND, status);
		}
	}

	private FileEntry _createFileEntry(String fileName, String mimeType) throws Exception {
		ServiceContext serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, fileName,
			mimeType, TestDataConstants.TEST_BYTE_ARRAY, null, null, null,
			serviceContext);
	}

	private int _testFileAccess(FileEntry fileEntry) throws Exception {
		MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.USER, UserLocalServiceUtil.getGuestUser(_group.getCompanyId()));
		mockHttpServletRequest.setRequestURI(
			StringBundler.concat("/", _group.getGroupId(), "/", fileEntry.getUuid()));

		MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();

		_webServerServlet.service(mockHttpServletRequest, mockHttpServletResponse);

		return mockHttpServletResponse.getStatus();
	}

	private void _removeAllDownloadPermissions(long fileEntryId) throws Exception {
		_removeResourcePermission(fileEntryId, RoleConstants.GUEST, ActionKeys.DOWNLOAD);
	}

	private void _removeResourcePermission(
			long fileEntryId, String roleName, String actionId)
		throws Exception {

		Role guestRole = RoleLocalServiceUtil.getRole(
			_group.getCompanyId(), roleName);

		ResourcePermissionLocalServiceUtil.removeResourcePermission(
			_group.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(fileEntryId),
			guestRole.getRoleId(), actionId);
	}

	private void _testGetStatus(int status) throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.USER,
			UserLocalServiceUtil.getGuestUser(_group.getCompanyId()));

		Repository repository = _repositoryLocalService.addRepository(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			PortalUtil.getClassNameId(LiferayRepository.class.getName()),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), StringPool.BLANK,
			RandomTestUtil.randomString(), new UnicodeProperties(), true,
			ServiceContextTestUtil.getServiceContext());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = _dlAppLocalService.addFolder(
			null, TestPropsValues.getUserId(), repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), repository.getRepositoryId(),
			folder.getFolderId(), RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN, TestDataConstants.TEST_BYTE_ARRAY, null,
			null, null, serviceContext);

		_removeResourcePermission(
			fileEntry.getFileEntryId(), RoleConstants.GUEST,
			ActionKeys.DOWNLOAD);
		_removeResourcePermission(
			fileEntry.getFileEntryId(), RoleConstants.OWNER,
			ActionKeys.DOWNLOAD);
		_removeResourcePermission(
			fileEntry.getFileEntryId(), RoleConstants.SITE_MEMBER,
			ActionKeys.DOWNLOAD);

		mockHttpServletRequest.setRequestURI(
			StringBundler.concat(
				"/", repository.getRepositoryId(), "/", fileEntry.getUuid()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_webServerServlet.service(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(status, mockHttpServletResponse.getStatus());
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLURLHelper _dlURLHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject
	private RepositoryLocalService _repositoryLocalService;

	private User _user;
	private final WebServerServlet _webServerServlet = new WebServerServlet();

}