/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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