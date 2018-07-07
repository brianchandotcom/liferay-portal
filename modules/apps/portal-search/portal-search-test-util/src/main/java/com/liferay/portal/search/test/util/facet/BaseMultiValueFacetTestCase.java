/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.test.util.facet;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author André de Oliveira
 */
public abstract class BaseMultiValueFacetTestCase extends BaseFacetTestCase {

	@Test
	public void testSelection() throws Exception {
		addDocuments(6, "one");
		addDocuments(5, "two");
		addDocuments(4, "three");
		addDocuments(3, "four");
		addDocuments(2, "five");

		assertSearchFacet(
			helper -> {
				MultiValueFacet multiValueFacet = helper.addFacet(
					this::createFacet);

				select(multiValueFacet, helper, "three", "one");

				helper.search();

				helper.assertResultCount(10);

				helper.assertFrequencies(
					multiValueFacet,
					Arrays.asList(
						"one=6", "two=5", "three=4", "four=3", "five=2"));
			});
	}

	protected MultiValueFacet createFacet(SearchContext searchContext) {
		MultiValueFacet multiValueFacet = new MultiValueFacet(searchContext);

		initFacet(multiValueFacet);

		multiValueFacet.setFieldName(Field.STATUS);

		return multiValueFacet;
	}

	@Override
	protected String getField() {
		return Field.STATUS;
	}

	protected void select(
		MultiValueFacet multiValueFacet, FacetTestHelper facetTestHelper,
		String... values) {

		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (String value : values) {
			jsonArray.put(value);
		}

		multiValueFacet.setValues(jsonArray);

		facetTestHelper.setSearchContextAttribute(
			multiValueFacet.getFieldId(), StringUtil.merge(values));
	}

}