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
 * comment creator form.
 *
 * @author Alejandro Hernández
 * @review
 */
public class BlogPostingCommentCreatorForm {

	/**
	 * Builds a {@code Form} that generates {@code
	 * BlogPostingCommentCreatorForm} depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a blog posting comment creator form
	 * @review
	 */
	public static Form<BlogPostingCommentCreatorForm> buildForm(
		Builder<BlogPostingCommentCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The blog posting comment creator form"
		).description(
			__ -> "This form can be used to create a blog posting comment"
		).constructor(
			BlogPostingCommentCreatorForm::new
		).addRequiredString(
			"text", BlogPostingCommentCreatorForm::_setText
		).addRequiredLong(
			"author", BlogPostingCommentCreatorForm::_setAuthor
		).build();
	}

	/**
	 * Returns the blog posting comment's author ID
	 *
	 * @return the blog posting comment's author ID
	 * @review
	 */
	public Long getAuthor() {
		return _author;
	}

	/**
	 * Returns the blog posting comment's text
	 *
	 * @return the blog posting comment's text
	 * @review
	 */
	public String getText() {
		return _text;
	}

	private void _setAuthor(Long author) {
		_author = author;
	}

	private void _setText(String text) {
		_text = text;
	}

	private Long _author;
	private String _text;

}