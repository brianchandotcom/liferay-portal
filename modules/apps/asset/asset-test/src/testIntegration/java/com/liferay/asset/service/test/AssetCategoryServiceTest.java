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

package com.liferay.asset.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author José Manuel Navarro
 */
@RunWith(Arquillian.class)
public class AssetCategoryServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDeleteVocabularyAlsoUpdatesCategoriesTree()
		throws Exception {

		long groupId = _group.getGroupId();

		AssetVocabulary vocabulary1 = AssetTestUtil.addVocabulary(groupId);
		AssetVocabulary vocabulary2 = AssetTestUtil.addVocabulary(groupId);

		AssetCategory category1a = AssetTestUtil.addCategory(
			groupId, vocabulary1.getVocabularyId());

		assertLeftRightCategory(1, category1a);

		AssetCategory category1b = AssetTestUtil.addCategory(
			groupId, vocabulary1.getVocabularyId());

		assertLeftRightCategory(3, category1b);

		AssetCategory category1c = AssetTestUtil.addCategory(
			groupId, vocabulary1.getVocabularyId());

		assertLeftRightCategory(5, category1c);

		AssetCategory category2a = AssetTestUtil.addCategory(
			groupId, vocabulary2.getVocabularyId());

		assertLeftRightCategory(7, category2a);

		AssetCategory category2b = AssetTestUtil.addCategory(
			groupId, vocabulary2.getVocabularyId());

		assertLeftRightCategory(9, category2b);

		AssetCategory category2c = AssetTestUtil.addCategory(
			groupId, vocabulary2.getVocabularyId());

		assertLeftRightCategory(11, category2c);

		AssetVocabularyServiceUtil.deleteVocabulary(
			vocabulary1.getVocabularyId());

		int count = AssetCategoryServiceUtil.getVocabularyCategoriesCount(
			groupId, vocabulary1.getVocabularyId());

		Assert.assertEquals(0, count);

		count = AssetCategoryServiceUtil.getVocabularyCategoriesCount(
			groupId, vocabulary2.getVocabularyId());

		Assert.assertEquals(3, count);

		category2a = AssetCategoryServiceUtil.getCategory(
			category2a.getCategoryId());

		assertLeftRightCategory(1, category2a);

		category2b = AssetCategoryServiceUtil.getCategory(
			category2b.getCategoryId());

		assertLeftRightCategory(3, category2b);

		category2c = AssetCategoryServiceUtil.getCategory(
			category2c.getCategoryId());

		assertLeftRightCategory(5, category2c);
	}

	protected void assertLeftRightCategory(
			long expectedLeft, AssetCategory category)
		throws Exception {

		Assert.assertEquals(expectedLeft, category.getLeftCategoryId());
		Assert.assertEquals(expectedLeft + 1, category.getRightCategoryId());
	}

	@DeleteAfterTestRun
	private Group _group;

}