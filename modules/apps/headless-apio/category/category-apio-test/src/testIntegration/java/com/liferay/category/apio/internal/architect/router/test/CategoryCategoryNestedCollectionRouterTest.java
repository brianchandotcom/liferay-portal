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

package com.liferay.category.apio.internal.architect.router.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.category.apio.architect.model.Category;
import com.liferay.category.apio.internal.architect.resource.test.model.CategoryImpl;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
public class CategoryCategoryNestedCollectionRouterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addDefaultVocabulary(
				_group.getGroupId());

		_assetCategory = _addAssetCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());
	}

	@Test
	public void testAddAssetSubcategory() throws Exception {
		Category category = new CategoryImpl(
			"testAddAssetSubcategory category name",
			"testAddAssetSubcategory category description");

		AssetCategory assetCategory = _addAssetSubcategory(
			_assetCategory.getCategoryId(), category);

		Assert.assertNotNull(assetCategory.getCreateDate());

		Map<Locale, String> descriptionMap = category.getDescriptionMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			assetCategory.getDescription(LocaleUtil.getDefault()));

		Assert.assertNotNull(assetCategory.getModifiedDate());

		Map<Locale, String> nameMap = category.getNameMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()), assetCategory.getName());
	}

	@Test
	public void testGetPageItems() throws Exception {
		Category category = new CategoryImpl(
			"testGetPageItems category name",
			"testGetPageItems category description");

		_addAssetSubcategory(_assetCategory.getCategoryId(), category);

		PageItems<AssetCategory> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _assetCategory.getCategoryId());

		Assert.assertEquals(1, pageItems.getTotalCount());

		List<AssetCategory> assetCategories =
			(List<AssetCategory>)pageItems.getItems();

		Assert.assertEquals(
			assetCategories.toString(), 1, assetCategories.size());

		AssetCategory assetCategory = assetCategories.get(0);

		Map<Locale, String> descriptionMap = category.getDescriptionMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			descriptionMap.get(LocaleUtil.getDefault()),
			assetCategory.getDescription(LocaleUtil.getDefault()));

		Map<Locale, String> nameMap = category.getNameMap(
			LocaleUtil.getDefault());

		Assert.assertEquals(
			nameMap.get(LocaleUtil.getDefault()), assetCategory.getName());
	}

	protected PageItems<AssetCategory> getPageItems(
			Pagination pagination, long parentCategoryId)
		throws Exception {

		NestedCollectionRouter nestedCollectionResource =
			_getNestedCollectionRouter();

		Class<?> clazz = nestedCollectionResource.getClass();

		Method method = clazz.getDeclaredMethod(
			"_getPageItems", Pagination.class, long.class);

		method.setAccessible(true);

		return (PageItems)method.invoke(
			_getNestedCollectionRouter(), pagination, parentCategoryId);
	}

	private AssetCategory _addAssetCategory(long groupId, long vocabularyId)
		throws Exception {

		return AssetCategoryServiceUtil.addCategory(
			groupId, RandomTestUtil.randomString(10), vocabularyId,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	private AssetCategory _addAssetSubcategory(
			long parentCategoryId, Category category)
		throws Exception {

		NestedCollectionRouter nestedCollectionRouter =
			_getNestedCollectionRouter();

		Class<?> clazz = nestedCollectionRouter.getClass();

		Method method = clazz.getDeclaredMethod(
			"_addAssetCategory", long.class, Category.class);

		method.setAccessible(true);

		return (AssetCategory)method.invoke(
			_getNestedCollectionRouter(), parentCategoryId, category);
	}

	private NestedCollectionRouter _getNestedCollectionRouter()
		throws Exception {

		Registry registry = RegistryUtil.getRegistry();

		Collection<NestedCollectionRouter> collection = registry.getServices(
			NestedCollectionRouter.class,
			"(component.name=com.liferay.category.apio.internal.architect." +
				"router.CategoryCategoryNestedCollectionRouter)");

		Iterator<NestedCollectionRouter> iterator = collection.iterator();

		return iterator.next();
	}

	private AssetCategory _assetCategory;

	@DeleteAfterTestRun
	private Group _group;

}