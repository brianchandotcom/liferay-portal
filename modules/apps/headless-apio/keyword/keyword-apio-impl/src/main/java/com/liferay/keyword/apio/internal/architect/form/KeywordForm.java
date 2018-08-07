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

package com.liferay.keyword.apio.internal.architect.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

/**
 * Instances of this class represent the values extracted from a keyword form.
 *
 * @author Alejandro Hernández
 */
public class KeywordForm {

	public static Form<KeywordForm> buildForm(Builder<KeywordForm> builder) {
		return builder.title(
			__ -> "The keyword form"
		).description(
			__ -> "This form can be used to create or update a keyword"
		).constructor(
			KeywordForm::new
		).addRequiredString(
			"name", KeywordForm::setName
		).build();
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	private String _name;

}