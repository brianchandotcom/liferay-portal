/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.application.endpoint;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class EndpointMatcherTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetEndpoint() {
		List<APIApplication.Endpoint> endpoints = Arrays.asList(
			_getEndpoint("/path-name", false),
			_getEndpoint("/path-name2", false),
			_getEndpoint("/path/{parameter}", true));

		EndpointMatcher endpointMatcher = new EndpointMatcher(endpoints);

		Assert.assertNull(endpointMatcher.getEndpoint("/path", null));
		Assert.assertNull(
			endpointMatcher.getEndpoint(
				"/path-name", APIApplication.Endpoint.Scope.GROUP));
		Assert.assertEquals(
			endpoints.get(0), endpointMatcher.getEndpoint("/path-name", null));
		Assert.assertEquals(
			endpoints.get(1), endpointMatcher.getEndpoint("/path-name2", null));
		Assert.assertEquals(
			endpoints.get(2), endpointMatcher.getEndpoint("/path/1234", null));
	}

	private APIApplication.Endpoint _getEndpoint(
		String path, boolean singleElement) {

		return new APIApplication.Endpoint() {

			@Override
			public APIApplication.Filter getFilter() {
				return null;
			}

			@Override
			public Http.Method getMethod() {
				return null;
			}

			@Override
			public String getPath() {
				return path;
			}

			@Override
			public PathParameter getPathParameter() {
				if (singleElement) {
					return PathParameter.ID;
				}

				return null;
			}

			@Override
			public APIApplication.Schema getRequestSchema() {
				return null;
			}

			@Override
			public APIApplication.Schema getResponseSchema() {
				return null;
			}

			@Override
			public RetrieveType getRetrieveType() {
				if (singleElement) {
					return RetrieveType.SINGLE_ELEMENT;
				}

				return RetrieveType.COLLECTION;
			}

			@Override
			public Scope getScope() {
				return null;
			}

			@Override
			public APIApplication.Sort getSort() {
				return null;
			}

		};
	}

}