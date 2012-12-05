/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import junit.framework.Assert;

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
public class ExpandoValueServiceTest {

	public ExpandoValueServiceTest() {
		_classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

		_en_US = LocaleUtil.fromLanguageId("en_US");
		_pt_BR = LocaleUtil.fromLanguageId("pt_BR");
	}

	@Test
	public void testAddLocalizedStringArrayValue() throws Exception {
		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			TestPropsValues.getCompanyId(), _classNameId, "Test Table 1");

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), "Test Column",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		Map<Locale, String[]> dataMap = new HashMap<Locale, String[]>();

		dataMap.put(_en_US, new String[] {"one", "two", "three"});
		dataMap.put(_pt_BR, new String[] {"um", "dois", "tres"});

		ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.addValue(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(_classNameId), expandoTable.getName(),
			expandoColumn.getName(), CounterLocalServiceUtil.increment(),
			dataMap);

		expandoValue = ExpandoValueLocalServiceUtil.getExpandoValue(
			expandoValue.getValueId());

		Map<Locale, String[]> stringArrayMap = expandoValue.getStringArrayMap();

		String[] enValues = stringArrayMap.get(_en_US);

		Assert.assertEquals(3, enValues.length);
		Assert.assertEquals("two", enValues[1]);

		String[] ptValues = stringArrayMap.get(_pt_BR);

		Assert.assertEquals(3, ptValues.length);
		Assert.assertEquals("tres", ptValues[2]);
	}

	@Test
	public void testAddLocalizedStringValue() throws Exception {
		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			TestPropsValues.getCompanyId(), _classNameId, "Test Table 2");

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), "Test Column",
			ExpandoColumnConstants.STRING_LOCALIZED);

		Map<Locale, String> dataMap = new HashMap<Locale, String>();

		dataMap.put(_en_US, "Test");
		dataMap.put(_pt_BR, "Teste");

		ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.addValue(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(_classNameId), expandoTable.getName(),
			expandoColumn.getName(), CounterLocalServiceUtil.increment(),
			dataMap);

		expandoValue = ExpandoValueLocalServiceUtil.getExpandoValue(
			expandoValue.getValueId());

		Map<Locale, String> stringMap = expandoValue.getStringMap();

		Assert.assertEquals("Test", stringMap.get(_en_US));
		Assert.assertEquals("Teste", stringMap.get(_pt_BR));
	}

	@Test
	public void testAddStringArrayValue() throws Exception {
		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			TestPropsValues.getCompanyId(), _classNameId, "Test Table 3");

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), "Test Column",
			ExpandoColumnConstants.STRING_ARRAY);

		ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.addValue(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(_classNameId), expandoTable.getName(),
			expandoColumn.getName(), CounterLocalServiceUtil.increment(),
			new String[] {"one", "two, three"});

		expandoValue = ExpandoValueLocalServiceUtil.getExpandoValue(
			expandoValue.getValueId());

		String[] data = expandoValue.getStringArray();

		Assert.assertEquals(2, data.length);

		Assert.assertEquals("one", data[0]);
		Assert.assertEquals("two, three", data[1]);
	}

	@Test
	public void testAddWrongValue() throws Exception {
		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			TestPropsValues.getCompanyId(), _classNameId, "Test Table 4");

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), "Test Column",
			ExpandoColumnConstants.STRING);

		Map<Locale, String> dataMap = new HashMap<Locale, String>();

		dataMap.put(_en_US, "one");
		dataMap.put(_pt_BR, "um");

		try {
			ExpandoValueLocalServiceUtil.addValue(
				TestPropsValues.getCompanyId(),
				PortalUtil.getClassName(_classNameId), expandoTable.getName(),
				expandoColumn.getName(), CounterLocalServiceUtil.increment(),
				dataMap);

			Assert.fail();
		}
		catch (ValueDataException vde) {
		}
	}

	@Test
	public void testGetDefaultColumnValue() throws Exception {
		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			TestPropsValues.getCompanyId(), _classNameId, "Test Table 5");

		Map<Locale, String> defaultData = new HashMap<Locale, String>();

		defaultData.put(_en_US, "Test");

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), "Test Column",
			ExpandoColumnConstants.STRING_LOCALIZED, defaultData);

		expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(
			expandoColumn.getColumnId());

		Map<Locale, String> data =
			(Map<Locale, String>)expandoColumn.getDefaultValue();

		Assert.assertEquals("Test", data.get(_en_US));
	}

	@Test
	public void testGetSerializableData() throws Exception {
		ExpandoTable expandoTable = ExpandoTableLocalServiceUtil.addTable(
			TestPropsValues.getCompanyId(), _classNameId, "Test Table 6");

		ExpandoColumn expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(
			expandoTable.getTableId(), "Test Column",
			ExpandoColumnConstants.STRING_ARRAY_LOCALIZED);

		Map<Locale, String[]> dataMap = new HashMap<Locale, String[]>();

		dataMap.put(_en_US, new String[] {"Hello, Joe", "Hi, Joe"});
		dataMap.put(_pt_BR, new String[] {"Ola, Joao", "Oi, Joao"});

		long classPK = CounterLocalServiceUtil.increment();

		ExpandoValueLocalServiceUtil.addValue(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(_classNameId), expandoTable.getName(),
			expandoColumn.getName(), classPK, dataMap);

		Serializable serializable = ExpandoValueLocalServiceUtil.getData(
			TestPropsValues.getCompanyId(),
			PortalUtil.getClassName(_classNameId), expandoTable.getName(),
			expandoColumn.getName(), classPK);

		Assert.assertTrue(serializable instanceof Map);

		dataMap = (Map<Locale, String[]>)serializable;

		String[] enValues = dataMap.get(_en_US);

		Assert.assertEquals(2, enValues.length);
		Assert.assertEquals("Hi, Joe", enValues[1]);
	}

	private long _classNameId;

	private Locale _en_US;
	private Locale _pt_BR;

}