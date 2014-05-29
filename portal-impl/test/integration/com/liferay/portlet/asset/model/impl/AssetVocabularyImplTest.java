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
import com.liferay.portal.model.Group;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

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

	@Test
	public void testHasMoreThanOneCategorySelectedAll() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		AssetCategory category1 = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetCategory category2 = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategoryIds =
			new long[]{category1.getCategoryId(), category2.getCategoryId()};

		Assert.assertTrue(
			_vocabulary.hasMoreThanOneCategorySelected(selectedCategoryIds));
	}

	@Test
	public void testHasMoreThanOneCategorySelectedNone() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategoryIds = new long[]{};

		Assert.assertFalse(
			_vocabulary.hasMoreThanOneCategorySelected(selectedCategoryIds));
	}

	@Test
	public void testHasMoreThanOneCategorySelectedOne() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		AssetCategory category1 = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategoryIds = new long[]{category1.getCategoryId()};

		Assert.assertFalse(
			_vocabulary.hasMoreThanOneCategorySelected(selectedCategoryIds));
	}

	@Test
	public void testHasMoreThanOneCategorySelectedOther() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		AssetVocabulary vocabulary2 = _addVocabularyAssociatedToAsset(2, true);

		AssetCategory category21 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());

		AssetCategory category22 = AssetTestUtil.addCategory(
			_group.getGroupId(), vocabulary2.getVocabularyId());

		long[] selectedCategoryIds =
			new long[]{category21.getCategoryId(), category22.getCategoryId()};

		Assert.assertFalse(
			_vocabulary.hasMoreThanOneCategorySelected(selectedCategoryIds));
	}

	@Test
	public void testIsAssociatedToAssetRendererFactoryAll() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(_vocabulary.isAssociatedToAssetRendererFactory(1));
	}

	@Test
	public void testIsAssociatedToAssetRendererFactoryClassName()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		Assert.assertTrue(_vocabulary.isAssociatedToAssetRendererFactory(1));

		Assert.assertFalse(_vocabulary.isAssociatedToAssetRendererFactory(2));
	}

	@Test
	public void testIsMissingRequiredCategoryMatches() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		AssetCategory category = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategoryIds = new long[]{category.getCategoryId()};

		Assert.assertFalse(
			_vocabulary.isMissingRequiredCategory(1, selectedCategoryIds));
	}

	@Test
	public void testIsMissingRequiredCategoryNoExistingCategories()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		long[] selectedCategoryIds = new long[]{1};

		Assert.assertTrue(
			_vocabulary.isMissingRequiredCategory(1, selectedCategoryIds));
	}

	@Test
	public void testIsMissingRequiredCategoryNoMatchesAndNoRequired()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, false);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategoryIds = new long[]{1};

		Assert.assertFalse(
			_vocabulary.isMissingRequiredCategory(1, selectedCategoryIds));
	}

	@Test
	public void testIsMissingRequiredCategoryNoMatchesAndRequired()
		throws Exception {

		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		long[] selectedCategoryIds = new long[]{1};

		Assert.assertTrue(
			_vocabulary.isMissingRequiredCategory(1, selectedCategoryIds));
	}

	@Test
	public void testIsMissingRequiredCategoryNoRelated() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		Assert.assertFalse(
			_vocabulary.isMissingRequiredCategory(2, new long[]{}));
	}

	@Test
	public void testIsRequiredAllNotRequired() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS, false);

		Assert.assertFalse(_vocabulary.isRequired(1));
		Assert.assertFalse(_vocabulary.isRequired(2));
	}

	@Test
	public void testIsRequiredAllRequired() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(
			AssetCategoryConstants.ALL_CLASS_NAME_IDS, true);

		Assert.assertTrue(_vocabulary.isRequired(1));
		Assert.assertTrue(_vocabulary.isRequired(2));
	}

	@Test
	public void testIsRequiredClassNameNotRequired() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(1, false);

		Assert.assertFalse(_vocabulary.isRequired(1));
		Assert.assertFalse(_vocabulary.isRequired(2));
	}

	@Test
	public void testIsRequiredClassNameRequired() throws Exception {
		_vocabulary = _addVocabularyAssociatedToAsset(1, true);

		Assert.assertTrue(_vocabulary.isRequired(1));
		Assert.assertFalse(_vocabulary.isRequired(2));
	}

	private AssetVocabulary _addVocabularyAssociatedToAsset(
			long classNameId, boolean isRequired)
		throws Exception {

		long[] classNameIds = new long[]{classNameId};
		boolean[] required = new boolean[]{isRequired};

		return AssetTestUtil.addVocabularyAssociatedToAssets(
			_group.getGroupId(), true, classNameIds, required);
	}

	private Group _group;
	private AssetVocabulary _vocabulary;

}