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

package com.liferay.batch.planner.rest.client.aggregation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * @author Matija Petanjek
 * @generated
 */
@Generated("")
public class Facet {

	public Facet() {
	}

	public Facet(String facetCriteria, List<FacetValue> facetValues) {
		_facetCriteria = facetCriteria;
		_facetValues = facetValues;
	}

	public String getFacetCriteria() {
		return _facetCriteria;
	}

	public List<FacetValue> getFacetValues() {
		return _facetValues;
	}

	public void setFacetCriteria(String facetCriteria) {
		_facetCriteria = facetCriteria;
	}

	public void setFacetValues(List<FacetValue> facetValues) {
		_facetValues = facetValues;
	}

	public static class FacetValue {

		public FacetValue() {
		}

		public FacetValue(Integer numberOfOccurrences, String term) {
			_numberOfOccurrences = numberOfOccurrences;
			_term = term;
		}

		public Integer getNumberOfOccurrences() {
			return _numberOfOccurrences;
		}

		public String getTerm() {
			return _term;
		}

		private Integer _numberOfOccurrences;
		private String _term;

	}

	private String _facetCriteria;
	private List<FacetValue> _facetValues = new ArrayList<>();

}