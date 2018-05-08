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

package com.liferay.expando.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.expando.kernel.exception.ValueDataException;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.expando.kernel.service.ExpandoColumnLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoRowLocalServiceUtil;
import com.liferay.expando.kernel.service.ExpandoValueLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.expando.util.test.ExpandoTestUtil;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@RunWith(Arquillian.class)
public class ExpandoValueLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_classNameId = PortalUtil.getClassNameId(DLFileEntry.class);

		_enLocale = LocaleUtil.fromLanguageId("en_US");
		_frLocale = LocaleUtil.fromLanguageId("fr_FR");
		_ptLocale = LocaleUtil.fromLanguageId("pt_BR");

		_expandoTable = ExpandoTestUtil.addTable(
			_classNameId, "testExpandoTable");
	}

	@Test
	public void testAddLocalizedStringArrayValue() throws Exception {
		ExpandoColumn column = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		Map<Locale, String[]> dataMap = new HashMap<>();

		dataMap.put(_enLocale, new String[] {"one", "two", "three"});
		dataMap.put(_ptLocale, new String[] {"um", "dois", "tres"});

		ExpandoValue value = ExpandoTestUtil.addValue(
			_expandoTable, column, dataMap);

		value = ExpandoValueLocalServiceUtil.getExpandoValue(
			value.getValueId());

		Map<Locale, String[]> stringArrayMap = value.getStringArrayMap();

		String[] enValues = stringArrayMap.get(_enLocale);

		Assert.assertEquals(Arrays.toString(enValues), 3, enValues.length);
		Assert.assertEquals("two", enValues[1]);

		String[] ptValues = stringArrayMap.get(_ptLocale);

		Assert.assertEquals(Arrays.toString(ptValues), 3, ptValues.length);
		Assert.assertEquals("tres", ptValues[2]);
	}

	@Test
	public void testAddLocalizedStringValue() throws Exception {
		ExpandoColumn column = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column",
			ExpandoColumnConstants.STRING_LOCALIZED);

		Map<Locale, String> dataMap = new HashMap<>();

		dataMap.put(_enLocale, "Test");
		dataMap.put(_ptLocale, "Teste");

		ExpandoValue value = ExpandoTestUtil.addValue(
			_expandoTable, column, dataMap);

		value = ExpandoValueLocalServiceUtil.getExpandoValue(
			value.getValueId());

		Map<Locale, String> stringMap = value.getStringMap();

		Assert.assertEquals("Test", stringMap.get(_enLocale));
		Assert.assertEquals("Teste", stringMap.get(_ptLocale));
	}

	@Test
	public void testAddStringArrayValue() throws Exception {
		ExpandoColumn column = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column", ExpandoColumnConstants.STRING_ARRAY);

		ExpandoValue value = ExpandoTestUtil.addValue(
			_expandoTable, column, new String[] {"one", "two, three"});

		value = ExpandoValueLocalServiceUtil.getExpandoValue(
			value.getValueId());

		String[] data = value.getStringArray();

		Assert.assertEquals(Arrays.toString(data), 2, data.length);
		Assert.assertEquals("one", data[0]);
		Assert.assertEquals("two, three", data[1]);
	}

	@Test
	public void testAddWrongValue() throws Exception {
		ExpandoColumn column = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column", ExpandoColumnConstants.STRING);

		Map<Locale, String> dataMap = new HashMap<>();

		dataMap.put(_enLocale, "one");
		dataMap.put(_ptLocale, "um");

		try {
			ExpandoTestUtil.addValue(
				_expandoTable, column, dataMap, LocaleUtil.getDefault());

			Assert.fail();
		}
		catch (ValueDataException vde) {
		}
	}

	@Test
	public void testDeleteAllColumns() throws Exception {
		ExpandoColumn column = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column", ExpandoColumnConstants.STRING);

		long classPK = CounterLocalServiceUtil.increment();

		ExpandoValue value = ExpandoTestUtil.addValue(
			_expandoTable, column, classPK, "value");

		ExpandoValueLocalServiceUtil.deleteColumnValues(column.getColumnId());

		Assert.assertNull(
			ExpandoRowLocalServiceUtil.fetchExpandoRow(value.getRowId()));
	}

	@Test
	public void testDeleteOneColumn() throws Exception {
		ExpandoColumn column1 = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column 1", ExpandoColumnConstants.STRING);

		ExpandoColumn column2 = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column 2", ExpandoColumnConstants.STRING);

		long classPK = CounterLocalServiceUtil.increment();

		ExpandoValue value = ExpandoTestUtil.addValue(
			_expandoTable, column1, classPK, "value");

		ExpandoTestUtil.addValue(_expandoTable, column2, classPK, "value");

		ExpandoValueLocalServiceUtil.deleteColumnValues(column1.getColumnId());

		Assert.assertNotNull(
			ExpandoRowLocalServiceUtil.getRow(value.getRowId()));
	}

	@Test
	public void testGetDefaultColumnValue() throws Exception {
		Map<Locale, String> defaultData = new HashMap<>();

		defaultData.put(_enLocale, "Test");

		ExpandoColumn column = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column",
			ExpandoColumnConstants.STRING_LOCALIZED, defaultData);

		column = ExpandoColumnLocalServiceUtil.getColumn(column.getColumnId());

		Map<Locale, String> data =
			(Map<Locale, String>)column.getDefaultValue();

		Assert.assertEquals("Test", data.get(_enLocale));
	}

	@Test
	public void testGetNonexistingLocaleValue() throws Exception {
		ExpandoColumn column = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column",
			ExpandoColumnConstants.STRING_LOCALIZED);

		Map<Locale, String> dataMap = new HashMap<>();

		dataMap.put(_enLocale, "one");
		dataMap.put(_ptLocale, "um");

		ExpandoValue value = ExpandoTestUtil.addValue(
			_expandoTable, column, dataMap, _ptLocale);

		value = ExpandoValueLocalServiceUtil.getExpandoValue(
			value.getValueId());

		Assert.assertEquals(_ptLocale, value.getDefaultLocale());

		List<Locale> availableLocales = value.getAvailableLocales();

		Assert.assertEquals(_ptLocale, availableLocales.get(0));
		Assert.assertEquals(_enLocale, availableLocales.get(1));

		Assert.assertEquals("um", value.getString(_ptLocale));
		Assert.assertEquals("one", value.getString(_enLocale));
		Assert.assertEquals("um", value.getString(_frLocale));
	}

	@Test
	public void testGetSerializableData() throws Exception {
		ExpandoColumn column = ExpandoTestUtil.addColumn(
			_expandoTable, "Test Column",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		Map<Locale, String[]> dataMap = new HashMap<>();

		dataMap.put(_enLocale, new String[] {"Hello, Joe", "Hi, Joe"});
		dataMap.put(_ptLocale, new String[] {"Ola, Joao", "Oi, Joao"});

		long classPK = CounterLocalServiceUtil.increment();

		ExpandoTestUtil.addValue(_expandoTable, column, classPK, dataMap);

		Serializable serializable = ExpandoValueLocalServiceUtil.getData(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(_classNameId), _expandoTable.getName(),
			column.getName(), classPK);

		Assert.assertTrue(serializable instanceof Map);

		dataMap = (Map<Locale, String[]>)serializable;

		String[] enValues = dataMap.get(_enLocale);

		Assert.assertEquals(Arrays.toString(enValues), 2, enValues.length);
		Assert.assertEquals("Hi, Joe", enValues[1]);
	}

	private long _classNameId;
	private Locale _enLocale;

	@DeleteAfterTestRun
	private ExpandoTable _expandoTable;

	private Locale _frLocale;
	private Locale _ptLocale;

}