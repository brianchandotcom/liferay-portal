/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.index.name;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.tuning.synonyms.index.name.SynonymSetIndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SynonymSetIndexNameBuilderImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testMultiTenancy() {
		_assertIndexName(
			2021, companyId -> "liferay-" + companyId,
			"liferay-2021-search-tuning-synonyms");
	}

	private void _assertIndexName(
		int companyId, IndexNameBuilder indexNameBuilder, String expected) {

		SynonymSetIndexNameBuilderImpl synonymSetIndexNameBuilderImpl =
			new SynonymSetIndexNameBuilderImpl();

		ReflectionTestUtil.setFieldValue(
			synonymSetIndexNameBuilderImpl, "_indexNameBuilder",
			indexNameBuilder);

		SynonymSetIndexName synonymSetIndexName =
			synonymSetIndexNameBuilderImpl.getSynonymSetIndexName(companyId);

		Assert.assertEquals(expected, synonymSetIndexName.getIndexName());
	}

}