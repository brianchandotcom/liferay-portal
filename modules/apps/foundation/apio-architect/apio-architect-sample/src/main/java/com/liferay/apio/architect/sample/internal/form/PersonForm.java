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

import java.util.Date;

/**
 * Instances of this class represent the values extracted from a person form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class PersonForm {

	/**
	 * Builds a {@code Form} that generates {@code PersonForm} depending on the
	 * HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a person form
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
			"birthDate", PersonForm::_setBirthDate
		).addRequiredString(
			"givenName", PersonForm::_setGivenName
		).addRequiredString(
			"jobTitle", PersonForm::_setJobTitle
		).addRequiredString(
			"address", PersonForm::_setAddress
		).addRequiredString(
			"image", PersonForm::_setImage
		).addRequiredString(
			"email", PersonForm::_setEmail
		).addRequiredString(
			"familyName", PersonForm::_setFamilyName
		).build();
	}

	/**
	 * Returns the person's address
	 *
	 * @return the person's address
	 * @review
	 */
	public String getAddress() {
		return _address;
	}

	/**
	 * Returns the person's birthday date
	 *
	 * @return the person's birthday date
	 * @review
	 */
	public Date getBirthDate() {
		return new Date(_birthDate.getTime());
	}

	/**
	 * Returns the person's email
	 *
	 * @return the person's email
	 * @review
	 */
	public String getEmail() {
		return _email;
	}

	/**
	 * Returns the person's family name
	 *
	 * @return the person's family name
	 * @review
	 */
	public String getFamilyName() {
		return _familyName;
	}

	/**
	 * Returns the person's given name
	 *
	 * @return the person's given name
	 * @review
	 */
	public String getGivenName() {
		return _givenName;
	}

	/**
	 * Returns the person's image
	 *
	 * @return the person's image
	 * @review
	 */
	public String getImage() {
		return _image;
	}

	/**
	 * Returns the person's job title
	 *
	 * @return the person's job title
	 * @review
	 */
	public String getJobTitle() {
		return _jobTitle;
	}

	private void _setAddress(String address) {
		_address = address;
	}

	private void _setBirthDate(Date birthDate) {
		_birthDate = birthDate;
	}

	private void _setEmail(String emailAddress) {
		_email = emailAddress;
	}

	private void _setFamilyName(String lastName) {
		_familyName = lastName;
	}

	private void _setGivenName(String givenName) {
		_givenName = givenName;
	}

	private void _setImage(String image) {
		_image = image;
	}

	private void _setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	private String _address;
	private Date _birthDate;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _image;
	private String _jobTitle;

}