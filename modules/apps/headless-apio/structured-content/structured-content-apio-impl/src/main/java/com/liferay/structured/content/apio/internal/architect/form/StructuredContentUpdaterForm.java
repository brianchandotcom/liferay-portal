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
import com.liferay.structured.content.apio.architect.model.StructuredContent;
import com.liferay.structured.content.apio.architect.model.StructuredContentValue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Represents the values extracted from a structured content form.
 *
 * @author Alejandro Hernández
 */
public class StructuredContentUpdaterForm implements StructuredContent {

	/**
	 * Builds a {@code Form} that generates a {@code
	 * StructuredContentUpdaterForm} that depends on the HTTP body.
	 *
	 * @param  formBuilder the form builder
	 * @return the form
	 */
	public static Form<StructuredContentUpdaterForm> buildForm(
		Form.Builder<StructuredContentUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The structured content updater form"
		).description(
			__ -> "This form can be used to update a structured content"
		).constructor(
			StructuredContentUpdaterForm::new
		).addOptionalDate(
			"datePublished", StructuredContentUpdaterForm::setPublishedDate
		).addOptionalLinkedModelList(
			"category", CategoryIdentifier.class,
			StructuredContentUpdaterForm::setCategories
		).addOptionalNestedModelList(
			"values", StructuredContentValueForm::buildForm,
			StructuredContentUpdaterForm::setStructuredContentValues
		).addOptionalString(
			"description", StructuredContentUpdaterForm::setDescription
		).addOptionalString(
			"title", StructuredContentUpdaterForm::setTitle
		).addOptionalStringList(
			"keywords", StructuredContentUpdaterForm::setKeywords
		).build();
	}

	@Override
	public List<Long> getCategories() {
		return _categories;
	}

	@Override
	public Long getContentStructureId() {
		return null;
	}

	@Override
	public Optional<Map<Locale, String>> getDescriptionMapOptional(
		Locale locale) {

		return _getStringMapOptional(locale, _description);
	}

	@Override
	public List<String> getKeywords() {
		return _keywords;
	}

	@Override
	public Optional<Integer> getPublishedDateDayOptional() {
		return Optional.ofNullable(_publishedDateDay);
	}

	@Override
	public Optional<Integer> getPublishedDateHourOptional() {
		return Optional.ofNullable(_publishedDateHour);
	}

	@Override
	public Optional<Integer> getPublishedDateMinuteOptional() {
		return Optional.ofNullable(_publishedDateMinute);
	}

	@Override
	public Optional<Integer> getPublishedDateMonthOptional() {
		return Optional.ofNullable(_publishedDateMonth);
	}

	@Override
	public Optional<Integer> getPublishedDateYearOptional() {
		return Optional.ofNullable(_publishedDateYear);
	}

	@Override
	public List<? extends StructuredContentValue> getStructuredContentValues() {
		return _structuredContentValues;
	}

	@Override
	public Map<Locale, String> getTitleMap(Locale locale) {
		Map<Locale, String> map = new HashMap<>();

		map.put(locale, _title);

		return map;
	}

	public void setCategories(List<Long> categories) {
		_categories = categories;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setPublishedDate(Date publishedDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(publishedDate);

		_publishedDateDay = calendar.get(Calendar.DATE);
		_publishedDateHour = calendar.get(Calendar.HOUR);
		_publishedDateMinute = calendar.get(Calendar.MINUTE);
		_publishedDateMonth = calendar.get(Calendar.MONTH);
		_publishedDateYear = calendar.get(Calendar.YEAR);
	}

	public void setStructuredContentValues(
		List<? extends StructuredContentValue> structuredContentValues) {

		_structuredContentValues = structuredContentValues;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private Optional<Map<Locale, String>> _getStringMapOptional(
		Locale locale, String value) {

		return Optional.ofNullable(
			value
		).map(
			description -> {
				Map<Locale, String> map = new HashMap<>();

				map.put(locale, value);

				return map;
			}
		);
	}

	private List<Long> _categories;
	private String _description;
	private List<String> _keywords;
	private Integer _publishedDateDay;
	private Integer _publishedDateHour;
	private Integer _publishedDateMinute;
	private Integer _publishedDateMonth;
	private Integer _publishedDateYear;
	private List<? extends StructuredContentValue> _structuredContentValues =
		new ArrayList<>();
	private String _title;

}