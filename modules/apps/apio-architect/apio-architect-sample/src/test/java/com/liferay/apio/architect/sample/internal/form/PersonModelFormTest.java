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

import static com.liferay.apio.architect.form.Form.Builder.empty;
import static com.liferay.apio.architect.sample.internal.form.PersonForm.buildForm;
import static com.liferay.apio.architect.test.util.form.FormMatchers.isAFormWithConditions;
import static com.liferay.apio.architect.test.util.form.FormMatchers.isReturnedIn;

import static org.hamcrest.MatcherAssert.assertThat;

import com.liferay.apio.architect.form.Form;

import org.hamcrest.Matcher;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PersonModelFormTest {

	@Test
	public void test() {
		Form<PersonForm> form = buildForm(empty());

		Matcher<Form<PersonForm>> isAFormWithConditions = isAFormWithConditions(
			builder -> builder.whereDate(
				"birthDate", isReturnedIn(PersonForm::getBirthDate)
			).whereString(
				"address", isReturnedIn(PersonForm::getAddress)
			).whereString(
				"email", isReturnedIn(PersonForm::getEmail)
			).whereString(
				"familyName", isReturnedIn(PersonForm::getFamilyName)
			).whereString(
				"givenName", isReturnedIn(PersonForm::getGivenName)
			).whereString(
				"image", isReturnedIn(PersonForm::getImage)
			).whereStringList(
				"jobTitle", isReturnedIn(PersonForm::getJobTitles)
			).build());

		assertThat(form, isAFormWithConditions);
	}

}