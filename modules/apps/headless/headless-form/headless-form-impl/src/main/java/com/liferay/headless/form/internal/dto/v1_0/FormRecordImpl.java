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

package com.liferay.headless.form.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.form.dto.v1_0.Creator;
import com.liferay.headless.form.dto.v1_0.FieldValues;
import com.liferay.headless.form.dto.v1_0.Form;
import com.liferay.headless.form.dto.v1_0.FormRecord;
import com.liferay.petra.function.UnsafeSupplier;

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
@XmlRootElement(name = "FormRecord")
public class FormRecordImpl implements FormRecord {

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
		this.creator = (CreatorImpl)creator;
	}

	@JsonIgnore
	public void setCreator(
		UnsafeSupplier<Creator, Throwable> creatorUnsafeSupplier) {

		try {
			creator = (CreatorImpl)creatorUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = (Date)dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
		UnsafeSupplier<Date, Throwable> dateCreatedUnsafeSupplier) {

		try {
			dateCreated = (Date)dateCreatedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = (Date)dateModified;
	}

	@JsonIgnore
	public void setDateModified(
		UnsafeSupplier<Date, Throwable> dateModifiedUnsafeSupplier) {

		try {
			dateModified = (Date)dateModifiedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDatePublished(Date datePublished) {
		this.datePublished = (Date)datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
		UnsafeSupplier<Date, Throwable> datePublishedUnsafeSupplier) {

		try {
			datePublished = (Date)datePublishedUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setDraft(Boolean draft) {
		this.draft = (Boolean)draft;
	}

	@JsonIgnore
	public void setDraft(
		UnsafeSupplier<Boolean, Throwable> draftUnsafeSupplier) {

		try {
			draft = (Boolean)draftUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setFieldValues(FieldValues[] fieldValues) {
		this.fieldValues = (FieldValuesImpl[])fieldValues;
	}

	@JsonIgnore
	public void setFieldValues(
		UnsafeSupplier<FieldValues[], Throwable> fieldValuesUnsafeSupplier) {

		try {
			fieldValues = (FieldValuesImpl[])fieldValuesUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setForm(Form form) {
		this.form = (FormImpl)form;
	}

	@JsonIgnore
	public void setForm(UnsafeSupplier<Form, Throwable> formUnsafeSupplier) {
		try {
			form = (FormImpl)formUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setFormId(Long formId) {
		this.formId = (Long)formId;
	}

	@JsonIgnore
	public void setFormId(
		UnsafeSupplier<Long, Throwable> formIdUnsafeSupplier) {

		try {
			formId = (Long)formIdUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setId(Long id) {
		this.id = (Long)id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = (Long)idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected CreatorImpl creator;

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
	protected FieldValuesImpl[] fieldValues;

	@GraphQLField
	@JsonProperty
	protected FormImpl form;

	@GraphQLField
	@JsonProperty
	protected Long formId;

	@GraphQLField
	@JsonProperty
	protected Long id;

}