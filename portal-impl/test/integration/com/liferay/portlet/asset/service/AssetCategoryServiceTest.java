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
	public void testRemoveVocabularyShouldUpdateCategoriesTree()
		throws Exception {

		long groupId = GroupTestUtil.addGroup().getGroupId();

		AssetVocabulary v1 = AssetTestUtil.addVocabulary(groupId);
		AssetVocabulary v2 = AssetTestUtil.addVocabulary(groupId);

		AssetCategory c1a = AssetTestUtil.addCategory(
			groupId, v1.getVocabularyId());

		assertLeftRightCategory(2, c1a);

		AssetCategory c1b = AssetTestUtil.addCategory(
			groupId, v1.getVocabularyId());

		assertLeftRightCategory(4, c1b);

		AssetCategory c1c = AssetTestUtil.addCategory(
			groupId, v1.getVocabularyId());

		assertLeftRightCategory(6, c1c);

		AssetCategory c2a = AssetTestUtil.addCategory(
			groupId, v2.getVocabularyId());

		assertLeftRightCategory(8, c2a);

		AssetCategory c2b = AssetTestUtil.addCategory(
			groupId, v2.getVocabularyId());

		assertLeftRightCategory(10, c2b);

		AssetCategory c2c = AssetTestUtil.addCategory(
			groupId, v2.getVocabularyId());

		assertLeftRightCategory(12, c2c);

		AssetVocabularyServiceUtil.deleteVocabulary(v1.getVocabularyId());

		int count = AssetCategoryServiceUtil.getVocabularyCategoriesCount(
			groupId, v1.getVocabularyId());

		Assert.assertEquals(0, count);

		count = AssetCategoryServiceUtil.getVocabularyCategoriesCount(
			groupId, v2.getVocabularyId());

		Assert.assertEquals(3, count);

		c2a = AssetCategoryServiceUtil.getCategory(c2a.getCategoryId());

		assertLeftRightCategory(2, c2a);

		c2b = AssetCategoryServiceUtil.getCategory(c2b.getCategoryId());

		assertLeftRightCategory(4, c2b);

		c2c = AssetCategoryServiceUtil.getCategory(c2c.getCategoryId());

		assertLeftRightCategory(6, c2c);
	}

	protected void assertLeftRightCategory(
			long expectedLeft, AssetCategory category)
		throws Exception {

		Assert.assertEquals(expectedLeft, category.getLeftCategoryId());
		Assert.assertEquals(expectedLeft + 1, category.getRightCategoryId());
	}

}