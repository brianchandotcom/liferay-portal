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
import com.liferay.apio.architect.form.Form.Builder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Instances of this class represent the values extracted from a structured
 * content form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class StructuredContentCreatorForm {

	/**
	 * Builds a {@code Form} that generates {@code StructuredContentCreatorForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a structured content creator form
	 * @review
	 */
	public static Form<StructuredContentCreatorForm> buildForm(
		Builder<StructuredContentCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The structured content creator form"
		).description(
			__ -> "This form can be used to create a structured content"
		).constructor(
			StructuredContentCreatorForm::new
		).addOptionalStringList(
			"keywords", StructuredContentCreatorForm::setKeywords
		).addRequiredDate(
			"displayDate", StructuredContentCreatorForm::setDisplayDate
		).addRequiredString(
			"description", StructuredContentCreatorForm::setDescription
		).addRequiredString(
			"structure", StructuredContentCreatorForm::setStructure
		).addRequiredString(
			"template", StructuredContentCreatorForm::setTemplate
		).addRequiredString(
			"text", StructuredContentCreatorForm::setText
		).addRequiredString(
			"title", StructuredContentCreatorForm::setTitle
		).build();
	}

	/**
	 * Returns the structured content's description map.
	 *
	 * @return the structured content's description map
	 * @review
	 */
	public Map<Locale, String> getDescriptionMap(Locale locale) {
		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, _description);

		return descriptionMap;
	}

	/**
	 * Returns the structured content's display date day.
	 *
	 * @return the structured content's display date day
	 * @review
	 */
	public int getDisplayDateDay() {
		return _displayDateDay;
	}

	/**
	 * Returns the structured content's display date hour.
	 *
	 * @return the structured content's display date hour
	 * @review
	 */
	public int getDisplayDateHour() {
		return _displayDateHour;
	}

	/**
	 * Returns the structured content's display date minute.
	 *
	 * @return the structured content's display date minute
	 * @review
	 */
	public int getDisplayDateMinute() {
		return _displayDateMinute;
	}

	/**
	 * Returns the structured content's display date month.
	 *
	 * @return the structured content's display date month
	 * @review
	 */
	public int getDisplayDateMonth() {
		return _displayDateMonth;
	}

	/**
	 * Returns the structured content's display date year.
	 *
	 * @return the structured content's display date year
	 * @review
	 */
	public int getDisplayDateYear() {
		return _displayDateYear;
	}

	/**
	 * Returns the service context related with this form
	 *
	 * @param  groupId the group ID
	 * @return the service context
	 * @review
	 */
	public ServiceContext getServiceContext(long groupId) {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setScopeGroupId(groupId);

		if (ListUtil.isNotEmpty(_keywords)) {
			serviceContext.setAssetTagNames(ArrayUtil.toStringArray(_keywords));
		}

		return serviceContext;
	}

	/**
	 * Returns the structured content's structure ID.
	 *
	 * @return the structured content's structure ID
	 * @review
	 */
	public String getStructure() {
		return _structure;
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
	public String getText() {
		return _text;
	}

	/**
	 * Returns the structured content's title map.
	 *
	 * @return the structured content's title map
	 * @review
	 */
	public Map<Locale, String> getTitleMap(Locale locale) {
		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(locale, _title);

		return titleMap;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setDisplayDate(Date displayDate) {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(displayDate);

		_displayDateMonth = calendar.get(Calendar.MONTH);
		_displayDateDay = calendar.get(Calendar.DATE);
		_displayDateYear = calendar.get(Calendar.YEAR);
		_displayDateHour = calendar.get(Calendar.HOUR);
		_displayDateMinute = calendar.get(Calendar.MINUTE);
	}

	public void setKeywords(List<String> keywords) {
		_keywords = keywords;
	}

	public void setStructure(String structure) {
		_structure = structure;
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

	private String _description;
	private Integer _displayDateDay;
	private Integer _displayDateHour;
	private Integer _displayDateMinute;
	private Integer _displayDateMonth;
	private Integer _displayDateYear;
	private List<String> _keywords;
	private String _structure;
	private String _template;
	private String _text;
	private String _title;

}