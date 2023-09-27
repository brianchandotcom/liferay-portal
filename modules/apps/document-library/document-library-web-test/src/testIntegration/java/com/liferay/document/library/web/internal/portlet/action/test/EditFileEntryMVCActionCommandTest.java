/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletSession;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class EditFileEntryMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddMultipleFileEntries() throws PortalException {
		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			"com.liferay.document.library.web.internal.portlet.action." +
				"EditFileEntryMVCActionCommand",
			TempFileEntryUtil.getTempFileName("image.jpg"), _getInputStream(),
			ContentTypes.IMAGE_JPEG);

		ReflectionTestUtil.invoke(
			_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
			new Class<?>[] {
				PortletConfig.class, ActionRequest.class, String.class,
				List.class, List.class, Date.class, Date.class,
				ServiceContext.class
			},
			_getLiferayPortletConfig(),
			_getMockLiferayPortletActionRequest(
				_getParameters(tempFileEntry, Constants.ADD_MULTIPLE)),
			tempFileEntry.getFileName(), new ArrayList<>(), new ArrayList<>(),
			null, null, ServiceContextTestUtil.getServiceContext());

		FileEntry fileName = _dlAppLocalService.getFileEntryByFileName(
			_group.getGroupId(), tempFileEntry.getFolderId(), "image.jpg");

		Assert.assertEquals("image.jpg", fileName.getFileName());
		Assert.assertEquals("image", fileName.getTitle());
	}

	@Test
	public void testAddMultipleFileEntriesSeveralFiles()
		throws PortalException {

		String tempFolderName =
			"com.liferay.document.library.web.internal.portlet.action." +
				"EditFileEntryMVCActionCommand";

		List<String> selectedFileNames = new ArrayList<>();

		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(), tempFolderName,
			TempFileEntryUtil.getTempFileName("image.jpg"), _getInputStream(),
			ContentTypes.IMAGE_JPEG);

		selectedFileNames.add(tempFileEntry.getFileName());

		tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(), tempFolderName,
			TempFileEntryUtil.getTempFileName("test.jpg"), _getInputStream(),
			ContentTypes.IMAGE_JPEG);

		selectedFileNames.add(tempFileEntry.getFileName());

		Map<String, String[]> parameters = _getParameters(
			tempFileEntry, Constants.ADD_MULTIPLE);

		for (String selectedFileName : selectedFileNames) {
			ReflectionTestUtil.invoke(
				_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
				new Class<?>[] {
					PortletConfig.class, ActionRequest.class, String.class,
					List.class, List.class, Date.class, Date.class,
					ServiceContext.class
				},
				_getLiferayPortletConfig(),
				_getMockLiferayPortletActionRequest(parameters),
				selectedFileName, new ArrayList<>(), new ArrayList<>(), null,
				null, ServiceContextTestUtil.getServiceContext());
		}

		long folderId = tempFileEntry.getFolderId();

		FileEntry fileEntry = _dlAppLocalService.getFileEntryByFileName(
			_group.getGroupId(), folderId, "test.jpg");

		Assert.assertEquals("test.jpg", fileEntry.getFileName());
		Assert.assertEquals("test", fileEntry.getTitle());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntryByFileName(
			_group.getGroupId(), folderId, "image.jpg");

		Assert.assertEquals("image.jpg", actualFileEntry.getFileName());
		Assert.assertEquals("image", actualFileEntry.getTitle());
	}

	@Test
	public void testAddMultipleFileEntriesSeveralFilesSameTitleDifferentExtension()
		throws PortalException {

		String tempFolderName =
			"com.liferay.document.library.web.internal.portlet.action." +
				"EditFileEntryMVCActionCommand";

		List<String> selectedFileNames = new ArrayList<>();

		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(), tempFolderName,
			TempFileEntryUtil.getTempFileName("test.jpg"), _getInputStream(),
			ContentTypes.IMAGE_JPEG);

		selectedFileNames.add(tempFileEntry.getFileName());

		tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(), tempFolderName,
			TempFileEntryUtil.getTempFileName("test.gif"), _getInputStream(),
			ContentTypes.IMAGE_GIF);

		selectedFileNames.add(tempFileEntry.getFileName());

		Map<String, String[]> parameters = _getParameters(
			tempFileEntry, Constants.ADD_MULTIPLE);

		for (String selectedFileName : selectedFileNames) {
			ReflectionTestUtil.invoke(
				_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
				new Class<?>[] {
					PortletConfig.class, ActionRequest.class, String.class,
					List.class, List.class, Date.class, Date.class,
					ServiceContext.class
				},
				_getLiferayPortletConfig(),
				_getMockLiferayPortletActionRequest(parameters),
				selectedFileName, new ArrayList<>(), new ArrayList<>(), null,
				null, ServiceContextTestUtil.getServiceContext());
		}

		long folderId = tempFileEntry.getFolderId();

		FileEntry fileEntry = _dlAppLocalService.getFileEntryByFileName(
			_group.getGroupId(), folderId, "test.jpg");

		Assert.assertEquals("test.jpg", fileEntry.getFileName());
		Assert.assertEquals("test", fileEntry.getTitle());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntryByFileName(
			_group.getGroupId(), folderId, "test.gif");

		Assert.assertEquals("test.gif", actualFileEntry.getFileName());
		Assert.assertEquals("test (1)", actualFileEntry.getTitle());
	}

	@Test
	public void testAddMultipleFileEntriesSeveralFilesWithSameTitleAndExtension()
		throws PortalException {

		String tempFolderName =
			"com.liferay.document.library.web.internal.portlet.action." +
				"EditFileEntryMVCActionCommand";

		List<String> selectedFileNames = new ArrayList<>();

		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(), tempFolderName,
			TempFileEntryUtil.getTempFileName("image.jpg"), _getInputStream(),
			ContentTypes.IMAGE_JPEG);

		selectedFileNames.add(tempFileEntry.getFileName());

		tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(), tempFolderName,
			TempFileEntryUtil.getTempFileName("image.jpg"), _getInputStream(),
			ContentTypes.IMAGE_JPEG);

		selectedFileNames.add(tempFileEntry.getFileName());

		Map<String, String[]> parameters = _getParameters(
			tempFileEntry, Constants.ADD_MULTIPLE);

		for (String selectedFileName : selectedFileNames) {
			ReflectionTestUtil.invoke(
				_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
				new Class<?>[] {
					PortletConfig.class, ActionRequest.class, String.class,
					List.class, List.class, Date.class, Date.class,
					ServiceContext.class
				},
				_getLiferayPortletConfig(),
				_getMockLiferayPortletActionRequest(parameters),
				selectedFileName, new ArrayList<>(), new ArrayList<>(), null,
				null, ServiceContextTestUtil.getServiceContext());
		}

		long folderId = tempFileEntry.getFolderId();

		FileEntry fileEntry = _dlAppLocalService.getFileEntryByFileName(
			_group.getGroupId(), folderId, "image.jpg");

		Assert.assertEquals("image.jpg", fileEntry.getFileName());
		Assert.assertEquals("image", fileEntry.getTitle());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntryByFileName(
			_group.getGroupId(), folderId, "image (1).jpg");

		Assert.assertEquals("image (1).jpg", actualFileEntry.getFileName());
		Assert.assertEquals("image (1)", actualFileEntry.getTitle());
	}

	@Test
	public void testCheckIn() throws PortalException, PortletException {
		FileEntry initialFileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, null, null,
			null, ServiceContextTestUtil.getServiceContext());

		_dlAppService.checkOutFileEntry(
			initialFileEntry.getFileEntryId(),
			ServiceContextTestUtil.getServiceContext());

		_editFileEntryMVCActionCommand.processAction(
			_getMockLiferayPortletActionRequest(
				HashMapBuilder.putAll(
					_getParameters(initialFileEntry, Constants.CHECKIN)
				).put(
					"changeLog", new String[] {"New Version"}
				).put(
					"rowIdsFileEntry",
					new String[] {
						String.valueOf(initialFileEntry.getFileEntryId())
					}
				).put(
					"versionIncrease",
					new String[] {String.valueOf(DLVersionNumberIncrease.MAJOR)}
				).build()),
			new MockLiferayPortletActionResponse());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntry(
			initialFileEntry.getFileEntryId());

		FileVersion fileVersion = actualFileEntry.getFileVersion();

		Assert.assertEquals("New Version", fileVersion.getChangeLog());
	}

	@Test
	public void testCheckOut() throws PortalException, PortletException {
		FileEntry initialFileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, null, null,
			null, ServiceContextTestUtil.getServiceContext());

		_editFileEntryMVCActionCommand.processAction(
			_getMockLiferayPortletActionRequest(
				HashMapBuilder.putAll(
					_getParameters(initialFileEntry, Constants.CHECKOUT)
				).put(
					"rowIdsFileEntry",
					new String[] {
						String.valueOf(initialFileEntry.getFileEntryId())
					}
				).build()),
			new MockLiferayPortletActionResponse());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntry(
			initialFileEntry.getFileEntryId());

		Assert.assertTrue(actualFileEntry.isCheckedOut());
	}

	private InputStream _getInputStream() {
		return new ByteArrayInputStream("test".getBytes());
	}

	private LiferayPortletConfig _getLiferayPortletConfig() {
		Portlet portlet = _portletLocalService.getPortletById(
			DLPortletKeys.DOCUMENT_LIBRARY);

		return (LiferayPortletConfig)PortletConfigFactoryUtil.create(
			portlet, null);
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			Map<String, String[]> parameters)
		throws PortalException {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			mockLiferayPortletActionRequest.setParameter(
				entry.getKey(), entry.getValue());
		}

		mockLiferayPortletActionRequest.setSession(new MockPortletSession());
		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockLiferayPortletActionRequest;
	}

	private Map<String, String[]> _getParameters(
		FileEntry tempFileEntry, String cmd) {

		return HashMapBuilder.put(
			Constants.CMD, new String[] {cmd}
		).put(
			"folderId",
			new String[] {String.valueOf(tempFileEntry.getFolderId())}
		).put(
			"repositoryId",
			new String[] {String.valueOf(tempFileEntry.getRepositoryId())}
		).build();
	}

	private ThemeDisplay _getThemeDisplay() throws PortalException {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRequest(new MockHttpServletRequest());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setServerName("localhost");
		themeDisplay.setServerPort(8080);
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLAppService _dlAppService;

	@Inject(filter = "mvc.command.name=/document_library/edit_file_entry")
	private MVCActionCommand _editFileEntryMVCActionCommand;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletLocalService _portletLocalService;

}