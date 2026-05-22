/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_4_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_4_x.UpgradeAssetEntryPublishDate;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Saurasish Basak
 */
@RunWith(Arquillian.class)
public class UpgradeAssetEntryPublishDateTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_user = TestPropsValues.getUser();

		_group = _user.getGroup();
	}

	@Test
	public void testUpgrade() throws Exception {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.YEAR, -1);

		Date displayDate = calendar.getTime();

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(), displayDate, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		FileEntry fileEntryWithoutDisplayDate = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(), null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		assetEntry.setPublishDate(fileEntry.getCreateDate());

		_assetEntryLocalService.updateAssetEntry(assetEntry);

		assetEntry = _assetEntryLocalService.getEntry(
			DLFileEntry.class.getName(),
			fileEntryWithoutDisplayDate.getFileEntryId());

		Date publishDate = RandomTestUtil.nextDate();

		assetEntry.setPublishDate(publishDate);

		_assetEntryLocalService.updateAssetEntry(assetEntry);

		UpgradeProcess upgradeProcess = new UpgradeAssetEntryPublishDate();

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();

		assetEntry = _assetEntryLocalService.getEntry(
			DLFileEntry.class.getName(), fileEntry.getFileEntryId());

		Assert.assertEquals(displayDate, assetEntry.getPublishDate());

		assetEntry = _assetEntryLocalService.getEntry(
			DLFileEntry.class.getName(),
			fileEntryWithoutDisplayDate.getFileEntryId());

		Assert.assertEquals(publishDate, assetEntry.getPublishDate());
	}

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private EntityCache _entityCache;

	private Group _group;

	@Inject
	private MultiVMPool _multiVMPool;

	private User _user;

}