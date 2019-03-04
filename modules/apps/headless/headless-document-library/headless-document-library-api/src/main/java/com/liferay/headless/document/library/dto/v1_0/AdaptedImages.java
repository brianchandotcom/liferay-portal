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

package com.liferay.headless.document.library.dto.v1_0;

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
@GraphQLName("AdaptedImages")
@JsonFilter("VulcanFilter")
@XmlRootElement(name = "AdaptedImages")
public class AdaptedImages {

	public String getContentUrl() {
		return contentUrl;
	}

	public Number getHeight() {
		return height;
	}

	public String getResolutionName() {
		return resolutionName;
	}

	public Number getSizeInBytes() {
		return sizeInBytes;
	}

	public Number getWidth() {
		return width;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	@JsonIgnore
	public void setContentUrl(
		UnsafeSupplier<String, Exception> contentUrlUnsafeSupplier) {

		try {
			contentUrl = contentUrlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setHeight(Number height) {
		this.height = height;
	}

	@JsonIgnore
	public void setHeight(
		UnsafeSupplier<Number, Exception> heightUnsafeSupplier) {

		try {
			height = heightUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setResolutionName(String resolutionName) {
		this.resolutionName = resolutionName;
	}

	@JsonIgnore
	public void setResolutionName(
		UnsafeSupplier<String, Exception> resolutionNameUnsafeSupplier) {

		try {
			resolutionName = resolutionNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setSizeInBytes(Number sizeInBytes) {
		this.sizeInBytes = sizeInBytes;
	}

	@JsonIgnore
	public void setSizeInBytes(
		UnsafeSupplier<Number, Exception> sizeInBytesUnsafeSupplier) {

		try {
			sizeInBytes = sizeInBytesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setWidth(Number width) {
		this.width = width;
	}

	@JsonIgnore
	public void setWidth(
		UnsafeSupplier<Number, Exception> widthUnsafeSupplier) {

		try {
			width = widthUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(20);

		sb.append("{");

		sb.append("\"contentUrl\": ");

		sb.append("\"");
		sb.append(contentUrl);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"height\": ");

		sb.append(height);
		sb.append(", ");

		sb.append("\"resolutionName\": ");

		sb.append("\"");
		sb.append(resolutionName);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"sizeInBytes\": ");

		sb.append(sizeInBytes);
		sb.append(", ");

		sb.append("\"width\": ");

		sb.append(width);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String contentUrl;

	@GraphQLField
	@JsonProperty
	protected Number height;

	@GraphQLField
	@JsonProperty
	protected String resolutionName;

	@GraphQLField
	@JsonProperty
	protected Number sizeInBytes;

	@GraphQLField
	@JsonProperty
	protected Number width;

}