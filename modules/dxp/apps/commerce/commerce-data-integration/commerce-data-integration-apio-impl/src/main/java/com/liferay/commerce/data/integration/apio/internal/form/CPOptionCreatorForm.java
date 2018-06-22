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

package com.liferay.commerce.data.integration.apio.internal.form;

import com.liferay.apio.architect.form.Form;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rodrigo Guedes de Souza
 */
public class CPOptionCreatorForm {

	public static Form<CPOptionCreatorForm> buildForm(
		Form.Builder<CPOptionCreatorForm> formBuilder) {

		return formBuilder.title(
			__ -> "The option creator form"
		).description(
			__ -> "This form can be used to create an option"
		).constructor(
			CPOptionCreatorForm::new
		).addRequiredString(
			"name", CPOptionCreatorForm::_setName
		).addOptionalString(
			"description", CPOptionCreatorForm::_setDescription
		).addRequiredString(
			"fieldType", CPOptionCreatorForm::_setFieldType
		).addRequiredString(
			"key", CPOptionCreatorForm::_setKey
		).build();
	}

	public String getDescription() {
		return _description;
	}

	public Map<Locale, String> getDescriptionMap() {
		return Collections.singletonMap(LocaleUtil.getDefault(), _description);
	}

	public String getFieldType() {
		return _fieldType;
	}

	public String getKey() {
		return _key;
	}

	public String getName() {
		return _name;
	}

	public Map<Locale, String> getNameMap() {
		return Collections.singletonMap(LocaleUtil.getDefault(), _name);
	}

	private void _setDescription(String description) {
		_description = description;
	}

	private void _setFieldType(String fieldType) {
		_fieldType = fieldType;
	}

	private void _setKey(String key) {
		_key = key;
	}

	private void _setName(String name) {
		_name = name;
	}

	private String _description;
	private String _fieldType;
	private String _key;
	private String _name;

}