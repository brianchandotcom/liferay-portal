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

package com.liferay.asset.entry.rel.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.entry.rel.model.AssetEntryAssetCategoryRel;
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jonathan McCann
 */
@RunWith(Arquillian.class)
public class AssetEntryAssetCategoryRelLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddAssetEntryAssetCategoryRel() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId);

		_assertAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId, 0);
	}

	@Test
	public void testAddAssetEntryAssetCategoryRelWithPriority() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();
		int priority = RandomTestUtil.randomInt();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId, priority);

		_assertAssetEntryAssetCategoryRel(
			assetEntryId, assetCategoryId, priority);
	}

	@Test
	public void testDeleteAssetEntryAssetCategoryRel() {
		long assetEntryId1 = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId1, assetCategoryId);

		long assetEntryId2 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId2, assetCategoryId);

		Assert.assertEquals(
			_assetEntryAssetCategoryRels.toString(), 2,
			_assetEntryAssetCategoryRels.size());

		_assetEntryAssetCategoryRelLocalService.
			deleteAssetEntryAssetCategoryRel(assetEntryId1, assetCategoryId);

		_assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetCategoryId(
					assetCategoryId);

		_assertAssetEntryAssetCategoryRel(assetEntryId2, assetCategoryId, 0);
	}

	@Test
	public void testDeleteAssetEntryAssetCategoryRelByAssetCategoryId() {
		long assetEntryId1 = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId1, assetCategoryId);

		long assetEntryId2 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId2, assetCategoryId);

		Assert.assertEquals(
			_assetEntryAssetCategoryRels.toString(), 2,
			_assetEntryAssetCategoryRels.size());

		_assetEntryAssetCategoryRelLocalService.
			deleteAssetEntryAssetCategoryRelByAssetCategoryId(assetCategoryId);

		_assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetCategoryId(
					assetCategoryId);

		Assert.assertEquals(
			_assetEntryAssetCategoryRels.toString(), 0,
			_assetEntryAssetCategoryRels.size());
	}

	@Test
	public void testDeleteAssetEntryAssetCategoryRelByAssetEntryId() {
		long assetEntryId1 = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId1, assetCategoryId);

		long assetEntryId2 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId2, assetCategoryId);

		Assert.assertEquals(
			_assetEntryAssetCategoryRels.toString(), 2,
			_assetEntryAssetCategoryRels.size());

		_assetEntryAssetCategoryRelLocalService.
			deleteAssetEntryAssetCategoryRelByAssetEntryId(assetEntryId1);

		_assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetCategoryId(
					assetCategoryId);

		_assertAssetEntryAssetCategoryRel(assetEntryId2, assetCategoryId, 0);
	}

	@Test
	public void testFetchAssetEntryAssetCategoryRel() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			assetEntryId, assetCategoryId);

		_assetEntryAssetCategoryRels.add(
			_assetEntryAssetCategoryRelLocalService.
				fetchAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId));

		_assertAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId, 0);
	}

	@Test
	public void testFetchAssetEntryAssetCategoryRelWithInvalidAssetEntryId() {
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			_assetEntryAssetCategoryRelLocalService.
				fetchAssetEntryAssetCategoryRel(
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong());

		Assert.assertNull(assetEntryAssetCategoryRel);
	}

	@Test
	public void testGetAssetCategoryPrimaryKeys() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId1 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId1);

		long assetCategoryId2 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId2);

		long[] assetCategoryPrimaryKeys =
			_assetEntryAssetCategoryRelLocalService.getAssetCategoryPrimaryKeys(
				assetEntryId);

		Assert.assertEquals(
			Arrays.toString(assetCategoryPrimaryKeys), 2,
			assetCategoryPrimaryKeys.length);

		Assert.assertEquals(assetCategoryId1, assetCategoryPrimaryKeys[0]);
		Assert.assertEquals(assetCategoryId2, assetCategoryPrimaryKeys[1]);
	}

	@Test
	public void testGetAssetEntryAssetCategoryRelsByAssetCategoryId() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId);

		_assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetCategoryId(
					assetCategoryId);

		_assertAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId, 0);
	}

	@Test
	public void testGetAssetEntryAssetCategoryRelsByAssetEntryId() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId);

		_assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetEntryId(assetEntryId);

		_assertAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId, 0);
	}

	@Test
	public void testGetAssetEntryAssetCategoryRelsCount() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId1 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId1);

		long assetCategoryId2 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId2);

		int assetEntryAssetCategoryRelsCount =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount(assetEntryId);

		Assert.assertEquals(2, assetEntryAssetCategoryRelsCount);
	}

	@Test
	public void testGetAssetEntryPrimaryKeys() {
		long assetEntryId1 = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId1, assetCategoryId);

		long assetEntryId2 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId2, assetCategoryId);

		long[] assetEntryPrimaryKeys =
			_assetEntryAssetCategoryRelLocalService.getAssetEntryPrimaryKeys(
				assetCategoryId);

		Assert.assertEquals(
			Arrays.toString(assetEntryPrimaryKeys), 2,
			assetEntryPrimaryKeys.length);

		Assert.assertEquals(assetEntryId1, assetEntryPrimaryKeys[0]);
		Assert.assertEquals(assetEntryId2, assetEntryPrimaryKeys[1]);
	}

	private void _addAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId) {

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId, 0);
	}

	private void _addAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId, int priority) {

		_assetEntryAssetCategoryRels.add(
			_assetEntryAssetCategoryRelLocalService.
				addAssetEntryAssetCategoryRel(
					assetEntryId, assetCategoryId, priority));
	}

	private void _assertAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId, int priority) {

		Assert.assertEquals(
			_assetEntryAssetCategoryRels.toString(), 1,
			_assetEntryAssetCategoryRels.size());

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			_assetEntryAssetCategoryRels.get(0);

		Assert.assertEquals(
			assetEntryId, assetEntryAssetCategoryRel.getAssetEntryId());

		Assert.assertEquals(
			assetCategoryId, assetEntryAssetCategoryRel.getAssetCategoryId());

		Assert.assertEquals(priority, assetEntryAssetCategoryRel.getPriority());
	}

	@Inject
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@DeleteAfterTestRun
	private List<AssetEntryAssetCategoryRel> _assetEntryAssetCategoryRels =
		new ArrayList<>();

}