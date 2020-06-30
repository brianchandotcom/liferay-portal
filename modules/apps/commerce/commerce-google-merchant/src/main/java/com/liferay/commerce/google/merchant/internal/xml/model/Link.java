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

/**
 * @author Kayleen Lim
 *
 * Represents Link, a required attribute of Atom 1.0 XML
 *
 * Example: <code><link rel="self" href="http://www.example.com"/></code>
 */
@JacksonXmlRootElement(localName = "link")
@JsonPropertyOrder({"rel", "href"})
public class Link {

	public Link() {
	}

	public Link(String href) {
		_href = href;
	}

	public void setHref(String href) {
		_href = href;
	}

	@JacksonXmlProperty(isAttribute = true, localName = "href")
	private String _href;

	@JacksonXmlProperty(isAttribute = true, localName = "rel")
	private String _rel = "self";

}