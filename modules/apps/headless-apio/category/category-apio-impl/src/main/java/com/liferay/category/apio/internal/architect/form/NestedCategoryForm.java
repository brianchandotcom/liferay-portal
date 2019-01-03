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
 * Represents the values extracted from a nested category form.
 *
 * @author Eduardo Pérez
 * @author Javier Gamarra
 */
public class NestedCategoryForm {

	/**
	 * Builds a {@code Form} that generates a {@code NestedCategoryForm} that
	 * depends on the HTTP body.
	 *
	 * @param  builder the {@code Form} builder
	 * @return the nested category form
	 */
	public static Form<NestedCategoryForm> buildForm(
		Form.Builder<NestedCategoryForm> builder) {

		return builder.title(
			__ -> "Category form"
		).description(
			__ -> "This form can be used to create or update a category"
		).constructor(
			NestedCategoryForm::new
		).addOptionalLong(
			"category", NestedCategoryForm::setParentCategoryId
		).addOptionalString(
			"description", NestedCategoryForm::setDescription
		).addRequiredLong(
			"vocabulary", NestedCategoryForm::setVocabularyId
		).addRequiredString(
			"name", NestedCategoryForm::setName
		).build();
	}

	/**
	 * Returns the asset category's description map.
	 *
	 * @return the description map
	 */
	public Map<Locale, String> getDescriptions(Locale locale) {
		return Collections.singletonMap(locale, _description);
	}

	/**
	 * Returns the asset category's parent category ID.
	 *
	 * @return the parent category ID
	 */
	public long getParentCategoryId() {
		return _parentCategoryId;
	}

	/**
	 * Returns the asset category's title map.
	 *
	 * @return the title map
	 */
	public Map<Locale, String> getTitles(Locale locale) {
		return Collections.singletonMap(locale, _name);
	}

	/**
	 * Returns the asset category's vocabulary ID.
	 *
	 * @return the vocabulary ID
	 */
	public long getVocabularyId() {
		return _vocabularyId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParentCategoryId(long parentCategoryId) {
		_parentCategoryId = parentCategoryId;
	}

	public void setVocabularyId(long vocabularyId) {
		_vocabularyId = vocabularyId;
	}

	private String _description;
	private String _name;
	private long _parentCategoryId;
	private long _vocabularyId;

}