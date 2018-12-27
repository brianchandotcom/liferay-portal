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

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * Represents the values extracted from a person updater form.
 *
 * @author Alejandro Hernández
 */
public class PersonUpdaterForm {

	/**
	 * Builds a {@code Form} that generates a {@code PersonUpdaterForm} that
	 * depends on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return the person updater form
	 */
	public static Form<PersonUpdaterForm> buildForm(
		Form.Builder<PersonUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The person updater form"
		).description(
			__ -> "This form can be used to update a person"
		).constructor(
			PersonUpdaterForm::new
		).addOptionalString(
			"alternateName", PersonUpdaterForm::setAlternateName
		).addOptionalDate(
			"birthDate", PersonUpdaterForm::setBirthDate
		).addOptionalString(
			"honorificPrefix", PersonUpdaterForm::setHonorificPrefix
		).addOptionalString(
			"honorificSuffix", PersonUpdaterForm::setHonorificSuffix
		).addOptionalString(
			"jobTitle", PersonUpdaterForm::setJobTitle
		).addRequiredString(
			"email", PersonUpdaterForm::setEmail
		).addRequiredString(
			"familyName", PersonUpdaterForm::setFamilyName
		).addRequiredString(
			"givenName", PersonUpdaterForm::setGivenName
		).addRequiredString(
			"password", PersonUpdaterForm::setPassword
		).build();
	}

	/**
	 * Returns the person's alternate name.
	 *
	 * @return the person's alternate name
	 */
	public String getAlternateName() {
		return _alternateName;
	}

	/**
	 * Returns the person's birth day.
	 *
	 * @return the person's birth day
	 */
	public Optional<Integer> getBirthdayDayOptional() {
		return Optional.ofNullable(_birthdayDay);
	}

	/**
	 * Returns the person's birth month.
	 *
	 * @return the person's birth month
	 */
	public Optional<Integer> getBirthdayMonthOptional() {
		return Optional.ofNullable(_birthdayMonth);
	}

	/**
	 * Returns the person's birth year.
	 *
	 * @return the person's birth year
	 */
	public Optional<Integer> getBirthdayYearOptional() {
		return Optional.ofNullable(_birthdayYear);
	}

	/**
	 * Returns the person's email address.
	 *
	 * @return the person's email address
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

	public String getHonorificPrefix() {
		return _honorificPrefix;
	}

	public String getHonorificSuffix() {
		return _honorificSuffix;
	}

	/**
	 * Returns the person's job title.
	 *
	 * @return the person's job title
	 */
	public Optional<String> getJobTitleOptional() {
		return Optional.ofNullable(_jobTitle);
	}

	/**
	 * Returns the person's password.
	 *
	 * @return the person's password
	 */
	public String getPassword() {
		return _password;
	}

	public void setAlternateName(String alternateName) {
		_alternateName = alternateName;
	}

	public void setBirthDate(Date birthDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(birthDate);

		_birthdayDay = calendar.get(Calendar.DATE);
		_birthdayMonth = calendar.get(Calendar.MONTH);
		_birthdayYear = calendar.get(Calendar.YEAR);
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

	public void setHonorificPrefix(String honorificPrefix) {
		_honorificPrefix = honorificPrefix;
	}

	public void setHonorificSuffix(String honorificSuffix) {
		_honorificSuffix = honorificSuffix;
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public void setPassword(String password) {
		_password = password;
	}

	private String _alternateName;
	private Integer _birthdayDay;
	private Integer _birthdayMonth;
	private Integer _birthdayYear;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _honorificPrefix;
	private String _honorificSuffix;
	private String _jobTitle;
	private String _password;

}