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

package com.liferay.headless.commerce.admin.catalog.v2.resource.v2_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.Category;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
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
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"externalReferenceCode", "name"};
	}

	@Override
	protected Category
			testGetProductByExternalReferenceCodeCategoriesPage_addCategory(
				String externalReferenceCode, Category category)
		throws Exception {

		return _addCategory(randomCategory());
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

		return _addCategory(randomCategory());
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

		_serviceContext.setAssetCategoryIds(
			ArrayUtil.toArray(
				ListUtil.toArray(
					_assetCategories, AssetCategory.CATEGORY_ID_ACCESSOR)));

		_cpDefinitionLocalService.updateCPDefinitionCategorization(
			_cpDefinition.getCPDefinitionId(), _serviceContext);

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

	@DeleteAfterTestRun
	private final List<AssetCategory> _assetCategories = new ArrayList<>();

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@DeleteAfterTestRun
	private AssetVocabulary _assetVocabulary;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

	private ServiceContext _serviceContext;

	@DeleteAfterTestRun
	private User _user;

}