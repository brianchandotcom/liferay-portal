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
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.headless.commerce.admin.catalog.v2.client.dto.v2_0.Specification;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Stefano Motta
 */
@RunWith(Arquillian.class)
public class SpecificationResourceTest
	extends BaseSpecificationResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_cpSpecificationOptionLocalService.deleteCPSpecificationOptions(
			testCompany.getCompanyId());
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"facetable", "title"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"key", "title"};
	}

	@Override
	protected Specification randomSpecification() throws Exception {
		return new Specification() {
			{
				facetable = true;
				id = RandomTestUtil.randomLong();
				key = StringUtil.toLowerCase(RandomTestUtil.randomString());
				title = LanguageUtils.getLanguageIdMap(
					RandomTestUtil.randomLocaleStringMap());
			}
		};
	}

	@Override
	protected Specification testDeleteSpecification_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification testGetSpecification_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification testGetSpecificationsPage_addSpecification(
			Specification specification)
		throws Exception {

		return specificationResource.postSpecification(specification);
	}

	@Override
	protected Specification testGraphQLSpecification_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification testPatchSpecification_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification testPostSpecification_addSpecification(
			Specification specification)
		throws Exception {

		return specificationResource.postSpecification(specification);
	}

	@Inject
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

}