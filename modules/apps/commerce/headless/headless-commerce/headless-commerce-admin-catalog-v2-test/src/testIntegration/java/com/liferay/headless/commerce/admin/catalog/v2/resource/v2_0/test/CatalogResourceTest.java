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
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.Catalog;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class CatalogResourceTest extends BaseCatalogResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testGetCatalogsPageWithSortString() throws Exception {
		super.testGetCatalogsPageWithSortString();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductByExternalReferenceCodeCatalog()
		throws Exception {

		super.testGetProductByExternalReferenceCodeCatalog();
	}

	@Ignore
	@Override
	@Test
	public void testGetProductIdCatalog() throws Exception {
		super.testGetProductIdCatalog();
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
	protected Catalog testPatchCatalog_addCatalog() throws Exception {
		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testPatchCatalogByExternalReferenceCode_addCatalog()
		throws Exception {

		return catalogResource.postCatalog(randomCatalog());
	}

	@Override
	protected Catalog testPostCatalog_addCatalog(Catalog catalog)
		throws Exception {

		return catalogResource.postCatalog(catalog);
	}

}