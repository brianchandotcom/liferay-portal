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
 * @author Javier Gamarra
 */
public class TaxonomyForm {

	public static Form<TaxonomyForm> buildForm(
		Form.Builder<TaxonomyForm> builder) {

		return builder.title(
			language -> "Title"
		).description(
			language -> "Description"
		).constructor(
			TaxonomyForm::new
		).addOptionalString(
			"description", TaxonomyForm::setDescription
		).addRequiredString(
			"name", TaxonomyForm::setName
		).build();
	}

	public String getDescription() {
		return _description;
	}

	public Map<Locale, String> getDescriptionMap(Locale locale) {
		return Collections.singletonMap(locale, _description);
	}

	public String getName() {
		return _name;
	}

	public Map<Locale, String> getTitleMap(Locale locale) {
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