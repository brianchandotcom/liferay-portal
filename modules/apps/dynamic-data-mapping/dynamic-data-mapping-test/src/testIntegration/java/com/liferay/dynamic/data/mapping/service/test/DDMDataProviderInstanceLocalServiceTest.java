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

package com.liferay.dynamic.data.mapping.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.exception.RequiredDataProviderInstanceException;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDMDataProviderInstanceLocalServiceTest
	extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(
		expected = RequiredDataProviderInstanceException.MustNotDeleteDataProviderInstanceReferencedByDataProviderInstanceLinks.class
	)
	public void testDeleteReferencedDataProviderInstance1() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("Field", "select");

		ddmFormField.setDataType("string");

		DDMDataProviderInstance dataProviderInstance =
			createDDMDataProviderInstance();

		ddmFormField.setProperty("dataSourceType", "data-provider");

		ddmFormField.setProperty(
			"ddmDataProviderInstanceId",
			dataProviderInstance.getDataProviderInstanceId());

		ddmForm.addDDMFormField(ddmFormField);

		ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		DDMDataProviderInstanceLocalServiceUtil.deleteDataProviderInstance(
			dataProviderInstance);
	}

	@Test(
		expected = RequiredDataProviderInstanceException.MustNotDeleteDataProviderInstanceReferencedByDataProviderInstanceLinks.class
	)
	public void testDeleteReferencedDataProviderInstance2() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField ddmFormField = new DDMFormField("Field", "select");

		ddmFormField.setDataType("string");

		DDMDataProviderInstance dataProviderInstance =
			createDDMDataProviderInstance();

		ddmFormField.setProperty("dataSourceType", "data-provider");

		JSONArray dataProviderInstanceIdJSONArray =
			_jsonFactory.createJSONArray();

		dataProviderInstanceIdJSONArray.put(
			dataProviderInstance.getDataProviderInstanceId());

		ddmFormField.setProperty(
			"ddmDataProviderInstanceId", dataProviderInstanceIdJSONArray);

		ddmForm.addDDMFormField(ddmFormField);

		ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		DDMDataProviderInstanceLocalServiceUtil.deleteDataProviderInstance(
			dataProviderInstance);
	}

	@Test(
		expected = RequiredDataProviderInstanceException.MustNotDeleteDataProviderInstanceReferencedByDataProviderInstanceLinks.class
	)
	public void testDeleteReferencedDataProviderInstance3() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("Field1");

		DDMDataProviderInstance dataProviderInstance =
			createDDMDataProviderInstance();

		List<String> actions = new ArrayList<>();

		String action = String.format(
			"call('%s','','')", dataProviderInstance.getUuid());

		actions.add(action);

		DDMFormRule ddmFormRule = new DDMFormRule("TRUE", actions);

		ddmForm.addDDMFormRule(ddmFormRule);

		ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.getValue());

		DDMDataProviderInstanceLocalServiceUtil.deleteDataProviderInstance(
			dataProviderInstance);
	}

	protected DDMDataProviderInstance createDDMDataProviderInstance()
		throws Exception {

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), StringUtil.randomString());

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("dataProviderName");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		return DDMDataProviderInstanceLocalServiceUtil.addDataProviderInstance(
			TestPropsValues.getUserId(), group.getGroupId(), nameMap, nameMap,
			ddmFormValues, "rest", serviceContext);
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}