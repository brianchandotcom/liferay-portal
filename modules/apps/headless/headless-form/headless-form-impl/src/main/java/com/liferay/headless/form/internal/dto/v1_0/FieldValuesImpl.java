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

import com.liferay.headless.form.dto.v1_0.FieldValues;
import com.liferay.headless.form.dto.v1_0.FormDocument;
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
@GraphQLName("FieldValues")
@XmlRootElement(name = "FieldValues")
public class FieldValuesImpl implements FieldValues {

	public FormDocument getDocument() {
			return document;
	}

	public void setDocument(
			FormDocument document) {

			this.document = (FormDocumentImpl)document;
	}

	@JsonIgnore
	public void setDocument(
			UnsafeSupplier<FormDocument, Throwable>
				documentUnsafeSupplier) {

			try {
				document =
					(FormDocumentImpl)documentUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected FormDocumentImpl document;
	public Long getDocumentId() {
			return documentId;
	}

	public void setDocumentId(
			Long documentId) {

			this.documentId = (Long)documentId;
	}

	@JsonIgnore
	public void setDocumentId(
			UnsafeSupplier<Long, Throwable>
				documentIdUnsafeSupplier) {

			try {
				documentId =
					(Long)documentIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long documentId;
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
	public String getName() {
			return name;
	}

	public void setName(
			String name) {

			this.name = (String)name;
	}

	@JsonIgnore
	public void setName(
			UnsafeSupplier<String, Throwable>
				nameUnsafeSupplier) {

			try {
				name =
					(String)nameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String name;
	public String getValue() {
			return value;
	}

	public void setValue(
			String value) {

			this.value = (String)value;
	}

	@JsonIgnore
	public void setValue(
			UnsafeSupplier<String, Throwable>
				valueUnsafeSupplier) {

			try {
				value =
					(String)valueUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String value;

}