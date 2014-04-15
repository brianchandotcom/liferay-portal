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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.util.AssetTestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author José Manuel Navarro
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetCategoryServiceTest {

	@Test
	public void testDeleteVocabularyAlsoUpdatesCategoriesTree()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		long groupId = group.getGroupId();

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

}