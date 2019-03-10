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

package com.liferay.bulk.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("Vocabulary")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Vocabulary")
public class Vocabulary {

	public Category[] getCategories() {
		return categories;
	}

	public void setCategories(Category[] categories) {
		this.categories = categories;
	}

	@JsonIgnore
	public void setCategories(
		UnsafeSupplier<Category[], Exception> categoriesUnsafeSupplier) {

		try {
			categories = categoriesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Category[] categories;

	public Boolean getMultiValued() {
		return multiValued;
	}

	public void setMultiValued(Boolean multiValued) {
		this.multiValued = multiValued;
	}

	@JsonIgnore
	public void setMultiValued(
		UnsafeSupplier<Boolean, Exception> multiValuedUnsafeSupplier) {

		try {
			multiValued = multiValuedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean multiValued;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	public Long getVocabularyId() {
		return vocabularyId;
	}

	public void setVocabularyId(Long vocabularyId) {
		this.vocabularyId = vocabularyId;
	}

	@JsonIgnore
	public void setVocabularyId(
		UnsafeSupplier<Long, Exception> vocabularyIdUnsafeSupplier) {

		try {
			vocabularyId = vocabularyIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long vocabularyId;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"categories\": ");

		if (categories == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < categories.length; i++) {
				sb.append(categories[i]);

				if ((i + 1) < categories.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"multiValued\": ");

		sb.append(multiValued);
		sb.append(", ");

		sb.append("\"name\": ");

		sb.append("\"");
		sb.append(name);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"vocabularyId\": ");

		sb.append(vocabularyId);

		sb.append("}");

		return sb.toString();
	}

}