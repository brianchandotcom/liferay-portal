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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.util.test.DLTestUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Michael C. Han
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLFileEntryLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetNoAssetEntries() throws Exception {
		DLFolder dlFolder = DLTestUtil.addDLFolder(_group.getGroupId());

		byte[] bytes = RandomTestUtil.randomBytes();

		InputStream is = new ByteArrayInputStream(bytes);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(dlFolder.getGroupId());

		DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		is = new ByteArrayInputStream(bytes);

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, is, bytes.length,
			serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		Assert.assertNotNull(assetEntry);

		AssetEntryLocalServiceUtil.deleteAssetEntry(assetEntry);

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getNoAssetFileEntries();

		Assert.assertEquals(1, dlFileEntries.size());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		Assert.assertEquals(
			fileEntry.getFileEntryId(), dlFileEntry.getFileEntryId());
	}

	@DeleteAfterTestRun
	private Group _group;

}