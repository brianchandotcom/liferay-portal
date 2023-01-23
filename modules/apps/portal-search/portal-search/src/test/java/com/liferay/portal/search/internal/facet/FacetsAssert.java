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

package com.liferay.portal.search.internal.facet;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.List;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class FacetsAssert {

	public static void assertFrequencies(
		String message, Facet facet, String expected) {

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		Assert.assertNotNull(termCollectors);

		List<String> termCollectorStrings = TransformUtil.transform(
			termCollectors, FacetsAssert::toString);

		Assert.assertEquals(message, expected, termCollectorStrings.toString());
	}

	public static void assertFrequencies(
		String facetName, SearchContext searchContext, List<String> expected) {

		String message = (String)searchContext.getAttribute("queryString");

		assertFrequencies(
			message, searchContext.getFacet(facetName), expected.toString());
	}

	protected static String toString(TermCollector termCollector) {
		return termCollector.getTerm() + "=" + termCollector.getFrequency();
	}

}