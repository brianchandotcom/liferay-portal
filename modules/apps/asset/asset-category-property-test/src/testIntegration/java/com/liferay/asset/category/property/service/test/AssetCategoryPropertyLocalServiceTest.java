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

package com.liferay.asset.category.property.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.category.property.exception.CategoryPropertyKeyException;
import com.liferay.asset.category.property.exception.CategoryPropertyValueException;
import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.asset.util.AssetHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class AssetCategoryPropertyLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		_assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());
	}

	@Test
	public void testCanAddCategoryPropertyValueWithSpecialCharacters()
		throws Exception {

		AssetCategoryProperty assetCategoryProperty =
			_assetCategoryPropertyLocalService.addCategoryProperty(
				TestPropsValues.getUserId(), _assetCategory.getCategoryId(),
				RandomTestUtil.randomString(),
				String.valueOf(AssetHelper.INVALID_CHARACTERS));

		Assert.assertEquals(
			String.valueOf(AssetHelper.INVALID_CHARACTERS),
			assetCategoryProperty.getValue());
	}

	@Test(expected = CategoryPropertyKeyException.class)
	public void testCannotAddCategoryPropertyWithVeryLongKey()
		throws Exception {

		int keyMaxLength = ModelHintsUtil.getMaxLength(
			AssetCategoryProperty.class.getName(), "key");

		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(), _assetCategory.getCategoryId(),
			RandomTestUtil.randomString(keyMaxLength + 1),
			RandomTestUtil.randomString());
	}

	@Test(expected = CategoryPropertyValueException.class)
	public void testCannotAddCategoryPropertyWithVeryLongValue()
		throws Exception {

		int keyMaxLength = ModelHintsUtil.getMaxLength(
			AssetCategoryProperty.class.getName(), "value");

		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(), _assetCategory.getCategoryId(),
			RandomTestUtil.randomString(),
			RandomTestUtil.randomString(keyMaxLength + 1));
	}

	@Test(expected = CategoryPropertyValueException.class)
	public void testCannotAddEmptyCategoryPropertyValue() throws Exception {
		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(), _assetCategory.getCategoryId(),
			RandomTestUtil.randomString(), StringPool.BLANK);
	}

	@Test
	public void testGetCategoryPropertyValues() throws Exception {
		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(), _assetCategory.getCategoryId(),
			"keyToBeFound", "someValue");
		_assetCategoryPropertyLocalService.addCategoryProperty(
			TestPropsValues.getUserId(), _assetCategory.getCategoryId(),
			"keyNotToBeFound", "anotherValue");

		List<AssetCategoryProperty> categoryPropertyValues =
			_assetCategoryPropertyLocalService.getCategoryPropertyValues(
				_group.getGroupId(), "keyToBeFound");

		AssetCategoryProperty assetCategoryProperty =
			categoryPropertyValues.get(0);

		Assert.assertEquals("keyToBeFound", assetCategoryProperty.getKey());
		Assert.assertEquals("someValue", assetCategoryProperty.getValue());
	}

	private AssetCategory _assetCategory;

	@Inject
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

	@DeleteAfterTestRun
	private Group _group;

}