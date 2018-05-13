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
public class AssetCategoryForm {

	public static Form<AssetCategoryForm> buildForm(
		Form.Builder<AssetCategoryForm> builder) {

		return builder.title(
			language -> "Title"
		).description(
			language -> "Description"
		).constructor(
			AssetCategoryForm::new
		).addOptionalLong(
			"vocabularyId", AssetCategoryForm::setVocabularyId
		).addRequiredString(
			"description", AssetCategoryForm::setDescription
		).addRequiredString(
			"title", AssetCategoryForm::setTitle
		).build();
	}

	public String getDescription() {
		return _description;
	}

	public Map<Locale, String> getDescriptionMap() {
		return Collections.singletonMap(Locale.getDefault(), _description);
	}

	public String getTitle() {
		return _title;
	}

	public Map<Locale, String> getTitleMap() {
		return Collections.singletonMap(Locale.getDefault(), _title);
	}

	public long getVocabularyId() {
		return _vocabularyId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setVocabularyId(Long vocabularyId) {
		_vocabularyId = vocabularyId;
	}

	private String _description;
	private String _title;
	private Long _vocabularyId;

}