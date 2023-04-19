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

	public Facet[] getFacets() {
		return facets;
	}

	public void setFacets(Facet[] facets) {
		this.facets = facets;
	}

	public void setFacets(
		UnsafeSupplier<Facet[], Exception> facetsUnsafeSupplier) {

		try {
			facets = facetsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Facet[] facets;

	public Map<String, Object> getSearchContextAttributes() {
		return searchContextAttributes;
	}

	public void setSearchContextAttributes(
		Map<String, Object> searchContextAttributes) {

		this.searchContextAttributes = searchContextAttributes;
	}

	public void setSearchContextAttributes(
		UnsafeSupplier<Map<String, Object>, Exception>
			searchContextAttributesUnsafeSupplier) {

		try {
			searchContextAttributes =
				searchContextAttributesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Map<String, Object> searchContextAttributes;

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