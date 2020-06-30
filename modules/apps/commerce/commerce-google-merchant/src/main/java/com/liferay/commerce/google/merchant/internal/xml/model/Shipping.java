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
 * Represents Google Merchant Center Shipping attributes
 */
@JacksonXmlRootElement(
	localName = "shipping", namespace = "http://base.google.com/ns/1.0"
)
@JsonPropertyOrder({"country", "service", "price"})
public class Shipping {

	public Shipping() {
	}

	public Shipping(String country, String service, String price) {
		_country = country;
		_service = service;
		_price = price;
	}

	public void setCountry(String country) {
		_country = country;
	}

	public void setPrice(String price) {
		_price = price;
	}

	public void setService(String service) {
		_service = service;
	}

	@JacksonXmlProperty(
		localName = "country", namespace = "http://base.google.com/ns/1.0"
	)
	private String _country;

	@JacksonXmlProperty(
		localName = "price", namespace = "http://base.google.com/ns/1.0"
	)
	private String _price;

	@JacksonXmlProperty(
		localName = "service", namespace = "http://base.google.com/ns/1.0"
	)
	private String _service;

}