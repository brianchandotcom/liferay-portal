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
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.list.service.persistence.AssetListEntrySegmentsEntryRelUtil;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
 * @author Eduardo García
 */
@RunWith(Arquillian.class)
public class AssetListEntrySegmentsEntryRelServiceTest {

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
	public void testAddAssetListEntrySegmentsEntryRel() throws Exception {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRelLocal =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRelDatabase =
			AssetListEntrySegmentsEntryRelUtil.findByUUID_G(
				assetListEntrySegmentsEntryRelLocal.getUuid(),
				assetListEntrySegmentsEntryRelLocal.getGroupId());

		_assertSameAssetListEntrySegmentsEntryRel(
			assetListEntrySegmentsEntryRelLocal,
			assetListEntrySegmentsEntryRelDatabase);

		Assert.assertEquals(
			assetListEntry.getAssetListEntryId(),
			assetListEntrySegmentsEntryRelLocal.getAssetListEntryId());
	}

	@Test
	public void testCountAssetListEntrySegmentsEntryRel() throws Exception {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry);
		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry);

		int assetListEntrySegmentsEntryRelsCount =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRelsCount(
					assetListEntry.getAssetListEntryId());

		Assert.assertEquals(3, assetListEntrySegmentsEntryRelsCount);
	}

	@Test
	public void testDeleteAssetListEntrySegmentsEntryRelByAssetListEntryId()
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
			_group.getGroupId(), assetListEntry);

		_assetListEntrySegmentsEntryRelLocalService.
			deleteAssetListEntrySegmentsEntryRelByAssetListEntryId(
				assetListEntry.getAssetListEntryId());

		Assert.assertNull(
			AssetListEntrySegmentsEntryRelUtil.fetchByAssetListEntryId_First(
				assetListEntry.getAssetListEntryId(), null));
	}

	@Test
	public void testGetAssetListEntrySegmentsEntryRels() throws Exception {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel1 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel2 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		List<AssetListEntrySegmentsEntryRel>
			assetListEntrySegmentsEntryRelList =
				_assetListEntrySegmentsEntryRelLocalService.
					getAssetListEntrySegmentsEntryRels(
						assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS);

		Assert.assertTrue(
			assetListEntrySegmentsEntryRelList.contains(
				assetListEntrySegmentsEntryRel1));
		Assert.assertTrue(
			assetListEntrySegmentsEntryRelList.contains(
				assetListEntrySegmentsEntryRel2));
	}

	@Test
	public void testUpdateAssetListEntrySegmentsEntryRelTypeSettings()
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		String typeSettingsUpdated = RandomTestUtil.randomString();

		_assetListEntrySegmentsEntryRelLocalService.
			updateAssetListEntrySegmentsEntryRelTypeSettings(
				assetListEntrySegmentsEntryRel.getAssetListEntryId(),
				assetListEntrySegmentsEntryRel.getSegmentsEntryId(),
				typeSettingsUpdated);

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRelUpdated =
			AssetListEntrySegmentsEntryRelUtil.findByPrimaryKey(
				assetListEntrySegmentsEntryRel.
					getAssetListEntrySegmentsEntryRelId());

		Assert.assertEquals(
			assetListEntrySegmentsEntryRelUpdated.getTypeSettings(),
			typeSettingsUpdated);
	}

	private void _assertSameAssetListEntrySegmentsEntryRel(
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel1,
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel2) {

		Assert.assertEquals(
			assetListEntrySegmentsEntryRel1.getAssetListEntryId(),
			assetListEntrySegmentsEntryRel2.getAssetListEntryId());
		Assert.assertEquals(
			assetListEntrySegmentsEntryRel1.
				getAssetListEntrySegmentsEntryRelId(),
			assetListEntrySegmentsEntryRel2.
				getAssetListEntrySegmentsEntryRelId());
		Assert.assertEquals(
			assetListEntrySegmentsEntryRel1.getSegmentsEntryId(),
			assetListEntrySegmentsEntryRel2.getSegmentsEntryId());
		Assert.assertEquals(
			assetListEntrySegmentsEntryRel1.getTypeSettings(),
			assetListEntrySegmentsEntryRel2.getTypeSettings());
		Assert.assertEquals(
			assetListEntrySegmentsEntryRel1.getUuid(),
			assetListEntrySegmentsEntryRel2.getUuid());
	}

	@Inject
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@DeleteAfterTestRun
	private Group _group;

}