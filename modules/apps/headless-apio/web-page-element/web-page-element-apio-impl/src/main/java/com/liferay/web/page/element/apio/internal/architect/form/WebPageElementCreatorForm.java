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

package com.liferay.web.page.element.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Instances of this class represent the values extracted from a web page
 * element form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class WebPageElementCreatorForm {

	/**
	 * Builds a {@code Form} that generates {@code WebPageElementCreatorForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a web page element creator form
	 * @review
	 */
	public static Form<WebPageElementCreatorForm> buildForm(
		Builder<WebPageElementCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The web page element creator form"
		).description(
			__ -> "This form can be used to create a web page element"
		).constructor(
			WebPageElementCreatorForm::new
		).addOptionalLong(
			"folder", WebPageElementCreatorForm::_setFolder
		).addRequiredDate(
			"displayDate", WebPageElementCreatorForm::_setDisplayDate
		).addRequiredString(
			"description", WebPageElementCreatorForm::_setDescription
		).addRequiredString(
			"structure", WebPageElementCreatorForm::_setStructure
		).addRequiredString(
			"template", WebPageElementCreatorForm::_setTemplate
		).addRequiredString(
			"text", WebPageElementCreatorForm::_setText
		).addRequiredString(
			"title", WebPageElementCreatorForm::_setTitle
		).build();
	}

	/**
	 * Returns the web page element's description map.
	 *
	 * @return the web page element's description map
	 * @review
	 */
	public Map<Locale, String> getDescriptionMap() {
		return Collections.singletonMap(Locale.getDefault(), _description);
	}

	/**
	 * Returns the web page element's display date day.
	 *
	 * @return the web page element's display date day
	 * @review
	 */
	public int getDisplayDateDay() {
		return _displayDateDay;
	}

	/**
	 * Returns the web page element's display date hour.
	 *
	 * @return the web page element's display date hour
	 * @review
	 */
	public int getDisplayDateHour() {
		return _displayDateHour;
	}

	/**
	 * Returns the web page element's display date minute.
	 *
	 * @return the web page element's display date minute
	 * @review
	 */
	public int getDisplayDateMinute() {
		return _displayDateMinute;
	}

	/**
	 * Returns the web page element's display date month.
	 *
	 * @return the web page element's display date month
	 * @review
	 */
	public int getDisplayDateMonth() {
		return _displayDateMonth;
	}

	/**
	 * Returns the web page element's display date year.
	 *
	 * @return the web page element's display date year
	 * @review
	 */
	public int getDisplayDateYear() {
		return _displayDateYear;
	}

	/**
	 * Returns the folder ID if added through the form. Returns {@code 0}
	 * otherwise.
	 *
	 * @return the web page element's folder ID if present; {@code 0} otherwise
	 * @review
	 */
	public long getFolder() {
		if (_folder == null) {
			return 0;
		}

		return _folder;
	}

	/**
	 * Returns the web page element's structure ID.
	 *
	 * @return the web page element's structure ID
	 * @review
	 */
	public String getStructure() {
		return _structure;
	}

	/**
	 * Returns the web page element's template ID.
	 *
	 * @return the web page element's template ID
	 * @review
	 */
	public String getTemplate() {
		return _template;
	}

	/**
	 * Returns the web page element's text.
	 *
	 * @return the web page element's text
	 * @review
	 */
	public String getText() {
		return _text;
	}

	/**
	 * Returns the web page element's title map.
	 *
	 * @return the web page element's title map
	 * @review
	 */
	public Map<Locale, String> getTitleMap() {
		return Collections.singletonMap(Locale.getDefault(), _title);
	}

	private void _setDescription(String description) {
		_description = description;
	}

	private void _setDisplayDate(Date displayDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(displayDate);

		_displayDateMonth = calendar.get(Calendar.MONTH);
		_displayDateDay = calendar.get(Calendar.DATE);
		_displayDateYear = calendar.get(Calendar.YEAR);
		_displayDateHour = calendar.get(Calendar.HOUR);
		_displayDateMinute = calendar.get(Calendar.MINUTE);
	}

	private void _setFolder(long folder) {
		_folder = folder;
	}

	private void _setStructure(String structure) {
		_structure = structure;
	}

	private void _setTemplate(String template) {
		_template = template;
	}

	private void _setText(String text) {
		_text = text;
	}

	private void _setTitle(String title) {
		_title = title;
	}

	private String _description;
	private Integer _displayDateDay;
	private Integer _displayDateHour;
	private Integer _displayDateMinute;
	private Integer _displayDateMonth;
	private Integer _displayDateYear;
	private Long _folder;
	private String _structure;
	private String _template;
	private String _text;
	private String _title;

}