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

package com.liferay.portal.upgrade.v7_3_x.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.v7_3_x.UpgradeAssetCategory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class UpgradeAssetCategoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		AssetCategory category = _createCategory(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);

		category = _createCategory(category.getCategoryId());

		_createCategory(category.getCategoryId());
	}

	@Test
	public void testUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = new UpgradeAssetCategory();

		upgradeProcess.upgrade();

		CacheRegistryUtil.clear();

		StringBundler sb = new StringBundler(2 * _assetCategories.size() + 1);

		sb.append("/");

		for (AssetCategory category : _assetCategories) {
			category = _assetCategoryLocalService.getAssetCategory(
				category.getCategoryId());

			sb.append(category.getCategoryId());

			sb.append("/");

			Assert.assertEquals(sb.toString(), category.getTreePath());
		}
	}

	private AssetCategory _createCategory(long parentCategoryId) {
		AssetCategory category = _assetCategoryLocalService.createAssetCategory(
			_counterLocalService.increment());

		category.setParentCategoryId(parentCategoryId);

		category = _assetCategoryLocalService.updateAssetCategory(category);

		_assetCategories.add(category);

		return category;
	}

	@Inject
	private static AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private static CounterLocalService _counterLocalService;

	@DeleteAfterTestRun
	private final List<AssetCategory> _assetCategories = new ArrayList<>();

}