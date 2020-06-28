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

/**
 * @author Javier Gamarra
 */
@JacksonXmlRootElement(localName = "facet")
public class Facet {

	public Facet() {
	}

	public Facet(String term, Integer numberOfOccurrences) {
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