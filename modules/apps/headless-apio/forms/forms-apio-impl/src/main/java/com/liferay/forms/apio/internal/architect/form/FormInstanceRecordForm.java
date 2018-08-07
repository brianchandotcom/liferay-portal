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

package com.liferay.forms.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;

/**
 * Instances of this class represent the values extracted from a form instance
 * record form.
 *
 * @author Paulo Cruz
 */
public class FormInstanceRecordForm {

	/**
	 * Builds a {@code Form} that generates {@code FormInstanceRecordForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a form instance record form
	 */
	public static Form<FormInstanceRecordForm> buildForm(
		Form.Builder<FormInstanceRecordForm> formBuilder) {

		return formBuilder.title(
			__ -> "The form instance record creator and updater form"
		).description(
			__ ->
				"This form can be used to create or update a form instance " +
					"record"
		).constructor(
			FormInstanceRecordForm::new
		).addRequiredString(
			"fieldValues", FormInstanceRecordForm::setFieldValues
		).addRequiredBoolean(
			"isDraft", FormInstanceRecordForm::setDraft
		).build();
	}

	public String getFieldValues() {
		return _fieldValues;
	}

	public boolean isDraft() {
		return _draft;
	}

	public void setDraft(boolean draft) {
		_draft = draft;
	}

	public void setFieldValues(String formValues) {
		_fieldValues = formValues;
	}

	private boolean _draft;
	private String _fieldValues;

}