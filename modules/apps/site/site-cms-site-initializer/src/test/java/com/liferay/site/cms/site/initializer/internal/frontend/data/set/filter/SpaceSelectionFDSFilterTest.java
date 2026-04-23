/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.frontend.data.set.filter;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Gabriel Lima
 */
public class SpaceSelectionFDSFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetAPIURLFiltersByTypeSpace() {
		SpaceSelectionFDSFilter spaceSelectionFDSFilter =
			new SpaceSelectionFDSFilter();

		String spaceSelectionFDSFilterAPIURL =
			spaceSelectionFDSFilter.getAPIURL();

		Assert.assertTrue(
			spaceSelectionFDSFilterAPIURL.contains("filter=type eq 'Space'"));
	}

}