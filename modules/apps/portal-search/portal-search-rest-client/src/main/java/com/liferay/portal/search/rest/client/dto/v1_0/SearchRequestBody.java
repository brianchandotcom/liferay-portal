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
import com.liferay.portal.search.rest.client.serdes.v1_0.SearchRequestBodySerDes;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class SearchRequestBody implements Cloneable, Serializable {

	public static SearchRequestBody toDTO(String json) {
		return SearchRequestBodySerDes.toDTO(json);
	}

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

	public FacetConfiguration[] getFacetConfigurations() {
		return facetConfigurations;
	}

	public void setFacetConfigurations(
		FacetConfiguration[] facetConfigurations) {

		this.facetConfigurations = facetConfigurations;
	}

	public void setFacetConfigurations(
		UnsafeSupplier<FacetConfiguration[], Exception>
			facetConfigurationsUnsafeSupplier) {

		try {
			facetConfigurations = facetConfigurationsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FacetConfiguration[] facetConfigurations;

	@Override
	public SearchRequestBody clone() throws CloneNotSupportedException {
		return (SearchRequestBody)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SearchRequestBody)) {
			return false;
		}

		SearchRequestBody searchRequestBody = (SearchRequestBody)object;

		return Objects.equals(toString(), searchRequestBody.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SearchRequestBodySerDes.toJSON(this);
	}

}