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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Instances of this class represent the values extracted from a structured
 * content form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class StructuredContentUpdaterForm {

	/**
	 * Builds a {@code Form} that generates {@code StructuredContentUpdaterForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a structured content updater form
	 * @review
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
		).addOptionalNestedModelList(
			"values", StructuredContentValuesForm::buildValuesForm,
			StructuredContentUpdaterForm::setStructuredContentValuesForms
		).addOptionalString(
			"description", StructuredContentUpdaterForm::setDescription
		).addOptionalString(
			"template", StructuredContentUpdaterForm::setTemplate
		).addOptionalString(
			"text", StructuredContentUpdaterForm::setText
		).addOptionalString(
			"title", StructuredContentUpdaterForm::setTitle
		).build();
	}

	/**
	 * Returns the structured content's description map.
	 *
	 * @return the structured content's description map
	 * @review
	 */
	public Optional<Map<Locale, String>> getDescriptionMapOptional() {
		return _getStringMapOptional(Locale.getDefault(), _description);
	}

	/**
	 * Returns the structured content's description map.
	 *
	 * @return the structured content's description map
	 * @review
	 */
	public Optional<Map<Locale, String>> getDescriptionMapOptional(
		Locale locale) {

		return _getStringMapOptional(locale, _description);
	}

	/**
	 * Returns the structured content's display date day.
	 *
	 * @return the structured content's display date day
	 * @review
	 */
	public Optional<Integer> getDisplayDateDayOptional() {
		return Optional.ofNullable(_publishedDateDay);
	}

	/**
	 * Returns the structured content's display date hour.
	 *
	 * @return the structured content's display date hour
	 * @review
	 */
	public Optional<Integer> getDisplayDateHourOptional() {
		return Optional.ofNullable(_publishedDateHour);
	}

	/**
	 * Returns the structured content's display date minute.
	 *
	 * @return the structured content's display date minute
	 * @review
	 */
	public Optional<Integer> getDisplayDateMinuteOptional() {
		return Optional.ofNullable(_publishedDateMinute);
	}

	/**
	 * Returns the structured content's display date month.
	 *
	 * @return the structured content's display date month
	 * @review
	 */
	public Optional<Integer> getDisplayDateMonthOptional() {
		return Optional.ofNullable(_publishedDateMonth);
	}

	/**
	 * Returns the structured content's display date year.
	 *
	 * @return the structured content's display date year
	 * @review
	 */
	public Optional<Integer> getDisplayDateYearOptional() {
		return Optional.ofNullable(_publishedDateYear);
	}

	public List<StructuredContentValuesForm> getStructuredContentValuesForms() {
		return _structuredContentValuesForms;
	}

	/**
	 * Returns the structured content's template ID.
	 *
	 * @return the structured content's template ID
	 * @review
	 */
	public String getTemplate() {
		return _template;
	}

	/**
	 * Returns the structured content's text.
	 *
	 * @return the structured content's text
	 * @review
	 */
	public Optional<String> getTextOptional() {
		return Optional.ofNullable(_text);
	}

	/**
	 * Returns the structured content's title map.
	 *
	 * @return the structured content's title map
	 * @review
	 */
	public Optional<Map<Locale, String>> getTitleMapOptional() {
		return _getStringMapOptional(Locale.getDefault(), _title);
	}

	/**
	 * Returns the structured content's title map.
	 *
	 * @return the structured content's title map
	 * @review
	 */
	public Optional<Map<Locale, String>> getTitleMapOptional(Locale locale) {
		return _getStringMapOptional(locale, _title);
	}

	public void setDescription(String description) {
		_description = description;
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

	public void setStructuredContentValuesForms(
		List<StructuredContentValuesForm> structuredContentValuesForms) {

		_structuredContentValuesForms = structuredContentValuesForms;
	}

	public void setTemplate(String template) {
		_template = template;
	}

	public void setText(String text) {
		_text = text;
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

	private String _description;
	private Integer _publishedDateDay;
	private Integer _publishedDateHour;
	private Integer _publishedDateMinute;
	private Integer _publishedDateMonth;
	private Integer _publishedDateYear;
	private List<StructuredContentValuesForm> _structuredContentValuesForms =
		new ArrayList<>();
	private String _template;
	private String _text;
	private String _title;

}