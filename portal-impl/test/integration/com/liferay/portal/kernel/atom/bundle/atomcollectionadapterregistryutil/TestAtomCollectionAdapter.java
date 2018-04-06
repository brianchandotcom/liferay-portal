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

package com.liferay.portal.kernel.atom.bundle.atomcollectionadapterregistryutil;

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomEntryContent;
import com.liferay.portal.kernel.atom.AtomRequestContext;

import java.io.InputStream;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Peter Fellwock
 */
@Component(
	immediate = true, property = "service.ranking:Integer=" + Integer.MAX_VALUE
)
public class TestAtomCollectionAdapter
	implements AtomCollectionAdapter<Object> {

	public static final String COLLECTION_NAME = "TestAtomCollectionAdapter";

	@Override
	public void deleteEntry(
		String resourceName, AtomRequestContext atomRequestContext) {
	}

	@Override
	public String getCollectionName() {
		return COLLECTION_NAME;
	}

	@Override
	public Object getEntry(
		String resourceName, AtomRequestContext atomRequestContext) {

		return null;
	}

	@Override
	public List<String> getEntryAuthors(Object entry) {
		return null;
	}

	@Override
	public AtomEntryContent getEntryContent(
		Object entry, AtomRequestContext atomRequestContext) {

		return null;
	}

	@Override
	public String getEntryId(Object entry) {
		return null;
	}

	@Override
	public String getEntrySummary(Object entry) {
		return null;
	}

	@Override
	public String getEntryTitle(Object entry) {
		return null;
	}

	@Override
	public Date getEntryUpdated(Object entry) {
		return null;
	}

	@Override
	public Iterable<Object> getFeedEntries(
		AtomRequestContext atomRequestContext) {

		return null;
	}

	@Override
	public String getFeedTitle(AtomRequestContext atomRequestContext) {
		return null;
	}

	@Override
	public String getMediaContentType(Object entry) {
		return null;
	}

	@Override
	public String getMediaName(Object entry) {
		return null;
	}

	@Override
	public InputStream getMediaStream(Object entry) {
		return null;
	}

	@Override
	public Object postEntry(
		String title, String summary, String content, Date date,
		AtomRequestContext atomRequestContext) {

		return null;
	}

	@Override
	public Object postMedia(
		String mimeType, String slug, InputStream inputStream,
		AtomRequestContext atomRequestContext) {

		return null;
	}

	@Override
	public void putEntry(
		Object entry, String title, String summary, String content, Date date,
		AtomRequestContext atomRequestContext) {
	}

	@Override
	public void putMedia(
		Object entry, String mimeType, String slug, InputStream inputStream,
		AtomRequestContext atomRequestContext) {
	}

}