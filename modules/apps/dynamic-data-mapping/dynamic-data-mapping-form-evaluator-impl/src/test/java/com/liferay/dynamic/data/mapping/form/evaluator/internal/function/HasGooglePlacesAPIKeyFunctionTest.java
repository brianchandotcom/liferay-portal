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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.function;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Rodrigo Paulino
 */
public class HasGooglePlacesAPIKeyFunctionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testApplyWithGooglePlacesAPIKey() {
		HasGooglePlacesAPIKeyFunction hasGooglePlacesAPIKeyFunction =
			_createHasGooglePlacesAPIKeyFunction(StringUtil::randomString);

		Assert.assertTrue(hasGooglePlacesAPIKeyFunction.apply());
	}

	@Test
	public void testApplyWithNullGooglePlacesAPIKey() {
		HasGooglePlacesAPIKeyFunction hasGooglePlacesAPIKeyFunction =
			_createHasGooglePlacesAPIKeyFunction(() -> null);

		Assert.assertFalse(hasGooglePlacesAPIKeyFunction.apply());
	}

	private HasGooglePlacesAPIKeyFunction _createHasGooglePlacesAPIKeyFunction(
		Supplier<String> getGooglePlacesAPIKeySupplier) {

		DefaultDDMExpressionParameterAccessor ddmExpressionParameterAccessor =
			new DefaultDDMExpressionParameterAccessor();

		if (getGooglePlacesAPIKeySupplier != null) {
			ddmExpressionParameterAccessor.setGetGooglePlacesAPIKeySupplier(
				getGooglePlacesAPIKeySupplier);
		}

		HasGooglePlacesAPIKeyFunction hasGooglePlacesAPIKeyFunction =
			new HasGooglePlacesAPIKeyFunction();

		hasGooglePlacesAPIKeyFunction.setDDMExpressionParameterAccessor(
			ddmExpressionParameterAccessor);

		return hasGooglePlacesAPIKeyFunction;
	}

}