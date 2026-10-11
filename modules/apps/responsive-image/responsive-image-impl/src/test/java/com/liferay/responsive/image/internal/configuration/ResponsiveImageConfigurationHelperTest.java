/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image.internal.configuration;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageConfigurationHelperTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetScopeKeyNamesTheNarrowestScope() {
		long companyId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();

		Assert.assertEquals(
			"group#" + groupId,
			_responsiveImageConfigurationHelper.getScopeKey(
				companyId, groupId));

		Assert.assertEquals(
			"company#" + companyId,
			_responsiveImageConfigurationHelper.getScopeKey(companyId, 0));
		Assert.assertEquals(
			"system", _responsiveImageConfigurationHelper.getScopeKey(0, 0));
	}

	@Test
	public void testGetScopeKeyTellsTheScopesApart() {
		long id = RandomTestUtil.randomLong();

		Assert.assertNotEquals(
			_responsiveImageConfigurationHelper.getScopeKey(0, id),
			_responsiveImageConfigurationHelper.getScopeKey(id, 0));
	}

	private final ResponsiveImageConfigurationHelper
		_responsiveImageConfigurationHelper =
			new ResponsiveImageConfigurationHelper(
				Mockito.mock(ConfigurationProvider.class),
				Mockito.mock(Portal.class));

}