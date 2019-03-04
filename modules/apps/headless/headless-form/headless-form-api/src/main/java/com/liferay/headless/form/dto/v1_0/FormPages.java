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

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@GraphQLName("FormPages")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "FormPages")
public class FormPages {

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
		UnsafeSupplier<Fields[], Exception> fieldsUnsafeSupplier) {

		try {
			fields = fieldsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	@JsonIgnore
	public void setHeadline(
		UnsafeSupplier<String, Exception> headlineUnsafeSupplier) {

		try {
			headline = headlineUnsafeSupplier.get();
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

	public void setText(String text) {
		this.text = text;
	}

	@JsonIgnore
	public void setText(UnsafeSupplier<String, Exception> textUnsafeSupplier) {
		try {
			text = textUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{");

		sb.append("\"fields\": ");

		sb.append("\"");
		sb.append(fields);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"headline\": ");

		sb.append("\"");
		sb.append(headline);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"text\": ");

		sb.append("\"");
		sb.append(text);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
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