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

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Category;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class CategoryResourceTest extends BaseCategoryResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_user = UserTestUtil.addUser(testCompany);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			testCompany.getCompanyId(), testGroup.getGroupId(),
			_user.getUserId());

		_assetVocabulary = _assetVocabularyLocalService.addVocabulary(
			_user.getUserId(), testGroup.getGroupId(),
			RandomTestUtil.randomString(), _serviceContext);

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();
	}

	@Override
	@Test
	public void testPatchProductByExternalReferenceCodeCategory()
		throws Exception {

		Category postCategory = _addCategory(randomCategory());

		Category randomPatchCategory = _addCategory(randomCategory());

		String productExternalReferenceCode =
			_cProduct.getExternalReferenceCode();

		categoryResource.patchProductByExternalReferenceCodeCategory(
			productExternalReferenceCode, new Category[] {randomPatchCategory});

		Category expectedCategory = postCategory.clone();

		BeanTestUtil.copyProperties(randomPatchCategory, expectedCategory);

		Page<Category> categoryPage =
			categoryResource.getProductByExternalReferenceCodeCategoriesPage(
				productExternalReferenceCode, null);

		Category getCategory = categoryPage.fetchFirstItem();

		assertEquals(expectedCategory, getCategory);
		assertValid(getCategory);
	}

	@Override
	@Test
	public void testPatchProductIdCategory() throws Exception {
		Category postCategory = _addCategory(randomCategory());

		Category randomPatchCategory = _addCategory(randomCategory());

		long productId = _cProduct.getCProductId();

		categoryResource.patchProductIdCategory(
			productId, new Category[] {randomPatchCategory});

		Category expectedCategory = postCategory.clone();

		BeanTestUtil.copyProperties(randomPatchCategory, expectedCategory);

		Page<Category> categoryPage =
			categoryResource.getProductIdCategoriesPage(productId, null);

		Category getCategory = categoryPage.fetchFirstItem();

		assertEquals(expectedCategory, getCategory);
		assertValid(getCategory);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected Category
			testGetProductByExternalReferenceCodeCategoriesPage_addCategory(
				String externalReferenceCode, Category category)
		throws Exception {

		category = _addCategory(category);

		Page<Category> categoryPage =
			categoryResource.getProductByExternalReferenceCodeCategoriesPage(
				externalReferenceCode, null);

		Collection<Category> categoryCollection = categoryPage.getItems();

		categoryCollection.add(category);

		categoryResource.patchProductByExternalReferenceCodeCategory(
			externalReferenceCode, categoryCollection.toArray(new Category[0]));

		return category;
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeCategoriesPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected Category testGetProductIdCategoriesPage_addCategory(
			Long id, Category category)
		throws Exception {

		category = _addCategory(category);

		Page<Category> categoryPage =
			categoryResource.getProductIdCategoriesPage(id, null);

		Collection<Category> categoryCollection = categoryPage.getItems();

		categoryCollection.add(category);

		categoryResource.patchProductIdCategory(
			id, categoryCollection.toArray(new Category[0]));

		return category;
	}

	@Override
	protected Long testGetProductIdCategoriesPage_getId() throws Exception {
		return _cProduct.getCProductId();
	}

	@Override
	protected Category testGraphQLCategory_addCategory() throws Exception {
		return _addCategory(randomCategory());
	}

	private Category _addCategory(Category category) throws Exception {
		AssetCategory assetCategory = _assetCategoryLocalService.addCategory(
			_user.getUserId(), category.getSiteId(), category.getName(),
			_assetVocabulary.getVocabularyId(), _serviceContext);

		_assetCategories.add(assetCategory);

		return new Category() {
			{
				externalReferenceCode =
					assetCategory.getExternalReferenceCode();
				id = assetCategory.getPrimaryKey();
				name = assetCategory.getName();
				siteId = assetCategory.getGroupId();
				vocabulary = String.valueOf(assetCategory.getVocabularyId());
			}
		};
	}

	@Inject
	private static AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private static AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private final List<AssetCategory> _assetCategories = new ArrayList<>();

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}