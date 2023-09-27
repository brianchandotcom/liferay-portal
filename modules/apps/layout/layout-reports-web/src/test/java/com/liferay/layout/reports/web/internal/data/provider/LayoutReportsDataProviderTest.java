/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.reports.web.internal.data.provider;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Cristina González
 */
public class LayoutReportsDataProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsValidConnection() {
		LayoutReportsDataProvider layoutReportsDataProvider =
			new LayoutReportsDataProvider("apiKey", "strategy");

		Assert.assertTrue(layoutReportsDataProvider.isValidConnection());
	}

	@Test
	public void testIsValidConnectionWithoutAPIKey() {
		LayoutReportsDataProvider layoutReportsDataProvider =
			new LayoutReportsDataProvider(null, "strategy");

		Assert.assertFalse(layoutReportsDataProvider.isValidConnection());
	}

}