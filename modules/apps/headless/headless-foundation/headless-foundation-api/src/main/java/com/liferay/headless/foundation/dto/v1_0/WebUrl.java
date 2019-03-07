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

package com.liferay.headless.foundation.dto.v1_0;

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
@GraphQLName("WebUrl")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "WebUrl")
public class WebUrl {

	public Long getId() {
		return id;
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

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Long id;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonIgnore
	public void setUrl(UnsafeSupplier<String, Exception> urlUnsafeSupplier) {
		try {
			url = urlUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String url;

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	@JsonIgnore
	public void setUrlType(
		UnsafeSupplier<String, Exception> urlTypeUnsafeSupplier) {

		try {
			urlType = urlTypeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String urlType;

	public String toString() {
		StringBundler sb = new StringBundler();

		sb.append("{");

		sb.append("\"id\": ");

		sb.append(id);
		sb.append(", ");

		sb.append("\"url\": ");

		sb.append("\"");
		sb.append(url);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"urlType\": ");

		sb.append("\"");
		sb.append(urlType);
		sb.append("\"");

		sb.append("}");

		return sb.toString();
	}

}