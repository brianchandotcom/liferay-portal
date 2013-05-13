/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search;

import com.browseengine.bobo.api.BrowseFacet;
import com.browseengine.bobo.api.FacetAccessible;

import com.liferay.portal.kernel.search.facet.collector.FacetCollector;
import com.liferay.portal.kernel.search.facet.collector.TermCollector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Raymond Augé
 */
public class BoboFacetCollector implements FacetCollector {

	public BoboFacetCollector(
		String fieldName, FacetAccessible facetAccessible) {

		_fieldName = fieldName;
		_facetAccessible = facetAccessible;

		for (BrowseFacet browseFacet : _facetAccessible.getFacets()) {
			_termCollectors.add(new BoboTermCollector(browseFacet));
		}
	}

	@Override
	public String getFieldName() {
		return _fieldName;
	}

	@Override
	public TermCollector getTermCollector(String term) {
		return new BoboTermCollector(_facetAccessible.getFacet(term));
	}

	@Override
	public List<TermCollector> getTermCollectors() {
		return _termCollectors;
	}

	private FacetAccessible _facetAccessible;
	private String _fieldName;
	private List<TermCollector> _termCollectors =
		new ArrayList<TermCollector>();

}