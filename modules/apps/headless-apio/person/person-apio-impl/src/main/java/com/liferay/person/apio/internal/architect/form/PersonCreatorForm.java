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
import java.util.Optional;

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
			"alternateName", PersonCreatorForm::setAlternateName
		).addOptionalDate(
			"birthDate", PersonCreatorForm::setBirthDate
		).addOptionalString(
			"gender", PersonCreatorForm::setGender
		).addRequiredString(
			"email", PersonCreatorForm::setEmail
		).addRequiredString(
			"familyName", PersonCreatorForm::setFamilyName
		).addRequiredString(
			"givenName", PersonCreatorForm::setGivenName
		).addOptionalString(
			"jobTitle", PersonCreatorForm::setJobTitle
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
		if (_birthdayDay != null) {
			return _birthdayDay;
		}

		return 1;
	}

	/**
	 * Returns the person's birthday month
	 *
	 * @return the person's birthday month
	 * @review
	 */
	public int getBirthdayMonth() {
		if (_birthdayMonth != null) {
			return _birthdayMonth;
		}

		return 0;
	}

	/**
	 * Returns the person's birthday year
	 *
	 * @return the person's birthday year
	 * @review
	 */
	public int getBirthdayYear() {
		if (_birthdayYear != null) {
			return _birthdayYear;
		}

		return 1970;
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
		return Optional.ofNullable(
			_jobTitle
		).orElse(
			""
		);
	}

	/**
	 * Checks if the person is a male
	 *
	 * @return {@code true} if the person is a male; {@code false} otherwise
	 * @review
	 */
	public boolean isMale() {
		return Optional.ofNullable(
			_male
		).orElse(
			true
		);
	}

	/**
	 * Checks if the person has an alternate name
	 *
	 * @return {@code true} if the person has an alternate name; {@code false}
	 *         otherwise
	 * @review
	 */
	public boolean needsAlternateName() {
		return Validator.isNull(_alternateName);
	}

	public void setAlternateName(String alternateName) {
		_alternateName = alternateName;
	}

	public void setBirthDate(Date birthDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(birthDate);

		_birthdayMonth = calendar.get(Calendar.MONTH);
		_birthdayDay = calendar.get(Calendar.DATE);
		_birthdayYear = calendar.get(Calendar.YEAR);
	}

	public void setEmail(String emailAddress) {
		_email = emailAddress;
	}

	public void setFamilyName(String lastName) {
		_familyName = lastName;
	}

	public void setGender(String gender) {
		_male = "male".equals(gender);
	}

	public void setGivenName(String givenName) {
		_givenName = givenName;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	private String _alternateName;
	private Integer _birthdayDay;
	private Integer _birthdayMonth;
	private Integer _birthdayYear;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _jobTitle;
	private Boolean _male;

}