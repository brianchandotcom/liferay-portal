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
import com.liferay.asset.list.model.AssetEntryAssetListEntryRel;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetEntryAssetListEntryRelLocalServiceUtil;
import com.liferay.asset.list.service.persistence.AssetEntryAssetListEntryRelUtil;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.asset.test.util.asset.renderer.factory.TestAssetRendererFactory;
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
public class AssetEntryAssetListEntryRelServiceTest {

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
	public void testAddAssetEntryAssetListEntryRel() throws PortalException {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRelLocal =
			AssetListTestUtil.addAssetEntryAssetListEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry);

		AssetEntryAssetListEntryRel assetListEntryRelDatabase =
			AssetEntryAssetListEntryRelUtil.findByUUID_G(
				assetEntryAssetListEntryRelLocal.getUuid(),
				assetEntryAssetListEntryRelLocal.getGroupId());

		_assertSameAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRelLocal, assetListEntryRelDatabase);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			assetEntryAssetListEntryRelLocal.getAssetListEntryId());

		Assert.assertEquals(
			assetEntry.getEntryId(),
			assetEntryAssetListEntryRelLocal.getAssetEntryId());
	}

	@Test
	public void testAddAssetEntryAssetListEntryRelToPosition()
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		int defaultPosition = 1;

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRelLocal =
			AssetListTestUtil.addAssetEntryAssetListEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry, 1);

		AssetEntryAssetListEntryRel assetListEntryRelDatabase =
			AssetEntryAssetListEntryRelUtil.findByA_P(
				assetEntryAssetListEntryRelLocal.getAssetListEntryId(),
				defaultPosition);

		_assertSameAssetEntryAssetListEntryRel(
			assetEntryAssetListEntryRelLocal, assetListEntryRelDatabase);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			assetEntryAssetListEntryRelLocal.getAssetListEntryId());

		Assert.assertEquals(
			assetEntry.getEntryId(),
			assetEntryAssetListEntryRelLocal.getAssetEntryId());
	}

	@Test
	public void testCountAssetEntryAssetListEntryRel() throws PortalException {
		AssetListEntry assetListEntryCount =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetListEntry assetListEntryOther =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		int currentCount =
			AssetEntryAssetListEntryRelLocalServiceUtil.
				getAssetEntryAssetListEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(0, currentCount);

		AssetListTestUtil.addAssetEntryAssetListEntryRel(
			_group.getGroupId(), assetEntry, assetListEntryOther);

		currentCount =
			AssetEntryAssetListEntryRelLocalServiceUtil.
				getAssetEntryAssetListEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(0, currentCount);

		AssetListTestUtil.addAssetEntryAssetListEntryRel(
			_group.getGroupId(), assetEntry, assetListEntryCount);

		currentCount =
			AssetEntryAssetListEntryRelLocalServiceUtil.
				getAssetEntryAssetListEntryRelsCount(
					assetListEntryCount.getAssetListEntryId());

		Assert.assertEquals(1, currentCount);
	}

	@Test
	public void testDeleteAssetEntryAssetListEntryRelByAssetListEntryId()
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListTestUtil.addAssetEntryAssetListEntryRel(
			_group.getGroupId(), assetEntry, assetListEntry);

		AssetEntryAssetListEntryRelLocalServiceUtil.
			deleteAssetEntryAssetListEntryRelByAssetListEntryId(
				assetListEntry.getAssetListEntryId());

		Assert.assertNull(
			AssetEntryAssetListEntryRelUtil.fetchByAssetListEntryId_First(
				assetListEntry.getAssetListEntryId(), null));
	}

	@Test
	public void testDeleteAssetEntryAssetListEntryRelByPosition()
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntryAlive = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetEntry assetEntryDeleted = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetEntryAssetListEntryRel assetListEntryRelAlive =
			AssetListTestUtil.addAssetEntryAssetListEntryRel(
				_group.getGroupId(), assetEntryAlive, assetListEntry, 1);

		AssetEntryAssetListEntryRel assetListEntryRelDeleted =
			AssetListTestUtil.addAssetEntryAssetListEntryRel(
				_group.getGroupId(), assetEntryDeleted, assetListEntry, 0);

		AssetEntryAssetListEntryRelLocalServiceUtil.
			deleteAssetEntryAssetListEntryRel(
				assetListEntry.getAssetListEntryId(), 0);

		Assert.assertEquals(
			assetListEntryRelAlive,
			AssetEntryAssetListEntryRelUtil.fetchByUUID_G(
				assetListEntryRelAlive.getUuid(),
				assetListEntryRelAlive.getGroupId()));

		Assert.assertNull(
			AssetEntryAssetListEntryRelUtil.fetchByUUID_G(
				assetListEntryRelDeleted.getUuid(),
				assetListEntryRelDeleted.getGroupId()));
	}

	@Test
	public void testGetAssetEntryAssetListEntryRels() throws PortalException {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry1 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetEntry assetEntry2 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListTestUtil.addAssetEntryAssetListEntryRel(
			_group.getGroupId(), assetEntry1, assetListEntry);

		AssetListTestUtil.addAssetEntryAssetListEntryRel(
			_group.getGroupId(), assetEntry2, assetListEntry);

		List<AssetEntryAssetListEntryRel> assetListEntryRelList =
			AssetEntryAssetListEntryRelLocalServiceUtil.
				getAssetEntryAssetListEntryRels(
					assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			assetListEntryRelList.get(0);

		Assert.assertEquals(
			assetEntryAssetListEntryRel.getAssetEntryId(),
			assetEntry1.getEntryId());

		assetEntryAssetListEntryRel = assetListEntryRelList.get(1);

		Assert.assertEquals(
			assetEntryAssetListEntryRel.getAssetEntryId(),
			assetEntry2.getEntryId());
	}

	@Test
	public void testMoveAssetEntryAssetListEntryRelToInvalidPosition()
		throws PortalException {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetEntryAssetListEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetEntryAssetListEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry);

		int currentPosition = assetListEntryRel.getPosition();

		AssetEntryAssetListEntryRel assetListEntryRelNegativePosition =
			AssetEntryAssetListEntryRelLocalServiceUtil.
				moveAssetEntryAssetListEntryRel(
					assetListEntry.getAssetListEntryId(), currentPosition, -1);

		Assert.assertEquals(
			assetListEntryRel.getPosition(),
			assetListEntryRelNegativePosition.getPosition());

		int highIndex = AssetEntryAssetListEntryRelUtil.countByAssetListEntryId(
			assetListEntry.getAssetListEntryId());

		AssetEntryAssetListEntryRel assetListEntryRelHighIndexPosition =
			AssetEntryAssetListEntryRelLocalServiceUtil.
				moveAssetEntryAssetListEntryRel(
					assetListEntry.getAssetListEntryId(), currentPosition,
					highIndex);

		Assert.assertEquals(
			assetListEntryRel.getPosition(),
			assetListEntryRelHighIndexPosition.getPosition());
	}

	@Test
	public void testUpdateAssetEntryAssetListEntryRel() throws PortalException {
		AssetListEntry assetListEntryOriginal =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntryOriginal = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetEntryAssetListEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetEntryAssetListEntryRel(
				_group.getGroupId(), assetEntryOriginal,
				assetListEntryOriginal);

		int positionOriginal = assetListEntryRel.getPosition();

		AssetListEntry assetListEntryUpdated =
			AssetListTestUtil.addAssetListEntry(_group.getGroupId());

		AssetEntry assetEntryUpdated = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetEntryAssetListEntryRelLocalServiceUtil.
			updateAssetEntryAssetListEntryRel(
				assetListEntryRel.getAssetEntryAssetListEntryRelId(),
				assetListEntryUpdated.getAssetListEntryId(),
				assetEntryUpdated.getEntryId(), positionOriginal + 1);

		AssetEntryAssetListEntryRel assetListEntryRelUpdated =
			AssetEntryAssetListEntryRelUtil.findByPrimaryKey(
				assetListEntryRel.getAssetEntryAssetListEntryRelId());

		Assert.assertEquals(
			assetListEntryUpdated.getAssetListEntryId(),
			assetListEntryRelUpdated.getAssetListEntryId());

		Assert.assertEquals(
			assetEntryUpdated.getEntryId(),
			assetListEntryRelUpdated.getAssetEntryId());

		Assert.assertEquals(
			assetListEntryRelUpdated.getPosition(), positionOriginal + 1);
	}

	private void _assertSameAssetEntryAssetListEntryRel(
		AssetEntryAssetListEntryRel assetListEntryRel1,
		AssetEntryAssetListEntryRel assetListEntryRel2) {

		Assert.assertEquals(
			assetListEntryRel1.getAssetEntryId(),
			assetListEntryRel2.getAssetEntryId());

		Assert.assertEquals(
			assetListEntryRel1.getAssetListEntryId(),
			assetListEntryRel2.getAssetListEntryId());

		Assert.assertEquals(
			assetListEntryRel1.getAssetEntryAssetListEntryRelId(),
			assetListEntryRel2.getAssetEntryAssetListEntryRelId());

		Assert.assertEquals(
			assetListEntryRel1.getUuid(), assetListEntryRel2.getUuid());

		Assert.assertEquals(
			assetListEntryRel1.getPosition(), assetListEntryRel2.getPosition());
	}

	@DeleteAfterTestRun
	private Group _group;

}