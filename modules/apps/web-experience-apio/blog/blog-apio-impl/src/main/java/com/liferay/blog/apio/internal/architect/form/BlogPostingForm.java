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

package com.liferay.blog.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

import java.util.Calendar;
import java.util.Date;

/**
 * Instances of this class represent the values extracted from a blog posting
 * form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class BlogPostingForm {

	/**
	 * Builds a {@code Form} that generates {@code BlogPostingForm} depending on
	 * the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a blog posting form
	 * @review
	 */
	public static Form<BlogPostingForm> buildForm(
		Builder<BlogPostingForm> formBuilder) {

		return formBuilder.title(
			__ -> "The blog posting form"
		).description(
			__ -> "This form can be used to create or update a blog posting"
		).constructor(
			BlogPostingForm::new
		).addRequiredDate(
			"displayDate", BlogPostingForm::_setDisplayDate
		).addRequiredString(
			"alternativeHeadline", BlogPostingForm::_setAlternativeHeadline
		).addRequiredString(
			"articleBody", BlogPostingForm::_setArticleBody
		).addRequiredString(
			"description", BlogPostingForm::_setDescription
		).addRequiredString(
			"headline", BlogPostingForm::_setHeadline
		).build();
	}

	/**
	 * Returns the blog posting's alternative headline
	 *
	 * @return the blog posting's alternative headline
	 * @review
	 */
	public String getAlternativeHeadline() {
		return _alternativeHeadline;
	}

	/**
	 * Returns the blog posting's body
	 *
	 * @return the blog posting's body
	 * @review
	 */
	public String getArticleBody() {
		return _articleBody;
	}

	/**
	 * Returns the blog posting's description
	 *
	 * @return the blog posting's description
	 * @review
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Returns the blog posting's display date day
	 *
	 * @return the blog posting's display date day
	 * @review
	 */
	public int getDisplayDateDay() {
		return _displayDateDay;
	}

	/**
	 * Returns the blog posting's display date hour
	 *
	 * @return the blog posting's display date hour
	 * @review
	 */
	public int getDisplayDateHour() {
		return _displayDateHour;
	}

	/**
	 * Returns the blog posting's display date minute
	 *
	 * @return the blog posting's display date minute
	 * @review
	 */
	public int getDisplayDateMinute() {
		return _displayDateMinute;
	}

	/**
	 * Returns the blog posting's display date month
	 *
	 * @return the blog posting's display date month
	 * @review
	 */
	public int getDisplayDateMonth() {
		return _displayDateMonth;
	}

	/**
	 * Returns the blog posting's display date year
	 *
	 * @return the blog posting's display date year
	 * @review
	 */
	public int getDisplayDateYear() {
		return _displayDateYear;
	}

	/**
	 * Returns the blog posting's headline
	 *
	 * @return the blog posting's headline
	 * @review
	 */
	public String getHeadline() {
		return _headline;
	}

	private void _setAlternativeHeadline(String alternativeHeadline) {
		_alternativeHeadline = alternativeHeadline;
	}

	private void _setArticleBody(String articleBody) {
		_articleBody = articleBody;
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

	private void _setHeadline(String headline) {
		_headline = headline;
	}

	private String _alternativeHeadline;
	private String _articleBody;
	private String _description;
	private int _displayDateDay;
	private int _displayDateHour;
	private int _displayDateMinute;
	private int _displayDateMonth;
	private int _displayDateYear;
	private String _headline;

}