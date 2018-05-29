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

package com.liferay.taxonomy.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Instances of this class represent the values extracted from a taxonomy form.
 *
 * @author Eduardo Perez
 * @author Javier Gamarra
 * @review
 */
public class TaxonomyForm {

	/**
	 * Builds a {@code Form} that generates {@code TaxonomyForm} depending on
	 * the HTTP body.
	 *
	 * @param  builder the {@code Form} builder
	 * @return a taxonomy form
	 * @review
	 */
	public static Form<TaxonomyForm> buildForm(
		Form.Builder<TaxonomyForm> builder) {

		return builder.title(
			__ -> "Taxonomy form"
		).description(
			__ -> "This form can be used to create or update a taxonomy"
		).constructor(
			TaxonomyForm::new
		).addOptionalString(
			"description", TaxonomyForm::_setDescription
		).addRequiredString(
			"name", TaxonomyForm::_setName
		).build();
	}

	/**
	 * Returns the taxonomy's description map.
	 *
	 * @return the taxonomy's description map
	 * @review
	 */
	public Map<Locale, String> getDescriptions(Locale locale) {
		return Collections.singletonMap(locale, _description);
	}

	/**
	 * Returns the taxonomy's description map.
	 *
	 * @return the taxonomy's description map
	 * @review
	 */
	public Map<Locale, String> getTitles(Locale locale) {
		return Collections.singletonMap(locale, _name);
	}

	private void _setDescription(String description) {
		_description = description;
	}

	private void _setName(String name) {
		_name = name;
	}

	private String _description;
	private String _name;

}