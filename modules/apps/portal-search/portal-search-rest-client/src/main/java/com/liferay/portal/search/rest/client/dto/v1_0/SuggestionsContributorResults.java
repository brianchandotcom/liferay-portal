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
import com.liferay.portal.search.rest.client.serdes.v1_0.SuggestionsContributorResultsSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Petteri Karttunen
 * @generated
 */
@Generated("")
public class SuggestionsContributorResults implements Cloneable, Serializable {

	public static SuggestionsContributorResults toDTO(String json) {
		return SuggestionsContributorResultsSerDes.toDTO(json);
	}

	public Object getAttributes() {
		return attributes;
	}

	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}

	public void setAttributes(
		UnsafeSupplier<Object, Exception> attributesUnsafeSupplier) {

		try {
			attributes = attributesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Object attributes;

	public String getDisplayGroupName() {
		return displayGroupName;
	}

	public void setDisplayGroupName(String displayGroupName) {
		this.displayGroupName = displayGroupName;
	}

	public void setDisplayGroupName(
		UnsafeSupplier<String, Exception> displayGroupNameUnsafeSupplier) {

		try {
			displayGroupName = displayGroupNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String displayGroupName;

	public Suggestion[] getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(Suggestion[] suggestions) {
		this.suggestions = suggestions;
	}

	public void setSuggestions(
		UnsafeSupplier<Suggestion[], Exception> suggestionsUnsafeSupplier) {

		try {
			suggestions = suggestionsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Suggestion[] suggestions;

	@Override
	public SuggestionsContributorResults clone()
		throws CloneNotSupportedException {

		return (SuggestionsContributorResults)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SuggestionsContributorResults)) {
			return false;
		}

		SuggestionsContributorResults suggestionsContributorResults =
			(SuggestionsContributorResults)object;

		return Objects.equals(
			toString(), suggestionsContributorResults.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SuggestionsContributorResultsSerDes.toJSON(this);
	}

}