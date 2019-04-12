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

package com.liferay.asset.list.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelUtil;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.asset.test.util.asset.renderer.factory.TestAssetRendererFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Kyle Miho
 */
@RunWith(Arquillian.class)
public class AssetListEntryAssetEntryRelServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.asset.list.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddAssetListEntryAssetEntryRel() throws Exception {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntryAssetEntryRel assetListEntryRelLocal =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				RandomTestUtil.nextLong());

		AssetListEntryAssetEntryRel assetListEntryRelDatabase =
			AssetListEntryAssetEntryRelUtil.findByUUID_G(
				assetListEntryRelLocal.getUuid(),
				assetListEntryRelLocal.getGroupId());

		_assertSameAssetListEntryAssetEntryRel(
			assetListEntryRelLocal, assetListEntryRelDatabase);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			assetListEntryRelLocal.getAssetListEntryId());
		Assert.assertEquals(
			assetEntry.getEntryId(), assetListEntryRelLocal.getAssetEntryId());
	}

	@Test
	public void testAddAssetListEntryAssetEntryRelToPosition()
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		int defaultPosition = 1;

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryRelLocal =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				segmentsEntryId, 1);

		AssetListEntryAssetEntryRel assetListEntryRelDatabase =
			AssetListEntryAssetEntryRelUtil.findByA_S_P(
				assetListEntryRelLocal.getAssetListEntryId(), segmentsEntryId,
				defaultPosition);

		_assertSameAssetListEntryAssetEntryRel(
			assetListEntryRelLocal, assetListEntryRelDatabase);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			assetListEntryRelLocal.getAssetListEntryId());
		Assert.assertEquals(
			assetEntry.getEntryId(), assetListEntryRelLocal.getAssetEntryId());
	}

	@Test
	public void testCountAssetListEntryAssetEntryRel() throws Exception {
		AssetListEntry assetListEntryCount =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetListEntry assetListEntryOther =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		int currentCount =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(0, currentCount);

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntryOther,
			RandomTestUtil.nextLong());

		currentCount =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(0, currentCount);

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntryCount,
			RandomTestUtil.nextLong());

		currentCount =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(1, currentCount);
	}

	@Test
	public void testDeleteAssetListEntryAssetEntryRelByAssetListEntryId()
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntry,
			RandomTestUtil.nextLong());

		AssetListEntryAssetEntryRelLocalServiceUtil.
			deleteAssetListEntryAssetEntryRelByAssetListEntryId(
				assetListEntry.getAssetListEntryId());

		Assert.assertNull(
			AssetListEntryAssetEntryRelUtil.fetchByAssetListEntryId_First(
				assetListEntry.getAssetListEntryId(), null));
	}

	@Test
	public void testDeleteAssetListEntryAssetEntryRelByPosition()
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntryAlive = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());
		AssetEntry assetEntryDeleted = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryRelAlive =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntryAlive, assetListEntry,
				segmentsEntryId, 1);
		AssetListEntryAssetEntryRel assetListEntryRelDeleted =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntryDeleted, assetListEntry,
				segmentsEntryId, 0);

		AssetListEntryAssetEntryRelLocalServiceUtil.
			deleteAssetListEntryAssetEntryRel(
				assetListEntry.getAssetListEntryId(), segmentsEntryId, 0);

		Assert.assertEquals(
			assetListEntryRelAlive,
			AssetListEntryAssetEntryRelUtil.fetchByUUID_G(
				assetListEntryRelAlive.getUuid(),
				assetListEntryRelAlive.getGroupId()));
		Assert.assertNull(
			AssetListEntryAssetEntryRelUtil.fetchByUUID_G(
				assetListEntryRelDeleted.getUuid(),
				assetListEntryRelDeleted.getGroupId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels() throws Exception {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry1 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());
		AssetEntry assetEntry2 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry1, assetListEntry, segmentsEntryId);
		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry2, assetListEntry, segmentsEntryId);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRels(
					assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			assetListEntryRelList.get(0);

		Assert.assertEquals(
			assetListEntryAssetEntryRel.getAssetEntryId(),
			assetEntry1.getEntryId());

		assetListEntryAssetEntryRel = assetListEntryRelList.get(1);

		Assert.assertEquals(
			assetListEntryAssetEntryRel.getAssetEntryId(),
			assetEntry2.getEntryId());
	}

	@Test
	public void testMoveAssetListEntryAssetEntryRelToInvalidPosition()
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				segmentsEntryId);

		int currentPosition = assetListEntryRel.getPosition();

		AssetListEntryAssetEntryRel assetListEntryRelNegativePosition =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				moveAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(), segmentsEntryId,
					currentPosition, -1);

		Assert.assertEquals(
			assetListEntryRel.getPosition(),
			assetListEntryRelNegativePosition.getPosition());

		int highIndex = AssetListEntryAssetEntryRelUtil.countByAssetListEntryId(
			assetListEntry.getAssetListEntryId());

		AssetListEntryAssetEntryRel assetListEntryRelHighIndexPosition =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				moveAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(), segmentsEntryId,
					currentPosition, highIndex);

		Assert.assertEquals(
			assetListEntryRel.getPosition(),
			assetListEntryRelHighIndexPosition.getPosition());
	}

	@Test
	public void testUpdateAssetListEntryAssetEntryRel() throws Exception {
		AssetListEntry assetListEntryOriginal =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntryOriginal = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntryOriginal, assetListEntryOriginal,
				segmentsEntryId);

		int positionOriginal = assetListEntryRel.getPosition();

		AssetListEntry assetListEntryUpdated =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntryUpdated = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntryAssetEntryRelLocalServiceUtil.
			updateAssetListEntryAssetEntryRel(
				assetListEntryRel.getAssetListEntryAssetEntryRelId(),
				assetListEntryUpdated.getAssetListEntryId(),
				assetEntryUpdated.getEntryId(), segmentsEntryId,
				positionOriginal + 1);

		AssetListEntryAssetEntryRel assetListEntryRelUpdated =
			AssetListEntryAssetEntryRelUtil.findByPrimaryKey(
				assetListEntryRel.getAssetListEntryAssetEntryRelId());

		Assert.assertEquals(
			assetListEntryUpdated.getAssetListEntryId(),
			assetListEntryRelUpdated.getAssetListEntryId());
		Assert.assertEquals(
			assetEntryUpdated.getEntryId(),
			assetListEntryRelUpdated.getAssetEntryId());
		Assert.assertEquals(
			assetListEntryRelUpdated.getPosition(), positionOriginal + 1);
	}

	private void _assertSameAssetListEntryAssetEntryRel(
		AssetListEntryAssetEntryRel assetListEntryRel1,
		AssetListEntryAssetEntryRel assetListEntryRel2) {

		Assert.assertEquals(
			assetListEntryRel1.getAssetEntryId(),
			assetListEntryRel2.getAssetEntryId());
		Assert.assertEquals(
			assetListEntryRel1.getAssetListEntryId(),
			assetListEntryRel2.getAssetListEntryId());
		Assert.assertEquals(
			assetListEntryRel1.getAssetListEntryAssetEntryRelId(),
			assetListEntryRel2.getAssetListEntryAssetEntryRelId());
		Assert.assertEquals(
			assetListEntryRel1.getUuid(), assetListEntryRel2.getUuid());
		Assert.assertEquals(
			assetListEntryRel1.getPosition(), assetListEntryRel2.getPosition());
		Assert.assertEquals(
			assetListEntryRel1.getSegmentsEntryId(),
			assetListEntryRel2.getSegmentsEntryId());
	}

	@DeleteAfterTestRun
	private Group _group;

}