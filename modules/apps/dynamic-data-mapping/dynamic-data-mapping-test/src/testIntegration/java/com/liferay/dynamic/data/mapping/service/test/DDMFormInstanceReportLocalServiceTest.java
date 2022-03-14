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
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceReportException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcos Martins
 */
@RunWith(Arquillian.class)
public class DDMFormInstanceReportLocalServiceTest
	extends BaseDDMServiceTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		_classNameId = PortalUtil.getClassNameId(DDMFormInstance.class);
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_userId = TestPropsValues.getUserId();
	}

	@Test
	public void testAddFormInstanceReport() throws Exception {
		DDMStructure ddmStructure = addStructure(
			0, _classNameId, null, "Test Structure", null,
			read("ddm-structure-radio-field.xsd"),
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("radio");

		_ddmFormInstance = DDMFormInstanceLocalServiceUtil.addFormInstance(
			ddmStructure.getUserId(), ddmStructure.getGroupId(),
			ddmStructure.getStructureId(), ddmStructure.getNameMap(),
			ddmStructure.getNameMap(),
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm),
			getServiceContext());

		DDMFormInstanceReport ddmFormInstanceReport =
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(
					_ddmFormInstance.getFormInstanceId());

		Assert.assertNotNull(ddmFormInstanceReport);
	}

	@Test(expected = NoSuchFormInstanceReportException.class)
	public void testDeleteDDMFormInstance() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), DDMFormInstance.class.getName());

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("name");

		_ddmFormInstance = DDMFormInstanceLocalServiceUtil.addFormInstance(
			ddmStructure.getUserId(), ddmStructure.getGroupId(),
			ddmStructure.getStructureId(), ddmStructure.getNameMap(),
			ddmStructure.getDescriptionMap(),
			DDMFormValuesTestUtil.createDDMFormValues(ddmForm),
			getServiceContext());

		DDMFormInstanceReport ddmFormInstanceReport =
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(
					_ddmFormInstance.getFormInstanceId());

		Assert.assertNotNull(ddmFormInstanceReport);

		DDMFormInstanceTestUtil.deleteFormInstance(_ddmFormInstance);

		_ddmFormInstanceReportLocalService.
			getFormInstanceReportByFormInstanceId(
				_ddmFormInstance.getFormInstanceId());
	}

	@Test
	public void testUpdateFormInstanceReport() throws Exception {
		DDMFormInstanceRecord ddmFormInstanceRecord =
			createDDMFormInstanceRecord();

		DDMFormInstanceReport ddmFormInstanceReport =
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstanceRecord.getFormInstanceId());

		JSONObject ddmFormInstanceReportDataJSONObject =
			JSONFactoryUtil.createJSONObject(ddmFormInstanceReport.getData());

		Assert.assertEquals(
			1, ddmFormInstanceReportDataJSONObject.getInt("totalItems"));

		JSONObject radioFieldJSONObject =
			ddmFormInstanceReportDataJSONObject.getJSONObject("radio");

		JSONObject radioFieldValuesJSONObject =
			radioFieldJSONObject.getJSONObject("values");

		Assert.assertEquals(1, radioFieldValuesJSONObject.getInt("value 1"));
	}

	@Test
	public void testUpdateFormInstanceReportWhenDeleteFormInstanceRecord()
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			createDDMFormInstanceRecord();

		_ddmFormInstanceRecordLocalService.deleteFormInstanceRecord(
			ddmFormInstanceRecord);

		DDMFormInstanceReport ddmFormInstanceReport =
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstanceRecord.getFormInstanceId());

		JSONObject ddmFormInstanceReportDataJSONObject =
			JSONFactoryUtil.createJSONObject(ddmFormInstanceReport.getData());

		Assert.assertEquals(
			0, ddmFormInstanceReportDataJSONObject.getInt("totalItems"));

		JSONObject radioFieldJSONObject =
			ddmFormInstanceReportDataJSONObject.getJSONObject("radio");

		JSONObject radioFieldValuesJSONObject =
			radioFieldJSONObject.getJSONObject("values");

		Assert.assertEquals(0, radioFieldValuesJSONObject.getInt("value 1"));
	}

	@Test
	public void testUpdateFormInstanceReportWhenFormInstanceHasNestedFields()
		throws Exception {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField textDDMFormField = DDMFormTestUtil.createTextDDMFormField(
			"field1", false, false, false);

		textDDMFormField.addNestedDDMFormField(
			DDMFormTestUtil.createTextDDMFormField(
				"field2", false, false, false));

		ddmForm.addDDMFormField(textDDMFormField);

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(ddmForm, group, _userId);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field1", new UnlocalizedValue("Text 1"));

		ddmFormFieldValue.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"field2", new UnlocalizedValue("Text 2")));

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormInstanceRecordLocalService.addFormInstanceRecord(
			_userId, group.getGroupId(), ddmFormInstance.getFormInstanceId(),
			ddmFormValues, getServiceContext());

		DDMFormInstanceReport ddmFormInstanceReport =
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstance.getFormInstanceId());

		JSONObject ddmFormInstanceReportDataJSONObject =
			JSONFactoryUtil.createJSONObject(ddmFormInstanceReport.getData());

		Assert.assertEquals(
			1, ddmFormInstanceReportDataJSONObject.getInt("totalItems"));

		JSONObject field1JSONObject =
			ddmFormInstanceReportDataJSONObject.getJSONObject("field1");

		Assert.assertEquals(
			"Text 1",
			field1JSONObject.getJSONArray(
				"values"
			).getJSONObject(
				0
			).getString(
				"value"
			));

		JSONObject field2JSONObject =
			ddmFormInstanceReportDataJSONObject.getJSONObject("field2");

		Assert.assertEquals(
			"Text 2",
			field2JSONObject.getJSONArray(
				"values"
			).getJSONObject(
				0
			).getString(
				"value"
			));
	}

	@Test
	public void testUpdateFormInstanceReportWhenUpdateFormInstanceRecord()
		throws Exception {

		DDMFormInstanceRecord ddmFormInstanceRecord =
			createDDMFormInstanceRecord();

		DDMFormValues ddmFormValues = ddmFormInstanceRecord.getDDMFormValues();

		DDMFormValues newDDMFormValues =
			DDMFormValuesTestUtil.createDDMFormValues(
				ddmFormValues.getDDMForm());

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"radio",
				new LocalizedValue() {
					{
						addString(LocaleUtil.US, "value 2");
					}
				});

		newDDMFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		_ddmFormInstanceRecordLocalService.updateFormInstanceRecord(
			_userId, ddmFormInstanceRecord.getFormInstanceRecordId(), false,
			newDDMFormValues, getServiceContext());

		DDMFormInstanceReport ddmFormInstanceReport =
			_ddmFormInstanceReportLocalService.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstanceRecord.getFormInstanceId());

		JSONObject ddmFormInstanceReportDataJSONObject =
			JSONFactoryUtil.createJSONObject(ddmFormInstanceReport.getData());

		Assert.assertEquals(
			1, ddmFormInstanceReportDataJSONObject.getInt("totalItems"));

		JSONObject radioFieldJSONObject =
			ddmFormInstanceReportDataJSONObject.getJSONObject("radio");

		JSONObject radioFieldValuesJSONObject =
			radioFieldJSONObject.getJSONObject("values");

		Assert.assertEquals(0, radioFieldValuesJSONObject.getInt("value 1"));
		Assert.assertEquals(1, radioFieldValuesJSONObject.getInt("value 2"));
	}

	protected DDMFormInstanceRecord createDDMFormInstanceRecord()
		throws Exception {

		DDMStructure ddmStructure = addStructure(
			0, _classNameId, null, "Test Structure", null,
			read("ddm-structure-radio-field.xsd"),
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm("radio");

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"radio",
				new LocalizedValue() {
					{
						addString(LocaleUtil.US, "value 1");
					}
				});

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		ServiceContext serviceContext = getServiceContext();

		_ddmFormInstance = DDMFormInstanceLocalServiceUtil.addFormInstance(
			ddmStructure.getUserId(), ddmStructure.getGroupId(),
			ddmStructure.getStructureId(), ddmStructure.getNameMap(),
			ddmStructure.getNameMap(),
			DDMFormInstanceTestUtil.createSettingsDDMFormValues(),
			serviceContext);

		return DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
			_userId, group.getGroupId(), _ddmFormInstance.getFormInstanceId(),
			ddmFormValues, serviceContext);
	}

	protected ServiceContext getServiceContext() throws PortalException {
		return ServiceContextTestUtil.getServiceContext(group, _userId);
	}

	private static long _classNameId;

	@DeleteAfterTestRun
	private DDMFormInstance _ddmFormInstance;

	@Inject
	private DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;

	@Inject
	private DDMFormInstanceReportLocalService
		_ddmFormInstanceReportLocalService;

	private long _userId;

}