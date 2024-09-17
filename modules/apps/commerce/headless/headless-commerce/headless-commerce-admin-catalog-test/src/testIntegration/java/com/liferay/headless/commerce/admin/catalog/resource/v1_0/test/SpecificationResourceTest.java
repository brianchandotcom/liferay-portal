/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.commerce.admin.catalog.client.dto.v1_0.Specification;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zoltán Takács
 */
@RunWith(Arquillian.class)
public class SpecificationResourceTest
	extends BaseSpecificationResourceTestCase {

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSpecification() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSpecificationByExternalReferenceCode()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSpecificationByExternalReferenceCodeNotFound()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSpecificationNotFound() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetSpecificationsPage() throws Exception {
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {"title"};
	}

	@Override
	protected Specification randomSpecification() throws Exception {
		return new Specification() {
			{
				externalReferenceCode = RandomTestUtil.randomString();
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
	protected Specification
			testDeleteSpecificationByExternalReferenceCode_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification testGetSpecification_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification
			testGetSpecificationByExternalReferenceCode_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification testGetSpecificationsPage_addSpecification(
			Specification specification)
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
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
	protected Specification
			testPatchSpecificationByExternalReferenceCode_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification testPostSpecification_addSpecification(
			Specification specification)
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

	@Override
	protected Specification
			testPutSpecificationByExternalReferenceCode_addSpecification()
		throws Exception {

		return specificationResource.postSpecification(randomSpecification());
	}

}