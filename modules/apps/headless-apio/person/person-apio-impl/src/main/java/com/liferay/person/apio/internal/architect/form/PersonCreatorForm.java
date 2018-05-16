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

package com.liferay.person.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.Date;

/**
 * Instances of this class represent the values extracted from a person creator
 * form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class PersonCreatorForm {

	/**
	 * Builds a {@code Form} that generates {@code PersonCreatorForm} depending
	 * on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a person creator form
	 */
	public static Form<PersonCreatorForm> buildForm(
		Form.Builder<PersonCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The person creator form"
		).description(
			__ -> "This form can be used to create a person"
		).constructor(
			PersonCreatorForm::new
		).addOptionalString(
			"gender", PersonCreatorForm::_setGender
		).addOptionalString(
			"alternateName", PersonCreatorForm::_setAlternateName
		).addRequiredDate(
			"birthDate", PersonCreatorForm::_setBirthDate
		).addRequiredString(
			"email", PersonCreatorForm::_setEmail
		).addRequiredString(
			"familyName", PersonCreatorForm::_setFamilyName
		).addRequiredString(
			"givenName", PersonCreatorForm::_setGivenName
		).addRequiredString(
			"jobTitle", PersonCreatorForm::_setJobTitle
		).addRequiredString(
			"password1", PersonCreatorForm::_setPassword1
		).addRequiredString(
			"password2", PersonCreatorForm::_setPassword2
		).build();
	}

	/**
	 * Returns the person's alternate name
	 *
	 * @return the person's alternate name
	 * @review
	 */
	public String getAlternateName() {
		return _alternateName;
	}

	/**
	 * Returns the person's birthday day
	 *
	 * @return the person's birthday day
	 * @review
	 */
	public int getBirthdayDay() {
		return _birthdayDay;
	}

	/**
	 * Returns the person's birthday month
	 *
	 * @return the person's birthday month
	 * @review
	 */
	public int getBirthdayMonth() {
		return _birthdayMonth;
	}

	/**
	 * Returns the person's birthday year
	 *
	 * @return the person's birthday year
	 * @review
	 */
	public int getBirthdayYear() {
		return _birthdayYear;
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
	 * Returns the person's job title
	 *
	 * @return the person's job title
	 * @review
	 */
	public String getJobTitle() {
		return _jobTitle;
	}

	/**
	 * Returns the person's password (first attempt)
	 *
	 * @return the person's password (first attempt)
	 * @review
	 */
	public String getPassword1() {
		return _password1;
	}

	/**
	 * Returns the person's password (second attempt)
	 *
	 * @return the person's password (second attempt)
	 * @review
	 */
	public String getPassword2() {
		return _password2;
	}

	/**
	 * Checks if the person has an alternate name
	 *
	 * @return {@code true} if the person has an alternate name; {@code false}
	 *         otherwise
	 * @review
	 */
	public boolean hasAlternateName() {
		return Validator.isNull(_alternateName);
	}

	/**
	 * Checks if the person is a male
	 *
	 * @return {@code true} if the person is a male; {@code false} otherwise
	 * @review
	 */
	public boolean isMale() {
		return _male;
	}

	private void _setAlternateName(String alternateName) {
		_alternateName = alternateName;
	}

	private void _setBirthDate(Date birthDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(birthDate);

		_birthdayMonth = calendar.get(Calendar.MONTH);
		_birthdayDay = calendar.get(Calendar.DATE);
		_birthdayYear = calendar.get(Calendar.YEAR);
	}

	private void _setEmail(String emailAddress) {
		_email = emailAddress;
	}

	private void _setFamilyName(String lastName) {
		_familyName = lastName;
	}

	private void _setGender(String gender) {
		_male = "male".equals(gender);
	}

	private void _setGivenName(String givenName) {
		_givenName = givenName;
	}

	private void _setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	private void _setPassword1(String password1) {
		_password1 = password1;
	}

	private void _setPassword2(String password2) {
		_password2 = password2;
	}

	private String _alternateName;
	private int _birthdayDay;
	private int _birthdayMonth;
	private int _birthdayYear;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _jobTitle;
	private Boolean _male;
	private String _password1;
	private String _password2;

}