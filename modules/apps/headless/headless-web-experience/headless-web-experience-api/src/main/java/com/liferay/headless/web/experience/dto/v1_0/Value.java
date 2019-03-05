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

package com.liferay.headless.web.experience.dto.v1_0;

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
@GraphQLName("Value")
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "Value")
public class Value {

	public String getData() {
		return data;
	}

	public ContentDocument getDocument() {
		return document;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public Geo getGeo() {
		return geo;
	}

	public String getLink() {
		return link;
	}

	public StructuredContent getStructuredContent() {
		return structuredContent;
	}

	public Long getStructuredContentId() {
		return structuredContentId;
	}

	public void setData(String data) {
		this.data = data;
	}

	@JsonIgnore
	public void setData(UnsafeSupplier<String, Exception> dataUnsafeSupplier) {
		try {
			data = dataUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDocument(ContentDocument document) {
		this.document = document;
	}

	@JsonIgnore
	public void setDocument(
		UnsafeSupplier<ContentDocument, Exception> documentUnsafeSupplier) {

		try {
			document = documentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	@JsonIgnore
	public void setDocumentId(
		UnsafeSupplier<Long, Exception> documentIdUnsafeSupplier) {

		try {
			documentId = documentIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	@JsonIgnore
	public void setGeo(UnsafeSupplier<Geo, Exception> geoUnsafeSupplier) {
		try {
			geo = geoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setLink(String link) {
		this.link = link;
	}

	@JsonIgnore
	public void setLink(UnsafeSupplier<String, Exception> linkUnsafeSupplier) {
		try {
			link = linkUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setStructuredContent(StructuredContent structuredContent) {
		this.structuredContent = structuredContent;
	}

	@JsonIgnore
	public void setStructuredContent(
		UnsafeSupplier<StructuredContent, Exception>
			structuredContentUnsafeSupplier) {

		try {
			structuredContent = structuredContentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void setStructuredContentId(Long structuredContentId) {
		this.structuredContentId = structuredContentId;
	}

	@JsonIgnore
	public void setStructuredContentId(
		UnsafeSupplier<Long, Exception> structuredContentIdUnsafeSupplier) {

		try {
			structuredContentId = structuredContentIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		StringBundler sb = new StringBundler(26);

		sb.append("{");

		sb.append("\"data\": ");

		sb.append("\"");
		sb.append(data);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"document\": ");

		sb.append(document);
		sb.append(", ");

		sb.append("\"documentId\": ");

		sb.append(documentId);
		sb.append(", ");

		sb.append("\"geo\": ");

		sb.append(geo);
		sb.append(", ");

		sb.append("\"link\": ");

		sb.append("\"");
		sb.append(link);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"structuredContent\": ");

		sb.append(structuredContent);
		sb.append(", ");

		sb.append("\"structuredContentId\": ");

		sb.append(structuredContentId);

		sb.append("}");

		return sb.toString();
	}

	@GraphQLField
	@JsonProperty
	protected String data;

	@GraphQLField
	@JsonProperty
	protected ContentDocument document;

	@GraphQLField
	@JsonProperty
	protected Long documentId;

	@GraphQLField
	@JsonProperty
	protected Geo geo;

	@GraphQLField
	@JsonProperty
	protected String link;

	@GraphQLField
	@JsonProperty
	protected StructuredContent structuredContent;

	@GraphQLField
	@JsonProperty
	protected Long structuredContentId;

}