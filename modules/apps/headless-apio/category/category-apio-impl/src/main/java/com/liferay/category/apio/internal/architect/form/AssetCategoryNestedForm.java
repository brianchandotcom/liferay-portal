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
 * @author Javier Gamarra
 */
public class AssetCategoryNestedForm {

	public static Form<AssetCategoryNestedForm> buildForm(
		Form.Builder<AssetCategoryNestedForm> builder) {

		return builder.title(
			language -> "Category Creator Form"
		).description(
			language -> "Form for adding categories"
		).constructor(
			AssetCategoryNestedForm::new
		).addOptionalLong(
			"parentCategoryId", AssetCategoryNestedForm::setParentCategoryId
		).addOptionalString(
			"description", AssetCategoryNestedForm::setDescription
		).addRequiredLong(
			"vocabularyId", AssetCategoryNestedForm::setVocabularyId
		).addRequiredString(
			"name", AssetCategoryNestedForm::setName
		).build();
	}

	public Map<Locale, String> getDescriptionMap(Locale locale) {
		return Collections.singletonMap(locale, _description);
	}

	public long getParentCategoryId() {
		return _parentCategoryId;
	}

	public Map<Locale, String> getTitleMap(Locale locale) {
		return Collections.singletonMap(locale, _name);
	}

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