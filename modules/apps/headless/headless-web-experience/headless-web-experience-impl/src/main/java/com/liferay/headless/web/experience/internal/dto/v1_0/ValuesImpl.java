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

package com.liferay.headless.web.experience.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.web.experience.dto.v1_0.Value;
import com.liferay.headless.web.experience.dto.v1_0.Values;
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
@GraphQLName("Values")
@XmlRootElement(name = "Values")
public class ValuesImpl implements Values {

	public String getDataType() {
			return dataType;
	}

	public void setDataType(
			String dataType) {

			this.dataType = dataType;
	}

	@JsonIgnore
	public void setDataType(
			UnsafeSupplier<String, Throwable>
				dataTypeUnsafeSupplier) {

			try {
				dataType =
					dataTypeUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String dataType;
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
	public String getInputControl() {
			return inputControl;
	}

	public void setInputControl(
			String inputControl) {

			this.inputControl = inputControl;
	}

	@JsonIgnore
	public void setInputControl(
			UnsafeSupplier<String, Throwable>
				inputControlUnsafeSupplier) {

			try {
				inputControl =
					inputControlUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String inputControl;
	public String getLabel() {
			return label;
	}

	public void setLabel(
			String label) {

			this.label = label;
	}

	@JsonIgnore
	public void setLabel(
			UnsafeSupplier<String, Throwable>
				labelUnsafeSupplier) {

			try {
				label =
					labelUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String label;
	public String getName() {
			return name;
	}

	public void setName(
			String name) {

			this.name = name;
	}

	@JsonIgnore
	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier) {

			try {
				name =
					nameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String name;
	public Value getValue() {
			return value;
	}

	public void setValue(
			Value value) {

			this.value = value;
	}

	@JsonIgnore
	public void setValue(
			UnsafeSupplier<Value, Throwable>
				valueUnsafeSupplier) {

			try {
				value =
					valueUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Value value;

}