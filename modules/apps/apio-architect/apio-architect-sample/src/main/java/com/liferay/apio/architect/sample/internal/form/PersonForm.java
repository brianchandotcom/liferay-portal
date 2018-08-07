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

package com.liferay.apio.architect.sample.internal.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.sample.internal.model.PostalAddressModel;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents the values extracted from a person form.
 *
 * @author Alejandro Hernández
 */
public class PersonForm {

	/**
	 * Builds and returns a {@link Form} that generates {@code PersonForm} that
	 * depends on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return the {@code PersonForm}
	 */
	public static Form<PersonForm> buildForm(
		Form.Builder<PersonForm> formBuilder) {

		return formBuilder.title(
			__ -> "The person form"
		).description(
			__ -> "This form can be used to create or update a person"
		).constructor(
			PersonForm::new
		).addRequiredDate(
			"birthDate", PersonForm::setBirthDate
		).addOptionalStringList(
			"jobTitle", PersonForm::setJobTitles
		).addRequiredString(
			"addressCountry", PersonForm::setCountryCode
		).addRequiredString(
			"addressLocality", PersonForm::setCity
		).addRequiredString(
			"addressRegion", PersonForm::setState
		).addRequiredString(
			"givenName", PersonForm::setGivenName
		).addRequiredString(
			"image", PersonForm::setImage
		).addRequiredString(
			"email", PersonForm::setEmail
		).addRequiredString(
			"familyName", PersonForm::setFamilyName
		).addRequiredString(
			"postalCode", PersonForm::setZipCode
		).addRequiredString(
			"streetAddress", PersonForm::setStreetAddress
		).build();
	}

	/**
	 * Returns the person's birthdate.
	 *
	 * @return the person's birthdate
	 */
	public Date getBirthDate() {
		return new Date(_birthDate.getTime());
	}

	/**
	 * Returns the person's email.
	 *
	 * @return the person's email
	 */
	public String getEmail() {
		return _email;
	}

	/**
	 * Returns the person's family name.
	 *
	 * @return the person's family name
	 */
	public String getFamilyName() {
		return _familyName;
	}

	/**
	 * Returns the person's given name.
	 *
	 * @return the person's given name
	 */
	public String getGivenName() {
		return _givenName;
	}

	/**
	 * Returns the person's image.
	 *
	 * @return the person's image
	 */
	public String getImage() {
		return _image;
	}

	/**
	 * Returns the person's job titles.
	 *
	 * @return the person's job titles
	 */
	public List<String> getJobTitles() {
		return _jobTitles;
	}

	/**
	 * Returns the person's address.
	 *
	 * @return the person's address
	 */
	public PostalAddressModel getPostalAddressModel() {
		return new PostalAddressModel(
			_countryCode, _state, _city, _zipCode, _streetAddress);
	}

	public void setBirthDate(Date birthDate) {
		_birthDate = birthDate;
	}

	public void setCity(String city) {
		_city = city;
	}

	public void setCountryCode(String countryCode) {
		_countryCode = countryCode;
	}

	public void setEmail(String emailAddress) {
		_email = emailAddress;
	}

	public void setFamilyName(String lastName) {
		_familyName = lastName;
	}

	public void setGivenName(String givenName) {
		_givenName = givenName;
	}

	public void setImage(String image) {
		_image = image;
	}

	public void setJobTitles(List<String> jobTitles) {
		_jobTitles = jobTitles;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setStreetAddress(String streetAddress) {
		_streetAddress = streetAddress;
	}

	public void setZipCode(String zipCode) {
		_zipCode = zipCode;
	}

	private Date _birthDate;
	private String _city;
	private String _countryCode;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _image;
	private List<String> _jobTitles = Collections.emptyList();
	private String _state;
	private String _streetAddress;
	private String _zipCode;

}