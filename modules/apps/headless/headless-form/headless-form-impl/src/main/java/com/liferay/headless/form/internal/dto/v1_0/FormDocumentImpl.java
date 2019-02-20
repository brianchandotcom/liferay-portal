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
@GraphQLName("FormDocument")
@XmlRootElement(name = "FormDocument")
public class FormDocumentImpl implements FormDocument {

	public String getContentUrl() {
			return contentUrl;
	}

	public void setContentUrl(
			String contentUrl) {

			this.contentUrl = contentUrl;
	}

	@JsonIgnore
	public void setContentUrl(
			UnsafeSupplier<String, Throwable>
				contentUrlUnsafeSupplier) {

			try {
				contentUrl =
					contentUrlUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String contentUrl;
	public String getEncodingFormat() {
			return encodingFormat;
	}

	public void setEncodingFormat(
			String encodingFormat) {

			this.encodingFormat = encodingFormat;
	}

	@JsonIgnore
	public void setEncodingFormat(
			UnsafeSupplier<String, Throwable>
				encodingFormatUnsafeSupplier) {

			try {
				encodingFormat =
					encodingFormatUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String encodingFormat;
	public String getFileExtension() {
			return fileExtension;
	}

	public void setFileExtension(
			String fileExtension) {

			this.fileExtension = fileExtension;
	}

	@JsonIgnore
	public void setFileExtension(
			UnsafeSupplier<String, Throwable>
				fileExtensionUnsafeSupplier) {

			try {
				fileExtension =
					fileExtensionUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String fileExtension;
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
	public Number getSizeInBytes() {
			return sizeInBytes;
	}

	public void setSizeInBytes(
			Number sizeInBytes) {

			this.sizeInBytes = sizeInBytes;
	}

	@JsonIgnore
	public void setSizeInBytes(
			UnsafeSupplier<Number, Throwable>
				sizeInBytesUnsafeSupplier) {

			try {
				sizeInBytes =
					sizeInBytesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Number sizeInBytes;
	public String getTitle() {
			return title;
	}

	public void setTitle(
			String title) {

			this.title = title;
	}

	@JsonIgnore
	public void setTitle(
			UnsafeSupplier<String, Throwable>
				titleUnsafeSupplier) {

			try {
				title =
					titleUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String title;

}