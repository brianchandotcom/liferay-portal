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

package com.liferay.dynamic.data.mapping.form.renderer.internal.helper;

import com.liferay.dynamic.data.mapping.form.field.type.internal.DDMFormFieldOptionsFactoryImpl;
import com.liferay.dynamic.data.mapping.form.field.type.internal.checkbox.multiple.CheckboxMultipleDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.date.DateDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.grid.GridDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.numeric.NumericDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.radio.RadioDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.select.SelectDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.form.field.type.internal.text.TextDDMFormFieldTemplateContextContributor;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.util.PortalImpl;

import org.mockito.Mockito;

/**
 * @author Rafael Praxedes
 */
public class DDMFormFieldTemplateContextContributorTestHelper {

	public CheckboxMultipleDDMFormFieldTemplateContextContributor
		createCheckboxMultipleDDMFormFieldTemplateContextContributor() {

		CheckboxMultipleDDMFormFieldTemplateContextContributor
			checkboxMultipleDDMFormFieldTemplateContextContributor =
				new CheckboxMultipleDDMFormFieldTemplateContextContributor();

		ReflectionTestUtil.setFieldValue(
			checkboxMultipleDDMFormFieldTemplateContextContributor,
			"jsonFactory", _jsonFactory);

		return checkboxMultipleDDMFormFieldTemplateContextContributor;
	}

	public DateDDMFormFieldTemplateContextContributor
		createDateDDMFormFieldTemplateContextContributor() {

		DateDDMFormFieldTemplateContextContributor
			dateDDMFormFieldTemplateContextContributor =
				new DateDDMFormFieldTemplateContextContributor();

		ReflectionTestUtil.setFieldValue(
			dateDDMFormFieldTemplateContextContributor, "_language", _language);

		return dateDDMFormFieldTemplateContextContributor;
	}

	public GridDDMFormFieldTemplateContextContributor
		createGridDDMFormFieldTemplateContextContributor() {

		GridDDMFormFieldTemplateContextContributor
			gridDDMFormFieldTemplateContextContributor =
				new GridDDMFormFieldTemplateContextContributor();

		ReflectionTestUtil.setFieldValue(
			gridDDMFormFieldTemplateContextContributor, "jsonFactory",
			_jsonFactory);

		return gridDDMFormFieldTemplateContextContributor;
	}

	public NumericDDMFormFieldTemplateContextContributor
		createNumericDDMFormFieldTemplateContextContributor() {

		return new NumericDDMFormFieldTemplateContextContributor();
	}

	public RadioDDMFormFieldTemplateContextContributor
		createRadioDDMFormFieldTemplateContextContributor() {

		RadioDDMFormFieldTemplateContextContributor
			radioDDMFormFieldTemplateContextContributor =
				new RadioDDMFormFieldTemplateContextContributor();

		ReflectionTestUtil.setFieldValue(
			radioDDMFormFieldTemplateContextContributor, "jsonFactory",
			_jsonFactory);

		return radioDDMFormFieldTemplateContextContributor;
	}

	public SelectDDMFormFieldTemplateContextContributor
		createSelectDDMFormFieldTemplateContextContributor() {

		SelectDDMFormFieldTemplateContextContributor
			selectDDMFormFieldTemplateContextContributor =
				new SelectDDMFormFieldTemplateContextContributor();

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor,
			"ddmFormFieldOptionsFactory", new DDMFormFieldOptionsFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor, "jsonFactory",
			_jsonFactory);
		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor, "portal", _portal);

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor, "_language",
			_language);

		return selectDDMFormFieldTemplateContextContributor;
	}

	public TextDDMFormFieldTemplateContextContributor
		createTextDDMFormFieldTemplateContextContributor() {

		TextDDMFormFieldTemplateContextContributor
			textDDMFormFieldTemplateContextContributor =
				new TextDDMFormFieldTemplateContextContributor();

		ReflectionTestUtil.setFieldValue(
			textDDMFormFieldTemplateContextContributor,
			"ddmFormFieldOptionsFactory", new DDMFormFieldOptionsFactoryImpl());

		return textDDMFormFieldTemplateContextContributor;
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();
	private final Language _language = Mockito.mock(Language.class);
	private final Portal _portal = new PortalImpl();

}