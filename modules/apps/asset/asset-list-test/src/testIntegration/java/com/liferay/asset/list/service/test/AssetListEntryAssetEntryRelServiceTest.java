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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
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
			PermissionCheckerTestRule.INSTANCE, PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(
				Propagation.REQUIRED, "com.liferay.asset.list.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddAssetListEntryAssetEntryRel() throws PortalException {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListEntryAssetEntryRel assetListEntryRelLocal =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry);

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
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		int defaultPosition = 1;

		AssetListEntryAssetEntryRel assetListEntryRelLocal =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry, 1);

		AssetListEntryAssetEntryRel assetListEntryRelDatabase =
			AssetListEntryAssetEntryRelUtil.findByA_P(
				assetListEntryRelLocal.getAssetListEntryId(), defaultPosition);

		_assertSameAssetListEntryAssetEntryRel(
			assetListEntryRelLocal, assetListEntryRelDatabase);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			assetListEntryRelLocal.getAssetListEntryId());

		Assert.assertEquals(
			assetEntry.getEntryId(), assetListEntryRelLocal.getAssetEntryId());
	}

	@Test
	public void testCountAssetListEntryAssetEntryRel() throws PortalException {
		AssetListEntry assetListEntryCount =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetListEntry assetListEntryOther =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntry = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		int currentCount =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(0, currentCount);

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntryOther);

		currentCount =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(0, currentCount);

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntryCount);

		currentCount =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(1, currentCount);
	}

	@Test
	public void testDeleteAssetListEntryAssetEntryRelByAssetListEntryId()
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntry);

		AssetListEntryAssetEntryRelLocalServiceUtil.
			deleteAssetListEntryAssetEntryRelByAssetListEntryId(
				assetListEntry.getAssetListEntryId());

		Assert.assertNull(
			AssetListEntryAssetEntryRelUtil.fetchByAssetListEntryId_First(
				assetListEntry.getAssetListEntryId(), null));
	}

	@Test
	public void testDeleteAssetListEntryAssetEntryRelByPosition()
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntryAlive = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetEntry assetEntryDeleted = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListEntryAssetEntryRel assetListEntryRelAlive =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntryAlive, assetListEntry, 1);

		AssetListEntryAssetEntryRel assetListEntryRelDeleted =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntryDeleted, assetListEntry, 0);

		AssetListEntryAssetEntryRelLocalServiceUtil.
			deleteAssetListEntryAssetEntryRel(
				assetListEntry.getAssetListEntryId(), 0);

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
	public void testGetAssetListEntryAssetEntryRels() throws PortalException {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry1 = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetEntry assetEntry2 = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry1, assetListEntry);

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry2, assetListEntry);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				getAssetListEntryAssetEntryRels(
					assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.get(0).getAssetEntryId(),
			assetEntry1.getEntryId());

		Assert.assertEquals(
			assetListEntryRelList.get(1).getAssetEntryId(),
			assetEntry2.getEntryId());
	}

	@Test
	public void testMoveAssetListEntryAssetEntryRelToInvalidPosition()
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry);

		int currentPosition = assetListEntryRel.getPosition();

		AssetListEntryAssetEntryRel assetListEntryRelNegativePosition =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				moveAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(), currentPosition, -1);

		Assert.assertEquals(
			assetListEntryRel.getPosition(),
			assetListEntryRelNegativePosition.getPosition());

		int highIndex = AssetListEntryAssetEntryRelUtil.countByAssetListEntryId(
			assetListEntry.getAssetListEntryId());

		AssetListEntryAssetEntryRel assetListEntryRelHighIndexPosition =
			AssetListEntryAssetEntryRelLocalServiceUtil.
				moveAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(), currentPosition,
					highIndex);

		Assert.assertEquals(
			assetListEntryRel.getPosition(),
			assetListEntryRelHighIndexPosition.getPosition());
	}

	@Test
	public void testMoveAssetListEntryAssetEntryRelToOccupiedPosition()
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListEntryAssetEntryRel assetListEntryRelOriginal =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry);

		int originalPosition = assetListEntryRelOriginal.getPosition();

		AssetListEntryAssetEntryRel assetListEntryRelOccupied =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry);

		int occupiedPosition = assetListEntryRelOccupied.getPosition();

		AssetListEntryAssetEntryRelLocalServiceUtil.
			moveAssetListEntryAssetEntryRel(
				assetListEntry.getAssetListEntryId(), originalPosition,
				occupiedPosition);

		assetListEntryRelOriginal =
			AssetListEntryAssetEntryRelUtil.findByPrimaryKey(
				assetListEntryRelOriginal.getAssetListEntryAssetEntryRelId());

		assetListEntryRelOccupied =
			AssetListEntryAssetEntryRelUtil.findByPrimaryKey(
				assetListEntryRelOccupied.getAssetListEntryAssetEntryRelId());

		//assert assetListEntryRels swap positions

		Assert.assertEquals(
			originalPosition, assetListEntryRelOccupied.getPosition());

		Assert.assertEquals(
			occupiedPosition, assetListEntryRelOriginal.getPosition());
	}

	@Test
	public void testUpdateAssetListEntryAssetEntryRel() throws PortalException {
		AssetListEntry assetListEntryOriginal =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntryOriginal = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntryOriginal,
				assetListEntryOriginal);

		int positionOriginal = assetListEntryRel.getPosition();

		AssetListEntry assetListEntryUpdated =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntryUpdated = AssetListTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListEntryAssetEntryRelLocalServiceUtil.
			updateAssetListEntryAssetEntryRel(
				assetListEntryRel.getAssetListEntryAssetEntryRelId(),
				assetListEntryUpdated.getAssetListEntryId(),
				assetEntryUpdated.getEntryId(), positionOriginal + 1);

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
			AssetListEntryAssetEntryRel assetListEntryRel2)
		throws PortalException {

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
	}

	@DeleteAfterTestRun
	private Group _group;

}