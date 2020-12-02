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
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.web.internal.util.MockActionRequest;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.PortletConfigFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class EditEntryMVCActionCommandMultipleFilesTest {

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

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		ReflectionTestUtil.invoke(
			_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
			new Class<?>[] {
				PortletConfig.class, ActionRequest.class, String.class,
				List.class, List.class
			},
			_getLiferayPortletConfig(),
			new MockActionRequest(company, _group, parameters),
			tempFileEntry.getFileName(), new ArrayList<>(), new ArrayList<>());

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

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		for (String selectedFileName : selectedFileNames) {
			ReflectionTestUtil.invoke(
				_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
				new Class<?>[] {
					PortletConfig.class, ActionRequest.class, String.class,
					List.class, List.class
				},
				_getLiferayPortletConfig(),
				new MockActionRequest(company, _group, parameters),
				selectedFileName, new ArrayList<>(), new ArrayList<>());
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

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		for (String selectedFileName : selectedFileNames) {
			ReflectionTestUtil.invoke(
				_editFileEntryMVCActionCommand, "_addMultipleFileEntries",
				new Class<?>[] {
					PortletConfig.class, ActionRequest.class, String.class,
					List.class, List.class
				},
				_getLiferayPortletConfig(),
				new MockActionRequest(company, _group, parameters),
				selectedFileName, new ArrayList<>(), new ArrayList<>());
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

	private InputStream _getInputStream() {
		Class<?> clazz = EditEntryMVCActionCommandMultipleFilesTest.class;

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/library/web/document/dependencies/test.jpg");
	}

	private LiferayPortletConfig _getLiferayPortletConfig() {
		Portlet portlet = _portletLocalService.getPortletById(
			DLPortletKeys.DOCUMENT_LIBRARY);

		return (LiferayPortletConfig)PortletConfigFactoryUtil.create(
			portlet, null);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject(
		filter = "mvc.command.name=/document_library/upload_multiple_file_entries"
	)
	private MVCActionCommand _editFileEntryMVCActionCommand;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private PortletLocalService _portletLocalService;

}