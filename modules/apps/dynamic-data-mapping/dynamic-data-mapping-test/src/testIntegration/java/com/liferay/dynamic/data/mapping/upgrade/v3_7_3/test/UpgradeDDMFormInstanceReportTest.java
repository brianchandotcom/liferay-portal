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

package com.liferay.dynamic.data.mapping.upgrade.v3_7_3.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceReport;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceReportLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.dynamic.data.mapping.test.util.DDMFormInstanceTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeStep;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marcos Martins
 */
@RunWith(Arquillian.class)
public class UpgradeDDMFormInstanceReportTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_jsonFactory = new JSONFactoryImpl();

		_userId = TestPropsValues.getUserId();

		setUpUpgradeDDMFormInstanceReport();
	}

	@Test
	public void testUpgradeDDMFormInstanceReportWhenFormRecordHasDraft()
		throws Exception {

		DDMForm ddmForm = _createDDMForm();

		ServiceContext serviceContext = getServiceContext();

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_createDDMFormInstanceRecord(ddmForm, serviceContext);

		DDMFormValues ddmFormValues = _updateTextDDMFormFieldValue(
			ddmForm, "Draft Text");

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		DDMFormInstanceRecordLocalServiceUtil.updateFormInstanceRecord(
			_userId, ddmFormInstanceRecord.getFormInstanceRecordId(), false,
			ddmFormValues, serviceContext);

		DDMFormInstanceReport ddmFormInstanceReport =
			DDMFormInstanceReportLocalServiceUtil.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstanceRecord.getFormInstanceId());

		_testUpgrade(ddmFormInstanceReport);
	}

	@Test
	public void testUpgradeDDMFormInstanceReportWhenFormRecordHasTwoApprovedVersions()
		throws Exception {

		DDMForm ddmForm = _createDDMForm();

		ServiceContext serviceContext = getServiceContext();

		DDMFormInstanceRecord ddmFormInstanceRecord =
			_createDDMFormInstanceRecord(ddmForm, serviceContext);

		DDMFormValues ddmFormValues = _updateTextDDMFormFieldValue(
			ddmForm, "Approved Text 2");

		DDMFormInstanceRecordLocalServiceUtil.updateFormInstanceRecord(
			_userId, ddmFormInstanceRecord.getFormInstanceRecordId(), false,
			ddmFormValues, serviceContext);

		DDMFormInstanceReport ddmFormInstanceReport =
			DDMFormInstanceReportLocalServiceUtil.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstanceRecord.getFormInstanceId());

		_testUpgrade(ddmFormInstanceReport);
	}

	@Test
	public void testUpgradeDDMFormInstanceReportWithEmptyEntries()
		throws Exception {

		DDMForm ddmForm = _createDDMForm();

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				ddmForm, _group, _userId);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"checkboxMultiple",
				new UnlocalizedValue(
					JSONUtil.put(
						StringPool.BLANK
					).toString())));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"grid", new UnlocalizedValue(StringPool.BLANK)));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"numeric", new UnlocalizedValue(StringPool.BLANK)));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"radio", new UnlocalizedValue(StringPool.BLANK)));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"text", new UnlocalizedValue(StringPool.BLANK)));

		DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
			_userId, _group.getGroupId(), ddmFormInstance.getFormInstanceId(),
			ddmFormValues, getServiceContext());

		DDMFormInstanceReport ddmFormInstanceReport =
			DDMFormInstanceReportLocalServiceUtil.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstance.getFormInstanceId());

		_testUpgrade(ddmFormInstanceReport);
	}

	@Test
	public void testUpgradeDDMFormInstanceReportWithNoEntries()
		throws Exception {

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				_createDDMForm(), _group, _userId);

		DDMFormInstanceReport ddmFormInstanceReport =
			DDMFormInstanceReportLocalServiceUtil.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstance.getFormInstanceId());

		_testUpgrade(ddmFormInstanceReport);
	}

	@Test
	public void testUpgradeWithExistingDDMFormInstanceReports()
		throws Exception {

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				_createDDMForm(), _group, _userId);

		_createDDMFormInstanceRecords(ddmFormInstance);

		DDMFormInstanceReport ddmFormInstanceReport =
			DDMFormInstanceReportLocalServiceUtil.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstance.getFormInstanceId());

		_testUpgrade(ddmFormInstanceReport);
	}

	@Test
	public void testUpgradeWithNoDDMFormInstanceReports() throws Exception {
		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				_createDDMForm(), _group, _userId);

		_createDDMFormInstanceRecords(ddmFormInstance);

		DDMFormInstanceReport ddmFormInstanceReport =
			DDMFormInstanceReportLocalServiceUtil.
				getFormInstanceReportByFormInstanceId(
					ddmFormInstance.getFormInstanceId());

		JSONObject expectedDataJSONObject = _jsonFactory.createJSONObject(
			ddmFormInstanceReport.getData());

		DDMFormInstanceReportLocalServiceUtil.deleteDDMFormInstanceReport(
			ddmFormInstanceReport);

		Assert.assertNull(
			DDMFormInstanceReportLocalServiceUtil.fetchDDMFormInstanceReport(
				ddmFormInstanceReport.getFormInstanceReportId()));

		_upgradeDDMFormInstanceReport.upgrade();

		ddmFormInstanceReport = _getDDMFormInstanceReport(
			ddmFormInstance.getFormInstanceId());

		JSONObject actualDataJSONObject = _jsonFactory.createJSONObject(
			ddmFormInstanceReport.getData());

		Assert.assertEquals(
			expectedDataJSONObject.toString(), actualDataJSONObject.toString());
	}

	protected ServiceContext getServiceContext() throws PortalException {
		return ServiceContextTestUtil.getServiceContext(_group, _userId);
	}

	protected void setUpUpgradeDDMFormInstanceReport() {
		_upgradeStepRegistrator.register(
			new UpgradeStepRegistrator.Registry() {

				@Override
				public void register(
					String fromSchemaVersionString,
					String toSchemaVersionString, UpgradeStep... upgradeSteps) {

					for (UpgradeStep upgradeStep : upgradeSteps) {
						Class<?> clazz = upgradeStep.getClass();

						String className = clazz.getName();

						if (className.contains(_CLASS_NAME)) {
							_upgradeDDMFormInstanceReport =
								(UpgradeProcess)upgradeStep;
						}
					}
				}

			});
	}

	private DDMForm _createDDMForm() {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm();

		DDMFormField checkboxMultipleDDMFormField =
			DDMFormTestUtil.createDDMFormField(
				"checkboxMultiple", "checkboxMultiple", "checkbox_multiple",
				"string", false, false, false);

		DDMFormFieldOptions checkboxMultipleDDMFormFieldOptions =
			checkboxMultipleDDMFormField.getDDMFormFieldOptions();

		checkboxMultipleDDMFormFieldOptions.addOptionLabel(
			"option0", LocaleUtil.US, "option0");
		checkboxMultipleDDMFormFieldOptions.addOptionLabel(
			"option1", LocaleUtil.US, "option1");
		checkboxMultipleDDMFormFieldOptions.addOptionLabel(
			"option2", LocaleUtil.US, "option2");
		checkboxMultipleDDMFormFieldOptions.addOptionLabel(
			"option3", LocaleUtil.US, "option3");
		checkboxMultipleDDMFormFieldOptions.addOptionLabel(
			"option4", LocaleUtil.US, "option4");

		ddmForm.addDDMFormField(checkboxMultipleDDMFormField);

		DDMFormField gridDDMFormField = DDMFormTestUtil.createDDMFormField(
			"grid", "grid", "grid", "string", false, false, false);

		DDMFormFieldOptions gridColumnDDMFormFieldOptions =
			new DDMFormFieldOptions();

		gridColumnDDMFormFieldOptions.addOptionLabel(
			"column0", LocaleUtil.US, "column0");
		gridColumnDDMFormFieldOptions.addOptionLabel(
			"column1", LocaleUtil.US, "column1");
		gridColumnDDMFormFieldOptions.addOptionLabel(
			"column2", LocaleUtil.US, "column2");
		gridColumnDDMFormFieldOptions.addOptionLabel(
			"column3", LocaleUtil.US, "column3");
		gridColumnDDMFormFieldOptions.addOptionLabel(
			"column4", LocaleUtil.US, "column4");

		gridDDMFormField.setProperty("columns", gridColumnDDMFormFieldOptions);

		DDMFormFieldOptions gridRowDDMFormFieldOptions =
			new DDMFormFieldOptions();

		gridRowDDMFormFieldOptions.addOptionLabel(
			"row0", LocaleUtil.US, "row0");
		gridRowDDMFormFieldOptions.addOptionLabel(
			"row1", LocaleUtil.US, "row1");
		gridRowDDMFormFieldOptions.addOptionLabel(
			"row2", LocaleUtil.US, "row2");
		gridRowDDMFormFieldOptions.addOptionLabel(
			"row3", LocaleUtil.US, "row3");
		gridRowDDMFormFieldOptions.addOptionLabel(
			"row4", LocaleUtil.US, "row4");

		gridDDMFormField.setProperty("rows", gridRowDDMFormFieldOptions);

		ddmForm.addDDMFormField(gridDDMFormField);

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"numeric", "numeric", "numeric", FieldConstants.DOUBLE, false,
				false, false));

		DDMFormField radioDDMFormField = DDMFormTestUtil.createDDMFormField(
			"radio", "radio", "radio", "string", false, false, false);

		DDMFormFieldOptions radioDDMFormFieldOptions =
			radioDDMFormField.getDDMFormFieldOptions();

		radioDDMFormFieldOptions.addOptionLabel(
			"option0", LocaleUtil.US, "option0");
		radioDDMFormFieldOptions.addOptionLabel(
			"option1", LocaleUtil.US, "option1");
		radioDDMFormFieldOptions.addOptionLabel(
			"option2", LocaleUtil.US, "option2");
		radioDDMFormFieldOptions.addOptionLabel(
			"option3", LocaleUtil.US, "option3");
		radioDDMFormFieldOptions.addOptionLabel(
			"option4", LocaleUtil.US, "option4");

		ddmForm.addDDMFormField(radioDDMFormField);

		ddmForm.addDDMFormField(
			DDMFormTestUtil.createDDMFormField(
				"text", "text", "text", "string", false, false, false));

		return ddmForm;
	}

	private DDMFormInstanceRecord _createDDMFormInstanceRecord(
			DDMForm ddmForm, ServiceContext serviceContext)
		throws Exception, PortalException {

		DDMFormInstance ddmFormInstance =
			DDMFormInstanceTestUtil.addDDMFormInstance(
				ddmForm, _group, _userId);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"text", new UnlocalizedValue("Approved Text")));

		return DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
			_userId, _group.getGroupId(), ddmFormInstance.getFormInstanceId(),
			ddmFormValues, serviceContext);
	}

	private void _createDDMFormInstanceRecords(DDMFormInstance ddmFormInstance)
		throws Exception {

		DDMForm ddmForm = _createDDMForm();

		for (int qtd = 0; qtd < _DDM_FORM_INSTANCE_RECORD_MAX_QTD; qtd++) {
			DDMFormValues ddmFormValues =
				DDMFormValuesTestUtil.createDDMFormValues(ddmForm);

			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"checkboxMultiple",
					new UnlocalizedValue(
						JSONUtil.put(
							"option" + qtd
						).toString())));

			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"grid",
					new UnlocalizedValue(
						JSONUtil.put(
							"row" + qtd, "column" + qtd
						).toString())));

			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"numeric", new UnlocalizedValue(String.valueOf(qtd))));

			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"radio", new UnlocalizedValue("option" + qtd)));

			ddmFormValues.addDDMFormFieldValue(
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					"text", new UnlocalizedValue("value " + qtd)));

			DDMFormInstanceRecordLocalServiceUtil.addFormInstanceRecord(
				_userId, _group.getGroupId(),
				ddmFormInstance.getFormInstanceId(), ddmFormValues,
				getServiceContext());
		}
	}

	private DDMFormInstanceReport _getDDMFormInstanceReport(long formInstanceId)
		throws Exception {

		EntityCacheUtil.clearCache();

		return DDMFormInstanceReportLocalServiceUtil.
			getFormInstanceReportByFormInstanceId(formInstanceId);
	}

	private void _testUpgrade(DDMFormInstanceReport ddmFormInstanceReport)
		throws Exception {

		JSONObject expectedDataJSONObject = _jsonFactory.createJSONObject(
			ddmFormInstanceReport.getData());

		_upgradeDDMFormInstanceReport.upgrade();

		ddmFormInstanceReport = _getDDMFormInstanceReport(
			ddmFormInstanceReport.getFormInstanceId());

		JSONObject actualDataJSONObject = _jsonFactory.createJSONObject(
			ddmFormInstanceReport.getData());

		Assert.assertEquals(
			expectedDataJSONObject.toString(), actualDataJSONObject.toString());
	}

	private DDMFormValues _updateTextDDMFormFieldValue(
		DDMForm ddmForm, String value) {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"text", new UnlocalizedValue(value)));

		return ddmFormValues;
	}

	private static final String _CLASS_NAME =
		"com.liferay.dynamic.data.mapping.internal.upgrade.v3_7_3." +
			"UpgradeDDMFormInstanceReport";

	private static final int _DDM_FORM_INSTANCE_RECORD_MAX_QTD = 5;

	@Inject(
		filter = "(&(objectClass=com.liferay.dynamic.data.mapping.internal.upgrade.DDMServiceUpgrade))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Group _group;

	private JSONFactory _jsonFactory;
	private UpgradeProcess _upgradeDDMFormInstanceReport;
	private long _userId;

}