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
import com.liferay.apio.architect.form.Form.Builder;

/**
 * Instances of this class represent the values extracted from a person updater
 * form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class PersonUpdaterForm {

	/**
	 * Builds a {@code Form} that generates {@code PersonUpdaterForm} depending
	 * on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a person updater form
	 */
	public static Form<PersonUpdaterForm> buildForm(
		Builder<PersonUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The person updater form"
		).description(
			__ -> "This form can be used to update a person"
		).constructor(
			PersonUpdaterForm::new
		).addOptionalString(
			"alternateName", PersonUpdaterForm::setAlternateName
		).addRequiredString(
			"email", PersonUpdaterForm::setEmail
		).addRequiredString(
			"familyName", PersonUpdaterForm::setFamilyName
		).addRequiredString(
			"givenName", PersonUpdaterForm::setGivenName
		).addRequiredString(
			"jobTitle", PersonUpdaterForm::setJobTitle
		).addRequiredString(
			"password", PersonUpdaterForm::setPassword
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
	 * Returns the person's password
	 *
	 * @return the person's password
	 * @review
	 */
	public String getPassword() {
		return _password;
	}

	public void setAlternateName(String alternateName) {
		_alternateName = alternateName;
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

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public void setPassword(String password) {
		_password = password;
	}

	private String _alternateName;
	private String _email;
	private String _familyName;
	private String _givenName;
	private String _jobTitle;
	private String _password;

}