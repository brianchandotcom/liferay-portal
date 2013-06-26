/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.service;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.expando.ValueDataException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.util.ExpandoTestUtil;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcellus Tavares
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class ExpandoValueLocalServiceTest {

	@Before
	public void setUp() {
		_classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

		_enLocale = LocaleUtil.fromLanguageId("en_US");
		_frLocale = LocaleUtil.fromLanguageId("fr_FR");
		_ptLocale = LocaleUtil.fromLanguageId("pt_BR");
	}

	@Test
	public void testAddLocalizedStringArrayValue() throws Exception {
		ExpandoTable table = ExpandoTestUtil.addTable(
			_classNameId, "testAddLocalizedStringArrayValue");

		ExpandoColumn column = ExpandoTestUtil.addColumn(
			table, "Test Column",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		Map<Locale, String[]> dataMap = new HashMap<Locale, String[]>();

		dataMap.put(_enLocale, new String[] {"one", "two", "three"});
		dataMap.put(_ptLocale, new String[] {"um", "dois", "tres"});

		ExpandoValue value = ExpandoTestUtil.addValue(table, column, dataMap);

		value = ExpandoValueLocalServiceUtil.getExpandoValue(
			value.getValueId());

		Map<Locale, String[]> stringArrayMap = value.getStringArrayMap();

		String[] enValues = stringArrayMap.get(_enLocale);

		Assert.assertEquals(3, enValues.length);
		Assert.assertEquals("two", enValues[1]);

		String[] ptValues = stringArrayMap.get(_ptLocale);

		Assert.assertEquals(3, ptValues.length);
		Assert.assertEquals("tres", ptValues[2]);
	}

	@Test
	public void testAddLocalizedStringValue() throws Exception {
		ExpandoTable table = ExpandoTestUtil.addTable(
			_classNameId, "testAddLocalizedStringValue");

		ExpandoColumn column = ExpandoTestUtil.addColumn(
			table, "Test Column", ExpandoColumnConstants.STRING_LOCALIZED);

		Map<Locale, String> dataMap = new HashMap<Locale, String>();

		dataMap.put(_enLocale, "Test");
		dataMap.put(_ptLocale, "Teste");

		ExpandoValue value = ExpandoTestUtil.addValue(table, column, dataMap);

		value = ExpandoValueLocalServiceUtil.getExpandoValue(
			value.getValueId());

		Map<Locale, String> stringMap = value.getStringMap();

		Assert.assertEquals("Test", stringMap.get(_enLocale));
		Assert.assertEquals("Teste", stringMap.get(_ptLocale));
	}

	@Test
	public void testAddStringArrayValue() throws Exception {
		ExpandoTable table = ExpandoTestUtil.addTable(
			_classNameId, "testAddStringArrayValue");

		ExpandoColumn column = ExpandoTestUtil.addColumn(
			table, "Test Column", ExpandoColumnConstants.STRING_ARRAY);

		ExpandoValue value = ExpandoTestUtil.addValue(
			table, column, new String[] {"one", "two, three"});

		value = ExpandoValueLocalServiceUtil.getExpandoValue(
			value.getValueId());

		String[] data = value.getStringArray();

		Assert.assertEquals(2, data.length);
		Assert.assertEquals("one", data[0]);
		Assert.assertEquals("two, three", data[1]);
	}

	@Test
	public void testAddWrongValue() throws Exception {
		ExpandoTable table = ExpandoTestUtil.addTable(
			_classNameId, "testAddWrongValue");

		ExpandoColumn column = ExpandoTestUtil.addColumn(
			table, "Test Column", ExpandoColumnConstants.STRING);

		Map<Locale, String> dataMap = new HashMap<Locale, String>();

		dataMap.put(_enLocale, "one");
		dataMap.put(_ptLocale, "um");

		try {
			ExpandoTestUtil.addValue(
				table, column, dataMap, LocaleUtil.getDefault());

			Assert.fail();
		}
		catch (ValueDataException vde) {
		}
	}

	@Test
	public void testGetDefaultColumnValue() throws Exception {
		ExpandoTable table = ExpandoTestUtil.addTable(
			_classNameId, "testGetDefaultColumnValue");

		Map<Locale, String> defaultData = new HashMap<Locale, String>();

		defaultData.put(_enLocale, "Test");

		ExpandoColumn column = ExpandoTestUtil.addColumn(
			table, "Test Column", ExpandoColumnConstants.STRING_LOCALIZED,
			defaultData);

		column = ExpandoColumnLocalServiceUtil.getColumn(column.getColumnId());

		Map<Locale, String> data =
			(Map<Locale, String>)column.getDefaultValue();

		Assert.assertEquals("Test", data.get(_enLocale));
	}

	@Test
	public void testGetNonexistingLocaleValue() throws Exception {
		ExpandoTable table = ExpandoTestUtil.addTable(
			_classNameId, "testGetNonexistingLocaleValue");

		ExpandoColumn column = ExpandoTestUtil.addColumn(
			table, "Test Column", ExpandoColumnConstants.STRING_LOCALIZED);

		Map<Locale, String> dataMap = new HashMap<Locale, String>();

		dataMap.put(_enLocale, "one");
		dataMap.put(_ptLocale, "um");

		ExpandoValue value = ExpandoTestUtil.addValue(
			table, column, dataMap, _ptLocale);

		value = ExpandoValueLocalServiceUtil.getExpandoValue(
			value.getValueId());

		Assert.assertEquals(_ptLocale, value.getDefaultLocale());

		List<Locale> availableLocales = value.getAvailableLocales();

		Assert.assertEquals(_ptLocale, availableLocales.get(0));
		Assert.assertEquals(_enLocale, availableLocales.get(1));
		Assert.assertEquals("um" , value.getString(_ptLocale));
		Assert.assertEquals("one" , value.getString(_enLocale));
		Assert.assertEquals("um" , value.getString(_frLocale));
	}

	@Test
	public void testGetSerializableData() throws Exception {
		ExpandoTable table = ExpandoTestUtil.addTable(
			_classNameId, "testGetSerializableData");

		ExpandoColumn column = ExpandoTestUtil.addColumn(
			table, "Test Column",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		Map<Locale, String[]> dataMap = new HashMap<Locale, String[]>();

		dataMap.put(_enLocale, new String[] {"Hello, Joe", "Hi, Joe"});
		dataMap.put(_ptLocale, new String[] {"Ola, Joao", "Oi, Joao"});

		long classPK = CounterLocalServiceUtil.increment();

		ExpandoTestUtil.addValue(table, column, classPK, dataMap);

		Serializable serializable = ExpandoValueLocalServiceUtil.getData(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(_classNameId), table.getName(),
			column.getName(), classPK);

		Assert.assertTrue(serializable instanceof Map);

		dataMap = (Map<Locale, String[]>)serializable;

		String[] enValues = dataMap.get(_enLocale);

		Assert.assertEquals(2, enValues.length);
		Assert.assertEquals("Hi, Joe", enValues[1]);
	}

	private long _classNameId;
	private Locale _enLocale;
	private Locale _frLocale;
	private Locale _ptLocale;

}