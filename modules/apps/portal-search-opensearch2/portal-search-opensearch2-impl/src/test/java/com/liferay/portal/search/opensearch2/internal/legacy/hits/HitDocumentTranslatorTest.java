/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.legacy.hits;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.opensearch.client.json.JsonData;
import org.opensearch.client.opensearch.core.search.Hit;

/**
 * @author Joshua Cords
 */
public class HitDocumentTranslatorTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Test
	public void testDocumentWithIgnoredField() {
		Hit.Builder<JsonData> builder = new Hit.Builder<>();

		builder.fields("_ignored", JsonData.of("value"));
		builder.ignored("_ignored");
		builder.index("0");

		HitDocumentTranslator hitDocumentTranslator =
			new HitDocumentTranslatorImpl();

		Document document = hitDocumentTranslator.translate(builder.build());

		Assert.assertEquals(StringPool.BLANK, document.get("_ignored"));
	}

}