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

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

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

	public void setData(String data) {
		this.data = data;
	}

	@JsonIgnore
	public void setData(UnsafeSupplier<String, Exception> dataUnsafeSupplier) {
		try {
			data = dataUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String data;

	public ContentDocument getDocument() {
		return document;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ContentDocument document;

	public Long getDocumentId() {
		return documentId;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Long documentId;

	@Schema(description = "https://www.schema.org/GeoCoordinates")
	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	@JsonIgnore
	public void setGeo(UnsafeSupplier<Geo, Exception> geoUnsafeSupplier) {
		try {
			geo = geoUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Geo geo;

	public ContentDocument getImage() {
		return image;
	}

	public void setImage(ContentDocument image) {
		this.image = image;
	}

	@JsonIgnore
	public void setImage(
		UnsafeSupplier<ContentDocument, Exception> imageUnsafeSupplier) {

		try {
			image = imageUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	protected ContentDocument image;

	public String getImageDescription() {
		return imageDescription;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	@JsonIgnore
	public void setImageDescription(
		UnsafeSupplier<String, Exception> imageDescriptionUnsafeSupplier) {

		try {
			imageDescription = imageDescriptionUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected String imageDescription;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	@JsonIgnore
	public void setImageId(
		UnsafeSupplier<Long, Exception> imageIdUnsafeSupplier) {

		try {
			imageId = imageIdUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Long imageId;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@JsonIgnore
	public void setLink(UnsafeSupplier<String, Exception> linkUnsafeSupplier) {
		try {
			link = linkUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String link;

	public Long getStructuredContentId() {
		return structuredContentId;
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
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	protected Long structuredContentId;

	public StructuredContentLink getStructuredContentLink() {
		return structuredContentLink;
	}

	public void setStructuredContentLink(
		StructuredContentLink structuredContentLink) {

		this.structuredContentLink = structuredContentLink;
	}

	@JsonIgnore
	public void setStructuredContentLink(
		UnsafeSupplier<StructuredContentLink, Exception>
			structuredContentLinkUnsafeSupplier) {

		try {
			structuredContentLink = structuredContentLinkUnsafeSupplier.get();
		}
		catch (RuntimeException re) {
			throw re;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected StructuredContentLink structuredContentLink;

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Value)) {
			return false;
		}

		Value value = (Value)object;

		return Objects.equals(toString(), value.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		StringBundler sb = new StringBundler();

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

		sb.append("\"image\": ");

		sb.append(image);
		sb.append(", ");

		sb.append("\"imageDescription\": ");

		sb.append("\"");
		sb.append(imageDescription);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"imageId\": ");

		sb.append(imageId);
		sb.append(", ");

		sb.append("\"link\": ");

		sb.append("\"");
		sb.append(link);
		sb.append("\"");
		sb.append(", ");

		sb.append("\"structuredContentId\": ");

		sb.append(structuredContentId);
		sb.append(", ");

		sb.append("\"structuredContentLink\": ");

		sb.append(structuredContentLink);

		sb.append("}");

		return sb.toString();
	}

}