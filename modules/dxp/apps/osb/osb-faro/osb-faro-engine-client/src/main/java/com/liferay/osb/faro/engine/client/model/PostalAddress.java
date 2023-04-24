/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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