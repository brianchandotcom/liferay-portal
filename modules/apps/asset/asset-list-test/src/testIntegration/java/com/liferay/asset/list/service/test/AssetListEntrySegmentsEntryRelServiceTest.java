/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.asset.list.service.persistence.AssetListEntryAssetEntryRelUtil;
import com.liferay.asset.list.service.persistence.AssetListEntrySegmentsEntryRelUtil;
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
 * @author Yurena Cabrera
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

		Assert.assertEquals(
			3,
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRelsCount(
					assetListEntry.getAssetListEntryId()));
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
	public void testDeleteAssetListEntrySegmentsEntryRelWithAssetListEntryAssetEntryRels()
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		AssetEntry assetEntry = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), assetEntry, assetListEntry,
			assetListEntrySegmentsEntryRel.getSegmentsEntryId());

		_assetListEntrySegmentsEntryRelLocalService.
			deleteAssetListEntrySegmentsEntryRel(
				assetListEntrySegmentsEntryRel);

		Assert.assertNull(
			AssetListEntryAssetEntryRelUtil.fetchByAssetListEntryId_First(
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
	public void testNewVariationCreationAssignTheRightPriorityWithFF()
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel1 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel2 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel3 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel4 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		Assert.assertEquals(1, assetListEntrySegmentsEntryRel1.getPriority());
		Assert.assertEquals(2, assetListEntrySegmentsEntryRel2.getPriority());
		Assert.assertEquals(3, assetListEntrySegmentsEntryRel3.getPriority());
		Assert.assertEquals(4, assetListEntrySegmentsEntryRel4.getPriority());
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

	@Test
	public void testUpdateVariations() throws Exception {
		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel1 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel2 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);
		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel3 =
			AssetListTestUtil.addAssetListEntrySegmentsEntryRel(
				_group.getGroupId(), assetListEntry);

		long[] priorities = {
			assetListEntrySegmentsEntryRel3.
				getAssetListEntrySegmentsEntryRelId(),
			assetListEntrySegmentsEntryRel1.
				getAssetListEntrySegmentsEntryRelId(),
			assetListEntrySegmentsEntryRel2.
				getAssetListEntrySegmentsEntryRelId()
		};

		_assetListEntrySegmentsEntryRelLocalService.updateVariationsPriority(
			priorities);

		AssetListEntrySegmentsEntryRel updatedAssetListEntrySegmentsEntryRel1 =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRel(
					assetListEntrySegmentsEntryRel1.
						getAssetListEntrySegmentsEntryRelId());

		AssetListEntrySegmentsEntryRel updatedAssetListEntrySegmentsEntryRel2 =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRel(
					assetListEntrySegmentsEntryRel2.
						getAssetListEntrySegmentsEntryRelId());

		AssetListEntrySegmentsEntryRel updatedAssetListEntrySegmentsEntryRel3 =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRel(
					assetListEntrySegmentsEntryRel3.
						getAssetListEntrySegmentsEntryRelId());

		Assert.assertEquals(
			1, updatedAssetListEntrySegmentsEntryRel1.getPriority());
		Assert.assertEquals(
			2, updatedAssetListEntrySegmentsEntryRel2.getPriority());
		Assert.assertEquals(
			0, updatedAssetListEntrySegmentsEntryRel3.getPriority());
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