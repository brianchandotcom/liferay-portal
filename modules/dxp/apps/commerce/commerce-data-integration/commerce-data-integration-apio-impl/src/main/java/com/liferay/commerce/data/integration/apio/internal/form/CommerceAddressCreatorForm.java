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

package com.liferay.commerce.data.integration.apio.internal.form;

import com.liferay.apio.architect.form.Form;

/**
 * @author Rodrigo Guedes de Souza
 */
public class CommerceAddressCreatorForm {

	public static Form<CommerceAddressCreatorForm> buildForm(
		Form.Builder<CommerceAddressCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The address creator form"
		).description(
			__ -> "This form can be used to create an address"
		).constructor(
			CommerceAddressCreatorForm::new
		).addRequiredString(
			"name", CommerceAddressCreatorForm::_setName
		).addOptionalString(
			"description", CommerceAddressCreatorForm::_setDescription
		).addRequiredString(
			"street1", CommerceAddressCreatorForm::_setStreet1
		).addOptionalString(
			"street2", CommerceAddressCreatorForm::_setStreet2
		).addOptionalString(
			"street3", CommerceAddressCreatorForm::_setStreet3
		).addRequiredString(
			"city", CommerceAddressCreatorForm::_setCity
		).addOptionalString(
			"zip", CommerceAddressCreatorForm::_setZip
		).addOptionalString(
			"phoneNumber", CommerceAddressCreatorForm::_setPhoneNumber
		).addRequiredLong(
			"countryId", CommerceAddressCreatorForm::_setCountryId
		).addOptionalLong(
			"regionId", CommerceAddressCreatorForm::_setRegionId
		).addOptionalDouble(
			"latitude", CommerceAddressCreatorForm::_setLatitude
		).addOptionalDouble(
			"longitude", CommerceAddressCreatorForm::_setLongitude
		).addOptionalBoolean(
			"_defaultBilling", CommerceAddressCreatorForm::_setDefaultBilling
		).addOptionalBoolean(
			"defaultShipping", CommerceAddressCreatorForm::_setDefaultShipping
		).build();
	}

	public String getCity() {
		return _city;
	}

	public long getCountryId() {
		return _countryId;
	}

	public boolean getDefaultBilling() {
		return _defaultBilling;
	}

	public boolean getDefaultShipping() {
		return _defaultShipping;
	}

	public String getDescription() {
		return _description;
	}

	public double getLatitude() {
		return _latitude;
	}

	public double getLongitude() {
		return _longitude;
	}

	public String getName() {
		return _name;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public long getRegionId() {
		return _regionId;
	}

	public String getStreet1() {
		return _street1;
	}

	public String getStreet2() {
		return _street2;
	}

	public String getStreet3() {
		return _street3;
	}

	public String getZip() {
		return _zip;
	}

	private void _setCity(String city) {
		_city = city;
	}

	private void _setCountryId(long countryId) {
		_countryId = countryId;
	}

	private void _setDefaultBilling(boolean defaultBilling) {
		_defaultBilling = defaultBilling;
	}

	private void _setDefaultShipping(boolean defaultShipping) {
		_defaultShipping = defaultShipping;
	}

	private void _setDescription(String description) {
		_description = description;
	}

	private void _setLatitude(double latitude) {
		_latitude = latitude;
	}

	private void _setLongitude(double longitude) {
		_longitude = longitude;
	}

	private void _setName(String name) {
		_name = name;
	}

	private void _setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	private void _setRegionId(long regionId) {
		_regionId = regionId;
	}

	private void _setStreet1(String street1) {
		_street1 = street1;
	}

	private void _setStreet2(String street2) {
		_street2 = street2;
	}

	private void _setStreet3(String street3) {
		_street3 = street3;
	}

	private void _setZip(String zip) {
		_zip = zip;
	}

	private String _city;
	private long _countryId;
	private boolean _defaultBilling;
	private boolean _defaultShipping;
	private String _description;
	private double _latitude;
	private double _longitude;
	private String _name;
	private String _phoneNumber;
	private long _regionId;
	private String _street1;
	private String _street2;
	private String _street3;
	private String _zip;

}