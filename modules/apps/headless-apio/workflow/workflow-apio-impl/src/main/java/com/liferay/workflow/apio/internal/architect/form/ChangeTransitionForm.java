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

/**
 * @author Sarai Díaz
 */
public class ChangeTransitionForm {

	/**
	 * Builds a {@code Form} that generates {@code ChangeTransitionForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a context form instance
	 * @review
	 */
	public static Form<ChangeTransitionForm> buildForm(
		Form.Builder<ChangeTransitionForm> formBuilder) {

		return formBuilder.title(
			__ -> "Change transition"
		).description(
			__ ->
				"This form can be used to change the transition of a task"
		).constructor(
			ChangeTransitionForm::new
		).addRequiredString(
			"transition", ChangeTransitionForm::setTransition
		).build();
	}

	public String getTransition() {
		return _transition;
	}

	public void setTransition(String transition) {
		_transition = transition;
	}

	private String _transition;

}