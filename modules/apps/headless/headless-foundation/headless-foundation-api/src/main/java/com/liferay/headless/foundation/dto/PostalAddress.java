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

package com.liferay.headless.foundation.dto;

import javax.annotation.Generated;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Javier Gamarra
 * @generated
 */
@Generated("")
@XmlRootElement(name = "PostalAddress")
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

	public String getAddressType() {
		return _addressType;
	}

	public Integer getId() {
		return _id;
	}

	public String getPostalCode() {
		return _postalCode;
	}

	public String getSelf() {
		return _self;
	}

	public String getStreetAddressLine1() {
		return _streetAddressLine1;
	}

	public String getStreetAddressLine2() {
		return _streetAddressLine2;
	}

	public String getStreetAddressLine3() {
		return _streetAddressLine3;
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

	public void setAddressType(String addressType) {
		_addressType = addressType;
	}

	public void setId(Integer id) {
		_id = id;
	}

	public void setPostalCode(String postalCode) {
		_postalCode = postalCode;
	}

	public void setSelf(String self) {
		_self = self;
	}

	public void setStreetAddressLine1(String streetAddressLine1) {
		_streetAddressLine1 = streetAddressLine1;
	}

	public void setStreetAddressLine2(String streetAddressLine2) {
		_streetAddressLine2 = streetAddressLine2;
	}

	public void setStreetAddressLine3(String streetAddressLine3) {
		_streetAddressLine3 = streetAddressLine3;
	}

	private String _addressCountry;
	private String _addressLocality;
	private String _addressRegion;
	private String _addressType;
	private Integer _id;
	private String _postalCode;
	private String _self;
	private String _streetAddressLine1;
	private String _streetAddressLine2;
	private String _streetAddressLine3;

}