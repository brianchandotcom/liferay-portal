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

package com.liferay.workflow.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;

import java.util.Date;

/**
 * @author Sarai Díaz
 */
public class AssignToMeForm {

	/**
	 * Builds a {@code Form} that generates {@code AssignToMeForm}
	 * depending on the HTTP body.
	 *
	 * @param formBuilder the {@code Form} builder
	 * @return a context form instance
	 * @review
	 */
	public static Form<AssignToMeForm> buildForm(
		Form.Builder<AssignToMeForm> formBuilder) {

		return formBuilder.title(
			__ -> "Assign task"
		).description(
			__ ->
				"This form can be used to assign a task to an user"
		).constructor(
			AssignToMeForm::new
		).addOptionalString(
			"comment", AssignToMeForm::setComment
		).addOptionalDate(
			"dueDate", AssignToMeForm::setDueDate
		).build();
	}

	public String getComment() {
		if (_comment == null) {
			return "";
		}

		return _comment;
	}

	public Date getDueDate() {
		if (_dueDate == null) {
			return null;
		}

		return _dueDate;
	}

	public void setComment(String comment) {
		_comment = comment;
	}

	public void setDueDate(Date dueDate) {
		_dueDate = dueDate;
	}

	private String _comment;
	private Date _dueDate;

}