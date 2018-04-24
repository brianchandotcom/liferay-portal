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

package com.liferay.tag.apio.internal.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.form.Form.Builder;

/**
 * @author Alejandro Hernández
 */
public class TagForm {

	public static Form<TagForm> buildForm(Builder<TagForm> builder) {
		return builder.title(
			__ -> "The tag form"
		).description(
			__ -> "This form can be used to create or update a tag"
		).constructor(
			TagForm::new
		).addRequiredString(
			"name", TagForm::_setName
		).build();
	}

	public String getName() {
		return _name;
	}

	private void _setName(String name) {
		_name = name;
	}

	private String _name;

}