/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.service.ObjectDefinitionLocalService;
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

		DDMFormInstanceLocalService ddmFormInstanceLocalService = Mockito.mock(
			DDMFormInstanceLocalService.class);

		Mockito.when(
			ddmFormInstanceLocalService.fetchDDMFormInstance(0)
		).thenReturn(
			null
		);

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor,
			"_ddmFormInstanceLocalService", ddmFormInstanceLocalService);

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor, "_language",
			_language);

		ListTypeEntryLocalService listTypeEntryLocalService = Mockito.mock(
			ListTypeEntryLocalService.class);

		Mockito.when(
			listTypeEntryLocalService.getListTypeEntries(Mockito.anyLong())
		).thenReturn(
			null
		);

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor,
			"_listTypeEntryLocalService", listTypeEntryLocalService);

		ObjectDefinitionLocalService objectDefinitionLocalService =
			Mockito.mock(ObjectDefinitionLocalService.class);

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor,
			"_objectDefinitionLocalService", objectDefinitionLocalService);

		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor,
			"ddmFormFieldOptionsFactory", new DDMFormFieldOptionsFactoryImpl());
		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor, "jsonFactory",
			_jsonFactory);
		ReflectionTestUtil.setFieldValue(
			selectDDMFormFieldTemplateContextContributor, "portal", _portal);

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