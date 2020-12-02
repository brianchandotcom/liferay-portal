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
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.web.internal.util.MockActionRequest;
import com.liferay.document.library.web.internal.util.MockActionResponse;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class EditEntryMVCActionCommandTest {

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
	public void testCheckIn() throws PortalException, PortletException {
		FileEntry initialFileEntry = _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, null,
			ServiceContextTestUtil.getServiceContext());

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

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_mvcActionCommand.processAction(
			new MockActionRequest(company, _group, parameters),
			new MockActionResponse());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntry(
			initialFileEntry.getFileEntryId());

		FileVersion fileVersion = actualFileEntry.getFileVersion();

		Assert.assertEquals("New Version", fileVersion.getChangeLog());
	}

	@Test
	public void testCheckInAll() throws PortalException, PortletException {
		FileEntry initialFileEntry = _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, null,
			ServiceContextTestUtil.getServiceContext());

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
				"selectAll", new String[] {String.valueOf(Boolean.TRUE)}),
			new AbstractMap.SimpleEntry<>(
				"versionIncrease",
				new String[] {String.valueOf(DLVersionNumberIncrease.MAJOR)})
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_mvcActionCommand.processAction(
			new MockActionRequest(company, _group, parameters),
			new MockActionResponse());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntry(
			initialFileEntry.getFileEntryId());

		FileVersion fileVersion = actualFileEntry.getFileVersion();

		Assert.assertEquals("New Version", fileVersion.getChangeLog());
	}

	@Test
	public void testCheckOut() throws PortalException, PortletException {
		FileEntry initialFileEntry = _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, null,
			ServiceContextTestUtil.getServiceContext());

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

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_mvcActionCommand.processAction(
			new MockActionRequest(company, _group, parameters),
			new MockActionResponse());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntry(
			initialFileEntry.getFileEntryId());

		Assert.assertTrue(actualFileEntry.isCheckedOut());
	}

	@Test
	public void testCheckOutAll() throws PortalException, PortletException {
		FileEntry initialFileEntry = _dlAppLocalService.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, null,
			ServiceContextTestUtil.getServiceContext());

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
				"selectAll", new String[] {String.valueOf(Boolean.TRUE)})
		).collect(
			Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)
		);

		Company company = _companyLocalService.getCompany(
			TestPropsValues.getCompanyId());

		_mvcActionCommand.processAction(
			new MockActionRequest(company, _group, parameters),
			new MockActionResponse());

		FileEntry actualFileEntry = _dlAppLocalService.getFileEntry(
			initialFileEntry.getFileEntryId());

		Assert.assertTrue(actualFileEntry.isCheckedOut());
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLAppService _dlAppService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.document.library.web.internal.portlet.action.EditEntryMVCActionCommand"
	)
	private MVCActionCommand _mvcActionCommand;

}