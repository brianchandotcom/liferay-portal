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
import com.liferay.asset.entry.rel.service.AssetEntryAssetCategoryRelLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRelModel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.asset.list.util.AssetListTestUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.asset.test.util.asset.renderer.factory.TestAssetRendererFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class AssetListEntryAssetEntryRelServiceAssetCategoriesTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		AssetVocabulary vocabulary1 = AssetTestUtil.addVocabulary(
			_group.getGroupId());
		AssetVocabulary vocabulary2 = AssetTestUtil.addVocabulary(
			_group.getGroupId());
		AssetVocabulary vocabulary3 = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		_assetVocabulary1AssetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());
		_assetVocabulary1AssetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());
		_assetVocabulary1AssetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());
		_assetVocabulary1AssetCategory4 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());
		_assetVocabulary1AssetCategory5 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary1.getVocabularyId());

		_assetVocabulary2AssetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());
		_assetVocabulary2AssetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());
		_assetVocabulary2AssetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());
		_assetVocabulary2AssetCategory4 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());

		_assetVocabulary3AssetCategory1 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary3.getVocabularyId());
		_assetVocabulary3AssetCategory2 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary3.getVocabularyId());
		_assetVocabulary3AssetCategory3 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary3.getVocabularyId());
		_assetVocabulary3AssetCategory4 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary3.getVocabularyId());

		_assetEntry1 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());
		_assetEntry2 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());
		_assetEntry3 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());
		_assetEntry4 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());
		_assetEntry5 = AssetTestUtil.addAssetEntry(
			_group.getGroupId(), null,
			TestAssetRendererFactory.class.getName());

		_assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId());

		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), _assetEntry1, _assetListEntry, 0);
		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), _assetEntry2, _assetListEntry, 0);
		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), _assetEntry3, _assetListEntry, 0);
		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), _assetEntry4, _assetListEntry, 0);
		AssetListTestUtil.addAssetListEntryAssetEntryRel(
			_group.getGroupId(), _assetEntry5, _assetListEntry, 0);

		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry1.getEntryId(),
			_assetVocabulary1AssetCategory1.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry1.getEntryId(),
			_assetVocabulary1AssetCategory2.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry1.getEntryId(),
			_assetVocabulary2AssetCategory1.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry1.getEntryId(),
			_assetVocabulary2AssetCategory2.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry1.getEntryId(),
			_assetVocabulary3AssetCategory1.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry1.getEntryId(),
			_assetVocabulary3AssetCategory2.getCategoryId(), 0);

		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry2.getEntryId(),
			_assetVocabulary1AssetCategory2.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry2.getEntryId(),
			_assetVocabulary1AssetCategory3.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry2.getEntryId(),
			_assetVocabulary2AssetCategory2.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry2.getEntryId(),
			_assetVocabulary2AssetCategory3.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry2.getEntryId(),
			_assetVocabulary3AssetCategory2.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry2.getEntryId(),
			_assetVocabulary3AssetCategory3.getCategoryId(), 0);

		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry3.getEntryId(),
			_assetVocabulary1AssetCategory4.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry3.getEntryId(),
			_assetVocabulary2AssetCategory4.getCategoryId(), 0);
		_assetEntryAssetCategoryRelLocalService.addAssetEntryAssetCategoryRel(
			_assetEntry3.getEntryId(),
			_assetVocabulary3AssetCategory4.getCategoryId(), 0);
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels0AssetVocabularies5Results() {
		long[][] assetCategoryIds = {};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(5, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 5, assetListEntryRelList.size());

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryRelList.stream();

		List<Long> assetEntryIds = stream.map(
			AssetListEntryAssetEntryRelModel::getAssetEntryId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(assetEntryIds.contains(_assetEntry1.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry2.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry3.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry4.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry5.getEntryId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels1AssetVocabulary0Results() {
		long[][] assetCategoryIds = {
			{_assetVocabulary1AssetCategory5.getCategoryId()}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(0, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 0, assetListEntryRelList.size());
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels1AssetVocabulary1Result() {
		long[][] assetCategoryIds = {
			{_assetVocabulary1AssetCategory4.getCategoryId()}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(1, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 1, assetListEntryRelList.size());

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryRelList.stream();

		List<Long> assetEntryIds = stream.map(
			AssetListEntryAssetEntryRelModel::getAssetEntryId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(assetEntryIds.contains(_assetEntry3.getEntryId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels1AssetVocabulary2Results() {
		long[][] assetCategoryIds = {
			{
				_assetVocabulary1AssetCategory1.getCategoryId(),
				_assetVocabulary1AssetCategory3.getCategoryId()
			}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(2, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 2, assetListEntryRelList.size());

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryRelList.stream();

		List<Long> assetEntryIds = stream.map(
			AssetListEntryAssetEntryRelModel::getAssetEntryId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(assetEntryIds.contains(_assetEntry1.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry2.getEntryId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels2AssetVocabularies0Results() {
		long[][] assetCategoryIds = {
			{_assetVocabulary1AssetCategory5.getCategoryId()},
			{_assetVocabulary2AssetCategory4.getCategoryId()}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(0, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 0, assetListEntryRelList.size());
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels2AssetVocabularies1Result() {
		long[][] assetCategoryIds = {
			{_assetVocabulary1AssetCategory4.getCategoryId()},
			{_assetVocabulary2AssetCategory4.getCategoryId()}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(1, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 1, assetListEntryRelList.size());

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryRelList.stream();

		List<Long> assetEntryIds = stream.map(
			AssetListEntryAssetEntryRelModel::getAssetEntryId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(assetEntryIds.contains(_assetEntry3.getEntryId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels2AssetVocabularies2Results() {
		long[][] assetCategoryIds = {
			{
				_assetVocabulary1AssetCategory1.getCategoryId(),
				_assetVocabulary1AssetCategory3.getCategoryId()
			},
			{
				_assetVocabulary2AssetCategory1.getCategoryId(),
				_assetVocabulary2AssetCategory3.getCategoryId()
			}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(2, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 2, assetListEntryRelList.size());

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryRelList.stream();

		List<Long> assetEntryIds = stream.map(
			AssetListEntryAssetEntryRelModel::getAssetEntryId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(assetEntryIds.contains(_assetEntry1.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry2.getEntryId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels3AssetVocabularies0Results() {
		long[][] assetCategoryIds = {
			{_assetVocabulary1AssetCategory5.getCategoryId()},
			{_assetVocabulary2AssetCategory4.getCategoryId()},
			{_assetVocabulary3AssetCategory4.getCategoryId()}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(0, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 0, assetListEntryRelList.size());
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels3AssetVocabularies1Result() {
		long[][] assetCategoryIds = {
			{_assetVocabulary1AssetCategory4.getCategoryId()},
			{_assetVocabulary2AssetCategory4.getCategoryId()},
			{_assetVocabulary3AssetCategory4.getCategoryId()}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(1, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 1, assetListEntryRelList.size());

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryRelList.stream();

		List<Long> assetEntryIds = stream.map(
			AssetListEntryAssetEntryRelModel::getAssetEntryId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(assetEntryIds.contains(_assetEntry3.getEntryId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRels3AssetVocabularies2Results() {
		long[][] assetCategoryIds = {
			{
				_assetVocabulary1AssetCategory1.getCategoryId(),
				_assetVocabulary1AssetCategory3.getCategoryId()
			},
			{
				_assetVocabulary2AssetCategory1.getCategoryId(),
				_assetVocabulary2AssetCategory3.getCategoryId()
			},
			{
				_assetVocabulary3AssetCategory1.getCategoryId(),
				_assetVocabulary3AssetCategory3.getCategoryId()
			}
		};

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(2, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 2, assetListEntryRelList.size());

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryRelList.stream();

		List<Long> assetEntryIds = stream.map(
			AssetListEntryAssetEntryRelModel::getAssetEntryId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(assetEntryIds.contains(_assetEntry1.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry2.getEntryId()));
	}

	@Test
	public void testGetAssetListEntryAssetEntryRelsNullAssetVocabularies5Results() {
		long[][] assetCategoryIds = null;

		int assetListEntryRelListCount =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRelsCount(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds);

		Assert.assertEquals(5, assetListEntryRelListCount);

		List<AssetListEntryAssetEntryRel> assetListEntryRelList =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					_assetListEntry.getAssetListEntryId(), new long[] {0},
					assetCategoryIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Assert.assertEquals(
			assetListEntryRelList.toString(), 5, assetListEntryRelList.size());

		Stream<AssetListEntryAssetEntryRel> stream =
			assetListEntryRelList.stream();

		List<Long> assetEntryIds = stream.map(
			AssetListEntryAssetEntryRelModel::getAssetEntryId
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(assetEntryIds.contains(_assetEntry1.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry2.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry3.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry4.getEntryId()));
		Assert.assertTrue(assetEntryIds.contains(_assetEntry5.getEntryId()));
	}

	private AssetEntry _assetEntry1;
	private AssetEntry _assetEntry2;
	private AssetEntry _assetEntry3;
	private AssetEntry _assetEntry4;
	private AssetEntry _assetEntry5;

	@Inject
	private AssetEntryAssetCategoryRelLocalService
		_assetEntryAssetCategoryRelLocalService;

	@Inject
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	private AssetListEntry _assetListEntry;

	@Inject
	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

	private AssetCategory _assetVocabulary1AssetCategory1;
	private AssetCategory _assetVocabulary1AssetCategory2;
	private AssetCategory _assetVocabulary1AssetCategory3;
	private AssetCategory _assetVocabulary1AssetCategory4;
	private AssetCategory _assetVocabulary1AssetCategory5;
	private AssetCategory _assetVocabulary2AssetCategory1;
	private AssetCategory _assetVocabulary2AssetCategory2;
	private AssetCategory _assetVocabulary2AssetCategory3;
	private AssetCategory _assetVocabulary2AssetCategory4;
	private AssetCategory _assetVocabulary3AssetCategory1;
	private AssetCategory _assetVocabulary3AssetCategory2;
	private AssetCategory _assetVocabulary3AssetCategory3;
	private AssetCategory _assetVocabulary3AssetCategory4;

	@DeleteAfterTestRun
	private Group _group;

}