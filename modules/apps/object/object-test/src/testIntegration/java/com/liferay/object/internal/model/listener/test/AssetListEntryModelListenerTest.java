/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carolina Barbosa
 */
@RunWith(Arquillian.class)
public class AssetListEntryModelListenerTest extends BaseModelListenerTestCase {

	@Override
	@Test
	public void testOnBeforeUpdate() throws Exception {
		super.testOnBeforeUpdate();

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.addAssetListEntry(
				RandomTestUtil.randomString(), companyAdminUser.getUserId(),
				group.getGroupId(), RandomTestUtil.randomString(),
				AssetListEntryTypeConstants.TYPE_DYNAMIC,
				UnicodePropertiesBuilder.create(
					true
				).buildString(),
				serviceContext);

		assetListEntry.setAssetEntryType(objectDefinition1.getClassName());

		assetListEntry = _assetListEntryLocalService.updateAssetListEntry(
			assetListEntry);

		Assert.assertEquals(
			objectDefinition2.getClassName(),
			assetListEntry.getAssetEntryType());
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

}