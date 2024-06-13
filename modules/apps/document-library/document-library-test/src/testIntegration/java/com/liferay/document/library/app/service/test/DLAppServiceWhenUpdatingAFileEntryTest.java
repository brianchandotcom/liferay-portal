/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.app.service.test.util.DLAppServiceTestUtil;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.store.DLStore;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.document.library.workflow.WorkflowHandlerInvocationCounter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.constants.TestDataConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

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
			parentFolder.getFolderId(), fileName, fileName, null, null, null,
			assetTagNames);

		assetTagNames = new String[] {"hello", "world"};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, bytes, null, null, null,
			serviceContext);

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		AssetEntry latestAssetEntry = _assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId());

		Assert.assertNotNull(latestAssetEntry);

		AssertUtils.assertEqualsSorted(
			assetTagNames, latestAssetEntry.getTagNames());

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
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
			parentFolder.getFolderId(), fileName, fileName, null, null, null,
			assetTagNames);

		assetTagNames = new String[] {"hello", "world"};

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, null, 0, null, null, null,
			serviceContext);

		FileVersion fileVersion = fileEntry.getLatestFileVersion();

		AssetEntry latestAssetEntry = _assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(),
			fileVersion.getFileVersionId());

		Assert.assertNotNull(latestAssetEntry);

		AssertUtils.assertEqualsSorted(
			assetTagNames, latestAssetEntry.getTagNames());

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
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

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, bytes, null, null, null,
			serviceContext);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		AssertUtils.assertEqualsSorted(assetTagNames, assetEntry.getTagNames());
	}

	@Test
	public void testFileEntryCanUpdateOldVersionStoreFileTitle()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), null, null, null,
			new UnsyncByteArrayInputStream(CONTENT.getBytes()),
			CONTENT.length(), null, null, null, serviceContext);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getDLFileEntry(
			fileEntry.getFileEntryId());

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		_dlStore.updateFile(
			DLStoreRequest.builder(
				dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName()
			).versionLabel(
				dlFileVersion.getVersion()
			).build(),
			new UnsyncByteArrayInputStream(CONTENT.getBytes()));
		_dlStore.deleteFile(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), dlFileVersion.getStoreFileName());

		String title = RandomTestUtil.randomString();

		InputStream inputStream = new UnsyncByteArrayInputStream(
			CONTENT.getBytes());

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM, title, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, DLVersionNumberIncrease.MAJOR,
			FileUtil.createTempFile(inputStream), null, null, null,
			serviceContext);

		Assert.assertEquals(title, fileEntry.getTitle());
	}

	@Test
	public void testFileEntryShouldUpdateDisplayDate() throws Exception {
		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, null);

		Assert.assertNull(fileEntry.getDisplayDate());
		Assert.assertNull(fileEntry.getExpirationDate());
		Assert.assertNull(fileEntry.getReviewDate());

		Date displayDate = new Date();

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR, CONTENT.getBytes(),
			displayDate, fileEntry.getExpirationDate(),
			fileEntry.getReviewDate(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertEquals(displayDate, fileEntry.getDisplayDate());
		Assert.assertNull(fileEntry.getExpirationDate());
		Assert.assertNull(fileEntry.getReviewDate());
	}

	@Test
	public void testFileEntryShouldUpdateExpirationDate() throws Exception {
		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, null);

		Assert.assertNull(fileEntry.getDisplayDate());
		Assert.assertNull(fileEntry.getExpirationDate());
		Assert.assertNull(fileEntry.getReviewDate());

		Date expirationDate = new Date(
			System.currentTimeMillis() + Time.MINUTE);

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR, CONTENT.getBytes(),
			fileEntry.getDisplayDate(), expirationDate,
			fileEntry.getReviewDate(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertNull(fileEntry.getDisplayDate());
		Assert.assertEquals(expirationDate, fileEntry.getExpirationDate());
		Assert.assertNull(fileEntry.getReviewDate());
	}

	@Test
	public void testFileEntryShouldUpdateReviewDate() throws Exception {
		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, null, null);

		Assert.assertNull(fileEntry.getDisplayDate());
		Assert.assertNull(fileEntry.getExpirationDate());
		Assert.assertNull(fileEntry.getReviewDate());

		Date reviewDate = new Date();

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), RandomTestUtil.randomString(), null,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MAJOR, CONTENT.getBytes(),
			fileEntry.getDisplayDate(), fileEntry.getExpirationDate(),
			reviewDate,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertNull(fileEntry.getDisplayDate());
		Assert.assertNull(fileEntry.getExpirationDate());
		Assert.assertEquals(reviewDate, fileEntry.getReviewDate());
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
				RandomTestUtil.randomString(), null, null, null, true);

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

		FileEntry fileEntry = dlAppService.addFileEntry(
			null, group.getGroupId(), parentFolder.getFolderId(), fileName,
			ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, null, 0, null, null, null,
			serviceContext);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"fileMaxSize", 1L
					).build())) {

			byte[] bytes = TestDataConstants.TEST_BYTE_ARRAY;

			dlAppService.updateFileEntry(
				fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
				StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
				StringPool.BLANK, DLVersionNumberIncrease.MAJOR, bytes, null,
				null, null, serviceContext);
		}
	}

	@Test
	public void testShouldIncrementMajorVersion() throws Exception {
		String fileName = "TestVersion.txt";

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), fileName);

		fileEntry = DLAppServiceTestUtil.updateFileEntry(
			group.getGroupId(), fileEntry.getFileEntryId(), fileName, null,
			null, null, true);

		fileEntry = DLAppServiceTestUtil.updateFileEntry(
			group.getGroupId(), fileEntry.getFileEntryId(), fileName, null,
			null, null, true);

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
			null, null, false);

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, TestDataConstants.repeatByteArray(2),
			null, null, null,
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

		fileEntry = dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, null, fileName,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR, CONTENT.getBytes(),
			fileEntry.getDisplayDate(), fileEntry.getExpirationDate(),
			fileEntry.getReviewDate(),
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertEquals(ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
	}

	@Test
	public void testShouldSucceedForRootFolder() throws Exception {
		dlAppService.updateFolder(
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

		dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR, (byte[])null, null, null, null,
			serviceContext);
	}

	@Test
	public void testShouldSucceedWithNullFile() throws Exception {
		String fileName = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());

		dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR, (File)null, null, null, null,
			serviceContext);
	}

	@Test
	public void testShouldSucceedWithNullInputStream() throws Exception {
		String fileName = RandomTestUtil.randomString();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());

		dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			fileName, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MAJOR, null, 0, null, null, null,
			serviceContext);
	}

	@Test
	public void testUpdateFileEntryPublishingAFileShouldOnlyHaveOneAssetEntryFromDrafts()
		throws Exception {

		_testUpdateFileEntryShouldOnlyHaveOneAssetEntryFromDrafts(null);
	}

	@Test
	public void testUpdateFileEntrySchedulingAFileShouldOnlyHaveOneAssetEntryFromDrafts()
		throws Exception {

		_testUpdateFileEntryShouldOnlyHaveOneAssetEntryFromDrafts(
			new Date(System.currentTimeMillis() + Time.DAY));
	}

	private void _testUpdateFileEntryShouldOnlyHaveOneAssetEntryFromDrafts(
			Date displayDate)
		throws Exception {

		String fileName = RandomTestUtil.randomString();
		byte[] bytes = CONTENT.getBytes();

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), fileName, fileName, displayDate, null,
			null, null);

		int initialAssetEntriesCount =
			_assetEntryLocalService.getAssetEntriesCount();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MINOR, bytes, displayDate,
			null, null, serviceContext);

		dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MINOR, bytes, displayDate,
			null, null, serviceContext);

		Assert.assertEquals(
			initialAssetEntriesCount + 1,
			_assetEntryLocalService.getAssetEntriesCount());

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomString(), StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, DLVersionNumberIncrease.MINOR, bytes, displayDate,
			null, null, serviceContext);

		Assert.assertEquals(
			initialAssetEntriesCount,
			_assetEntryLocalService.getAssetEntriesCount());
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLStore _dlStore;

}