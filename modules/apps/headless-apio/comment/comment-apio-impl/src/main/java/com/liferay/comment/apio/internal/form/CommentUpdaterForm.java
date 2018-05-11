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

package com.liferay.comment.apio.internal.form;

import com.liferay.apio.architect.form.Form;

/**
 * Represents the values extracted from a comment updater form.
 *
 * @author Alejandro Hernández
 */
public class CommentUpdaterForm {

	/**
	 * Builds and returns a {@link Form} that generates a {@code
	 * CommentUpdaterForm} that depends on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return the {@code CommentUpdaterForm}
	 */
	public static Form<CommentUpdaterForm> buildForm(
		Form.Builder<CommentUpdaterForm> formBuilder) {

		return formBuilder.title(
			__ -> "The comment updater form"
		).description(
			__ -> "This form can be used to update a comment"
		).constructor(
			CommentUpdaterForm::new
		).addRequiredString(
			"text", CommentUpdaterForm::_setText
		).build();
	}

	/**
	 * Returns the comment's text.
	 *
	 * @return the comment's text
	 */
	public String getText() {
		return _text;
	}

	private void _setText(String text) {
		_text = text;
	}

	private String _text;

}