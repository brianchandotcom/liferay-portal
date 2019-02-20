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

	public void setCreator(
			Creator creator) {

			this.creator = creator;
	}

	@JsonIgnore
	public void setCreator(
			UnsafeSupplier<Creator, Throwable>
				creatorUnsafeSupplier) {

			try {
				creator =
					creatorUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Creator creator;
	public Date getDateCreated() {
			return dateCreated;
	}

	public void setDateCreated(
			Date dateCreated) {

			this.dateCreated = dateCreated;
	}

	@JsonIgnore
	public void setDateCreated(
			UnsafeSupplier<Date, Throwable>
				dateCreatedUnsafeSupplier) {

			try {
				dateCreated =
					dateCreatedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dateCreated;
	public Date getDateModified() {
			return dateModified;
	}

	public void setDateModified(
			Date dateModified) {

			this.dateModified = dateModified;
	}

	@JsonIgnore
	public void setDateModified(
			UnsafeSupplier<Date, Throwable>
				dateModifiedUnsafeSupplier) {

			try {
				dateModified =
					dateModifiedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date dateModified;
	public Date getDatePublished() {
			return datePublished;
	}

	public void setDatePublished(
			Date datePublished) {

			this.datePublished = datePublished;
	}

	@JsonIgnore
	public void setDatePublished(
			UnsafeSupplier<Date, Throwable>
				datePublishedUnsafeSupplier) {

			try {
				datePublished =
					datePublishedUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Date datePublished;
	public Boolean getDraft() {
			return draft;
	}

	public void setDraft(
			Boolean draft) {

			this.draft = draft;
	}

	@JsonIgnore
	public void setDraft(
			UnsafeSupplier<Boolean, Throwable>
				draftUnsafeSupplier) {

			try {
				draft =
					draftUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Boolean draft;
	public FieldValues[] getFieldValues() {
			return fieldValues;
	}

	public void setFieldValues(
			FieldValues[] fieldValues) {

			this.fieldValues = fieldValues;
	}

	@JsonIgnore
	public void setFieldValues(
			UnsafeSupplier<FieldValues[], Throwable>
				fieldValuesUnsafeSupplier) {

			try {
				fieldValues =
					fieldValuesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected FieldValues[] fieldValues;
	public Form getForm() {
			return form;
	}

	public void setForm(
			Form form) {

			this.form = form;
	}

	@JsonIgnore
	public void setForm(
			UnsafeSupplier<Form, Throwable>
				formUnsafeSupplier) {

			try {
				form =
					formUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Form form;
	public Long getFormId() {
			return formId;
	}

	public void setFormId(
			Long formId) {

			this.formId = formId;
	}

	@JsonIgnore
	public void setFormId(
			UnsafeSupplier<Long, Throwable>
				formIdUnsafeSupplier) {

			try {
				formId =
					formIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long formId;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;

}