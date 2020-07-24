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

package com.liferay.portal.workflow.metrics.rest.client.aggregation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * @author Rafael Praxedes
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