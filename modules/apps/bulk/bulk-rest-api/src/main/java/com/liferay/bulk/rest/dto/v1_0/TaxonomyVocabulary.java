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

import java.util.Objects;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Alejandro Tardín
 * @generated
 */
@Generated("")
@GraphQLName("TaxonomyVocabulary")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "TaxonomyVocabulary")
public class TaxonomyVocabulary {

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
		catch (RuntimeException re) {
			throw re;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String name;

	public Boolean getRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

	@JsonIgnore
	public void setRequired(
		UnsafeSupplier<Boolean, Exception> requiredUnsafeSupplier) {

		try {
			required = requiredUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean required;

	public TaxonomyCategory[] getTaxonomyCategories() {
		return taxonomyCategories;
	}

	public void setTaxonomyCategories(TaxonomyCategory[] taxonomyCategories) {
		this.taxonomyCategories = taxonomyCategories;
	}

	@JsonIgnore
	public void setTaxonomyCategories(
		UnsafeSupplier<TaxonomyCategory[], Exception>
			taxonomyCategoriesUnsafeSupplier) {

		try {
			taxonomyCategories = taxonomyCategoriesUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected TaxonomyCategory[] taxonomyCategories;

	public Long getTaxonomyVocabularyId() {
		return taxonomyVocabularyId;
	}

	public void setTaxonomyVocabularyId(Long taxonomyVocabularyId) {
		this.taxonomyVocabularyId = taxonomyVocabularyId;
	}

	@JsonIgnore
	public void setTaxonomyVocabularyId(
		UnsafeSupplier<Long, Exception> taxonomyVocabularyIdUnsafeSupplier) {

		try {
			taxonomyVocabularyId = taxonomyVocabularyIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long taxonomyVocabularyId;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TaxonomyVocabulary)) {
			return false;
		}

		TaxonomyVocabulary taxonomyVocabulary = (TaxonomyVocabulary)object;

		return Objects.equals(toString(), taxonomyVocabulary.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"multiValued\": ");

		if (multiValued == null) {
			sb.append("null");
		}
		else {
			sb.append(multiValued);
		}

		sb.append(", ");

		sb.append("\"name\": ");

		if (name == null) {
			sb.append("null");
		}
		else {
			sb.append("\"");
			sb.append(name);
			sb.append("\"");
		}

		sb.append(", ");

		sb.append("\"required\": ");

		if (required == null) {
			sb.append("null");
		}
		else {
			sb.append(required);
		}

		sb.append(", ");

		sb.append("\"taxonomyCategories\": ");

		if (taxonomyCategories == null) {
			sb.append("null");
		}
		else {
			sb.append("[");

			for (int i = 0; i < taxonomyCategories.length; i++) {
				sb.append(taxonomyCategories[i]);

				if ((i + 1) < taxonomyCategories.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append(", ");

		sb.append("\"taxonomyVocabularyId\": ");

		if (taxonomyVocabularyId == null) {
			sb.append("null");
		}
		else {
			sb.append(taxonomyVocabularyId);
		}

		sb.append("}");

		return sb.toString();
	}

}