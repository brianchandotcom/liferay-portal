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

import com.liferay.portal.kernel.atom.AtomCollectionAdapter;
import com.liferay.portal.kernel.atom.AtomEntryContent;
import com.liferay.portal.kernel.atom.AtomException;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimeType;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Content;
import org.apache.abdera.model.Person;
import org.apache.abdera.model.Text;
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.context.ResponseContextException;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterWrapper<E>
	extends BaseEntityCollectionAdapter<E> {

	public AtomCollectionAdapterWrapper(
		AtomCollectionAdapter<E> atomCollectionAdapter) {

		super(
			StringUtil.toLowerCase(atomCollectionAdapter.getCollectionName()));

		_atomCollectionAdapter = atomCollectionAdapter;
	}

	@Override
	public void deleteEntry(String resourceName, RequestContext requestContext)
		throws ResponseContextException {

		try {
			_atomCollectionAdapter.deleteEntry(
				resourceName, new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	public List<Person> getAuthors(E entry, RequestContext requestContext) {
		List<Person> persons = new ArrayList<>();

		List<String> authors = _atomCollectionAdapter.getEntryAuthors(entry);

		for (String author : authors) {
			Abdera abdera = requestContext.getAbdera();

			Factory factory = abdera.getFactory();

			Person person = factory.newAuthor();

			person.setName(author);

			persons.add(person);
		}

		return persons;
	}

	@Override
	public Object getContent(E entry, RequestContext requestContext) {
		AtomEntryContent atomEntryContent =
			_atomCollectionAdapter.getEntryContent(
				entry, new AtomRequestContextImpl(requestContext));

		Content content = newContent(
			atomEntryContent.getType(), requestContext);

		if (atomEntryContent.getMimeType() != null) {
			content.setMimeType(atomEntryContent.getMimeType());
		}

		if (atomEntryContent.getSrcLink() != null) {
			content.setSrc(atomEntryContent.getSrcLink());
		}

		content.setText(atomEntryContent.getText());

		return content;
	}

	@Override
	public String getContentType(E entry) {
		return _atomCollectionAdapter.getMediaContentType(entry);
	}

	@Override
	public Iterable<E> getEntries(RequestContext requestContext)
		throws ResponseContextException {

		try {
			return _atomCollectionAdapter.getFeedEntries(
				new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	public E getEntry(String resourceName, RequestContext requestContext)
		throws ResponseContextException {

		try {
			if (resourceName.endsWith(":media")) {
				resourceName = resourceName.substring(
					0, resourceName.length() - 6);
			}

			return _atomCollectionAdapter.getEntry(
				resourceName, new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	public String getMediaName(E entry) throws ResponseContextException {
		try {
			return _atomCollectionAdapter.getMediaName(entry);
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	public InputStream getMediaStream(E entry) throws ResponseContextException {
		try {
			return _atomCollectionAdapter.getMediaStream(entry);
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	public Text getSummary(E entry, RequestContext request) {
		Abdera abdera = new Abdera();

		Factory factory = abdera.getFactory();

		Text summary = factory.newSummary();

		summary.setValue(_atomCollectionAdapter.getEntrySummary(entry));

		return summary;
	}

	@Override
	public String getTitle(E entry) {
		return _atomCollectionAdapter.getEntryTitle(entry);
	}

	@Override
	public String getTitle(RequestContext requestContext) {
		return _atomCollectionAdapter.getFeedTitle(
			new AtomRequestContextImpl(requestContext));
	}

	@Override
	public Date getUpdated(E entry) {
		return _atomCollectionAdapter.getEntryUpdated(entry);
	}

	@Override
	public E postEntry(
			String title, IRI id, String summary, Date updated,
			List<Person> authors, Content content,
			RequestContext requestContext)
		throws ResponseContextException {

		try {
			return _atomCollectionAdapter.postEntry(
				title, summary, content.getText(), updated,
				new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	public E postMedia(
			MimeType mimeType, String slug, InputStream inputStream,
			RequestContext requestContext)
		throws ResponseContextException {

		try {
			return _atomCollectionAdapter.postMedia(
				mimeType.toString(), slug, inputStream,
				new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	public void putEntry(
			E entry, String title, Date updated, List<Person> authors,
			String summary, Content content, RequestContext requestContext)
		throws ResponseContextException {

		try {
			_atomCollectionAdapter.putEntry(
				entry, title, summary, content.getText(), updated,
				new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	public void putMedia(
			E entry, MimeType contentType, String slug, InputStream inputStream,
			RequestContext requestContext)
		throws ResponseContextException {

		try {
			_atomCollectionAdapter.putMedia(
				entry, contentType.toString(), slug, inputStream,
				new AtomRequestContextImpl(requestContext));
		}
		catch (AtomException ae) {
			throw new ResponseContextException(
				ae.getErrorCode(), ae.getCause());
		}
	}

	@Override
	protected String getEntryId(E entry) {
		return _atomCollectionAdapter.getEntryId(entry);
	}

	protected Content newContent(
		AtomEntryContent.Type atomEntryContentType,
		RequestContext requestContext) {

		Abdera abdera = requestContext.getAbdera();

		Factory factory = abdera.getFactory();

		if (atomEntryContentType == AtomEntryContent.Type.HTML) {
			return factory.newContent(Content.Type.HTML);
		}
		else if (atomEntryContentType == AtomEntryContent.Type.MEDIA) {
			return factory.newContent(Content.Type.MEDIA);
		}
		else if (atomEntryContentType == AtomEntryContent.Type.TEXT) {
			return factory.newContent(Content.Type.TEXT);
		}
		else if (atomEntryContentType == AtomEntryContent.Type.XHTML) {
			return factory.newContent(Content.Type.XHTML);
		}
		else if (atomEntryContentType == AtomEntryContent.Type.XML) {
			return factory.newContent(Content.Type.XML);
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	private final AtomCollectionAdapter<E> _atomCollectionAdapter;

}