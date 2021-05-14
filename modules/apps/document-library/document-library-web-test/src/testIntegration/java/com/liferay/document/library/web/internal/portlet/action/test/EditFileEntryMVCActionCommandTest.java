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
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletSession;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		long folderId = tempFileEntry.getFolderId();

		Map<String, String[]> parameters = Stream.of(
			new AbstractMap.SimpleEntry<>(
				Constants.CMD, new String[] {Constants.ADD_MULTIPLE}),
			new AbstractMap.SimpleEntry<>(
				"folderId", new String[] {String.valueOf(folderId)}),
			new AbstractMap.SimpleEntry<>(
				"repositoryId",
				new String[] {String.valueOf(tempFileEntry.getRepositoryId())})
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);

		ReflectionTestUtil.invoke(
			_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
			new Class<?>[] {
				PortletConfig.class, ActionRequest.class, String.class,
				List.class, List.class, ServiceContext.class
			},
			_getLiferayPortletConfig(),
			_getMockLiferayPortletActionRequest(parameters),
			tempFileEntry.getFileName(), new ArrayList<>(), new ArrayList<>(),
			ServiceContextTestUtil.getServiceContext());

		FileEntry fileName = _dlAppLocalService.getFileEntryByFileName(
			_group.getGroupId(), folderId, "image.jpg");

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

		long folderId = tempFileEntry.getFolderId();

		Map<String, String[]> parameters = Stream.of(
			new AbstractMap.SimpleEntry<>(
				Constants.CMD, new String[] {Constants.ADD_MULTIPLE}),
			new AbstractMap.SimpleEntry<>(
				"folderId", new String[] {String.valueOf(folderId)}),
			new AbstractMap.SimpleEntry<>(
				"repositoryId",
				new String[] {String.valueOf(tempFileEntry.getRepositoryId())})
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);

		for (String selectedFileName : selectedFileNames) {
			ReflectionTestUtil.invoke(
				_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
				new Class<?>[] {
					PortletConfig.class, ActionRequest.class, String.class,
					List.class, List.class, ServiceContext.class
				},
				_getLiferayPortletConfig(),
				_getMockLiferayPortletActionRequest(parameters),
				selectedFileName, new ArrayList<>(), new ArrayList<>(),
				ServiceContextTestUtil.getServiceContext());
		}

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
	public void testAddMultipleFileEntriesSeveralFilesWithSameTitle()
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

		long folderId = tempFileEntry.getFolderId();

		Map<String, String[]> parameters = Stream.of(
			new AbstractMap.SimpleEntry<>(
				Constants.CMD, new String[] {Constants.ADD_MULTIPLE}),
			new AbstractMap.SimpleEntry<>(
				"folderId", new String[] {String.valueOf(folderId)}),
			new AbstractMap.SimpleEntry<>(
				"repositoryId",
				new String[] {String.valueOf(tempFileEntry.getRepositoryId())})
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);

		for (String selectedFileName : selectedFileNames) {
			ReflectionTestUtil.invoke(
				_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
				new Class<?>[] {
					PortletConfig.class, ActionRequest.class, String.class,
					List.class, List.class, ServiceContext.class
				},
				_getLiferayPortletConfig(),
				_getMockLiferayPortletActionRequest(parameters),
				selectedFileName, new ArrayList<>(), new ArrayList<>(),
				ServiceContextTestUtil.getServiceContext());
		}

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

		Map<String, String[]> parameters = Stream.of(
			new AbstractMap.SimpleEntry<>(
				"changeLog", new String[] {"New Version"}),
			new AbstractMap.SimpleEntry<>(
				Constants.CMD, new String[] {Constants.CHECKIN}),
			new AbstractMap.SimpleEntry<>(
				"folderId",
				new String[] {String.valueOf(initialFileEntry.getFolderId())}),
			new AbstractMap.SimpleEntry<>(
				"repositoryId",
				new String[] {
					String.valueOf(initialFileEntry.getRepositoryId())
				}),
			new AbstractMap.SimpleEntry<>(
				"rowIdsFileEntry",
				new String[] {
					String.valueOf(initialFileEntry.getFileEntryId())
				}),
			new AbstractMap.SimpleEntry<>(
				"versionIncrease",
				new String[] {String.valueOf(DLVersionNumberIncrease.MAJOR)})
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);

		_editFileEntryMVCActionCommand.processAction(
			_getMockLiferayPortletActionRequest(parameters),
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

		Map<String, String[]> parameters = Stream.of(
			new AbstractMap.SimpleEntry<>(
				Constants.CMD, new String[] {Constants.CHECKOUT}),
			new AbstractMap.SimpleEntry<>(
				"folderId",
				new String[] {String.valueOf(initialFileEntry.getFolderId())}),
			new AbstractMap.SimpleEntry<>(
				"repositoryId",
				new String[] {
					String.valueOf(initialFileEntry.getRepositoryId())
				}),
			new AbstractMap.SimpleEntry<>(
				"rowIdsFileEntry",
				new String[] {
					String.valueOf(initialFileEntry.getFileEntryId())
				})
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);

		_editFileEntryMVCActionCommand.processAction(
			_getMockLiferayPortletActionRequest(parameters),
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