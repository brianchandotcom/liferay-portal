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