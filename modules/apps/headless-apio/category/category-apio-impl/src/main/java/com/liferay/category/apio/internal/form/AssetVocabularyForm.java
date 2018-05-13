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

package com.liferay.category.apio.internal.form;

import com.liferay.apio.architect.form.Form;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class AssetVocabularyForm {

	public static Form<AssetVocabularyForm> buildForm(
		Form.Builder<AssetVocabularyForm> builder) {

		return builder.title(
			language -> "Title"
		).description(
			language -> "Description"
		).constructor(
			AssetVocabularyForm::new
		).addRequiredString(
			"description", AssetVocabularyForm::setDescription
		).addRequiredString(
			"name", AssetVocabularyForm::setName
		).addRequiredString(
			"title", AssetVocabularyForm::setTitle
		).build();
	}

	public String getDescription() {
		return _description;
	}

	public Map<Locale, String> getDescriptionMap() {
		return Collections.singletonMap(Locale.getDefault(), _description);
	}

	public String getName() {
		return _name;
	}

	public String getTitle() {
		return _title;
	}

	public Map<Locale, String> getTitleMap() {
		return Collections.singletonMap(Locale.getDefault(), _name);
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _description;
	private String _name;
	private String _title;

}