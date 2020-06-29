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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Javier Gamarra
 */
@JacksonXmlRootElement(localName = "facet")
public class Facet {

	public Facet() {
	}

	public Facet(String facetCriteria, List<FacetValue> facetValues) {
		_facetCriteria = facetCriteria;
		_facetValues = facetValues;
	}

	@JsonProperty("facetCriteria")
	public String getFacetCriteria() {
		return _facetCriteria;
	}

	@JsonProperty("facetValues")
	public List<FacetValue> getFacetValues() {
		return _facetValues;
	}

	public void setFacetCriteria(String facetCriteria) {
		_facetCriteria = facetCriteria;
	}

	public void setFacetValues(List<FacetValue> facetValues) {
		_facetValues = facetValues;
	}

	@JacksonXmlRootElement(localName = "facetValue")
	public static class FacetValue {

		public FacetValue() {
		}

		public FacetValue(String term, Integer numberOfOccurrences) {
			_term = term;
			_numberOfOccurrences = numberOfOccurrences;
		}

		@JsonProperty("numberOfOccurrences")
		public Integer getNumberOfOccurrences() {
			return _numberOfOccurrences;
		}

		@JsonProperty("term")
		public String getTerm() {
			return _term;
		}

		private Integer _numberOfOccurrences;
		private String _term;

	}

	private String _facetCriteria;
	private List<FacetValue> _facetValues = new ArrayList<>();

}