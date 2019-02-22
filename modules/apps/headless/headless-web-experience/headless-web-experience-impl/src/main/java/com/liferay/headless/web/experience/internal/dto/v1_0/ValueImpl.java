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

import com.liferay.headless.web.experience.dto.v1_0.ContentDocument;
import com.liferay.headless.web.experience.dto.v1_0.Geo;
import com.liferay.headless.web.experience.dto.v1_0.StructuredContent;
import com.liferay.headless.web.experience.dto.v1_0.Value;
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
@GraphQLName("Value")
@XmlRootElement(name = "Value")
public class ValueImpl implements Value {

	public String getData() {
			return data;
	}

	public void setData(
			String data) {

			this.data = (String)data;
	}

	@JsonIgnore
	public void setData(
			UnsafeSupplier<String, Throwable>
				dataUnsafeSupplier) {

			try {
				data =
					(String)dataUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String data;
	public ContentDocument getDocument() {
			return document;
	}

	public void setDocument(
			ContentDocument document) {

			this.document = (ContentDocumentImpl)document;
	}

	@JsonIgnore
	public void setDocument(
			UnsafeSupplier<ContentDocument, Throwable>
				documentUnsafeSupplier) {

			try {
				document =
					(ContentDocumentImpl)documentUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected ContentDocumentImpl document;
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
	public Geo getGeo() {
			return geo;
	}

	public void setGeo(
			Geo geo) {

			this.geo = (GeoImpl)geo;
	}

	@JsonIgnore
	public void setGeo(
			UnsafeSupplier<Geo, Throwable>
				geoUnsafeSupplier) {

			try {
				geo =
					(GeoImpl)geoUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected GeoImpl geo;
	public String getLink() {
			return link;
	}

	public void setLink(
			String link) {

			this.link = (String)link;
	}

	@JsonIgnore
	public void setLink(
			UnsafeSupplier<String, Throwable>
				linkUnsafeSupplier) {

			try {
				link =
					(String)linkUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected String link;
	public StructuredContent getStructuredContent() {
			return structuredContent;
	}

	public void setStructuredContent(
			StructuredContent structuredContent) {

			this.structuredContent = (StructuredContentImpl)structuredContent;
	}

	@JsonIgnore
	public void setStructuredContent(
			UnsafeSupplier<StructuredContent, Throwable>
				structuredContentUnsafeSupplier) {

			try {
				structuredContent =
					(StructuredContentImpl)structuredContentUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected StructuredContentImpl structuredContent;
	public Long getStructuredContentId() {
			return structuredContentId;
	}

	public void setStructuredContentId(
			Long structuredContentId) {

			this.structuredContentId = (Long)structuredContentId;
	}

	@JsonIgnore
	public void setStructuredContentId(
			UnsafeSupplier<Long, Throwable>
				structuredContentIdUnsafeSupplier) {

			try {
				structuredContentId =
					(Long)structuredContentIdUnsafeSupplier.get();
	}
			catch (Throwable t) {
				throw new RuntimeException(t);
	}
	}

	@GraphQLField
	@JsonProperty
	protected Long structuredContentId;

}