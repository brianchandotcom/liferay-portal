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

package com.liferay.rss.internal.export;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.rss.export.RSSExporter;
import com.liferay.rss.model.SyndContent;
import com.liferay.rss.model.SyndEnclosure;
import com.liferay.rss.model.SyndEntry;
import com.liferay.rss.model.SyndFeed;
import com.liferay.rss.model.SyndLink;

import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEnclosureImpl;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndLinkImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

import java.util.ArrayList;
import java.util.List;

import org.jdom.IllegalDataException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = RSSExporter.class)
public class RSSExporterImpl implements RSSExporter {

	@Override
	public String export(SyndFeed syndFeed) {
		com.sun.syndication.feed.synd.SyndFeed realSyndFeed = _toRealSyndFeed(
			syndFeed);

		SyndFeedOutput output = new SyndFeedOutput();

		try {
			return output.outputString(realSyndFeed);
		}
		catch (IllegalDataException ide) {

			// LEP-4450

			_regexpStrip(realSyndFeed);

			try {
				return output.outputString(realSyndFeed);
			}
			catch (FeedException fe) {
				throw new SystemException(fe);
			}
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
	}

	private static void _regexpStrip(
		com.sun.syndication.feed.synd.SyndFeed syndFeed) {

		syndFeed.setTitle(_regexpStrip(syndFeed.getTitle()));
		syndFeed.setDescription(_regexpStrip(syndFeed.getDescription()));

		@SuppressWarnings("unchecked")
		List<com.sun.syndication.feed.synd.SyndEntry> syndEntries =
			syndFeed.getEntries();

		for (com.sun.syndication.feed.synd.SyndEntry syndEntry : syndEntries) {
			syndEntry.setTitle(_regexpStrip(syndEntry.getTitle()));

			com.sun.syndication.feed.synd.SyndContent syndContent =
				syndEntry.getDescription();

			syndContent.setValue(_regexpStrip(syndContent.getValue()));
		}
	}

	private static String _regexpStrip(String text) {
		text = Normalizer.normalizeToAscii(text);

		char[] array = text.toCharArray();

		for (int i = 0; i < array.length; i++) {
			String s = String.valueOf(array[i]);

			if (!s.matches(_REGEXP_STRIP)) {
				array[i] = CharPool.SPACE;
			}
		}

		return new String(array);
	}

	private com.sun.syndication.feed.synd.SyndContent _toRealSyncContent(
		SyndContent syndContent) {

		com.sun.syndication.feed.synd.SyndContent realSyndContent =
			new SyndContentImpl();

		realSyndContent.setType(syndContent.getType());
		realSyndContent.setValue(syndContent.getValue());

		return realSyndContent;
	}

	private List<com.sun.syndication.feed.synd.SyndEnclosure>
		_toRealSyndEnclosures(List<SyndEnclosure> syndEnclosures) {

		if (syndEnclosures == null) {
			return null;
		}

		List<com.sun.syndication.feed.synd.SyndEnclosure> realSyndEnclosures =
			new ArrayList<>();

		for (SyndEnclosure syndEnclosure : syndEnclosures) {
			com.sun.syndication.feed.synd.SyndEnclosure realSyndEnclosure =
				new SyndEnclosureImpl();

			realSyndEnclosure.setLength(syndEnclosure.getLength());
			realSyndEnclosure.setType(syndEnclosure.getType());
			realSyndEnclosure.setUrl(syndEnclosure.getUrl());

			realSyndEnclosures.add(realSyndEnclosure);
		}

		return realSyndEnclosures;
	}

	private List<com.sun.syndication.feed.synd.SyndEntry> _toRealSyndEntries(
		List<SyndEntry> syndEntries) {

		if (syndEntries == null) {
			return null;
		}

		List<com.sun.syndication.feed.synd.SyndEntry> realSyndEntries =
			new ArrayList<>();

		for (SyndEntry syndEntry : syndEntries) {
			com.sun.syndication.feed.synd.SyndEntry realSyndEntry =
				new SyndEntryImpl();

			realSyndEntry.setAuthor(syndEntry.getAuthor());
			realSyndEntry.setDescription(
				_toRealSyncContent(syndEntry.getDescription()));
			realSyndEntry.setEnclosures(
				_toRealSyndEnclosures(syndEntry.getEnclosures()));
			realSyndEntry.setLink(syndEntry.getLink());
			realSyndEntry.setLinks(_toRealSyndLinks(syndEntry.getLinks()));
			realSyndEntry.setPublishedDate(syndEntry.getPublishedDate());
			realSyndEntry.setTitle(syndEntry.getTitle());
			realSyndEntry.setUpdatedDate(syndEntry.getUpdatedDate());
			realSyndEntry.setUri(syndEntry.getUri());

			realSyndEntries.add(realSyndEntry);
		}

		return realSyndEntries;
	}

	private com.sun.syndication.feed.synd.SyndFeed _toRealSyndFeed(
		SyndFeed syndFeed) {

		com.sun.syndication.feed.synd.SyndFeed realSyndFeed =
			new SyndFeedImpl();

		realSyndFeed.setDescription(syndFeed.getDescription());
		realSyndFeed.setEntries(_toRealSyndEntries(syndFeed.getEntries()));
		realSyndFeed.setFeedType(syndFeed.getFeedType());
		realSyndFeed.setLinks(_toRealSyndLinks(syndFeed.getLinks()));
		realSyndFeed.setPublishedDate(syndFeed.getPublishedDate());
		realSyndFeed.setTitle(syndFeed.getTitle());
		realSyndFeed.setUri(syndFeed.getUri());

		return realSyndFeed;
	}

	private List<com.sun.syndication.feed.synd.SyndLink> _toRealSyndLinks(
		List<SyndLink> syndLinks) {

		if (syndLinks == null) {
			return null;
		}

		List<com.sun.syndication.feed.synd.SyndLink> realSyndLinks =
			new ArrayList<>();

		for (SyndLink syndLink : syndLinks) {
			com.sun.syndication.feed.synd.SyndLink realSyndLink =
				new SyndLinkImpl();

			realSyndLink.setHref(syndLink.getHref());
			realSyndLink.setLength(syndLink.getLength());
			realSyndLink.setRel(syndLink.getRel());
			realSyndLink.setType(syndLink.getType());

			realSyndLinks.add(realSyndLink);
		}

		return realSyndLinks;
	}

	private static final String _REGEXP_STRIP = "[\\d\\w]";

}