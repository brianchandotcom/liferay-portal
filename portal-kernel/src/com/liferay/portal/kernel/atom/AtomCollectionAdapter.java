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

package com.liferay.portal.kernel.atom;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

/**
 * @author Igor Spasic
 */
public interface AtomCollectionAdapter<E> {

	public static final int SC_BAD_CONTENT = 422;

	public static final int SC_BAD_REQUEST = 400;

	public static final int SC_CONFLICT = 409;

	public static final int SC_CREATED = 201;

	public static final int SC_FORBIDDEN = 403;

	public static final int SC_INTERNAL_SERVER_ERROR = 500;

	public static final int SC_NOT_FOUND = 404;

	public static final int SC_NOT_MODIFIED = 304;

	public static final int SC_OK = 200;

	public static final int SC_UNAUTHORIZED = 401;

	public void deleteEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws AtomException;

	public String getCollectionName();

	public E getEntry(
			String resourceName, AtomRequestContext atomRequestContext)
		throws AtomException;

	public List<String> getEntryAuthors(E entry);

	public AtomEntryContent getEntryContent(
		E entry, AtomRequestContext atomRequestContext);

	public String getEntryId(E entry);

	public String getEntrySummary(E entry);

	public String getEntryTitle(E entry);

	public Date getEntryUpdated(E entry);

	public Iterable<E> getFeedEntries(AtomRequestContext atomRequestContext)
		throws AtomException;

	public String getFeedTitle(AtomRequestContext atomRequestContext);

	public String getMediaContentType(E entry);

	public String getMediaName(E entry) throws AtomException;

	public InputStream getMediaStream(E entry) throws AtomException;

	public E postEntry(
			String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws AtomException;

	public E postMedia(
			String mimeType, String slug, InputStream inputStream,
			AtomRequestContext atomRequestContext)
		throws AtomException;

	public void putEntry(
			E entry, String title, String summary, String content, Date date,
			AtomRequestContext atomRequestContext)
		throws AtomException;

	public void putMedia(
			E entry, String mimeType, String slug, InputStream inputStream,
			AtomRequestContext atomRequestContext)
		throws AtomException;

}