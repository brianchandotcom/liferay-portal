/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Leonardo Barros
 */
public class IsURLFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testInvalidURL() {
		IsURLFunction isURLFunction = new IsURLFunction();

		Assert.assertFalse(isURLFunction.apply(null));
		Assert.assertFalse(isURLFunction.apply(Http.HTTP_WITH_SLASH));
		Assert.assertFalse(isURLFunction.apply(Http.HTTPS_WITH_SLASH));
		Assert.assertFalse(isURLFunction.apply(RandomTestUtil.randomString()));
	}

	@Test
	public void testValidURL() {
		IsURLFunction isURLFunction = new IsURLFunction();

		Assert.assertTrue(isURLFunction.apply("http://www.liferay.com"));
	}

}