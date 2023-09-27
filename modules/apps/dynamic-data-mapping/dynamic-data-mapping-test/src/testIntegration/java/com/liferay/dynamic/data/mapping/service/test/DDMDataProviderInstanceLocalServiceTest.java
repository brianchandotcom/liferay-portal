/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
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
			ddmForm, StorageType.DEFAULT.getValue());

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
			ddmForm, StorageType.DEFAULT.getValue());

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

		DDMFormRule ddmFormRule = new DDMFormRule(actions, "TRUE");

		ddmForm.addDDMFormRule(ddmFormRule);

		ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.DEFAULT.getValue());

		DDMDataProviderInstanceLocalServiceUtil.deleteDataProviderInstance(
			dataProviderInstance);
	}

	protected DDMDataProviderInstance createDDMDataProviderInstance()
		throws Exception {

		Map<Locale, String> nameMap = HashMapBuilder.put(
			LocaleUtil.getSiteDefault(), StringUtil.randomString()
		).build();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("dataProviderName");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		return DDMDataProviderInstanceLocalServiceUtil.addDataProviderInstance(
			TestPropsValues.getUserId(), group.getGroupId(), nameMap, nameMap,
			ddmFormValues, "rest",
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId()));
	}

	private final JSONFactory _jsonFactory = new JSONFactoryImpl();

}