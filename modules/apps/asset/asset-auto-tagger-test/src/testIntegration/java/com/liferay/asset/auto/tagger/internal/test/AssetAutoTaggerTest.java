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

package com.liferay.asset.auto.tagger.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.AssetAutoTagger;
import com.liferay.asset.auto.tagger.model.AssetAutoTaggerEntry;
import com.liferay.asset.auto.tagger.service.AssetAutoTaggerEntryLocalService;
import com.liferay.asset.auto.tagger.test.BaseAssetAutoTaggerTestCase;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
@Sync
public class AssetAutoTaggerTest extends BaseAssetAutoTaggerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAutoTagsANewAssetOnCreation() throws Exception {
		withAutoTaggerEnabled(
			() -> {
				AssetEntry assetEntry = addFileEntryAssetEntry();

				assertContainsAssetTagName(assetEntry, ASSET_TAG_NAME_AUTO);
			});
	}

	@Test
	public void testDeletesAssetAutoTaggerEntriesWhenAssetIsDeleted()
		throws Exception {

		withAutoTaggerEnabled(
			() -> {
				AssetEntry assetEntry = addFileEntryAssetEntry();

				List<AssetAutoTaggerEntry> assetAutoTaggerEntries =
					_assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntries(
						assetEntry);

				Assert.assertEquals(
					assetAutoTaggerEntries.toString(), 1,
					assetAutoTaggerEntries.size());

				DLAppServiceUtil.deleteFileEntry(assetEntry.getClassPK());

				assetAutoTaggerEntries =
					_assetAutoTaggerEntryLocalService.getAssetAutoTaggerEntries(
						assetEntry);

				Assert.assertEquals(
					assetAutoTaggerEntries.toString(), 0,
					assetAutoTaggerEntries.size());
			});
	}

	@Test
	public void testIsDisabledByDefault() throws Exception {
		AssetEntry assetEntry = addFileEntryAssetEntry();

		List<AssetTag> assetTags = assetEntry.getTags();

		Assert.assertTrue(assetTags.isEmpty());
	}

	@Test
	public void testKeepsAssetTagCount() throws Exception {
		withAutoTaggerEnabled(
			() -> {
				AssetEntry assetEntry = addFileEntryAssetEntry();

				AssetTag assetTag = _assetTagLocalService.getTag(
					group.getGroupId(), ASSET_TAG_NAME_AUTO);

				Assert.assertEquals(1, assetTag.getAssetCount());

				_assetAutoTagger.untag(assetEntry);

				assetTag = _assetTagLocalService.getTag(
					group.getGroupId(), ASSET_TAG_NAME_AUTO);

				Assert.assertEquals(0, assetTag.getAssetCount());
			});
	}

	@Test
	public void testRemovesAutoTags() throws Exception {
		withAutoTaggerEnabled(
			() -> {
				AssetEntry assetEntry = addFileEntryAssetEntry();

				assertContainsAssetTagName(assetEntry, ASSET_TAG_NAME_AUTO);

				applyAssetTagName(assetEntry, ASSET_TAG_NAME_MANUAL);

				_assetAutoTagger.untag(assetEntry);

				String[] assetTagNames = assetEntry.getTagNames();

				Assert.assertEquals(
					assetTagNames.toString(), 1, assetTagNames.length);

				assertContainsAssetTagName(assetEntry, ASSET_TAG_NAME_MANUAL);
			});
	}

	@Inject
	private AssetAutoTagger _assetAutoTagger;

	@Inject
	private AssetAutoTaggerEntryLocalService _assetAutoTaggerEntryLocalService;

	@Inject
	private AssetTagLocalService _assetTagLocalService;

}