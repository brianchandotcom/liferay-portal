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

package com.liferay.commerce.google.merchant.internal.xml.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kayleen Lim
 *
 * Represents Google Merchant Center Feed
 */
@JacksonXmlRootElement(localName = "feed")
@JsonPropertyOrder({"xmlns", "xmlns:g", "title", "link", "updated", "entries"})
public class Feed {

	public Feed() {
		_xmlns = _XMLNS;
		_xmlnsg = _XMLNS_GOOGLE;
	}

	public void addEntry(Entry entry) {
		_entries.add(entry);
	}

	public void setEntries(List<Entry> entries) {
		_entries = entries;
	}

	public void setLink(Link link) {
		_link = link;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setUpdated(String updated) {
		_updated = updated;
	}

	public void setXmlns(String xmlns) {
		_xmlns = xmlns;
	}

	public void setXmlnsg(String xmlnsg) {
		_xmlnsg = xmlnsg;
	}

	private static final String _XMLNS = "http://www.w3.org/2005/Atom";

	private static final String _XMLNS_GOOGLE = "http://base.google.com/ns/1.0";

	@JacksonXmlProperty(localName = "entry")
	private List<Entry> _entries = new ArrayList<>();

	@JacksonXmlProperty(localName = "link")
	private Link _link;

	@JacksonXmlProperty(localName = "title")
	private String _title;

	@JacksonXmlProperty(localName = "updated")
	private String _updated;

	@JacksonXmlProperty(isAttribute = true, localName = "xmlns")
	private String _xmlns;

	@JacksonXmlProperty(isAttribute = true, localName = "xmlns:g")
	private String _xmlnsg;

}