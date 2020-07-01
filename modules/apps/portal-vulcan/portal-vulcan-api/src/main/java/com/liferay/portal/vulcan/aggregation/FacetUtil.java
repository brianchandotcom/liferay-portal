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

package com.liferay.portal.vulcan.aggregation;

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;
import com.liferay.portal.kernel.search.facet.config.FacetConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class FacetUtil {

	public static Facet toFacet(
		com.liferay.portal.kernel.search.facet.Facet facet) {

		FacetCollector facetCollector = facet.getFacetCollector();

		List<TermCollector> termCollectors = facetCollector.getTermCollectors();

		if (termCollectors.isEmpty()) {
			return null;
		}

		List<Facet.FacetValue> facetValues = new ArrayList<>();

		for (TermCollector termCollector : termCollectors) {
			facetValues.add(
				new Facet.FacetValue(
					termCollector.getTerm(), termCollector.getFrequency()));
		}

		FacetConfiguration facetConfiguration = facet.getFacetConfiguration();

		String fieldName = facet.getFieldName();

		if (facetConfiguration.getLabel() != null) {
			fieldName = facetConfiguration.getLabel();
		}

		return new Facet(fieldName, facetValues);
	}

}