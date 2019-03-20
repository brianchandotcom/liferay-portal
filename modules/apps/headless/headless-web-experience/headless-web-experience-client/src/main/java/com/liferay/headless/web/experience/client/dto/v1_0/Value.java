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

package com.liferay.headless.web.experience.client.dto.v1_0;

import com.liferay.headless.web.experience.client.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public class Value {

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setData(UnsafeSupplier<String, Exception> dataUnsafeSupplier) {
		try {
			data = dataUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String data;

	public com.liferay.headless.web.experience.dto.v1_0.ContentDocument
		getDocument() {

		return document;
	}

	public void setDocument(
		com.liferay.headless.web.experience.dto.v1_0.ContentDocument document) {

		this.document = document;
	}

	public void setDocument(
		UnsafeSupplier
			<com.liferay.headless.web.experience.dto.v1_0.ContentDocument,
			 Exception> documentUnsafeSupplier) {

		try {
			document = documentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected com.liferay.headless.web.experience.dto.v1_0.ContentDocument
		document;

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public void setDocumentId(
		UnsafeSupplier<Long, Exception> documentIdUnsafeSupplier) {

		try {
			documentId = documentIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long documentId;

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public void setGeo(UnsafeSupplier<Geo, Exception> geoUnsafeSupplier) {
		try {
			geo = geoUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Geo geo;

	public com.liferay.headless.web.experience.dto.v1_0.StructuredContentImage
		getImage() {

		return image;
	}

	public void setImage(
		com.liferay.headless.web.experience.dto.v1_0.StructuredContentImage
			image) {

		this.image = image;
	}

	public void setImage(
		UnsafeSupplier
			<com.liferay.headless.web.experience.dto.v1_0.
				StructuredContentImage,
			 Exception> imageUnsafeSupplier) {

		try {
			image = imageUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected
		com.liferay.headless.web.experience.dto.v1_0.StructuredContentImage
			image;

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setLink(UnsafeSupplier<String, Exception> linkUnsafeSupplier) {
		try {
			link = linkUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String link;

	public Long getStructuredContentId() {
		return structuredContentId;
	}

	public void setStructuredContentId(Long structuredContentId) {
		this.structuredContentId = structuredContentId;
	}

	public void setStructuredContentId(
		UnsafeSupplier<Long, Exception> structuredContentIdUnsafeSupplier) {

		try {
			structuredContentId = structuredContentIdUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long structuredContentId;

	public StructuredContentLink getStructuredContentLink() {
		return structuredContentLink;
	}

	public void setStructuredContentLink(
		StructuredContentLink structuredContentLink) {

		this.structuredContentLink = structuredContentLink;
	}

	public void setStructuredContentLink(
		UnsafeSupplier<StructuredContentLink, Exception>
			structuredContentLinkUnsafeSupplier) {

		try {
			structuredContentLink = structuredContentLinkUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected StructuredContentLink structuredContentLink;

}