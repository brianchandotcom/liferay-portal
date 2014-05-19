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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.util.AssetTestUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author José Manuel Navarro
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetVocabularyImplTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsDuplicatedCategoryWhenAllCategoriesAreSelected()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		AssetCategory category1 = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetCategory category2 = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategories =
			new long[]{category1.getCategoryId(), category2.getCategoryId()};

		Assert.assertTrue(_vocabulary.isDuplicatedCategory(selectedCategories));
	}

	@Test
	public void testIsDuplicatedCategoryWhenNoCategoriesAreSelected()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategories = new long[]{};

		Assert.assertFalse(
			_vocabulary.isDuplicatedCategory(selectedCategories));
	}

	@Test
	public void testIsDuplicatedCategoryWhenOnlyOneCategoryIsSelected()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		AssetCategory category1 = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategories = new long[]{category1.getCategoryId()};

		Assert.assertFalse(
			_vocabulary.isDuplicatedCategory(selectedCategories));
	}

	@Test
	public void testIsDuplicatedCategoryWhenOtherCategoriesAreSelected()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetVocabulary vocabulary2 = _addVocabularyAssociatedToAsset(
			2, 2, true);

		AssetCategory category21 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());

		AssetCategory category22 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());

		long[] selectedCategories =
			new long[]{category21.getCategoryId(), category22.getCategoryId()};

		Assert.assertFalse(
			_vocabulary.isDuplicatedCategory(selectedCategories));
	}

	@Test
	public void testIsMissingRequiredCategoryWhenEverythingMatches()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		AssetCategory category = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategories = new long[]{category.getCategoryId()};

		Assert.assertFalse(
			_vocabulary.isMissingRequiredCategory(1, 1, selectedCategories));
	}

	@Test
	public void testIsMissingRequiredCategoryWhenMatchingAllAndNotRequired()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS,
			AssetCategoryConstants.ALL_CLASS_TYPE_IDS, false);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategories = new long[]{2};

		Assert.assertFalse(
			_vocabulary.isMissingRequiredCategory(
				AssetCategoryConstants.ALL_CLASS_NAME_IDS,
				AssetCategoryConstants.ALL_CLASS_TYPE_IDS, selectedCategories));
	}

	@Test
	public void testIsMissingRequiredCategoryWhenMatchingAllAndRequired()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS,
			AssetCategoryConstants.ALL_CLASS_TYPE_IDS, true);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategories = new long[]{2};

		Assert.assertTrue(
				_vocabulary.isMissingRequiredCategory(
					AssetCategoryConstants.ALL_CLASS_NAME_IDS,
					AssetCategoryConstants.ALL_CLASS_TYPE_IDS,
					selectedCategories));
	}

	@Test
	public void testIsMissingRequiredCategoryWhenNoExistingCategories()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		long[] selectedCategories = new long[]{1};

		Assert.assertFalse(
				_vocabulary.isMissingRequiredCategory(
					1, 1, selectedCategories));
	}

	@Test
	public void testIsMissingRequiredCategoryWhenNotMatchingAndNotRequired()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, false);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategories = new long[]{1};

		Assert.assertFalse(
				_vocabulary.isMissingRequiredCategory(
					1, 1, selectedCategories));
	}

	@Test
	public void testIsMissingRequiredCategoryWhenNotMatchingAndRequired()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategories = new long[]{1};

		Assert.assertTrue(
				_vocabulary.isMissingRequiredCategory(
					1, 1, selectedCategories));
	}

	@Test
	public void testIsMissingRequiredCategoryWhenNotRelatedAtAll()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		Assert.assertFalse(
			_vocabulary.isMissingRequiredCategory(2, 2, new long[]{}));
	}

	@Test
	public void testIsMissingRequiredCategoryWhenNotRelatedClassType()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, 1, true);

		Assert.assertFalse(
			_vocabulary.isMissingRequiredCategory(1, 2, new long[]{}));
	}

	private AssetVocabulary _addVocabularyAssociatedToAsset(
			long assetClassName, long assetClassType, boolean assetRequired)
		throws Exception {

		List<Long> classNames = ListUtil.toList(new long[]{assetClassName});
		List<Long> classTypes = ListUtil.toList(new long[]{assetClassType});
		List<Boolean> required = ListUtil.toList(new boolean[]{assetRequired});

		return AssetTestUtil.addVocabularyAssociatedToAssets(
			_group.getGroupId(), true, classNames, classTypes, required);
	}

	private Group _group;
	private AssetVocabulary _vocabulary;

}