/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class RankingTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDefaults() {
		Ranking.RankingBuilder rankingBuilder = new Ranking.RankingBuilder();

		Ranking ranking = rankingBuilder.build();

		Assert.assertEquals("[]", String.valueOf(ranking.getAliases()));
		Assert.assertEquals(null, ranking.getGroupExternalReferenceCode());
		Assert.assertEquals(
			"[]", String.valueOf(ranking.getHiddenDocumentIds()));
		Assert.assertEquals("[]", String.valueOf(ranking.getPins()));
		Assert.assertEquals("[]", String.valueOf(ranking.getQueryStrings()));
		Assert.assertEquals(
			null, ranking.getSXPBlueprintExternalReferenceCode());
	}

}