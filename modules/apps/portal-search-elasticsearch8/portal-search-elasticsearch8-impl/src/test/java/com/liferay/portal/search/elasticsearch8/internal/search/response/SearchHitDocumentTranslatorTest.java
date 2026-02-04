/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.response;

import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.json.JsonData;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Joshua Cords
 */
public class SearchHitDocumentTranslatorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testDocumentWithIgnoredField() {
		SearchHitDocumentTranslator searchHitDocumentTranslator =
			new SearchHitDocumentTranslatorImpl();

		Hit.Builder<JsonData> builder = new Hit.Builder<>();

		builder.fields("_ignored", JsonData.of("value"));
		builder.ignored("_ignored");
		builder.index("0");

		Document document = searchHitDocumentTranslator.translate(
			builder.build());

		Assert.assertEquals(StringPool.BLANK, document.get("_ignored"));
	}

}