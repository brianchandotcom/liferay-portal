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

package com.liferay.portal.search.rest.client.dto.v1_0;

import com.liferay.portal.search.rest.client.function.UnsafeSupplier;
import com.liferay.portal.search.rest.client.serdes.v1_0.FacetSerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class Facet implements Cloneable, Serializable {

	public static Facet toDTO(String json) {
		return FacetSerDes.toDTO(json);
	}

	public String getAggregationName() {
		return aggregationName;
	}

	public void setAggregationName(String aggregationName) {
		this.aggregationName = aggregationName;
	}

	public void setAggregationName(
		UnsafeSupplier<String, Exception> aggregationNameUnsafeSupplier) {

		try {
			aggregationName = aggregationNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String aggregationName;

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public void setAttributes(
		UnsafeSupplier<Map<String, Object>, Exception>
			attributesUnsafeSupplier) {

		try {
			attributes = attributesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> attributes;

	public Integer getFrequencyThreshold() {
		return frequencyThreshold;
	}

	public void setFrequencyThreshold(Integer frequencyThreshold) {
		this.frequencyThreshold = frequencyThreshold;
	}

	public void setFrequencyThreshold(
		UnsafeSupplier<Integer, Exception> frequencyThresholdUnsafeSupplier) {

		try {
			frequencyThreshold = frequencyThresholdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer frequencyThreshold;

	public Integer getMaxTerms() {
		return maxTerms;
	}

	public void setMaxTerms(Integer maxTerms) {
		this.maxTerms = maxTerms;
	}

	public void setMaxTerms(
		UnsafeSupplier<Integer, Exception> maxTermsUnsafeSupplier) {

		try {
			maxTerms = maxTermsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Integer maxTerms;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

	public void setValues(
		UnsafeSupplier<Object[], Exception> valuesUnsafeSupplier) {

		try {
			values = valuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object[] values;

	@Override
	public Facet clone() throws CloneNotSupportedException {
		return (Facet)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Facet)) {
			return false;
		}

		Facet facet = (Facet)object;

		return Objects.equals(toString(), facet.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FacetSerDes.toJSON(this);
	}

}