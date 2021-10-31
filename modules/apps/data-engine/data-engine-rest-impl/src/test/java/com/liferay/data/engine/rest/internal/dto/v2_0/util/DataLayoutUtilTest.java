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

package com.liferay.data.engine.rest.internal.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutColumn;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutPage;
import com.liferay.data.engine.rest.dto.v2_0.DataLayoutRow;
import com.liferay.dynamic.data.mapping.form.builder.rule.DDMFormRuleDeserializer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.ArrayList;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Marcela Cunha
 */
@PrepareForTest(LocaleUtil.class)
@RunWith(PowerMockRunner.class)
public class DataLayoutUtilTest extends PowerMockito {

	@Before
	public void setUp() {
		_setUpJSONFactoryUtil();
		_setUpLocaleUtil();
	}

	@Test
	public void testToDDMFormLayoutEquals() throws Exception {
		Locale locale = LocaleUtil.US;

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(locale), locale);

		ddmForm.addDDMFormField(
			new DDMFormField() {
				{
					setIndexType("text");
					setLabel(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "label1"
							).build(),
							locale));
					setLocalizable(true);
					setName("textName");
					setPredefinedValue(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "enter a text"
							).build(),
							locale));
					setReadOnly(true);
					setRepeatable(true);
					setRequired(true);
					setShowLabel(true);
					setTip(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "tip1"
							).build(),
							locale));
					setType("text");
				}
			});

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		ddmFormLayout.setDefaultLocale(LocaleUtil.US);
		ddmFormLayout.setPaginationMode("wizard");

		ddmFormLayout.addDDMFormLayoutPage(
			new DDMFormLayoutPage() {
				{
					setDDMFormLayoutRows(
						new ArrayList<DDMFormLayoutRow>() {
							{
								add(
									new DDMFormLayoutRow() {
										{
											setDDMFormLayoutColumns(
												new ArrayList
													<DDMFormLayoutColumn>() {

													{
														add(
															new DDMFormLayoutColumn(
																DDMFormLayoutColumn.FULL,
																"textName"));
													}
												});
										}
									});
							}
						});
					setDescription(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "Description"
							).build(),
							LocaleUtil.US));
					setTitle(
						LocalizedValueUtil.toLocalizedValue(
							HashMapBuilder.<String, Object>put(
								"en_US", "Title"
							).build(),
							LocaleUtil.US));
				}
			});

		DataLayout dataLayout = new DataLayout();

		dataLayout.setDataLayoutPages(
			new DataLayoutPage[] {
				new DataLayoutPage() {
					{
						setDataLayoutRows(
							new DataLayoutRow[] {
								new DataLayoutRow() {
									{
										setDataLayoutColumns(
											new DataLayoutColumn[] {
												new DataLayoutColumn() {
													{
														setColumnSize(12);
														setFieldNames(
															new String[] {
																"textName"
															});
													}
												}
											});
									}
								}
							});
						setDescription(
							HashMapBuilder.<String, Object>put(
								"en_US", "Description"
							).build());
						setTitle(
							HashMapBuilder.<String, Object>put(
								"en_US", "Title"
							).build());
					}
				}
			});
		dataLayout.setPaginationMode("wizard");

		DDMFormRuleDeserializer ddmFormRuleDeserializer = PowerMockito.mock(
			DDMFormRuleDeserializer.class);

		Mockito.when(
			ddmFormRuleDeserializer.deserialize(
				Mockito.anyObject(), Mockito.anyObject())
		).thenReturn(
			new ArrayList<DDMFormRule>()
		);

		Assert.assertEquals(
			ddmFormLayout,
			DataLayoutUtil.toDDMFormLayout(
				dataLayout, ddmForm,
				PowerMockito.mock(DDMFormFieldTypeServicesTracker.class),
				ddmFormRuleDeserializer));
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLocaleUtil() {
		mockStatic(LocaleUtil.class);

		when(
			LocaleUtil.fromLanguageId("en_US")
		).thenReturn(
			LocaleUtil.US
		);

		when(
			LocaleUtil.toLanguageId(LocaleUtil.US)
		).thenReturn(
			"en_US"
		);
	}

}