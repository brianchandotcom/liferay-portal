/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.util;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
	public void testGetOperationId() {
		Assert.assertEquals(
			"getCamelSchemasPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/camelschemas", "CamelSchema"));
		Assert.assertEquals(
			"getPathNamePage",
			OpenAPIUtil.getOperationId(Http.Method.GET, "/path-name", null));
		Assert.assertEquals(
			"getPathNamePage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/path-name", "Schema"));
		Assert.assertEquals(
			"getSchemasWhateverPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/schema/whatever", "Schema"));
		Assert.assertEquals(
			"getScopeScopeKeySiteScopedPathPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/scopes/{scopeKey}/site-scoped-path",
				"Schema"));
		Assert.assertEquals(
			"getScopeScopeKeyNoSchemaPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/scopes/{scopeKey}/no-schema", null));
		Assert.assertEquals(
			"getSegmentASegmentBPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/segment-a/segment-b", "Schema"));
		Assert.assertEquals(
			"getWhateverPage",
			OpenAPIUtil.getOperationId(
				Http.Method.GET, "/whatever", "CamelSchema"));
	}

}