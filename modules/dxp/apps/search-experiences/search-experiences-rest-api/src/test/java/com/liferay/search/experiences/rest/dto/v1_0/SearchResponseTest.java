/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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