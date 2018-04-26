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

package com.liferay.dynamic.data.mapping.form.renderer.internal.util;

import com.liferay.dynamic.data.mapping.form.field.type.internal.DDMFormFieldOptionsFactoryImpl;
import com.liferay.dynamic.data.mapping.type.checkbox.multiple.internal.CheckboxMultipleDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.type.date.internal.DateDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.type.grid.internal.GridDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.type.numeric.internal.NumericDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.type.radio.internal.RadioDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.type.select.internal.SelectDDMFormFieldTemplateContextContributor;
import com.liferay.dynamic.data.mapping.type.text.internal.TextDDMFormFieldTemplateContextContributor;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactory;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Rafael Praxedes
 */
public class DDMFormFieldTemplateContextContributorTestHelper
	extends PowerMockito {

	public CheckboxMultipleDDMFormFieldTemplateContextContributor
			createCheckboxMultipleDDMFormFieldTemplateContextContributor()
		throws Exception {

		CheckboxMultipleDDMFormFieldTemplateContextContributor
			checkboxMultipleDDMFormFieldTemplateContextContributor =
				new CheckboxMultipleDDMFormFieldTemplateContextContributor();

		field(
			CheckboxMultipleDDMFormFieldTemplateContextContributor.class,
			"jsonFactory"
		).set(
			checkboxMultipleDDMFormFieldTemplateContextContributor, _jsonFactory
		);

		return checkboxMultipleDDMFormFieldTemplateContextContributor;
	}

	public DateDDMFormFieldTemplateContextContributor
		createDateDDMFormFieldTemplateContextContributor() {

		return new DateDDMFormFieldTemplateContextContributor();
	}

	public GridDDMFormFieldTemplateContextContributor
		createGridDDMFormFieldTemplateContextContributor() throws Exception {

		GridDDMFormFieldTemplateContextContributor
			gridDDMFormFieldTemplateContextContributor =
				new GridDDMFormFieldTemplateContextContributor();

		field(
			GridDDMFormFieldTemplateContextContributor.class, "jsonFactory"
		).set(
			gridDDMFormFieldTemplateContextContributor, _jsonFactory
		);

		return gridDDMFormFieldTemplateContextContributor;
	}

	public NumericDDMFormFieldTemplateContextContributor
		createNumericDDMFormFieldTemplateContextContributor() throws Exception {

		NumericDDMFormFieldTemplateContextContributor
			numericDDMFormFieldTemplateContextContributor =
				new NumericDDMFormFieldTemplateContextContributor();

		return numericDDMFormFieldTemplateContextContributor;
	}

	public RadioDDMFormFieldTemplateContextContributor
		createRadioDDMFormFieldTemplateContextContributor() throws Exception {

		RadioDDMFormFieldTemplateContextContributor
			radioDDMFormFieldTemplateContextContributor =
				new RadioDDMFormFieldTemplateContextContributor();

		field(
			RadioDDMFormFieldTemplateContextContributor.class, "jsonFactory"
		).set(
			radioDDMFormFieldTemplateContextContributor, _jsonFactory
		);

		return radioDDMFormFieldTemplateContextContributor;
	}

	public SelectDDMFormFieldTemplateContextContributor
		createSelectDDMFormFieldTemplateContextContributor() throws Exception {

		SelectDDMFormFieldTemplateContextContributor
			selectDDMFormFieldTemplateContextContributor =
				new SelectDDMFormFieldTemplateContextContributor();

		field(
			SelectDDMFormFieldTemplateContextContributor.class,
			"ddmFormFieldOptionsFactory"
		).set(
			selectDDMFormFieldTemplateContextContributor,
			new DDMFormFieldOptionsFactoryImpl()
		);

		field(
			SelectDDMFormFieldTemplateContextContributor.class, "jsonFactory"
		).set(
			selectDDMFormFieldTemplateContextContributor, _jsonFactory
		);

		return selectDDMFormFieldTemplateContextContributor;
	}

	public TextDDMFormFieldTemplateContextContributor
		createTextDDMFormFieldTemplateContextContributor() throws Exception {

		TextDDMFormFieldTemplateContextContributor
			textDDMFormFieldTemplateContextContributor =
				new TextDDMFormFieldTemplateContextContributor();

		field(
			TextDDMFormFieldTemplateContextContributor.class,
			"ddmFormFieldOptionsFactory"
		).set(
			textDDMFormFieldTemplateContextContributor,
			new DDMFormFieldOptionsFactoryImpl()
		);

		return textDDMFormFieldTemplateContextContributor;
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}