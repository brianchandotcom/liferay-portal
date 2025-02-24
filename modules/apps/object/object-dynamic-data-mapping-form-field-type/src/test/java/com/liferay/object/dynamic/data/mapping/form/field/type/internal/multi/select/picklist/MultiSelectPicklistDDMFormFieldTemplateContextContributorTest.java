/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.form.field.type.internal.multi.select.picklist;

import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.test.util.BaseDDMFormFieldTemplateContextContributorTestCase;
import com.liferay.dynamic.data.mapping.test.util.DDMFormFieldOptionsTestUtil;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.dynamic.data.mapping.form.field.type.constants.ObjectDDMFormFieldTypeConstants;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Pedro Leite
 */
public class MultiSelectPicklistDDMFormFieldTemplateContextContributorTest
	extends BaseDDMFormFieldTemplateContextContributorTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_ddmFormField.setDDMForm(getDDMForm());

		ReflectionTestUtil.setFieldValue(
			_multiSelectPicklistDDMFormFieldTemplateContextContributor,
			"_listTypeEntryLocalService", _listTypeEntryLocalService);
	}

	@Test
	public void testGetParametersLocalizedObjectField() {
		_ddmFormField.setProperty("localizedObjectField", true);

		Assert.assertTrue(
			MapUtil.getBoolean(
				_multiSelectPicklistDDMFormFieldTemplateContextContributor.
					getParameters(
						_ddmFormField, createDDMFormFieldRenderingContext()),
				"localizedObjectField"));
	}

	@Test
	public void testGetParametersOptions() throws Exception {
		_ddmFormField.setDDMFormFieldOptions(
			DDMFormFieldOptionsTestUtil.createDDMFormFieldOptions());

		long listTypeDefinitionId = RandomTestUtil.randomLong();

		_ddmFormField.setProperty("listTypeDefinitionId", listTypeDefinitionId);

		Map<Locale, String> labelMap = HashMapBuilder.put(
			LocaleUtil.SPAIN, RandomTestUtil.randomString()
		).put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();

		_mockListTypeEntry(listTypeDefinitionId, labelMap, "value 1");

		Map<String, Object> parameters =
			_multiSelectPicklistDDMFormFieldTemplateContextContributor.
				getParameters(
					_ddmFormField, createDDMFormFieldRenderingContext());

		List<Map<String, Object>> options =
			(List<Map<String, Object>>)parameters.get("options");

		Map<String, Object> option = options.get(0);

		Assert.assertEquals("Label 1", option.get("label"));
		Assert.assertEquals(labelMap, option.get("labelMap"));
		Assert.assertEquals("value 1", option.get("value"));
	}

	private void _mockListTypeEntry(
		Long listTypeDefinitionId, Map<Locale, String> nameMap, String key) {

		Mockito.when(
			_listTypeEntry.getNameMap()
		).thenReturn(
			nameMap
		);

		Mockito.when(
			_listTypeEntryLocalService.fetchListTypeEntry(
				Mockito.eq(listTypeDefinitionId), Mockito.eq(key))
		).thenReturn(
			_listTypeEntry
		);
	}

	private final DDMFormField _ddmFormField = new DDMFormField(
		"field", ObjectDDMFormFieldTypeConstants.MULTISELECT_PICKLIST);
	private final ListTypeEntry _listTypeEntry = Mockito.mock(
		ListTypeEntry.class);
	private final ListTypeEntryLocalService _listTypeEntryLocalService =
		Mockito.mock(ListTypeEntryLocalService.class);
	private final MultiSelectPicklistDDMFormFieldTemplateContextContributor
		_multiSelectPicklistDDMFormFieldTemplateContextContributor =
			new MultiSelectPicklistDDMFormFieldTemplateContextContributor();

}