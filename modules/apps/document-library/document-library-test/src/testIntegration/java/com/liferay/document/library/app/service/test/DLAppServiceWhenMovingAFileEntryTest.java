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
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.sync.constants.DLSyncConstants;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenMovingAFileEntryTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldFireSyncEvent() throws Exception {
		AtomicInteger moveCounter =
			DLAppServiceTestUtil.registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_MOVE);

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		DLAppServiceUtil.moveFileEntry(
			fileEntry.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertEquals(1, moveCounter.get());
	}

	@Test
	public void testShouldHaveSameFileExtension() throws Exception {
		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			DLAppServiceTestUtil.STRIPPED_FILE_NAME, null, null, null);

		FileEntry copiedFileEntry = DLAppServiceUtil.moveFileEntry(
			fileEntry.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceContextTestUtil.getServiceContext(targetGroup.getGroupId()));

		Assert.assertEquals(
			fileEntry.getExtension(), copiedFileEntry.getExtension());
	}

}