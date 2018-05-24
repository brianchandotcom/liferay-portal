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

package com.liferay.commerce.headless.product.apio.internal.form;

import com.liferay.apio.architect.form.Form;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rodrigo Guedes de Souza
 */
public class OptionValueForm {

	public static Form<OptionValueForm> buildForm(
		Form.Builder<OptionValueForm> formBuilder) {

		return formBuilder.title(
			__ -> "The option value form"
		).description(
			__ -> "This form can be used to create a option value"
		).constructor(
			OptionValueForm::new
		).addRequiredString(
			"name", OptionValueForm::_setName
		).addRequiredString(
			"key", OptionValueForm::_setKey
		).build();
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public Map<Locale, String> getNameMap() {
		return Collections.singletonMap(Locale.getDefault(), _name);
	}

	private void _setKey(String key) {
		_key = key;
	}

	private void _setName(String name) {
		_name = name;
	}

	private String _key;
	private String _name;

}