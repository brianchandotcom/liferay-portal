/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityField;

import java.math.BigDecimal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
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
		ProductOption postProductOption = _addProductOption();

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

		_testPatchProductOptionWithOptionExternalReferenceCode();
	}

	@Override
	@Test
	public void testPostProductByExternalReferenceCodeProductOptionsPage()
		throws Exception {

		ProductOption randomProductOption = randomProductOption();

		Page<ProductOption> productOptionPage =
			productOptionResource.
				postProductByExternalReferenceCodeProductOptionsPage(
					_cProduct.getExternalReferenceCode(),
					new ProductOption[] {randomProductOption});

		ProductOption postProductOption = _getLastProductOption(
			productOptionPage.getItems());

		assertEquals(randomProductOption, postProductOption);
		assertValid(postProductOption);

		_testPostProductByExternalReferenceCodeProductOptionsPageWithOptionExternalReferenceCode();
	}

	@Override
	@Test
	public void testPostProductIdProductOptionsPage() throws Exception {
		ProductOption randomProductOption = randomProductOption();

		Page<ProductOption> productOptionPage =
			productOptionResource.postProductIdProductOptionsPage(
				_cProduct.getCProductId(),
				new ProductOption[] {randomProductOption});

		ProductOption postProductOption = _getLastProductOption(
			productOptionPage.getItems());

		assertEquals(randomProductOption, postProductOption);
		assertValid(postProductOption);

		_testPostProductIdProductOptionsPageWithOptionExternalReferenceCode();
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
				optionExternalReferenceCode =
					cpOption.getExternalReferenceCode();
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

		return _addProductOption();
	}

	@Override
	protected ProductOption
			testGetProductByExternalReferenceCodeProductOptionsPage_addProductOption(
				String externalReferenceCode, ProductOption productOption)
		throws Exception {

		Page<ProductOption> productOptionPage =
			productOptionResource.
				postProductByExternalReferenceCodeProductOptionsPage(
					externalReferenceCode, new ProductOption[] {productOption});

		return _getLastProductOption(productOptionPage.getItems());
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

		Page<ProductOption> productOptionPage =
			productOptionResource.postProductIdProductOptionsPage(
				id, new ProductOption[] {productOption});

		return _getLastProductOption(productOptionPage.getItems());
	}

	@Override
	protected Long testGetProductIdProductOptionsPage_getId() throws Exception {
		return _cProduct.getCProductId();
	}

	@Override
	protected ProductOption testGetProductOption_addProductOption()
		throws Exception {

		return _addProductOption();
	}

	@Override
	protected ProductOption testGraphQLProductOption_addProductOption()
		throws Exception {

		return _addProductOption();
	}

	private ProductOption _addProductOption() throws Exception {
		Page<ProductOption> productOptionPage =
			productOptionResource.postProductIdProductOptionsPage(
				_cProduct.getCProductId(),
				new ProductOption[] {randomProductOption()});

		return _getLastProductOption(productOptionPage.getItems());
	}

	private ProductOption _getLastProductOption(
		Collection<ProductOption> productOptions) {

		for (ProductOption productOption : productOptions) {
			long optionId = productOption.getOptionId();

			if (!_productOptions.containsKey(optionId)) {
				_productOptions.put(optionId, productOption);

				return productOption;
			}
		}

		return null;
	}

	private void _testPatchProductOptionWithOptionExternalReferenceCode()
		throws Exception {

		ProductOption postProductOption = _addProductOption();

		ProductOption randomPatchProductOption = randomPatchProductOption();

		long optionId = randomPatchProductOption.getOptionId();

		randomPatchProductOption.setOptionId(0L);

		productOptionResource.patchProductOption(
			postProductOption.getId(), randomPatchProductOption);

		randomPatchProductOption.setOptionId(optionId);

		ProductOption expectedProductOption = postProductOption.clone();

		BeanTestUtil.copyProperties(
			randomPatchProductOption, expectedProductOption);

		ProductOption getProductOption = productOptionResource.getProductOption(
			postProductOption.getId());

		assertEquals(expectedProductOption, getProductOption);
		assertValid(getProductOption);
		Assert.assertEquals(
			optionId, GetterUtil.getLong(getProductOption.getOptionId()));
		Assert.assertEquals(
			randomPatchProductOption.getOptionExternalReferenceCode(),
			getProductOption.getOptionExternalReferenceCode());
	}

	private void _testPostProductByExternalReferenceCodeProductOptionsPageWithOptionExternalReferenceCode()
		throws Exception {

		ProductOption randomProductOption = randomProductOption();

		long optionId = randomProductOption.getOptionId();

		randomProductOption.setOptionId(0L);

		Page<ProductOption> productOptionPage =
			productOptionResource.
				postProductByExternalReferenceCodeProductOptionsPage(
					_cProduct.getExternalReferenceCode(),
					new ProductOption[] {randomProductOption});

		randomProductOption.setOptionId(optionId);

		ProductOption postProductOption = _getLastProductOption(
			productOptionPage.getItems());

		assertEquals(randomProductOption, postProductOption);
		assertValid(postProductOption);
		Assert.assertEquals(
			optionId, GetterUtil.getLong(postProductOption.getOptionId()));
		Assert.assertEquals(
			randomProductOption.getOptionExternalReferenceCode(),
			postProductOption.getOptionExternalReferenceCode());
	}

	private void _testPostProductIdProductOptionsPageWithOptionExternalReferenceCode()
		throws Exception {

		ProductOption randomProductOption = randomProductOption();

		long optionId = randomProductOption.getOptionId();

		randomProductOption.setOptionId(0L);

		Page<ProductOption> productOptionPage =
			productOptionResource.postProductIdProductOptionsPage(
				_cProduct.getCProductId(),
				new ProductOption[] {randomProductOption});

		randomProductOption.setOptionId(optionId);

		ProductOption postProductOption = _getLastProductOption(
			productOptionPage.getItems());

		assertEquals(randomProductOption, postProductOption);
		assertValid(postProductOption);
		Assert.assertEquals(
			optionId, GetterUtil.getLong(postProductOption.getOptionId()));
		Assert.assertEquals(
			randomProductOption.getOptionExternalReferenceCode(),
			postProductOption.getOptionExternalReferenceCode());
	}

	private CPDefinition _cpDefinition;
	private CPInstance _cpInstance;
	private CProduct _cProduct;
	private final Map<Long, ProductOption> _productOptions = new HashMap<>();

}