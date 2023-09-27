/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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