/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.client.problem.Problem;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.List;
import java.util.Objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 * @author Crescenzo Rega
 */
@RunWith(Arquillian.class)
public class ProductResourceTest extends BaseProductResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User user = UserTestUtil.addUser(testCompany);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(testCompany.getGroupId());

		_commerceCatalog = CommerceCatalogLocalServiceUtil.addCommerceCatalog(
			null, RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			LocaleUtil.US.getDisplayLanguage(), serviceContext);

		CommerceCurrency commerceCurrency =
			_commerceCurrencyLocalService.addCommerceCurrency(
				user.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomString(), BigDecimal.ONE,
				RandomTestUtil.randomLocaleStringMap(), 2, 2, "HALF_EVEN",
				false, RandomTestUtil.nextDouble(), true);

		_commercePriceListLocalService.addCatalogBaseCommercePriceList(
			_commerceCatalog.getGroupId(), user.getUserId(),
			commerceCurrency.getCommerceCurrencyId(), "price-list",
			RandomTestUtil.randomString(), serviceContext);

		_commercePriceListLocalService.addCatalogBaseCommercePriceList(
			_commerceCatalog.getGroupId(), user.getUserId(),
			commerceCurrency.getCommerceCurrencyId(), "promotion",
			RandomTestUtil.randomString(), serviceContext);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		List<CPDefinition> cpDefinitions =
			_cpDefinitionLocalService.getCPDefinitions(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinition cpDefinition : cpDefinitions) {
			_cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
		}
	}

	@Ignore
	@Override
	@Test
	public void testDeleteProduct() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testDeleteProductByExternalReferenceCodeByVersion()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testDeleteProductByVersion() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetProduct() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeByVersion()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByVersion() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetProductsPage() throws Exception {
		super.testGetProductsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductsPageWithFilterStringEquals() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetProductsPageWithSortDateTime() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetProductsPageWithSortInteger() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGetProductsPageWithSortString() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteProduct() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProduct() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeByVersion()
		throws Exception {

		super.testGraphQLGetProductByExternalReferenceCodeByVersion();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByVersion() throws Exception {
		super.testGraphQLGetProductByVersion();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductsPage() throws Exception {
	}

	@Override
	@Test
	public void testPatchProduct() throws Exception {
		Product postProduct = _testPatchProduct_addProduct();

		Product randomPatchProduct = randomProduct();

		productResource.patchProduct(
			postProduct.getProductId(), randomPatchProduct);

		Product getProduct = productResource.getProduct(
			postProduct.getProductId());

		Product expectedPatchProduct = postProduct.clone();

		BaseProductResourceTestCase.BeanTestUtil.copyProperties(
			randomPatchProduct, expectedPatchProduct);

		assertEquals(expectedPatchProduct, getProduct);

		assertValid(getProduct);

		randomPatchProduct = randomProduct();

		productResource.patchProductByExternalReferenceCode(
			getProduct.getExternalReferenceCode(), randomPatchProduct);

		getProduct = productResource.getProduct(postProduct.getProductId());

		BaseProductResourceTestCase.BeanTestUtil.copyProperties(
			randomPatchProduct, expectedPatchProduct);

		assertEquals(expectedPatchProduct, getProduct);

		assertValid(getProduct);

		_testPatchProductWithNegativeValue("cost");
		_testPatchProductWithNegativeValue("depth");
		_testPatchProductWithNegativeValue("height");
		_testPatchProductWithNegativeValue("price");
		_testPatchProductWithNegativeValue("promo price");
		_testPatchProductWithNegativeValue("weight");
		_testPatchProductWithNegativeValue("width");
	}

	@Ignore
	@Override
	@Test
	public void testPatchProductByExternalReferenceCode() throws Exception {
	}

	@Override
	@Test
	public void testPostProduct() throws Exception {
		super.testPostProduct();

		Product randomProduct = randomProduct();

		randomProduct.setCatalogId((Long)null);
		randomProduct.setCatalogExternalReferenceCode(
			_commerceCatalog.getExternalReferenceCode());

		Product postProduct = testPostProduct_addProduct(randomProduct);

		Product getProduct = productResource.getProduct(
			postProduct.getProductId());

		Product expectedPostProduct = postProduct.clone();

		BaseProductResourceTestCase.BeanTestUtil.copyProperties(
			postProduct, expectedPostProduct);

		assertEquals(expectedPostProduct, getProduct);

		assertValid(getProduct);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {
			"active", "catalogId", "description", "externalReferenceCode",
			"name", "productType", "shortDescription"
		};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"name", "productType"};
	}

	@Override
	protected Product randomProduct() throws Exception {
		return new Product() {
			{
				active = true;
				catalogId = _commerceCatalog.getCommerceCatalogId();
				description = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				productType = SimpleCPTypeConstants.NAME;
				shortDescription = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
			}
		};
	}

	@Override
	protected Product testDeleteProduct_addProduct() throws Exception {
		return productResource.postProduct(randomProduct());
	}

	@Override
	protected Product testDeleteProductByExternalReferenceCode_addProduct()
		throws Exception {

		return productResource.postProduct(randomProduct());
	}

	@Override
	protected Product testGetProduct_addProduct() throws Exception {
		return productResource.postProduct(randomProduct());
	}

	@Override
	protected Product testGetProductByExternalReferenceCode_addProduct()
		throws Exception {

		return productResource.postProduct(randomProduct());
	}

	@Override
	protected Product testGetProductsPage_addProduct(Product product)
		throws Exception {

		return productResource.postProduct(product);
	}

	@Override
	protected Product testGraphQLProduct_addProduct() throws Exception {
		return productResource.postProduct(randomProduct());
	}

	@Override
	protected Product testPostProduct_addProduct(Product product)
		throws Exception {

		return productResource.postProduct(product);
	}

	@Override
	protected Product testPostProductByExternalReferenceCodeClone_addProduct(
			Product product)
		throws Exception {

		return productResource.postProduct(product);
	}

	@Override
	protected Product testPostProductClone_addProduct(Product product)
		throws Exception {

		return productResource.postProduct(product);
	}

	private Product _randomProductWithSku() throws Exception {
		return new Product() {
			{
				active = true;
				catalogId = _commerceCatalog.getCommerceCatalogId();
				description = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				name = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				productType = SimpleCPTypeConstants.NAME;
				shortDescription = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				skus = new Sku[] {
					new Sku() {
						{
							cost = BigDecimal.valueOf(
								RandomTestUtil.randomDouble());
							depth = RandomTestUtil.randomDouble();
							discontinued = false;
							discontinuedDate = RandomTestUtil.nextDate();
							displayDate = RandomTestUtil.nextDate();
							expirationDate = RandomTestUtil.nextDate();
							externalReferenceCode = StringUtil.toLowerCase(
								RandomTestUtil.randomString());
							gtin = StringUtil.toLowerCase(
								RandomTestUtil.randomString());
							height = RandomTestUtil.randomDouble();
							inventoryLevel = RandomTestUtil.randomInt();
							manufacturerPartNumber = StringUtil.toLowerCase(
								RandomTestUtil.randomString());
							neverExpire = true;
							price = BigDecimal.valueOf(
								RandomTestUtil.randomDouble());
							promoPrice = BigDecimal.valueOf(
								RandomTestUtil.randomDouble());
							published = true;
							purchasable = true;
							sku = StringUtil.toLowerCase(
								RandomTestUtil.randomString());
							unspsc = StringUtil.toLowerCase(
								RandomTestUtil.randomString());
							weight = RandomTestUtil.randomDouble();
							width = RandomTestUtil.randomDouble();
						}
					}
				};
			}
		};
	}

	private Product _testPatchProduct_addProduct() throws Exception {
		return productResource.postProduct(randomProduct());
	}

	private void _testPatchProductWithNegativeValue(String fieldName)
		throws Exception {

		try {
			Product randomProduct = _randomProductWithSku();

			Product postProduct = productResource.postProduct(randomProduct);

			Sku sku = randomProduct.getSkus()[0];

			if (Objects.equals(fieldName, "cost")) {
				sku.setCost(BigDecimal.valueOf(-10.0));
			}
			else if (Objects.equals(fieldName, "depth")) {
				sku.setDepth(-10.0);
			}
			else if (Objects.equals(fieldName, "height")) {
				sku.setHeight(-10.0);
			}
			else if (Objects.equals(fieldName, "price")) {
				sku.setPrice(BigDecimal.valueOf(-10.0));
			}
			else if (Objects.equals(fieldName, "promo price")) {
				sku.setPromoPrice(BigDecimal.valueOf(-10.0));
			}
			else if (Objects.equals(fieldName, "weight")) {
				sku.setWeight(-10.0);
			}
			else if (Objects.equals(fieldName, "width")) {
				sku.setWidth(-10.0);
			}

			productResource.patchProduct(
				postProduct.getProductId(), randomProduct);

			Assert.fail();
		}
		catch (Problem.ProblemException problemException) {
			Problem problem = problemException.getProblem();

			Assert.assertEquals("BAD_REQUEST", problem.getStatus());
			Assert.assertEquals(
				"Sku " + fieldName + " is invalid", problem.getTitle());
		}
	}

	@DeleteAfterTestRun
	private CommerceCatalog _commerceCatalog;

	@Inject
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Inject
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Inject
	private CPDefinitionLocalService _cpDefinitionLocalService;

}