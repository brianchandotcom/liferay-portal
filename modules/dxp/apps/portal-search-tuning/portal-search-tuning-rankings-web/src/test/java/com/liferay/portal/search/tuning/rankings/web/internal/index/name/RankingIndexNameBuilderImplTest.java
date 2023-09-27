/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.index.name;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class RankingIndexNameBuilderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMultiTenancy() {
		_assertIndexName(
			2021, companyId -> "liferay-" + companyId,
			"liferay-2021-search-tuning-rankings");
	}

	private void _assertIndexName(
		long companyId, IndexNameBuilder indexNameBuilder, String expected) {

		RankingIndexNameBuilderImpl rankingIndexNameBuilderImpl =
			new RankingIndexNameBuilderImpl();

		ReflectionTestUtil.setFieldValue(
			rankingIndexNameBuilderImpl, "_indexNameBuilder", indexNameBuilder);

		RankingIndexName rankingIndexName =
			rankingIndexNameBuilderImpl.getRankingIndexName(companyId);

		Assert.assertEquals(expected, rankingIndexName.getIndexName());
	}

}