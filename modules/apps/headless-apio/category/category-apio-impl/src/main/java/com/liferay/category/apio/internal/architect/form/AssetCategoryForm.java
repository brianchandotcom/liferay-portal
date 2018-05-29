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

package com.liferay.category.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * Instances of this class represent the values extracted from an asset category
 * form.
 *
 * @author Eduardo Perez
 * @author Javier Gamarra
 * @review
 */
public class AssetCategoryForm {

	/**
	 * Builds a {@code Form} that generates {@code AssetCategoryForm} depending
	 * on the HTTP body.
	 *
	 * @param  builder the {@code Form} builder
	 * @return an asset category form
	 * @review
	 */
	public static Form<AssetCategoryForm> buildForm(
		Form.Builder<AssetCategoryForm> builder) {

		return builder.title(
			language -> "Category Creator Form"
		).description(
			language -> "Form for adding categories"
		).constructor(
			AssetCategoryForm::new
		).addOptionalString(
			"description", AssetCategoryForm::setDescription
		).addRequiredString(
			"name", AssetCategoryForm::setName
		).build();
	}

	/**
	 * Returns the asset category's description map.
	 *
	 * @return the asset category's description map
	 * @review
	 */
	public Map<Locale, String> getDescriptionMap(Locale locale) {
		return Collections.singletonMap(locale, _description);
	}

	/**
	 * Returns the asset category's title map.
	 *
	 * @return the asset category's title map
	 * @review
	 */
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