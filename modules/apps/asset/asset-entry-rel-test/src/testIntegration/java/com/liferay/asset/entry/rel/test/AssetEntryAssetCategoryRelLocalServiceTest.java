/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import org.junit.Before;
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

	@Before
	public void setUp() {
		_initialAssetEntryAssetCategoryRelsCount =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount();
	}

	@Test
	public void testAddAssetEntryAssetCategoryRel() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 1,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());
		Assert.assertEquals(
			assetEntryId, assetEntryAssetCategoryRel.getAssetEntryId());
		Assert.assertEquals(
			assetCategoryId, assetEntryAssetCategoryRel.getAssetCategoryId());
		Assert.assertEquals(0, assetEntryAssetCategoryRel.getPriority());
	}

	@Test
	public void testAddAssetEntryAssetCategoryRelWithPriority() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();
		int priority = RandomTestUtil.randomInt();

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			_addAssetEntryAssetCategoryRel(
				assetEntryId, assetCategoryId, priority);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 1,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());
		Assert.assertEquals(
			assetEntryId, assetEntryAssetCategoryRel.getAssetEntryId());
		Assert.assertEquals(
			assetCategoryId, assetEntryAssetCategoryRel.getAssetCategoryId());
		Assert.assertEquals(priority, assetEntryAssetCategoryRel.getPriority());
	}

	@Test
	public void testDeleteAssetEntryAssetCategoryRel() {
		long assetEntryId1 = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId1, assetCategoryId);

		long assetEntryId2 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId2, assetCategoryId);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 2,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());

		_assetEntryAssetCategoryRelLocalService.
			deleteAssetEntryAssetCategoryRel(assetEntryId1, assetCategoryId);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 1,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());

		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetCategoryId(
					assetCategoryId, 0, 1);

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			assetEntryAssetCategoryRels.get(0);

		Assert.assertEquals(
			assetEntryId2, assetEntryAssetCategoryRel.getAssetEntryId());
		Assert.assertEquals(
			assetCategoryId, assetEntryAssetCategoryRel.getAssetCategoryId());
		Assert.assertEquals(0, assetEntryAssetCategoryRel.getPriority());
	}

	@Test
	public void testDeleteAssetEntryAssetCategoryRelByAssetCategoryId() {
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(
			RandomTestUtil.randomLong(), assetCategoryId);
		_addAssetEntryAssetCategoryRel(
			RandomTestUtil.randomLong(), assetCategoryId);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 2,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());

		_assetEntryAssetCategoryRelLocalService.
			deleteAssetEntryAssetCategoryRelByAssetCategoryId(assetCategoryId);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());
	}

	@Test
	public void testDeleteAssetEntryAssetCategoryRelByAssetEntryId() {
		long assetEntryId1 = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId1, assetCategoryId);

		long assetEntryId2 = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId2, assetCategoryId);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 2,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());

		_assetEntryAssetCategoryRelLocalService.
			deleteAssetEntryAssetCategoryRelByAssetEntryId(assetEntryId1);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 1,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());

		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetCategoryId(
					assetCategoryId, 0, 1);

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			assetEntryAssetCategoryRels.get(0);

		Assert.assertEquals(
			assetEntryId2, assetEntryAssetCategoryRel.getAssetEntryId());
		Assert.assertEquals(
			assetCategoryId, assetEntryAssetCategoryRel.getAssetCategoryId());
		Assert.assertEquals(0, assetEntryAssetCategoryRel.getPriority());
	}

	@Test
	public void testFetchAssetEntryAssetCategoryRel() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			assetEntryId, assetCategoryId);

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			_assetEntryAssetCategoryRelLocalService.
				fetchAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId);

		_assetEntryAssetCategoryRels.add(assetEntryAssetCategoryRel);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 1,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());
		Assert.assertEquals(
			assetEntryId, assetEntryAssetCategoryRel.getAssetEntryId());
		Assert.assertEquals(
			assetCategoryId, assetEntryAssetCategoryRel.getAssetCategoryId());
		Assert.assertEquals(0, assetEntryAssetCategoryRel.getPriority());
	}

	@Test
	public void testFetchAssetEntryAssetCategoryRelWithInvalidAssetEntryId() {
		Assert.assertNull(
			_assetEntryAssetCategoryRelLocalService.
				fetchAssetEntryAssetCategoryRel(
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong()));
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

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 1,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());

		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetCategoryId(
					assetCategoryId, 0, 1);

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			assetEntryAssetCategoryRels.get(0);

		Assert.assertEquals(
			assetEntryId, assetEntryAssetCategoryRel.getAssetEntryId());
		Assert.assertEquals(
			assetCategoryId, assetEntryAssetCategoryRel.getAssetCategoryId());
		Assert.assertEquals(0, assetEntryAssetCategoryRel.getPriority());
	}

	@Test
	public void testGetAssetEntryAssetCategoryRelsByAssetEntryId() {
		long assetEntryId = RandomTestUtil.randomLong();
		long assetCategoryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId);

		Assert.assertEquals(
			_initialAssetEntryAssetCategoryRelsCount + 1,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount());

		List<AssetEntryAssetCategoryRel> assetEntryAssetCategoryRels =
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsByAssetEntryId(
					assetEntryId, 0, 1);

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			assetEntryAssetCategoryRels.get(0);

		Assert.assertEquals(
			assetEntryId, assetEntryAssetCategoryRel.getAssetEntryId());
		Assert.assertEquals(
			assetCategoryId, assetEntryAssetCategoryRel.getAssetCategoryId());
		Assert.assertEquals(0, assetEntryAssetCategoryRel.getPriority());
	}

	@Test
	public void testGetAssetEntryAssetCategoryRelsCount() {
		long assetEntryId = RandomTestUtil.randomLong();

		_addAssetEntryAssetCategoryRel(
			assetEntryId, RandomTestUtil.randomLong());

		_addAssetEntryAssetCategoryRel(
			assetEntryId, RandomTestUtil.randomLong());

		Assert.assertEquals(
			2,
			_assetEntryAssetCategoryRelLocalService.
				getAssetEntryAssetCategoryRelsCount(assetEntryId));
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

	private AssetEntryAssetCategoryRel _addAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId) {

		return _addAssetEntryAssetCategoryRel(assetEntryId, assetCategoryId, 0);
	}

	private AssetEntryAssetCategoryRel _addAssetEntryAssetCategoryRel(
		long assetEntryId, long assetCategoryId, int priority) {

		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel =
			_assetEntryAssetCategoryRelLocalService.
				addAssetEntryAssetCategoryRel(
					assetEntryId, assetCategoryId, priority);

		_assetEntryAssetCategoryRels.add(assetEntryAssetCategoryRel);

		return assetEntryAssetCategoryRel;
	}

	@Inject
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@DeleteAfterTestRun
	private final List<AssetEntryAssetCategoryRel>
		_assetEntryAssetCategoryRels = new ArrayList<>();

	private int _initialAssetEntryAssetCategoryRelsCount;

}