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

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenSearchingFileEntriesTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldFindFileEntryByAssetTagName() throws Exception {
		String fileName = RandomTestUtil.randomString();

		String[] assetTagNames = {"hello", "world"};

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), fileName, fileName,
			assetTagNames);

		DLAppServiceTestUtil.search(fileEntry, "hello", true);
		DLAppServiceTestUtil.search(fileEntry, "world", true);
		DLAppServiceTestUtil.search(fileEntry, "liferay", false);
	}

	@Ignore
	@Test
	public void testShouldFindFileEntryByAssetTagNameAfterUpdate()
		throws Exception {

		String fileName = RandomTestUtil.randomString();
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;
		byte[] bytes = CONTENT.getBytes();

		String[] assetTagNames = {"hello", "world"};

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), fileName, fileName,
			assetTagNames);

		assetTagNames = new String[] {"hello", "world", "liferay"};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetTagNames(assetTagNames);

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, description, changeLog, DLVersionNumberIncrease.MINOR,
			bytes, serviceContext);

		DLAppServiceTestUtil.search(fileEntry, "hello", true);
		DLAppServiceTestUtil.search(fileEntry, "world", true);
		DLAppServiceTestUtil.search(fileEntry, "liferay", true);
	}

	@Ignore
	@Test
	public void testShouldFindFileEntryInRootFolder() throws Exception {
		DLAppServiceTestUtil.searchFile(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Ignore
	@Test
	public void testShouldFindFileEntryInSubfolder() throws Exception {
		DLAppServiceTestUtil.searchFile(
			group.getGroupId(), parentFolder.getFolderId());
	}

}