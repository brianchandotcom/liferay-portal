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

import com.liferay.headless.form.dto.v1_0.Fields;
import com.liferay.headless.form.dto.v1_0.FormPages;
import com.liferay.petra.function.UnsafeSupplier;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLName;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("FormPages")
@XmlRootElement(name = "FormPages")
public class FormPagesImpl implements FormPages {

	public Fields[] getFields() {
			return fields;
	}

	public void setFields(
			Fields[] fields) {

			this.fields = (FieldsImpl[])fields;
	}

	@JsonIgnore
	public void setFields(
			UnsafeSupplier<Fields[], Throwable>
				fieldsUnsafeSupplier) {

			try {
				fields =
					(FieldsImpl[])fieldsUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected FieldsImpl[] fields;
	public String getHeadline() {
			return headline;
	}

	public void setHeadline(
			String headline) {

			this.headline = (String)headline;
	}

	@JsonIgnore
	public void setHeadline(
			UnsafeSupplier<String, Throwable>
				headlineUnsafeSupplier) {

			try {
				headline =
					(String)headlineUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String headline;
	public Long getId() {
			return id;
	}

	public void setId(
			Long id) {

			this.id = (Long)id;
	}

	@JsonIgnore
	public void setId(
			UnsafeSupplier<Long, Throwable>
				idUnsafeSupplier) {

			try {
				id =
					(Long)idUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long id;
	public String getText() {
			return text;
	}

	public void setText(
			String text) {

			this.text = (String)text;
	}

	@JsonIgnore
	public void setText(
			UnsafeSupplier<String, Throwable>
				textUnsafeSupplier) {

			try {
				text =
					(String)textUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String text;

}