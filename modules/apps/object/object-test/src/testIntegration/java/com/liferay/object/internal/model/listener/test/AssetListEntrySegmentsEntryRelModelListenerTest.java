/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.segments.constants.SegmentsEntryConstants;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class AssetListEntrySegmentsEntryRelModelListenerTest
	extends BaseModelListenerTestCase {

	@Override
	@Test
	public void testOnBeforeCreate() throws Exception {
		super.testOnBeforeCreate();

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), companyAdminUser.getUserId(),
				group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC,
				UnicodePropertiesBuilder.create(
					true
				).buildString(),
				serviceContext);

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			_assetListEntrySegmentsEntryRelLocalService.
				addAssetListEntrySegmentsEntryRel(
					companyAdminUser.getUserId(), group.getGroupId(),
					assetListEntry.getAssetListEntryId(),
					SegmentsEntryConstants.ID_DEFAULT,
					UnicodePropertiesBuilder.create(
						true
					).put(
						"anyAssetTypeClassName",
						objectDefinition1.getClassName()
					).put(
						"classNames", objectDefinition1.getClassName()
					).buildString(),
					serviceContext);

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.load(
			assetListEntrySegmentsEntryRel.getTypeSettings()
		).build();

		Assert.assertEquals(
			String.valueOf(objectDefinition2ClassNameId),
			unicodeProperties.getProperty("anyAssetType"));
		Assert.assertEquals(
			String.valueOf(objectDefinition2ClassNameId),
			unicodeProperties.getProperty("classNameIds"));
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

}