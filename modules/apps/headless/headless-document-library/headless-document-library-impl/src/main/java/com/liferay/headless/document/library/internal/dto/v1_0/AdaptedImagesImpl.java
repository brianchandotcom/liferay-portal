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

package com.liferay.headless.document.library.internal.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.headless.document.library.dto.v1_0.AdaptedImages;
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
@GraphQLName("AdaptedImages")
@XmlRootElement(name = "AdaptedImages")
public class AdaptedImagesImpl implements AdaptedImages {

	public String getContentUrl() {
			return contentUrl;
	}

	public void setContentUrl(
			String contentUrl) {

			this.contentUrl = (String)contentUrl;
	}

	@JsonIgnore
	public void setContentUrl(
			UnsafeSupplier<String, Throwable>
				contentUrlUnsafeSupplier) {

			try {
				contentUrl =
					(String)contentUrlUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String contentUrl;
	public Number getHeight() {
			return height;
	}

	public void setHeight(
			Number height) {

			this.height = (Number)height;
	}

	@JsonIgnore
	public void setHeight(
			UnsafeSupplier<Number, Throwable>
				heightUnsafeSupplier) {

			try {
				height =
					(Number)heightUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Number height;
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
	public String getResolutionName() {
			return resolutionName;
	}

	public void setResolutionName(
			String resolutionName) {

			this.resolutionName = (String)resolutionName;
	}

	@JsonIgnore
	public void setResolutionName(
			UnsafeSupplier<String, Throwable>
				resolutionNameUnsafeSupplier) {

			try {
				resolutionName =
					(String)resolutionNameUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String resolutionName;
	public Number getSizeInBytes() {
			return sizeInBytes;
	}

	public void setSizeInBytes(
			Number sizeInBytes) {

			this.sizeInBytes = (Number)sizeInBytes;
	}

	@JsonIgnore
	public void setSizeInBytes(
			UnsafeSupplier<Number, Throwable>
				sizeInBytesUnsafeSupplier) {

			try {
				sizeInBytes =
					(Number)sizeInBytesUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Number sizeInBytes;
	public Number getWidth() {
			return width;
	}

	public void setWidth(
			Number width) {

			this.width = (Number)width;
	}

	@JsonIgnore
	public void setWidth(
			UnsafeSupplier<Number, Throwable>
				widthUnsafeSupplier) {

			try {
				width =
					(Number)widthUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Number width;

}