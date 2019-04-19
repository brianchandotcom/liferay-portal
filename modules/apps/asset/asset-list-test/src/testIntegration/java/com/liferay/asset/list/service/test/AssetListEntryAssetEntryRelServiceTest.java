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
import com.liferay.asset.list.exception.AssetListEntryAssetEntryRelPostionException;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelUtil;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.asset.test.util.asset.renderer.factory.TestAssetRendererFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.test.rule.Inject;
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

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				RandomTestUtil.nextLong());

		AssetListEntryAssetEntryRel persistedAssetListEntryRel =
			AssetListEntryAssetEntryRelUtil.findByUUID_G(
				assetListEntryRel.getUuid(), assetListEntryRel.getGroupId());

		_assertSameAssetListEntryAssetEntryRel(
			assetListEntryRel, persistedAssetListEntryRel);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			assetListEntryRel.getAssetListEntryId());

		Assert.assertEquals(
			assetEntry.getEntryId(), assetListEntryRel.getAssetEntryId());
	}

	@Test
	public void testAddAssetListEntryAssetEntryRelToPosition()
		throws Exception {

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				segmentsEntryId, 1);

		AssetListEntryAssetEntryRel persistedAssetListEntryRel =
			AssetListEntryAssetEntryRelUtil.findByA_S_P(
				assetListEntryRel.getAssetListEntryId(), segmentsEntryId, 1);

		_assertSameAssetListEntryAssetEntryRel(
			assetListEntryRel, persistedAssetListEntryRel);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			assetListEntryRel.getAssetListEntryId());

		Assert.assertEquals(
			assetEntry.getEntryId(), assetListEntryRel.getAssetEntryId());
	}

	@Test(expected = AssetListEntryAssetEntryRelPostionException.class)
	public void testAddAssetListEntryAssetEntryRelWrongPosition()
		throws Exception {

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId());

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		long segmentsEntryId = RandomTestUtil.nextLong();

		_assetListEntryAssetEntryRelLocalService.addAssetListEntryAssetEntryRel(
			assetListEntry.getAssetListEntryId(), segmentsEntryId,
			assetEntry.getEntryId(), 1, serviceContext);

		_assetListEntryAssetEntryRelLocalService.addAssetListEntryAssetEntryRel(
			assetListEntry.getAssetListEntryId(), segmentsEntryId,
			assetEntry.getEntryId(), serviceContext);
	}

	@Test
	public void testCountAssetListEntryAssetEntryRel() throws Exception {
		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		int originalAssetListEntryAssetEntryRelCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					assetListEntry.getAssetListEntryId());

		Assert.assertEquals(0, originalAssetListEntryAssetEntryRelCount);

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntry,
			RandomTestUtil.nextLong());

		int actualAssetListEntryAssetEntryRelCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					assetListEntry.getAssetListEntryId());

		Assert.assertEquals(
			originalAssetListEntryAssetEntryRelCount + 1,
			actualAssetListEntryAssetEntryRelCount);
	}

	@Test
	public void testDeleteAssetListEntryAssetEntryRelByAssetListEntryId()
		throws Exception {

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntry,
			RandomTestUtil.nextLong());

		_assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRelByAssetListEntryId(
				assetListEntry.getAssetListEntryId());

		Assert.assertNull(
			AssetListEntryAssetEntryRelUtil.fetchByAssetListEntryId_First(
				assetListEntry.getAssetListEntryId(), null));
	}

	@Test
	public void testDeleteAssetListEntryAssetEntryRelByPosition()
		throws Exception {

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				segmentsEntryId, 1);

		_assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRel(
				assetListEntry.getAssetListEntryId(), segmentsEntryId, 1);

		Assert.assertNull(
			AssetListEntryAssetEntryRelUtil.fetchByUUID_G(
				assetListEntryRel.getUuid(), assetListEntryRel.getGroupId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels() throws Exception {
		AssetEntry assetEntry1 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetEntry assetEntry2 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry1, assetListEntry, segmentsEntryId);

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry2, assetListEntry, segmentsEntryId);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
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

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry, assetListEntry,
				segmentsEntryId);

		int currentPosition = assetListEntryRel.getPosition();

		AssetListEntryAssetEntryRel assetListEntryRelNegativePosition =
			_assetListEntryAssetEntryRelLocalService.
				moveAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(), segmentsEntryId,
					currentPosition, -1);

		Assert.assertEquals(
			assetListEntryRel.getPosition(),
			assetListEntryRelNegativePosition.getPosition());

		int highIndex = AssetListEntryAssetEntryRelUtil.countByAssetListEntryId(
			assetListEntry.getAssetListEntryId());

		AssetListEntryAssetEntryRel assetListEntryRelHighIndexPosition =
			_assetListEntryAssetEntryRelLocalService.
				moveAssetListEntryAssetEntryRel(
					assetListEntry.getAssetListEntryId(), segmentsEntryId,
					currentPosition, highIndex);

		Assert.assertEquals(
			assetListEntryRel.getPosition(),
			assetListEntryRelHighIndexPosition.getPosition());
	}

	@Test
	public void testUpdateAssetListEntryAssetEntryRel() throws Exception {
		AssetEntry assetEntry1 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry1 = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		long segmentsEntryId = RandomTestUtil.nextLong();

		AssetListEntryAssetEntryRel assetListEntryRel =
			AssetListTestUtil.addAssetListEntryAssetEntryRel(
				_group.getGroupId(), assetEntry1, assetListEntry1,
				segmentsEntryId);

		int position = assetListEntryRel.getPosition();

		AssetEntry assetEntry2 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListEntry assetListEntry2 = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		_assetListEntryAssetEntryRelLocalService.
			updateAssetListEntryAssetEntryRel(
				assetListEntryRel.getAssetListEntryAssetEntryRelId(),
				assetListEntry2.getAssetListEntryId(), assetEntry2.getEntryId(),
				segmentsEntryId, position + 1);

		assetListEntryRel = AssetListEntryAssetEntryRelUtil.findByPrimaryKey(
			assetListEntryRel.getAssetListEntryAssetEntryRelId());

		Assert.assertEquals(
			assetListEntry2.getAssetListEntryId(),
			assetListEntryRel.getAssetListEntryId());
		Assert.assertEquals(
			assetEntry2.getEntryId(), assetListEntryRel.getAssetEntryId());
		Assert.assertEquals(assetListEntryRel.getPosition(), position + 1);
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

	@Inject
	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

	@DeleteAfterTestRun
	private Group _group;

}