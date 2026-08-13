/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.search.spi.index.configuration.contributor;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mikel Lorza
 */
public class CMSTextSimilarityCompanyIndexConfigurationContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testContributeMappings() {
		String mappingsJSON = StringUtil.read(
			CMSTextSimilarityCompanyIndexConfigurationContributor.class,
			"dependencies/text-similarity-type-mappings.json");

		Assert.assertTrue(mappingsJSON.contains("\"textSimilarityKeys\""));
		Assert.assertTrue(mappingsJSON.contains("\"keyword\""));
	}

}