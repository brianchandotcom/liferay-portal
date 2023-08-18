/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.headless.builder.application.APIApplication;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sergio Jiménez del Coso
 */
public class OpenAPIUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetDifferentOperationId() {
		String expectedOperationId = "endpointPath";

		String newOperationId = OpenAPIUtil.getOperationId(
			_createHttpMethod("GET"), _createAPISchema(), expectedOperationId,
			"/path-name", "Page");

		Assert.assertNotNull(newOperationId);
		Assert.assertNotEquals(expectedOperationId, newOperationId);
	}

	@Test
	public void testGetOperationId() {
		String expectedOperationId = "getPathNamePage";

		String newOperationId = OpenAPIUtil.getOperationId(
			_createHttpMethod("GET"), _createAPISchema(), expectedOperationId,
			"/path-name", "Page");

		Assert.assertNotNull(newOperationId);
		Assert.assertEquals(expectedOperationId, newOperationId);
	}

	@Test(expected = NullPointerException.class)
	public void testGetOperationIdNullHttpMethod() {
		OpenAPIUtil.getOperationId(
			null, _createAPISchema(), "getPathNamePage", "/path-name", "Page");
	}

	@Test
	public void testGetOperationIdNullOperationId() {
		Assert.assertNotNull(
			OpenAPIUtil.getOperationId(
				_createHttpMethod("GET"), _createAPISchema(), null,
				"/path-name", "Page"));
	}

	@Test(expected = NullPointerException.class)
	public void testGetOperationIdNullPath() {
		OpenAPIUtil.getOperationId(
			_createHttpMethod("GET"), _createAPISchema(), "getPathNamePage",
			null, "Page");
	}

	@Test
	public void testGetOperationIdNullResponseSchema() {
		String expectedOperationId = "getPathNamePage";

		String newOperationId = OpenAPIUtil.getOperationId(
			_createHttpMethod("GET"), null, expectedOperationId, "/path-name",
			"Page");

		Assert.assertNotNull(newOperationId);
		Assert.assertEquals(expectedOperationId, newOperationId);
	}

	@Test
	public void testGetOperationIdNullReturnType() {
		String expectedOperationId = "getPathNamePage";

		String newOperationId = OpenAPIUtil.getOperationId(
			_createHttpMethod("GET"), _createAPISchema(), expectedOperationId,
			"/path-name", null);

		Assert.assertNotNull(newOperationId);
		Assert.assertEquals(expectedOperationId, newOperationId);
	}

	private APIApplication.Schema _createAPISchema() {
		return new APIApplication.Schema() {

			@Override
			public String getDescription() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getExternalReferenceCode() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getMainObjectDefinitionExternalReferenceCode() {
				return RandomTestUtil.randomString();
			}

			@Override
			public String getName() {
				return RandomTestUtil.randomString();
			}

			@Override
			public List<APIApplication.Property> getProperties() {
				return null;
			}

		};
	}

	private Http.Method _createHttpMethod(String method) {
		return Http.Method.valueOf(method);
	}

}