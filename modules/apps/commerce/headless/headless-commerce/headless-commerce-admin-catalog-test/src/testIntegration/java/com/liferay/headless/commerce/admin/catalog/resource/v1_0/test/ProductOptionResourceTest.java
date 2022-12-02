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
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Page;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class ProductOptionResourceTest
	extends BaseProductOptionResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpInstance = CPTestUtil.addCPInstanceWithRandomSku(
			testGroup.getGroupId(), BigDecimal.TEN);

		_cpDefinition = _cpInstance.getCPDefinition();

		_cProduct = _cpDefinition.getCProduct();
	}

	@Ignore
	@Override
	@Test
	public void testDeleteProductOption() throws Exception {
		super.testDeleteProductOption();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteProductOption() throws Exception {
		super.testGraphQLDeleteProductOption();
	}

	@Override
	@Test
	public void testPatchProductOption() throws Exception {
		ProductOption postProductOption = _postProductIdProductOptionsPage();

		ProductOption randomPatchProductOption = randomPatchProductOption();

		productOptionResource.patchProductOption(
			postProductOption.getId(), randomPatchProductOption);

		ProductOption expectedProductOption = postProductOption.clone();

		BeanTestUtil.copyProperties(
			randomPatchProductOption, expectedProductOption);

		ProductOption getProductOption = productOptionResource.getProductOption(
			postProductOption.getId());

		assertEquals(expectedProductOption, getProductOption);
		assertValid(getProductOption);
	}

	@Override
	@Test
	public void testPostProductByExternalReferenceCodeProductOptionsPage()
		throws Exception {

		ProductOption randomMappedProduct = randomProductOption();

		Page<ProductOption> optionPage =
			productOptionResource.
				postProductByExternalReferenceCodeProductOptionsPage(
					_cProduct.getExternalReferenceCode(),
					new ProductOption[] {randomMappedProduct});

		ProductOption postMappedProduct = _getLastProductOptionCreated(
			optionPage.getItems());

		assertEquals(randomMappedProduct, postMappedProduct);
		assertValid(postMappedProduct);
	}

	@Override
	@Test
	public void testPostProductIdProductOptionsPage() throws Exception {
		ProductOption randomMappedProduct = randomProductOption();

		Page<ProductOption> optionPage =
			productOptionResource.postProductIdProductOptionsPage(
				_cProduct.getCProductId(),
				new ProductOption[] {randomMappedProduct});

		ProductOption postMappedProduct = _getLastProductOptionCreated(
			optionPage.getItems());

		assertEquals(randomMappedProduct, postMappedProduct);
		assertValid(postMappedProduct);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"description", "facetable", "fieldType", "name", "required",
			"skuContributor"
		};
	}

	@Override
	protected Collection<EntityField> getEntityFields() throws Exception {
		try {
			return super.getEntityFields();
		}
		catch (NullPointerException nullPointerException) {
			Map<String, EntityField> entityFieldsMap = new HashMap<>();

			return entityFieldsMap.values();
		}
	}

	@Override
	protected ProductOption randomProductOption() throws Exception {
		CPOption cpOption = CPTestUtil.addCPOption(
			testGroup.getGroupId(), false);

		_cpOptions.add(cpOption);

		return new ProductOption() {
			{
				catalogId = testCompany.getCompanyId();
				description = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				facetable = RandomTestUtil.randomBoolean();
				fieldType = "text";
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				optionId = cpOption.getCPOptionId();
				priority = RandomTestUtil.randomDouble();
				required = RandomTestUtil.randomBoolean();
				skuContributor = false;
			}
		};
	}

	@Override
	protected ProductOption testDeleteProductOption_addProductOption()
		throws Exception {

		return _postProductIdProductOptionsPage();
	}

	@Override
	protected ProductOption
			testGetProductByExternalReferenceCodeProductOptionsPage_addProductOption(
				String externalReferenceCode, ProductOption productOption)
		throws Exception {

		Page<ProductOption> optionPage =
			productOptionResource.
				postProductByExternalReferenceCodeProductOptionsPage(
					externalReferenceCode, new ProductOption[] {productOption});

		return _getLastProductOptionCreated(optionPage.getItems());
	}

	@Override
	protected String
			testGetProductByExternalReferenceCodeProductOptionsPage_getExternalReferenceCode()
		throws Exception {

		return _cProduct.getExternalReferenceCode();
	}

	@Override
	protected ProductOption testGetProductIdProductOptionsPage_addProductOption(
			Long id, ProductOption productOption)
		throws Exception {

		Page<ProductOption> optionPage =
			productOptionResource.postProductIdProductOptionsPage(
				id, new ProductOption[] {productOption});

		return _getLastProductOptionCreated(optionPage.getItems());
	}

	@Override
	protected Long testGetProductIdProductOptionsPage_getId() throws Exception {
		return _cProduct.getCProductId();
	}

	@Override
	protected ProductOption testGetProductOption_addProductOption()
		throws Exception {

		return _postProductIdProductOptionsPage();
	}

	@Override
	protected ProductOption testGraphQLProductOption_addProductOption()
		throws Exception {

		return _postProductIdProductOptionsPage();
	}

	private ProductOption _getLastProductOptionCreated(
		Collection<ProductOption> options) {

		for (ProductOption option : options) {
			Long optionId = Long.valueOf(option.getOptionId());

			if (!_productOptionsSaved.containsKey(optionId)) {
				_productOptionsSaved.put(optionId, option);

				return option;
			}
		}

		return null;
	}

	private ProductOption _postProductIdProductOptionsPage() throws Exception {
		Page<ProductOption> optionPage =
			productOptionResource.postProductIdProductOptionsPage(
				_cProduct.getCProductId(),
				new ProductOption[] {randomProductOption()});

		return _getLastProductOptionCreated(optionPage.getItems());
	}

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private final List<CPOption> _cpOptions = new ArrayList<>();

	@DeleteAfterTestRun
	private CProduct _cProduct;

	private final Map<Long, ProductOption> _productOptionsSaved =
		new HashMap<>();

}