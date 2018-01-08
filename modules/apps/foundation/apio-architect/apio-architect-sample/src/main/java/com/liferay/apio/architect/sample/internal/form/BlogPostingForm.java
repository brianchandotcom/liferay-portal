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

package com.liferay.apio.architect.sample.internal.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

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
	 */
	public static Form<BlogPostingForm> buildForm(
		Builder<BlogPostingForm> formBuilder) {

		return formBuilder.title(
			__ -> "The blog posting form"
		).description(
			__ -> "This form can be used to create or update a blog posting"
		).constructor(
			BlogPostingForm::new
		).addRequiredString(
			"headline", BlogPostingForm::_setHeadline
		).addRequiredString(
			"articleBody", BlogPostingForm::_setArticleBody
		).addRequiredLong(
			"creator", BlogPostingForm::_setCreator
		).addRequiredString(
			"alternativeHeadline", BlogPostingForm::_setAlternativeHeadline
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
	 * Returns the blog posting's creator ID
	 *
	 * @return the blog posting's creator ID
	 * @review
	 */
	public Long getCreator() {
		return _creator;
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

	private void _setAlternativeHeadline(String lastName) {
		_alternativeHeadline = lastName;
	}

	private void _setArticleBody(String articleBody) {
		_articleBody = articleBody;
	}

	private void _setCreator(Long creator) {
		_creator = creator;
	}

	private void _setHeadline(String headline) {
		_headline = headline;
	}

	private String _alternativeHeadline;
	private String _articleBody;
	private Long _creator;
	private String _headline;

}