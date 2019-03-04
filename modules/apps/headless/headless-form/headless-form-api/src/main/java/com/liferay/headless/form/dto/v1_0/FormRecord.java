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

package com.liferay.headless.form.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import java.util.Date;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("FormRecord")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "FormRecord")
public class FormRecord {

	public Creator getCreator() {
		return creator;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public Date getDatePublished() {
		return datePublished;
	}

	public Boolean getDraft() {
		return draft;
	}

	public FieldValues[] getFieldValues() {
		return fieldValues;
	}

	public Form getForm() {
		return form;
	}

	public Long getFormId() {
		return formId;
	}

	public Long getId() {
		return id;
	}

	public void setCreator(Creator creator) {
		this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Exception> creatorUnsafeSupplier) {

		try {
			creator = creatorUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Exception> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = dateCreatedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Exception> dateModifiedUnsafeSupplier) {

		try {
			dateModified = dateModifiedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
		UnsafeSupplier<Date, Exception> datePublishedUnsafeSupplier) {

		try {
			datePublished = datePublishedUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDraft(Boolean draft) {
		this.draft = draft;
	}

	@JsonIgnore
	public void setDraft(
		UnsafeSupplier<Boolean, Exception> draftUnsafeSupplier) {

		try {
			draft = draftUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setFieldValues(FieldValues[] fieldValues) {
		this.fieldValues = fieldValues;
	}

	@JsonIgnore
	public void setFieldValues(
		UnsafeSupplier<FieldValues[], Exception> fieldValuesUnsafeSupplier) {

		try {
			fieldValues = fieldValuesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setForm(Form form) {
		this.form = form;
	}

	@JsonIgnore
	public void setForm(UnsafeSupplier<Form, Exception> formUnsafeSupplier) {
		try {
			form = formUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	@JsonIgnore
	public void setFormId(
		UnsafeSupplier<Long, Exception> formIdUnsafeSupplier) {

		try {
			formId = formIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Exception> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(36);

		sb.append("{");

		sb.append("\"creator\": ");

		sb.append(creator);
		sb.append(", ");

		sb.append("\"dateCreated\": ");

		sb.append("\"");
		sb.append(dateCreated);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"dateModified\": ");

		sb.append("\"");
		sb.append(dateModified);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"datePublished\": ");

		sb.append("\"");
		sb.append(datePublished);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"draft\": ");

		sb.append(draft);
		sb.append(", ");

		sb.append("\"fieldValues\": ");

		sb.append("\"");
		sb.append(fieldValues);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"form\": ");

		sb.append(form);
		sb.append(", ");

		sb.append("\"formId\": ");

		sb.append(formId);
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected Creator creator;

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;

	@GraphQLField
	@JsonProperty
	protected Date dateModified;

	@GraphQLField
	@JsonProperty
	protected Date datePublished;

	@GraphQLField
	@JsonProperty
	protected Boolean draft;

	@GraphQLField
	@JsonProperty
	protected FieldValues[] fieldValues;

	@GraphQLField
	@JsonProperty
	protected Form form;

	@GraphQLField
	@JsonProperty
	protected Long formId;

	@GraphQLField
	@JsonProperty
	protected Long id;

}