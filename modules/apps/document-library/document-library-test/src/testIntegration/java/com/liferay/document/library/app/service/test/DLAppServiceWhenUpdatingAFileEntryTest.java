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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.sync.constants.DLSyncConstants;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.document.library.workflow.WorkflowHandlerInvocationCounter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;

import java.util.Arrays;
import java.util.Map;
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
public class DLAppServiceWhenUpdatingAFileEntryTest extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAssetEntryShouldBeAddedWhenDraft() throws Exception {
		String fileName = RandomTestUtil.randomString();
		byte[] bytes = CONTENT.getBytes();
		String[] assetTagNames = {"hello"};

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), fileName, fileName, null, null,
			assetTagNames);

		assetTagNames = new String[] {"hello", "world"};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, bytes, null, null, serviceContext);

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		AssetEntry latestAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId());

		Assert.assertNotNull(latestAssetEntry);

		AssertUtils.assertEqualsSorted(
			assetTagNames, latestAssetEntry.getTagNames());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		Assert.assertNotNull(assetEntry);

		assetTagNames = assetEntry.getTagNames();

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 1, assetTagNames.length);
	}

	@Test
	public void testAssetEntryShouldBeAddedWithNullBytesWhenDraft()
		throws Exception {

		String fileName = RandomTestUtil.randomString();
		String[] assetTagNames = {"hello"};

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), fileName, fileName, null, null,
			assetTagNames);

		assetTagNames = new String[] {"hello", "world"};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, null, 0, null, null, serviceContext);

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		AssetEntry latestAssetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId());

		Assert.assertNotNull(latestAssetEntry);

		AssertUtils.assertEqualsSorted(
			assetTagNames, latestAssetEntry.getTagNames());

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		Assert.assertNotNull(assetEntry);

		assetTagNames = assetEntry.getTagNames();

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 1, assetTagNames.length);
	}

	@Test
	public void testAssetTagsShouldBeOrdered() throws Exception {
		String fileName = RandomTestUtil.randomString();
		byte[] bytes = CONTENT.getBytes();

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), fileName);

		String[] assetTagNames = {"hello", "world", "liferay"};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetTagNames(assetTagNames);

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, bytes, null, null, serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		AssertUtils.assertEqualsSorted(assetTagNames, assetEntry.getTagNames());
	}

	@Test
	public void testShouldCallWorkflowHandler() throws Exception {
		try (WorkflowHandlerInvocationCounter<DLFileEntry>
				workflowHandlerInvocationCounter =
					new WorkflowHandlerInvocationCounter<>(
						DLFileEntryConstants.getClassName())) {

			FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			Assert.assertEquals(
				1,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", Object.class, int.class, Map.class));

			DLAppServiceTestUtil.updateFileEntry(
				group.getGroupId(), fileEntry.getFileEntryId(),
				RandomTestUtil.randomString(), null, null, true);

			Assert.assertEquals(
				2,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", Object.class, int.class, Map.class));
		}
	}

	@Test(expected = FileSizeException.class)
	public void testShouldFailIfSizeLimitExceeded() throws Exception {
		String fileName = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(), fileName,
			ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, null, 0, null, null,
			serviceContext);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				DLAppServiceTestUtil.getConfigurationTemporarySwapper(
					"fileMaxSize", 1L)) {

			byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, DLVersionNumberIncrease.MAJOR, bytes, null,
				null, serviceContext);
		}
	}

	@Test
	public void testShouldFireSyncEvent() throws Exception {
		AtomicInteger counter =
			DLAppServiceTestUtil.registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceTestUtil.updateFileEntry(
			fileEntry.getGroupId(), fileEntry.getFileEntryId(),
			fileEntry.getTitle(), null, null, true);

		Assert.assertEquals(2, counter.get());
	}

	@Test
	public void testShouldIncrementMajorVersion() throws Exception {
		String fileName = "TestVersion.txt";

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), fileName);

		fileEntry = DLAppServiceTestUtil.updateFileEntry(
			group.getGroupId(), fileEntry.getFileEntryId(), fileName, null,
			null, true);

		fileEntry = DLAppServiceTestUtil.updateFileEntry(
			group.getGroupId(), fileEntry.getFileEntryId(), fileName, null,
			null, true);

		Assert.assertEquals(
			"Version label incorrect after major update", "3.0",
			fileEntry.getVersion());
	}

	@Test
	public void testShouldIncrementMinorVersion() throws Exception {
		String fileName = "TestVersion.txt";

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), fileName);

		fileEntry = DLAppServiceTestUtil.updateFileEntry(
			group.getGroupId(), fileEntry.getFileEntryId(), fileName, null,
			null, false);

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, TestDataConstants.repeatByteArray(2),
			null, null,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertEquals(
			"Version label incorrect after major update", "1.2",
			fileEntry.getVersion());
	}

	@Test
	public void testShouldNotChangeMimeTypeIfNullContent() throws Exception {
		String fileName = RandomTestUtil.randomString();

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, null, fileName,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR, CONTENT.getBytes(),
			fileEntry.getExpirationDate(), fileEntry.getReviewDate(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertEquals(ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
	}

	@Test
	public void testShouldSucceedForRootFolder() throws Exception {
		DLAppServiceUtil.updateFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), StringPool.BLANK,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));
	}

	@Test
	public void testShouldSucceedWithNullBytes() throws Exception {
		String fileName = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR, (byte[])null, null, null,
			serviceContext);
	}

	@Test
	public void testShouldSucceedWithNullFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR, (File)null, null, null,
			serviceContext);
	}

	@Test
	public void testShouldSucceedWithNullInputStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());

		DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR, null, 0, null, null, serviceContext);
	}

}