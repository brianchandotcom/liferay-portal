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

	public String getHeadline() {
		return headline;
	}

	public Long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public void setFields(Fields[] fields) {
		this.fields = fields;
	}

	@JsonIgnore
	public void setFields(
		UnsafeSupplier<Fields[], Throwable> fieldsUnsafeSupplier) {

		try {
			fields = fieldsUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	@JsonIgnore
	public void setHeadline(
		UnsafeSupplier<String, Throwable> headlineUnsafeSupplier) {

		try {
			headline = headlineUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public void setId(UnsafeSupplier<Long, Throwable> idUnsafeSupplier) {
		try {
			id = idUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public void setText(String text) {
		this.text = text;
	}

	@JsonIgnore
	public void setText(UnsafeSupplier<String, Throwable> textUnsafeSupplier) {
		try {
			text = textUnsafeSupplier.get();
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	@GraphQLField
	@JsonProperty
	protected Fields[] fields;

	@GraphQLField
	@JsonProperty
	protected String headline;

	@GraphQLField
	@JsonProperty
	protected Long id;

	@GraphQLField
	@JsonProperty
	protected String text;

}