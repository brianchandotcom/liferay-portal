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

package com.liferay.depot.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.depot.group.provider.SiteConnectedGroupGroupProvider;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.ArrayList;
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
public class DepotEntryDLAppServiceWhenCopyingWithCategoryAndTagTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_depotEntry = _addDepotEntry();

		_depotGroup = _groupLocalService.getGroup(_depotEntry.getGroupId());

		_depotParentFolder = _dlAppService.addFolder(
			null, _depotGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder",
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_depotGroup.getGroupId(), TestPropsValues.getUserId()));

		_group = GroupTestUtil.addGroup();

		_groupParentFolder = _dlAppService.addFolder(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Target Test Folder",
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _depotGroup.getGroupId(),
				"Vocabulary", new ServiceContext());

		_assetCategory = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _depotGroup.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			new ServiceContext());
	}

	@Test
	public void testCopyFileShouldCopyTagRelatedDepotGroup() throws Exception {
		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			_depotEntry.getDepotEntryId(), _group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_depotGroup.getGroupId());

		String assetTagName = RandomTestUtil.randomString();

		_addTag(_depotGroup.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), _depotGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _FILE_NAME,
			ContentTypes.TEXT_PLAIN, _FILE_NAME, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, new byte[0], null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new String[] {StringUtil.toLowerCase(assetTagName)},
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _groupParentFolder.getFolderId(),
			_groupParentFolder.getGroupId(),
			_siteConnectedGroupGroupProvider.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					_groupParentFolder.getGroupId()),
			ServiceContextTestUtil.getServiceContext(
				_groupParentFolder.getGroupId()));

		Assert.assertArrayEquals(
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()),
			_assetTagLocalService.getTagNames(
				className, fileEntry2.getFileEntryId()));
	}

	@Test
	public void testCopyFileShouldNotCopyCategoryNotRelatedDepotGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_depotGroup.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), _depotGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _FILE_NAME,
			ContentTypes.TEXT_PLAIN, _FILE_NAME, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, new byte[0], null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new long[] {_assetCategory.getCategoryId()},
			_assetCategoryLocalService.getCategoryIds(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _groupParentFolder.getFolderId(),
			_groupParentFolder.getGroupId(),
			_siteConnectedGroupGroupProvider.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					_groupParentFolder.getGroupId()),
			ServiceContextTestUtil.getServiceContext(
				_groupParentFolder.getGroupId()));

		Assert.assertTrue(
			ArrayUtil.isEmpty(
				_assetCategoryLocalService.getCategoryIds(
					className, fileEntry2.getFileEntryId())));
	}

	@Test
	public void testCopyFileShouldNotCopyTagNotRelatedDepotGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_depotGroup.getGroupId());

		String assetTagName = RandomTestUtil.randomString();

		_addTag(_depotGroup.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), _depotGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _FILE_NAME,
			ContentTypes.TEXT_PLAIN, _FILE_NAME, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, new byte[0], null, null,
			serviceContext);

		String className = DLFileEntryConstants.getClassName();

		Assert.assertArrayEquals(
			new String[] {StringUtil.toLowerCase(assetTagName)},
			_assetTagLocalService.getTagNames(
				className, fileEntry1.getFileEntryId()));

		FileEntry fileEntry2 = _dlAppService.copyFileEntry(
			fileEntry1.getFileEntryId(), _groupParentFolder.getFolderId(),
			_groupParentFolder.getGroupId(),
			_siteConnectedGroupGroupProvider.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					_groupParentFolder.getGroupId()),
			ServiceContextTestUtil.getServiceContext(
				_groupParentFolder.getGroupId()));

		Assert.assertTrue(
			ArrayUtil.isEmpty(
				_assetTagLocalService.getTagNames(
					className, fileEntry2.getFileEntryId())));
	}

	@Test
	public void testCopyFolderShouldCopyCategoryAndTagRelatedDepotGroup()
		throws Exception {

		_depotEntryGroupRelLocalService.addDepotEntryGroupRel(
			_depotEntry.getDepotEntryId(), _group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_depotGroup.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		String assetTagName = RandomTestUtil.randomString();

		_addTag(_depotGroup.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			RandomTestUtil.randomString(), _depotGroup.getGroupId(),
			_depotParentFolder.getFolderId(), _FILE_NAME,
			ContentTypes.TEXT_PLAIN, _FILE_NAME, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, new byte[0], null, null,
			serviceContext);

		Folder folder = _dlAppService.copyFolder(
			_depotGroup.getGroupId(), _depotParentFolder.getFolderId(),
			_group.getGroupId(), _groupParentFolder.getFolderId(),
			_siteConnectedGroupGroupProvider.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					_groupParentFolder.getGroupId()),
			ServiceContextTestUtil.getServiceContext(_depotGroup.getGroupId()));

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			_group.getGroupId(), folder.getFolderId());

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
	public void testCopyFolderShouldNotCopyCategoryNorTagNotRelatedDepotGroup()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_depotGroup.getGroupId());

		serviceContext.setAssetCategoryIds(
			new long[] {_assetCategory.getCategoryId()});

		String assetTagName = RandomTestUtil.randomString();

		_addTag(_depotGroup.getGroupId(), assetTagName);

		serviceContext.setAssetTagNames(new String[] {assetTagName});

		_dlAppService.addFileEntry(
			RandomTestUtil.randomString(), _depotGroup.getGroupId(),
			_depotParentFolder.getFolderId(), _FILE_NAME,
			ContentTypes.TEXT_PLAIN, _FILE_NAME, StringPool.BLANK,
			StringPool.BLANK, StringPool.BLANK, new byte[0], null, null,
			serviceContext);

		Folder folder = _dlAppService.copyFolder(
			_depotGroup.getGroupId(), _depotParentFolder.getFolderId(),
			_group.getGroupId(), _groupParentFolder.getFolderId(),
			_siteConnectedGroupGroupProvider.
				getCurrentAndAncestorSiteAndDepotGroupIds(
					_groupParentFolder.getGroupId()),
			ServiceContextTestUtil.getServiceContext(_depotGroup.getGroupId()));

		List<FileEntry> fileEntries = _dlAppService.getFileEntries(
			_group.getGroupId(), folder.getFolderId());

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

	private DepotEntry _addDepotEntry() throws Exception {
		DepotEntry depotEntry = _depotEntryLocalService.addDepotEntry(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), "name"
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getDefault(), "description"
			).build(),
			ServiceContextTestUtil.getServiceContext());

		_depotEntries.add(depotEntry);

		return depotEntry;
	}

	private AssetTag _addTag(long groupId, String assetTagName)
		throws Exception {

		long userId = TestPropsValues.getUserId();

		return _assetTagLocalService.addTag(
			userId, groupId, assetTagName,
			ServiceContextTestUtil.getServiceContext(groupId, userId));
	}

	private static final String _FILE_NAME = "Title.txt";

	@Inject
	private static DLAppService _dlAppService;

	private AssetCategory _assetCategory;

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private final List<DepotEntry> _depotEntries = new ArrayList<>();

	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@DeleteAfterTestRun
	private Group _depotGroup;

	private Folder _depotParentFolder;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	private Folder _groupParentFolder;

	@Inject
	private SiteConnectedGroupGroupProvider _siteConnectedGroupGroupProvider;

}