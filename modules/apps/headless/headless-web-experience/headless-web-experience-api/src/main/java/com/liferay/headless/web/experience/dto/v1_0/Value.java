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

import com.liferay.petra.function.UnsafeSupplier;

import javax.annotation.Generated;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
public interface Value {

	public String getData();

	public void setData(
			String data);

	public void setData(
			UnsafeSupplier<String, Throwable>
				dataUnsafeSupplier);
	public ContentDocument getDocument();

	public void setDocument(
			ContentDocument document);

	public void setDocument(
			UnsafeSupplier<ContentDocument, Throwable>
				documentUnsafeSupplier);
	public Long getDocumentId();

	public void setDocumentId(
			Long documentId);

	public void setDocumentId(
			UnsafeSupplier<Long, Throwable>
				documentIdUnsafeSupplier);
	public Geo getGeo();

	public void setGeo(
			Geo geo);

	public void setGeo(
			UnsafeSupplier<Geo, Throwable>
				geoUnsafeSupplier);
	public String getLink();

	public void setLink(
			String link);

	public void setLink(
			UnsafeSupplier<String, Throwable>
				linkUnsafeSupplier);
	public StructuredContent getStructuredContent();

	public void setStructuredContent(
			StructuredContent structuredContent);

	public void setStructuredContent(
			UnsafeSupplier<StructuredContent, Throwable>
				structuredContentUnsafeSupplier);
	public Long getStructuredContentId();

	public void setStructuredContentId(
			Long structuredContentId);

	public void setStructuredContentId(
			UnsafeSupplier<Long, Throwable>
				structuredContentIdUnsafeSupplier);

}