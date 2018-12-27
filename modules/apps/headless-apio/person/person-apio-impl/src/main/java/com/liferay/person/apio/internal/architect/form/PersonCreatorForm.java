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

import com.liferay.apio.architect.file.BinaryFile;
import com.liferay.apio.architect.form.Form;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

/**
 * Represents the values extracted from a person creator form.
 *
 * @author Alejandro Hernández
 */
public class PersonCreatorForm {

	/**
	 * Builds a {@code Form} that generates a {@code PersonCreatorForm} that
	 * depends on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return the person creator form
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
			"honorificPrefix", PersonCreatorForm::setHonorificPrefix
		).addOptionalString(
			"honorificSuffix", PersonCreatorForm::setHonorificSuffix
		).addOptionalFile(
			"image", PersonCreatorForm::setImageBinaryFile
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
	public int getBirthdayDay() {
		if (_birthdayDay != null) {
			return _birthdayDay;
		}

		return 1;
	}

	/**
	 * Returns the person's birth month.
	 *
	 * @return the person's birth month
	 */
	public int getBirthdayMonth() {
		if (_birthdayMonth != null) {
			return _birthdayMonth;
		}

		return 0;
	}

	/**
	 * Returns the person's birth year.
	 *
	 * @return the person's birth year
	 */
	public int getBirthdayYear() {
		if (_birthdayYear != null) {
			return _birthdayYear;
		}

		return 1970;
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

	public BinaryFile getImageBinaryFile() {
		return _imageBinaryFile;
	}

	/**
	 * Returns the person's job title.
	 *
	 * @return the person's job title
	 */
	public String getJobTitle() {
		return Optional.ofNullable(
			_jobTitle
		).orElse(
			""
		);
	}

	/**
	 * Returns {@code true} if the person has an alternate name.
	 *
	 * @return {@code true} if the person has an alternate name; {@code false}
	 *         otherwise
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

	public void setGivenName(String givenName) {
		_givenName = givenName;
	}

	public void setHonorificPrefix(String honorificPrefix) {
		_honorificPrefix = honorificPrefix;
	}

	public void setHonorificSuffix(String honorificSuffix) {
		_honorificSuffix = honorificSuffix;
	}

	public void setImageBinaryFile(BinaryFile imageBinaryFile) {
		_imageBinaryFile = imageBinaryFile;
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
	private String _honorificPrefix;
	private String _honorificSuffix;
	private BinaryFile _imageBinaryFile;
	private String _jobTitle;

}