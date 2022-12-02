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
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.commerce.product.test.util.CPTestUtil;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Catalog;
import com.liferay.headless.commerce.admin.catalog.client.pagination.Pagination;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.test.rule.Inject;

import java.math.BigDecimal;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class CatalogResourceTest extends BaseCatalogResourceTestCase {

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
	public void testDeleteCatalog() throws Exception {
		super.testDeleteCatalog();
	}

	@Ignore
	@Override
	@Test
	public void testDeleteCatalogByExternalReferenceCode() throws Exception {
		super.testDeleteCatalogByExternalReferenceCode();
	}

	@Ignore
	@Override
	@Test
	public void testGetCatalogsPageWithSortString() throws Exception {
		super.testGetCatalogsPageWithSortString();
	}

	@Override
	@Test
	public void testGetProductByExternalReferenceCodeCatalog()
		throws Exception {

		String externalReferenceCode = _cProduct.getExternalReferenceCode();

		Catalog postCatalog = _getCommerceCatalogs();

		Catalog getCatalog =
			catalogResource.getProductByExternalReferenceCodeCatalog(
				externalReferenceCode, Pagination.of(1, 2));

		assertEquals(postCatalog, getCatalog);
		assertValid(getCatalog);
	}

	@Override
	@Test
	public void testGetProductIdCatalog() throws Exception {
		Long id = _cProduct.getCProductId();

		Catalog postCatalog = _getCommerceCatalogs();

		Catalog getCatalog = catalogResource.getProductIdCatalog(
			id, Pagination.of(1, 2));

		assertEquals(postCatalog, getCatalog);
		assertValid(getCatalog);
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteCatalog() throws Exception {
		super.testGraphQLDeleteCatalog();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetCatalogsPage() throws Exception {
		super.testGraphQLGetCatalogsPage();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductByExternalReferenceCodeCatalog()
		throws Exception {

		super.testGraphQLGetProductByExternalReferenceCodeCatalog();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetProductIdCatalog() throws Exception {
		super.testGraphQLGetProductIdCatalog();
	}

	@Override
	@Test
	public void testPatchCatalog() throws Exception {
		Catalog postCatalog = _getCommerceCatalogs();

		Catalog randomPatchCatalog = randomPatchCatalog();

		catalogResource.patchCatalog(postCatalog.getId(), randomPatchCatalog);

		Catalog expectedCatalog = postCatalog.clone();

		BeanTestUtil.copyProperties(randomPatchCatalog, expectedCatalog);

		Catalog getCatalog = catalogResource.getCatalog(postCatalog.getId());

		assertEquals(expectedCatalog, getCatalog);
		assertValid(getCatalog);
	}

	@Override
	@Test
	public void testPatchCatalogByExternalReferenceCode() throws Exception {
		Catalog postCatalog = _getCommerceCatalogs();

		Catalog randomPatchCatalog = randomPatchCatalog();

		catalogResource.patchCatalogByExternalReferenceCode(
			postCatalog.getExternalReferenceCode(), randomPatchCatalog);

		Catalog expectedCatalog = postCatalog.clone();

		BeanTestUtil.copyProperties(randomPatchCatalog, expectedCatalog);

		Catalog getCatalog = catalogResource.getCatalogByExternalReferenceCode(
			postCatalog.getExternalReferenceCode());

		assertEquals(expectedCatalog, getCatalog);
		assertValid(getCatalog);
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"currencyCode", "name"};
	}

	@Override
	protected Catalog testDeleteCatalog_addCatalog() throws Exception {
		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testDeleteCatalogByExternalReferenceCode_addCatalog()
		throws Exception {

		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testGetCatalog_addCatalog() throws Exception {
		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testGetCatalogByExternalReferenceCode_addCatalog()
		throws Exception {

		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testGetCatalogsPage_addCatalog(Catalog catalog)
		throws Exception {

		return catalogResource.postCatalog(catalog);
	}

	@Override
	protected Catalog testGetProductByExternalReferenceCodeCatalog_addCatalog()
		throws Exception {

		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testGetProductIdCatalog_addCatalog() throws Exception {
		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testGraphQLCatalog_addCatalog() throws Exception {
		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testPostCatalog_addCatalog(Catalog catalog)
		throws Exception {

		return catalogResource.postCatalog(catalog);
	}

	private Catalog _getCommerceCatalogs() {
		List<CommerceCatalog> commerceCatalogs =
			_commerceCatalogLocalService.getCommerceCatalogs(
				testCompany.getCompanyId(), true);

		CommerceCatalog commerceCatalog = commerceCatalogs.get(0);

		return new Catalog() {
			{
				currencyCode = commerceCatalog.getCommerceCurrencyCode();
				defaultLanguageId =
					commerceCatalog.getCatalogDefaultLanguageId();
				externalReferenceCode =
					commerceCatalog.getExternalReferenceCode();
				id = commerceCatalog.getCommerceCatalogId();
				name = commerceCatalog.getName();
				system = commerceCatalog.isSystem();
			}
		};
	}

	@Inject
	private static CommerceCatalogLocalService _commerceCatalogLocalService;

	@DeleteAfterTestRun
	private CPDefinition _cpDefinition;

	@DeleteAfterTestRun
	private CPInstance _cpInstance;

	@DeleteAfterTestRun
	private CProduct _cProduct;

}