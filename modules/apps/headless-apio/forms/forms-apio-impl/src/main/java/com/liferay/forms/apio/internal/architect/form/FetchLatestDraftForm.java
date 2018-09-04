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
 * @author Paulo Cruz
 */
public class FetchLatestDraftForm {

	/**
	 * Builds a {@code Form} that generates {@code FetchLatestDraftForm}
	 * depending on the HTTP body.
	 *
	 * @param  formBuilder the {@code Form} builder
	 * @return a context form instance
	 */
	public static Form<FetchLatestDraftForm> buildForm(
		Form.Builder<FetchLatestDraftForm> formBuilder) {

		return formBuilder.title(
			__ -> "The fetch latest draft"
		).description(
			__ ->
				"This form can be used to fetch the latest draft record of " +
					"the current user"
		).constructor(
			FetchLatestDraftForm::new
		).build();
	}

}