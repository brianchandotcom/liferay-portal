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

package com.liferay.asset.auto.tagger.internal.osgi.commands.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.auto.tagger.test.BaseAssetAutoTaggerTestCase;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.Method;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
@Sync
public class AssetAutoTaggerOSGiCommandsTest
	extends BaseAssetAutoTaggerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public void setUp() throws Exception {
		super.setUp();

		Registry registry = RegistryUtil.getRegistry();

		_assetAutoTaggerOSGiCommands = registry.getService(
			"com.liferay.asset.auto.tagger.internal.osgi.commands." +
				"AssetAutoTaggerOSGiCommands");
	}

	@Test
	public void testTagAllUntaggedTagsAllTheAssetsThatHaveNoTags()
		throws Exception {

		withAutoTaggerDisabled(
			() -> {
				AssetEntry assetEntryWithPreviousTags =
					addFileEntryAssetEntry();

				applyAssetTagName(
					assetEntryWithPreviousTags, ASSET_TAG_NAME_MANUAL);

				assertContainsAssetTagName(
					assetEntryWithPreviousTags, ASSET_TAG_NAME_MANUAL);

				AssetEntry assetEntryWithNoPreviousTags =
					addFileEntryAssetEntry();

				assertHasNoTags(assetEntryWithNoPreviousTags);

				withAutoTaggerEnabled(
					() -> {
						_tagAllUntagged(DLFileEntryConstants.getClassName());

						assertContainsAssetTagName(
							assetEntryWithNoPreviousTags, ASSET_TAG_NAME_AUTO);

						assertDoesNotContainAssetTagName(
							assetEntryWithPreviousTags, ASSET_TAG_NAME_AUTO);
					});
			});
	}

	@Test
	public void testUnTagAllRemovesAllTheAutoTags() throws Exception {
		AssetEntry assetEntry = addFileEntryAssetEntry();

		applyAssetTagName(assetEntry, ASSET_TAG_NAME_MANUAL);

		assertContainsAssetTagName(assetEntry, ASSET_TAG_NAME_AUTO);

		assertContainsAssetTagName(assetEntry, ASSET_TAG_NAME_MANUAL);

		_untagAll(DLFileEntryConstants.getClassName());

		assertDoesNotContainAssetTagName(assetEntry, ASSET_TAG_NAME_AUTO);

		assertContainsAssetTagName(assetEntry, ASSET_TAG_NAME_MANUAL);
	}

	private void _tagAllUntagged(String... classNames) throws Exception {
		Class<?> clazz = _assetAutoTaggerOSGiCommands.getClass();

		Method method = clazz.getMethod(
			"tagAllUntagged", String.class, String[].class);

		method.invoke(
			_assetAutoTaggerOSGiCommands, String.valueOf(group.getCompanyId()),
			classNames);
	}

	private void _untagAll(String... classNames) throws Exception {
		Class<?> clazz = _assetAutoTaggerOSGiCommands.getClass();

		Method method = clazz.getMethod(
			"untagAll", String.class, String[].class);

		method.invoke(
			_assetAutoTaggerOSGiCommands, String.valueOf(group.getCompanyId()),
			classNames);
	}

	private Object _assetAutoTaggerOSGiCommands;

}