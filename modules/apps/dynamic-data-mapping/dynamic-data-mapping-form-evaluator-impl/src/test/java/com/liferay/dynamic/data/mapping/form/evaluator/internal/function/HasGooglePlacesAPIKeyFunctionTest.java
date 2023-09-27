/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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