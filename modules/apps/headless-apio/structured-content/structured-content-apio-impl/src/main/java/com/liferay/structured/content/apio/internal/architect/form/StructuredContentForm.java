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

package com.liferay.structured.content.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.category.apio.architect.identifier.CategoryIdentifier;
import com.liferay.structure.apio.architect.identifier.ContentStructureIdentifier;
import com.liferay.structured.content.apio.architect.model.StructuredContent;
import com.liferay.structured.content.apio.architect.model.StructuredContentValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Represents the values extracted from a structured content form.
 *
 * @author Alejandro Hernández
 */
public class StructuredContentForm implements StructuredContent {

	/**
	 * Builds a {@code Form} that generates a {@code StructuredContentForm} that
	 * depends on the HTTP body.
	 *
	 * @param  formBuilder the form builder
	 * @return the form
	 */
	public static Form<StructuredContentForm> buildForm(
		Form.Builder<StructuredContentForm> formBuilder) {

		return formBuilder.title(
			__ -> "The structured content creator form"
		).description(
			__ -> "This form can be used to create a structured content"
		).constructor(
			StructuredContentForm::new
		).addOptionalDate(
			"datePublished", StructuredContentForm::setPublishedDate
		).addOptionalLinkedModelList(
			"category", CategoryIdentifier.class,
			StructuredContentForm::setCategories
		).addOptionalNestedModelList(
			"values", StructuredContentValueForm::buildForm,
			StructuredContentForm::setStructuredContentValues
		).addOptionalString(
			"description", StructuredContentForm::setDescription
		).addOptionalStringList(
			"keywords", StructuredContentForm::setKeywords
		).addRequiredLinkedModel(
			"contentStructure", ContentStructureIdentifier.class,
			StructuredContentForm::setContentStructureId
		).addRequiredString(
			"title", StructuredContentForm::setTitle
		).build();
	}

	@Override
	public List<Long> getCategories() {
		return _categories;
	}

	@Override
	public Long getContentStructureId() {
		return _contentStructureId;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public List<String> getKeywords() {
		return _keywords;
	}

	@Override
	public Date getPublishedDate() {
		return _publishedDate;
	}

	@Override
	public List<? extends StructuredContentValue> getStructuredContentValues() {
		return _structuredContentValues;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	public void setCategories(List<Long> categories) {
		_categories = categories;
	}

	public void setContentStructureId(Long contentStructureId) {
		_contentStructureId = contentStructureId;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setPublishedDate(Date publishedDate) {
		_publishedDate = publishedDate;
	}

	public void setStructuredContentValues(
		List<? extends StructuredContentValue> structuredContentValues) {

		_structuredContentValues = structuredContentValues;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private List<Long> _categories;
	private Long _contentStructureId;
	private String _description;
	private List<String> _keywords;
	private Date _publishedDate;
	private List<? extends StructuredContentValue> _structuredContentValues =
		new ArrayList<>();
	private String _title;

}