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
 * Represents Google Merchant Center Entry in a Feed
 */
@JacksonXmlRootElement(localName = "entry")
@JsonPropertyOrder(
	{
		"id", "title", "description", "link", "image_link", "condition",
		"availability", "price", "shipping"
	}
)
public class Entry {

	public void setAvailability(String availability) {
		_availability = availability;
	}

	public void setCondition(String condition) {
		_condition = condition;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setImageLink(String imageLink) {
		_imageLink = imageLink;
	}

	public void setLink(String link) {
		_link = link;
	}

	public void setPrice(String price) {
		_price = price;
	}

	public void setShipping(Shipping shipping) {
		_shipping = shipping;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@JacksonXmlProperty(
		localName = "availability", namespace = "http://base.google.com/ns/1.0"
	)
	private String _availability;

	@JacksonXmlProperty(
		localName = "condition", namespace = "http://base.google.com/ns/1.0"
	)
	private String _condition;

	@JacksonXmlProperty(
		localName = "description", namespace = "http://base.google.com/ns/1.0"
	)
	private String _description;

	@JacksonXmlProperty(
		localName = "id", namespace = "http://base.google.com/ns/1.0"
	)
	private String _id;

	@JacksonXmlProperty(
		localName = "image_link", namespace = "http://base.google.com/ns/1.0"
	)
	private String _imageLink;

	@JacksonXmlProperty(
		localName = "link", namespace = "http://base.google.com/ns/1.0"
	)
	private String _link;

	@JacksonXmlProperty(
		localName = "price", namespace = "http://base.google.com/ns/1.0"
	)
	private String _price;

	@JacksonXmlProperty(
		localName = "shipping", namespace = "http://base.google.com/ns/1.0"
	)
	private Shipping _shipping;

	@JacksonXmlProperty(
		localName = "title", namespace = "http://base.google.com/ns/1.0"
	)
	private String _title;

}