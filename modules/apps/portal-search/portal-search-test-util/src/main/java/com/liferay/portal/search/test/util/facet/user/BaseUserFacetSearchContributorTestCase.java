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

package com.liferay.portal.search.test.util.facet.user;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.search.facet.user.UserFacetSearchContributor;
import com.liferay.portal.search.internal.facet.user.UserFacetFactoryImpl;
import com.liferay.portal.search.internal.facet.user.UserFacetSearchContributorImpl;
import com.liferay.portal.search.test.util.FacetsAssert;
import com.liferay.portal.search.test.util.indexing.BaseIndexingTestCase;
import com.liferay.portal.search.test.util.indexing.DocumentCreationHelpers;

import org.junit.Before;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseUserFacetSearchContributorTestCase
	extends BaseIndexingTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		setUpJSONFactoryUtil();

		_userFacetSearchContributor = new UserFacetSearchContributorImpl() {
			{
				setUserFacetFactory(new UserFacetFactoryImpl());
			}
		};
	}

	@Test
	public void testFrequencies() throws Exception {
		index("alpha");
		index("beta");
		index("beta");
		index("charlie");
		index("charlie");
		index("charlie");

		assertSearch(
			indexingTestHelper -> {
				indexingTestHelper.defineRequest(
					searchRequestBuilder ->
						_userFacetSearchContributor.contribute(
							searchRequestBuilder,
							userFacetBuilder ->
								userFacetBuilder.aggregationName(USE)));

				indexingTestHelper.search();

				indexingTestHelper.verifyResponse(
					searchResponse -> searchResponse.withFacetContext(
						facetContext -> FacetsAssert.assertFrequencies(
							searchResponse.getRequestString(),
							facetContext.getFacet(USE),
							"[charlie=3, beta=2, alpha=1]")));
			});
	}

	protected void index(String user) throws Exception {
		addDocument(
			DocumentCreationHelpers.singleKeyword(Field.USER_NAME, user));
	}

	protected void setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	protected static final String USE = "use";

	private UserFacetSearchContributor _userFacetSearchContributor;

}