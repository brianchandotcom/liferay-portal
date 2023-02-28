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
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.ProductGroup;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class ProductGroupResourceTest extends BaseProductGroupResourceTestCase {

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"description", "title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"description", "title"};
	}

	@Override
	protected ProductGroup randomProductGroup() throws Exception {
		return new ProductGroup() {
			{
				description = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
				externalReferenceCode = StringUtil.toLowerCase(
					RandomTestUtil.randomString());
				id = RandomTestUtil.randomLong();
				productsCount = RandomTestUtil.randomInt();
				title = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
			}
		};
	}

	@Override
	protected ProductGroup testDeleteProductGroup_addProductGroup()
		throws Exception {

		return productGroupResource.postProductGroup(randomProductGroup());
	}

	@Override
	protected ProductGroup
			testDeleteProductGroupByExternalReferenceCode_addProductGroup()
		throws Exception {

		return productGroupResource.postProductGroup(randomProductGroup());
	}

	@Override
	protected ProductGroup testGetProductGroup_addProductGroup()
		throws Exception {

		return productGroupResource.postProductGroup(randomProductGroup());
	}

	@Override
	protected ProductGroup
			testGetProductGroupByExternalReferenceCode_addProductGroup()
		throws Exception {

		return productGroupResource.postProductGroup(randomProductGroup());
	}

	@Override
	protected ProductGroup testGetProductGroupsPage_addProductGroup(
			ProductGroup productGroup)
		throws Exception {

		return productGroupResource.postProductGroup(randomProductGroup());
	}

	@Override
	protected ProductGroup testGraphQLProductGroup_addProductGroup()
		throws Exception {

		return productGroupResource.postProductGroup(randomProductGroup());
	}

	@Override
	protected ProductGroup testPatchProductGroup_addProductGroup()
		throws Exception {

		return productGroupResource.postProductGroup(randomProductGroup());
	}

	@Override
	protected ProductGroup
			testPatchProductGroupByExternalReferenceCode_addProductGroup()
		throws Exception {

		return productGroupResource.postProductGroup(randomProductGroup());
	}

	@Override
	protected ProductGroup testPostProductGroup_addProductGroup(
			ProductGroup productGroup)
		throws Exception {

		return productGroupResource.postProductGroup(productGroup);
	}

}