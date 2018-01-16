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

package com.liferay.portal.atom;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;

import java.util.Date;
import java.util.List;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;
import org.apache.abdera.model.Text;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.TargetType;
import org.apache.abdera.protocol.server.context.ResponseContextException;
import org.apache.abdera.protocol.server.impl.AbstractEntityCollectionAdapter;

/**
 * @author Igor Spasic
 */
public abstract class BaseEntityCollectionAdapter<T>
	extends AbstractEntityCollectionAdapter<T> {

	@Override
	public String getAuthor(RequestContext requestContext) {
		String author = null;

		try {
			Company company = AtomUtil.getCompany();

			author = company.getName();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return author;
	}

	@Override
	public String getHref(RequestContext requestContext) {
		return requestContext.urlFor(
			TargetType.TYPE_COLLECTION, collectionName);
	}

	@Override
	public String getId(RequestContext requestContext) {
		String id = AtomUtil.createIdTagPrefix(collectionName);

		id = id.concat("feed");

		return id;
	}

	@Override
	public String getId(T entry) {
		String id = AtomUtil.createIdTagPrefix(collectionName);

		id = id.concat("entry:");
		id = id.concat(getName(entry));

		return id;
	}

	@Override
	public String getName(T entry) {
		return getEntryId(entry);
	}

	protected BaseEntityCollectionAdapter(String collectionName) {
		this.collectionName = collectionName;
	}

	@Override
	protected String addEntryDetails(
			RequestContext requestContext, Entry entry, IRI feedIri, T entryObj)
		throws ResponseContextException {

		String link = getLink(entryObj, feedIri, requestContext);

		entry.addLink(link);

		List<Person> authors = getAuthors(entryObj, requestContext);

		if (authors != null) {
			for (Person author : authors) {
				entry.addAuthor(author);
			}
		}

		entry.setId(getId(entryObj));

		Text text = getSummary(entryObj, requestContext);

		if (text != null) {
			entry.setSummaryElement(text);
		}

		entry.setTitle(getTitle(entryObj));
		entry.setUpdated(getUpdated(entryObj));

		return link;
	}

	@Override
	protected void addFeedDetails(Feed feed, RequestContext requestContext)
		throws ResponseContextException {

		super.addFeedDetails(feed, requestContext);

		AtomPager atomPager = AtomUtil.getPager(requestContext);

		if (atomPager != null) {
			String url = String.valueOf(requestContext.getResolvedUri());

			atomPager.setFeedPagingLinks(feed, url);
		}
	}

	@Override
	protected Feed createFeedBase(RequestContext requestContext) {
		Abdera abdera = requestContext.getAbdera();

		Factory factory = abdera.getFactory();

		Feed feed = factory.newFeed();

		feed.addAuthor(getAuthor(requestContext));

		String url = String.valueOf(requestContext.getResolvedUri());

		url = AtomUtil.resolveCollectionUrl(url, collectionName);

		feed.addLink(url);
		feed.addLink(url, "self");

		feed.setId(getId(requestContext));
		feed.setTitle(getTitle(requestContext));
		feed.setUpdated(new Date());

		return feed;
	}

	protected abstract String getEntryId(T entry);

	protected String collectionName;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEntityCollectionAdapter.class);

}