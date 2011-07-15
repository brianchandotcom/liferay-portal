/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import org.apache.abdera.protocol.server.RequestContext;
import org.apache.abdera.protocol.server.context.ResponseContextException;

/**
 * @author Igor Spasic
 */
public class AtomCollectionAdapterWrapper<E>
	extends BaseEntityCollectionAdapter<E> {

	public AtomCollectionAdapterWrapper(
		AtomCollectionAdapter<E> atomCollectionAdapter) {

		super(atomCollectionAdapter.getCollectionName().toLowerCase());

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
		List<Person> persons = new ArrayList<Person>();

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
		Abdera abdera = requestContext.getAbdera();

		AtomEntryContent atomContent =
			_atomCollectionAdapter.getEntryContent(
				entry, new AtomRequestContextImpl(requestContext));

		Factory factory = abdera.getFactory();

		Content content = null;

		switch (atomContent.getType()) {
			case HTML: content = factory.newContent(Content.Type.HTML); break;
			case MEDIA: content = factory.newContent(Content.Type.MEDIA); break;
			case TEXT: content = factory.newContent(Content.Type.TEXT); break;
			case XHTML: content = factory.newContent(Content.Type.XHTML); break;
			case XML: content = factory.newContent(Content.Type.XML); break;
		}

		content.setText(atomContent.getText());

		if (atomContent.getSrcLink() != null) {
			content.setSrc(atomContent.getSrcLink());
		}

		if (atomContent.getMimeType() != null) {
			content.setMimeType(atomContent.getMimeType());
		}

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
	public String getTitle(E entry) {
		return _atomCollectionAdapter.getEntryTitle(entry);
	}

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
		RequestContext requestContext) throws ResponseContextException {

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

	private AtomCollectionAdapter<E> _atomCollectionAdapter;

}
