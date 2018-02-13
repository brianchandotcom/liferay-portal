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

package com.liferay.bookmarks.uad.exporter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.uad.constants.BookmarksUADConstants;
import com.liferay.bookmarks.uad.test.BaseBookmarksEntryUADEntityTestCase;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exporter.UADEntityExporter;

import java.io.InputStream;
import java.io.StringWriter;

import java.util.List;

import org.apache.commons.io.IOUtils;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Noah Sherrill
 */
@RunWith(Arquillian.class)
public class BookmarksEntryUADEntityExporterTest
	extends BaseBookmarksEntryUADEntityTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser();
	}

	@Test
	public void testExport() throws Exception {
		BookmarksEntry bookmarksEntry = addBookmarksEntry(_user.getUserId());

		List<UADEntity> uadEntities = _uadEntityAggregator.getUADEntities(
			_user.getUserId());

		UADEntity uadEntity = uadEntities.get(0);

		_uadEntityExporter.export(uadEntity);

		FileEntry fileEntry = _getFileEntry(
			bookmarksEntry.getCompanyId(), uadEntity.getUADEntityId());

		_verifyFileEntry(fileEntry, bookmarksEntry);

		PortletFileRepositoryUtil.deletePortletFileEntry(
			fileEntry.getFileEntryId());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testExportAll() throws Exception {
		BookmarksEntry bookmarksEntry = addBookmarksEntry(
			TestPropsValues.getUserId());
		BookmarksEntry bookmarksEntryExported = addBookmarksEntry(
			_user.getUserId());

		_uadEntityExporter.exportAll(_user.getUserId());

		FileEntry fileEntry = _getFileEntry(
			bookmarksEntry.getCompanyId(),
			String.valueOf(bookmarksEntryExported.getEntryId()));

		_verifyFileEntry(fileEntry, bookmarksEntryExported);

		PortletFileRepositoryUtil.deletePortletFileEntry(
			fileEntry.getFileEntryId());

		_getFileEntry(
			bookmarksEntry.getCompanyId(),
			String.valueOf(bookmarksEntry.getEntryId()));
	}

	@Test
	public void testExportAllNoBookmarksEntries() throws Exception {
		_uadEntityExporter.exportAll(_user.getUserId());
	}

	private FileEntry _getFileEntry(long companyId, String uadEntityId)
		throws Exception {

		Group guestGroup = _groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		Repository repository = PortletFileRepositoryUtil.getPortletRepository(
			guestGroup.getGroupId(), BookmarksPortletKeys.BOOKMARKS);

		Folder folder = PortletFileRepositoryUtil.getPortletFolder(
			repository.getRepositoryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "UADExport");

		return PortletFileRepositoryUtil.getPortletFileEntry(
			guestGroup.getGroupId(), folder.getFolderId(),
			uadEntityId + ".json");
	}

	private void _verifyFileEntry(
			FileEntry fileEntry, BookmarksEntry bookmarksEntry)
		throws Exception {

		InputStream is = _dlFileEntryLocalService.getFileAsStream(
			fileEntry.getFileEntryId(), fileEntry.getVersion());
		StringWriter stringWriter = new StringWriter();

		IOUtils.copy(is, stringWriter, StringPool.UTF8);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			stringWriter.toString());

		Assert.assertEquals(
			bookmarksEntry.getEntryId(), jsonObject.getInt("entryId"));
	}

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY
	)
	private UADEntityAggregator _uadEntityAggregator;

	@Inject(
		filter = "model.class.name=" + BookmarksUADConstants.CLASS_NAME_BOOKMARKS_ENTRY
	)
	private UADEntityExporter _uadEntityExporter;

	@DeleteAfterTestRun
	private User _user;

}