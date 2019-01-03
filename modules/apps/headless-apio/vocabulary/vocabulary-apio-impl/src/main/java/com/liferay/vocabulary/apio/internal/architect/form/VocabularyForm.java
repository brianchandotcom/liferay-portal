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

package com.liferay.vocabulary.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Represents the values extracted from a vocabulary form.
 *
 * @author Eduardo Pérez
 * @author Javier Gamarra
 */
public class VocabularyForm {

	/**
	 * Builds a {@code Form} that generates a {@code VocabularyForm} that
	 * depends on the HTTP body.
	 *
	 * @param  builder the form builder
	 * @return the vocabulary form
	 */
	public static Form<VocabularyForm> buildForm(
		Form.Builder<VocabularyForm> builder) {

		return builder.title(
			__ -> "Vocabulary form"
		).description(
			__ -> "This form can be used to create or update a vocabulary"
		).constructor(
			VocabularyForm::new
		).addOptionalString(
			"description", VocabularyForm::setDescription
		).addRequiredString(
			"name", VocabularyForm::setName
		).build();
	}

	/**
	 * Returns the vocabulary's description map.
	 *
	 * @return the description map
	 */
	public Map<Locale, String> getDescriptions(Locale locale) {
		return Collections.singletonMap(locale, _description);
	}

	/**
	 * Returns the vocabulary's title map.
	 *
	 * @return the title map
	 */
	public Map<Locale, String> getTitles(Locale locale) {
		return Collections.singletonMap(locale, _name);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _description;
	private String _name;

}