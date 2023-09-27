/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

/**
 * @author Matthew Kong
 */
public class PostalAddress {

	public String getAddressCountry() {
		return _addressCountry;
	}

	public String getAddressLocality() {
		return _addressLocality;
	}

	public String getAddressRegion() {
		return _addressRegion;
	}

	public String getPostalCode() {
		return _postalCode;
	}

	public String getStreetAddress() {
		return _streetAddress;
	}

	public void setAddressCountry(String addressCountry) {
		_addressCountry = addressCountry;
	}

	public void setAddressLocality(String addressLocality) {
		_addressLocality = addressLocality;
	}

	public void setAddressRegion(String addressRegion) {
		_addressRegion = addressRegion;
	}

	public void setPostalCode(String postalCode) {
		_postalCode = postalCode;
	}

	public void setStreetAddress(String streetAddress) {
		_streetAddress = streetAddress;
	}

	private String _addressCountry;
	private String _addressLocality;
	private String _addressRegion;
	private String _postalCode;
	private String _streetAddress;

}