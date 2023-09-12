/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.service.RatingsEntryLocalService;

import java.util.HashMap;
import java.util.List;

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
public class
	DLAppServiceWhenCopyingWithAssetCategoriesRatingsEntriesAndAssetTagsTest
		extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		super.setUp();

		_assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			TestPropsValues.getUserId(), group.getGroupId(), "Vocabulary",
			new ServiceContext());

		_assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), group.getGroupId(),
			RandomTestUtil.randomString(), _assetVocabulary.getVocabularyId(),
			new ServiceContext());

		_childGroup = GroupTestUtil.addGroup(group.getGroupId());

		_newParentFolder = _dlAppService.addFolder(
			null, group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "New Test Folder",
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
		_targetParentFolder = _dlAppService.addFolder(
			null, targetGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Target Test Folder",
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				targetGroup.getGroupId(), TestPropsValues.getUserId()));
	}

	@Test
	public void testCopyFileShouldCopyAssetCategoriesParentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new long[] {_assetCategory.getCategoryId()},
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			_childGroup.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(_childGroup.getGroupId()));

		Assert.assertArrayEquals(
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry1.getFileEntryId()),
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry2.getFileEntryId()));
	}

	@Test
	public void testCopyFileShouldCopyAssetCategoriesSameGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new long[] {_assetCategory.getCategoryId()},
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _newParentFolder.getFolderId(),
			_newParentFolder.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(
				_newParentFolder.getGroupId()));

		Assert.assertArrayEquals(
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry1.getFileEntryId()),
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry2.getFileEntryId()));
	}

	@Test
	public void testCopyFileShouldCopyAssetTagsParentGroup() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		String assetTagName = RandomTestUtil.randomString();

		AssetTestUtil.addTag(group.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new String[] {StringUtil.toLowerCase(assetTagName)},
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			_childGroup.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(_childGroup.getGroupId()));

		Assert.assertArrayEquals(
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()),
			_assetTagLocalService.getTagNames(
				className, fileEntry2.getFileEntryId()));
	}

	@Test
	public void testCopyFileShouldCopyAssetTagsSameGroup() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		String assetTagName = RandomTestUtil.randomString();

		AssetTestUtil.addTag(group.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new String[] {StringUtil.toLowerCase(assetTagName)},
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _newParentFolder.getFolderId(),
			_newParentFolder.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(
				_newParentFolder.getGroupId()));

		Assert.assertArrayEquals(
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()),
			_assetTagLocalService.getTagNames(
				className, fileEntry2.getFileEntryId()));
	}

	@Test
	public void testCopyFileShouldCopyRatingsEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();
		double score = 0.3D;

		_ratingsEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), className, fileEntry1.getFileEntryId(),
			score, serviceContext);

		List<RatingsEntry> ratingsEntries1 =
			_ratingsEntryLocalService.getEntries(
				className, fileEntry1.getFileEntryId());

		Assert.assertEquals(
			ratingsEntries1.toString(), 1, ratingsEntries1.size());

		RatingsEntry ratingsEntry1 = ratingsEntries1.get(0);

		Assert.assertEquals(score, ratingsEntry1.getScore(), 0.1);

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _newParentFolder.getFolderId(),
			_newParentFolder.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(
				_newParentFolder.getGroupId()));

		List<RatingsEntry> ratingsEntries2 =
			_ratingsEntryLocalService.getEntries(
				className, fileEntry2.getFileEntryId());

		Assert.assertEquals(
			ratingsEntries2.toString(), 1, ratingsEntries2.size());

		RatingsEntry ratingsEntry2 = ratingsEntries2.get(0);

		Assert.assertEquals(score, ratingsEntry2.getScore(), 0.1);
	}

	@Test
	public void testCopyFileShouldNotCopyAssetCategoriesDifferentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new long[] {_assetCategory.getCategoryId()},
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _targetParentFolder.getFolderId(),
			_targetParentFolder.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new long[] {_targetParentFolder.getGroupId()},
			ServiceContextTestUtil.getServiceContext(
				_targetParentFolder.getGroupId()));

		Assert.assertTrue(
			ArrayUtil.isEmpty(
				_assetCategoryLocalService.getCategoryIds(
					className, fileEntry2.getFileEntryId())));
	}

	@Test
	public void testCopyFileShouldNotCopyAssetTagsDifferentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		String assetTagName = RandomTestUtil.randomString();

		AssetTestUtil.addTag(group.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLAppServiceTestUtil.FILE_NAME, ContentTypes.TEXT_PLAIN,
			DLAppServiceTestUtil.FILE_NAME, StringPool.BLANK, StringPool.BLANK,
			StringPool.BLANK, BaseDLAppTestCase.CONTENT.getBytes(), null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new String[] {StringUtil.toLowerCase(assetTagName)},
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _targetParentFolder.getFolderId(),
			_targetParentFolder.getGroupId(),
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT,
			new long[] {_targetParentFolder.getGroupId()},
			ServiceContextTestUtil.getServiceContext(
				_targetParentFolder.getGroupId()));

		Assert.assertTrue(
			ArrayUtil.isEmpty(
				_assetTagLocalService.getTagNames(
					className, fileEntry2.getFileEntryId())));
	}

	@Test
	public void testCopyFolderShouldCopyAssetCategoriesAndAssetTagsParentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		String assetTagName = RandomTestUtil.randomString();

		AssetTestUtil.addTag(group.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			ContentTypes.TEXT_PLAIN, DLAppServiceTestUtil.FILE_NAME,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), null, null, serviceContext);

		Folder folder = _dlAppService.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			_childGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, new HashMap<>(),
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(_childGroup.getGroupId()));

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			_childGroup.getGroupId(), folder.getFolderId());

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry fileEntry2 = fileEntries.get(0);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry1.getFileEntryId()),
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry2.getFileEntryId()));

		Assert.assertArrayEquals(
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()),
			_assetTagLocalService.getTagNames(
				className, fileEntry2.getFileEntryId()));
	}

	@Test
	public void testCopyFolderShouldCopyAssetTagsSameGroup() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		String assetTagName = RandomTestUtil.randomString();

		AssetTestUtil.addTag(group.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			ContentTypes.TEXT_PLAIN, DLAppServiceTestUtil.FILE_NAME,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), null, null, serviceContext);

		Folder folder = _dlAppService.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(), group.getGroupId(),
			_newParentFolder.getFolderId(), new HashMap<>(),
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry fileEntry2 = fileEntries.get(0);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry1.getFileEntryId()),
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry2.getFileEntryId()));

		Assert.assertArrayEquals(
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()),
			_assetTagLocalService.getTagNames(
				className, fileEntry2.getFileEntryId()));
	}

	@Test
	public void testCopyFolderShouldCopyRatingsEntriesFromFile()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			ContentTypes.TEXT_PLAIN, DLAppServiceTestUtil.FILE_NAME,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), null, null, serviceContext);

		String className = DLFileEntryConstants.getClassName();
		double score = 0.3D;

		_ratingsEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), className, fileEntry1.getFileEntryId(),
			score, serviceContext);

		List<RatingsEntry> ratingsEntries1 =
			_ratingsEntryLocalService.getEntries(
				className, fileEntry1.getFileEntryId());

		Assert.assertEquals(
			ratingsEntries1.toString(), 1, ratingsEntries1.size());

		RatingsEntry ratingsEntry1 = ratingsEntries1.get(0);

		Assert.assertEquals(score, ratingsEntry1.getScore(), 0.1);

		Folder folder = _dlAppService.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(), group.getGroupId(),
			_newParentFolder.getFolderId(), new HashMap<>(),
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry fileEntry2 = fileEntries.get(0);

		List<RatingsEntry> ratingsEntries2 =
			_ratingsEntryLocalService.getEntries(
				className, fileEntry2.getFileEntryId());

		Assert.assertEquals(
			ratingsEntries2.toString(), 1, ratingsEntries2.size());

		RatingsEntry ratingsEntry2 = ratingsEntries2.get(0);

		Assert.assertEquals(score, ratingsEntry2.getScore(), 0.1);
	}

	@Test
	public void testCopyFolderShouldCopyRatingsEntriesFromFolder()
		throws Exception {

		String className = DLFolderConstants.getClassName();
		double score = 0.3D;

		_ratingsEntryLocalService.updateEntry(
			TestPropsValues.getUserId(), className, parentFolder.getFolderId(),
			score,
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		List<RatingsEntry> ratingsEntries1 =
			_ratingsEntryLocalService.getEntries(
				className, parentFolder.getFolderId());

		Assert.assertEquals(
			ratingsEntries1.toString(), 1, ratingsEntries1.size());

		RatingsEntry ratingsEntry1 = ratingsEntries1.get(0);

		Assert.assertEquals(score, ratingsEntry1.getScore(), 0.1);

		Folder folder = _dlAppService.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(), group.getGroupId(),
			_newParentFolder.getFolderId(), new HashMap<>(),
			new long[] {group.getGroupId()},
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		List<RatingsEntry> ratingsEntries2 =
			_ratingsEntryLocalService.getEntries(
				className, folder.getFolderId());

		Assert.assertEquals(
			ratingsEntries2.toString(), 1, ratingsEntries2.size());

		RatingsEntry ratingsEntry2 = ratingsEntries2.get(0);

		Assert.assertEquals(score, ratingsEntry2.getScore(), 0.1);
	}

	@Test
	public void testCopyFolderShouldNotCopyAssetTagsDifferentGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		String assetTagName = RandomTestUtil.randomString();

		AssetTestUtil.addTag(group.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		_dlAppService.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			ContentTypes.TEXT_PLAIN, DLAppServiceTestUtil.FILE_NAME,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), null, null, serviceContext);

		Folder folder = _dlAppService.copyFolder(
			group.getGroupId(), parentFolder.getFolderId(),
			targetGroup.getGroupId(), _targetParentFolder.getFolderId(),
			new HashMap<>(), new long[] {targetGroup.getGroupId()},
			ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			targetGroup.getGroupId(), folder.getFolderId());

		Assert.assertEquals(fileEntries.toString(), 1, fileEntries.size());

		FileEntry fileEntry = fileEntries.get(0);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertTrue(
			ArrayUtil.isEmpty(
				_assetCategoryLocalService.getCategoryIds(
					className, fileEntry.getFileEntryId())));

		Assert.assertTrue(
			ArrayUtil.isEmpty(
				_assetTagLocalService.getTagNames(
					className, fileEntry.getFileEntryId())));
	}

	@Inject
	private static DLAppService _dlAppService;

	private AssetCategory _assetCategory;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private Group _childGroup;

	private Folder _newParentFolder;

	@Inject
	private RatingsEntryLocalService _ratingsEntryLocalService;

	private Folder _targetParentFolder;

}