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
@GraphQLName("Validation")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "Validation")
public class Validation {

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getExpression() {
		return expression;
	}

	public Long getId() {
		return id;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@JsonIgnore
	public void setErrorMessage(
		UnsafeSupplier<String, Exception> errorMessageUnsafeSupplier) {

		try {
			errorMessage = errorMessageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@JsonIgnore
	public void setExpression(
		UnsafeSupplier<String, Exception> expressionUnsafeSupplier) {

		try {
			expression = expressionUnsafeSupplier.get();
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
		StringBundler sb = new StringBundler(14);

		sb.append("{");

		sb.append("\"errorMessage\": ");

		sb.append("\"");
		sb.append(errorMessage);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"expression\": ");

		sb.append("\"");
		sb.append(expression);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"id\": ");

		sb.append(id);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String errorMessage;

	@GraphQLField
	@JsonProperty
	protected String expression;

	@GraphQLField
	@JsonProperty
	protected Long id;

}