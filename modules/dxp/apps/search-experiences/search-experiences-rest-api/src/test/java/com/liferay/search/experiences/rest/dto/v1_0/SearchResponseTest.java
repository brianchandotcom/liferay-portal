/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.dto.v1_0;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class SearchResponseTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testElasticsearchJSONWithNullValue() throws Exception {
		String elasticsearchJSON = "{\"hits\":{\"max_score\":null},\"took\":0}";

		SearchResponse searchResponse = new SearchResponse() {
			{
				response = JSONFactoryUtil.createJSONObject(elasticsearchJSON);
			}
		};

		Assert.assertEquals(
			"{\"response\": " + elasticsearchJSON + "}",
			searchResponse.toString());

		Assert.assertEquals(
			"{\"response\": {\"hits\":{},\"took\":0}}",
			String.valueOf(
				SearchResponse.unsafeToDTO(searchResponse.toString())));
	}

}