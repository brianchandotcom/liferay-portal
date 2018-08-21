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

package com.liferay.structure.apio.internal.model;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.structure.apio.architect.model.FormLayoutPage;

import java.util.List;
import java.util.Locale;

/**
 * @author Paulo Cruz
 */
public class FormLayoutPageImpl implements FormLayoutPage {

	public FormLayoutPageImpl(
		DDMFormLayoutPage ddmFormLayoutPage, List<DDMFormField> fields) {

		_description = ddmFormLayoutPage.getDescription();
		_fields = fields;
		_title = ddmFormLayoutPage.getTitle();
	}

	public String getDescription(Locale locale) {
		return _description.getString(locale);
	}

	public List<DDMFormField> getFields() {
		return _fields;
	}

	public String getTitle(Locale locale) {
		return _title.getString(locale);
	}

	private final LocalizedValue _description;
	private final List<DDMFormField> _fields;
	private final LocalizedValue _title;

}